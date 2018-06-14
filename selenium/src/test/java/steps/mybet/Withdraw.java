package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import other.SeleniumTestTemplate;
import util.NumberUtil;
import util.Utils;

public class Withdraw extends SeleniumTestTemplate {

    private Customer account;

    public Withdraw(Customer account) {
        this.account = account;
    }

    @When("^User clicks on Withdraw$")
    public void user_clicks_on_Withdraw() throws Throwable {
        account.setBalance(driver.findElement(By.cssSelector("div[id='transactions'] span:nth-of-type(2)")).getText().substring(1));

        moveOver(By.id("transactions"));
        driver.findElement(By.cssSelector("[data-test-withdrawal]")).click();
    }

    @When("^Enters a withdraw amount$")
    public void enters_a_withdraw_amount() throws Throwable {
        driver.findElement(By.cssSelector("input.no-label")).clear();
        Double withdrawAmount = 30.00;
        driver.findElement(By.cssSelector("input.no-label")).sendKeys(withdrawAmount.toString());
        account.subtract(withdrawAmount.toString());
    }

    @When("^continues with the process$")
    public void continues_with_the_process() throws Throwable {
        driver.findElement(By.cssSelector("[data-test-select-amount-continue-withdrawal-btn]")).click();
        driver.findElement(By.cssSelector("[data-test-withdrawal-select-master]")).click();
        driver.findElement(By.cssSelector("a[class='btn btn-orange large inline-block pull-right tablet-s-block']")).click();
    }

    @Then("^A successful message is displayed$")
    public void a_successful_message_is_displayed() throws Throwable {
        Assert.assertTrue(exists(By.id("success")));

        Utils.waitSeconds(2);
        Assert.assertEquals("Cash balance not updated properly", account.getBalance(),
                NumberUtil.parseToBigDecimal(driver.findElement(By.cssSelector("div[id='transactions'] span:nth-of-type(2)")).getText().substring(1)));
    }

}
