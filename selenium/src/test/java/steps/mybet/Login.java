package steps.mybet;

import org.apache.log4j.Logger;
import org.junit.Assert;

import PageFactory.MyBetPageFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import other.Constants;

public class Login {

    private MyBetPageFactory myBetPageFactory;
    private Customer account;

    protected static final Logger log = Logger.getLogger(Login.class);

    public Login(MyBetPageFactory myBetPageFactory, Customer account) {
        this.myBetPageFactory = myBetPageFactory;
        this.account = account;
    }

    @Given("^User is on the customer web$")
    public void user_is_on_the_customer_web() throws Throwable {
        myBetPageFactory.initMyBetPageFactory();
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
        myBetPageFactory.setLoginUsername(account.getUsername());
        myBetPageFactory.setLoginPassword(Constants.MYBET_PASSWORD);
    }

    @When("^User clicks on Login button$")
    public void user_clicks_on_Login_button() throws Throwable {
        myBetPageFactory.clickLoginButton();
    }

    @Then("^Message displayed Login Successfully$")
    public void message_displayed_Login_Successfully() throws Throwable {
        Assert.assertTrue("Login failed", myBetPageFactory.isUserLoggedIn());
    }

    @When("^User enters invalid credentials$")
    public void user_enters_invalid_credentials() throws Throwable {
        /*if ("chromeMobile".equalsIgnoreCase(Constants.browserName)) {
            driver.findElement(By.cssSelector("div.field.tablet-show button")).click();
        }*/
        myBetPageFactory.setLoginUsername("John");
        myBetPageFactory.setLoginPassword("Smith1234");
    }

    @Then("^Message displayed Invalid credentials entered$")
    public void message_displayed_Invalid_credentials_entered() throws Throwable {
       myBetPageFactory.isIncorrectLoginMsgDisplayed();
    }

    @When("^User LogOut from the Application$")
    public void user_LogOut_from_the_Application() throws Throwable {
        myBetPageFactory.clickLogoutButton();
    }

    @Then("^Message displayed LogOut Successfully$")
    public void message_displayed_LogOut_Successfully() throws Throwable {
        Assert.assertFalse("Logout failed", myBetPageFactory.isUserLoggedIn());
    }

    @Given("^the User is logged onto the customer Sportsbook$")
    public void the_user_is_logged_onto_the_customer_sportsbook() throws Throwable {
        user_is_on_the_customer_web();
        user_enters_valid_credentials();
        user_clicks_on_Login_button();
        message_displayed_Login_Successfully();
    }

}