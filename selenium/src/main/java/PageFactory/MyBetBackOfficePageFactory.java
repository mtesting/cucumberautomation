package PageFactory;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import other.SeleniumTestTemplate;

public class MyBetBackOfficePageFactory extends SeleniumTestTemplate {

    @FindBy(css = "td.w2ui-tb-caption")
    private WebElement loginButton;

    @FindBy(id = "username")
    private WebElement loginUsername;

    @FindBy(id = "password")
    private WebElement loginPassword;

    @FindBy(name = "save")
    private WebElement acceptLoginButton;

    @FindBy(css = "table[title='Search Bets']")
    private WebElement searchBets;

    @FindBy(id = "grid_searchBetsByIdGrid_search_all")
    private WebElement gridSearchBetsByIdGridSearchAll;

    @FindBy(css = "select.bet-results")
    private WebElement selectBetResults;

    @FindBy(id = "amendButton")
    private WebElement amendButton;

    public void initMyBetBackOfficePageFactory() throws DecoderConfigException {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        driver.get(decoder.decodePunterUrl("MYBET_BACKOFFICE"));
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

    public void clickOnAcceptLoginButton() {
        acceptLoginButton.click();
    }

    public void clickOnsearchBets() {
        waitFor(searchBets);
        javascriptClick(searchBets);
    }

    public void enterBetRefNumber(String betRefNumber){
        gridSearchBetsByIdGridSearchAll.clear();
        gridSearchBetsByIdGridSearchAll.sendKeys(betRefNumber);
        gridSearchBetsByIdGridSearchAll.sendKeys(Keys.RETURN);
    }

    public void clickOnSelectBetResults(){
        selectBetResults.click();
    }

    public void selectBetOutcomeResult(String betOutcome){
        new Select(selectBetResults).selectByVisibleText(betOutcome);
    }

    public void clickOnAmendButton(){
        amendButton.click();
    }

    public void closeClient(){
        driver.switchTo().defaultContent();
        driver.close();
    }

    public String getAlertMsg(){
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void clickOnAcceptAlert(){
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

}
