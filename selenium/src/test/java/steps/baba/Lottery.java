package steps.baba;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import other.SeleniumTestTemplate;
import util.Utils;

public class Lottery extends SeleniumTestTemplate {

    @Given("^user choose a lottery draw$")
    public void user_choose_a_lottery_draw() throws Throwable {
        driver.findElement(By.id("play-lottery-tab")).click();
        driver.findElement(By.cssSelector("div.date-header")).click();

        if (exists(By.cssSelector("p.close-time"))) {
            driver.findElement(By.cssSelector("p.close-time")).click();
        } else {
            driver.findElement(By.cssSelector("ul.lotto-draws li")).click();
        }

    }

    @When("^user adds (\\d+) draw numbers$")
    public void user_adds_draw_numbers(int numberOfTips) throws Throwable {

        List<WebElement> cells;

        log.info("Finding outcomes to bet on ...");
        cells = driver
                .findElement(By.xpath("//div[contains(@class, 'lottery-grid')]"))
                .findElements(By.className("ball"));

        List<WebElement> outcomes = new ArrayList<>(cells);

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

    }

    @When("^user selects a (.*) bet$")
    public void user_selects_a_bet(String betType) throws Throwable {
        scrollIntoView(By.id(betType.toLowerCase()));
        driver.findElement(By.id(betType.toLowerCase())).click();

        scrollIntoView(By.cssSelector("label.stake > input[type='text']"));
        driver.findElement(By.cssSelector("label.stake > input[type='text']")).clear();
        driver.findElement(By.cssSelector("label.stake > input[type='text']")).sendKeys("50.00");
        driver.findElement(By.id("place-lottery-bet-button")).click();
    }

    @Then("^the bet must be placed successfully$")
    public void the_bet_must_be_placed_successfully() throws Throwable {
        Utils.waitSeconds(2);
        Assert.assertTrue("Bet could not be placed.", driver.findElement(By.cssSelector("li[class='dialog success']")).isDisplayed());
    }

}
