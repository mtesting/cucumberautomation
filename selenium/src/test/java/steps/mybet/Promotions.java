package steps.mybet;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import decoders.Decoder;
import decoders.DecoderConfigException;
import decoders.DecoderManager;
import entities.TestPromotionData;
import other.Constants;
import other.SeleniumTestTemplate;
import thirdparty.RethinkHelper;
import util.Utils;

public class Promotions extends SeleniumTestTemplate {

    private TestPromotionData promotionData;
    private final Decoder decoder = DecoderManager.getManager().getDecoder();

    public Promotions() throws DecoderConfigException {
    }

    @Given("^a user is logged into the CMS backoffice$")
    public void a_user_is_logged_into_the_CMS_backoffice() throws Throwable {
        RethinkHelper rethinkDbHelper = new RethinkHelper("sportsbook-demo.amelco.co.uk", 28015);
        rethinkDbHelper.mybetPromotionsCleanup();

        driver = initDriver(decoder.decodePunterUrl("MYBET_CMS"));
        driver.findElement(By.cssSelector("input[label='Username']")).sendKeys(Constants.MYBET_BACKOFFICE_USER);
        driver.findElement(By.cssSelector("input[label='Password']")).sendKeys(Constants.MYBET_BACKOFFICE_PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Utils.waitSeconds(2);
    }

    @Given("^the user tries to create a new promotion$")
    public void the_user_tries_to_create_a_new_promotion() throws Throwable {
        driver.findElement(By.cssSelector("li[title='Cms']")).click();
        driver.findElement(By.cssSelector("li[title='Promotions']")).click();
        driver.findElement(By.linkText("Create Promotion")).click();
    }

    @When("^selects the \"([^\"]*)\" promotion style$")
    public void selects_the_promotion_style(String promotionStyle) throws Throwable {
        promotionData = new TestPromotionData(promotionStyle);
        driver.findElement(By.id("rw_1")).click();
        List<WebElement> allElements = driver.findElements(By.xpath("//*[@id='rw_1__listbox']/li"));

        if (!driver.findElement(By.id("rw_1__listbox")).isDisplayed()) {
            driver.findElement(By.id("rw_1")).click();
        }

        for (WebElement element : allElements) {
            if (element.getText().equalsIgnoreCase(promotionStyle)) {
                element.click();
                break;
            }
        }
    }

    @Then("^the user populates the <promotion name>, <time frame> And relevant defaults$")
    public void the_user_populates_the_promotion_name_time_frame_And_relevant_defaults() throws Throwable {
        driver.findElement(By.cssSelector("input[placeholder~=internal]")).sendKeys(promotionData.getName());

        driver.findElement(By.id("rw_4_input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), promotionData.getEndDate());
        driver.findElement(By.id("rw_4_input")).sendKeys(Keys.chord(Keys.CONTROL, "a"), promotionData.getEndDate());

        switch (driver.findElement(By.id("rw_1")).getText()) {
            case "Picture only×":
                uploadPictures();
                break;
            case "Sports×":
                driver.findElement(By.cssSelector("input[placeholder~=title]")).sendKeys(promotionData.getTitle());
                driver.findElement(By.cssSelector("input[placeholder~=subtitle]")).sendKeys(promotionData.getSubtitle());
                driver.findElement(By.cssSelector("input[placeholder~=details]")).sendKeys(promotionData.getDetails());
                driver.findElement(By.cssSelector("textarea[placeholder~=flipped]")).sendKeys(promotionData.getContent());

                Select select = new Select(driver.findElement(By.xpath("//*[contains(text(), 'data-test-promotionsimagecontrol-scopeselector')]")));
                select.selectByVisibleText("Slider only");

                //driver.findElement(By.linkText("Link Promotion to EventHelperTemplate")).click();
                //driver.findElement(By.linkText("Search")).click();
                //waitSeconds(5);
                //driver.findElement(By.xpath("//*[@id='modalWin']/div[2]/div/div/div/div[1]")).click();
                //waitSeconds(5);

                uploadPictures();
                break;
            case "Promotion×":
                driver.findElement(By.cssSelector("input[placeholder~=title]")).sendKeys(promotionData.getTitle());
                driver.findElement(By.cssSelector("input[placeholder~=subtitle]")).sendKeys(promotionData.getSubtitle());
                driver.findElement(By.cssSelector("textarea[placeholder~=details]")).sendKeys(promotionData.getDetails());

                driver.findElement(By.cssSelector("input[placeholder='(click-through link) ']")).sendKeys(promotionData.getBackgroundLink());
                driver.findElement(By.cssSelector("input[placeholder*=deposit]")).sendKeys(promotionData.getButtonLinkURL());

                driver.findElement(By.cssSelector("input[placeholder*='text for the button']")).sendKeys(promotionData.getButtonLabel());

                uploadPictures();
                break;
            case "Bonus Tile×":
                Assert.assertFalse("Input options not updated as expected", exists(By.id("rw_27_input")));
                break;
        }

        waitFor(By.cssSelector("[data-test-promotions-savebtn]"));
        driver.findElement(By.cssSelector("[data-test-promotions-savebtn]")).click();
    }

    @When("^ticks the <Bonus Tile> box$")
    public void ticks_the_Bonus_Tile_box() throws Throwable {
        javascriptClick(By.cssSelector("input[type='checkbox'][name='checkbox']"));
    }

    @Then("^the promotion is created successfully$")
    public void the_promotion_is_created_successfully() throws Throwable {
        waitFor(By.linkText("Create Promotion"));
        log.info("Promotion created: " + promotionData.getName());
        driver.get(Constants.MYBET_DEMO_CMS_URL + "/cms/promotions/list");
        driver.findElement(By.cssSelector("[data-test-promotions-searchinput]")).sendKeys(promotionData.getName());
        Assert.assertTrue("Promo creation failed", exists(By.cssSelector("div[value='" + promotionData.getName() + "']")));
    }

    @Given("^user selects the \"([^\"]*)\" section at the Page Builder$")
    public void user_selects_the_section_at_the_Page_Builder(String sectionName) throws Throwable {
        driver.findElement(By.cssSelector("li[title='Cms']")).click();
        driver.findElement(By.cssSelector("li[title='Page Builder']")).click();
        driver.findElement(By.xpath("//*[contains(text(), '" + sectionName + "')]")).click();
        log.info("Section selected - section=" + sectionName);
        //driver.findElement(By.cssSelector("div:contains('/Demo')")) // Idk why it doesn't work
    }

    @When("^user adds the \"([^\"]*)\" promotion type to the Carousel Widget$")
    public void user_adds_the_promotion_type_to_the_Carousel_Widget(String promotionType) throws Throwable {
        driver.findElement(By.cssSelector("input[placeholder='Add a promotion...']")).sendKeys(promotionData.getName());
        Utils.waitSeconds(1);
        driver.findElement(By.cssSelector("input[placeholder='Add a promotion...']")).sendKeys(Keys.RETURN);
        Utils.waitSeconds(1);
        driver.findElement(By.cssSelector("[data-test-pagebuilder-savebtn]")).click();
        log.info("Promotion added - promo=" + promotionData.getName());
    }

    @When("^user goes to the front-end Promotions section$")
    public void user_goes_to_the_front_end_Promotions_section() throws Throwable {
        driver.get(decoder.decodePunterUrl("MYBET") + "/en/promotions/sports");
    }

    @Then("^promotion should be displayed successfully$")
    public void promotion_should_be_displayed_successfully() throws Throwable {
        Utils.waitSeconds(1);
        waitFor(By.xpath("//div[contains(text(), '" + promotionData.getTitle() + "')]"));
        driver.findElement(By.xpath("//div[contains(text(), '" + promotionData.getTitle() + "')]")).click();
        Assert.assertTrue("Promotion not displayed", exists(By.xpath("//*[contains(text(), '" + promotionData.getTitle() + "')]")));
        Assert.assertTrue("Promotion subtitle not displayed properly", exists(By.xpath("//*[contains(text(), '" + promotionData.getSubtitle() + "')]")));
        Assert.assertTrue("Promotion subtitle not displayed properly", exists(By.xpath("//*[contains(text(), '" + promotionData.getDetails() + "')]")));
        boolean isPresent = driver.findElements(By.cssSelector("div.carousel-container-cell > * div.loading")).size() > 0;
        Assert.assertFalse("Promotion not fully loaded", isPresent);
    }

    private void uploadPictures() {
        File folder = new File("src/main/resources/mybetPromotionPictures/");
        File[] listOfFiles = folder.listFiles();
        WebElement element = driver.findElement(By.id("uploadBtn"));
        ((RemoteWebElement) element).setFileDetector(new LocalFileDetector());
        try {
            for (File file : listOfFiles) {
                element.sendKeys(getSourceAsAbsolutePath("mybetPromotionPictures/" + file.getName()));
                Utils.waitSeconds(1);
            }
        } catch (URISyntaxException e) {
            log.error(e);
        }
        Utils.waitSeconds(2);
    }

}
