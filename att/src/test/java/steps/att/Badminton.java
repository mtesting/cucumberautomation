package steps.att;


import org.apache.log4j.Logger;

import java.util.Map;

import att.events.EventHelperTemplate;
import att.events.EventManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import util.Utils;

public class Badminton {

    private static final Logger log = Logger.getLogger(Badminton.class);

    private final EventHelperTemplate eventHelper = EventManager.getEventManager().getEventHelper();
    private final Decoder decoder = DecoderManager.getManager().getDecoder();


    public Badminton() throws DecoderConfigException {
    }

    @Given("^a badminton event set as authorized, displayed and in-play$")
    public void a_badminton_event_set_as_authorized_displayed_and_in_play(Map<String, String> data) throws Throwable {
        eventHelper.createEvent(decoder.getCompetitionId("badminton"), data.get("Incidents"),
                1, 1, "", data.get("StartTime"), null);
        eventHelper.assertEventIsCreated();
        eventHelper.waitForEventToBeInplay();
        eventHelper.waitForEventToActiveDisplayed();
    }

    @When("^the badminton event runs the incidents from \"([^\"]*)\"$")
    public void theEventRunsTheIncidentsFrom(String fileName) throws Throwable {
        eventHelper.processIncidents(Utils.getSourceAsAbsolutePath(fileName), "Sheet1");
    }

}
