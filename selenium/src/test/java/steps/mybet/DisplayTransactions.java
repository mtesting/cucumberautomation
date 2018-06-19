package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.DataBaseHelper;
import decoders.Decoder;
import decoders.DecoderManager;
import entities.Customer;
import other.Constants;
import other.SeleniumTestTemplate;

public class DisplayTransactions extends SeleniumTestTemplate {

    private String filter;
    private Integer sportsbookCount;
    private Customer userAccount;

    public DisplayTransactions(Customer userAccount) {
        this.userAccount = userAccount;
    }

    @And("^the user has perform a \"([^\"]*)\" operation$")
    public void the_user_has_perform_a_operation(String transactionType) throws Throwable {
        switch (transactionType) {
            case "Bets":
//                PlaceBet placeBet = new PlaceBet(userAccount);
//                int selections = 1;
//                String stake = "1";
//                String betType = "SINGLE";
//                placeBet.user_clicks_on_selections(selections);
//                placeBet.user_enters_a_bet_amount(betType, stake);
//                placeBet.user_clicks_on_Place_Bet();
//                placeBet.a_Bet_Placed_message_is_displayed();
                break;
            case "Deposit":
                Deposit deposit = new Deposit(userAccount);
                deposit.user_clicks_on_Deposit();
                deposit.user_enters_a_deposit_amount("35");
                deposit.clicks_on_Continue_To_Payment_Method();
                deposit.pays_with_a_valid_method();
                deposit.message_displayed_Your_deposit_was_successful();
                driver.findElement(By.cssSelector("[data-test-depositbtn]")).click();
                break;
            case "Withdrawal":
                Withdraw withdraw = new Withdraw(userAccount);
                withdraw.user_clicks_on_Withdraw();
                withdraw.enters_a_withdraw_amount();
                withdraw.continues_with_the_process();
                withdraw.a_successful_message_is_displayed();
                break;
            case "Chips Transfer":
                Chips chips = new Chips(userAccount);
                chips.user_clicks_on_Chips_Buy_sell();
                chips.user_clicks_on_Buy_Chips();
                chips.user_enters_an_amount("5");
                chips.user_clicks_on_Buy_Chips_orange_button();
                chips.message_displayed_Your_transaction_was_successful();
                break;
        }
    }

    @When("^the sportsbook user navigates to their account transactions$")
    public void the_sportsbook_user_navigates_to_their_account_transactions() throws Throwable {
        moveOver(By.id("transactions"));
        driver.findElement(By.cssSelector("[data-test-transactions]")).click();
    }

    @When("^they filter by \"([^\"]*)\" only$")
    public void they_filter_by_only(String filterParam) throws Throwable {
        filter = filterParam;
        selectElement(By.xpath("//div[@id='transactions-view']/div[3]/div/div/div/div/div/div/span[2]/select"), filter);
    }

    @Then("^All the relevant transactions must be displayed$")
    public void all_the_relevant_transactions_must_be_displayed() throws Throwable {
        sportsbookCount = driver.findElements(By.cssSelector("div[class='table-row alt-cell-highlight']")).size();
        if (sportsbookCount >= 8) {
            selectElement(By.id("rowsPerPageSelect"), "100");
            sportsbookCount = driver.findElements(By.cssSelector("div[class='table-row alt-cell-highlight']")).size();
        }
    }

    @And("^no other transactions will be visible$")
    public void no_other_transactions_will_be_visible() throws Throwable {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        Integer dbCount = null;

        DataBaseHelper dbHelper = new DataBaseHelper(decoder.decodeCustomerDb("MYBET_ATS_DB"), Constants.ATS_DB_USER, Constants.MYBET_DB_PASSWORD);
        dbHelper.connect();
        Long userId = dbHelper.getUserIdFor(userAccount.getUsername());
        dbHelper.disconnect();

        dbHelper = new DataBaseHelper(decoder.decodeCustomerDb("MYBET_BETS_DB"), Constants.ATS_DB_USER, Constants.MYBET_DB_PASSWORD);
        dbHelper.connect();
        if ("Bets".equalsIgnoreCase(filter)) {
            dbCount = Integer.valueOf(dbHelper.executeQuery("" +
                    "select count(*) from account_statements " +
                    "where acco_id = " + userId + " " +
                    "and trans_date >= (current_date-3) " +
                    "and trans in ('STAKE','SETTLEMENT');"
            ));
        } else if ("Withdrawal".equalsIgnoreCase(filter)) {
            dbCount = Integer.valueOf(dbHelper.executeQuery("" +
                    "select count(*) from account_statements " +
                    "where acco_id = " + userId + " " +
                    "and trans_date >= (current_date-3) " +
                    "and trans in ('WITHDRAWAL_PENDING');"
            ));
        } else if ("Deposit".equalsIgnoreCase(filter)) {
            dbCount = Integer.valueOf(dbHelper.executeQuery("" +
                    "select count(*) from account_statements " +
                    "where acco_id = " + userId + " " +
                    "and trans_date >= (current_date-3) " +
                    "and trans in ('DEPOSIT_FEE','DEPOSIT');"
            ));
        } else if ("Chips Transfer".equalsIgnoreCase(filter)) {
            dbCount = Integer.valueOf(dbHelper.executeQuery("" +
                    "select count(*) from account_statements " +
                    "where acco_id = " + userId + " " +
                    "and trans_date >= (current_date-3) " +
                    "and trans in ('CHIPS_SELL','CHIPS_BUY') " +
                    "and fund_type_id = 3;"
            ));
        }
        dbHelper.disconnect();

        Assert.assertEquals("The transactions displayed mismatch with the DB", dbCount, sportsbookCount);
    }

}
