package steps.baba;

import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import other.SeleniumTestTemplate;

public class Exclusion extends SeleniumTestTemplate {

    @Given("^user goes to the limits/exclusion section$")
    public void user_goes_to_the_limits_exclusion_section() throws Throwable {
        moveOver(By.id("user-logged-in-account"));
        driver.findElement(By.id("accounts-option")).click();
        driver.findElement(By.id("my-self-exclusion-tab")).click();
    }

    @When("^user enters some limit details$")
    public void user_enters_some_limit_details(Map<String, String> limitsData) throws Throwable {

        driver.findElement(By.id("newDailyDepositLimit")).clear();
        driver.findElement(By.id("newDailyDepositLimit")).sendKeys(limitsData.get("Daily Deposit Limit"));

        driver.findElement(By.id("newWeeklyDepositLimit")).clear();
        driver.findElement(By.id("newWeeklyDepositLimit")).sendKeys(limitsData.get("Weekly Deposit Limit"));

        driver.findElement(By.id("newMonthlyDepositLimit")).clear();
        driver.findElement(By.id("newMonthlyDepositLimit")).sendKeys(limitsData.get("Monthly Deposit Limit"));

        driver.findElement(By.id("submit-deposit-limit")).click();
    }

    @Then("^A message is displayed saying Changes Saved$")
    public void a_message_is_displayed_saying_Changes_Saved() throws Throwable {
        Assert.assertFalse(driver.findElement(By.id("dailyDepositLimitInformation")).getText().isEmpty());
        Assert.assertFalse(driver.findElement(By.id("currentWeeklyDepositLimit")).getText().isEmpty());
        Assert.assertFalse(driver.findElement(By.id("currentMonthlyDepositLimit")).getText().isEmpty());
    }

}
