package apiLevelInteraction;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.security.auth.login.LoginException;

import ats.core.util.json.JsonUtil;
import decoders.DecoderConfigException;
import entities.BetPlacementHelper;
import generated.ats.betsync.betcatcher.dto.Bets;
import generated.ats.betsync.betcatcher.dto.CalculateCashoutRequest;
import generated.ats.betsync.betcatcher.dto.CalculateCashoutResponse;
import generated.ats.betsync.betcatcher.dto.PlaceBetsResponse;
import generated.ats.betsync.dto.BetAmendmentResponse;
import generated.ats.sportsbook.punter.dto.AccountBalance;
import generated.ats.sportsbook.punter.dto.Event;
import generated.ats.sportsbook.punter.dto.Login;
import generated.ats.sportsbook.punter.dto.Market;
import generated.ats.sportsbook.punter.dto.Rule4;
import generated.ats.sportsbook.punter.dto.Selection;
import other.Constants;
import util.Utils;

/**
 * Class designed to perform API level operations on the Sportsbook
 */
public class SportsbookHelper extends ApiHelper {

    private static final Logger log = Logger.getLogger(SportsbookHelper.class);

    private Login login;
    private PsWalletHelper psWalletHelper;
    private HttpPost postRequest;

    private final String customerPunterUrl = decoder.decodePunterUrl(Constants.CUSTOMER_IN_TEST);

    public SportsbookHelper() throws DecoderConfigException {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        psWalletHelper = new PsWalletHelper();
    }

