package steps.baba;

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
import other.Constants;
import other.SeleniumTestTemplate;

public class PlaceBet extends SeleniumTestTemplate {

    @When("^User clicks on \"([^\"]*)\" selections$")
    public void user_clicks_on_selections(Integer selectionsQuantity) throws Throwable {
        int numberOfTips = selectionsQuantity;
        List<WebElement> outcomes = new ArrayList<>();
        List<WebElement> cells;

        log.info("Finding outcomes to bet on ...");
        cells = driver
                .findElement(By.xpath("//section[contains(@class, 'include')]"))
                .findElements(By.className("pill-price"));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        for (WebElement cell : cells) {
            try {
                if (cell.getTagName().equalsIgnoreCase("a")) {
                    try {
                        if (cell.getText().substring(0, 1).equalsIgnoreCase("x")) { //Check the selection type to only 1 per event
                            outcomes.add(cell);
                        }
                    } catch (NoSuchElementException ignored) {
                        outcomes.add(cell);
                    }
                }
                if (outcomes.size() > 40) {
                    break;
                }
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {
            }
        }

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
        /*if (isBetslipIconized()) {
            driver.findElement(By.className("betslip-icon")).click();
        }*/
        List<WebElement> tipsOnBetslip =
                //driver.findElement(By.id("betslip")).findElements(By.className("selection-group"));
                driver.findElement(By.id("bet-slip")).findElements(By.cssSelector("li[id^='single-bet-li-bet-']"));
        Assert.assertTrue("No tips on betslip.", tipsOnBetslip.size() > 0);
        Assert.assertTrue("Too many tips on betslip", tipsOnBetslip.size() <= numberOfTips);
    }

    @And("^User enters a \"([^\"]*)\" bet amount \"([^\"]*)\"$")
    public void user_enters_a_bet_amount(String betType, String stake) throws Throwable {

        /*if(!exists(By.cssSelector("div[class='bet-slip-container open']"))){
            driver.findElement(By.className("icon-betslip")).click();
        }*/

        if (betType.equalsIgnoreCase("Single")) {
            scrollIntoView(By.xpath("id('bets-singles')")); // scroll field "Singles" of betslip into view so you can click on it
            //driver.findElement(By.xpath("id('bet-slip')//div[@class='row stakeBox']")).click(); // open input field
            driver.findElement(By.xpath("id('bet-slip')//div[@class='row stakeBox']//input")).sendKeys(stake);
        } else if (betType.equalsIgnoreCase("Multiple")) {
            scrollIntoView(By.id("bets-multiples"));
            if (!driver.findElement(By.xpath("id('bets-multiples')//input")).isDisplayed()) {
                driver.findElement(By.xpath("id('bets-multiples')")).click();
            }
            driver.findElement(By.xpath("id('bets-multiples')//input")).sendKeys(stake);
        } else if (betType.equalsIgnoreCase("System")) {
            if (exists(By.cssSelector("i[class='icon-angle-left']"))) {
                driver.findElement(By.id("TREBLE")).click();
            }
            scrollIntoView(By.id("TREBLE"));
            driver.findElement(By.xpath("//*[@id='TREBLE']//input")).sendKeys(stake);
        }

        //scrollIntoView(By.xpath("id('betslip')//li[@class='higher-odds']"));
        //checkBox(driver.findElement(By.xpath("id('betslip')//li[@class='higher-odds']")), "Accept all odds changes");
    }

    @And("^User clicks on Place Bet$")
    public void user_clicks_on_Place_Bet() throws Throwable {
        scrollIntoView(By.id("place-bet-button"));
        driver.findElement(By.id("place-bet-button")).click();
    }

    @Then("^A Bet Placed message is displayed$")
    public void a_Bet_Placed_message_is_displayed() throws Throwable {
        Assert.assertTrue("Bet could not be placed.", driver.findElement(By.cssSelector("li[class='dialog success']")).isDisplayed());
    }

    @Then("^An unsuccessful message is displayed$")
    public void an_unsuccessful_message_is_displayed() throws Throwable {
        Assert.assertFalse("Bet could not be placed.", driver.findElement(By.className("dialog success")).isDisplayed());
    }

}
