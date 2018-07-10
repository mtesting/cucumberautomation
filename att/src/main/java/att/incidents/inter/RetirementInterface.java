package att.incidents.inter;

import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import att.incidents.TennisIncidentsHelper;

public interface RetirementInterface {

    List<Incident> incidents = null;
    TennisIncidentsHelper tennisIncidentsHelper = null;

    List<Incident> getRetirementPrePlayTeamAIncidents();

    List<Incident> getRetirementPrePlayTeamBIncidents();

    List<Incident> getRetirementTeamAServerSetIncidents();

    List<Incident> getRetirementTeamBServerSetIncidents();

    List<Incident> getRetirementInPlayTeamAIncidents();

    List<Incident> getRetirementInPlayTeamBIncidents();
    
}
