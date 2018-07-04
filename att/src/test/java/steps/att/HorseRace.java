package steps.att;

import org.apache.log4j.Logger;
import org.junit.Assert;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import apiLevelInteraction.SportsbookHelper;
import apiLevelInteraction.TraderClientHelper;
import ats.betting.trading.att.ws.scenario.dto.RaceStage;
import att.events.RaceHelper;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import decoders.DecoderConfigException;
import entities.Customer;
import generated.ats.sportsbook.punter.dto.Event;
import generated.ats.sportsbook.punter.dto.Market;
import generated.ats.sportsbook.punter.dto.Selection;
import util.NumberUtil;
import util.Utils;

public class HorseRace extends EventSteps {

    private static final Logger log = Logger.getLogger(HorseRace.class);
    private ArrayList<RaceHelper> racingEvents;
    private final SportsbookHelper sportsbook;
    private Customer customer;
    private double deduction = 0.5;
    private double betSelectionOdds;
    private String betsResult;
    private String betPriceType;
    private String betMarketType;
    private String betType;

    public HorseRace(SportsbookHelper sportsbook, Customer customer, ArrayList<RaceHelper> racingEvents) throws DecoderConfigException {
        this.sportsbook = sportsbook;
        this.customer = customer;
        this.racingEvents = racingEvents;
    }

    @When("^user launch HR event$")
    public void LaunchHREvent() {
        for (RaceHelper racingEvent : racingEvents) {
            racingEvent.launchEvent();
            Utils.waitSeconds(12);
        }
    }

    @When("^send PA update for Off$")
    public void PAOffmessage() {
        for (RaceHelper racingEvent : racingEvents) {
            racingEvent.sendFeedUpdates("horses", RaceStage.OFF, 1, "price");
            Utils.waitSeconds(20);
        }
    }

    @When("^send PA update for finish$")
    public void PAFinishMessage() {
        for (RaceHelper racingEvent : racingEvents) {
            racingEvent.sendFeedUpdates("horses", RaceStage.FINISHED, 3, "finish");
            Utils.waitSeconds(20);
        }
    }

    @When("^send PA update for Result$")
    public void PAResultMessage() {
        for (RaceHelper racingEvent : racingEvents) {
            racingEvent.sendFeedUpdates("horses", RaceStage.RESULT, 4, "result");
            Utils.waitSeconds(20);
        }
    }

    @Then("^results are updated in ATS database$")
    public void verifyHRResultInDB() throws DecoderConfigException, SQLException {
        for (RaceHelper racingEvent : racingEvents) {
            racingEvent.validateHorseRaceResultsinDB("horses");
        }
    }

    @When("^user runs the event E2E$")
    public void userRunsTheEventE2E() throws Throwable {
        PAOffmessage();
        PAFinishMessage();
        PAResultMessage();
    }

    private BigDecimal calculatePayBack(){
        switch (betsResult) {
            case "W": //TODO do calculation without checking ATS, we already know the odds. How to solve when odds get increased?
                return customer.getBalance().add(
                        customer.placeBetsResponse.getBetPlacementResult().get(0).getPotentialPayout());
            case "L":
                return customer.getBalance();
            case "V":
                return customer.getBalance().add(
                        customer.placeBetsResponse.getBetPlacementResult().get(0).getTotalStake());
            default:
                throw new RuntimeException("Invalid value for bet results, expected W, L or V");
        }
    }

    @And("^the wallets balance updated$")
    public void theWalletsBalanceUpdated() throws Throwable {
        log.info("Checking wallet after bets settlement..");
        Utils.waitSeconds(5);

        switch (betsResult) {
            case "W":
                NumberUtil.assertAreEqual("Balance not updated properly",
                        calculatePayBack(), sportsbook.getWalletBalance()
                );
                break;
            case "L":
                NumberUtil.assertAreEqual("Balance not updated properly",
                        calculatePayBack(),
                        sportsbook.getWalletBalance()
                );
                break;
            case "V":
                NumberUtil.assertAreEqual("Balance not updated properly",
                        calculatePayBack(),
                        sportsbook.getWalletBalance()
                );
                break;
        }

    }

