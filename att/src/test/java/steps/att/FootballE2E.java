package steps.att;

import org.apache.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import ats.betting.model.domain.Instrument;
import ats.betting.model.domain.Market;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import att.MarketsHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.DataBaseHelper;
import decoders.DecoderConfigException;
import other.Constants;
import other.EventDataService;
import util.Utils;

public class FootballE2E extends EventSteps {

    private static final Logger log = Logger.getLogger(FootballE2E.class);

    public FootballE2E() throws DecoderConfigException {
    }

    @When("^the event runs the incidents from \"([^\"]*)\"$")
    public void theEventRunsTheIncidentsFrom(String fileName) throws Throwable {
        eventHelper.processIncidents(Utils.getSourceAsAbsolutePath("incidents/Football/" + fileName), null);
    }

    @Then("^the extra markets should get \"([^\"]*)\"$")
    public void the_extra_markets_should_get_status(String status) throws Throwable {
        EventDataService eds = new EventDataService(decoder.decodeCustomerDb(Constants.TESTING_ENV));

        DataBaseHelper db = eds.getDbHelper();
        List<Market> mlist = db.getMarketsFromDB(eventHelper.getEvent().getEventRef());

        for (Market market : mlist) {
            if (market.getName().toLowerCase().contains("extra")) {
                if ((market.getName().contains("3.5") || market.getName().contains("4.5")) && status.equalsIgnoreCase("open")) {
                    log.info("Ignoring Market (due to PSL) :" + market.getName());
                    log.info("Expected Status : SUSPENDED (due to PSL)");
                    log.info("Actual Status : " + market.getState());
                    //Assert.assertTrue(market.getStatus().equalsIgnoreCase("SUSPENDED"));    	
                } else {
                    log.info("Validating Market :" + market.getName());
                    log.info("Expected Status : " + status);
                    log.info("Actual Status : " + market.getState());
                    Assert.assertTrue("Market not as expected status=" + market.getState() + " for market=" + market.getName(),
                            (status.toUpperCase()).contains(market.getState().toString().toUpperCase()));
                }
            }
        }

    }

    @And("^there is a feed disconnection$")
    public void thereIsAFeedDisconnection() throws Throwable {
        theEventRunsTheIncidentsFrom("disconnect.csv");
        Utils.waitSeconds(180);
        theEventRunsTheIncidentsFrom("reconnect.csv");
    }

    @And("^there is a partial feed disconnection$")
    public void thereIsAPartialFeedDisconnection() throws Throwable {
        theEventRunsTheIncidentsFrom("partialdisconnect.csv");
        Utils.waitSeconds(210);
        theEventRunsTheIncidentsFrom("reconnect.csv");
    }

    @And("^the relevant markets should be settled in \"([^\"]*)\"$")
    public void EndPointService(String endPoint) throws Throwable {
        this.assertStatusMarketsSettled(endPoint, Utils.getSourceAsAbsolutePath("incidents/Football/FootBallResult.xlsx"), "result");
    }

    @And("^the feed heartbeat alive reply stops$")
    public void theFeedHeartbeatAliveReplyStops() throws Throwable {
        eventHelper.setBetradarAlive(false);
        Utils.waitSeconds(180);
        eventHelper.setBetradarAlive(true);
    }

    @When("^the event gets score updates from \"([^\"]*)\"$")
    public void theEventGetsScoreUpdatesFrom(String incidentsFile) throws Throwable {
        eventHelper.processLSportsIncidents(Utils.getSourceAsAbsolutePath("incidents/" + incidentsFile));
    }

    @Then("^the Correct Score market gets resulted successfully$")
    public void theCorrectScoreMarketGetsResultedSuccessfully(List<List<String>> data) throws Throwable {
        EventDataService eds = new EventDataService(decoder.decodeCustomerDb(Constants.TESTING_ENV));
        DataBaseHelper db = eds.getDbHelper();
        List<Market> marketList = db.getMarketsFromDB(eventHelper.getEvent().getEventRef());
        for (Market market : marketList) {
            if (market.getType().equalsIgnoreCase("CSFT")) {
                for (Instrument selection : market.getInstruments()) {
                    if (selection.getName().equalsIgnoreCase(data.get(0).get(0))) {
                        Assert.assertTrue("Resulting failed for instrument=" + selection.getId(),
                                selection.getResult().equalsIgnoreCase(data.get(0).get(1)));
                    } else if (selection.getName().equalsIgnoreCase(data.get(1).get(0))) {
                        Assert.assertTrue("Resulting failed for instrument=" + selection.getId(),
                                selection.getResult().equalsIgnoreCase(data.get(1).get(1)));
                    } else if (selection.getName().equalsIgnoreCase(data.get(2).get(0))) {
                        Assert.assertTrue("Resulting failed for instrument=" + selection.getId(),
                                selection.getResult().equalsIgnoreCase(data.get(2).get(1)));
                    }
                }
            }
        }
    }

    @Given("^a football event is sent by the feed to ATS$")
    public void aeventIsSentByTheFeedToATS(Map<String, String> data) throws Throwable {
        eventHelper.createEvent(decoder.getCompetitionId("football"), data.get("Incidents"),
                1, 1, data.get("Pricing"), "currenttime", null);
    }

    @Then("^the event gets successfully created$")
    public void theEventGetsSuccessfullyCreated() throws Throwable {
        eventHelper.assertEventIsCreated();
    }

    @When("^\"([^\"]*)\" football event set as authorized, displayed and in-play$")
    public void footballEventSetAsAuthorizedDisplayedAndInPlay(int numberOfEvents, Map<String, String> data) throws Throwable {
        EventDefinition.Markets markets = null;
        if (data.size() >= 3) {
             markets = MarketsHelper.prepareBetradarMirrorMarkets(Constants.BETRADAR_MIRROR_INPUT, data.get("excelSheet"));
        }
        eventHelper.createEvent(decoder.getCompetitionId("football"), data.get("Incidents"), numberOfEvents,
                numberOfEvents, data.get("Pricing"), "currenttime", markets);

        eventHelper.assertEventIsCreated();
        eventHelper.waitForEventToActiveDisplayed();
        eventHelper.scheduleInplay();
    }

    @And("^Abelson Goal Scorer markets added$")
    public void abelsonGoalScorerMarketsAdded() throws Throwable {
        eventHelper.addAbelsonFeed();
    }

}