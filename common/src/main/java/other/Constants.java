package other;

public class Constants {

    /**
     * Read from properties file
     */
    public static final String SELENIUM_HUB_URL = System.getProperty("selenium.hub.url");
    public static final String MYBET_DEMO_PUNTER_URL = System.getProperty("mybet.demo.url");
    public static final String MYBET_AUTO_PUNTER_URL = System.getProperty("mybet.auto.url");
    public static final String MYBET_STAGE_PUNTER_URL = System.getProperty("mybet.stage.url");
    public static final String MYBET_UAT_URL = System.getProperty("mybet.uat.url");
    public static final String MYBET_DEMO_CMS_URL = System.getProperty("mybet.demo.cms");
    public static final String MYBET_STAGE_CMS_URL = System.getProperty("mybet.stage.cms");
    public static final String MYBET_DEMO_BACKOFFICE_URL = System.getProperty("mybet.demo.backoffice");
    public static final String MYBET_STAGE_BACKOFFICE_URL = System.getProperty("mybet.stage.backoffice");
    public static final String BOB_DEV_URL = System.getProperty("bob.dev.url");
    public static final String BOB_UAT_URL = System.getProperty("bob.uat.sportsbook");
    public static final String GANA_URL = System.getProperty("gana.url");
    public static final String REDZONE_DEV_URL = System.getProperty("redzone.dev.url");
    public static final String BABA_UAT_URL = System.getProperty("baba.uat.url");
    public static final String BETSTARS_URL = System.getProperty("betstars.url");
    public static final String BETSTARS_RAM_URL = System.getProperty("betstars.ram.url");
    public static final String SPORTBOOK_USER = System.getProperty("sportbook.user");
    public static final String MYBET_PASSWORD = System.getProperty("mybet.password");
    public static final String MYBET_BACKOFFICE_USER = System.getProperty("mybet.backoffice.user");
    public static final String MYBET_BACKOFFICE_PASSWORD = System.getProperty("mybet.backoffice.password");
    public static final String BOB_PASSWORD = System.getProperty("bob.password");
    public static final long DEFAULT_TIMEOUT = Long.parseLong(System.getProperty("default.timeout"));
    public static final long PAGE_LOAD_TIMEOUT = Long.parseLong(System.getProperty("page.load.timeout"));
    public static final String SAUCELABS_USER = System.getProperty("saucelabs.user");
    public static final String SAUCELABS_ACCESS_KEY = System.getProperty("saucelabs.access.key");
    public static final String MYBET_DEMO_DB_SPORTSBOOK_URL = System.getProperty("mybet.demo.db.sportsbook.url");
    public static final String MYBET_DEMO_DB_BETCATCH_URL = System.getProperty("mybet.demo.db.betcatch.url");
    public static final String MYBET_STAGE_DB_SPORTSBOOK_URL = System.getProperty("mybet.stage.db.sportsbook.url");
    public static final String MYBET_STAGE_DB_BETCATCH_URL = System.getProperty("mybet.stage.db.betcatch.url");
    public static final String MYBET_AUTO_DB_SPORTSBOOK_URL = System.getProperty("mybet.auto.db.sportsbook.url");
    public static final String MYBET_AUTO_DB_BETCATCH_URL = System.getProperty("mybet.auto.db.betcatch.url");
    public static final String POKERSTARS_DB_URL = System.getProperty("pokerstars.db.url");
    public static final String POKERSTARS_STAGING_PUNTER_URL = System.getProperty("pokerstars.staging.url");
    public static final String POKERSTARS_STAGING_TRADING_URL = System.getProperty("pokerstars.staging.trading.url");
    public static final String POKERSTARS_STAGING_DB_URL = System.getProperty("pokerstars.staging.db.url");
    public static final String POKERSTARS_STAGING_ATT = System.getProperty("pokerstars.staging.att");
    public static final String POKERSTARS_BENCHMARK_DB_URL = System.getProperty("pokerstars.benchmark.db.url");
    public static final String POKERSTARS_BENCHMARK_PUNTER_URL = System.getProperty("pokerstars.benchmark.url");
    public static final String POKERSTARS_BENCHMARK_TRADING_URL = System.getProperty("pokerstars.benchmark.trading.url");
    public static final String POKERSTARS_BENCHMARK_ATT = System.getProperty("pokerstars.benchmark.att");
    public static final String POKERSTARS_UAT_PUNTER_URL = System.getProperty("pokerstars.uat.url");
    public static final String POKERSTARS_QACORE_PUNTER_URL = System.getProperty("pokerstars.qacore.url");
    public static final String ATS_DB_USER = System.getProperty("ats.db.user");
    public static final String BF_PPBF_DB_URL = System.getProperty("betfair.ppbf.db");
    public static final String BF_PPBF_DB_PASSWORD = System.getProperty("betfair.ppbf.db.pwd");
    public static final String BF_PPBF_ATT = System.getProperty("betfair.ppbf.att");
    public static final String MYBET_DB_PASSWORD = System.getProperty("mybet.db.password");
    public static final String androidDeviceName = System.getProperty("android.devicename");
    public static final String CORAL_DEV2_URL = System.getProperty("coral.dev2.url");
    public static final String CORAL_DEV2_DB = System.getProperty("coral.dev2.db");
    public static final String LADBROKES_TST1_URL = System.getProperty("ladbrokes.tst1.url");
    public static final String LADBROKES_TST1_DB = System.getProperty("ladbrokes.tst1.db");
    public static final String LADBROKES_TST1_ENDPOINT = System.getProperty("ladbrokes.tst1.endpoint");
    public static final String LADBROKES_TST1_ATT = System.getProperty("ladbrokes.tst1.att");
    public static final String LADBROKES_TST1_ATS_DB_PWD = System.getProperty("ladbrokes.tst1.ats.db.pwd");
    public static final String INTRALOT_TEST1_URL = System.getProperty("intralot.test1.url");
    public static final String INTRALOT_AUTO_URL = System.getProperty("intralot.auto.url");
    public static final String INTRALOT_TEST1_TRADING_URL = System.getProperty("intralot.test1.trading.url");
    public static final String INTRALOT_AUTO_NATS_URL = System.getProperty("intralot.auto.nats.url");
    public static final String INTRALOT_TEST1_DB_URL = System.getProperty("intralot.test1.db.url");
    public static final String INTRALOT_AUTO_TRADING_URL = System.getProperty("intralot.auto.trading.url");
    public static final String INTRALOT_AUTO_DB_URL = System.getProperty("intralot.auto.db.url");
    public static final String BETFAIR_PPBF_TRADING_URL = System.getProperty("betfair.ppbf.trading.url");
    public static final boolean SAUCELABS_RECORDSCREEN = Boolean.parseBoolean(System.getProperty("saucelabs.recordscreen"));
    public static final String CUSTOMER_IN_TEST = System.getProperty("customer");
    public static final String DRIVER_TYPE = System.getProperty("driverType");
    public static final String TESTING_ENV = System.getProperty("testingEnv");
    public static final String HR_INPUT_EXCEL = "hrNHorses_NR.xlsx";
    public static final String BUILD_TAG = setBuildTag();
    public static String platformType = System.getProperty("platform.type");
    public static String browserName = System.getProperty("browser.name");
    public static String browserVersion;
    public static final String ALGO_ATT_URL = System.getProperty("algo.att");
    public static final String ALGO_DB_URL = System.getProperty("algo.dev.db");
    public static final String BETRADAR_MIRROR_INPUT = System.getProperty("betradar.mirror.input");
    public static final String REPLAY_DATASET = System.getProperty("replay.dataset");


    private Constants() {
    }

    /**
     * Jenkins use the env variable BUILD_TAG
     *
     * @return build identifier
     */
    private static String setBuildTag() {
        String build = System.getenv("BUILD_TAG");
        if (build != null) {
            return build;
        } else {
            return System.getenv("SAUCE_BAMBOO_BUILDNUMBER");
        }
    }

}
