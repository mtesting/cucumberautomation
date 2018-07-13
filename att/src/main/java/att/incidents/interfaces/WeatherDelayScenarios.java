package att.incidents.interfaces;

import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.incidents.TennisIncidentsHelper;

public interface WeatherDelayScenarios {

    List<Incident> incidents = null;
    TennisIncidentsHelper tennisIncidentsHelper = null;

    List<Incident> getRainStopPlayIncidents();

    List<Incident> getRainStopPlayAndUndoIncidents();

    List<Incident> getRainStopAndStartPlayIncidents();

    default List<Incident> getWeatherStopPlayIncidents() {
        throw new RuntimeException("Not supported for IMG");
    }

    List<Incident> getHeatStopPlayIncidents();

    List<Incident> getChallengeStopPlayIncidents();

    List<Incident> getOnCourtCoachStopAndStartPlayIncidents();

    List<Incident> getToiletBreakStopAndStartPlayIncidents();

    List<Incident> getMatchInitialIncidents(TennisIncidentsHelper tennisIncidentsHelper);

}
