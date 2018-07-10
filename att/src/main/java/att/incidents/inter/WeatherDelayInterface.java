package att.incidents.inter;

import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.incidents.TennisIncidentsHelper;

public interface WeatherDelayInterface {

    List<Incident> incidents = null;
    TennisIncidentsHelper tennisIncidentsHelper = null;

    List<Incident> getRainStopPlayIncidents();

    List<Incident> getRainStopPlayAndUndoIncidents();

    List<Incident> getRainStopAndStartPlayIncidents();

}
