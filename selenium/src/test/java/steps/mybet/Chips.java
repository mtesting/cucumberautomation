package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import other.Constants;
import other.SeleniumTestTemplate;
import util.NumberUtil;
import util.Utils;

import static org.junit.Assert.assertEquals;

public class Chips extends SeleniumTestTemplate {

    private Customer userAccount;
    private String chipsAmount;

    public Chips(Customer userAccount) {
        this.userAccount = userAccount;
    }

    @When("^User clicks on Chips Buy/sell$")
    public void user_clicks_on_Chips_Buy_sell() throws Throwable {
        moveOver(By.id("transactions"));
        driver.findElement(By.linkText("Buy/sell Chips")).click();
        //driver.findElement(By.cssSelector("[data-test-chips_buy_sell]")).click();
    }

    @And("^User clicks on Buy Chips$")
    public void user_clicks_on_Buy_Chips() throws Throwable {
        javascriptClick(By.xpath("//*[contains(text(), 'Buy Chips')]"));
    }

    @And("^User clicks on Sell Chips$")
    public void user_clicks_on_Sell_Chips() throws Throwable {
        javascriptClick(By.xpath("//*[contains(text(), 'Sell Chips')]"));
    }

    @And("^User enters an amount \"([^\"]*)\"$")
    public void user_enters_an_amount(String amount) throws Throwable {
        userAccount.setBalance(driver.findElement(By.cssSelector("div#transactions span:nth-child(2)")).getText().substring(1)); //Read the account oldBalance
        chipsAmount = amount;
        driver.findElement(By.cssSelector("input[type=\"text\"]")).clear();
        driver.findElement(By.cssSelector("input[type=\"text\"]")).sendKeys(amount);
    }

    @And("^User clicks on Buy Chips orange button$")
    public void user_clicks_on_Buy_Chips_orange_button() throws Throwable {
        driver.findElement(By.xpath("//*[contains(text(), 'Buy Chips')]")).click();
    }

    @And("^User clicks on Sell Chips orange button$")
    public void user_clicks_on_Sell_Chips_orange_button() throws Throwable {
        driver.findElement(By.xpath("//*[contains(text(), 'Sell Chips')]")).click();
    }

    @Then("^Message displayed Your transaction was successful$")
    public void message_displayed_Your_transaction_was_successful() throws Throwable {
        assertEquals("Congratulations! Your transaction was successful.",
                driver.findElement(By.xpath("//*[contains(text(), 'Congratulations! Your transaction was successful.')]")).getText());
    }

    @Then("^display Message \"([^\"]*)\"$")
    public void display_Message(String errorMsg) throws Throwable {
        exists(By.className("error-box"));
        //Assert.assertEquals(errorMsg, driver.findElement(By.className("error-box")).getText());
    }

    @Then("^do not redirect the Punter to the success page$")
    public void do_not_redirect_the_Punter_to_the_success_page() throws Throwable {
        Assert.assertNotEquals("Url mismatch", driver.getCurrentUrl(), Constants.MYBET_DEMO_PUNTER_URL);
    }

    @And("^The account cash balance updated$")
    public void the_account_cash_balance_updated() throws Throwable {
        Utils.waitSeconds(5);
        if (driver.findElement(By.className("inline-element")).getText().contains("Sell")) {
            userAccount.deposit(chipsAmount);
        } else {
            userAccount.subtract(chipsAmount);
        }
        assertEquals("The cash balance was not updated properly", userAccount.getBalance(),
                NumberUtil.parseToBigDecimal(driver.findElement(By.cssSelector("div#transactions span:nth-child(2)")).getText().substring(1)));
    }

}
