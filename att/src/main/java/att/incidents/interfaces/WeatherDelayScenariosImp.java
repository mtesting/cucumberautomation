package att.incidents.interfaces;

import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public abstract class WeatherDelayScenariosImp implements WeatherDelayScenarios{

    protected List<Incident> incidents;
    protected TennisIncidentsHelper tennisIncidentsHelper;

    public List<Incident> getRainStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public List<Incident> getRainStopPlayAndUndoIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getUndoIncident("0:35", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public List<Incident> getRainStopAndStartPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getRainReStartPlayIncident("0:40", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:50", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    public List<Incident> getHeatStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getHeatStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

}
