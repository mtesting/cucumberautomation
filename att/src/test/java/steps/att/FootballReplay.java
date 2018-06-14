package steps.att;

import java.util.Map;

import cucumber.api.java.en.Given;
import decoders.DecoderConfigException;
import decoders.DecoderManager;

public class FootballReplay extends EventSteps {


    public FootballReplay() throws DecoderConfigException {
    }

    /**
     * To Replay N events with randon datasets
     */
    @Given("^there are (\\d+) football event set as authorized, displayed, in-play$")
    public void N_football_event_set_as_authorized_displayed_in_play(int numberOfEvents, Map<String, String> data) throws Throwable {
        String eventStartDateTime = data.get("EventDate");
        eventHelper.createEventSchedule(DecoderManager.getManager().getDecoder().getCompetitionId("football"),
                data.get("Incidents"), numberOfEvents, numberOfEvents, data.get("Pricing"), data.get("DataSet"), null, eventStartDateTime);
        eventHelper.assertEventIsCreated();
        eventHelper.waitForEventToActiveDisplayed();
    }

}