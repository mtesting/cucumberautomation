package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import other.SeleniumTestTemplate;

public class MyBetRegistrationPageFactory extends SeleniumTestTemplate {

    @FindBy(css = "input[id='Email Address']")
    private WebElement emailAddress;

    @FindBy(id = "Username")
    private WebElement username;

    @FindBy(id = "Password")
    private WebElement password;

    @FindBy(css = "input[id='Confirm Password']")
    private WebElement passwordConfirm;

    @FindBy(css = "input[id='Mobile number (for mobile bonuses)']")
    private WebElement mobileNumber;

    @FindBy(css = "input[id='Enter first name']")
    private WebElement firstName;

    @FindBy(css = "input[id='Enter last name']")
    private WebElement lastName;

    @FindBy(xpath = "id('register-page')//div[@class='cell-1'][1]/descendant::select[1]")
    private WebElement birthDateDay;

    @FindBy(xpath = "id('register-page')//div[@class='cell-1'][1]/descendant::select[3]")
    private WebElement birthDateYear;

    @FindBy(id = "Street")
    private WebElement street;

    @FindBy(id = "Number")
    private WebElement number;

    @FindBy(css = "input[id='Post Code']")
    private WebElement postCode;

    @FindBy(id = "City")
    private WebElement city;

    @FindBy(css = "input[id='Place of Birth']")
    private WebElement placeOfBirth;

    @FindBy(xpath = "id('register-page')//div[@class='cell-1'][1]/descendant::input[1]")
    private WebElement registerProfilePage;

    @FindBy(xpath = "//*[contains(text(), 'Please check your mailbox')]")
    private WebElement registrationSuccessMsg;

    @FindBy(className = "error-box")
    private WebElement registrationErrorMsg;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(id = "check1")
    private WebElement confirmationCheckbox;

    public MyBetRegistrationPageFactory() {
        PageFactory.initElements(driver, this);
    }

    public void enterRegistrationProfile(String emailAddress, String username, String password, String mobileNumber) {
        waitFor(registerProfilePage);
        this.emailAddress.sendKeys(emailAddress);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.passwordConfirm.sendKeys(password);
        this.mobileNumber.sendKeys(mobileNumber);
    }

    public void enterRegistrationPersonalData(String firstName, String lastName, String birthDateDay, String birthDateYear,
            String street, String number, String postCode, String city) {
        waitFor(this.firstName);
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        selectElement(this.birthDateDay, birthDateDay);
        selectElement(this.birthDateYear, birthDateYear);
        this.street.sendKeys(street);
        this.number.sendKeys(number);
        this.postCode.sendKeys(postCode);
        this.city.sendKeys(city);
        this.placeOfBirth.sendKeys(city);
    }

    public boolean isRegistrationSuccessfulMsgDisplayed(){
        return registrationSuccessMsg.isDisplayed();
    }

    public void clickSubmit(){
        submitButton.click();
    }

    public void tickConfirmationCheckbox() {
        javascriptClick(confirmationCheckbox);
    }

    public boolean isRegistrationErrorMsgDisplayed() {
        return confirmationCheckbox.isDisplayed();
    }
}
