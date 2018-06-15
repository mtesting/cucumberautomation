package steps.argyll;

import org.openqa.selenium.By;

import cucumber.api.java.en.Then;
import other.SeleniumTestTemplate;

@Deprecated
public class SmokeTest extends SeleniumTestTemplate {

    @Then("^the homepage should load successfully$")
    public void the_homepage_should_load_successfully() throws Throwable {
        //Asserts for specific objects should be added
        if (!exists(By.id("searchInput"))) {
            log.error("Page not loaded successfully");
            throw new Error("Page not loaded successfully");
        }
        log.info("Page loaded successfully");
    }

}
