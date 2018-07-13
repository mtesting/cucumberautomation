package steps.att;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import apiLevelInteraction.SportsbookHelper;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.PeriodScore;
import att.MarketsHelper;
import att.ResultHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import entities.Customer;
import generated.ats.sportsbook.punter.dto.Selection;
import other.Constants;
import util.Utils;

public class GenericSteps extends EventSteps {

    private static final Logger log = Logger.getLogger(GenericSteps.class);

    private final SportsbookHelper sportsbook;
    private Customer customer;


    public GenericSteps(SportsbookHelper sportsbook, Customer customer) throws DecoderConfigException {
        this.sportsbook = sportsbook;
        this.customer = customer;
    }

    @And("^mirror resulting \"([^\"]*)\" is sent to ATS$")
    public void betradarMirrorResultingIsSentToATS(String excelSheet) {
        ResultHelper resultHelper = new ResultHelper();

        //TODO temp solution for scoreboard resulting, a proper function will be implemented later
        PeriodScore periodScoreHt = new PeriodScore();
        periodScoreHt.setType("HT");
        periodScoreHt.setScore("0:0");

        PeriodScore periodScoreFt = new PeriodScore();
        periodScoreFt.setType("FT");

        List<PeriodScore> periodScores = new ArrayList<>();
        periodScores.add(periodScoreHt);
        periodScores.add(periodScoreFt);

        switch (resultHelper.loadBetradarMirrorResulting(excelSheet).get(0).getOutcome()){
            case "1":
                periodScoreFt.setScore("1:0");
                break;
            case "X":
                periodScoreFt.setScore("0:0");
                break;
            case "2":
                periodScoreFt.setScore("0:1");
                break;
        }

        eventHelper.sendBetradarMirrorResults(periodScores, resultHelper.loadBetradarMirrorResulting(excelSheet));
    }

    @Then("^the relevant markets should get settled$")
    public void the_relevant_markets_should_get_settled() throws Throwable {
        Utils.waitSeconds(120); //AlgoMgr has 2min delay when doing the resulting
        int timeout = 15;
        int pollIntervalSecs = 1;
        boolean resulted = false;
        while ((!resulted) && timeout > 0){
            Utils.waitSeconds(pollIntervalSecs);
            timeout -= pollIntervalSecs;
            try{
                resulted = eventHelper.assertMarketsSettled(decoder.decodeCustomerDb(Constants.TESTING_ENV));
            } catch (AssertionError e){
               log.warn(e);
            }
        }
        eventHelper.assertMarketsSettled(decoder.decodeCustomerDb(Constants.TESTING_ENV));
    }

    @Then("^incidents from dataset will be replayed for the event$")
    public void incidents_from_dataset_will_be_replayed_for_the_event() {
        eventHelper.scheduleInplay();
    }

    /**
     * To replay 1 event with the given dataset
     */
    @Given("^a \"([^\"]*)\" event set as authorized, displayed, in-play$")
    public void aEventSetAsAuthorizedDisplayedInPlay(String sportType, Map<String, String> data) throws Throwable {
        String dataset = "";
        if (!Constants.REPLAY_DATASET.equalsIgnoreCase("")){
            dataset = Constants.REPLAY_DATASET;
        } else if ((data.get("DataSet")!= null) ||(!Objects.equals(data.get("DataSet"), ""))) {
            dataset = data.get("DataSet");
        }

        eventHelper.createEventSchedule(DecoderManager.getManager().getDecoder().getCompetitionId(sportType), data.get("Incidents"),
                1, 1, data.get("Pricing"), dataset, null, "currenttime");
        eventHelper.assertEventIsCreated();
        eventHelper.waitForEventToActiveDisplayed();
    }

    @Given("^a \"([^\"]*)\" event with market data \"([^\"]*)\" set as authorized, displayed and in-play$")
    public void aEventWithMarketDataSetAsAuthorizedDisplayedAndInPlay(String sportType, String excelSheet, Map<String, String> data) throws Throwable {
        EventDefinition.Markets markets;
        if (data.get("Incidents").equalsIgnoreCase("LSPORTS")) {
            markets = MarketsHelper.prepareLsportsMirrorMarkets("MarketLSports.xlsx", excelSheet);
        } else {
            markets = MarketsHelper.prepareBetradarMirrorMarkets(Constants.BETRADAR_MIRROR_INPUT, excelSheet);
        }
        eventHelper.createEvent(decoder.getCompetitionId(sportType), data.get("Incidents"),
                1, 1, data.get("Pricing"), "currenttime", markets);
        eventHelper.assertEventIsCreated();
        eventHelper.waitForEventToActiveDisplayed();
        eventHelper.scheduleInplay();
    }

    @Then("^user successfully place bets")
    public void userSuccessfullyPlaceBets(Map<String, String> betsData) throws Throwable {
        String betType = betsData.get("betType");
        String mktType = betsData.get("mktType");
        String winType = "";
        if (betsData.get("winType") != null) winType = betsData.get("winType");
        String priceType = "";
        if (betsData.get("priceType") != null) priceType = betsData.get("priceType");

        List<Selection> selections;

        //customer.setBalance(sportsbook.getWalletBalance());

        if (betType.equalsIgnoreCase("SINGLE")){
            customer.placeBetsResponse = sportsbook.placeRandomBets(betType, mktType,
                    winType, priceType, sportsbook.getEvent(Integer.valueOf(eventHelper.getEvent().getEventRef())));
        } else{
            selections = getSingleSelectionPerEvent(mktType, sportsbook);
            customer.placeBetsResponse = sportsbook.placeBet(selections, betType, betType, priceType);
        }

        assertBetPlacement(customer.placeBetsResponse);

//        if(!Constants.CUSTOMER_IN_TEST.equalsIgnoreCase("INTRALOT")){
//            verifyWalletUpdateAfterBetsPlacement(customer, sportsbook);
//        }
    }

    @Then("^the markets should be settled in \"([^\"]*)\" as specified in \"([^\"]*)\" sheet \"([^\"]*)\" within (\\d+) minutes$")
    public void validateResult(String endPoint, String inputPath, String sheetName, int waitTimeMin) throws Throwable {
        this.assertStatusMarketsSettled(endPoint, Utils.getSourceAsAbsolutePath("incidents/" +inputPath), sheetName, waitTimeMin * 60);
    }
    @And("^wait (\\d+) minute$")
    public void waitNmin(int n) {
        Utils.waitSeconds(60 * n);
    }

    @And("^wait (\\d+) second")
    public void waitNSec(int n) {
        Utils.waitSeconds(n);
    }

    @And("^mirror update \"([^\"]*)\" is sent to ATS$")
    public void mirrorUpdateIsSentToATS(String excelSheet) throws Throwable {
        EventDefinition.Markets markets = MarketsHelper.prepareBetradarMirrorMarkets(Constants.BETRADAR_MIRROR_INPUT, excelSheet);
        eventHelper.updateMirror(markets);
    }

    @And("^a LiveOdds update \"([^\"]*)\" is sent to ATS$")
    public void aLiveOddsUpdateIsSentToATS(String inputFile) throws Throwable {
        eventHelper.sendLiveOddsUpdate(MarketsHelper.prepareLiveOddsMatchUpdate("incidents/LiveOdds/" + inputFile));
    }

}