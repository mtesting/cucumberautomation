package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import other.SeleniumTestTemplate;

public class MyBetBetslipPageFactory extends SeleniumTestTemplate {

    @FindBy(css = "li[class=slip-summary__total-stake] > span:nth-child(2)")
    private WebElement betTotalStake;

    @FindBy(css = "li[class=slip-summary__percentage-tax] > span:nth-child(2)")
    private WebElement betTaxPercentage;

    @FindBy(css = "div[class='bet-slip-confirmation-view row bet-row confirmation'] li[class='slip-summary__winnings'] span span")
    private WebElement betPotentialWinnings;

    @FindBy(css = "span[class='betId']")
    private WebElement betRefNumber;

    @FindBy(xpath = "id('betslip')//li[@class='higher-odds']")
    private WebElement higherOddsCheckbox;

    @FindBy(css = "div.betslip-success")
    private WebElement betsPlacementSuccessMsg;

    @FindBy(css = "div.single-bet__error")
    private WebElement betsPlacementErrorMsg;

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

    MyBetBetslipPageFactory(){
        PageFactory.initElements(driver, this);
    }

    public void acceptAllOddsChanges(){
        scrollIntoView(higherOddsCheckbox);
        checkBox(higherOddsCheckbox, "Accept all odds changes");
    }

    public String getBetTotalStake(){
        return betTotalStake.getText();
    }

    public String getBetTaxPercentage(){
        return betTaxPercentage.getText();
    }

    public String getBetPotentialWinnings(){
        return betPotentialWinnings.getText();
    }

    public String getBetRefNumber(){
        return betRefNumber.getText();
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

    public void waitForBetslipSuccessMsg(){
        waitFor(betsPlacementSuccessMsg);
    }

    public void waitForBetslipErrorMsg(){
        waitFor(betsPlacementErrorMsg);
    }

}
