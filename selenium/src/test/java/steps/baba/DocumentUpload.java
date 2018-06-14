package steps.baba;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import other.SeleniumTestTemplate;

public class DocumentUpload extends SeleniumTestTemplate {

    @Given("^user access to the Document Upload section$")
    public void user_access_to_the_Document_Upload_section() throws Throwable {
        moveOver(By.id("user-logged-in-account"));
        driver.findElement(By.id("accounts-option")).click();
        driver.findElement(By.id("my-document-upload-tab")).click();

    }

    @When("^selects a document to upload$")
    public void selects_a_document_to_upload() throws Throwable {
        WebElement element = driver.findElement(By.id("selectedFile"));
        ((RemoteWebElement) element).setFileDetector(new LocalFileDetector());
        element.sendKeys(getSourceAsAbsolutePath("documentUpload/idPic.jpg"));
    }

    @When("^enters the required details$")
    public void enters_the_required_details() throws Throwable {
        new Select(driver.findElement(By.id("documentType"))).selectByVisibleText("International Passport");
        new Select(driver.findElement(By.id("documentType"))).selectByVisibleText("International Passport");
        driver.findElement(By.id("PASSPORT")).click();
        driver.findElement(By.id("expiresOn")).clear();
        driver.findElement(By.id("expiresOn")).sendKeys("05.05.2017");
        driver.findElement(By.id("expiresOn")).clear();
        driver.findElement(By.id("expiresOn")).sendKeys("05.05.2017");
        driver.findElement(By.id("submit")).click();
    }

    @Then("^a message confirms upload success$")
    public void a_message_confirms_upload_success() throws Throwable {
        Assert.assertFalse("Upload failed", driver.findElement(By.id("messageText")).getText().contains("unsucessful"));
    }

}
