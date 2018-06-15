package other;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import util.Utils;

import static other.SeleniumTestTemplate.driver;

public abstract class SeleniumUtils {

    private static final Logger log = Logger.getLogger(SeleniumUtils.class);

    protected boolean exists(WebElement webElement) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException ignored) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    protected boolean exists(By by, WebElement parent) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            parent.findElement(by);
            return true;
        } catch (NoSuchElementException ignored) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }

    }

    protected boolean exists(By by) {
        return exists(by, driver);
    }

    private boolean exists(By by, SearchContext searchContext) {
        try {
            return null != searchContext.findElement(by);
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }

    /**
     * Returns the file path for the given file name
     *
     * @param fileName folder/fileName
     * @return file absolute path
     * @throws URISyntaxException wrong file name
     */
    protected String getSourceAsAbsolutePath(String fileName) throws URISyntaxException {
        URL idUrl = getClass()
                .getClassLoader()
                .getResource(fileName);
        if (idUrl != null) {
            log.info(new File(idUrl.toURI()).getAbsolutePath());
            return new File(idUrl.toURI()).getAbsolutePath();
        } else {
            log.warn("File not found");
            return null;
        }
    }

    /**
     * Performs a click through java script
     *
     * @param by element locator
     */
    protected void javascriptClick(By by) {
        WebElement element = driver.findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * A FluentWait checks the visibility of an object
     *
     * @param by element locator
     */
    protected void waitFor(By by) {
        Wait wait = new FluentWait(driver)
                .withTimeout(Duration.of(Constants.DEFAULT_TIMEOUT, ChronoUnit.SECONDS))
                .pollingEvery(Duration.of(5, ChronoUnit.SECONDS))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Scrolls the webpage view to an specific element
     *
     * @param by element locator
     */
    protected void scrollIntoView(By by) {
        WebElement element = driver.findElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
        Utils.waitSeconds(1);
    }

    protected void clickByActionOn(WebElement element) {
        Actions clickAction = new Actions(driver);
        clickAction.moveToElement(element).click().perform();
        log.info("Clicked by action on " + element + " successfully.");
        Utils.waitSeconds(1);
    }

    protected void clickByActionOn(By by) {
        WebElement element = driver.findElement(by);
        Actions clickAction = new Actions(driver);
        clickAction.moveToElement(element).click().perform();
        log.info("Clicked by action on " + element + " successfully.");
        Utils.waitSeconds(1);
    }

    /**
     * Moves the mouse pointer over an specific element
     *
     * @param by element locator
     */
    protected void moveOver(By by) {
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(by);
        //action.moveToElement(we);
        action.moveToElement(we).build().perform();
    }

    protected void checkBox(WebElement element, String linkText) {
        WebElement checkBox = element.findElement(By.xpath("//*[text() = '" + linkText + "']"));
        checkBox.click();
        log.info("Clicked " + checkBox + " (WebElement) successfully.");
    }

    protected boolean isBetslipIconized() {
        By by = By.className("betslip-icon");
        return exists(by) && driver.findElement(by).isDisplayed();
    }

    /**
     * Logs the punter version reading it from the page source
     */
    void findPunterVersion() {
        List<WebElement> scriptTags = driver.findElements(By.tagName("script"));
        String prefix = "/js/app-";
        String postfix = ".min.js";
        try {
            for (WebElement scriptTag : scriptTags) {
                String scriptSource = scriptTag.getAttribute("src");
                if (scriptSource.contains(prefix) && scriptSource.contains(postfix)) {
                    String punterVersion = scriptSource.substring(scriptSource.indexOf(prefix) + prefix.length(),
                            scriptSource.lastIndexOf(postfix));
                    log.info("Punter version: " + punterVersion);
                    return;
                }
            }
        } catch (Exception e) {
            log.warn("Punter version not found.", e);
        }
    }

    /**
     * Performs the selections of an option into a select list
     *
     * @param by select element locator
     * @param option select option value
     */
    protected void selectElement(By by, String option) {
        Select select = new Select(driver.findElement(by));
        select.selectByVisibleText(option);
        log.debug("Select option selected: " + option);
    }

}
