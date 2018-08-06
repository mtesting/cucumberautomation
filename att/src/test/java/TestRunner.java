import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features"
        , glue = {"steps.common", "steps.att", "steps.trader"}
        //,tags = {"@F-BrTxBets"}
        ,tags = {"@T-imgE2E"}
        , monochrome = true
        , plugin = {"pretty", "html:target/html/", "json:target/reports.json"}
)


@Ignore
public class TestRunner {

    @BeforeClass
    public static void readAndSetProperties() {
        System.setProperty("customer", "BETSTARS");
        System.setProperty("testingEnv", "STAGING");

        //System.setProperty("customer", "LADBROKES");
        //System.setProperty("testingEnv", "AWS");

        //System.setProperty("customer", "INTRALOT");
        //System.setProperty("testingEnv", "AUTO");

        //System.setProperty("customer", "BETFAIR");
        //System.setProperty("testingEnv", "PPBF");

        //System.setProperty("customer", "MYBET");
        //System.setProperty("testingEnv", "AUTO");
    }

}