package other;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import thirdparty.SauceHelpers;
import util.StringEncrypt;


public abstract class SeleniumTestTemplate extends SeleniumUtils {

    protected static final Logger log = Logger.getLogger(SeleniumTestTemplate.class);
    protected static WebDriver driver;
    private DesiredCapabilities capabilities;

    /**
     * Starts the instance for a local or remote WebDriver depending on the given config
     *
     * @param webUrl initial url
     * @return WebDriver set up
     */
    protected WebDriver initDriver(String webUrl) {
        log.info("DRIVER_TYPE=" + Constants.DRIVER_TYPE);
        if ("local".equalsIgnoreCase(Constants.DRIVER_TYPE)) {
            setDriverBrowser();
            driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.get(webUrl);
            findPunterVersion();
            return driver;
        } else {
            return initRemoteDriver(webUrl);
        }
    }

    /**
     * Function to instance a RemoteWebDriver and open the initial url
     *
     * @param webUrl url to be initially opened
     * @return RemoteWebDriver
     */
    private WebDriver initRemoteDriver(String webUrl) {
        final String nodeUrl = "https://" + Constants.SAUCELABS_USER + ":"
                + StringEncrypt.decryptXOR(Constants.SAUCELABS_ACCESS_KEY)
                + Constants.SELENIUM_HUB_URL + "/wd/hub";
        //String nodeUrl = Constants.SELENIUM_HUB_URL + "/wd/hub";
        log.info("Pointing selenium hub to: " + nodeUrl);
        try {
            driver = new RemoteWebDriver(new URL(nodeUrl), setRemoteDriverCapabilities());
        } catch (MalformedURLException e) {
            log.error(e);
        }
        driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (!"android".equalsIgnoreCase(Constants.browserName)) {
            if (!"edge".equalsIgnoreCase(Constants.browserName)) {
                driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
                driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
            }
        }

        driver.get(webUrl);

        if (exists(By.id("overridelink"))) { //Checks a possible certificate issue on IE
            driver.get("javascript:document.getElementById('overridelink').click();");
            log.info("Click on accept certificate visible");
        }

        findPunterVersion();
        return driver;
    }

    /**
     * Method for local WebDriver executions
     * Sets the driver browser reading the global variable browserName
     * geckodriver 0.11.1, chromedriver 2.24.41
     */
    private void setDriverBrowser() {
        log.info("Browser name read: " + Constants.browserName);
        log.info("OS name property read: " + System.getProperty("os.name"));
        if (Constants.browserName != null) {
            switch (Constants.browserName) {
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", getWebDriverPath("geckodriver"));
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    ChromeOptions options = new ChromeOptions();
                    if("true".equalsIgnoreCase(System.getProperty("headless"))){
                        options.setHeadless(true);
                        options.addArguments("window-size=1200x600");
                    }
                    driver = new ChromeDriver(options);
                    break;
                case "ie":
                    System.setProperty("webdriver.ie.driver", getWebDriverPath("IEDriverServer"));
                    driver = new InternetExplorerDriver();
                    break;
                case "chromeMobile":
                    Map<String, String> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceName", Constants.androidDeviceName);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    log.info("The browser name doesn't match with any config, Chrome will be set by default");
                    driver = new ChromeDriver();
                    break;
            }
        } else {
            log.warn("The browserName property is null, Chrome will be set by default");
            driver = new ChromeDriver();
        }
    }

    /**
     * Picks web driver files from the source for Linux64, Windows64 and MacOS
     *
     * @param driver name
     * @return driver path at the local build
     */
    private String getWebDriverPath(String driver) {
        URL idUrl;
        String osName = System.getProperty("os.name");
        log.info("OS name property read: " + osName);
        if ("Windows".equalsIgnoreCase(osName)) {
            idUrl = getClass().getClassLoader().getResource("webdriversWindows/" + driver + ".exe");
        } else if ("Linux".equalsIgnoreCase(osName)) {
            idUrl = getClass().getClassLoader().getResource("webdriversLinux/" + driver);
            try {
                log.info("Driver path: " + idUrl.getPath());
                Runtime.getRuntime().exec("chmod u+x " + idUrl.getPath());
            } catch (IOException | NullPointerException e) {
                log.error("Error trying to retrieve the driver path", e);
                return "";
            }
        } else if ("OS X".equalsIgnoreCase(osName) || "Mac".equalsIgnoreCase(osName)) {
            idUrl = getClass().getClassLoader().getResource("webdriversMac/" + driver);
        } else {
            log.warn("OS name detected doesn't match with any driver config");
            return "";
        }
        return idUrl.getPath();
    }

