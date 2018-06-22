package steps.common;

import org.apache.log4j.Logger;

import att.events.EventManager;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import other.PropertyReader;

public class Hook {

    private static final Logger log = Logger.getLogger(Hook.class);

    /**
     * Method executed before each cucumber scenario
     */
    @Before
    public void readAndSetProperties(Scenario scenario) {
        log.info("Properties set for customer=" + System.getProperty("customer") + ", env=" + System.getProperty("testingEnv"));
        log.info("Executing scenario: " + scenario.getName() + ", tags=" + scenario.getSourceTagNames());
        setSportType(scenario);
        PropertyReader reader = new PropertyReader();
        log.info("Loading properties file..");
        for (String key : reader.getProperties().stringPropertyNames()) {
            log.debug("Property read: " + key + "=" + reader.getProperties().getProperty(key));
            System.setProperty(key, reader.getProperties().getProperty(key));
        }
        log.info("Properties file read");
        overwriteProperties();
    }

    private void setSportType(Scenario scenario){
        for(Object sport : scenario.getSourceTagNames().toArray()){
            String sportType = sport.toString().substring(1);
            for(EventManager.SportType object : EventManager.SportType.values()){
                if (sportType.equalsIgnoreCase(object.value())){
                    System.setProperty("sport", sportType.toUpperCase());
                }
            }
        }
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

}
