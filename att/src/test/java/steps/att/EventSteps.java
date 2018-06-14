package steps.att;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;

import apiLevelInteraction.SportsbookHelper;
import ats.betting.trading.att.ws.scenario.dto.Event;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition;
import ats.betting.trading.att.ws.scenario.dto.EventDefinition.Markets;
import ats.betting.trading.att.ws.scenario.dto.EventMarket;
import ats.betting.trading.att.ws.scenario.dto.Instrument;
import att.ResultHelper;
import att.StatusHelper;
import att.events.EventHelperTemplate;
import att.events.EventManager;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import entities.Customer;
import generated.ats.betsync.betcatcher.dto.BetPlacementResult;
import generated.ats.betsync.betcatcher.dto.BetPlacementStatus;
import generated.ats.betsync.betcatcher.dto.PlaceBetsResponse;
import generated.ats.sportsbook.dto.Market;
import generated.ats.sportsbook.dto.Selection;
import util.Utils;

public class EventSteps {

    private static final Logger log = Logger.getLogger(EventSteps.class);
    protected final Decoder decoder = DecoderManager.getManager().getDecoder();
    protected EventHelperTemplate eventHelper = EventManager.getEventManager().getEventHelper();


    public EventSteps() throws DecoderConfigException {
    }

    public static void assertBetPlacement(PlaceBetsResponse placeBetsResponse) {
        for (BetPlacementResult result : placeBetsResponse.getBetPlacementResult()) {
            Assert.assertEquals("Bets placement failed, rejectionReason=" + result.getRejectionReason(),
                    BetPlacementStatus.ACCEPTED, result.getStatus());
        }
    }

    /**
     * preparing Markets for Betradar Mirror feed from given input
     */
    protected Markets prepareBetradarMirrorMarketsFromDataset(Map<String, String> data) {
        EventDefinition.Markets markets = new EventDefinition.Markets();
        EventMarket.Instruments evtInstruments = new EventMarket.Instruments();

        Set<String> keys = data.keySet();
        for (String aKey : keys) {
            String temp = aKey.substring(0, 7);
            if (temp.equalsIgnoreCase("outcome")) {
                Instrument instrument = new Instrument();
                String[] outCome = aKey.split("_");
                instrument.setValue(data.get(aKey));
                instrument.setOutCome(outCome[1]);
                evtInstruments.getInstrument().add(instrument);
            }
            if (aKey.equalsIgnoreCase("marketType")) {
                EventMarket market = new EventMarket();
                market.setType(data.get("marketType"));
                if (evtInstruments.getInstrument().size() > 0) {
                    market.setInstruments(evtInstruments);
                    markets.getMarket().add(market);
                    evtInstruments = new EventMarket.Instruments();
                }
            }

        }
        return markets;
    }

    protected void assertStatusMarketsSettled(String endPoint, String marketDataInputExcel, String excelSheet) throws DecoderConfigException, SOAPException, XPathExpressionException {
        assertStatusMarketsSettled(endPoint, marketDataInputExcel, excelSheet, 60);
    }

