package steps.argyll;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import other.SeleniumTestTemplate;

public class Withdraw extends SeleniumTestTemplate {

    @When("^User clicks on Withdraw$")
    public void user_clicks_on_Withdraw() throws Throwable {
        driver.findElement(By.cssSelector("#deposit")).click();
        moveOver(By.className("c-account-info__menu"));
        driver.findElement(By.cssSelector("[data-test-user-dropdown-withdrawal]")).click();
    }

    @When("^Enters a withdraw amount$")
    public void enters_a_withdraw_amount() throws Throwable {
        driver.findElement(By.cssSelector("input.has-currency")).clear();
        driver.findElement(By.cssSelector("input.has-currency")).sendKeys("10");
    }

    @When("^continues with the process$")
    public void continues_with_the_process() throws Throwable {
        driver.findElements(By.linkText("Withdraw")).get(1).click();
    }

    @Then("^A successful message is displayed$")
    public void a_successful_message_is_displayed() throws Throwable {
        Assert.assertTrue(exists(By.id("success")));
    }

}
