package att;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.AbandonName;
import ats.betting.trading.att.ws.scenario.dto.Badminton;
import ats.betting.trading.att.ws.scenario.dto.BadmintonPlayer;
import ats.betting.trading.att.ws.scenario.dto.Incident;
import ats.betting.trading.att.ws.scenario.dto.IncidentType;
import ats.betting.trading.att.ws.scenario.dto.LsportsIncident;
import ats.betting.trading.att.ws.scenario.dto.LsportsScores;
import ats.betting.trading.att.ws.scenario.dto.MatchState;
import ats.betting.trading.att.ws.scenario.dto.Score;
import ats.betting.trading.att.ws.scenario.dto.Side;
import ats.betting.trading.att.ws.scenario.dto.Soccer;
import ats.betting.trading.att.ws.scenario.dto.Tennis;
import ats.betting.trading.att.ws.scenario.dto.TennisMatchState;
import ats.core.util.CsvReader;
import util.DateUtil;
import util.XssReader;


public class IncidentsHelper {

    private static final Logger log = Logger.getLogger(IncidentsHelper.class);
    private final int DEFAULT_INCIDENT_DELAY = 10;

    /**
     * Loads the incidents from a CSV file
     *
     * @param incidentsFile csv file path
     * @return list of ATT Incidents
     */
    public List<Incident> loadSoccerIncidentsFromFile(String incidentsFile) throws FileNotFoundException {
        log.info("-- STEP -- load incidents from file");
        log.info("Loading incidents from file=" + incidentsFile);

        CsvReader csvReader = new CsvReader(new FileInputStream(incidentsFile), false);
        String[] headers = new String[] {"match_time", "score", "side", "type", "is_action", "delay", "is_overtime"};
        csvReader.addHeaders(headers);
        csvReader.init();

        List<Incident> incidentsList = new ArrayList<>();
        Soccer incident;

        for (CsvReader.Row row : csvReader.getRows()) {
            incident = new Soccer();
            incident.setMatchTime(row.getValue("match_time"));

            incident.setType(IncidentType.fromValue(row.getValue("type")));

            log.info("Action incident ? : " + row.getValue("is_action"));
            if (!Boolean.parseBoolean(row.getValue("is_action"))) { // Check if is period or action incident
                log.info("Action incident created");
                incident.setSide(Side.fromValue(row.getValue("side")));
                incident.setScore(row.getValue("score"));
            }

            if (row.getValue("delay") != null) {
                incident.setIncidentDelay(Integer.valueOf(row.getValue("delay")));
            } else {
                incident.setIncidentDelay(DEFAULT_INCIDENT_DELAY);
            }

            try {
                if (!row.getValue("is_overtime").isEmpty()) {
                    incident.setOvertime(Boolean.parseBoolean(row.getValue("is_overtime")));
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                log.debug("Incident is not overtime");
            }

            incidentsList.add(incident);
        }
        return incidentsList;
    }

    /**
     * Loads the incidents from an excel file
     *
     * @param incidentsFile xlsx file path
     * @param sheetName file sheet name
     * @return list of ATT Incidents
     */
    public List<Incident> loadBadmintonIncidentsFromFile(String incidentsFile, String sheetName) throws IOException {
        final int MATCH_TIME = 0;
        final int SCORE = 1;
        final int SIDE = 2;
        final int TYPE = 3;
        final int PLAYER_A_HISTORY = 4;
        final int PLAYER_B_HISTORY = 5;
        final int MATCH_STATE = 6;
        final int INCIDENT_REASON = 7;
        final int PLAYER_SERVING = 8;
        final int PLAYER_RECEIVING = 9;

        log.info("-- STEP -- load incidents from file");
        log.info("Loading incidents from file=" + incidentsFile);
        List<Incident> incidentsList = new ArrayList<>();
        XssReader xssReader = new XssReader(new FileInputStream(new File(incidentsFile)), sheetName);

        for (XSSFRow row : xssReader.getCurrentSheetRows()) {

            if (row != null) {

                Badminton incident = new Badminton();
                incident.setMatchTime(row.getCell(MATCH_TIME).getStringCellValue());
                incident.setType(IncidentType.fromValue(row.getCell(TYPE).getStringCellValue()));
                incident.setMatchState(MatchState.fromValue(row.getCell(MATCH_STATE).getStringCellValue()));

                if (row.getCell(SCORE) != null) {
                    incident.setScore(row.getCell(SCORE).getStringCellValue());
                    incident.setSide(Side.fromValue(row.getCell(SIDE).getStringCellValue()));
                }

                if (!row.getCell(PLAYER_A_HISTORY).getStringCellValue().equalsIgnoreCase("nil")) { //if history is present, set historyA&B
                    incident.setHistoryA(row.getCell(PLAYER_A_HISTORY).getStringCellValue());
                    incident.setHistoryB(row.getCell(PLAYER_B_HISTORY).getStringCellValue());
                }

                if (row.getCell(PLAYER_SERVING) != null) {
                    incident.setServer(BadmintonPlayer.fromValue(row.getCell(PLAYER_SERVING).getStringCellValue()));
                    incident.setReceiver(BadmintonPlayer.fromValue(row.getCell(PLAYER_RECEIVING).getStringCellValue()));
                }

                    //if reason value is present, set reason
                    incident.setReason(row.getCell(INCIDENT_REASON, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    incident.setIncidentDelay(DEFAULT_INCIDENT_DELAY);
                    incidentsList.add(incident);
                }
            }

        return incidentsList;
    }

    /**
     * Loads the incidents from an excel file
     *
     * @param incidentsFile xlsx file path
     * @param sheetName file sheet name
     * @return list of ATT Incidents
     */
    public List<Incident> loadTennisIncidentsFromFile(String incidentsFile, String sheetName) throws IOException {
        final int MATCH_TIME = 0;
        final int SCORE = 1;
        final int SIDE = 2;
        final int TYPE = 3;
        final int PLAYER_A_HISTORY = 4;
        final int PLAYER_B_HISTORY = 5;
        final int EVENT_SET = 6;
        final int GAME = 7;
        final int PLAYER_SERVING = 8;
        final int IS_SET_WINNING_POINT = 9;

        log.info("-- STEP -- load incidents from file");
        log.info("Loading incidents from file=" + incidentsFile);
        List<Incident> incidentsList = new ArrayList<>();
        String timeServersFirst = "";

        XssReader xssReader = new XssReader(new FileInputStream(new File(incidentsFile)), sheetName);

        for (XSSFRow row : xssReader.getCurrentSheetRows()) {

            if (row != null && row.getCell(TYPE) != null) {
                Tennis incident = new Tennis();

                incident.setMatchTime(row.getCell(MATCH_TIME, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                log.info(row.getCell(MATCH_TIME, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                try {
                    incident.setType(IncidentType.fromValue(row.getCell(TYPE).getStringCellValue()));
                    switch (incident.getType()) {
                        case ABANDON_MATCH:
                            incident.setName(AbandonName.WALKOVER);
                            break;
                        case TENNIS_MATCH_STATE_CHANGED:
                        case PREGAME:
                            incident.setMatchState(TennisMatchState.IN_PROGRESS);
                            break;
                        case TENNIS_SERVES_FIRST:
                            timeServersFirst = DateUtil.getCurrentTimeInFormat("HH:mm:ss", Calendar.SECOND, 1);
                            break;
                        case TENNIS_INCIDENT_UPDATE:
                            incident.setInfo("First serve event (originally entered at " + timeServersFirst + " UTC) has been updated.");
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("incident " + row.getCell(TYPE).getStringCellValue() + " not supported");
                }

                if (row.getCell(PLAYER_SERVING) != null)
                    incident.setServer(Side.fromValue(row.getCell(PLAYER_SERVING).getStringCellValue()));

                if (row.getCell(IS_SET_WINNING_POINT) != null) {
                    incident.setWinningPoint((Boolean.valueOf(row.getCell(IS_SET_WINNING_POINT).getStringCellValue())));
                } else
                    incident.setWinningPoint(false);

                incident.setSet((int) row.getCell(EVENT_SET, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                incident.setGame((int) row.getCell(GAME, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                if (row.getCell(SCORE) != null && !row.getCell(SCORE).getStringCellValue().isEmpty()) { //if score is present, set score and side
                    incident.setScore(row.getCell(SCORE).getStringCellValue());
                    incident.setSide(Side.fromValue(row.getCell(SIDE).getStringCellValue()));
                }

                incident.setHistoryA(row.getCell(PLAYER_A_HISTORY, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                incident.setHistoryB(row.getCell(PLAYER_B_HISTORY, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                incident.setIncidentDelay(DEFAULT_INCIDENT_DELAY);

                incidentsList.add(incident);
            }
        }

        return incidentsList;
    }

    /**
     * Loads the incidents from a CSV file
     *
     * @param incidentsFile csv file path
     * @return list of ATT Incidents
     */
    public List<LsportsIncident> loadLsportsIncidentsFromFile(String incidentsFile) {
        log.info("-- STEP -- load incidents from file");
        log.info("Loading incidents from file=" + incidentsFile);
        List<LsportsIncident> incidentList = new ArrayList<>();
        LsportsIncident lsportsIncident;
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(incidentsFile));
            while ((line = br.readLine()) != null) {
                lsportsIncident = new LsportsIncident();
                String[] incidentData = line.split(",");

                //LsportsIncident.Outcomes outcomes = new LsportsIncident.Outcomes();
                //lsportsIncident.setOutcomes(outcomes);
                LsportsScores lsportsScores = new LsportsScores();
                lsportsScores.setStatus(incidentData[0]); //1st Half, 2nd Half, Finished
                lsportsScores.setTime(incidentData[1]); //X, Finished
                Score score = new Score();
                score.setAwayScore(incidentData[2]);
                score.setHomeScore(incidentData[3]);
                score.setPeriod(incidentData[4]); //CFS, FT, HT, 2H
                lsportsScores.getScore().add(score);
                lsportsIncident.setScores(lsportsScores);

                incidentList.add(lsportsIncident);
            }
        } catch (IOException e) {
            log.error(e);
        }
        return incidentList;
    }

}