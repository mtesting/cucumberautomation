import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features"
        , glue = {"steps.common", "steps.mybet"}
        , tags = {"@MyBet"}
        , monochrome = true
        , plugin = {"pretty", "html:target/html/", "json:target/reports.json"}
)

@Ignore
public class TestStuff {

    @BeforeClass
    public static void readAndSetProperties() {
        System.setProperty("driverType", "local");
        System.setProperty("browserName", "chrome");
        System.setProperty("headless", "true");
        //System.setProperty("platform", "Mac");
        System.setProperty("customer", "MYBET");
        System.setProperty("testingEnv", "DEMO");
    }

}