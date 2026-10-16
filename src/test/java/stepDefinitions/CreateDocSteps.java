package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.Commons;
import utils.CreateDocs;
import utils.CreateItems;
import utils.objects.Doc;
import utils.objects.Item;
import webElements.EditDocumentPage;
import webElements.EditItemPage;

public class CreateDocSteps {

    public static Doc doc;
    Item item;

    public static WebDriver driver = CreateClassSteps.driver;
    Commons commons = new Commons();

    @When("^user tries to create (.*) document with docGenMtd as (.*) Prefix as (.*) Root Part-Family as (.*) Version as (.*) Legacy as (.*) for (.*)$")
    public void createDocument(String document, int docGenMtd, String pre, String rpf, String ver, String legacy, String splCase) {
        commons.home();

        CreateDocs tasksDocs = new CreateDocs();
        doc = new Doc(document, null, null, docGenMtd, pre, rpf, ver, legacy, "Air", "CZ52 - Brno", "COTS", splCase);
//        doc = new Doc(document, null, null, docGenMtd, pre, rpf, ver, legacy, splCase);



        try{
            if(!(doc.docGenMtd == 2)){
                tasksDocs.createDoc(driver, doc);
            }else{
                CreateItems tasksItems = new CreateItems();
                item = new Item();
                item = tasksItems.createItem(driver, item);
                doc.relatedItem = item.prefix;
                doc.rootPartFamily = item.existingFamily;
                doc.version = item.familyVersion;
                commons.home();
                tasksDocs.createDoc(driver, doc);
            }
        }catch(NoSuchElementException e){
            Commons.takeScreenshot(driver);
            e.printStackTrace();
            Assert.fail("Some element which was expected is not found. Failing the test. Screenshot taken with file name"+ Commons.takeScreenshot(driver));
        }
    }

    @Then("edit document page is to be reached with all relevant attributes")
    public static void verifyEditItemPageAttributes() {

        Commons.takeFullScreenshot();

        // verify the number of the document created
        EditDocumentPage.generalInformation(driver, doc);
        if(doc.className.equalsIgnoreCase("Operational Documents") || doc.className.equalsIgnoreCase("System Documents") || doc.className.equalsIgnoreCase("Quality Records")){
            // no validation rules for the above three doc types in tests shared. Hence, skipping other validations.
            return;
        }

        EditDocumentPage.commonPartAttributes(driver, doc);

        // class information specification

        // legacy attributes

        EditDocumentPage.modify(driver, doc);

        // approval matrix DAC only applicable for specification type of document
        if(doc.type.equalsIgnoreCase("Specification")) {
            System.out.println("verifying DAC...");
            EditItemPage.verifyDAC(doc);
        }
    }

}