    @And("^user place \"([^\"]*)\" bets on the market \"([^\"]*)\" for HR$")
    public void userPlaceBetsOnTheMarketforHr(String type, String marketType, Map<String, String> betsData) throws Throwable {
        customer.setBalance(sportsbook.getWalletBalance());

        betsResult = betsData.get("outcome");
        betPriceType = betsData.get("priceType");
        betType = betsData.get("betType");
        betMarketType = marketType;

        List<Selection> selections = new ArrayList<>();
        String horseName = racingEvents.get(0).getHorseByResult(betsResult, "saveResults");

        Utils.waitSeconds(7);

        switch (type) {
            case "SINGLE":
                for (Market market : sportsbook.getEvent(Integer.valueOf(racingEvents.get(0).getEvent().getEventRef())).getMarkets()) {
                    if (market.getType().equalsIgnoreCase(marketType)) { //Checks if the market is the required one
                        for (Selection selection : market.getSelection()) {
                            if (horseName.contains(selection.getName())) {
                                selections.add(selection);
                                if ("LP".equalsIgnoreCase(betPriceType)) {
                                    betSelectionOdds = Double.parseDouble(selection.getOdds().getDec());
                                }

                            }
                        }
                    }
                }
                break;
            case "DOUBLE":
                for (RaceHelper racingEvent : racingEvents) {
                    for (Market market : sportsbook.getEvent(Integer.valueOf(racingEvent.getEvent().getEventRef())).getMarkets()) {
                        if (market.getType().equalsIgnoreCase(marketType)) { //Checks if the market is the required one
                            for (Selection selection : market.getSelection()) {
                                if (horseName.contains(selection.getName())) {
                                    selections.add(selection);
                                }
                            }
                        }
                    }
                }
                break;
        }

        customer.placeBetsResponse = sportsbook.placeBet(selections, type, betType, betPriceType);
        assertBetPlacement(customer.placeBetsResponse);

        Utils.waitSeconds(10);
        NumberUtil.assertAreEqual("Balance not updated properly after bets placement betId=" + customer.placeBetsResponse.getBetSlipId(),
                customer.getBalance().subtract(customer.placeBetsResponse.getBetPlacementResult().get(0).getTotalStake()),
                sportsbook.getWalletBalance()
        );
        customer.setBalance(customer.getBalance().subtract(customer.placeBetsResponse.getBetPlacementResult().get(0).getTotalStake()));
    }

    @When("^the race results are saved in ats$")
    public void save_race_result() {
        for (RaceHelper racingEvent : racingEvents) {
            Assert.assertTrue("Error saving race results for event=" + racingEvent.getEvent().getEventRef(),
                    racingEvent.saveHorseRaceResults(racingEvent.getEvent().getEventRef(), "saveResults"));
        }
    }

    @When("^the event settle button is clicked$")
    public void settle_race_event() throws Throwable {
        //Potential payout update after the race has finished in case price has changed or bet was placed as SP
        for (generated.ats.betsync.betcatcher.dto.Bet bet : sportsbook.getOpenBets().getBet()) {
            if (customer.placeBetsResponse.getBetSlipId().equalsIgnoreCase(bet.getBetSlipId())) {
                customer.placeBetsResponse.getBetPlacementResult().get(0).setPotentialPayout(
                        NumberUtil.parseToBigDecimal(Double.toString(bet.getPotentialPayout()))
                );
                log.info("Update bet PotentialPayout="+bet.getPotentialPayout());
            }
        }

        Utils.waitSeconds(60);
        if ("SP".equalsIgnoreCase(betPriceType)) {
            for (Market market : sportsbook.getEvent(Integer.valueOf(racingEvents.get(0).getEvent().getEventRef())).getMarkets()) {
                if ("WEW".equalsIgnoreCase(market.getType())) {
                    for (Selection selection : market.getSelection()) {
                        if ("Tiger Would".equalsIgnoreCase(selection.getName())) {
                            betSelectionOdds = Double.parseDouble(selection.getOdds().getDec());
                        }
                    }
                }
            }

        }

        for (RaceHelper racingEvent : racingEvents) {
            Assert.assertTrue("Race settlement failed", racingEvent.settleRaceEvent(racingEvent.getEvent().getEventRef()));
        }
    }

    @And("^there is a deduction of \"([^\"]*)\" applied on that selection$")
    public void thereIsADeductionOfAppliedOnThatSelection(String deduction) throws Throwable {
        TraderClientHelper traderClient = new TraderClientHelper();
        this.deduction = Double.parseDouble(deduction);
        for (RaceHelper racingEvent : racingEvents) {
            for (Market market : sportsbook.getEvent(Integer.valueOf(racingEvent.getEvent().getEventRef())).getMarkets()) {
                if (market.getType().equalsIgnoreCase(betMarketType)) {
                    traderClient.betAmendment(betPriceType, (int) (Double.parseDouble(deduction) * 100), market.getId());
                }
            }
        }
    }

