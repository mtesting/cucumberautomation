package att.events;

import org.junit.Assert;

import java.io.FileNotFoundException;
import java.util.List;

import ats.betting.trading.att.ws.scenario.SendIncidentException;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.LsportsIncident;
import att.AbelsonHelper;
import att.IncidentsHelper;
import util.Utils;

public class FootballHelper extends EventHelper {


    public void processIncidents(String incidentsFile, String sheetName) throws SendIncidentException, FileNotFoundException {
        IncidentsHelper incidentHelper = new IncidentsHelper();
        List<Incident> incidentsList = incidentHelper.loadSoccerIncidentsFromFile(incidentsFile);
        sendIncidents(incidentsList);
    }

    /**
     * Sends score updates for LSports feed
     * @param incidentsFile feed input
     */
    public void processLSportsIncidents(String incidentsFile) {
        log.info("-- STEP -- send score update");
        IncidentsHelper incidentHelper = new IncidentsHelper();
        List<LsportsIncident> incidentList = incidentHelper.loadLsportsIncidentsFromFile(incidentsFile);
        for (LsportsIncident incident : incidentList) {
            Assert.assertTrue("Error sending incident",
                    testScenarioSupport.sendLsportsIncidents(event.getEventRef(), incident));
            Utils.waitSeconds(20);
        }
    }

    @Override
    public void addAbelsonFeed() {
        log.info("-- STEP -- add Abelson feed");
        AbelsonHelper abelsonFeed = new AbelsonHelper();
        testScenarioSupport.prepareAbelsonMatches(abelsonFeed.createAbelsonEvent(event));
    }

}
