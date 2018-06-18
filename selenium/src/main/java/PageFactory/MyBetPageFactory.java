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

    @FindBy(className = "betslip-icon")
    private WebElement betslipIcon;

    @FindBy(xpath = "id('betslip')//div[@class='single-grouped-bet-view']")
    private WebElement singleBetsView;

    @FindBy(xpath = "id('betslip')//div[@class='system-bets-view']")
    private WebElement systemBetsView;

    @FindBy(xpath = "id('betslip')//div[@class='combi-bet-view']")
    private WebElement accumulatorBetsView;

    @FindBy(xpath = "id('betslip')//div[@class='single-grouped-bet-view']//input")
    private WebElement stakeInputSINGLE;

    @FindBy(css = "[data-test-stake-input='stakeInputDOUBLE']")
    private WebElement stakeInputDOUBLE;

    @FindBy(css = "[data-test-stake-input='stakeInputTRIXIE']")
    private WebElement stakeInputTRIXIE;

    @FindBy(css = "[data-test-stake-input='stakeInputPATENT']")
    private WebElement stakeInputPATENT;

    @FindBy(xpath = "id('betslip')//div[@class='combi-bet-view']//input")
    private WebElement stakeInputAccumulator;

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

    public void acceptAllOddsChanges(){
        scrollIntoView(higherOddsCheckbox);
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

    public boolean isBetslipIconized() {
        return exists(betslipIcon) && betslipIcon.isDisplayed();
    }

    public void clickOnBetslipIcon() {
        betslipIcon.click();
    }

    public List<WebElement> getTipsOnBetslip(){
        return driver.findElement(By.id("betslip")).findElements(By.className("selection-group"));
    }

    public void openSingleBetsView(){
        scrollIntoView(singleBetsView);
        singleBetsView.click();
    }

    public void openSystemBetsView(){
        scrollIntoView(systemBetsView);
        systemBetsView.click();
    }

    public void openAccumulatorBetsView(){
        scrollIntoView(accumulatorBetsView);
        if(!accumulatorBetsView.isDisplayed()){
            accumulatorBetsView.click();
        }
    }

    public void enterStakeInputSINGLE(String stake){
        stakeInputSINGLE.sendKeys(stake);
    }

    public void enterStakeInputDOUBLE(String stake){
        stakeInputDOUBLE.sendKeys(stake);
    }

    public void enterStakeInputTRIXIE(String stake){
        stakeInputTRIXIE.sendKeys(stake);
    }

    public void enterStakeInputPATENT(String stake){
        stakeInputPATENT.sendKeys(stake);
    }

    public void enterStakeInputAccumulator(String stake){
        stakeInputAccumulator.sendKeys(stake);
    }

}
