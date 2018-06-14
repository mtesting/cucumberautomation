package att.events;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import ats.betting.trading.att.ws.scenario.ScenarioDefinitionException;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.Horse;
import ats.betting.trading.att.ws.scenario.dto.Market;
import ats.betting.trading.att.ws.scenario.dto.Outcome;
import ats.betting.trading.att.ws.scenario.dto.Race;
import ats.betting.trading.att.ws.scenario.dto.Race.Horses;
import ats.betting.trading.att.ws.scenario.dto.RaceMeetingDefinition_0020;
import ats.betting.trading.att.ws.scenario.dto.RaceMeetingDefinition_0020.Races;
import ats.betting.trading.att.ws.scenario.dto.RaceSelection;
import ats.betting.trading.att.ws.scenario.dto.RaceSelectionPrice;
import ats.betting.trading.att.ws.scenario.dto.RaceStage;
import ats.betting.trading.att.ws.scenario.dto.RaceUpdate;
import ats.betting.trading.att.ws.scenario.dto.RaceUpdate.Markets;
import ats.betting.trading.att.ws.scenario.dto.RaceUpdate.RaceSelections;
import ats.betting.trading.att.ws.scenario.dto.ResultType;
import ats.betting.trading.att.ws.scenario.dto.SelectionStatus;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import other.Constants;
import other.EventDataService;
import util.DateUtil;
import util.Utils;

/**
 * Used for HorseRace Meeting creation
 *
 * @author pelumalai
 */
public class RaceHelper extends EventHelper {

    private static final String InputFilesLocation = "HR/";
    private XSSFWorkbook HRExcelWorkbook;

    /**
     * Read in a given excel file name in the HR resources directory
     *
     * @param inputExcel the excel file name
     * @return the excel file as a WorkBook instance
     */
    private static XSSFWorkbook readHRExcelFile(String inputExcel) {
        XSSFWorkbook workbook = null;
        try {
            FileInputStream input = new FileInputStream(
                    new File(Utils.getSourceAsAbsolutePath(InputFilesLocation + inputExcel)));
            workbook = new XSSFWorkbook(input);
            input.close();
        } catch (URISyntaxException | IOException e) {
            log.error(e);
        }
        return workbook;
    }

    /**
     * Will read in the HR excel file defined by Constants.HR_INPUT_EXCEL
     * and saves the Workbook instance in the class for use later
     */
    private void readHRWorkbook() {
        HRExcelWorkbook = readHRExcelFile(Constants.HR_INPUT_EXCEL);
    }

    /**
     * Returns the Workbook instance.
     * If it has not been initialised yet, the excel file defined by Constants.HR_INPUT_EXCEL
     * will be read and saved to it before returning.
     *
     * @return the Workbook instance
     */
    private XSSFWorkbook getHRWorkbook() {
        if (HRExcelWorkbook == null)
            readHRWorkbook();
        return HRExcelWorkbook;
    }

    public void validateHorseRaceResultsinDB(String inputExcel, String sheetname) throws DecoderConfigException, SQLException {
        HRExcelWorkbook = readHRExcelFile(inputExcel);
        validateHorseRaceResultsinDB(sheetname);
    }

    /**
     * This overload will use the currently cached workbook instead of reading in the file
     */
    public void validateHorseRaceResultsinDB(String sheetname) throws DecoderConfigException, SQLException {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        EventDataService eds = new EventDataService(decoder.decodeCustomerDb(Constants.CUSTOMER_IN_TEST));

        XSSFSheet sheet = getHRWorkbook().getSheet(sheetname);

        //Iterate through each rows one by one
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        XSSFRow row;

        //read horse details one by one
        for (int r = 3; r <= rows; r++) {

            row = sheet.getRow(r);
            if (row != null) {

                XSSFCell name = row.getCell(1);
                XSSFCell expResult = row.getCell(6);
                String dbResult = eds.getResult(Integer.parseInt(this.event.getEventRef()), "Win Each Way", name.getStringCellValue());
                Assert.assertNotNull("DB resulting is null for horse=" + name.getStringCellValue(), dbResult);
                log.info(name.getStringCellValue() + " - comparing the DB result with the expected result [" + dbResult + ":" + expResult + "]");
                Assert.assertTrue("Result assertion failed for horse=" + name.getStringCellValue(),
                        dbResult.trim().toUpperCase().startsWith(expResult.getStringCellValue().toUpperCase()));

            }

        }
    }

