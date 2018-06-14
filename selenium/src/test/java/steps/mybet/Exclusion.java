package steps.mybet;

import org.openqa.selenium.By;

import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import other.SeleniumTestTemplate;

public class Exclusion extends SeleniumTestTemplate {

    @Given("^user goes to the limits/exclusion section$")
    public void user_goes_to_the_limits_exclusion_section() throws Throwable {
        moveOver(By.className("outer-bal"));
        driver.findElement(By.cssSelector("[data-test-userdropdown-accountdetails]")).click();
        driver.findElement(By.linkText("Exclusions")).click();
    }

    @When("^user enters some limit details$")
    public void user_enters_some_limit_details(Map<String, String> limitsData) throws Throwable {

        scrollIntoView(By.id("input-Daily Deposit Limit "));
        driver.findElement(By.id("input-Daily Deposit Limit ")).clear();
        driver.findElement(By.id("input-Daily Deposit Limit ")).sendKeys(limitsData.get("Daily Deposit Limit"));

        driver.findElement(By.id("input-Weekly Deposit Limit ")).clear();
        driver.findElement(By.id("input-Weekly Deposit Limit ")).sendKeys(limitsData.get("Weekly Deposit Limit"));

        driver.findElement(By.id("input-Monthly Deposit Limit ")).clear();
        driver.findElement(By.id("input-Monthly Deposit Limit ")).sendKeys(limitsData.get("Monthly Deposit Limit"));

        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Then("^A message is displayed saying Changes Saved$")
    public void a_message_is_displayed_saying_Changes_Saved() throws Throwable {
        driver.findElement(By.xpath("//*[contains(text(), 'Changes Saved')]"));
    }

}
