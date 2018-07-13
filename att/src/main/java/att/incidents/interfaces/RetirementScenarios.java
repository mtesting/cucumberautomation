package att.incidents.interfaces;

import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.incidents.TennisIncidentsHelper;

public interface RetirementScenarios {

    List<Incident> incidents = null;
    TennisIncidentsHelper tennisIncidentsHelper = null;

    List<Incident> getRetirementPrePlayTeamAIncidents();

    List<Incident> getRetirementTeamAServerSetIncidents();

    List<Incident> getRetirementInPlayTeamAIncidents();
    
}
