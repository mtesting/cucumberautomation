package steps.att;

import org.apache.log4j.Logger;

import java.util.Map;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import decoders.DecoderConfigException;
import other.Constants;
import util.Utils;

public class Tennis extends EventSteps {

    private static final Logger log = Logger.getLogger(Tennis.class);

    public Tennis() throws DecoderConfigException {
    }

    public String getEventId(){
        return eventHelper.getEvent().getEventRef();
    }

    @Given("^a tennis event set as authorized, displayed and in-play$")
    public void a_tennis_event_set_as_authorized_displayed_and_in_play(Map<String, String> data) throws Throwable {
        eventHelper.createEvent(decoder.getCompetitionId("tennis"), data.get("Incidents"),
                1,1, "", "currenttime", null);
        eventHelper.assertEventIsCreated();
        eventHelper.waitForEventToActiveDisplayed();
        eventHelper.scheduleInplay();
    }

    @Given("^a tennis doubles event set as authorized, displayed and in-play$")
    public void a_tennis_doubles_set_as_authorized_displayed_and_in_play(Map<String, String> data) throws Throwable {
        eventHelper.createEvent(decoder.getCompetitionId("tennis doubles"), data.get("Incidents"),
                1,1, "", "currenttime", null);
        eventHelper.assertEventIsCreated();
        eventHelper.waitForEventToActiveDisplayed();
        eventHelper.scheduleInplay();
    }

    @When("^the event runs the tennis incidents from \"([^\"]*)\" sheet \"([^\"]*)\"$")
    public void theEventRunsTheIncidentsFromExcelSheet(String fileName, String sheetName) throws Throwable {
        eventHelper.processIncidents(Utils.getSourceAsAbsolutePath("incidents/" + fileName), sheetName);
    }

    @And("^the relevant tennis markets should be settled in \"([^\"]*)\"$")
    public void StatusService(String endPoint) throws Throwable {
        String sheetName;
        if (Constants.CUSTOMER_IN_TEST.equalsIgnoreCase("LADBROKES")) {
            sheetName = "result_ladcoral";
        } else {
            sheetName = "result";
        }
        this.assertStatusMarketsSettled(endPoint, Utils.getSourceAsAbsolutePath("incidents/Tennis/TennisE2E.xlsx"), sheetName);
    }

    @And("^the tennis feed heartbeat alive reply stops$")
    public void theTennisFeedHeartbeatAliveReplyStops() throws Throwable {
        eventHelper.setBetradarAlive(false);
        Utils.waitSeconds(180);
        eventHelper.setBetradarAlive(true);
    }

    /*
    @And("^get status from \"([^\"]*)\" for the tennis event$")
    public void getStatus(String destination) throws Throwable {
        StatusHelper shelper = new StatusHelper();
        shelper.getStatus(this.getEventId(), destination);
    } */
}
