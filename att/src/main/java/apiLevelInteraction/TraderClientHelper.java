package apiLevelInteraction;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpHeaders;
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
    private HttpPost postRequest;

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
            postRequest = new HttpPost(customerTradingUrl + "/trader-api/v1/api/internalLogin");
            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("username", "test1"));
            postParameters.add(new BasicNameValuePair("password", "test1"));
            postParameters.add(new BasicNameValuePair("application", "sb-backoffice"));
            //request.addHeader("content-type", "application/json");
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
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
        postRequest = new HttpPost(customerTradingUrl + "/ats-trader/api/settings/save/BOOKMAKING/tierLevel/"
                + tierLevel + "/node/" + eventID);

        postRequest.addHeader("trader-session-token", backofficeLogin.getSessionToken());

        JSONObject response = executeHttpPost(postRequest);
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
        postRequest = new HttpPost(customerTradingUrl + "/ats-trader/api/match/params/save");
        postRequest.setHeader(HttpHeaders.ACCEPT, "application/json");
        postRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        postRequest.setHeader("trader-session-token", backofficeLogin.getSessionToken());

        GenericMatchParams genericMatchParams = generateMatchParams(eventId);

        String json = JsonUtil.marshalJson(genericMatchParams);
        log.debug("Post to saveMatchParams \n" + json);

        try {
            StringEntity entity = new StringEntity(json);
            postRequest.setEntity(entity);

            JSONObject response = executeHttpPost(postRequest);
            log.info("Match param response=" + response.toString());

            Assert.assertEquals("Save match params failed error="+response.get("error"), "OK", response.get("status"));
        } catch (IOException ex) {
            log.error(ex);
        }
    }

    private GenericMatchParams generateMatchParams(String eventId){
        LinkedHashMap<String, MatchParam> mpList = new LinkedHashMap<>();

        MatchParam teamAPreMatchLine = generateMatchParam(MatchParamType.A,"Probability team A wins match");
        mpList.put("teamAPreMatchLine", teamAPreMatchLine);

        MatchParam teamBPreMatchLine = generateMatchParam(MatchParamType.B,"Probability team B wins match");
        mpList.put("teamBPreMatchLine", teamBPreMatchLine);

        GenericMatchParams genericMatchParams = new GenericMatchParams();
        genericMatchParams.setEventId(Long.valueOf(eventId));
        //genericMatchParams.setRequestId("CreateEvent_" + eventId);
        //genericMatchParams.setRequestTime(0);
        genericMatchParams.setOriginatingClassName("ats.algo.sport.tennis.TennisMatchParams");
        genericMatchParams.setParamMap(mpList);

        return genericMatchParams;
    }

    private MatchParam generateMatchParam(MatchParamType matchParamType, String description){
        MatchParam matchParam = new MatchParam();
        matchParam.setMatchParameterType(matchParamType);
        matchParam.setGaussian(new Gaussian(0.67, 0.05));
        matchParam.setMarketGroup(MarketGroup.NOT_SPECIFIED);
        matchParam.setMinAllowedParamValue(0.45);
        matchParam.setMaxAllowedParamValue(0.99);
        matchParam.setDisplayAsPercentage(true);
        matchParam.setDescription(description);

        return matchParam;
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
        BetAmendmentRequest betAmendmentRequest = generateBetAmendmentRequest(deductionType, deductionAmount, marketId);

        postRequest = new HttpPost(customerTradingUrl + "/sb-backoffice/v1/api/amendBets");

        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("amendment", JsonUtil.marshalJson(betAmendmentRequest)));
        postParameters.add(new BasicNameValuePair("sessionToken", backofficeLogin.getSessionToken()));

        try {
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
            log.info("amendBets response=" + response.toString());

            Assert.assertEquals("", response.get("status").toString(), "OK");
            //betAmendmentResponse = mapper.readValue(response.getJSONObject("amendment").toString(), BetAmendmentResponse.class);
        } catch (UnsupportedEncodingException | JSONException ex) {
            log.error(ex);
        }
        //return betAmendmentResponse;
    }

    private BetAmendmentRequest generateBetAmendmentRequest(String deductionType, Integer deductionAmount, Long marketId){
        BetAmendmentRequest betAmendmentRequest = new BetAmendmentRequest();
        AmendMarketResult amendMarketResult = new AmendMarketResult();
        Deductions deductions = new Deductions();
        List<Deduction> deductionsList = new ArrayList<>();
        Deduction deduction = new Deduction();

        deduction.setKind(DeductionType.fromValue(deductionType));
        deduction.setToTime(ZonedDateTime.now().plusDays(1).toEpochSecond());
        deduction.setDeduction(deductionAmount);
        deduction.setFromTime(ZonedDateTime.now().minusDays(1).toEpochSecond());

        deductionsList.add(deduction);
        deductions.setDeduction(deductionsList);
        amendMarketResult.setDeductions(deductions);
        amendMarketResult.setMarketId(marketId);
        betAmendmentRequest.setAmendMarketResult(amendMarketResult);

        return betAmendmentRequest;
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
        request.addHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("trader-session-token", backofficeLogin.getSessionToken());

        JSONObject response = executeHttpGet(request);
        log.info("getMarketResulting response=" + response.toString());

        //return mapper.readValue(response.toString(), MatchResultMap.class);
    }

}
