package steps.mybet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.DataBaseHelper;
import decoders.Decoder;
import decoders.DecoderManager;
import other.Constants;
import other.SeleniumTestTemplate;

public class DocumentUpload extends SeleniumTestTemplate {

    @Given("^user access to the Document Upload section$")
    public void user_access_to_the_Document_Upload_section() throws Throwable {
        moveOver(By.className("outer-bal"));
        driver.findElement(By.cssSelector("[data-test-userdropdown-accountdetails]")).click();
        driver.findElement(By.linkText("Upload Documents")).click();
        if (exists(By.xpath("//*[contains(text(), 'Upload More Documents')]"))) {
            javascriptClick(By.xpath("//*[contains(text(), 'Upload More Documents')]"));
            javascriptClick(By.xpath("//*[contains(text(), 'Upload More Documents')]"));
        } else {
            driver.findElement(By.xpath("//*[contains(text(), 'Start Document Verification')]")).click();
        }
    }

    @When("^selects a document to upload$")
    public void selects_a_document_to_upload() throws Throwable {
        //driver.findElement(By.id("uploadBtn")).click();
        WebElement element = driver.findElement(By.id("uploadBtn"));
        ((RemoteWebElement) element).setFileDetector(new LocalFileDetector());
        element.sendKeys(getSourceAsAbsolutePath("documentUpload/idPic.jpg"));
    }

    @When("^enters the required details$")
    public void enters_the_required_details() throws Throwable {
        new Select(driver.findElement(By.cssSelector("select"))).selectByVisibleText("Proof of ID");
        //driver.findElement(By.cssSelector("i.icon-calendar")).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("^a message confirms upload success$")
    public void a_message_confirms_upload_success() throws Throwable {
        Decoder decoder = DecoderManager.getManager().getDecoder();
        driver.findElement(By.className("upload-form__success"));
        scrollIntoView(By.className("upload-form__success"));

        DataBaseHelper dbHelper = new DataBaseHelper(decoder.decodeCustomerDb("MYBET_ATS_DB"), Constants.ATS_DB_USER, Constants.MYBET_DB_PASSWORD);
        dbHelper.connect();
        dbHelper.documentsCleanup(Constants.SPORTBOOK_USER);
        dbHelper.disconnect();
    }

}
