package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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

    @FindBy(id = "place-bet")
    private WebElement placeBetButton;

    @FindBy(css = "li[class=slip-summary__total-stake] > span:nth-child(2)")
    private WebElement betTotalStake;

    @FindBy(css = "li[class=slip-summary__percentage-tax] > span:nth-child(2)")
    private WebElement betTaxPercentage;

    @FindBy(css = "div[id='transactions'] span:nth-of-type(2)")
    private WebElement accountCashBalance;

    @FindBy(xpath = "id('betslip')//li[@class='higher-odds']")
    private WebElement higherOddsCheckbox;

    @FindBy(xpath = "//section[contains(@class, 'full-schedule-widget')]")
    private WebElement fullScheduleWidget;

    @FindBy(xpath = "//section[contains(@class, 'league-selector-widget')]")
    private WebElement leagueSelectorWidget;

    @FindBy(css = "div.betslip-success")
    private WebElement betsPlacementSuccessMsg;

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

    public void clickPlaceBetButton() {
        scrollIntoView(placeBetButton);
        placeBetButton.click();
    }

    public List<WebElement> findAvailableSelections(){
        List<WebElement> selections = null;
        for (WebElement row : findAvailableMarkets()) {
            selections.addAll(row.findElements(By.className("selection-view"))); //TODO possible null maybe add a try
        }
        log.info("Potential selections found: " + selections.size());
        return selections;
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

    public void acceptAllOddsChanges(){
        //scrollIntoView(higherOddsCheckbox);
        checkBox(higherOddsCheckbox, "Accept all odds changes");
    }

    /*public boolean isBetsPlacementMsgSuccessDisplayed(){
        waitFor(betsPlacementSuccessMsg);
    }*/

    public String getBetTotalStake(){
        return betTotalStake.getText();
    }

    public String getBetTaxPercentage(){
        return betTaxPercentage.getText();
    }

    public String getAccountCashBalance(){
        return accountCashBalance.getText();
    }

}
