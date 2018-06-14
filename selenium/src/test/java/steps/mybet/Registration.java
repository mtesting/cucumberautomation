package steps.mybet;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.TestUserData;
import entities.TestUserDataService;
import other.SeleniumTestTemplate;

import static org.junit.Assert.assertEquals;

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
        waitFor(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::input[1]"));
        driver.findElement(By.cssSelector("input[id='Email Address']")).clear();
        driver.findElement(By.cssSelector("input[id='Email Address']")).sendKeys(testUser.geteMailAddress());

        driver.findElement(By.id("Username")).clear();
        driver.findElement(By.id("Username")).sendKeys(testUser.getUserName());

        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[id='Confirm Password']")).clear();
        driver.findElement(By.cssSelector("input[id='Confirm Password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[id='Mobile number (for mobile bonuses)']")).clear();
        driver.findElement(By.cssSelector("input[id='Mobile number (for mobile bonuses)']")).sendKeys(testUser.getTelephoneNumber());

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        waitFor(By.cssSelector("input[id='Enter first name']"));
        driver.findElement(By.cssSelector("input[id='Enter first name']")).clear();
        driver.findElement(By.cssSelector("input[id='Enter first name']")).sendKeys(testUser.getFirstName());

        driver.findElement(By.cssSelector("input[id='Enter last name']")).clear();
        driver.findElement(By.cssSelector("input[id='Enter last name']")).sendKeys(testUser.getLastName());

        selectElement(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::select[1]"), testUser.getBirthDateDay());
        //select = new Select(driver.findElement(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::select[2]")));
        //select.selectByVisibleText(testUser.getBirthDateMonth());
        selectElement(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::select[3]"), testUser.getBirthDateYear());

        driver.findElement(By.id("Street")).clear();
        driver.findElement(By.id("Street")).sendKeys(testUser.getStreet());

        driver.findElement(By.id("Number")).clear();
        driver.findElement(By.id("Number")).sendKeys(testUser.getHouseNumber());

        driver.findElement(By.cssSelector("input[id='Post Code']")).clear();
        driver.findElement(By.cssSelector("input[id='Post Code']")).sendKeys(testUser.getZipCode());

        driver.findElement(By.id("City")).clear();
        driver.findElement(By.id("City")).sendKeys(testUser.getCity());

        driver.findElement(By.cssSelector("input[id='Place of Birth']")).clear();
        driver.findElement(By.cssSelector("input[id='Place of Birth']")).sendKeys(testUser.getCity());
    }

    @Then("^A Confirmation message is displayed$")
    public void a_confirmation_message_is_displayed() throws Throwable {
        assertEquals("Successful message not displayed",
                "Please check your mailbox", driver.findElement(By.xpath("//*[contains(text(), 'Please check your mailbox')]")).getText());
    }

    @Then("^A duplicated data message is displayed$")
    public void a_duplicated_data_message_is_displayed() throws Throwable {
        assertEquals("Your desired username already exists. Please choose another one.",
                driver.findElement(By.xpath("//*[contains(text(), 'Your desired username already exists. Please choose another one.')]")).getText());
    }

    @When("^user tries to register with the same email address \"([^\"]*)\"$")
    public void user_tries_to_register_with_the_same_email_address(String duplicatedEmail) throws Throwable {
        user_clicks_on_Register();
        waitFor(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::input[1]"));
        driver.findElement(By.id("Email Address")).clear();
        driver.findElement(By.id("Email Address")).sendKeys(duplicatedEmail);

        driver.findElement(By.id("Username")).clear();
        driver.findElement(By.id("Username")).sendKeys(testUser.getUserName());

        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(testUser.getPassword());

        driver.findElement(By.id("Confirm Password")).clear();
        driver.findElement(By.id("Confirm Password")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[id='Mobile number (for mobile bonuses)']")).clear();
        driver.findElement(By.cssSelector("input[id='Mobile number (for mobile bonuses)']")).sendKeys(testUser.getTelephoneNumber());

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Then("^display a message \"([^\"]*)\"$")
    public void display_a_message(String errorMsg) throws Throwable {
        waitFor(By.className("error-box"));
        //Assert.assertEquals(errorMsg, driver.findElement(By.className("error-box")).getText());
        Assert.assertThat(driver.findElement(By.className("error-box")).getText(), CoreMatchers.containsString(errorMsg));
    }

    @When("^user tries to register with the same username \"([^\"]*)\"$")
    public void user_tries_to_register_with_the_same_username(String duplicatedUsername) throws Throwable {
        user_clicks_on_Register();
        waitFor(By.xpath("id('register-page')//div[@class='cell-1'][1]/descendant::input[1]"));
        driver.findElement(By.id("Email Address")).clear();
        driver.findElement(By.id("Email Address")).sendKeys(testUser.geteMailAddress());

        driver.findElement(By.id("Username")).clear();
        driver.findElement(By.id("Username")).sendKeys(duplicatedUsername);

        driver.findElement(By.id("Password")).clear();
        driver.findElement(By.id("Password")).sendKeys(testUser.getPassword());

        driver.findElement(By.id("Confirm Password")).clear();
        driver.findElement(By.id("Confirm Password")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[placeholder='Enter number']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter number']")).sendKeys(testUser.getTelephoneNumber());

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @And("^User ticks the confirmation checkbox$")
    public void user_ticks_the_confirmation_checkbox() throws Throwable {
        javascriptClick(By.id("check1"));
    }

    @And("^User clicks on Next Step button$")
    public void user_clicks_on_Next_Step_button() throws Throwable {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

}
