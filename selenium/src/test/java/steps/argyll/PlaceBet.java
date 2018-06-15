package steps.argyll;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import other.Constants;
import other.SeleniumTestTemplate;

@Deprecated
public class PlaceBet extends SeleniumTestTemplate {

    @When("^User clicks on \"([^\"]*)\" selections$")
    public void user_clicks_on_selections(Integer selectionsQuantity) throws Throwable {
        int numberOfTips = selectionsQuantity;

        List<WebElement> outcomes = new ArrayList<>();
        List<WebElement> cells;

        cells = driver
                .findElement(By.className("col-12_lg-12"))
                .findElements(By.className("c-multi-markets-row__cell--selection"));

        for (WebElement cell : cells) {
            try {
                if (exists(By.className("c-price-block"), cell)) {
                    if (cell.getTagName().equalsIgnoreCase("div")) {
                        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                        try {
                            WebElement lockIcon = cell.findElement(By.className("icon-lock"));
                            if (!lockIcon.isDisplayed() && !cell.getText().isEmpty()) {
                                outcomes.add(cell);
                            }
                        } catch (NoSuchElementException ignored) {
                            outcomes.add(cell);
                        }

                    }
                }
                if (outcomes.size() >= 40) break;
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {
            }
        }

        driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        log.info("Placing bets ...");
        scrollIntoView(By.className("col-12_lg-12"));
        Random random = new Random();
        int numberOfOutcomes = outcomes.size();
        if (numberOfOutcomes < numberOfTips) {
            numberOfTips = numberOfOutcomes;
        }
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
        /*if (isBetslipIconized()) {
            driver.findElement(By.className("betslip-icon")).click();
        }*/
        List<WebElement> tipsOnBetslip =
                driver.findElement(By.className("c-bet-slip__body")).findElements(By.className("c-single-bet-group__section"));
        Assert.assertTrue("No tips on betslip.", tipsOnBetslip.size() > 0);
        Assert.assertTrue("Too many tips on betslip", tipsOnBetslip.size() <= numberOfTips);
    }

    @And("^User enters a \"([^\"]*)\" bet amount \"([^\"]*)\"$")
    public void user_enters_a_bet_amount(String betType, String stake) throws Throwable {

        scrollIntoView(By.className("c-betslip-summary__price-change-options"));
        Select select = new Select(driver.findElement(By.className("c-betslip-summary__price-change-options")));
        select.selectByVisibleText("All odds");

        if (betType.equalsIgnoreCase("Single")) {
            scrollIntoView(By.className("c-single-grouped__input"));   // scroll field "Singles" of betslip into view so you can click on it
            driver.findElement(By.className("c-single-grouped__input")).sendKeys(stake);
        } else if (betType.equalsIgnoreCase("Multiple")) {
            scrollIntoView(By.className("c-combi-bets__input"));
            driver.findElement(By.className("c-combi-bets__input")).sendKeys(stake);
        } else if (betType.equalsIgnoreCase("System")) {
            scrollIntoView(By.cssSelector("div[class='c-system-bets']"));

            if (!exists((By.cssSelector("div[class='c-system-bets__body']")), driver.findElement(By.className("c-bet-slip")))) {
                driver.findElement(By.cssSelector("div[class='c-system-bets']")).click();
            }

            List<WebElement> systemBets = driver.findElements(By.cssSelector("input[class='c-system-bets__input g-input-field g-input-field--text']"));
            systemBets.get(0).sendKeys(stake); //DOUBLE
            systemBets.get(1).sendKeys(stake); //TRIXIE
            systemBets.get(2).sendKeys(stake); //PATENT
        }

    }

    @And("^User clicks on Place Bet$")
    public void user_clicks_on_Place_Bet() throws Throwable {
        driver.findElement(By.linkText("Place Bet")).click();
    }

    @Then("^An unsuccessful message is displayed$")
    public void an_unsuccessful_message_is_displayed() throws Throwable {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assert.assertFalse("Bets accepted.", exists(By.className("c-selection-group__potential-returns")));
    }

    @Then("^A Bet Placed message is displayed$")
    public void a_Bet_Placed_message_is_displayed() throws Throwable {
        Assert.assertTrue("Bets not accepted.", exists(By.className("c-selection-group__potential-returns")));
        scrollIntoView(By.className("c-bet-slip-confirmation__header"));
    }

}
