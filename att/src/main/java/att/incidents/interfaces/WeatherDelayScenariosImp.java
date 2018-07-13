package att.incidents.interfaces;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public class WeatherDelayScenariosImp implements WeatherDelayScenarios{

    protected List<Incident> incidents;
    protected TennisIncidentsHelper tennisIncidentsHelper;

    @Override
    public List<Incident> getRainStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getRainStopPlayAndUndoIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getUndoIncident("0:35", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getRainStopAndStartPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getRainStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getRainReStartPlayIncident("0:40", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:50", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getHeatStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getHeatStopPlayIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getChallengeStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getChallengedIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:50", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getOnCourtCoachStopAndStartPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getOnCourtCoachIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:50", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getToiletBreakStopAndStartPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getToiletBreakIncident("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0", 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:50", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0", 1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getMatchInitialIncidents(TennisIncidentsHelper tennisIncidentsHelper) {
        String initialTime = "0:00:00";
        incidents = new ArrayList<>();

        incidents.add(tennisIncidentsHelper.getPregameIncident());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME)); //TODO remove?
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG()); //TODO would affect BR?
        incidents.add(tennisIncidentsHelper.getServeFirstIncident(Side.HOME));

        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME, 1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("0:10", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(tennisIncidentsHelper.getPointIncident("0:20", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    @Override
    public List<Incident> getWeatherStopPlayIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getWeatherStopPlayIncidentBetRadar("0:30", "30-0",
                Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        return incidents;
    }

}
