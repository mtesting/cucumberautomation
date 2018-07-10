package att.incidents.betradar.weatherdelay;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;
import att.incidents.inter.WeatherDelayScenarios;

public class WeatherDelayScenariosBetradar implements WeatherDelayScenarios {

    private List<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    public List<Incident> getRainStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncidentBetRadar("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }

    public List<Incident> getRainStopPlayAndUndoIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncidentBetRadar("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getUndoIncidentBetRadar("0:35", "15-15", Side.HOME, Side.HOME,
                "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public List<Incident> getRainStopAndStartPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();

        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncidentBetRadar("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getRainReStartPlayIncidentBetRadar("0:40", "30-0",
                Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:50", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }

    private List<Incident> getMatchIncidents(TennisIncidentsHelper tennisIncidentsHelper) {
        String initialTime = "0:00:00";
        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameIncidentBetradar());
        incidents.add(tennisIncidentsHelper.getServeFirstIncidentBetRadar(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME,
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:10", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:20", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

}