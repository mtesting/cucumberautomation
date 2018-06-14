package steps.argyll;

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
    private final TestUserData testUser = userDataService.generateForBem();

    @When("^User clicks on Register$")
    public void user_clicks_on_Register() throws Throwable {
        driver.findElement(By.cssSelector("[data-test-headerview-register-button]")).click();
        //driver.findElement(By.cssSelector("[data-test-headernotloggedin-registerbtn]")).click();
    }

    @And("^User enters valid data")
    public void user_enters_valid_data() throws Throwable {
        driver.findElement(By.cssSelector("input[placeholder='Enter email address']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter email address']")).sendKeys(testUser.geteMailAddress());

        driver.findElement(By.cssSelector("input[placeholder='Enter desired username']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter desired username']")).sendKeys(testUser.getUserName());

        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[placeholder='Retype password']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Retype password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[placeholder*='Enter number']")).clear();
        driver.findElement(By.cssSelector("input[placeholder*='Enter number']")).sendKeys(testUser.getTelephoneNumber());

        driver.findElement(By.cssSelector("button.btn--secondary--large--with-arrow")).click();

        waitFor(By.cssSelector("input[placeholder='Enter first name']"));
        driver.findElement(By.cssSelector("input[placeholder='Enter first name']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter first name']")).sendKeys(testUser.getFirstName());

        driver.findElement(By.cssSelector("input[placeholder='Enter last name']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter last name']")).sendKeys(testUser.getLastName());

        //Select select = new Select(driver.findElement(By.xpath("id('main-content')//div[@class='form-section birthday'][1]/descendant::select[1]")));
        //select.selectByVisibleText(testUser.getBirthDateDay());

        //select = new Select(driver.findElement(By.xpath("id('main-content')//div[@class='form-section birthday'][1]/descendant::select[2]")));
        //select.selectByVisibleText(testUser.getBirthDateMonth());

        //select = new Select(driver.findElement(By.xpath("id('main-content')//div[@class='form-section birthday'][1]/descendant::select[3]")));
        //select.selectByVisibleText(testUser.getBirthDateYear());

        driver.findElement(By.cssSelector("input[placeholder='Enter Street']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter Street']")).sendKeys(testUser.getStreet());

        driver.findElement(By.cssSelector("input[placeholder='House Number']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='House Number']")).sendKeys(testUser.getHouseNumber());

        driver.findElement(By.cssSelector("input[placeholder='Enter City']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter City']")).sendKeys(testUser.getCity());

        driver.findElement(By.cssSelector("input[placeholder='Enter Post Code']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter Post Code']")).sendKeys(testUser.getZipCode());
    }

    @Then("^A Confirmation message is displayed$")
    public void a_confirmation_message_is_displayed() throws Throwable {
        //driver.findElement(By.className("c-register-view--success__title"));
        waitFor(By.className("c-register-view--success__title"));
    }

    @Then("^A duplicated data message is displayed$")
    public void a_duplicated_data_message_is_displayed() throws Throwable {
        assertEquals("This Email Address Already Exists In Our System.",
                driver.findElement(By.xpath("//*[contains(text(), 'This Email Address Already Exists In Our System.')]")).getText());
    }

    @When("^user tries to register with the same email address \"([^\"]*)\"$")
    public void user_tries_to_register_with_the_same_email_address(String duplicatedEmail) throws Throwable {
        user_clicks_on_Register();

        driver.findElement(By.cssSelector("input[placeholder='Enter email address']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter email address']")).sendKeys(duplicatedEmail);

        driver.findElement(By.cssSelector("input[placeholder='Enter desired username']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter desired username']")).sendKeys(testUser.getUserName());

        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[placeholder='Retype password']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Retype password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[placeholder*='Enter number']")).clear();
        driver.findElement(By.cssSelector("input[placeholder*='Enter number']")).sendKeys(testUser.getTelephoneNumber());

        driver.findElement(By.cssSelector("button.btn--secondary--large--with-arrow")).click();
    }

    @When("^user tries to register with the same username \"([^\"]*)\"$")
    public void user_tries_to_register_with_the_same_username(String duplicatedUsername) throws Throwable {
        user_clicks_on_Register();

        driver.findElement(By.cssSelector("input[placeholder='Enter email address']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter email address']")).sendKeys(testUser.geteMailAddress());

        driver.findElement(By.cssSelector("input[placeholder='Enter desired username']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter desired username']")).sendKeys(duplicatedUsername);

        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[placeholder='Retype password']")).clear();
        driver.findElement(By.cssSelector("input[placeholder='Retype password']")).sendKeys(testUser.getPassword());

        driver.findElement(By.cssSelector("input[placeholder*='Enter number']")).clear();
        driver.findElement(By.cssSelector("input[placeholder*='Enter number']")).sendKeys(testUser.getTelephoneNumber());

        driver.findElement(By.cssSelector("button.btn--secondary--large--with-arrow")).click();
    }

    @Then("^display a message \"([^\"]*)\"$")
    public void display_a_message(String errorMsg) throws Throwable {
        exists(By.className("error-info"));
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