    /**
     * API called when a Sportsbook customer logs into the system as part of authenticating the session
     *
     * @param username account username
     * @param password account password
     */
    public void login(String username, String password) throws LoginException {
        List<NameValuePair> postParameters;
        JSONObject response = null;

        try {
            postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/login");

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("username", username));
            postParameters.add(new BasicNameValuePair("password", password));
            postParameters.add(new BasicNameValuePair("siteId", "2"));
            postParameters.add(new BasicNameValuePair("brandId", "2"));
            postParameters.add(new BasicNameValuePair("application", "web-sportsbook"));
            //request.addHeader("content-type", "application/json");
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            response = executeHttpPost(postRequest);
            log.info("Post to login response=" + response.toString());

            login = mapper.readValue(response.getJSONObject("Login").toString(), Login.class);
        } catch (IOException ex) {
            log.error(ex);
        } catch (JSONException ex){
            throw new LoginException(response.toString());
        }
    }

    /**
     * API call is used to pass through an external login token when the authentication is handled outside of ATS
     *
     * @param username account username
     */
    public void externalLogin(String username) throws LoginException, IOException {
        List<NameValuePair> postParameters;

        postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/externalLogin");

        postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("externalToken", Utils.randomAlphanumeric()));
        postParameters.add(new BasicNameValuePair("signature", "cpVCwjrv%2FgHmSpX2uI8JGOKA%3D%3D"));
        postParameters.add(new BasicNameValuePair("webId", username));
        postParameters.add(new BasicNameValuePair("lsrc", "1"));
        postParameters.add(new BasicNameValuePair("site", "1"));
        postParameters.add(new BasicNameValuePair("locale", "en-gb"));
        postParameters.add(new BasicNameValuePair("application", "web-sportsbook"));
        postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

        JSONObject responseBody = executeHttpPost(postRequest);
        log.info("Post to externalLogin response=" + responseBody.toString());

        try {
            login = mapper.readValue(responseBody.getJSONObject("Login").toString(), Login.class);
        } catch (JSONException ex){
            throw new LoginException(responseBody.toString());
        }
    }

    public void setExternalBalance(String amount) throws JSONException {
        psWalletHelper.setExternalBalance(login, amount);
    }

    /**
     * Performs a bet cash out
     *
     * @param betId bet slip id to be cashed out
     */
    public void cashoutBet(String betId, BigDecimal cashOutStake) {
        log.info("-- STEP -- Cash Out");
        List<NameValuePair> postParameters;

        try {
            postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/cashoutBet");

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("sessionToken", login.getSessionToken()));
            postParameters.add(new BasicNameValuePair("betId", betId));
            postParameters.add(new BasicNameValuePair("selectionId", "304312741")); //TODO find out if is being validated by ats
            postParameters.add(new BasicNameValuePair("cashOutStake", cashOutStake.toString()));
            postParameters.add(new BasicNameValuePair("accountId", String.valueOf(login.getAccountId())));
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
            log.info("cashoutBet response=" + response.toString());

            String status = response.get("status").toString();
            Assert.assertTrue("Cash Out failed, rejectionCode=" + response.get("rejectionCode").toString(),
                    "SUCCESS".equalsIgnoreCase(status));
            log.info("Cash Out successfully, status=" + status);
        } catch (JSONException | UnsupportedEncodingException ex) {
            log.error(ex);
        }
    }

    /**
     * This request is used to calculate the cashout across a collection of bets
     *
     * @return response data
     */
    public CalculateCashoutResponse calculateCashout() {
        log.info("-- STEP -- Calculate Cash Out");
        List<NameValuePair> postParameters;
        CalculateCashoutResponse calculateCashoutResponse = null;

        try {
            postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/calculateCashout");

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("sessionToken", login.getSessionToken()));
            postParameters.add(new BasicNameValuePair("bets", generateCalculateCashoutRequest()));
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
            log.info("calculateCashout response=" + response.toString());

            calculateCashoutResponse = mapper.readValue(response.getJSONObject("CalculateCashoutResponse").toString(),
                    CalculateCashoutResponse.class);
        } catch (JSONException | IOException ex) {
            log.error(ex);
        }
        return calculateCashoutResponse;
    }

    private String generateCalculateCashoutRequest() throws JSONException {
        CalculateCashoutRequest calculateCashoutRequest = new CalculateCashoutRequest();

        calculateCashoutRequest.setAccountId(login.getAccountId());
        calculateCashoutRequest.setChannelId(6);

        Bets bets = getOpenBets();
        calculateCashoutRequest.setBets(bets);

        return JsonUtil.marshalJson(calculateCashoutRequest);
    }

    /**
     * API call that returns all open bets held by the Sportsbook customer
     *
     * @return open bets
     */
    public Bets getOpenBets() throws JSONException {
        List<NameValuePair> postParameters;
        Bets bets = null;

        try {
            postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/getOpenBets");

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("sessionToken", login.getSessionToken()));
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
            log.debug("getOpenBets response=" + response.toString());

            bets = mapper.readValue(response.getJSONObject("Bets").toString(), Bets.class);
        } catch (IOException e) {
            log.error(e);
        }
        return bets;
    }

    /**
     * API call to perform chips buy/sell transactions
     *
     * @param transactionType buy/sell
     * @param amount number of chips to trade
     */
    public void tradeChips(String transactionType, String amount) {
        HttpGet request = new HttpGet(customerPunterUrl + "/payment/rest/chips/" + transactionType + "/" + amount + "/0");

        request.setHeader("sessionToken", login.getSessionToken());

        JSONObject response = executeHttpGet(request);
        log.info("Post to chips response=" + response.toString());
    }

    /**
     * API used to update the Sportsbook customer’s balance, once they complete transactions such as deposit or
     * withdrawal.
     */
    public AccountBalance getBalance() {
        List<NameValuePair> postParameters;
        AccountBalance accountBalance = null;

        try {
            postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/getBalance");

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("sessionToken", login.getSessionToken()));
            //request.addHeader("content-type", "application/json");
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
            log.info("Post to getBalance response=" + response.toString());

            accountBalance = mapper.readValue(response.getJSONObject("AccountBalance").toString(), AccountBalance.class);

        } catch (IOException | JSONException ex) {
            log.error(ex);
        }

        return accountBalance;
    }

    /**
     * Checks the wallet balance for the actual customer in test
     *
     * @return wallet balance
     */
    public BigDecimal getWalletBalance() throws JSONException {
        if (Constants.CUSTOMER_IN_TEST.equalsIgnoreCase("betstars")) {
            return psWalletHelper.getUserInfo(login).getAmount();
        } else {
            return getBalance().getAmount();
        }
    }

    /**
     * API call that return details of a specific event including its attributes, markets, selections and odds
     *
     * @param nodeId event ats id
     * @return event
     */
    public Event getEvent(Integer nodeId) throws IOException {
        String url = (customerPunterUrl + "/sportsbook/v1/api/getEvent?eventId=" + nodeId + "&channelId=6&locale=en-gb&siteId=1");
        log.info("Loading from api=" + url);
        return getObjectFromURL(url, Event.class);
    }

    private <E> E getObjectFromURL(String url, Class<E> objectClass) throws IOException {
        E object;
        try {
            //If added because of different json format btw customers, otherwise a JsonMappingException is thrown
            if (!Constants.CUSTOMER_IN_TEST.equalsIgnoreCase("betstars")) {
                log.info("ObjectMapper set DeserializationFeature.UNWRAP_ROOT_VALUE=true");
                mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            }
            object = mapper.readValue(new URL(url), objectClass);
        } finally {
            log.info("ObjectMapper set DeserializationFeature.UNWRAP_ROOT_VALUE=false");
            mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        }
        return object;
    }

    /**
     * method to place bets with selections default decimal/fractional price
     *
     * @param selections selections to place bets on
     * @param type bet type
     * @param winType bet win type
     */
    public PlaceBetsResponse placeBet(List<Selection> selections, String type, String winType) throws JSONException {
        return placeBet(selections, type, winType, "");
    }

    /**
     * API call that place bets on the desired selections
     *
     * @param selections selections to bet on
     * @param type bet type
     * @param winType selection win type
     * @param priceType selection price type
     */
    public PlaceBetsResponse placeBet(List<Selection> selections, String type, String winType, String priceType) throws JSONException {
        log.info("-- STEP -- Place Bet");
        List<NameValuePair> postParameters;
        PlaceBetsResponse placeBetsResponse = null;
        JSONObject response = null;
        BetPlacementHelper betPlacementHelper = new BetPlacementHelper(selections, type, winType, priceType);


        postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/placeBets");
        addPokerstarsCookies(postRequest);

        postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("bets", betPlacementHelper.buildPlaceBetsRequest(login)));
        postParameters.add(new BasicNameValuePair("sessionToken", login.getSessionToken()));
        postParameters.add(new BasicNameValuePair("isSpinAndBet", "false"));
        postParameters.add(new BasicNameValuePair("siteId", "1"));
        postParameters.add(new BasicNameValuePair("locale", "en-gb"));

        try{
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            response = executeHttpPost(postRequest);
            log.info("placeBets response=" + response.toString());

            placeBetsResponse = mapper.readValue(response.getJSONObject("PlaceBetsResponse").toString(), PlaceBetsResponse.class);

        } catch (IOException ex) {
            log.error(ex);
        }
        return placeBetsResponse;
    }

    private void addPokerstarsCookies(HttpPost request){
        if("BETSTARS".equalsIgnoreCase(Constants.CUSTOMER_IN_TEST)){
            request.addHeader("Cookie",
                    "SBTK=-q10dxP6gWmXK7IldNOhlkCU;" +
                            "ats_st=-q10dxP6gWmXK7IldNOhlkCU;" +
                            "SBSG=-q10dxP6gWmXK7IldNOhlkCU;" +
                            "WBID="+login.getUsername().toUpperCase());
        }
    }

    public PlaceBetsResponse placeRandomBets(String type, String marketType, String winType, String priceType, Event event) throws JSONException {
        return placeBet(selectRandomSelections(marketType, event), type, winType, priceType);
    }

    /**
     * @param market
     */
    public void setRule4(Market market) {
        Rule4 rule4 = new Rule4();
        List<Rule4> rule4s = new ArrayList<>();
        rule4.setDeduction(80);
        //rule4.setKind("SP");
        rule4s.add(rule4);
        market.setRule4S(rule4s);
    }

    public void addDeduction(String betPriceType, int deduction, long marketId) throws JSONException {
        log.info("-- STEP -- Apply Deduction");
        List<NameValuePair> postParameters;
        BetAmendmentResponse betAmendmentResponse;
        long fromTime = ZonedDateTime.now().minusDays(1).toInstant().toEpochMilli();
        long toTime = ZonedDateTime.now().plusDays(1).toInstant().toEpochMilli();

        try {
            postRequest = new HttpPost("https://trading-bob-uat.amelco.co.uk/sb-backoffice/v1/api/amendBets"); //customerPunterUrl

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("useCacheHeaders", "true"));
            postParameters.add(new BasicNameValuePair("sessionToken", "7753d8e44cd344959b106d41e7a3328c")); //login.getSessionToken()
            postParameters.add(new BasicNameValuePair("amendment", String.format("{\"BetAmendmentRequest\":{\"amendMarketResult\"" +
                    ":{\"deductions\":{\"deduction\":[{\"kind\":\"%s\",\"toTime\":%d,\"deduction\":%d,\"comment\":\"\",\"fromTime\":%d}]}," +
                    "\"marketId\":%s}}}", betPriceType, toTime, deduction, fromTime, marketId)));
            postParameters.add(new BasicNameValuePair("siteId", "1"));
            postParameters.add(new BasicNameValuePair("locale", "en-im"));
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
            log.info("amendBets response=" + response.toString());

            betAmendmentResponse = mapper.readValue(response.getJSONObject("BetAmendmentResponse").toString(), BetAmendmentResponse.class);


            String details = betAmendmentResponse.getDetails();
            Assert.assertTrue("Bets amendment failed", betAmendmentResponse.isSuccess());

        } catch (IOException e) {
            log.error(e);
        }

    }

    public Double getMaxAllowedBetStake(List<Selection> selections, String type, String winType, String priceType) {
        log.info("-- STEP -- get Max Allowed Bet Stake");
        List<NameValuePair> postParameters;
        Double maxAllowed = null;
        BetPlacementHelper betPlacementHelper = new BetPlacementHelper(selections, type, winType, priceType);

        try {
            postRequest = new HttpPost(customerPunterUrl + "/sportsbook/v1/api/getMaxAllowedBetStake");

            postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair("bets", betPlacementHelper.buildPlaceBetsRequest(login)));
            postParameters.add(new BasicNameValuePair("sessionToken", login.getSessionToken()));
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));

            JSONObject response = executeHttpPost(postRequest);
            log.debug("getMaxAllowedBetStake response=" + response.toString());
            maxAllowed =  Double.valueOf(response.get("Double").toString());

        } catch (UnsupportedEncodingException | JSONException ex) {
            log.error(ex);
        }
        return maxAllowed;
    }

    public List<Selection> selectRandomSelections(String marketType, Event event){
        List<Selection> selections = null;
        Random random = new Random();
        for (Market market : event.getMarkets()) { //Iterates through the event markets
            if (market.getType().equalsIgnoreCase(marketType)) { //Checks if the market is the required one
                int numberOfSelections = market.getSelection().size();
                int randomIndex = random.nextInt(numberOfSelections);
                selections = new ArrayList<>();
                selections.add(market.getSelection().get(randomIndex)); //add the selection for betting
                //market.getSelection().remove(randomIndex); //remove the selection from the available list
                //numberOfSelections--; //decrease the number of selections available
            }
        }
        Assert.assertNotNull("No selections found for mrkt_type=" + marketType, selections);
        return selections;
    }

}