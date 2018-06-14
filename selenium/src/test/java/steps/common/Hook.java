package steps.common;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import entities.Customer;
import other.Constants;
import other.PropertyReader;
import other.SeleniumTestTemplate;
import thirdparty.SauceHelpers;

public class Hook extends SeleniumTestTemplate {

    private Customer account;

    public Hook(Customer account) {
        this.account = account;
    }

    /**
     * Overwrites the properties file values in case user sent a param through Jenkins
     */
    private static void overwriteProperties() {
        if (null != System.getProperty("browserName")) {
            System.setProperty("browser.name", System.getProperty("browserName"));
            log.info("browserName parameter read: " + System.getProperty("browserName"));
        }
        if (null != System.getProperty("platform")) {
            System.setProperty("platform.type", System.getProperty("platform"));
            log.info("platform parameter read: " + System.getProperty("platform"));
        }
        if (null != System.getProperty("userName")) {
            System.setProperty("sportbook.user", System.getProperty("userName"));
            log.info("userName parameter read: " + System.getProperty("userName"));
        }
        if (null != System.getProperty("dataset")) {
            System.setProperty("replay.dataset", System.getProperty("dataset"));
            log.info("dataset parameter read: " + System.getProperty("dataset"));
        } else {
            System.setProperty("replay.dataset", "");
        }
    }

    /**
     * Method executed after each cucumber scenario
     * If the test ran on Selenium it takes an screenshot, ends the driver session and updates SauceLabs and
     * @param scenario current scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        if (scenario != null && driver != null) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
            if (!"local".equalsIgnoreCase(Constants.DRIVER_TYPE)) {
                SauceHelpers.updateSauceLabs(scenario);
            }
            log.info("This test was executed on: " + Constants.browserName + "-" + Constants.platformType);
            driver.close();
            driver.quit();
        }
    }

    /**
     * Method executed before each cucumber scenario
     */
    @Before
    public void readAndSetProperties(Scenario scenario) {
        log.info("Properties set for customer=" + System.getProperty("customer") + ", env=" + System.getProperty("testingEnv"));
        log.info("Executing scenario: " + scenario.getName() + ", tags=" + scenario.getSourceTagNames());
        System.setProperty("sport", scenario.getSourceTagNames().toArray()[0].toString());
        PropertyReader reader = new PropertyReader();
        log.info("Loading properties file..");
        for (String key : reader.getProperties().stringPropertyNames()) {
            log.debug("Property read: " + key + "=" + reader.getProperties().getProperty(key));
            System.setProperty(key, reader.getProperties().getProperty(key));
        }
        log.info("Properties file read");
        overwriteProperties();
    }

    /**
     * Checks the Feature name and assigns a unique user to avoid login crashes when executing in parallel
     * @param scenario scenario under execution
     */
    @Before("@selenium")
    public void assignUniqueUser(Scenario scenario) {
        log.info("Assigning unique user for scenario id=" + scenario.getId());
        String[] parts = scenario.getId().split(";");
        String featureName = parts[0];
        log.info("Feature name=" + featureName);
        switch (featureName) {
            case "chips-transactions":
                account.setUsername("autotest1");
                break;
            case "deposit-action":
                account.setUsername("autotest2");
                break;
            case "document-upload-action":
                account.setUsername("autotest3");
                break;
            case "exclusion":
                account.setUsername("autotest4");
                break;
            case "login-action":
                account.setUsername("autotest5");
                break;
            case "bets-cash-out":
                account.setUsername("autotest5");
                break;
            case "display-transactions":
                account.setUsername("autotest6");
                break;
            case "bets-placement":
                account.setUsername("frantzes");
                break;
            case "logout-action":
                account.setUsername("autotest7");
                break;
            case "withdraw-action":
                account.setUsername("autotest8");
                break;
            case "limits":
                account.setUsername("autotest9");
                break;
            case "lottery":
                account.setUsername("autotest10");
                break;
            case "bets-settlement":
                account.setUsername("autotest10");
                break;
            default:
                account.setUsername(System.getProperty("sportbook.user"));
                log.warn("Default user will be used for scenario=" + scenario.getId());
                break;
        }
        log.info("Account set to username=" + account.getUsername() + ", for feature=" + featureName);
    }

}