    @Then("^the payout would be calculated from the stake, odds and deduction$")
    public void thePayoutWouldBeCalculatedFromTheStakeOddsAndDeduction() throws Throwable {
        log.info("Checking wallet after bets settlement..");
        Utils.waitSeconds(5);

        double delta = 0.015;
        BigDecimal stake = customer.placeBetsResponse.getBetPlacementResult().get(0).getTotalStake();
        BigDecimal odd = BigDecimal.valueOf(betSelectionOdds).subtract(BigDecimal.valueOf(1));
        BigDecimal deductionValue = BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(deduction));
        stake = "EACH_WAY".equalsIgnoreCase(betType) ? stake.round(MathContext.DECIMAL32)
                .divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_UP) : stake;
        BigDecimal winPayout = stake.multiply((odd.multiply(deductionValue)).add(BigDecimal.valueOf(1))).setScale(2, RoundingMode.CEILING);
        if ("EACH_WAY".equalsIgnoreCase(betType)) {
            odd = odd.multiply(BigDecimal.valueOf(0.25));
            winPayout = winPayout.add(stake.multiply((odd.multiply(deductionValue)).add(BigDecimal.valueOf(1)))).setScale(2, RoundingMode.CEILING);
        }

        switch (betsResult) {
            case "W":
                Assert.assertEquals(
                        customer.getBalance().add(winPayout).doubleValue(),
                        sportsbook.getWalletBalance().doubleValue(),
                        delta
                );
                break;
            case "L":
                NumberUtil.assertAreEqual("Balance not updated properly",
                        customer.getBalance(),
                        sportsbook.getWalletBalance()
                );
                break;
            case "V":
                NumberUtil.assertAreEqual("Balance not updated properly",
                        customer.getBalance().add((customer.placeBetsResponse.getBetPlacementResult().get(0).getTotalStake())),
                        sportsbook.getWalletBalance()
                );
                break;
        }
    }

    @Given("^user is able to create (\\d+) HR event$")
    public void userIsAbleToCreateHREvent(int numberOfEvents) throws Throwable {
        RaceHelper racingEvent;
        for (int x = 0; x < numberOfEvents; x++) {
            racingEvent = new RaceHelper();
            racingEvent.createRaceMeeting();
            if (numberOfEvents > 1) {
                Utils.waitSeconds(61);
            }
            racingEvents.add(racingEvent);
        }
        LaunchHREvent();
    }

    @And("^the LP flag is set to true$")
    public void theLPFlagIsSetToTrue() throws Throwable {
        racingEvents.get(0).setLpFlag(String.valueOf(sportsbook.getEvent(
                Integer.valueOf(racingEvents.get(0).getEvent().getEventRef())).getMarkets().get(0).getId()));
    }

    @And("^the place term is set to \"([^\"]*)\" places$")
    public void updatePlaceTerm(String noOfPlace) throws Throwable { //TODO method not being used, delete it?
        racingEvents.get(0).updateEachWay(sportsbook.getEvent(
                Integer.valueOf(racingEvents.get(0).getEvent().getEventRef())).getMarkets().get(0).getId(), noOfPlace);
    }

    @And("^selections odds get higher$")
    public void selectionsOddsGetHigher() {
        RaceHelper race = racingEvents.get(0);
        race.increaseHorseOdds("horses");

        /*
        Map<String, SimpleFraction> newOdds = race.getHorseOddsFromExcel(Constants.HR_INPUT_EXCEL, "horses");
        log.info("New odds:");
        for(Map.Entry<String, SimpleFraction> entry : newOdds.entrySet())
            log.info("\t" + entry.getKey() + "\t" + entry.getValue().getNumerator() + "/" + entry.getValue().getDenominator());
        */
    }

    @And("^race number of places gets lower$")
    public void raceNumberOfPlacesGetsLower() throws Throwable {
        RaceHelper race = racingEvents.get(0);
        Event event = sportsbook.getEvent(Integer.valueOf(racingEvents.get(0).getEvent().getEventRef()));

        int places = 1;
        Market market = event.getMarkets().get(0);
        log.info("Setting market '" + market.getName() + "' places to " + places);
        race.updateEachWay(market.getId(), Integer.toString(places));
        race.updateHorsePlaces("horses", places);
    }

    @And("^user successfully place random bets for HR$")
    public void userSuccessfullyPlaceRandomBetsForHR(Map<String, String> betsData) throws Throwable {
        customer.setBalance(sportsbook.getWalletBalance());

        customer.placeBetsResponse = sportsbook.placeRandomBets(betsData.get("betType"), betsData.get("mktType"),
                betsData.get("winType"), betsData.get("priceType"), sportsbook.getEvent(Integer.valueOf(racingEvents.get(0).getEvent().getEventRef())));

        EventSteps.assertBetPlacement(customer.placeBetsResponse);

        Utils.waitSeconds(1);
        NumberUtil.assertAreEqual("Balance not updated properly after bets placement betId=" + customer.placeBetsResponse.getBetSlipId(),
                customer.getBalance().subtract(customer.placeBetsResponse.getBetPlacementResult().get(0).getTotalStake()),
                sportsbook.getWalletBalance()
        );
        customer.setBalance(sportsbook.getWalletBalance());
    }

}

