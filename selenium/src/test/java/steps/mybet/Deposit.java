package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import other.SeleniumTestTemplate;
import util.NumberUtil;
import util.Utils;

public class Deposit extends SeleniumTestTemplate {

    private Customer account;

    public Deposit(Customer account) {
        this.account = account;
    }

    @When("^User clicks on Deposit$")
    public void user_clicks_on_Deposit() throws Throwable {
        driver.findElement(By.id("deposit")).click();
    }

    @And("^Clicks on Continue To Payment Method$")
    public void clicks_on_Continue_To_Payment_Method() throws Throwable {
        Utils.waitSeconds(1);
        driver.findElement(By.cssSelector("[data-test-deposit-button-continue-to-payment]")).click();
    }

    @And("^User enters a deposit amount \"([^\"]*)\"$")
    public void user_enters_a_deposit_amount(String amount) throws Throwable {
        waitFor(By.cssSelector("input.no-label"));

        try {
            account.setBalance(driver.findElement(By.cssSelector("div[class='cash'] span")).getText().substring(1));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Cash balance info not found in the screen", e);
        }

        driver.findElement(By.cssSelector("[data-test-deposit-amount-input]")).clear();
        driver.findElement(By.cssSelector("[data-test-deposit-amount-input]")).sendKeys(amount);
    }

    @And("^pays with a valid method$")
    public void pays_with_a_valid_method() throws Throwable {
        String paymentMethod = "cc-visa-master";
        driver.findElement(By.cssSelector("a > i.logo-" + paymentMethod)).click();

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@name='cnpIframe']")));
        javascriptClick(By.xpath("//button[contains(@class, 'wpwl-button-pay')]"));
    }

    @And("^Enters credit card number \"([^\"]*)\"$")
    public void enters_credit_card_number(String cardNumber) throws Throwable {
        driver.findElement(By.cssSelector("input[name='card.number']")).clear();
        driver.findElement(By.cssSelector("input[name='card.number']")).sendKeys(cardNumber);
    }

    @And("^Enters expiry date \"([^\"]*)\"$")
    public void enters_expiry_date(String expiryDate) throws Throwable {
        driver.findElement(By.cssSelector("input.wpwl-control wpwl-control-expiry")).clear();
        driver.findElement(By.cssSelector("input.wpwl-control wpwl-control-expiry")).sendKeys(expiryDate);
    }

    @And("^Enters CVV value \"([^\"]*)\"$")
    public void enters_CVV_value(String cvvNumber) throws Throwable {
        driver.findElement(By.name("card.cvv")).clear();
        driver.findElement(By.name("card.cvv")).sendKeys(cvvNumber);
    }

    @And("^Enters card holder \"([^\"]*)\"$")
    public void enters_card_holder(String cardHolderName) throws Throwable {
        driver.findElement(By.name("card.holder")).clear();
        driver.findElement(By.name("card.holder")).sendKeys(cardHolderName);
    }

    @Then("^Message displayed Your deposit was successful$")
    public void message_displayed_Your_deposit_was_successful() throws Throwable {
        driver.switchTo().defaultContent();
        String confirmationMsg = driver.findElement(By.id("depositConfirmView")).getText().trim();
        Assert.assertTrue(confirmationMsg.contains("Your deposit was successful"));

        Utils.waitSeconds(5);
        account.deposit(driver.findElement(By.cssSelector("span[class='pull-right']")).getText().substring(1));
        Assert.assertEquals("Cash balance not updated properly", account.getBalance(),
                NumberUtil.parseToBigDecimal(driver.findElement(By.cssSelector("div[class='cash'] span")).getText().substring(1)));
    }

}