    public void createRaceMeeting() throws ScenarioDefinitionException {
        RaceMeetingDefinition_0020 raceMeeting = this.getRaceMeetingDetailsFomExcel("creation",
                "PRESS_ASSOCIATION", "currenttime", "Horses", 1);
        this.trackingId = testScenarioSupport.prepareRaceMeeting(raceMeeting);
        this.assertEventIsCreated();
    }

    public void launchEvent() {
        testScenarioSupport.launchEvent(this.event.getEventRef());
    }

    public void sendFeedUpdates(String inputExcel, String sheetName, RaceStage inStage, long revisionId, String FeedType) {
        sendFeedUpdates(sheetName, generateRaceSelectionsFromExcel(inputExcel, sheetName, FeedType), inStage, revisionId, FeedType);
    }

    /**
     * This overload will use the currently cached workbook instead of reading in the file
     */
    public void sendFeedUpdates(String sheetName, RaceStage inStage, long revisionId, String FeedType) {
        sendFeedUpdates(sheetName, generateRaceSelectionsFromExcel(sheetName, FeedType), inStage, revisionId, FeedType);
    }

    private void sendFeedUpdates(String sheetName, RaceSelections raceList, RaceStage inStage, long revisionId, String FeedType) {
        //	SetRaceUpdates raceupdates = new SetRaceUpdates();
        //	raceupdates.setEventRef(this.EventHelperTemplate.getId());
        RaceUpdate raceupdate = new RaceUpdate();

        //Get time
        String time = DateUtil.getCurrentTimeInFormat("yyyyMMdd'T'HHmmssZ");
        raceupdate.setTimeStamp(time);
        raceupdate.setRevision(revisionId);
        raceupdate.setRaceStage(inStage);

        //To-do code for race selections
        raceupdate.setRaceSelections(raceList);

        if (FeedType != null) {
            //Market Details
            Markets mrList = new Markets();

            Market wew = new Market();
            wew.setDeduction("2");
            wew.setDeductionType("AllBets");
            wew.setId(1);
            mrList.getMarket().add(wew);
            raceupdate.setMarkets(mrList);
        }
        //raceupdates.setRaceUpdate(raceupdate);

        Boolean result = testScenarioSupport.setRaceUpdates(raceupdate, this.event.getEventRef());
        Assert.assertTrue("PA update failed for stage=" + inStage.value(), result);
        log.info("PA " + sheetName + " update sent for " + this.event.getEventRef() + " and response is " + result);
    }

    private RaceMeetingDefinition_0020 getRaceMeetingDetailsFomExcel(String creationSheet, String inFeed, String startTime, String raceType, int noOfRaces) {
        RaceMeetingDefinition_0020 racemeet = new RaceMeetingDefinition_0020();

        long maxRunners = 0;
        //Direct Parameters
        racemeet.setEndpointMonitoring(true);
        racemeet.setFeed(inFeed);
        racemeet.setTotalNumberOfRaces(noOfRaces);
        racemeet.setRaceType(raceType);
        racemeet.setStatus("Dormant");

        //Todo calculation in a better way

        SimpleDateFormat formatterRaceTime = new SimpleDateFormat("HHmmZ");
        String raceTime;
        Calendar c1 = Calendar.getInstance();
        Date cDate = null;
        if (startTime.trim().equalsIgnoreCase("currenttime")) {
            // add months to current date
            c1.add(Calendar.MINUTE, 10);
            cDate = c1.getTime();

            c1.add(Calendar.SECOND, 12);
            raceTime = formatterRaceTime.format(c1.getTime());
        } else {
            raceTime = startTime;
        }

        //Change to XMLGregorianCalendar
        XMLGregorianCalendar meetingTime = DateUtil.getDateAsXMLGregorianCalender(cDate);
        racemeet.setDate(meetingTime);
        log.info("meeting time : " + meetingTime);
        log.info("race time : " + raceTime);

        //Read the competition details
        XSSFSheet sheet = getHRWorkbook().getSheet(creationSheet);

        XSSFRow row;
        int r = 1;
        row = sheet.getRow(r);
        if (row != null) {
            log.info(r);
            racemeet.setAtsRaceTrackId((long) row.getCell(0).getNumericCellValue());
            racemeet.setName(row.getCell(1).getStringCellValue());
            racemeet.setTotalNumberOfRaces((int) row.getCell(2).getNumericCellValue());
            maxRunners = (long) (row.getCell(3).getNumericCellValue());

        }

        //Race object
        Race race = new Race();
        race.setHandicap("No");
        race.setTime(raceTime);
        Horses hrList = this.getHorseDetails("horses");
        race.setHorses(hrList);
        race.setRaceType("Flat");
        race.setMaxRunners(maxRunners);

        //Generating the list of Horses in the Race
        Races raceList = new Races();
        raceList.getRace().add(race);
        racemeet.setRaces(raceList);

        return racemeet;
    }

