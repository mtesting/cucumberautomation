package steps.trader;

import javax.security.auth.login.LoginException;

import apiLevelInteraction.TraderClientHelper;
import att.events.EventHelperTemplate;
import att.events.EventManager;
import cucumber.api.java.en.When;
import decoders.DecoderConfigException;
import util.Utils;

public class TraderClient {

    private final TraderClientHelper TCApi = new TraderClientHelper();
    private EventHelperTemplate eventHelper = EventManager.getEventManager().getEventHelper();

    public TraderClient() throws DecoderConfigException, LoginException {
    }

    @When("user sets the tier level in trader client")
    public void setTCMatchParams() {
        TCApi.setTierLevelViaAPI(eventHelper.getEvent().getEventRef(), "6");
        Utils.waitSeconds(10);
 //      TCApi.saveMatchParams(eventManager.getEventHelper().getEvent().getEventRef());
    }

}