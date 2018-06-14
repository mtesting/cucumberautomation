package steps.betstars;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import other.SeleniumTestTemplate;

public class CashOut extends SeleniumTestTemplate {

    private Customer customer;

    public CashOut(Customer account) {
        this.customer = account;
    }

    @When("^User tries to cash out the bet placed$")
    public void user_tries_to_cash_out_the_bet_placed() throws Throwable {
        clickByActionOn(By.id("tab-nav-mybets"));
        Assert.assertTrue("Cash Out option not found", exists(By.cssSelector("button[betid='cashout-button-" + customer.bets.get(0).getBetId() + "']")));
        scrollIntoView(By.cssSelector("button[betid='cashout-button-" + customer.bets.get(0).getBetId() + "']"));
        clickByActionOn(By.cssSelector("button[betid='cashout-button-" + customer.bets.get(0).getBetId() + "']"));
    }

    @Then("^the money has to be paid back into the account$")
    public void the_money_has_to_be_paid_back_into_the_account() throws Throwable {
        Assert.assertTrue("Cash out failed", exists(By.className("cashout__status__title")));
        Assert.assertTrue("Cash out failed", exists(By.id("cashout-confirm-value-" + customer.bets.get(0).getBetId() + "")));
        //TODO Assert the account balance increased based on the cash out value
    }

}