    /**
     * Generates the RemoteWebDriver capabilities
     * Using Windows the webdriver.ie.driver and webdriver.gecko.driver will be set up at the selenium node start up
     *
     * @return capabilities with browser and OS set
     */
    private Capabilities setRemoteDriverCapabilities() {
        setCapabilitiesBrowser();
        setCapabilitiesBrowserVersion();
        setCapabilitiesPlatform();
        capabilities.setCapability("screenResolution", "1280x768");
        capabilities.setCapability("build", Constants.BUILD_TAG);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        SauceHelpers.addSauceConnectTunnelId(capabilities);
        capabilities.setCapability("recordScreenshots", true);
        capabilities.setCapability("recordVideo", Constants.SAUCELABS_RECORDSCREEN);
        return capabilities;
    }

    /**
     * Sets the capabilities browser version reading the global variable browserVersion
     */
    private void setCapabilitiesBrowserVersion() {
        if (Constants.browserVersion != null) {
            capabilities.setCapability("version", Constants.browserVersion);
            log.info("Browser version set to: " + Constants.browserVersion);
        } else {
            log.info("No browser version specified");
        }
    }

    /**
     * Sets the capabilities browser reading the global variable browserName
     */
    private void setCapabilitiesBrowser() {
        if (Constants.browserName != null) {
            log.info("Browser name read: " + Constants.browserName);
            switch (Constants.browserName) {
                case "firefox":
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setAcceptUntrustedCertificates(true);
                    capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability(FirefoxDriver.PROFILE, profile); //won't work on v51 https://bugzilla.mozilla.org/show_bug.cgi?id=1103196
                    break;
                case "chrome":
                    capabilities = DesiredCapabilities.chrome();
                    break;
                case "ie":
                    capabilities = DesiredCapabilities.internetExplorer();
                    break;
                case "edge":
                    capabilities = DesiredCapabilities.edge();
                    break;
                case "safari":
                    capabilities = DesiredCapabilities.safari();
                    break;
                case "opera":
                    capabilities = DesiredCapabilities.operaBlink();
                    break;
                case "android":
                    capabilities = DesiredCapabilities.android();
                    break;
                case "iphone":
                    capabilities = DesiredCapabilities.iphone();
                    break;
                case "ipad":
                    capabilities = DesiredCapabilities.ipad();
                    break;
                case "chromeMobile":
                    Map<String, String> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceName", Constants.androidDeviceName);
                    Map<String, Object> chromeOptions = new HashMap<>();
                    chromeOptions.put("mobileEmulation", mobileEmulation);
                    capabilities = DesiredCapabilities.chrome();
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    break;
                default:
                    log.info("No valid browser read, Chrome will be set by default");
                    capabilities = DesiredCapabilities.chrome();
                    break;
            }
        } else {
            log.warn("The browserName property is null, Chrome will be set by default");
            capabilities = DesiredCapabilities.chrome();
        }
    }

    /**
     * Sets the capabilities platform reading the global variable platformType
     */
    private void setCapabilitiesPlatform() {
        if (Constants.platformType != null) {
            log.info("Platform property read: " + Constants.platformType);
            switch (Constants.platformType) {
                case "Windows8":
                    capabilities.setPlatform(Platform.WIN8);
                    break;
                case "Windows8.1":
                    capabilities.setPlatform(Platform.WIN8_1);
                    break;
                case "Windows10":
                    capabilities.setPlatform(Platform.WIN10);
                    break;
                case "Windows":
                    capabilities.setPlatform(Platform.WINDOWS);
                    break;
                case "Linux":
                    capabilities.setPlatform(Platform.LINUX);
                    capabilities.setCapability("marionette", false); //Added for FF version < 48
                    break;
                case "Mac":
                    capabilities.setPlatform(Platform.MAC);
                    break;
                case "ElCapitan":
                    capabilities.setPlatform(Platform.EL_CAPITAN);
                    break;
                case "iOS":
                    capabilities.setPlatform(Platform.IOS);
                    break;
                case "Android":
                    capabilities.setPlatform(Platform.ANDROID);
                    break;
                default:
                    log.warn("No valid platform read");
                    break;
            }
        } else {
            log.warn("The platformType property is null");
        }
    }

    public static String getSessionId(){
        return (((RemoteWebDriver) SeleniumTestTemplate.driver).getSessionId()).toString();
    }

}
