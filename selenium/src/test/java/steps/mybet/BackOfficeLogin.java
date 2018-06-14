package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import decoders.Decoder;
import decoders.DecoderManager;
import other.Constants;
import other.SeleniumTestTemplate;
import util.Utils;

public class BackOfficeLogin extends SeleniumTestTemplate {

    @Given("^a user is on the CMS site$")
    public void a_user_is_on_the_CMS_site() throws Throwable {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        driver = initDriver(decoder.decodePunterUrl("MYBET_CMS"));
    }

    @When("^user enters invalid credentials$")
    public void user_enters_invalid_credentials() throws Throwable {
        driver.findElement(By.cssSelector("input[label='Username']")).sendKeys("John");
        driver.findElement(By.cssSelector("input[label='Password']")).sendKeys("Smith");
    }

    @When("^user clicks on login button$")
    public void user_clicks_on_login_button() throws Throwable {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("^an error message is displayed: “Invalid Credentials”$")
    public void an_error_message_is_displayed_Invalid_Credentials() throws Throwable {
        Utils.waitSeconds(2);
        Assert.assertTrue("Error msg not displayed", driver.findElement(By.cssSelector("div[id='modalWin'] div:nth-of-type(3)")).isDisplayed());
    }

    @When("^user enters valid credentials$")
    public void user_enters_valid_credentials() throws Throwable {
        driver.findElement(By.cssSelector("input[label='Username']")).sendKeys(Constants.MYBET_BACKOFFICE_USER);
        driver.findElement(By.cssSelector("input[label='Password']")).sendKeys(Constants.MYBET_BACKOFFICE_PASSWORD);
    }

    @Then("^the user is successfully logged in And redirected to the CMS backoffice$")
    public void the_user_is_successfully_logged_in_And_redirected_to_the_CMS_backoffice() throws Throwable {
        Assert.assertTrue("Login failed", driver.findElement(By.cssSelector("a[class='logout btn']")).isDisplayed());
    }


}
