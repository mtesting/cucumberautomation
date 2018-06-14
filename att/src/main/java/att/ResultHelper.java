package att;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.BetResult;
import generated.ats.sportsbook.dto.Market;
import generated.ats.sportsbook.dto.ResultType;
import generated.ats.sportsbook.dto.Selection;
import other.Constants;
import util.Utils;


public class ResultHelper {

    private static final Logger log = Logger.getLogger(ResultHelper.class);

    /**
     * Loads expected market results from an excel input source
     * @param ResultsFile excel file input
     * @param sheetName excel sheet name
     * @return resulted markets
     */
    //TODO migrate from excel to CSV file format
    public List<Market> loadSoccerResultFromFile(String ResultsFile, String sheetName) {
        int MARKET_NAME = 0;
        int MARKET_SELECTION = 1;
        int SELECTION_RESULT = 2;

        log.info("-- STEP -- load Results from file");
        log.info("Loading Results from file=" + ResultsFile);
        List<Market> resultedMarkets = new ArrayList<>();
        //Read the competition details from Excel
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(ResultsFile)));
            XSSFSheet sheet = workbook.getSheet(sheetName);

            Iterator<Row> rowIterator = sheet.iterator();
            Row row = rowIterator.next();
                while (row.getCell(MARKET_NAME).getStringCellValue() != null) {
                    if (!rowIterator.hasNext())
                        break;
                    Market resultedMarket = new Market();
                    resultedMarket.setName(row.getCell(MARKET_NAME).getStringCellValue());
                    List<Selection> selections = new ArrayList<>();
                    while (resultedMarket.getName().equalsIgnoreCase(row.getCell(MARKET_NAME).getStringCellValue())){
                        Selection selection = new Selection();
                        selection.setName(row.getCell(MARKET_SELECTION).getStringCellValue());
                        selection.setResult(ResultType.fromValue(row.getCell(SELECTION_RESULT).getStringCellValue().toUpperCase()));
                        selections.add(selection);
                        if (rowIterator.hasNext()) {
                            row = rowIterator.next();
                        } else {
                            break;
                        }
                    }
                    resultedMarket.setSelection(selections);
                    resultedMarkets.add(resultedMarket);
                }
            workbook.close();
        } catch (IOException e) {
            log.error(e);
        }
        return resultedMarkets;
    }

    /**
     * Reads an excel file and prepares the resulting for BR Mirror
     * @param excelSheet input excel sheet
     * @return a List of BetResult with the winning outcomes
     */
    public List<BetResult> loadBetradarMirrorResulting(String excelSheet) {
        List<BetResult> betResults = new ArrayList<>();
        BetResult betResult;
        try {
            File inputFile = new File(Utils.getSourceAsAbsolutePath(Constants.BETRADAR_MIRROR_INPUT));
            log.info(inputFile.getAbsolutePath());
            FileInputStream file = new FileInputStream(inputFile);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(excelSheet);

            int rows = sheet.getPhysicalNumberOfRows();

            XSSFRow row;
            for (int r = 1; r <= rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    if (row.getCell(4) != null){
                        XSSFCell Oddstype = row.getCell(0);
                        XSSFCell outcome = row.getCell(1);

                        betResult = new BetResult();
                        betResult.setOddstype(Oddstype.toString());
                        betResult.setOutcome(outcome.toString());

                        if (row.getCell(5) != null) {
                            betResult.setHandicap(row.getCell(5).toString());
                        }

                        betResults.add(betResult);
                    }
                }
            }

            file.close();
        } catch (IOException | URISyntaxException e) {
            log.error(e);
        }
        return betResults;
    }

}