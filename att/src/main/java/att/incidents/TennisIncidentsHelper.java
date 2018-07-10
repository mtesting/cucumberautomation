package att.incidents;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.AbandonName;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.IncidentType;
import ats.betting.trading.att.ws.scenario.dto.Side;
import ats.betting.trading.att.ws.scenario.dto.Tennis;
import ats.betting.trading.att.ws.scenario.dto.TennisMatchState;
import util.DateUtil;

public class TennisIncidentsHelper {

    public static final String FEED_BETRADAR = "BETRADAR";
    public static final String FEED_IMG = "IMG";

    private static final Logger log = Logger.getLogger(TennisIncidentsHelper.class);

    public List<Incident> getTestBetRadarIncidents() {
        String initialTime = "0:00:00";

        List<Incident> incidents = new ArrayList<>();
        incidents.add(getPregameIncidentBetradar());
        incidents.add(getServeFirstIncidentBetRadar(Side.HOME));
        incidents.add(getPeriodIncident(initialTime, Side.HOME,
                1, 1));

        incidents.add(getPointIncident("0:10", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(getPointIncident("0:20", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(getPointIncident("0:30", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(getPointIncident("0:50", "0-0",
                Side.HOME, Side.HOME, "1,0,0", "0,0,0",
                1, 1));

        incidents.add(getPeriodIncident(initialTime, Side.AWAY, 1, 2));
        return incidents;
    }

    public List<Incident> getTestIMGIncidents() {
        String initialTime = "0:00:10";

        List<Incident> incidents = new ArrayList<>();
        incidents.add(getPregameUmpireIncidentIMG());
        incidents.add(getPregameTossIncidentIMG(Side.HOME));
        incidents.add(getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(getMatchStateChangedIncidentIMG());
        incidents.add(getPeriodIncident(initialTime, Side.HOME,
                1, 1));

        incidents.add(getPointIncident("0:15", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(getPointIncident("0:20", "30-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(getPointIncident("0:30", "40-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));
        incidents.add(getPointIncident("0:50", "0-0",
                Side.HOME, Side.HOME, "1,0,0", "0,0,0",
                1, 1));

        incidents.add(getPeriodIncident(initialTime, Side.AWAY, 1, 2));

        return incidents;
    }

    public Incident getPregameIncidentBetradar() {
        String initialTime = "0:00:00";
        Tennis incident = new Tennis();
        incident.setMatchTime(initialTime);
        log.info(initialTime);
        incident.setType(IncidentType.PREGAME);
        incident.setSide(Side.HOME);
        incident.setServer(Side.HOME);
        incident.setWinningPoint(false);
        incident.setIncidentDelay(5);
        return incident;
    }

    public Incident getPregameUmpireIncidentIMG() {
        String initialTime = "0:00:10";
        Tennis incident = new Tennis();
        incident.setMatchTime(initialTime);
        log.info(initialTime);
        incident.setType(IncidentType.PREGAME);
        incident.setMatchState(TennisMatchState.UMPIRE_ON_COURT);

        incident.setServer(Side.HOME);
        incident.setWinningPoint(false);
        incident.setIncidentDelay(5);
        return incident;
    }

    public Incident getPregameTossIncidentIMG(Side firstServerSide) {
        String initialTime = "0:00:10";
        Tennis incident = new Tennis();
        incident.setMatchTime(initialTime);
        log.info(initialTime);
        incident.setType(IncidentType.PREGAME);
        incident.setMatchState(TennisMatchState.WARM_UP);
        incident.setTossChooser("Server");
        incident.setTossWinner(firstServerSide);

        incident.setServer(Side.HOME);
        incident.setWinningPoint(false);
        incident.setIncidentDelay(5);
        return incident;
    }

    public Incident getPregameInProgressIncidentIMG(Side firstServerSide) {
        String initialTime = "0:00:10";
        Tennis incident = new Tennis();
        incident.setMatchTime(initialTime);
        log.info(initialTime);
        incident.setType(IncidentType.PREGAME);
        incident.setMatchState(TennisMatchState.IN_PROGRESS);
        incident.setTossChooser("Server");
        incident.setTossWinner(firstServerSide);

        incident.setServer(Side.HOME);
        incident.setWinningPoint(false);
        incident.setIncidentDelay(5);
        return incident;
    }

    public Incident getMatchStateChangedIncidentIMG() {
        String initialTime = "0:00:10";
        Tennis incident = new Tennis();
        incident.setMatchTime(initialTime);
        log.info(initialTime);
        incident.setType(IncidentType.TENNIS_MATCH_STATE_CHANGED);
        incident.setServer(Side.HOME);
        incident.setWinningPoint(false);
        incident.setIncidentDelay(5);
        return incident;
    }

    public Incident getMedicalTimeOutOnIncidentIMG(String matchTime, String score, Side medicalSide, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.MEDICAL_TIMEOUT_ON);
        incident.setSide(medicalSide);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(60);
        incident.setWinningPoint(false);
        incident.setPlayerNumber("1");
        return incident;
    }

    public Incident getMedicalTimeOutOffIncidentIMG(String matchTime, String score, Side medicalSide, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.MEDICAL_TIMEOUT_OFF);
        incident.setSide(medicalSide);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(60);
        incident.setWinningPoint(false);
        incident.setPlayerNumber("1");
        return incident;
    }

    public Incident getRainStopPlayIncidentIMG(String matchTime, String score, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.RAIN_STOPPED_PLAY);
        incident.setSide(Side.HOME);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(20);
        incident.setWinningPoint(false);
        return incident;
    }

    public Incident getRainReStartPlayIncidentIMG(String matchTime, String score, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        //incident.setType(IncidentType.BETSTART_INPLAY);
        incident.setSide(Side.HOME);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(20);
        incident.setWinningPoint(false);
        return incident;
    }

    public Incident getRainStopPlayIncidentBetRadar(String matchTime, String score, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.RAIN_STOPPED_PLAY);
        incident.setSide(Side.HOME);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(20);
        incident.setWinningPoint(false);
        return incident;
    }

    public Incident getWeatherStopPlayIncidentBetRadar(String matchTime, String score, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.TENNIS_WEATHER_STOPPED_PLAY);
        incident.setSide(Side.HOME);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(20);
        incident.setWinningPoint(false);
        return incident;
    }

    public Incident getHeatStopPlayIncident(String matchTime, String score, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.HEAT_DELAY);
        incident.setSide(Side.HOME);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(20);
        incident.setWinningPoint(false);
        return incident;
    }

    public Incident getRainReStartPlayIncidentBetRadar(String matchTime, String score, Side serverSide, String historySet1, String historySet2, int gameNumber, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.BETSTART_INPLAY);
        incident.setSide(Side.HOME);
        incident.setMatchTime(matchTime);
        incident.setScore(score);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(20);
        incident.setWinningPoint(false);
        return incident;
    }

    public Incident getUndoIncidentBetRadar(String matchTime, String newScore, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.UNDO);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore(newScore);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getUndoIncidentIMG(String matchTime, String newScore, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.UNDO);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore(newScore);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getServeFirstIncidentBetRadar(Side firstServerSide) {
        int firstGame = 1;
        int firstSet = 1;
        String initialTime = "0:00:00";
        String initialScore = "0-0";
        String timeServersFirst = DateUtil.getCurrentTimeInFormat("HH:mm:ss", Calendar.SECOND, 1);
        String historySet1 = "0,0,0";
        String historySet2 = "0,0,0";

        Tennis incident = new Tennis();
        incident.setType(IncidentType.TENNIS_SERVES_FIRST);
        incident.setSide(firstServerSide);
        incident.setMatchTime(initialTime);
        incident.setScore(initialScore);
        incident.setSet(firstSet);
        incident.setGame(firstGame);
        incident.setWinningPoint(false);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(firstServerSide);
        incident.setIncidentDelay(5);

        return incident;
    }

    public Incident getPeriodIncident(String matchTime, Side currentServer, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.PERIOD);
        incident.setSide(currentServer);
        incident.setMatchTime(matchTime);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setWinningPoint(false);
        incident.setIncidentDelay(5);
        incident.setServer(currentServer);

        return incident;
    }

    public Incident getPointIncident(String matchTime, String newScore, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.POINT);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore(newScore);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getWinningPointIncident(String matchTime, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.POINT);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore("0-0");
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(true);

        return incident;
    }

