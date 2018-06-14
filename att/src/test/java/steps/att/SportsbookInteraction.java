package steps.att;

import org.apache.log4j.Logger;

import apiLevelInteraction.SportsbookHelper;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import other.Constants;

public class SportsbookInteraction {

    private static final Logger log = Logger.getLogger(SportsbookInteraction.class);
    private final SportsbookHelper sportsbook;
    private Scenario scenario;

    public SportsbookInteraction(SportsbookHelper sportsbook) {
        this.sportsbook = sportsbook;
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("^the User is logged onto the customer Sportsbook$")
    public void theUserIsLoggedOntoTheCustomerSportsbook() throws Throwable {
        log.info("-- STEP -- Sportsbook login");
        if (Constants.CUSTOMER_IN_TEST.equalsIgnoreCase("betstars")) {

            String accountName;
            String[] parts = scenario.getId().split(";");
            String featureName = parts[0];
            log.info("Feature name: " + featureName);
            switch (featureName) {
                case "bets-cash-out":
                    accountName = "WWIFXSZZZZZ";
                    break;
                case "hr-bets-settlement":
                    accountName = "WWFCFGZZZZZ";
                    break;
                case "hr-bets-placement":
                    accountName = "ZJKBCKZZZZZ";
                    break;
                case "football-bets-placement":
                    accountName = "WCDVRVZZZZZ";
                    break;
                case "hr-rule4":
                    accountName = "WUEYMFZZZZZ";
                    break;
                case "post-bet-placement":
                    accountName = "MLPTNQZZZZZ";
                    break;
                default:
                    accountName = "LEIYZMZZZZZ";
                    log.warn("Default user will be used for scenario=" + scenario.getId());
                    break;
            }
            log.info("Sportsbook account set to " + accountName);

            sportsbook.externalLogin(accountName);
            sportsbook.setExternalBalance("5000");
        } else {
            sportsbook.login("frantzes", "Test1234");
        }

    }
}
