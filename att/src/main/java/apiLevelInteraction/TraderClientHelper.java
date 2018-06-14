package apiLevelInteraction;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import ats.algo.core.MarketGroup;
import ats.algo.core.baseclasses.GenericMatchParams;
import ats.algo.core.baseclasses.MatchParam;
import ats.algo.core.baseclasses.MatchParamType;
import ats.algo.genericsupportfunctions.Gaussian;
import ats.core.util.json.JsonUtil;
import decoders.DecoderConfigException;
import generated.ats.betsync.dto.AmendMarketResult;
import generated.ats.betsync.dto.BetAmendmentRequest;
import generated.ats.betsync.dto.Deduction;
import generated.ats.betsync.dto.DeductionType;
import generated.ats.betsync.dto.Deductions;
import generated.ats.sportsbook.punter.dto.Login;
import other.Constants;

public class TraderClientHelper extends ApiPostHelper {

    private static final Logger log = Logger.getLogger(TraderClientHelper.class);

    private Login backofficeLogin;

    private final String customerTradingUrl = decoder.decodeTradingUrl(Constants.CUSTOMER_IN_TEST);

    public TraderClientHelper() throws DecoderConfigException, LoginException {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        log.debug("customerTradingUrl=" + customerTradingUrl);
        backofficeLogin();
    }

