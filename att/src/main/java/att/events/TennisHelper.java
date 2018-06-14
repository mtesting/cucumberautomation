package att.events;

import java.io.IOException;
import java.util.List;

import ats.betting.trading.att.ws.scenario.SendIncidentException;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.IncidentsHelper;

public class TennisHelper extends EventHelper {


    public void processIncidents(String incidentsFile, String sheetName) throws SendIncidentException, IOException {
        IncidentsHelper incidentHelper = new IncidentsHelper();
        List<Incident> incidentsList = incidentHelper.loadTennisIncidentsFromFile(incidentsFile, sheetName);
        sendIncidents(incidentsList);
    }

}