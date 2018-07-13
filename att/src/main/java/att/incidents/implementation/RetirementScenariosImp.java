package att.incidents.implementation;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;
import att.incidents.interfaces.RetirementScenarios;

public class RetirementScenariosImp implements RetirementScenarios {

    private List<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    @Override
    public List<Incident> getRetirementPrePlayTeamAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        List<Incident> incidents = new ArrayList<>();

        incidents.add(tennisIncidentsHelper.getPregameIncident());
        //incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG()); //TODO needed?
        //incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME)); //TODO needed?

        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:00", "0-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getRetirementTeamAServerSetIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = new ArrayList<>();

        incidents.add(tennisIncidentsHelper.getPregameIncident());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME)); //TODO needed?
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG()); //TODO needed?
        incidents.add(tennisIncidentsHelper.getServeFirstIncident(Side.HOME));

        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:00", "0-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getRetirementInPlayTeamAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = new ArrayList<>();
        String initialTime = "0:00:00";

        incidents.add(tennisIncidentsHelper.getPregameIncident());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME)); //TODO needed?
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG()); //TODO needed?
        incidents.add(tennisIncidentsHelper.getServeFirstIncident(Side.HOME));

        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME,  1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:10", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:20", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:30", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:55", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

}
