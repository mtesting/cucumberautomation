package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import io.cucumber.datatable.DataTable;
import other.SeleniumTestTemplate;
import util.NumberUtil;
import util.Utils;

public class Transactions extends SeleniumTestTemplate {

    private String transactionTypeUsed;
    private Customer userAccount;

    public Transactions(Customer userAccount) {
        this.userAccount = userAccount;
    }

    @Given("^the user searches for a customer account$")
    public void the_user_searches_for_a_customer_account() throws Throwable {
        driver.findElement(By.cssSelector("input[type='text']")).clear();
        String account = "lalala1";
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys(account); //TODO REPLACE ACCOUNT
        driver.findElement(By.xpath("//section[@id='body-content']/div/div[2]/div[2]/div/div/div/div/div/div/div/form/div/div[9]/button")).click();
        driver.findElement(By.cssSelector("div[title='" + account + "']")).click();
    }

    @When("^the user initiates a transaction \"([^\"]*)\"$")
    public void the_user_initiates_a_transaction(String transactionType) throws Throwable {
        transactionTypeUsed = transactionType;

        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        driver.findElement(By.cssSelector("li[title='Transactions']")).click();
        driver.findElement(By.linkText("Initiate Transaction")).click();
        new Select(driver.findElement(By.cssSelector("div.inline-form-elements > div > div.select-style > select"))).selectByVisibleText(transactionType);
    }

    @When("^input the details:$")
    public void input_the_details(DataTable dataTable) throws Throwable {
        List<List<String>> data = dataTable.asLists();

        driver.findElement(By.cssSelector("[data-test-funds-type-options]")).click();
        driver.findElement(By.cssSelector("input[type='number']")).clear();
        driver.findElement(By.cssSelector("input[type='number']")).sendKeys(data.get(1).get(1));
        userAccount.setBalance(data.get(1).get(1));

        driver.findElement(By.cssSelector("div.inline-form-elements > input[type='text']")).clear();
        driver.findElement(By.cssSelector("div.inline-form-elements > input[type='text']")).sendKeys(data.get(1).get(2));

        driver.findElement(By.linkText("Ok")).click();
    }

    @Then("^the customers account is debited accordingly$")
    public void the_customers_account_is_debited_accordingly() throws Throwable {

        Utils.waitSeconds(3);
        String[][] tablaDeDatos;
        int row_num, col_num;
        row_num = 1;

        WebElement table_element = driver.findElement(By.xpath("//*[@id=\"body-content\"]/div/div[2]/div[2]/div/div/div/div/div[2]/div"));
        List<WebElement> tr_collection = table_element.findElements(By.className("table-row")); //READ ALL ROWS
        log.debug("NUMBER OF ROWS IN THIS TABLE = " + tr_collection.size());

        tablaDeDatos = new String[tr_collection.size() + 1][9];

        for (WebElement trElement : tr_collection) {
            List<WebElement> td_collection = trElement.findElements(By.className("table-cell")); //READ ALL COLUMNS
            log.debug("NUMBER OF COLUMNS = " + td_collection.size());
            col_num = 1;

            for (WebElement tdElement : td_collection) {
                log.debug("row # " + row_num + ", col # " + col_num + "text=" + tdElement.getText());

                tablaDeDatos[row_num][col_num] = tdElement.getText();

                col_num++;
            }
            row_num++;
        }

        userAccount.deposit(tablaDeDatos[3][6].substring(1));

        //scrollIntoView(By.cssSelector("i[class='fa fa-chevron-right']"));

        Assert.assertTrue("Transaction not displayed properly", transactionTypeUsed.toLowerCase().contains(tablaDeDatos[2][3].toLowerCase()));
        Assert.assertEquals("Balance not updated properly", userAccount.getBalance(), NumberUtil.parseToBigDecimal(tablaDeDatos[2][6].substring(1)));

    }

}
