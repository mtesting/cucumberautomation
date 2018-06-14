package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import decoders.Decoder;
import decoders.DecoderManager;
import entities.Customer;
import other.SeleniumTestTemplate;
import util.NumberUtil;
import util.Utils;

public class BetSettlement extends SeleniumTestTemplate {

    private String betRefNumber;
    private String betOutcome;
    private Customer customer;

    public BetSettlement(Customer customer) {
        this.customer = customer;
    }

    @Given("^user has placed a \"([^\"]*)\" bet$")
    public void user_has_placed_a_bet(String betType) throws Throwable {
        customer.setBalance(driver.findElement(By.cssSelector("div[id='transactions'] span:nth-of-type(2)")).getText().substring(1));
        log.info("Initial balance=" + customer.getBalance());
        PlaceBet placeBet = new PlaceBet(customer);

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
        placeBet.user_clicks_on_selections(selections);
        placeBet.user_enters_a_bet_amount(betType, stake);
        placeBet.user_clicks_on_Place_Bet();
        placeBet.a_Bet_Placed_message_is_displayed();
        betRefNumber = driver.findElement(By.cssSelector("span[class='betId']")).getText().substring(6);
        log.info("Bet ref = " + betRefNumber);
        customer.addNewBet(betRefNumber);

        customer.bets.get(0).setPotentialWinnings(driver.findElement(
                By.cssSelector("div[class='bet-slip-confirmation-view row bet-row confirmation'] li[class='slip-summary__winnings'] span span"))
                        .getText().substring(1));
        log.info("Potential Winnings = " + customer.bets.get(0).getPotentialWinnings());

        customer.bets.get(0).setTotalStake(driver.findElement(
                By.cssSelector("div[class='bet-slip-confirmation-view row bet-row confirmation'] li[class='slip-summary__total-stake'] span span"))
                        .getText().substring(1));
        log.info("Total Stake = " + customer.bets.get(0).getTotalStake());

        log.info("New balance=" + customer.getBalance());
    }

    @When("^the user settles the bet as \"([^\"]*)\"$")
    public void the_user_settles_the_bet_as(String betOutcome) throws Throwable {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        this.betOutcome = betOutcome;
        driver.get(decoder.decodePunterUrl("MYBET_BACKOFFICE"));
        driver.findElement(By.cssSelector("td.w2ui-tb-caption")).click();
        Utils.waitSeconds(1);
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("manager1");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test1");
        driver.findElement(By.name("save")).click();
        waitFor(By.cssSelector("table[title='Search Bets']"));
        //driver.findElement(By.cssSelector("table[title='Search Bets']")).click(); //Other element would receive the click
        javascriptClick(By.cssSelector("table[title='Search Bets']")); //TODO ask for a data-test tag
        driver.findElement(By.id("grid_searchBetsByIdGrid_search_all")).clear();
        driver.findElement(By.id("grid_searchBetsByIdGrid_search_all")).sendKeys(betRefNumber);
        driver.findElement(By.id("grid_searchBetsByIdGrid_search_all")).sendKeys(Keys.RETURN);
        driver.findElement(By.cssSelector("select.bet-results")).click();
        new Select(driver.findElement(By.cssSelector("select.bet-results"))).selectByVisibleText(betOutcome);
        //driver.findElement(By.cssSelector("option[value='"+betOutcome.toUpperCase()+"']")).click();
        driver.findElement(By.id("amendButton")).click();
        Utils.waitSeconds(1);
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        log.debug("Alert text: " + alertText);
        Assert.assertEquals("Save Complete", alertText);
        alert.accept();
        driver.switchTo().defaultContent();
        driver.close();
    }

    @Then("^the user cash balance gets updated properly$")
    public void the_user_cash_balance_gets_updated_properly() throws Throwable {
        Login login = new Login(customer);
        login.the_user_is_logged_onto_the_customer_sportsbook();
        switch (betOutcome.toUpperCase()) {
            case "WIN":
                //customer.deposit(add(customer.getTotalStake().toString());
                customer.deposit(customer.bets.get(0).getPotentialWinnings().toString());
                log.info("Win add=" + customer.bets.get(0).getPotentialWinnings());
                break;
            case "LOSE":
                log.info("Lose substract=" + customer.bets.get(0).getTotalStake());
                break;
            case "VOID - FEED":
                customer.deposit(customer.bets.get(0).getTotalStake().toString());
                log.info("Void add=" + customer.bets.get(0).getTotalStake());
                break;
        }
        BigDecimal actualBalance = NumberUtil.parseToBigDecimal(driver.findElement(By.cssSelector("div[id='transactions'] span:nth-of-type(2)")).getText().substring(1));
        log.info("Actual balance=" + actualBalance);
        Assert.assertEquals("Balance not updated properly", customer.getBalance(), actualBalance);
    }

}
