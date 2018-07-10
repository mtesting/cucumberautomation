package att.incidents.inter;

import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.incidents.TennisIncidentsHelper;

public interface MedicalTimeoutScenarios {

    List<Incident> incidents = null;
    TennisIncidentsHelper tennisIncidentsHelper = null;

    List<Incident> getMedicalTimeoutAIncidents();

    List<Incident> getMedicalTimeoutBIncidents();

    List<Incident> getMedicalTimeoutAndUndoAIncidents();

    Incident getUndoIncident();

    List<Incident> getMedicalTimeoutOffAIncidents();

    List<Incident> getMedicalTimeoutOffBIncidents();
}
