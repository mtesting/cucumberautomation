package steps.mybet;

import PageFactory.MyBetPageFactory;
import PageFactory.MyBetRegistrationPageFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.TestUserData;
import entities.TestUserDataService;

import static org.junit.Assert.assertTrue;

public class Registration {

    private final TestUserDataService userDataService = new TestUserDataService();
    private final TestUserData testUser = userDataService.generateForMtLicense();
    private MyBetPageFactory myBetPageFactory;
    private MyBetRegistrationPageFactory myBetRegistrationPageFactory;

    public Registration(MyBetPageFactory myBetPageFactory){
        this.myBetPageFactory = myBetPageFactory;
    }

    @When("^User clicks on Register$")
    public void user_clicks_on_Register() throws Throwable {
        myBetRegistrationPageFactory = myBetPageFactory.newRegistration();
    }

    @And("^User enters valid data")
    public void user_enters_valid_data() throws Throwable {
        myBetRegistrationPageFactory.enterRegistrationProfile(
                testUser.geteMailAddress(), testUser.getUserName(), testUser.getPassword(), testUser.getTelephoneNumber());

        myBetRegistrationPageFactory.clickSubmit();
        myBetRegistrationPageFactory.clickSubmit();

        myBetRegistrationPageFactory.enterRegistrationPersonalData(testUser.getFirstName(), testUser.getLastName(),
                testUser.getBirthDateDay(), testUser.getBirthDateYear(), testUser.getStreet(), testUser.getHouseNumber(),
                testUser.getZipCode(), testUser.getCity());
    }

    @Then("^A Confirmation message is displayed$")
    public void a_confirmation_message_is_displayed() throws Throwable {
        assertTrue("Successful message not displayed", myBetRegistrationPageFactory.isRegistrationSuccessfulMsgDisplayed());
    }

    @Then("^A duplicated data message is displayed$")
    public void a_duplicated_data_message_is_displayed() throws Throwable {
//        assertEquals("Your desired username already exists. Please choose another one.",
//                driver.findElement(By.xpath("//*[contains(text(), 'Your desired username already exists. Please choose another one.')]")).getText());
        throw new PendingException();
    }

    @When("^user tries to register with the same email address \"([^\"]*)\"$")
    public void user_tries_to_register_with_the_same_email_address(String duplicatedEmail) throws Throwable {
        user_clicks_on_Register();

        myBetRegistrationPageFactory.enterRegistrationProfile(
                duplicatedEmail, testUser.getUserName(), testUser.getPassword(), testUser.getTelephoneNumber());

        myBetRegistrationPageFactory.clickSubmit();
        myBetRegistrationPageFactory.clickSubmit();
    }

    @Then("^display a message \"([^\"]*)\"$")
    public void display_a_message(String errorMsg) throws Throwable {
        assertTrue(myBetRegistrationPageFactory.isRegistrationErrorMsgDisplayed());
    }

    @When("^user tries to register with the same username \"([^\"]*)\"$")
    public void user_tries_to_register_with_the_same_username(String duplicatedUsername) throws Throwable {
        user_clicks_on_Register();

        myBetRegistrationPageFactory.enterRegistrationProfile(
                testUser.geteMailAddress(), duplicatedUsername, testUser.getPassword(), testUser.getTelephoneNumber());

        myBetRegistrationPageFactory.clickSubmit();
        myBetRegistrationPageFactory.clickSubmit();
    }

    @And("^User ticks the confirmation checkbox$")
    public void user_ticks_the_confirmation_checkbox() throws Throwable {
        myBetRegistrationPageFactory.tickConfirmationCheckbox();
    }

    @And("^User clicks on Next Step button$")
    public void user_clicks_on_Next_Step_button() throws Throwable {
        myBetRegistrationPageFactory.clickSubmit();
    }

}
