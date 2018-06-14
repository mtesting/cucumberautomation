package steps.baba;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import other.SeleniumTestTemplate;
import util.NumberUtil;
import util.Utils;

public class Deposit extends SeleniumTestTemplate {

    private Customer account;

    public Deposit(Customer account) {
        this.account = account;
    }

    @When("^User clicks on Deposit$")
    public void user_clicks_on_Deposit() throws Throwable {
        driver.findElement(By.id("fund-action")).click();
    }

    @And("^User enters a deposit amount \"([^\"]*)\"$")
    public void user_enters_a_deposit_amount(String amount) throws Throwable {
        account.setBalance(driver.findElement(By.cssSelector("div[id='fund-type'] span:nth-child(2)")).getText().substring(1));
        account.deposit(amount);

        driver.findElement(By.cssSelector("input[name='depositAmount']")).clear();
        driver.findElement(By.cssSelector("input[name='depositAmount']")).sendKeys(amount);
    }

    @And("^Clicks on Continue To Payment Method$")
    public void clicks_on_Continue_To_Payment_Method() throws Throwable {
        driver.findElement(By.id("depositButtonSubmit")).click();
    }

    @And("^pays with a valid method$")
    public void pays_with_a_valid_method() throws Throwable {
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='ilixium-iframe']")));

        driver.findElement(By.cssSelector("input[data-validate='cardno']")).sendKeys("5363 2195 6763 6671");
        driver.findElement(By.cssSelector("input[data-validate='valid']")).sendKeys("02/12");
        driver.findElement(By.cssSelector("input[data-validate='expiry']")).sendKeys("02/22");
        driver.findElement(By.cssSelector("input[data-validate='name']")).sendKeys("Lince Iberico");

        javascriptClick(By.cssSelector("div[class='button proceed']"));

        Utils.waitSeconds(1);
        driver.findElement(By.cssSelector("input[data-validate='cvv']")).sendKeys("312");
        javascriptClick(By.cssSelector("div[class='button proceed']"));
        Utils.waitSeconds(5);

        javascriptClick(By.cssSelector("input[type='submit']"));
    }

    @Then("^Message displayed Your deposit was successful$")
    public void message_displayed_Your_deposit_was_successful() throws Throwable {
        Utils.waitSeconds(10);
        driver.switchTo().defaultContent();
        Assert.assertEquals("Cash balance not updated properly", account.getBalance(),
                NumberUtil.parseToBigDecimal(driver.findElement(By.cssSelector("div[id='fund-type'] span:nth-child(2)")).getText().substring(1)));
    }

}
