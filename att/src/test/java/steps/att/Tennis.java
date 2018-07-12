package steps.att;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.incidents.betradar.retirement.RetirementScenariosBetradar;
import att.incidents.betradar.weatherdelay.WeatherDelayScenariosBetradar;
import att.incidents.img.weatherdelay.WeatherDelayScenariosImg;
import att.incidents.interfaces.RetirementScenarios;
import att.incidents.interfaces.WeatherDelayScenarios;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import decoders.DecoderConfigException;
import other.Constants;
import util.Utils;

public class Tennis extends EventSteps {

    private static final Logger log = Logger.getLogger(Tennis.class);
    private String incidentsProvider;

    public Tennis() throws DecoderConfigException {
    }

    public String getEventId(){
        return eventHelper.getEvent().getEventRef();
    }

    @Given("^a tennis event set as authorized, displayed and in-play$")
    public void a_tennis_event_set_as_authorized_displayed_and_in_play(Map<String, String> data) throws Throwable {
        incidentsProvider = data.get("Incidents");
        eventHelper.createEvent(decoder.getCompetitionId("tennis"), incidentsProvider,
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

    @And("^the event runs the tennis for Rain Delay$")
    public void theEventRunsTheTennisForRainDelay() throws Throwable {
        WeatherDelayScenarios weatherDelayScenarios = null;
        if ("IMG".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosImg();
        } else if ("BETRADAR".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosBetradar();
        }
        eventHelper.sendIncidents(weatherDelayScenarios.getRainStopAndStartPlayIncidents());
    }

    @And("^the event runs the tennis for Weather Delay$")
    public void theEventRunsTheTennisForWeatherDelay() throws Throwable {
        WeatherDelayScenarios weatherDelayScenarios = new WeatherDelayScenariosBetradar();
        eventHelper.sendIncidents(weatherDelayScenarios.getWeatherStopPlayIncidents());
    }

    @And("^the event runs the tennis for Heat Delay$")
    public void theEventRunsTheTennisForHeatDelay() throws Throwable {;
        WeatherDelayScenarios weatherDelayScenarios = null;
        if ("IMG".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosImg();
        } else if ("BETRADAR".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosBetradar();
        }
        eventHelper.sendIncidents(weatherDelayScenarios.getHeatStopPlayIncidents());
    }

    @And("^the event runs the tennis for Abandon on \"([^\"]*)\"$")
    public void theEventRunsTheTennisForAbandonOn(String abandonType) throws Throwable {
        RetirementScenarios retirementScenarios = null;
        if ("IMG".equalsIgnoreCase(incidentsProvider)){
            retirementScenarios = new RetirementScenariosBetradar();
        } else if ("BETRADAR".equalsIgnoreCase(incidentsProvider)){
            retirementScenarios = new RetirementScenariosBetradar();
        }
        List<Incident> incidents = null;
        switch (abandonType){
            case "pre-match":
                incidents = retirementScenarios.getRetirementPrePlayTeamAIncidents();
                break;
            case "serve":
                incidents = retirementScenarios.getRetirementTeamAServerSetIncidents();
                break;
            case "1st set":
                incidents = retirementScenarios.getRetirementInPlayTeamAIncidents();
                break;
        }
        eventHelper.sendIncidents(incidents);
    }

    @And("^the event runs the tennis for Challenged Delay$")
    public void theEventRunsTheTennisForChallengedDelay() throws Throwable {
        WeatherDelayScenarios weatherDelayScenarios = null;
        if ("IMG".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosImg();
        } else if ("BETRADAR".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosBetradar();
        }
        eventHelper.sendIncidents(weatherDelayScenarios.getChallengeStopPlayIncidents());
    }

    @And("^the event runs the tennis for Coach on Court Delay$")
    public void theEventRunsTheTennisForCoachOnCourtDelay() throws Throwable {
        WeatherDelayScenarios weatherDelayScenarios = null;
        if ("IMG".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosImg();
        } else if ("BETRADAR".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosBetradar();
        }
        eventHelper.sendIncidents(weatherDelayScenarios.getOnCourtCoachStopAndStartPlayIncidents());
    }

    @And("^the event runs the tennis for Toilet Break Delay$")
    public void theEventRunsTheTennisForToiletBreakDelay() throws Throwable {
        WeatherDelayScenarios weatherDelayScenarios = null;
        if ("IMG".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosImg();
        } else if ("BETRADAR".equalsIgnoreCase(incidentsProvider)){
            weatherDelayScenarios = new WeatherDelayScenariosBetradar();
        }
        eventHelper.sendIncidents(weatherDelayScenarios.getToiletBreakStopAndStartPlayIncidents());
    }
}