    /**
     * Validate if the Selections in the event is settled correctly in the Status Service
     */
    protected void assertStatusMarketsSettled(String endPoint, String marketDataInputExcel, String excelSheet, int waitTimeSec) throws DecoderConfigException, SOAPException, XPathExpressionException {
        ResultHelper resultHelper = new ResultHelper();
        List<Market> expectedMarketsResults = resultHelper.loadSoccerResultFromFile(marketDataInputExcel, excelSheet);
        
        for (Market market : expectedMarketsResults) {
            String marketName = market.getName();
            String selectionName;
            String expectedResult;
            List<String> results;
            log.info("Market Name -------" + marketName);
            log.info("Selection Size ------");

            for (Selection selection : market.getSelection()) {

                selectionName = assignSelectionName(selection, endPoint);
                
                expectedResult = selection.getResult().name();
                StatusHelper statusHelper = new StatusHelper();

                boolean isSelectionResulted = false;
                int loopCounter = waitTimeSec / 2;
                statusHelper.getStatus(eventHelper.getEvent().getEventRef(), endPoint.toUpperCase());
                results = statusHelper.getTextContentOfNode("//event/Markets/Market/Name[text()='" + marketName +
                        "']/../Selections/Selection/Name[text()='" + selectionName + "']/../Result");

                log.info("loopCounter --- " + loopCounter);
                while (loopCounter > 0) {
                    loopCounter--;
                    log.info("loopCounter --- " + loopCounter);
                    if ((results.size() > 0)) {
                        //log.info("result list contains " + results.get(0));
                        isSelectionResulted = true;
                        break;
                    }
                    Utils.waitSeconds(2);

                    //repeat looking for result
                    log.info("Repeat looking for result...");
                    statusHelper.getStatus(eventHelper.getEvent().getEventRef(), endPoint.toUpperCase());
                    results = statusHelper.getTextContentOfNode("//event/Markets/Market/Name[text()='" + marketName +
                            "']/../Selections/Selection/Name[text()='" + selectionName + "']/../Result");
                }

                if (!isSelectionResulted) {
                    statusHelper.printStatus();
                    Assert.fail("Selection=" + selectionName + " of market=" + marketName + " didnt get resutled");
                } else if (results.get(0).equalsIgnoreCase(expectedResult)) {
                    log.info("Result for selection=" + selectionName + " at market=" + marketName + " Actual result=" +
                            results.get(0) + " Expected Result=" + expectedResult);
                } else {
                    statusHelper.printStatus();
                    Assert.fail("Result mismatch for selection=" + selectionName + " at market=" + marketName + " Actual result=" +
                            results.get(0) + " Expected Result=" + expectedResult);
                }
            }
        }
    }
    
    private String assignSelectionName(Selection selection, String endPoint){
        switch (selection.getName()) {
            case "|A|":
                if (endPoint.equalsIgnoreCase("Openbet")){
                    return "|" + eventHelper.getPartA() + "|";
                } else {
                    return eventHelper.getPartA();
                }
            case "|B|":
                if (endPoint.equalsIgnoreCase("Openbet")){
                    return "|" + eventHelper.getPartB() + "|";
                } else {
                    return eventHelper.getPartB();
                }
            default:
                return selection.getName();
        }
    }

    protected void verifyWalletUpdateAfterBetsPlacement(Customer customer, SportsbookHelper sportsbookHelper) throws JSONException {
        Utils.waitSeconds(1);
        Assert.assertEquals("Balance not updated properly after bets placement betId=" + customer.placeBetsResponse.getBetSlipId(),
                customer.getBalance().subtract(customer.placeBetsResponse.getBetPlacementResult().get(0).getTotalStake()),
                sportsbookHelper.getWalletBalance()
        );
        customer.setBalance(sportsbookHelper.getWalletBalance());
    }

    /**
     * Selects a single selection per Event for the given market
     * @param mktType given market type
     * @param sportsbookApiInteractor sportsbook session
     * @return ArrayList with selections
     * @throws IOException
     */
    protected List<generated.ats.sportsbook.punter.dto.Selection> getSingleSelectionPerEvent(String mktType,
            SportsbookHelper sportsbookApiInteractor) throws IOException {
        List<generated.ats.sportsbook.punter.dto.Selection> selections = new ArrayList<>();
        for (Event event : eventHelper.getEvents()) {
            for (generated.ats.sportsbook.punter.dto.Market market : sportsbookApiInteractor.getEvent(
                    Integer.valueOf(event.getEventRef())).getMarkets()
                    ) {
                if (market.getType().equalsIgnoreCase(mktType)) { //Checks if the market is the required one
                    selections.add(market.getSelection().get(0));
                    break;
                }
            }
        }
        return selections;
    }

}