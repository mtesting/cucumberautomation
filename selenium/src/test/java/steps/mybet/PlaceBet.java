package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

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

    private Customer userAccount;

    public PlaceBet(Customer userAccount) {
        this.userAccount = userAccount;
    }

    @When("^User clicks on \"([^\"]*)\" selections$")
    public void user_clicks_on_selections(Integer selectionsQuantity) throws Throwable {

        userAccount.setBalance(driver.findElement(By.cssSelector("div[id='transactions'] span:nth-of-type(2)")).getText().substring(1));

        int numberOfTips = selectionsQuantity;
        List<WebElement> outcomes = new ArrayList<>();
        List<WebElement> cells;
        String sectionWidget;

        Utils.waitSeconds(1);
        log.info("Finding outcomes to bet on ...");
        try{
            sectionWidget = "//section[contains(@class, 'full-schedule-widget')]";
            cells = driver
                    .findElement(By.xpath(sectionWidget))
                    .findElements(By.className("multi-markets-view"));
        }catch(NoSuchElementException ignored){
            sectionWidget = "//section[contains(@class, 'league-selector-widget')]";
            cells = driver
                    .findElement(By.xpath(sectionWidget))
                    .findElements(By.className("multi-markets-view"));
        }

        for (WebElement row : cells) {
            List<WebElement> selections = row.findElements(By.className("selection-view"));
            log.info("Potential selections found: " + selections.size());
            for (WebElement selection : selections) {
                try {
                    if (exists(By.className("price"), selection)) {
                        if (!selection.getText().contains("\n")) {
                            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                            if (selection.getTagName().equalsIgnoreCase("a")) {
                                try {
                                    WebElement lockIcon = selection.findElement(By.className("icon-lock"));
                                    if (!lockIcon.isDisplayed() && !selection.getText().isEmpty()) {
                                        outcomes.add(selection);
                                        break;
                                    }
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
            log.info("Selections added: " + outcomes.size());
        }

        log.info("Placing bets ...");
        scrollIntoView(By.xpath(sectionWidget));
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
        if (isBetslipIconized()) {
            driver.findElement(By.className("betslip-icon")).click();
        }
        List<WebElement> tipsOnBetslip =
                driver.findElement(By.id("betslip")).findElements(By.className("selection-group"));
        Assert.assertTrue("No tips on betslip.", tipsOnBetslip.size() > 0);
        Assert.assertTrue("Too many tips on betslip", tipsOnBetslip.size() <= numberOfTips);
    }

    @And("^User enters a \"([^\"]*)\" bet amount \"([^\"]*)\"$")
    public void user_enters_a_bet_amount(String betType, String stake) throws Throwable {

        if (driver.findElement(By.cssSelector("a[class='betslip-icon']")).isDisplayed()) {
            if (!exists(By.cssSelector("div[class='bet-slip-container open']"))) {
                driver.findElement(By.className("icon-betslip")).click();
            }
        }

        switch (betType.toUpperCase()) {
            case "SINGLE":
                scrollIntoView(By.xpath("id('betslip')//div[@class='single-grouped-bet-view']")); // scroll field "Singles" of betslip into view so you can click on it
                driver.findElement(By.xpath("id('betslip')//div[@class='single-grouped-bet-view']")).click(); // open input field
                driver.findElement(By.xpath("id('betslip')//div[@class='single-grouped-bet-view']//input")).sendKeys(stake);
                break;
            case "MULTIPLE":
                scrollIntoView(By.xpath("id('betslip')//div[@class='combi-bet-view']"));
                if (!driver.findElement(By.xpath("id('betslip')//div[@class='combi-bet-view']//input")).isDisplayed()) {
                    driver.findElement(By.xpath("id('betslip')//div[@class='combi-bet-view']")).click();
                }
                driver.findElement(By.xpath("id('betslip')//div[@class='combi-bet-view']//input")).sendKeys(stake);
                break;
            case "SYSTEM":
                scrollIntoView(By.xpath("id('betslip')//div[@class='system-bets-view']"));
                driver.findElement(By.xpath("id('betslip')//div[@class='system-bets-view']")).click();
                driver.findElement(By.cssSelector("[data-test-stake-input='stakeInputDOUBLE']")).sendKeys(stake);
                driver.findElement(By.cssSelector("[data-test-stake-input='stakeInputTRIXIE']")).sendKeys(stake);
                driver.findElement(By.cssSelector("[data-test-stake-input='stakeInputPATENT']")).sendKeys(stake);
                break;
        }

        scrollIntoView(By.xpath("id('betslip')//li[@class='higher-odds']"));
        checkBox(driver.findElement(By.xpath("id('betslip')//li[@class='higher-odds']")), "Accept all odds changes");
    }

    @And("^User clicks on Place Bet$")
    public void user_clicks_on_Place_Bet() throws Throwable {

        userAccount.subtract(driver.findElement(By.cssSelector("li[class=slip-summary__total-stake] > span:nth-child(2)"))
                .getText().substring(1));
        userAccount.subtract(driver.findElement(By.cssSelector("li[class=slip-summary__percentage-tax] > span:nth-child(2)"))
                .getText().substring(3));

        scrollIntoView(By.id("place-bet"));
        driver.findElement(By.id("place-bet")).click();
    }

    @Then("^A Bet Placed message is displayed$")
    public void a_Bet_Placed_message_is_displayed() throws Throwable {
        waitFor(By.cssSelector("div.betslip-success"));
        log.info("Bet placed.");

        Utils.waitSeconds(1);
        Assert.assertEquals("Cash balance not updated properly", userAccount.getBalance(),
                NumberUtil.parseToBigDecimal(driver.findElement(By.cssSelector("div[id='transactions'] span:nth-of-type(2)")).getText().substring(1)));
    }

    @Then("^An unsuccessful message is displayed$")
    public void an_unsuccessful_message_is_displayed() throws Throwable {
        waitFor(By.cssSelector("div.single-bet__error"));
        log.info("Bet not placed.");
    }

}
