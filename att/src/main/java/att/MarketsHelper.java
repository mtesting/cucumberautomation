package att;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import ats.betting.trading.att.ws.scenario.dto.Betradarliveodds;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.EventMarket;
import ats.betting.trading.att.ws.scenario.dto.Instrument;
import ats.betting.trading.att.ws.scenario.dto.Instrument2;
import ats.betting.trading.att.ws.scenario.dto.Liveoddsmarket;
import ats.betting.trading.att.ws.scenario.dto.Match;
import ats.core.util.CsvReader;
import util.Utils;
import util.XssReader;

/**
 * This class loads Markets input for mirror feeds
 */
public class MarketsHelper {

    private static final Logger log = Logger.getLogger(MarketsHelper.class);

    /**
     * This function will return Markets for Betradar Mirror feed
     *
     * @param inputExcel input excel file name
     * @param excelSheet excel sheet to be loaded
     * @return Betradar markets
     * @throws IOException input file not found
     */
    public static EventDefinition.Markets prepareBetradarMirrorMarkets(String inputExcel, String excelSheet) throws URISyntaxException, IOException {
        EventDefinition.Markets markets = new EventDefinition.Markets();

        XssReader xssReader = new XssReader(new FileInputStream(new File(Utils.getSourceAsAbsolutePath(inputExcel))), excelSheet);

        String tempMtype = "";

        EventMarket market = new EventMarket();
        EventMarket.Instruments evtInstruments = new EventMarket.Instruments();
        for (XSSFRow row : xssReader.getCurrentSheetRows()) {

            if (row != null) {
                XSSFCell marketType = row.getCell(0);
                XSSFCell outCome = row.getCell(1);
                XSSFCell outComeId = row.getCell(2);
                XSSFCell value = row.getCell(3);
                XSSFCell handicap = row.getCell(5);
                XSSFCell baseline = row.getCell(6);
                XSSFCell bet = row.getCell(7);
                XSSFCell line = row.getCell(9);
                XSSFCell startPrice = row.getCell(10);
                XSSFCell status = row.getCell(11);

                if (!tempMtype.equalsIgnoreCase(marketType.toString())) {
                    evtInstruments = new EventMarket.Instruments();
                }

                Instrument instrument = new Instrument();
                instrument.setOutCome(outCome.toString());
                if (checkCell(outComeId)) instrument.setOutComeId(outComeId.toString());
                instrument.setValue(value.toString());
                if (checkCell(handicap)) instrument.setHandicap(handicap.toString());

                evtInstruments.getInstrument().add(instrument);
                if (!tempMtype.equalsIgnoreCase(marketType.toString())) {
                    market = new EventMarket();
                    market.setType(marketType.toString());
                    market.setInstruments(evtInstruments);
                    markets.getMarket().add(market);
                    tempMtype = marketType.toString();
                } else {
                    market.setInstruments(evtInstruments);
                }
            }
        }
        return markets;
    }

    public static EventDefinition.Markets prepareLsportsMirrorMarkets(String inputExcel, String excelSheet) throws URISyntaxException, IOException {
        EventDefinition.Markets markets = new EventDefinition.Markets();

        XssReader xssReader = new XssReader(new FileInputStream(new File(Utils.getSourceAsAbsolutePath(inputExcel))), excelSheet);
        String tempMtype = "";

        EventMarket market = new EventMarket();
        EventMarket.Instruments evtInstruments = new EventMarket.Instruments();
        for (XSSFRow row : xssReader.getCurrentSheetRows()) {

            if (row != null) {
                XSSFCell marketType = row.getCell(0);
                //XSSFCell outcome = row.getCell(1);
                XSSFCell outcomeId = row.getCell(2);
                XSSFCell odd = row.getCell(3);
                XSSFCell startPrice = row.getCell(4);
                XSSFCell bet = row.getCell(5);
                XSSFCell isWinner = row.getCell(6);
                XSSFCell line = row.getCell(7);
                XSSFCell status = row.getCell(8);

                if (!tempMtype.equalsIgnoreCase(marketType.toString())) {
                    evtInstruments = new EventMarket.Instruments();
                }

                Instrument instrument = new Instrument();

                //instrument.setOutCome(outcome.toString());
                if (outcomeId != null) instrument.setOutComeId(outcomeId.toString());

                instrument.setValue(odd.toString());

                if (startPrice != null) instrument.setStartPrice(startPrice.toString());

                if (bet != null) instrument.setBet(bet.toString());

                if (isWinner != null) instrument.setIsWinner(isWinner.toString());

                if (line != null) instrument.setLine(line.toString());

                if (status != null) instrument.setStatus(status.toString());

                evtInstruments.getInstrument().add(instrument);
                if (!tempMtype.equalsIgnoreCase(marketType.toString())) {
                    market = new EventMarket();
                    market.setType(marketType.toString());
                    market.setInstruments(evtInstruments);
                    market.setBookmakerId(8L);
                    markets.getMarket().add(market);
                    tempMtype = marketType.toString();
                } else {
                    market.setInstruments(evtInstruments);
                }
            }
        }

        return markets;
    }

