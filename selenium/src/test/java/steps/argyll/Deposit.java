package steps.argyll;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import other.SeleniumTestTemplate;
import util.Utils;

@Deprecated
public class Deposit extends SeleniumTestTemplate {

    @When("^User clicks on Deposit$")
    public void user_clicks_on_Deposit() throws Throwable {
        driver.findElement(By.id("deposit")).click();
    }

    @And("^Clicks on Continue To Payment Method$")
    public void clicks_on_Continue_To_Payment_Method() throws Throwable {
        driver.findElement(By.xpath("//*[contains(text(), 'Continue to Payment Method')]")).click();
        //data-test-deposit-button-continue-to-payment
    }

    @And("^User enters a deposit amount \"([^\"]*)\"$")
    public void user_enters_a_deposit_amount(String amount) throws Throwable {
        waitFor(By.cssSelector("input.has-currency"));
        driver.findElement(By.cssSelector("input.has-currency")).clear();
        driver.findElement(By.cssSelector("input.has-currency")).sendKeys(amount);
        //"id('pea-header')/following::div[@class='deposit-amount-view']//div[contains(@class, 'deposit-amount__select-amount')]//input";
    }

    @And("^pays with a valid method$")
    public void pays_with_a_valid_method() throws Throwable {
        driver.switchTo().frame(driver.findElement(By.id("ilixium-iframe")));
        if (driver.findElement(By.cssSelector("[data-validate='cardno']")).getAttribute("value").isEmpty()) {
            driver.findElement(By.cssSelector("[data-validate='cardno']")).sendKeys("4769 8985 1052 4416");
            driver.findElement(By.cssSelector("[data-validate='valid']")).sendKeys("0915");
            driver.findElement(By.cssSelector("[data-validate='expiry']")).sendKeys("0917");
            driver.findElement(By.cssSelector("[data-validate='name']")).sendKeys("William Brown");
        }
        javascriptClick(By.cssSelector("[class='button proceed']"));
        Utils.waitSeconds(5);
        driver.findElement(By.cssSelector("[data-validate='cvv']")).sendKeys("312");
        javascriptClick(By.cssSelector("[class='button proceed']"));
    }

    @Then("^Message displayed Your deposit was successful$")
    public void message_displayed_Your_deposit_was_successful() throws Throwable {
        Utils.waitSeconds(5);
        String confirmationMsg = driver.findElement(By.id("paymentcomplete")).getText().trim();
        Assert.assertTrue(confirmationMsg.contains("Payment Complete."));

        driver.findElement(By.cssSelector("button[name='close']")).click();

        String confirmationMsg2 = driver.findElement(By.className("c-deposit-confirm")).getText().trim();
        Assert.assertTrue(confirmationMsg2.contains("Your deposit was successful"));
    }

}
