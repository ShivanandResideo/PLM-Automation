package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.CreateDocs;
import utils.CreateItems;
import utils.objects.Doc;
import utils.objects.Item;
import utils.objects.Part;
import utils.objects.Type;
import webElements.*;

import static utils.Commons.driver;

public class StructureSteps {
    static Item[] items;
    static Doc[] docs;
    WebDriver driver = Commons.driver;

    @When("^user tries to create (.*) items and (.*) documents for (.*)$")
    public void user_tries_to_create_item_and_documents(Integer iNum, Integer dNum, String specialCase) {

        if (specialCase.contains("AffectedObjects")) {

            items = new Item[iNum];
            docs = new Doc[dNum];
            CreateItems createItem = new CreateItems();
            CreateDocs createDocs = new CreateDocs();

            for (int i = 0; i < iNum; i++) {
                items[i] = new Item();
                items[i] = CreateItems.createItem(driver, items[i]);
            }
            for (int i = 0; i < dNum; i++) {
                docs[i] = new Doc();
                docs[i] = CreateDocs.createDoc(driver, docs[i]);
            }

            if(specialCase.contains("CO")){
                // add 3 objects under each of the item and docs created...

                // reach edit page
                Commons.reachEditPage(items[0].type, items[0].name);
                Commons.sleepWait(2000);
                // reach the structures tab
                Type.reachTab("structure");
                Commons.sleepWait(2000);

                // add 2 objects to structure
                // one to delete, one to edit, one to replace
                StructuresTab.addNewChildObjects("item");
                Commons.sleepWait(2000);
                StructuresTab.addNewChildObjects("document");
                Commons.sleepWait(2000);
                StructuresTab.addNewChildObjects("item");
                Commons.sleepWait(3000);

                // add attachments
                Type.reachTab("attachments");
                AttachmentsTab.addNecessaryAttachments("Zip");
                Commons.sleepWait(3000);

                // add AML
                Type.reachTab("aml");
                AMLTab.selectAML(driver, new Part("101","AUTOMATION COMPONENTS INC", "Active", "NA", "NA"));
                Commons.sleepWait(3000);

                driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
                Commons.sleepWait(2000);
                // reach edit page
                Commons.reachEditPage(docs[0].type, docs[0].name);
                Commons.sleepWait(2000);
                // reach the structures tab
                Type.reachTab("structure");
                Commons.sleepWait(2000);

                // add 2 objects
                StructuresTab.addNewChildObjects("item");
                StructuresTab.addNewChildObjects("document");
                StructuresTab.addNewChildObjects("item");

                // add attachments
                Type.reachTab("attachments");
                AttachmentsTab.addNecessaryAttachments("Zip");

                driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
                Commons.sleepWait();
            }
        }
    }

    @Then("^all relevant (.*) functionalities are working as expected for (.*)$")
    public void validateStructureTabFunctionalities(String functionality, String parent) {

        // navigate to the item[0] and add children
        StructuresTab.addChildElements(items, docs, parent);

        if(functionality.contains("drag and drop")){
            // Add an item by dragging the item from clipboard and place it under first level
            WebElement wParent = driver.findElement(By.cssSelector("*[id$='" + "0" + StructuresTab.TABLE_ROWS_END + "']")).findElement(By.xpath("./../.."));
            wParent.click();

            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CLIPBOARD_END + "']")).click();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.RECENT_ITEMS_END + "']")).click();
            WebElement child = driver.findElement(By.cssSelector("*[id$='" + OverviewPage.RECENT_LIST_20_END + "']"));
            Commons.actions.dragAndDrop(child, wParent);
            Commons.assertPresent(child.getText().split(" ")[0]);

            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SAVE_END + "']")).click();
            return;
        }

        Commons.sleepWait(2000);
        // driver.findElement(By.cssSelector("*[id$='"+ EditItemStructuresPage.SAVE_END +"']")).click();
        // Select each sub item and some attributes are editable and some are not editable

        // select the first child of the items[0].
        driver.findElement(By.cssSelector("*[id$='" + StructuresTab.TABLE_ELEMENTS_MIDDLE + "1" + StructuresTab.TABLE_ROWS_END + "']")).findElement(By.xpath("./../..")).click();
        Commons.sleepWait(2000);

        StructuresTab.validateParameters(1);

        // Add child to a child item using create and check the modifiable and non-modifiable attributes
        driver.findElement(By.cssSelector("*[id$='" + "1" + StructuresTab.TABLE_ROWS_END + "']")).findElement(By.xpath("./../..")).click();

        StructuresTab.addNewChildObjects("item");

//         assert if the child is created and in the structure
        Commons.sleepWait(2000);

        Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='" + "1" + StructuresTab.TABLE_COLUMN_EXPAND_CHILDREN_END + "']")));
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + "1" + StructuresTab.TABLE_COLUMN_EXPAND_CHILDREN_END + "']")).click();
        //  child of the first child
        driver.findElement(By.cssSelector("*[id$='" + (docs.length + items.length) + StructuresTab.TABLE_ROWS_END + "']")).findElement(By.xpath("./../..")).click();
        Commons.sleepWait(2000);
        StructuresTab.validateParameters(docs.length + items.length);

        // check right pane for parent which has 5 tabs while child components have 3 extra components.

        StructuresTab.validateRightPane("parent");
        Commons.sleepWait(3000);
        StructuresTab.validateRightPane("child");

//        LATER-----------------------------check in the child's edit page if it is reflected. LATER-------------------- LATER

        // remove the item by clicking and then remove
        driver.findElement(By.cssSelector("*[id$='" + "1" + StructuresTab.TABLE_ROWS_END + "']")).findElement(By.xpath("./../.."));
        String name = driver.findElement(By.cssSelector("*[id$='" + StructuresTab.TABLE_ELEMENTS_MIDDLE + "1" + StructuresTab.TABLE_COLUMN_NAME_END + "']")).getText();
        driver.findElement(By.cssSelector("*[id$='" + StructuresTab.DELETE_END + "']"));
        Commons.assertNotPresent(name);

        // save as another part
        // add attachments
        driver.findElement(By.cssSelector("*[id$='" + AttachmentsTab.ATTACHMENTS_TAB_END + "']")).click();
        AttachmentsTab.addNecessaryAttachments("Zip");
        if (parent.equalsIgnoreCase("Items")) {
            // add AML
            driver.findElement(By.cssSelector("*[id$='" + AMLTab.AML_TAB_END + "']")).click();
            AMLTab.selectAML(driver, new Part());
        }

        CommonElements.parent = parent;
        Commons.sleepWait(3000);
        CommonElements.saveAs(true, true, true);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(true, true, true);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
        CommonElements.saveAs(true, true, false);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(true, true, false);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
        CommonElements.saveAs(true, false, true);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(true, false, true);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
        CommonElements.saveAs(true, false, false);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(true, false, false);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
        CommonElements.saveAs(false, true, true);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(false, true, true);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
        CommonElements.saveAs(false, true, false);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(false, true, false);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
        CommonElements.saveAs(false, false, true);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(false, false, true);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
        CommonElements.saveAs(false, false, false);
        Commons.sleepWait(3000);
        CommonElements.verifySaveAs(false, false, false);
        // close the newly created tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
    }
}