    private RaceSelections generateRaceSelectionsFromExcel(String inputExcel, String sheetname, String FeedType) {
        HRExcelWorkbook = readHRExcelFile(inputExcel);
        return generateRaceSelectionsFromExcel(sheetname, FeedType);
    }

    /**
     * This overload will use the currently cached workbook instead of reading in the file
     */
    private RaceSelections generateRaceSelectionsFromExcel(String sheetname, String FeedType) {
        RaceSelections raceList = new RaceSelections();

        XSSFWorkbook workbook = getHRWorkbook();
        XSSFSheet sheet = workbook.getSheet(sheetname);

        //Iterate through each rows one by one
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        XSSFRow row;

        //read horse details one by one
        for (int r = 3; r <= rows; r++) {

            row = sheet.getRow(r);
            if (row != null) {

                RaceSelection raceSelection = new RaceSelection();
                XSSFCell id = row.getCell(0);
                XSSFCell feedPriceNum = row.getCell(2);
                XSSFCell feedPriceDen = row.getCell(3);
                XSSFCell status = row.getCell(4);
                XSSFCell position = row.getCell(5);

                raceSelection.setId(id.getRawValue());
                RaceSelectionPrice rsp = new RaceSelectionPrice();

                switch (FeedType) {
                    case "price":
                        if (status.getStringCellValue().trim().equalsIgnoreCase("RUNNER"))
                            raceSelection.setSelectionStatus(SelectionStatus.RUNNER);
                        else
                            raceSelection.setSelectionStatus(SelectionStatus.WITHDRAWN);
                        rsp.setMarketId(1);
                        rsp.setDenominator((int) feedPriceDen.getNumericCellValue());
                        rsp.setNumerator((int) feedPriceNum.getNumericCellValue());

                        raceSelection.setRaceSelectionPrice(rsp);
                        break;

                    case "off":

                        break;
                    case "finish":
                        if (status.getStringCellValue().trim().equalsIgnoreCase("RUNNER"))
                            raceSelection.setSelectionStatus(SelectionStatus.RUNNER);
                        else
                            raceSelection.setSelectionStatus(SelectionStatus.WITHDRAWN);

                        rsp.setMarketId(1);
                        rsp.setDenominator((int) feedPriceDen.getNumericCellValue());
                        rsp.setNumerator((int) feedPriceNum.getNumericCellValue());

                        raceSelection.setRaceSelectionPrice(rsp);
                        break;
                    case "result":
                        if (status.getStringCellValue().trim().equalsIgnoreCase("RUNNER"))
                            raceSelection.setSelectionStatus(SelectionStatus.RUNNER);
                        else
                            raceSelection.setSelectionStatus(SelectionStatus.WITHDRAWN);

                        raceSelection.setFinishedPosition((int) position.getNumericCellValue());
                        rsp.setMarketId(1);
                        rsp.setDenominator((int) feedPriceDen.getNumericCellValue());
                        rsp.setNumerator((int) feedPriceNum.getNumericCellValue());

                        raceSelection.setRaceSelectionPrice(rsp);
                        break;

                    default:
                        //To -do
                        break;

                }
                if (raceSelection != null)
                    raceList.getRaceSelection().add(raceSelection);
            }

        }

        return raceList;
    }

    private Horses getHorseDetails(String sheetname) {
        Horses HRList = new Horses();

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = getHRWorkbook().getSheet(sheetname);

        //Iterate through each rows one by one
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        XSSFRow row;

        //read horse details one by one
        for (int r = 3; r <= rows; r++) {
            Horse hr = new Horse();
            row = sheet.getRow(r);
            if (row != null) {
                XSSFCell id = row.getCell(0);
                XSSFCell name = row.getCell(1);

                hr.setId(id.getRawValue());
                hr.setName(name.getStringCellValue());

                HRList.getHorse().add(hr);

            }
        }
        return HRList;
    }

