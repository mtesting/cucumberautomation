package steps.mybet;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import PageFactory.MyBetPageFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import util.NumberUtil;
import util.Utils;

public class PlaceBet {

    private static final Logger log = Logger.getLogger(PlaceBet.class);
    private MyBetPageFactory myBetPageFactory;
    private Customer userAccount;

    public PlaceBet(MyBetPageFactory myBetPageFactory, Customer userAccount) {
        this.myBetPageFactory = myBetPageFactory;
        this.userAccount = userAccount;
    }

    @When("^User clicks on \"([^\"]*)\" selections$")
    public void user_clicks_on_selections(Integer requestedSelectionsQuantity) throws Throwable {
        userAccount.setBalance(myBetPageFactory.getAccountCashBalance().substring(1));
        int numberOfTips = requestedSelectionsQuantity;

        log.info("Placing bets ...");
        //scrollIntoView(By.xpath(sectionWidget)); //TODO is it required?

        List<WebElement> outcomes = myBetPageFactory.findAvailableSelections();
        int numberOfOutcomes = outcomes.size();
        if (numberOfOutcomes < numberOfTips) {
            numberOfTips = numberOfOutcomes;
        }

        Random random = new Random();
        for (int i = 0; i < numberOfTips; i++) {
            int randomIndex = random.nextInt(numberOfOutcomes);
            WebElement outcome = outcomes.get(randomIndex);
            try {
                myBetPageFactory.clickOnSelection(outcome);
                outcomes.remove(outcome);
                numberOfOutcomes--;
            } catch (WebDriverException e) {
                if (e.getMessage().startsWith("Element is not clickable at point ")) {
                    i--;
                }
            }
        }

        log.info("Submitting bet slip ...");
        if (myBetPageFactory.isBetslipIconized()) {
            myBetPageFactory.clickOnBetslipIcon();
        }

        List<WebElement> tipsOnBetslip = myBetPageFactory.getTipsOnBetslip();
        Assert.assertTrue("No tips on betslip.", tipsOnBetslip.size() > 0);
        Assert.assertTrue("Too many tips on betslip", tipsOnBetslip.size() <= numberOfTips);
    }

    @And("^User enters a \"([^\"]*)\" bet amount \"([^\"]*)\"$")
    public void user_enters_a_bet_amount(String betType, String stake) throws Throwable {
        switch (betType.toUpperCase()) {
            case "SINGLE":
                myBetPageFactory.openSingleBetsView();
                myBetPageFactory.enterStakeInputSINGLE(stake);
                break;
            case "MULTIPLE":
                myBetPageFactory.openAccumulatorBetsView();
                myBetPageFactory.enterStakeInputAccumulator(stake);
                break;
            case "SYSTEM":
                myBetPageFactory.openSystemBetsView();
                myBetPageFactory.enterStakeInputDOUBLE(stake);
                myBetPageFactory.enterStakeInputPATENT(stake);
                myBetPageFactory.enterStakeInputTRIXIE(stake);
                break;
        }

        myBetPageFactory.acceptAllOddsChanges();
    }

    @And("^User clicks on Place Bet$")
    public void user_clicks_on_Place_Bet() throws Throwable {
        userAccount.subtract(myBetPageFactory.getBetTotalStake().substring(1));

        try{
            userAccount.subtract(myBetPageFactory.getBetTaxPercentage().substring(3));
        } catch (NoSuchElementException e){
            log.error("No tax info found when placing bet", e);
        }

        myBetPageFactory.clickPlaceBetButton();
    }

    @Then("^A Bet Placed message is displayed$")
    public void a_Bet_Placed_message_is_displayed() throws Throwable {
        myBetPageFactory.waitForBetslipSuccessMsg();
        log.info("Bet placed.");

        Utils.waitSeconds(1);
        Assert.assertEquals("Cash balance not updated properly", userAccount.getBalance(),
                NumberUtil.parseToBigDecimal(myBetPageFactory.getAccountCashBalance().substring(1)));
    }

    @Then("^An unsuccessful message is displayed$")
    public void an_unsuccessful_message_is_displayed() throws Throwable {
        myBetPageFactory.waitForBetslipErrorMsg();
        log.info("Bet not placed.");
    }

    @Given("^user has placed a \"([^\"]*)\" bet$")
    public void user_has_placed_a_bet(String betType) throws Throwable {
        int selections = 1;
        switch (betType.toUpperCase()) {
            case "SINGLE":
                selections = 1;
                break;
            case "MULTIPLE":
                selections = 2;
                break;
            case "SYSTEM":
                selections = 3;
                break;
        }

        String stake = "1";
        user_clicks_on_selections(selections);
        user_enters_a_bet_amount(betType, stake);
        user_clicks_on_Place_Bet();
        a_Bet_Placed_message_is_displayed();

        String betRef = myBetPageFactory.getBetRefNumber().substring(6);
        log.info("Bet ref = " + betRef);
        userAccount.addNewBet(betRef);

        userAccount.bets.get(0).setPotentialWinnings(myBetPageFactory.getBetPotentialWinnings().substring(1));
        log.info("Potential Winnings = " + userAccount.bets.get(0).getPotentialWinnings());

        //userAccount.bets.get(0).setTotalStake(myBetPageFactory.getBetTotalStake().substring(1));
        //log.info("Total Stake = " + userAccount.bets.get(0).getTotalStake());

        log.info("New balance=" + userAccount.getBalance());
    }

}
