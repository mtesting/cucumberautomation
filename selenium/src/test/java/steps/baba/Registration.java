package steps.baba;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.TestUserData;
import entities.TestUserDataService;
import other.SeleniumTestTemplate;

public class Registration extends SeleniumTestTemplate {

    private final TestUserDataService userDataService = new TestUserDataService();
    private final TestUserData testUser = userDataService.generateForMtLicense();

    @When("^User clicks on Register$")
    public void user_clicks_on_Register() throws Throwable {
        driver.findElement(By.id("registerBtn")).click();
        //driver.findElement(By.cssSelector("[data-test-headernotloggedin-registerbtn]")).click();
    }

    @And("^User enters valid data")
    public void user_enters_valid_data() throws Throwable {
        driver.findElement(By.cssSelector("input[name='email")).clear();
        driver.findElement(By.cssSelector("input[name='email")).sendKeys(testUser.geteMailAddress());

        driver.findElement(By.cssSelector("input[name='reg_username']")).clear();
        driver.findElement(By.cssSelector("input[name='reg_username']")).sendKeys(testUser.getUserName());

        driver.findElement(By.cssSelector("input[name='reg_password']")).clear();
        driver.findElement(By.cssSelector("input[name='reg_password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[name='reg_password2']")).clear();
        driver.findElement(By.cssSelector("input[name='reg_password2']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[name='firstname']")).clear();
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys(testUser.getFirstName());

        driver.findElement(By.cssSelector("input[name='surname']")).clear();
        driver.findElement(By.cssSelector("input[name='surname']")).sendKeys(testUser.getLastName());

        driver.findElement(By.cssSelector("input[name='mobile']")).clear();
        driver.findElement(By.cssSelector("input[name='mobile']")).sendKeys(testUser.getTelephoneNumber());

        //Select select = new Select(driver.findElement(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::select[1]")));
        //select.selectByVisibleText(testUser.getBirthDateDay());
        //select = new Select(driver.findElement(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::select[2]")));
        //select.selectByVisibleText(testUser.getBirthDateMonth());
        Select select = new Select(driver.findElement(By.id("reg_year")));
        select.selectByVisibleText(testUser.getBirthDateYear());

        driver.findElement(By.cssSelector("input[name='addr1']")).clear();
        driver.findElement(By.cssSelector("input[name='addr1']")).sendKeys(testUser.getStreet());

        driver.findElement(By.cssSelector("input[name='addr2']")).clear();
        driver.findElement(By.cssSelector("input[name='addr2']")).sendKeys(testUser.getHouseNumber());

        driver.findElement(By.cssSelector("input[name='city']")).clear();
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys(testUser.getCity());

        driver.findElement(By.cssSelector("input[name='securityAnswer']")).clear();
        driver.findElement(By.cssSelector("input[name='securityAnswer']")).sendKeys(testUser.getCountry());
    }

    @And("^User ticks the confirmation checkbox$")
    public void user_ticks_the_confirmation_checkbox() throws Throwable {
        javascriptClick(By.id("termsAndConditions"));
    }

    @And("^User clicks on Next Step button$")
    public void user_clicks_on_Next_Step_button() throws Throwable {
        driver.findElement(By.cssSelector("a[class='bbm-button submit'][type='submit']")).click();
    }

    @Then("^A Confirmation message is displayed$")
    public void a_confirmation_message_is_displayed() throws Throwable {
        waitFor((By.id("loginTitle")));
        Assert.assertTrue("Registration failed", driver.findElement(By.id("loginTitle")).isDisplayed());
    }

    @When("^user tries to register with the same email address \"([^\"]*)\"$")
    public void user_tries_to_register_with_the_same_email_address(String duplicatedEmail) throws Throwable {
        driver.findElement(By.id("registerBtn")).click();
        driver.findElement(By.cssSelector("input[name='email")).clear();
        driver.findElement(By.cssSelector("input[name='email")).sendKeys("autotest2@amelco.co.uk");

        driver.findElement(By.cssSelector("input[name='reg_username']")).clear();
        driver.findElement(By.cssSelector("input[name='reg_username']")).sendKeys(testUser.getUserName());

        driver.findElement(By.cssSelector("input[name='reg_password']")).clear();
        driver.findElement(By.cssSelector("input[name='reg_password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[name='reg_password2']")).clear();
        driver.findElement(By.cssSelector("input[name='reg_password2']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[name='firstname']")).clear();
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys(testUser.getFirstName());

        driver.findElement(By.cssSelector("input[name='surname']")).clear();
        driver.findElement(By.cssSelector("input[name='surname']")).sendKeys(testUser.getLastName());

        driver.findElement(By.cssSelector("input[name='mobile']")).clear();
        driver.findElement(By.cssSelector("input[name='mobile']")).sendKeys(testUser.getTelephoneNumber());

        //Select select = new Select(driver.findElement(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::select[1]")));
        //select.selectByVisibleText(testUser.getBirthDateDay());
        //select = new Select(driver.findElement(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::select[2]")));
        //select.selectByVisibleText(testUser.getBirthDateMonth());
        Select select = new Select(driver.findElement(By.id("reg_year")));
        select.selectByVisibleText(testUser.getBirthDateYear());

        driver.findElement(By.cssSelector("input[name='addr1']")).clear();
        driver.findElement(By.cssSelector("input[name='addr1']")).sendKeys(testUser.getStreet());

        driver.findElement(By.cssSelector("input[name='addr2']")).clear();
        driver.findElement(By.cssSelector("input[name='addr2']")).sendKeys(testUser.getHouseNumber());

        driver.findElement(By.cssSelector("input[name='city']")).clear();
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys(testUser.getCity());

        driver.findElement(By.cssSelector("input[name='securityAnswer']")).clear();
        driver.findElement(By.cssSelector("input[name='securityAnswer']")).sendKeys(testUser.getCountry());

        javascriptClick(By.id("termsAndConditions"));
        driver.findElement(By.cssSelector("a[class='bbm-button submit'][type='submit']")).click();
    }

    @Then("^display a message \"([^\"]*)\"$")
    public void display_a_message(String errorMsg) throws Throwable {
        scrollIntoView(By.cssSelector("input[name='email"));
        Assert.assertTrue("Registration failed", driver.findElement(By.id("error")).isDisplayed());
    }

}