    /**
     * API called for BackOffice system login (sb-backoffice)
     */
    public void backofficeLogin() throws LoginException {
        List<NameValuePair> postParameters;

        try {
            HttpPost request = new HttpPost(customerTradingUrl + "/trader-api/v1/api/internalLogin");
            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("username", "test1"));
            postParameters.add(new BasicNameValuePair("password", "test1"));
            postParameters.add(new BasicNameValuePair("application", "sb-backoffice"));
            //request.addHeader("content-type", "application/json");
            request.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(request);
            log.info("Post to login response=" + response.toString());

            backofficeLogin = mapper.readValue(response.getJSONObject("Login").toString(), Login.class);
        } catch (IOException ex) {
            log.error(ex);
        } catch (JSONException ex) {
            log.error(ex);
            throw new LoginException("Trader client login failed");
        }
    }

    /**
     * Method to set event tier level
     *
     * @param eventID ATS event id
     * @param tierLevel event tier level
     */
    public void setTierLevelViaAPI(String eventID, String tierLevel) {
        log.info("--STEP-- set event tier level");
        HttpPost request = new HttpPost(customerTradingUrl + "/ats-trader/api/settings/save/BOOKMAKING/tierLevel/"
                + tierLevel + "/node/" + eventID);

        request.addHeader("trader-session-token", backofficeLogin.getSessionToken());

        JSONObject response = executeHttpPost(request);
        log.info("Post to tierLevel response=" + response.toString());
        try {
            Assert.assertEquals("Set tierLevel failed", "OK", response.get("status"));
        } catch (JSONException e) {
            log.error(e);
        }
    }

    /**
     * Method to save event MP
     * @param eventId ATS event id
     */
    public void saveMatchParams(String eventId) throws JSONException {
        log.info("--STEP-- save match params for " + eventId);
        HttpPost request = new HttpPost(customerTradingUrl + "/ats-trader/api/match/params/save");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("trader-session-token", backofficeLogin.getSessionToken());

        LinkedHashMap<String, MatchParam> mpList = new LinkedHashMap<>();

        MatchParam teamAPreMatchLine = new MatchParam();
        teamAPreMatchLine.setMatchParameterType(MatchParamType.A);
        teamAPreMatchLine.setGaussian(new Gaussian(0.67, 0.05));
        teamAPreMatchLine.setMarketGroup(MarketGroup.NOT_SPECIFIED);
        teamAPreMatchLine.setMinAllowedParamValue(0.45);
        teamAPreMatchLine.setMaxAllowedParamValue(0.99);
        teamAPreMatchLine.setDisplayAsPercentage(true);
        teamAPreMatchLine.setDescription("Probability team A wins match");

        mpList.put("teamAPreMatchLine", teamAPreMatchLine);

        MatchParam teamBPreMatchLine = new MatchParam();
        teamBPreMatchLine.setMatchParameterType(MatchParamType.B);
        teamBPreMatchLine.setGaussian(new Gaussian(0.655, 0.05));
        teamBPreMatchLine.setMarketGroup(MarketGroup.NOT_SPECIFIED);
        teamBPreMatchLine.setMinAllowedParamValue(0.45);
        teamBPreMatchLine.setMaxAllowedParamValue(0.99);
        teamBPreMatchLine.setDisplayAsPercentage(true);
        teamBPreMatchLine.setDescription("Probability team B wins match");

        mpList.put("teamBPreMatchLine", teamBPreMatchLine);


        GenericMatchParams genericMatchParams = new GenericMatchParams();
        genericMatchParams.setEventId(Long.valueOf(eventId));
        //genericMatchParams.setRequestId("CreateEvent_" + eventId);
        //genericMatchParams.setRequestTime(0);
        genericMatchParams.setOriginatingClassName("ats.algo.sport.tennis.TennisMatchParams");
        genericMatchParams.setParamMap(mpList);

        String json = JsonUtil.marshalJson(genericMatchParams);
        log.debug("Post to saveMatchParams \n" + json);

        try {
            StringEntity entity = new StringEntity(json);
            request.setEntity(entity);

            JSONObject response = executeHttpPost(request);
            log.info("Match param response=" + response.toString());

            Assert.assertEquals("Save match params failed error="+response.get("error"), "OK", response.get("status"));
        } catch (IOException ex) {
            log.error(ex);
        }
    }

    /**
     * API called when performing bet amends such as rule4 deductions
     *
     * @param deductionType SP or LP
     * @param deductionAmount deduction amount
     * @param marketId ats market id //* @return api response object
     */
    public void betAmendment(String deductionType, Integer deductionAmount, Long marketId) {
        //backofficeLogin();
        List<NameValuePair> postParameters;
        //BetAmendmentResponse betAmendmentResponse = null;

        BetAmendmentRequest betAmendmentRequest = new BetAmendmentRequest();
        AmendMarketResult amendMarketResult = new AmendMarketResult();
        Deductions deductions = new Deductions();
        List<Deduction> deductionsList = new ArrayList<>();
        Deduction deduction = new Deduction();

        deduction.setKind(DeductionType.fromValue(deductionType));
        deduction.setToTime(ZonedDateTime.now().plusDays(1).toEpochSecond()); //(System.currentTimeMillis()+86400000)/1000);
        deduction.setDeduction(deductionAmount);
        deduction.setFromTime(ZonedDateTime.now().minusDays(1).toEpochSecond());//System.currentTimeMillis()/1000);

        deductionsList.add(deduction);
        deductions.setDeduction(deductionsList);
        amendMarketResult.setDeductions(deductions);
        amendMarketResult.setMarketId(marketId);
        betAmendmentRequest.setAmendMarketResult(amendMarketResult);

        try {
            HttpPost request = new HttpPost(customerTradingUrl + "/sb-backoffice/v1/api/amendBets");

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("amendment", JsonUtil.marshalJson(betAmendmentRequest)));
            postParameters.add(new BasicNameValuePair("sessionToken", backofficeLogin.getSessionToken()));
            request.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(request);
            log.info("amendBets response=" + response.toString());

            Assert.assertEquals("", response.get("status").toString(), "OK");
            //betAmendmentResponse = mapper.readValue(response.getJSONObject("amendment").toString(), BetAmendmentResponse.class);
        } catch (UnsupportedEncodingException | JSONException ex) {
            log.error(ex);
        }
        //return betAmendmentResponse;
    }

    /**
     * API call to check market selections info (id, name, result, etc)
     * @param marketId ATS market id
     * @throws URISyntaxException string could not be parsed as a URI
     */
    public void getMarketResulting(String marketId) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(customerTradingUrl + "/ats-trader/api/resulting/market/" + marketId
                + "/selections");

        HttpGet request = new HttpGet(builder.build());
        request.addHeader("Accept", "application/json");
        request.addHeader("trader-session-token", backofficeLogin.getSessionToken());

        JSONObject response = executeHttpGet(request);
        log.info("getMarketResulting response=" + response.toString());

        //return mapper.readValue(response.toString(), MatchResultMap.class);
    }

}
