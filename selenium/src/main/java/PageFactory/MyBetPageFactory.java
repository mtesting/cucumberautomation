package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import other.Constants;
import other.SeleniumTestTemplate;

public class MyBetPageFactory extends SeleniumTestTemplate {

    @FindBy(id = "usernameInput")
    private WebElement loginUsername;

    @FindBy(id = "passwordInput")
    private WebElement loginPassword;

    @FindBy(id = "loginBtn")
    private WebElement loginButton;

    @FindBy(id = "logoutBtn")
    private WebElement logoutButton;

    @FindBy(css = "div[id='transactions'] span:nth-of-type(2)")
    private WebElement accountCashBalance;

    @FindBy(xpath = "//section[contains(@class, 'full-schedule-widget')]")
    private WebElement fullScheduleWidget;

    @FindBy(xpath = "//section[contains(@class, 'league-selector-widget')]")
    private WebElement leagueSelectorWidget;

    @FindBy(className = "betslip-icon")
    private WebElement betslipIcon;

    public MyBetPageFactory() {
    }

    public void initMyBetPageFactory() throws DecoderConfigException {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        driver = initDriver(decoder.decodePunterUrl(Constants.CUSTOMER_IN_TEST));
        PageFactory.initElements(driver, this);
    }

    public void setLoginUsername(String srtLoginUsername) {
        loginUsername.sendKeys(srtLoginUsername);
    }

    public void setLoginPassword(String strLoginPassword) {
        loginPassword.sendKeys(strLoginPassword);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void clickLogoutButton() {
        //moveOver(By.className("outer-bal"));
        logoutButton.click();
    }

    public boolean isUserLoggedIn(){
        return !exists(loginButton);
    }

    public boolean isIncorrectLoginMsgDisplayed(){
        if (!exists(By.xpath("//*[contains(text(), 'Username/password incorrect.')]"))) {
            return exists(By.className("icon-alert"));
        } else return true;
    }

    public void clickOnSelection(WebElement webElement){
        clickByActionOn(webElement);
    }

    public List<WebElement> findAvailableSelections(){
        List<WebElement> availableMarkets = findAvailableMarkets();
        List<WebElement> availableSelections = new ArrayList<>();
        for (WebElement row : availableMarkets) {
            List<WebElement> selectionCells = row.findElements(By.className("selection-view"));
            log.info("Potential selections found: " + selectionCells.size());

            for (WebElement selection : selectionCells) {
                try {
                    if (exists(By.className("price"), selection)) {
                        if (!selection.getText().contains("\n")) {
                            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                            if (selection.getTagName().equalsIgnoreCase("a")) {
                                try {
                                    WebElement lockIcon = selection.findElement(By.className("icon-lock"));
                                    if (!lockIcon.isDisplayed() && !selection.getText().isEmpty()) {
                                        availableSelections.add(selection);
                                        break;
                                    }
                                } catch (NoSuchElementException ignored) {
                                    availableSelections.add(selection);
                                    break;
                                }
                            }
                        }
                    }
                } catch (NoSuchElementException | StaleElementReferenceException ignored) {
                }
            }
        }

        driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        log.info("Selections added: " + availableSelections.size());
        return availableSelections;
    }

    private List<WebElement> findAvailableMarkets(){
        List<WebElement> cells;
        try{
            cells = fullScheduleWidget.findElements(By.className("multi-markets-view"));
        } catch (NoSuchElementException ignored){
            try {
                cells = leagueSelectorWidget.findElements(By.className("multi-markets-view"));
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("No markets widget found", e);
            }
        }
        return cells;
    }

    public String getAccountCashBalance(){
        return accountCashBalance.getText();
    }

    public boolean isBetslipIconized() {
        return exists(betslipIcon) && betslipIcon.isDisplayed();
    }

    public void clickOnBetslipIcon() {
        betslipIcon.click();
    }

    public List<WebElement> getTipsOnBetslip(){
        return driver.findElement(By.id("betslip")).findElements(By.className("selection-group"));
    }

    public MyBetBetslipPageFactory betSlip(){
        return new MyBetBetslipPageFactory();
    }

}
