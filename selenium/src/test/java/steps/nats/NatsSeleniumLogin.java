package steps.nats;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import other.Constants;
import other.SeleniumTestTemplate;


public class NatsSeleniumLogin extends SeleniumTestTemplate {

    private final Decoder decoder = DecoderManager.getManager().getDecoder();

    private String page;

    public NatsSeleniumLogin() throws DecoderConfigException {
    }

    @Given("^User is on the NATs login page$")
    public void userIsOnTheNatsClient() throws Throwable {
        driver = initDriver(decoder.decodeNatsUrl((Constants.CUSTOMER_IN_TEST)));
    }

    @When("^User enters valid credentials$")
    public void user_enters_valid_credentials() throws Throwable {
        driver.findElement(By.id("userName")).clear();
        driver.findElement(By.id("userName")).sendKeys("test1");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test1");
    }

    @When("^User enters invalid credentials$")
    public void user_enters_invalid_credentials() throws Throwable {
        driver.findElement(By.id("userName")).clear();
        driver.findElement(By.id("userName")).sendKeys("John");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Smith");
    }

    @When("^User clicks on Login button$")
    public void user_clicks_on_Login_button() throws Throwable {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("^Message displayed Invalid credentials entered$")
    public void message_displayed_Invalid_credentials_entered() throws Throwable {
        Assert.assertFalse("Login failed", exists(By.cssSelector("li[class='ant-menu-item logout']")));
    }

    @Then("^Message displayed Login Successfully$")
    public void message_displayed_Login_Successfully() throws Throwable {
        Assert.assertTrue("Login failed", exists(By.cssSelector("li[class='ant-menu-item logout']")));
    }

    @When("^User clicks on \"([^\"]*)\"$")
    public void userClicksOnPage(String page) throws Throwable {
        this.page = page;
        switch (page) {
            case "Global Config":
            case "Sportsbook Settings":
            case "Segments":
            case "Global Event Limits":
                driver.findElement(By.xpath("//span[text()='Configuration']")).click();
                break;
            case "Match Format":
            case "Node Settings":
            case "Feed Management":
            case "Mapping":
            case "Outrights Types":
            case "Odds Ladder":
                driver.findElement(By.xpath("//span[text()='Operations']")).click();
                break;
            case "Risk Manager":
            case "Bet Ticker":
            case "Overask":
            case "Punter Limits":
            case "Same Combination Bets":
                driver.findElement(By.xpath("//span[text()='Risk-Mgmt']")).click();
                break;
            case "Alerts Ticker":
            case "Alerts Config":
                driver.findElement(By.xpath("//span[text()='Alerts']")).click();
                break;
            case "Event Codes Configuration":
            case "Event Codes Assignment":
            case "Selection Codes Configuration":
            case "Competition Priorities":
                driver.findElement(By.xpath("//span[text()='Retail Management']")).click();
                break;
            case "Campaign Manager":
            case "Campaign Template":
            case "Bulk Awards":
                driver.findElement(By.xpath("//span[text()='Bonuses']")).click();
                break;
            case "Account Management":
            case "Role Permissions":
                driver.findElement(By.xpath("//span[text()='User']")).click();
                break;
            case "Event Logs":
            case "User Logs":
                driver.findElement(By.xpath("//span[text()='Audit']")).click();
                break;
            default:
                break;
        }
        driver.findElement(By.xpath("//span[text()='" + page + "']")).click();
    }

    @Then("^the page gets loaded successfully$")
    public void thePageGetsLoadedSuccessfully() throws Throwable {
        Assert.assertTrue("Page didn't load correctly",
                exists(By.xpath("//span[text()='" + page + "']")));
    }

    @Given("^the a \"([^\"]*)\" user is logged onto the Nats client$")
    public void theAUserIsLoggedOntoTheNatsClient(String browser) throws Throwable {
        Constants.browserName = browser;
        userIsOnTheNatsClient();
        user_enters_valid_credentials();
        user_clicks_on_Login_button();
    }

}
