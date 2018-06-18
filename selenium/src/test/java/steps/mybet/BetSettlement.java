package steps.mybet;

import org.junit.Assert;

import PageFactory.MyBetBackOfficePageFactory;
import cucumber.api.java.en.When;
import entities.Customer;
import util.Utils;

public class BetSettlement {

    private MyBetBackOfficePageFactory myBetBackOfficePageFactory;
    private Customer customer;

    public BetSettlement(Customer customer) {
        this.customer = customer;
        myBetBackOfficePageFactory = new MyBetBackOfficePageFactory();
    }

    @When("^the user settles the bet as \"([^\"]*)\"$")
    public void the_user_settles_the_bet_as(String betOutcome) throws Throwable {
        customer.setBetOutcome(betOutcome);
        myBetBackOfficePageFactory.initMyBetBackOfficePageFactory();
        myBetBackOfficePageFactory.clickLoginButton();
        Utils.waitSeconds(1);

        myBetBackOfficePageFactory.setLoginUsername("manager1");
        myBetBackOfficePageFactory.setLoginPassword("test1");
        myBetBackOfficePageFactory.clickOnAcceptLoginButton();

        myBetBackOfficePageFactory.clickOnsearchBets();
        myBetBackOfficePageFactory.enterBetRefNumber(customer.bets.get(0).getBetId().toString());

        myBetBackOfficePageFactory.clickOnSelectBetResults();

        myBetBackOfficePageFactory.selectBetOutcomeResult(betOutcome);
        myBetBackOfficePageFactory.clickOnAmendButton();
        Utils.waitSeconds(1);
        Assert.assertEquals("Save Complete", myBetBackOfficePageFactory.getAlertMsg());
        myBetBackOfficePageFactory.clickOnAcceptAlert();
        //myBetBackOfficePageFactory.clickOnAcceptAlert();
        myBetBackOfficePageFactory.closeClient();
    }

}