    public boolean saveHorseRaceResults(String eventid, String sheetname) {
        List<Outcome> OutcomeList = new ArrayList<>();
        //Get first/desired sheet from the workbook
        XSSFSheet sheet = getHRWorkbook().getSheet(sheetname);

        //Iterate through each rows one by one
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        XSSFRow row;

        //read horse details one by one
        for (int r = 1; r <= rows; r++) {
            Outcome hr = new Outcome();
            row = sheet.getRow(r);
            if (row != null) {
                XSSFCell name = row.getCell(0);
                XSSFCell position = row.getCell(1);
                XSSFCell result = row.getCell(2);

                hr.setPlaced((int) position.getNumericCellValue());
                hr.setName(name.getStringCellValue());
                hr.setResult(ResultType.fromValue(result.getStringCellValue()));

                OutcomeList.add(hr);

            }
        }
        return testScenarioSupport.saveRacingResult(eventid, OutcomeList);

    }

    public boolean settleRaceEvent(String eventRef) {
        return testScenarioSupport.settleRaceEvent(eventRef);
    }

    public String getHorseByResult(String horseOutcome, String sheetname) {
        //Get first/desired sheet from the workbook
        XSSFSheet sheet = getHRWorkbook().getSheet(sheetname);

        //Iterate through each rows one by one
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        XSSFRow row;

        //read horse details one by one
        for (int r = 1; r <= rows; r++) {
            row = sheet.getRow(r);
            if (row != null) {
                XSSFCell name = row.getCell(0);
                XSSFCell result = row.getCell(2);

                if (horseOutcome.equalsIgnoreCase(result.getStringCellValue())) {
                    return name.getStringCellValue();
                }

            }
        }
        return null;
    }

    public void increaseHorseOdds(String sheetName) {
        log.info("-- STEP -- Increase Horse Odds");
        XSSFSheet sheet = getHRWorkbook().getSheet(sheetName);
        XSSFRow row;

        for (int r = 3; r <= sheet.getPhysicalNumberOfRows(); r++) {
            row = sheet.getRow(r);
            if (row == null) continue;

            //Change odds - this is hardcoded and adds 5 to the numerators
            XSSFCell numerator = row.getCell(2);
            numerator.setCellValue(((int) numerator.getNumericCellValue()) + 5);
            log.info("Odds increased for horse " + row.getCell(1) + " "
                    + "to price=" + (int) Double.parseDouble(row.getCell(2).toString())
                    + "/" + (int) Double.parseDouble(row.getCell(3).toString()));
        }
    }

    public void updateHorsePlaces(String sheetName, int numPlaces) {
        XSSFSheet sheet = getHRWorkbook().getSheet(sheetName);
        boolean placedWinner = false;

        for (int r = 3; r < sheet.getPhysicalNumberOfRows(); r++) {
            XSSFRow row = sheet.getRow(r);
            XSSFCell statusCell = row.getCell(4);
            XSSFCell resultCell = row.getCell(6);
            if (statusCell.getStringCellValue().equalsIgnoreCase("runner")) {
                if (numPlaces > 0) {
                    if (placedWinner)
                        resultCell.setCellValue("P");
                    else {
                        resultCell.setCellValue("W");
                        placedWinner = true;
                    }
                    numPlaces--;
                } else
                    resultCell.setCellValue("L");
            } else
                resultCell.setCellValue("V");
        }
    }

    public void setLpFlag(String marketId) {
        log.info("-- STEP -- Set LP flag as true");
        testScenarioSupport.setMarketFlags(marketId, true, "OPEN", true, true, true);
    }

    public void updateEachWay(long marketId, String numPlaces) {
        log.info("Updating each way place terms to " + numPlaces);
        Assert.assertTrue("Fail trying to updating place terms", testScenarioSupport.updateEachway(marketId, numPlaces));
    }

    @Override
    public void createEvent(long competitionId, String incidentsFeedProvider, int matchesTotal, int matchesInplay, String pricingFeedProvider, String startDateTime, EventDefinition.Markets markets) throws ScenarioDefinitionException {

    }

}
