package att.incidents.img.retirement;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public class RetirementScenariosImg implements att.incidents.interfaces.RetirementScenarios {

    private List<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    public List<Incident> getRetirementPrePlayTeamAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();

        List<Incident> incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());

        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:00", "0-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }

    public List<Incident> getRetirementPrePlayTeamBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();

        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());

        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:00", "0-0",
                Side.AWAY, Side.AWAY, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }

    public List<Incident> getRetirementTeamAServerSetIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();

        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG());

        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:00", "0-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public List<Incident> getRetirementTeamBServerSetIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();

        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG());

        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:00", "0-0",
                Side.AWAY, Side.AWAY, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public List<Incident> getRetirementInPlayTeamAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();

        String initialTime = "0:00:00";

        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPeriodIncident("", Side.HOME, 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:10", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:20", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:30", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:55", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }

    public List<Incident> getRetirementInPlayTeamBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();

        String initialTime = "0:00:00";

        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPeriodIncident("", Side.HOME, 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:10", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:20", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:30", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getRetirementIncident("0:55", "40-0",
                Side.HOME, Side.AWAY, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }
}
