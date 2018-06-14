package steps.att;

import org.apache.log4j.Logger;
import org.junit.Assert;

import java.math.BigDecimal;

import apiLevelInteraction.SportsbookHelper;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import generated.ats.betsync.betcatcher.dto.CashoutResult;
import util.Utils;

public class Cashout {

    private static final Logger log = Logger.getLogger(Cashout.class);

    private final SportsbookHelper sportsbook;
    private Customer customer;
    private BigDecimal cashOutAmount;

    public Cashout(SportsbookHelper sportsbook, Customer customer) {
        this.sportsbook = sportsbook;
        this.customer = customer;
    }

    @When("^User tries to cash out the bet placed$")
    public void user_tries_to_cash_out_the_bet_placed() throws Throwable {
        for (CashoutResult result : sportsbook.calculateCashout().getCashoutResult()) {
            if (result.getBetId().equalsIgnoreCase(customer.placeBetsResponse.getBetPlacementResult().get(0).getBetId())) {
                log.info("Trying to cashout betId=" + result.getBetId() + " with status=" + result.getStatus());
                cashOutAmount = result.getCashoutValue();
                sportsbook.cashoutBet(
                        customer.placeBetsResponse.getBetPlacementResult().get(0).getBetId(), result.getCashoutValue()
                );
            }
        }
    }

    @Then("^the wallets balance gets updated after cashout$")
    public void theWalletsBalanceGetsUpdatedAfterCashout() throws Throwable {
        Utils.waitSeconds(5);
        Assert.assertEquals("Wallet not updated properly after cashout",
                customer.getBalance().add(cashOutAmount), sportsbook.getWalletBalance());
    }

}
