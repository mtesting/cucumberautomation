package steps.common;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import decoders.Decoder;
import decoders.DecoderManager;
import other.Constants;
import other.SeleniumTestTemplate;

public class SmokeTest extends SeleniumTestTemplate {

    @Given("^a \"([^\"]*)\" user$")
    public void a_user(String platform) throws Throwable {
        Constants.platformType = platform;
    }

    @When("^they access the homepage using \"([^\"]*)\"$")
    public void they_access_the_homepage_using(String browser) throws Throwable {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        log.info("Browser property set to: " + browser);
        Constants.browserName = browser;

        driver = initDriver(decoder.decodePunterUrl(Constants.CUSTOMER_IN_TEST));
    }

}
