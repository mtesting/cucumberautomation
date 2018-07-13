package att.incidents.implementation.img.penalties;

import java.util.ArrayList;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public class PointPenaltyScenarios {

    private ArrayList<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    private ArrayList<Incident> getMatchInitialIncidents(TennisIncidentsHelper tennisIncidentsHelper) {
        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPeriodIncident("", Side.HOME, 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:10", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:20", "15-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public ArrayList<Incident> getCodePointPenaltyAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getCodePointPenaltyIncident("0:25", "30-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public ArrayList<Incident> getCodePointPenaltyBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getCodePointPenaltyIncident("0:25", "15-30",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public ArrayList<Incident> getTimePointPenaltyAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getTimePointPenaltyIncident("0:25", "30-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public ArrayList<Incident> getTimePointPenaltyBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getTimePointPenaltyIncident("0:25", "15-30",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }
}
