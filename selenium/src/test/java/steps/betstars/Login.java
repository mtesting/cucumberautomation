package steps.betstars;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import decoders.Decoder;
import decoders.DecoderManager;
import entities.Customer;
import other.Constants;
import other.SeleniumTestTemplate;

public class Login extends SeleniumTestTemplate {

    private Customer account;

    public Login(Customer account) {
        this.account = account;
    }

    @Given("^a \"([^\"]*)\" user using \"([^\"]*)\"$")
    public void a_user_using(String platform, String browser) throws Throwable {
        log.info("Setting property platformType to:" + platform);
        Constants.platformType = platform;
        log.info("Setting property browserName to:" + browser);
        Constants.browserName = browser;
    }

    @When("^User enters valid credentials$")
    public void user_enters_valid_credentials() throws Throwable {
        driver.findElement(By.id("userID")).clear();
        driver.findElement(By.id("userID")).sendKeys(account.getUsername()); //FrancoUAT
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(Constants.MYBET_PASSWORD);
    }

    @When("^User clicks on Login button$")
    public void user_clicks_on_Login_button() throws Throwable {
        clickByActionOn(driver.findElement(By.id("loginBtnTxt")));
    }

    @Then("^Message displayed Login Successfully$")
    public void message_displayed_Login_Successfully() throws Throwable {
        waitFor((By.id("logoutButton")));
    }

    @When("^User enters invalid credentials$")
    public void user_enters_invalid_credentials() throws Throwable {
        driver.findElement(By.id("userID")).clear();
        driver.findElement(By.id("userID")).sendKeys("John");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Test1234");
    }

    @Then("^Message displayed Invalid credentials entered$")
    public void message_displayed_Invalid_credentials_entered() throws Throwable {
        waitFor(By.cssSelector("ul[class='errors alert alert-danger']"));
        Assert.assertTrue("Login failed", driver.findElement(By.cssSelector("ul[class='errors alert alert-danger']")).isDisplayed());
    }

    @When("^User LogOut from the Application$")
    public void user_LogOut_from_the_Application() throws Throwable {
        moveOver(By.className("outer-bal"));
        if (exists(By.id("logoutBtn"))) {
            clickByActionOn(driver.findElement(By.id("logoutBtn")));
        } else {
            javascriptClick(By.id("logoutBtn"));
        }
    }

    @Then("^Message displayed LogOut Successfully$")
    public void message_displayed_LogOut_Successfully() throws Throwable {
        //waitFor(By.id("loginBtn"));
        waitFor(By.cssSelector("div.field.tablet-hide button"));
    }

    @Given("^the User is logged onto the customer Sportsbook$")
    public void the_user_is_logged_onto_the_customer_sportsbook() throws Throwable {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        driver = initDriver(Constants.BETSTARS_RAM_URL);
        user_enters_valid_credentials();
        user_clicks_on_Login_button();
        driver.get(decoder.decodePunterUrl(Constants.CUSTOMER_IN_TEST));
        //user_enters_valid_credentials();
        //user_clicks_on_Login_button();
        waitFor(By.id("accountBalance"));
        account.setBalance(driver.findElement(By.cssSelector("span[id='accountBalance'] a")).getText().substring(1));
    }

}