package att.incidents.img.weatherdelay;

import java.util.ArrayList;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;
import att.incidents.inter.WeatherDelayInterface;

public class WeatherDelayScenarios implements WeatherDelayInterface {

    private ArrayList<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    public ArrayList<Incident> getRainStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncidentIMG("0:30", "30-15",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public ArrayList<Incident> getRainStopPlayAndUndoIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        // Add a RainDelay
        incidents.add(tennisIncidentsHelper.getRainStopPlayIncidentIMG("0:30", "30-15",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getUndoIncidentIMG("0:35", "15-15", Side.HOME, Side.HOME,
                "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public ArrayList<Incident> getRainStopAndStartPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncidentIMG("0:30", "30-15",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getRainReStartPlayIncidentIMG("1:30", "30-15",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));


        incidents.add(tennisIncidentsHelper.getPointIncident("1:35", "40-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }

    private ArrayList<Incident> getMatchIncidents(TennisIncidentsHelper tennisIncidentsHelper) {
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
