package steps.betstars;

import org.openqa.selenium.By;

import cucumber.api.java.en.Then;
import other.SeleniumTestTemplate;

public class SmokeTest extends SeleniumTestTemplate {

    @Then("^the homepage should load successfully$")
    public void the_homepage_should_load_successfully() throws Throwable {
        //Asserts for specific objects should be added
        if (!exists(By.id("tab-nav-betslip"))) {//loginButton
            if (!exists(By.id("loginButton"))) {
                if (!exists(By.className("sportsList__label"))) {
                    log.error("Page not loaded successfully");
                    throw new Error("Page not loaded successfully");
                }
            }
        }

        if (!exists(By.id("siteHeader"))) {
            throw new Error("Page header not loaded successfully");
        }
        if (!exists(By.id("footer"))) {
            throw new Error("Page footer not loaded successfully");
        }

        log.info("Page loaded successfully");
    }

}
