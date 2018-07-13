package att.incidents.img.penalties;

import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public class GamePenaltyScenarios {

    private List<Incident> incidents;
    private TennisIncidentsHelper tennisIncidentsHelper;

    private List<Incident> getMatchInitialIncidents(TennisIncidentsHelper tennisIncidentsHelper) {
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
        incidents.add(tennisIncidentsHelper.getPointIncident("0:30", "30-30",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    private List<Incident> getOnePointFromSetA(TennisIncidentsHelper tennisIncidentsHelper) {
        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPeriodIncident("", Side.HOME, 1, 1));

        int seconds = 20;
        int minutes = 0;
        int gamesSet1A = 0;
        int gamesSet2A = 0;
        int pointsA = 0;
        int setNumber = 1;
        int gameNumber = 1;
        Side servingSide = Side.HOME;
        boolean endGame = false;

        while (!(gamesSet1A == 5 && pointsA == 40)) {
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

                incidents.add(tennisIncidentsHelper.getPeriodIncident("", servingSide, setNumber, gameNumber));

            } else {
                incidents.add(tennisIncidentsHelper.getPointIncident(minutes + ":" + seconds, pointsA + "-0",
                        Side.HOME, servingSide, gamesSet1A + "," + gamesSet2A + ",0", "0,0,0",
                        setNumber, gameNumber));
            }
        }

        return incidents;
    }

    private List<Incident> getThreePointsFromSetB(TennisIncidentsHelper tennisIncidentsHelper) {
        incidents = new ArrayList<>();
        incidents.add(tennisIncidentsHelper.getPregameUmpireIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPregameTossIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getPregameInProgressIncidentIMG(Side.HOME));
        incidents.add(tennisIncidentsHelper.getMatchStateChangedIncidentIMG());
        incidents.add(tennisIncidentsHelper.getPeriodIncident("", Side.HOME, 1, 1));

        int seconds = 20;
        int minutes = 0;
        int gamesSet1B = 0;
        int gamesSet2B = 0;
        int pointsB = 0;
        int setNumber = 1;
        int gameNumber = 1;
        Side servingSide = Side.HOME;
        boolean endGame = false;

        while (!(gamesSet1B == 5 && pointsB == 15)) {
            seconds += 5;
            if (seconds >= 59) {
                minutes++;
                seconds = 0;
            }

            switch (pointsB) {
                case 0:
                    pointsB = 15;
                    break;
                case 15:
                    pointsB = 30;
                    break;
                case 30:
                    pointsB = 40;
                    break;
                case 40:
                    endGame = true;
                    break;
            }

            if (endGame) {
                endGame = false;
                pointsB = 0;
                if (gamesSet1B < 6) {
                    gamesSet1B++;
                } else {
                    gamesSet2B++;
                }

                incidents.add(tennisIncidentsHelper.getPointIncident(minutes + ":" + seconds, "0-" + pointsB,
                        Side.AWAY, servingSide, "0,0,0", gamesSet1B + "," + gamesSet2B + ",0",
                        setNumber, gameNumber));


                if (servingSide == Side.HOME) {
                    servingSide = Side.AWAY;
                } else {
                    servingSide = Side.HOME;
                }

                gameNumber++;

                incidents.add(tennisIncidentsHelper.getPeriodIncident("", servingSide, setNumber, gameNumber));

            } else {
                incidents.add(tennisIncidentsHelper.getPointIncident(minutes + ":" + seconds, "0-" + pointsB,
                        Side.AWAY, servingSide, "0,0,0", gamesSet1B + "," + gamesSet2B + ",0",
                        setNumber, gameNumber));
            }
        }

        return incidents;
    }

    public List<Incident> getGamePenaltyAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        // We get the Match back at 30-30 - A GamePenalty To Player B will award the game to Player A
        incidents.add(tennisIncidentsHelper.getGameWonPenaltyIncident("0:40", "0-0",
                Side.AWAY, Side.HOME, "1,0,0", "0,0,0",
                1, 1));

        return incidents;
    }

    public List<Incident> getGamePenaltyBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getMatchInitialIncidents(tennisIncidentsHelper);

        // We get the  Score back at 30-30, we award B a point to 30-40, then give Player A a GamePenalty - B wins the Game
        incidents.add(tennisIncidentsHelper.getPointIncident("0:35", "30-40",
                Side.AWAY, Side.HOME, "0,0,0", "0,0,0",
                1, 1));

        incidents.add(tennisIncidentsHelper.getGameWonPenaltyIncident("0:40", "0-0",
                Side.HOME, Side.HOME, "0,0,0", "1,0,0",
                1, 1));

        return incidents;
    }

    public List<Incident> getWinSetByGamePenaltyAIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getOnePointFromSetA(tennisIncidentsHelper);

        Incident lastIncident = incidents.get(incidents.size() - 1);
        String matchTime = lastIncident.getMatchTime();
        String newTime = matchTime.substring(0, matchTime.length() - 1) + "9";
        Side newSide = lastIncident.getSide() == Side.HOME ? Side.AWAY : Side.HOME;

        incidents.add(tennisIncidentsHelper.getGameWonPenaltyIncident(newTime, "0-0",
                Side.AWAY, newSide, "6,0,0", "0,0,0",
                1, 6));

        return incidents;
    }

    public List<Incident> getWinSetByGamePenaltyBIncidents() {
        tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getThreePointsFromSetB(tennisIncidentsHelper);

        Incident lastIncident = incidents.get(incidents.size() - 1);
        String matchTime = lastIncident.getMatchTime();
        String newTime = matchTime.substring(0, matchTime.length() - 1) + "9";
        Side newSide = lastIncident.getSide() == Side.HOME ? Side.AWAY : Side.HOME;

        incidents.add(tennisIncidentsHelper.getGameWonPenaltyIncident(newTime, "0-0",
                Side.HOME, newSide, "0,0,0", "6,0,0",
                1, 6));

        return incidents;
    }

}
