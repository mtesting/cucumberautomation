package att.incidents.img.weatherdelay;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;
import att.incidents.interfaces.WeatherDelayScenariosImp;

public class WeatherDelayScenariosImg extends WeatherDelayScenariosImp {

    public List<Incident> getMatchIncidents(TennisIncidentsHelper tennisIncidentsHelper) {
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

        return incidents;
    }

}