    /**
     * This function prepares BetradarLiveOdds Match updates based on the inputFile
     *
     * @param inputExcel inputFile
     * @return List of BetradarLiveOdds updates
     */
    public static List<Betradarliveodds> prepareLiveOddsMatchUpdate(String inputExcel) throws IOException, URISyntaxException {
        log.info("-- STEP -- prepare LiveOdds match update");
        List<Betradarliveodds> betradarliveoddsList = new ArrayList<>();
        Betradarliveodds betradarLiveOdds;
        XssReader xssReader = new XssReader(new FileInputStream(new File(Utils.getSourceAsAbsolutePath(inputExcel))), "Sheet1");

        for (XSSFRow row : xssReader.getCurrentSheetRows()) {
            if (row != null) {
                betradarLiveOdds = new Betradarliveodds();
                betradarLiveOdds.setType(row.getCell(0).toString());
                betradarLiveOdds.setMessageType(row.getCell(1).toString());
                Match match = new Match();
                if (checkCell(row.getCell(2))) match.setMatchtime(Long.valueOf(row.getCell(2).toString()));
                if (checkCell(row.getCell(3))) match.setMatchtimeExtended(row.getCell(3).toString());
                match.setStatus(row.getCell(4).toString());
                if (checkCell(row.getCell(5))) match.setBetstatus(row.getCell(5).toString());
                if (checkCell(row.getCell(6))) match.setScore(row.getCell(6).toString());
                if (checkCell(row.getCell(7))) match.setClearedscore(row.getCell(7).toString());
                if (checkCell(row.getCell(8))) match.setBooked(Boolean.valueOf(row.getCell(8).toString()));
                match.setActive(Long.valueOf(row.getCell(9).toString()));
                if (checkCell(row.getCell(10))) {
                    match.setMarkets(loadLiveOddsMarkets(Utils.getSourceAsAbsolutePath(
                            "incidents/LiveOdds/" + row.getCell(10).toString())));
                }
                if (checkCell(row.getCell(11))) match.setMessage(row.getCell(11).toString());
                betradarLiveOdds.setMatch(match);
                if (checkCell(row.getCell(12))) betradarLiveOdds.setStarttime(Long.valueOf(row.getCell(12).toString()));
                betradarliveoddsList.add(betradarLiveOdds);
            }
        }
        return betradarliveoddsList;
    }

    /**
     * This function prepares BetradarLiveOdds Odds updates
     * The odds are listed per match. Each Match element contains all markets the Betradar system delivers for this match.
     * Per market the odds are added in sub-elements called OddsField, if that market is currently active.
     *
     * @param inputFile CSV inputFile
     * @return Odds update
     */
    private static Match.Markets loadLiveOddsMarkets(String inputFile) throws IOException {
        log.info("-- STEP -- load Odds update from file");
        Match.Markets markets1 = new Match.Markets();
        Liveoddsmarket liveoddsmarket = new Liveoddsmarket();
        Liveoddsmarket.Instruments instruments = new Liveoddsmarket.Instruments();
        Instrument2 instrument1;

        CsvReader csvReader = new CsvReader(new FileInputStream(inputFile), false);
        //TODO add headers in the input file, change the false flag avobe and replace magic numbers

        for (CsvReader.Row row : csvReader.getRows()) {

            if (row.getValue(0).equalsIgnoreCase(liveoddsmarket.getFreetext())) {
                instrument1 = new Instrument2();
                instrument1.setActive(1L);
                instrument1.setType(row.getValue(6));
                instrument1.setTypeid(Integer.valueOf(row.getValue(7)));
                instrument1.setValue(row.getValue(8));
                instruments.getInstrument().add(instrument1);
            } else {
                liveoddsmarket = new Liveoddsmarket();
                liveoddsmarket.setChanged(true);
                liveoddsmarket.setActive(1L);
                liveoddsmarket.setFreetext(row.getValue(0));
                liveoddsmarket.setCombination(Long.parseLong(row.getValue(1)));
                liveoddsmarket.setTypeid(Long.parseLong(row.getValue(2)));
                liveoddsmarket.setSubtype(Long.parseLong(row.getValue(3)));
                liveoddsmarket.setType(row.getValue(4));
                liveoddsmarket.setSpecialoddsvalue(row.getValue(5));
                //liveoddsmarket.setMostbalanced(true);
                instruments = new Liveoddsmarket.Instruments();
                markets1.getMarket().add(liveoddsmarket);

                instrument1 = new Instrument2();
                instrument1.setActive(1L);
                instrument1.setType(row.getValue(6));
                instrument1.setTypeid(Integer.valueOf(row.getValue(7)));
                instrument1.setValue(row.getValue(8));
                if (row.getRowNum() > 9) {
                    instrument1.setOutcome(Long.valueOf(row.getValue(9)));
                }
                instruments.getInstrument().add(instrument1);
            }
            liveoddsmarket.setInstruments(instruments);
        }
        return markets1;
    }

    private static boolean checkCell(XSSFCell cell) {
        return cell != null && !cell.toString().isEmpty();
    }

}
