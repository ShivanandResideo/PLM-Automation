package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.Commons;
import utils.CreateDocs;
import utils.CreateParts;
import utils.objects.Part;
import webElements.EditPartsPage;

public class CreatePartsSteps {
    Part part;

    public static WebDriver driver = CreateClassSteps.driver;
    Commons commons = new Commons();

    @When("^user tries to create new manufacturer Parts with part number (.*) under Manufacturer (.*) with Status (.*) for (.*)$")
    public void createPart(String MPN, String manufacturer, String status, String specialCase){
        commons.home();

        CreateParts tasksParts = new CreateParts();
        part = new Part(MPN, manufacturer, status, "NA", specialCase);

        try{
            tasksParts.createPart(driver, part);
        }catch(NoSuchElementException e){
            Commons.takeScreenshot(driver);
            e.printStackTrace();
            Assert.fail("Some element which was expected is not found. Failing the test. Screenshot taken with file name"+ Commons.takeScreenshot(driver));
        }

    }
    @Then("part details page is to be reached")
    public void verifyManufacturerPartAttributes() {
        Commons.takeFullScreenshot();

        EditPartsPage.generalInformation(driver, part);

        EditPartsPage.additionalInformation(driver, part);

        EditPartsPage.attachFile(driver, part);
    }
}