    public Incident getRetirementIncident(String matchTime, String newScore, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setName(AbandonName.WALKOVER);
        incident.setType(IncidentType.ABANDON_MATCH);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore(newScore);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getTimePointPenaltyIncident(String matchTime, String newScore, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.PENALTY_POINT_WON_TIME);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore(newScore);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getCodePointPenaltyIncident(String matchTime, String newScore, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.PENALTY_POINT_WON_CODE);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore(newScore);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getGameWonPenaltyIncident(String matchTime, String newScore, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.PENALTY_GAME_WON);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore(newScore);
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);
        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getMatchWonPenaltyIncident(String matchTime, Side sidePointWinner, Side serverSide, String historySet1, String historySet2, int setNumber, int gameNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.PENALTY_MATCH_WON);
        incident.setSide(sidePointWinner);
        incident.setMatchTime(matchTime);
        incident.setScore("0-0");
        incident.setSet(setNumber);
        incident.setGame(gameNumber);
        incident.setHistoryA(historySet1);
        incident.setHistoryB(historySet2);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);

        incident.setWinningPoint(false);

        return incident;
    }

    public Incident getFinishedIncident(String matchTime, Side sideMatchWinner, Side serverSide, int setNumber) {
        Tennis incident = new Tennis();
        incident.setType(IncidentType.FINISHED);
        incident.setSide(sideMatchWinner);
        incident.setMatchTime(matchTime);
        incident.setScore("0-0");
        incident.setSet(setNumber);
        incident.setGame(1);
        incident.setServer(serverSide);
        incident.setIncidentDelay(5);

        // In the Spreadsheet the winning point was not mentioned for this scenario
        incident.setWinningPoint(false);

        return incident;
    }

}
