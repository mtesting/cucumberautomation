package steps.att;

import org.junit.Assert;

import java.math.BigDecimal;

import apiLevelInteraction.SportsbookHelper;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;

public class Chips {

    private final SportsbookHelper sportsbook;
    private Customer customer;

    private BigDecimal chipsAmount;
    private String transactionType;

    public Chips(SportsbookHelper sportsbook, Customer customer) {
        this.sportsbook = sportsbook;
        this.customer = customer;
    }

    @When("^User \"([^\"]*)\" \"([^\"]*)\" chips$")
    public void userChips(String transactionType, BigDecimal chipsAmount) throws Throwable {
        this.chipsAmount = chipsAmount;
        this.transactionType = transactionType;
        customer.setBalance(sportsbook.getWalletBalance());
        sportsbook.tradeChips(transactionType, chipsAmount.toString());
    }

    @Then("^the wallets balance must be updated$")
    public void theWalletsBalanceMustBeUpdated() throws Throwable {
        if ("buy".equalsIgnoreCase(transactionType)) {
            Assert.assertEquals("Balance not updated properly", customer.getBalance().subtract(chipsAmount), sportsbook.getWalletBalance());
        } else {
            Assert.assertEquals("Balance not updated properly", customer.getBalance().add(chipsAmount), sportsbook.getWalletBalance());
        }
    }

}
