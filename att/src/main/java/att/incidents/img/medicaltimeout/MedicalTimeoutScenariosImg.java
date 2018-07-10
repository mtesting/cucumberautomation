package att.incidents.img.medicaltimeout;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public class MedicalTimeoutScenariosImg implements att.incidents.inter.MedicalTimeoutScenarios {

    private List<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    public List<Incident> getMedicalTimeoutAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getMedicalTimeOutOnIncidentIMG("0:30", "30-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public List<Incident> getMedicalTimeoutBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getMedicalTimeOutOnIncidentIMG("0:30", "30-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public List<Incident> getMedicalTimeoutAndUndoAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getMedicalTimeOutOnIncidentIMG("0:30", "30-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getUndoIncidentIMG("0:35", "15-15", Side.HOME, Side.HOME,
                "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public Incident getUndoIncident() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        return tennisIncidentsHelper.getUndoIncidentIMG("0:35", "15-15", Side.HOME, Side.HOME,
                "0,0,0", "0,0,0", 1, 1);
    }

    public List<Incident> getMedicalTimeoutOffAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getMedicalTimeOutOnIncidentIMG("0:30", "30-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getMedicalTimeOutOffIncidentIMG("1:00", "30-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));


        incidents.add(tennisIncidentsHelper.getPointIncident("1:05", "40-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public List<Incident> getMedicalTimeoutOffBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getMedicalTimeOutOnIncidentIMG("0:30", "30-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getMedicalTimeOutOffIncidentIMG("1:00", "30-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0", 1, 1));


        incidents.add(tennisIncidentsHelper.getPointIncident("1:05", "40-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    private List<Incident> getMatchIncidents(TennisIncidentsHelper tennisIncidentsHelper) {
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

        incidents.add(tennisIncidentsHelper.getPointIncident("0:25", "30-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

}
