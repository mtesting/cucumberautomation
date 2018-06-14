package steps.mybet;

import org.openqa.selenium.By;

import cucumber.api.java.en.Then;
import other.SeleniumTestTemplate;

public class SmokeTest extends SeleniumTestTemplate {

    @Then("^the homepage should load successfully$")
    public void the_homepage_should_load_successfully() throws Throwable {
        //Asserts for specific objects should be added
        if (!exists(By.id("betslip"))) {//Check if the betslip is there for the sportsbook webs
            if (!exists(By.id("mybet-content"))) { //Check for the casino web
                log.error("Page not loaded successfully");
                throw new Error("Page not loaded successfully");
            }
        }
        if (!exists(By.id("header"))) {
            throw new Error("Page not loaded successfully");
        }
        if (!exists(By.className("site-footer"))) {
            throw new Error("Page not loaded successfully");
        }
        log.info("Page loaded successfully");
    }

}
