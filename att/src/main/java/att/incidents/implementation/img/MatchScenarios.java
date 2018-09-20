package att.incidents.implementation.img;


import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.Side;
import att.incidents.TennisIncidentsHelper;

public class MatchScenarios {

    private List<Incident> incidents;

    private List<Incident> getOnePointFromMatchA(TennisIncidentsHelper tennisIncidentsHelper) {
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

    public List<Incident> getWinMatchIncidentsA() {
        TennisIncidentsHelper tennisIncidentsHelper = new TennisIncidentsHelper();
        incidents = getOnePointFromMatchA(tennisIncidentsHelper);

        incidents.add(tennisIncidentsHelper.getWinningPointIncident("4:40",
                Side.HOME, Side.AWAY, "6,6,0", "0,0,0",
                2, 6));

        incidents.add(tennisIncidentsHelper.getFinishedIncident("4:45", Side.HOME, Side.AWAY, 2));

        return incidents;
    }
}