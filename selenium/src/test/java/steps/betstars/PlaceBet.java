package steps.betstars;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Customer;
import other.Constants;
import other.SeleniumTestTemplate;
import util.NumberUtil;
import util.Utils;

public class PlaceBet extends SeleniumTestTemplate {

    private Customer customer;

    public PlaceBet(Customer account) {
        this.customer = account;
    }

    @When("^User clicks on \"([^\"]*)\" selections$")
    public void user_clicks_on_selections(Integer selectionsQuantity) throws Throwable {
        customer.setBalance(driver.findElement(By.cssSelector("span[id='accountBalance'] a")).getText().substring(1));

        driver.findElement(By.linkText("Football")).click();
        //waitFor(By.cssSelector("#market-h2h > div > section"));
        //driver.get(decodeCustomer(Constants.CUSTOMER_IN_TEST)+"#/soccer/competitions");

        int numberOfTips = selectionsQuantity;
        List<WebElement> outcomes = new ArrayList<>();
        List<WebElement> cells;

        log.info("Finding outcomes to bet on ...");
        cells = driver
                .findElement(By.xpath("//section[contains(@class, 'include')]"))
                .findElements(By.className("promoted-market"));


        for (WebElement row : cells) {
            List<WebElement> selections = row.findElements(By.cssSelector("a[id^='event-schedule-selection-']"));
            log.info("Potential selections found: " + selections.size());
            for (WebElement selection : selections) {
                try {
                    if (!exists(By.cssSelector("a[class='disabled default']"), selection)) {
                        if (!selection.getText().contains("\n")) {
                            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                            if (selection.getTagName().equalsIgnoreCase("a")) {
                                try {
                                    outcomes.add(selection);
                                    break;
                                } catch (NoSuchElementException ignored) {
                                    outcomes.add(selection);
                                    break;
                                }
                            }
                        }
                    }
                } catch (NoSuchElementException | StaleElementReferenceException ignored) {
                }
            }
        }
        log.info("Selections added: " + outcomes.size());

        log.info("Placing bets ...");
        scrollIntoView(By.xpath("//section[contains(@class, 'include')]"));
        Random random = new Random();
        int numberOfOutcomes = outcomes.size();
        if (numberOfOutcomes < numberOfTips) {
            numberOfTips = numberOfOutcomes;
        }
        driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        for (int i = 0; i < numberOfTips; i++) {
            int randomIndex = random.nextInt(numberOfOutcomes);
            WebElement outcome = outcomes.get(randomIndex);
            try {
                clickByActionOn(outcome);
                outcomes.remove(outcome);
                numberOfOutcomes--;
            } catch (WebDriverException e) {
                if (e.getMessage().startsWith("Element is not clickable at point ")) {
                    i--;
                }
            }
        }

        log.info("Submitting bet slip ...");
        /*if (!exists(By.cssSelector("div[class='betslipToggle show']"))) {
            driver.findElement(By.className("betslipToggle")).click();
		}*/
        List<WebElement> tipsOnBetslip =
                driver.findElement(By.cssSelector("#bets-singles > div > ul")).findElements(By.tagName("li"));
        Assert.assertTrue("No tips on betslip.", tipsOnBetslip.size() > 0);
        Assert.assertTrue("Too many tips on betslip", tipsOnBetslip.size() <= numberOfTips);
    }

    @And("^User enters a \"([^\"]*)\" bet amount \"([^\"]*)\"$")
    public void user_enters_a_bet_amount(String betType, String stake) throws Throwable {

        /*if (driver.findElement(By.cssSelector("a[class='betslip-icon']")).isDisplayed()) {
            if(!exists(By.cssSelector("div[class='bet-slip-container open']"))){
                driver.findElement(By.className("icon-betslip")).click();
            }
        }*/

        switch (betType.toUpperCase()) {
            case "SINGLE":
                scrollIntoView(By.id("bet-slip"));
                //driver.findElement(By.id("multiple-bet-li-GROUPED_SINGLE")).click(); //FIXME
                driver.findElement(By.cssSelector("input[class='stake__input singleBetInput deactivate-spinner']")).sendKeys(stake);
                break;
            case "MULTIPLE":
                scrollIntoView(By.id("multiple-bet-li-DOUBLE"));
                driver.findElement(By.id("multiple-bet-li-DOUBLE")).click();
                driver.findElement(By.id("stake-input-DOUBLE")).sendKeys(stake);
                break;
            case "SYSTEM":
                scrollIntoView(By.id("multiple-bet-li-TRIXIE"));
                driver.findElement(By.id("multiple-bet-li-TRIXIE")).click();
                driver.findElement(By.id("stake-input-TRIXIE")).sendKeys(stake);
                //driver.findElement(By.id("stake-input-PATENT")).sendKeys(stake);
                break;
        }

        scrollIntoView(By.id("bets-auto-accept-price-change"));
        driver.findElement(By.id("betslip-accept-price-change-toggle"));
    }

    @And("^User clicks on Place Bet$")
    public void user_clicks_on_Place_Bet() throws Throwable {
        customer.subtract(driver.findElement(By.cssSelector("#bet-total-stake em")).getText().substring(1));

        scrollIntoView(By.id("place-bet-button"));
        driver.findElement(By.id("place-bet-button")).click();
    }

    @Then("^A Bet Placed message is displayed$")
    public void a_Bet_Placed_message_is_displayed() throws Throwable {
        waitFor(By.cssSelector("li[class='dialog success']"));
        log.info("Bet placed.");

        Utils.waitSeconds(1);
        Assert.assertEquals("Cash balance not updated properly", customer.getBalance(),
                NumberUtil.parseToBigDecimal(driver.findElement(By.cssSelector("span[id='accountBalance'] a")).getText().substring(1)));
    }

    @Then("^An unsuccessful message is displayed$")
    public void an_unsuccessful_message_is_displayed() throws Throwable {
        waitFor(By.cssSelector("li.betPlaceError"));
        log.info("Bet not placed.");
    }

    @And("^user has placed a \"([^\"]*)\" bet$")
    public void user_has_placed_a_bet(String betType) throws Throwable {

        String stake = "1";
        int selections = 1;
        switch (betType.toUpperCase()) {
            case "SINGLE":
                selections = 1;
                break;
            case "MULTIPLE":
                selections = 2;
                break;
            case "SYSTEM":
                selections = 3;
                break;
        }

        user_clicks_on_selections(selections);
        user_enters_a_bet_amount(betType, stake);
        user_clicks_on_Place_Bet();
        a_Bet_Placed_message_is_displayed();

        customer.bets.get(0).setBetId(Long.valueOf(driver.findElement(By.cssSelector("li[class='dialog success'] p")).getText().substring(5)));

    }

}
