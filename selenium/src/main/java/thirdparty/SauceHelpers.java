package thirdparty;

import com.saucelabs.saucerest.SauceREST;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

import cucumber.api.Scenario;
import other.Constants;
import other.SeleniumTestTemplate;
import util.StringEncrypt;

public class SauceHelpers {

    private static final Logger log = Logger.getLogger(SauceHelpers.class);

    private SauceHelpers() {
    }

    /**
     * Updates the test job with the test name, build and result
     *
     * @param scenario cucumber scenario
     */
    public static void updateSauceLabs(Scenario scenario) {
        SauceREST client = new SauceREST(Constants.SAUCELABS_USER,
                StringEncrypt.decryptXOR(Constants.SAUCELABS_ACCESS_KEY)
                );
        Map<String, Object> updates = new HashMap<>();
        String sessionId = (((RemoteWebDriver) SeleniumTestTemplate.driver).getSessionId()).toString();

        updates.put("build", Constants.BUILD_TAG);
        updates.put("name", scenario.getName());

        if (scenario.isFailed()) {
            updates.put("passed", false);
        } else {
            updates.put("passed", true);
        }

        client.updateJobInfo(sessionId, updates);
    }

    /**
     * Adds/updates sauce tunnel id to desired capabilities in place
     *
     * @param desiredCapabilities desired caps
     * @param tunnelId tunnel id
     */
    private static void addSauceConnectTunnelId(DesiredCapabilities desiredCapabilities, String tunnelId) {
        if (tunnelId == null || tunnelId.length() == 0) {
            tunnelId = System.getenv("TUNNEL_IDENTIFIER");
        }

        if (tunnelId != null && tunnelId.length() > 0) {
            desiredCapabilities.setCapability("tunnel-identifier", tunnelId);
        }
        log.info("Tunnel identifier set to: " + tunnelId);
    }

    /**
     * Adds/updates sauce tunnel id to desired capabilities from env in place
     *
     * @param desiredCapabilities desired caps
     */
    public static void addSauceConnectTunnelId(DesiredCapabilities desiredCapabilities) {
        addSauceConnectTunnelId(desiredCapabilities, null);
    }

}
