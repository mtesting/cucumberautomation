package att.events;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import ats.betting.trading.att.ws.scenario.ScenarioDefinitionException;
import ats.betting.trading.att.ws.scenario.SendIncidentException;
import ats.betting.trading.att.ws.scenario.dto.Event;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.IncidentsHelper;
import util.Utils;

public class BadmintonHelper extends EventHelper {

    @Override
    public void createEvent(long competitionId, String incidentsFeedProvider, int matchesTotal, int matchesInplay,
            String pricingFeedProvider, String startDateTime, EventDefinition.Markets markets) throws ScenarioDefinitionException {
        log.info("-- STEP -- to create a event");
        setEvent(new Event());
        try {

            trackingId = testScenarioSupport.getTrackingIdforPrepareScenario(competitionId, incidentsFeedProvider,
                    matchesTotal, matchesInplay, pricingFeedProvider, startDateTime, "", markets);
            log.info("Tracking id :::" + trackingId);

            synchronized (this) {
                Utils.waitSeconds(20);
            }

        } catch (ParseException | DatatypeConfigurationException e) {
            log.error(e);
        }
    }

    @Override
    public void processIncidents(String incidentsFile, String sheetName) throws SendIncidentException, IOException {
        IncidentsHelper incidentHelper = new IncidentsHelper();
        List<Incident> incidentsList = incidentHelper.loadBadmintonIncidentsFromFile(incidentsFile, sheetName);
        sendIncidents(incidentsList);
    }

    @Override
    public void processLSportsIncidents(String incidentsFile) {

    }
}
