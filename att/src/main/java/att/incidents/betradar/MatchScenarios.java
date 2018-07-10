package att.incidents.betradar;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public class MatchScenarios {

    private List<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    private List<Incident> getOnePointFromMatchA(TennisIncidentsHelper tennisIncidentsHelper) {
        String initialTime = "00:00";
        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameIncidentBetradar());
        incidents.add(tennisIncidentsHelper.getServeFirstIncidentBetRadar(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME,
                1, 1));

        int seconds = 20;
        int minutes = 0;
        int gamesSet1A = 0;
        int gamesSet2A = 0;
        int pointsA = 0;
        int setNumber = 1;
        int gameNumber = 1;
        Side servingSide = Side.HOME;
        boolean endGame = false;

        while (!(gamesSet2A == 5 && pointsA == 40)) {
            seconds += 5;
            if (seconds >= 59) {
                minutes++;
                seconds = 0;
            }

            switch (pointsA) {
                case 0:
                    pointsA = 15;
                    break;
                case 15:
                    pointsA = 30;
                    break;
                case 30:
                    pointsA = 40;
                    break;
                case 40:
                    endGame = true;
                    break;
            }

            if (endGame) {
                endGame = false;
                pointsA = 0;
                if (gamesSet1A < 6) {
                    gamesSet1A++;
                } else {
                    gamesSet2A++;
                }

                incidents.add(tennisIncidentsHelper.getPointIncident(minutes + ":" + seconds, pointsA + "-0",
                        Side.HOME, servingSide, gamesSet1A + "," + gamesSet2A + ",0", "0,0,0",
                        setNumber, gameNumber));


                if (servingSide == Side.HOME) {
                    servingSide = Side.AWAY;
                } else {
                    servingSide = Side.HOME;
                }

                gameNumber++;
                if (gameNumber > 6) {
                    gameNumber = 1;
                    setNumber++;
                }

                incidents.add(tennisIncidentsHelper.getPeriodIncident("", servingSide, setNumber, gameNumber));

            } else {
                incidents.add(tennisIncidentsHelper.getPointIncident(minutes + ":" + seconds, pointsA + "-0",
                        Side.HOME, servingSide, gamesSet1A + "," + gamesSet2A + ",0", "0,0,0",
                        setNumber, gameNumber));
            }
        }

        return incidents;
    }

    public List<Incident> getMatchWinAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getOnePointFromMatchA(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getWinningPointIncident("4:40",
                Side.HOME, Side.AWAY, "6,6,0", "0,0,0",
                2, 6));

        incidents.add(tennisIncidentsHelper.getFinishedIncident("4:45", Side.HOME, Side.AWAY, 2));
        return incidents;
    }

    public List<Incident> getDeuceAndGameIncidentsA() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = new ArrayList<>();

        String initialTime = "00:00";
        incidents.add(tennisIncidentsHelper.getPregameIncidentBetradar());
        incidents.add(tennisIncidentsHelper.getServeFirstIncidentBetRadar(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME,
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:05", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:15", "15-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:25", "30-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:35", "30-30",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:45", "40-30",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:55", "40-40",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("01:05", "0-0",
                Side.HOME, Side.HOME, "1,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public List<Incident> getDeuceAndGameIncidentsB() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = new ArrayList<>();

        String initialTime = "00:00";
        incidents.add(tennisIncidentsHelper.getPregameIncidentBetradar());
        incidents.add(tennisIncidentsHelper.getServeFirstIncidentBetRadar(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME,
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:05", "15-0",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:15", "15-15",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:25", "30-15",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:35", "30-30",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:45", "40-30",
                Side.HOME, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("00:55", "40-40",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getPointIncident("01:05", "0-0",
                Side.AWAY, Side.HOME, "0,0,0", "1,0,0",
                1, 1));

        return incidents;
    }

    public List<Incident> getSet2_0Incidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = new ArrayList<>();

        String initialTime = "00:00";
        incidents.add(tennisIncidentsHelper.getPregameIncidentBetradar());
        incidents.add(tennisIncidentsHelper.getServeFirstIncidentBetRadar(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME, 1, 1));

        // Get All Incidents for 1st Set
        incidents.addAll(getSetIncidents(tennisIncidentsHelper, 1, 0, 0, Side.HOME, Side.HOME));
        incidents.addAll(getSetIncidents(tennisIncidentsHelper, 1, 0, 0, Side.HOME, Side.AWAY));

        return incidents;
    }

    public List<Incident> getSet3CorrectScoreBugIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = new ArrayList<>();

        String initialTime = "00:00";
        incidents.add(tennisIncidentsHelper.getPregameIncidentBetradar());
        incidents.add(tennisIncidentsHelper.getServeFirstIncidentBetRadar(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPeriodIncident(initialTime, Side.HOME, 1, 1));

        // Get All Incidents for 1st Set
        incidents.addAll(getSetIncidents(tennisIncidentsHelper, 1, 0, 0, Side.HOME, Side.HOME));

        // Get All Incidents for 2nd Set (currently hardcoded, need to automate this) - Gets us to TieBreak 6-6 need to add Tiebreak Scores
        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.AWAY, Side.HOME, "6,0,0", "0,0,0", 2, 1));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.AWAY, 2, 2));
        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.HOME, Side.AWAY, "6,1,0", "0,0,0", 2, 2));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.HOME, 2, 3));

        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.AWAY, Side.HOME, "6,1,0", "0,1,0", 2, 3));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.AWAY, 2, 4));
        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.HOME, Side.AWAY, "6,2,0", "0,1,0", 2, 4));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.HOME, 2, 5));

        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.AWAY, Side.HOME, "6,2,0", "0,2,0", 2, 5));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.AWAY, 2, 6));
        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.HOME, Side.AWAY, "6,3,0", "0,2,0", 2, 6));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.HOME, 2, 7));

        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.AWAY, Side.HOME, "6,3,0", "0,3,0", 2, 7));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.AWAY, 2, 8));
        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.HOME, Side.AWAY, "6,4,0", "0,3,0", 2, 8));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.HOME, 2, 9));

        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.HOME, Side.HOME, "6,5,0", "0,4,0", 2, 9));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.AWAY, 2, 10));
        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.AWAY, Side.AWAY, "6,5,0", "0,4,0", 2, 10));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.HOME, 2, 11));

        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.HOME, Side.HOME, "6,6,0", "0,5,0", 2, 11));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.AWAY, 2, 12));
        incidents.addAll(getGameIncidents(tennisIncidentsHelper, Side.AWAY, Side.AWAY, "6,6,0", "0,5,0", 2, 12));
        incidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", Side.HOME, 2, 13));

        List<String> matchTimes = getMatchTimes(1, 0);

        for (int i = 0; i < incidents.size(); i++) {
            incidents.get(i).setMatchTime(matchTimes.get(i));
        }

        return incidents;
    }

    public List<Incident> getSetIncidents(TennisIncidentsHelper tennisIncidentsHelper, int setNumber, int historySet1A,
            int historySet1B, Side winner, Side serverSide) {
        int gameNumber = 1;
        int historySet2A = 0;
        int historySet2B = 0;

        ArrayList<Incident> setIncidents = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            setIncidents.addAll(getGameIncidents(tennisIncidentsHelper, serverSide, winner,
                    historySet1A + "," + historySet2A + ",0", historySet1B + "," + historySet2B + ",0", setNumber, gameNumber));

            gameNumber = gameNumber + 1;

            if (winner == Side.HOME) {
                switch (setNumber) {
                    case 1:
                        historySet1A = historySet1A + 1;
                        break;
                    case 2:
                        historySet2A = historySet2A + 1;
                        break;
                }
            } else {
                switch (setNumber) {
                    case 1:
                        historySet1B = historySet1B + 1;
                        break;
                    case 2:
                        historySet2B = historySet2B + 1;
                        break;
                }
            }

            if (gameNumber == 7) {
                setNumber = setNumber + 1;
                gameNumber = 1;
            }

            if (serverSide == Side.HOME) {
                serverSide = Side.AWAY;
            } else {
                serverSide = Side.HOME;
            }

            setIncidents.add(tennisIncidentsHelper.getPeriodIncident("00:00", serverSide,
                    setNumber, gameNumber));
        }

        return setIncidents;
    }

    public List<Incident> getGameIncidents(TennisIncidentsHelper tennisIncidentsHelper, Side server, Side winner, String historySetA,
            String historySetB, int setNumber, int gameNumber) {
        ArrayList<Incident> gameIncidents = new ArrayList<>();
        gameIncidents.add(tennisIncidentsHelper.getPointIncident("00:00", winner == Side.HOME ? "15-0" : "0-15", winner, server,
                historySetA, historySetB, setNumber, gameNumber));
        gameIncidents.add(tennisIncidentsHelper.getPointIncident("00:00", winner == Side.HOME ? "30-0" : "0-30", winner, server,
                historySetA, historySetB, setNumber, gameNumber));
        gameIncidents.add(tennisIncidentsHelper.getPointIncident("00:00", winner == Side.HOME ? "40-0" : "0-40", winner, server,
                historySetA, historySetB, setNumber, gameNumber));
        gameIncidents.add(tennisIncidentsHelper.getPointIncident("00:00", winner == Side.HOME ? "0-0" : "0-0", winner, server,
                winner == Side.HOME ? incrementHistory(setNumber, historySetA) : historySetA,
                winner == Side.AWAY ? incrementHistory(setNumber, historySetB) : historySetB,
                setNumber, gameNumber));

        return gameIncidents;
    }

    public String incrementHistory(int setNumber, String oldHistory) {
        int set1Games = Integer.parseInt(oldHistory.split(",")[0]);
        int set2Games = Integer.parseInt(oldHistory.split(",")[1]);
        int set3Games = Integer.parseInt(oldHistory.split(",")[2]);

        if (setNumber == 1) {
            set1Games++;
        }
        if (setNumber == 2) {
            set2Games++;
        }
        if (setNumber == 3) {
            set3Games++;
        }

        return set1Games + "," + set2Games + "," + set3Games;
    }

    public List<Incident> getIncidents() {
        incidents = new ArrayList<>();
        List<String> matchTimes = getMatchTimes(1, 0);

        for (int i = 0; i < incidents.size(); i++) {
            incidents.get(i).setMatchTime(matchTimes.get(i));
        }

        return incidents;
    }

    public List<String> getMatchTimes(String startTime) {

        int startMins = Integer.parseInt(startTime.split(":")[0]);
        int startSeconds = Integer.parseInt(startTime.split(":")[1]);
        return getMatchTimes(startMins, startSeconds);
    }

    public List<String> getMatchTimes(int mins, int seconds) {
        ArrayList<String> matchTimes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            seconds += 5;
            if (seconds >= 59) {
                seconds = 0;
                mins++;
            }

            matchTimes.add(mins + ":" + seconds);
        }

        return matchTimes;
    }
}
