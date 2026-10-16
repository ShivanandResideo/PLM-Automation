package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.CreateChange;
import utils.objects.Change;
import webElements.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabSteps {

    WebDriver driver = Commons.driver;

    // old code used during hard coding (2025). used to reach edit page for any existing objects.
    @When("^(.*) reaches old edit page for (.*)$")
    public void reachEditPage(String userType,String type){
        if(type.equalsIgnoreCase("Items")){
             // Commons.reachEditPage(type, ""); /to be hard coded later/
                    // select an Antenna item which has attachments, revisions, part of change, AML, and other tabs information already loaded
        }
        else if(type.equalsIgnoreCase("Documents")){

            Commons.reachEditPage(type, "920-00058-001"); // created in delta test for structure tab 920-00058-001-00
            // select a specification document
        }
        else if(type.equalsIgnoreCase("Manufacturer Parts")){
            Commons.reachEditPage(type, "");
        }
        else if(type.equalsIgnoreCase("Manufacturer")){
            Commons.reachEditPage(type, "");
        }
        else if(type.equalsIgnoreCase("SCR")){
            Commons.reachEditPage(type, "");
        }
        else if(type.equalsIgnoreCase("SDV")){
            Commons.reachEditPage(type, "");
        }
        else if(type.equalsIgnoreCase("SCO")){
            Commons.reachEditPage(type, "");
        }
        else if(type.equalsIgnoreCase("NRU")){
            // create NRU and then do the tests
            Commons.reachEditPage(type, "");
        }
    }

    @Then("^user checks information of (.*) in (.*) for (.*)$")
    public void validateTab(String type, String tab, String specialCase){
        if(tab.equalsIgnoreCase("General Information")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.GENERAL_INFORMATION_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // check if relevant sections are present and are collapsible
            List<String> sections = new ArrayList<>();
            if(type.equalsIgnoreCase("Items")){
                sections.add("Common Part Attributes");
                sections.add("ERCV Information");
                sections.add("Antenna Information");
                sections.add("Legacy Attributes");
                sections.add("Additional Attributes");
            }
            else if(type.equalsIgnoreCase("Documents")){
                sections.add("Common Document Attributes");
                sections.add("ERCV Information");
                sections.add("Specification Information");
                sections.add("Legacy Attributes");
                sections.add("Additional Attributes");
            }
            else if(type.equalsIgnoreCase("SCR")){
                sections.add("SCR Change Attributes");
                sections.add("Design Team");
                sections.add("Impacted Manufacturing Site");
            }
            else if(type.equalsIgnoreCase("SDV")){
                sections.add("SDV Change Attributes");
                sections.add("Design Team");
                sections.add("Impacted Manufacturing Site");
            }
            else if(type.equalsIgnoreCase("SCO")){
                sections.add("SCO Change Attributes");
                sections.add("Design Team");
                sections.add("Impacted Manufacturing Site");
                sections.add("Change Implementation Details");
            }
            else if(type.equalsIgnoreCase("NRU")){
                sections.add("NRU Change Attributes");
                sections.add("Design Team");
                sections.add("Impacted Manufacturing Site");
                sections.add("Change Implementation Details");
            }

            // check if the individual sections are collapsable
            for(String section : sections){
                // Commons.scrollToView(); add if required during validation
                WebElement element = Commons.findElementByExactName(section);
                Assert.assertTrue(element.isDisplayed());
                Assert.assertTrue(element.findElement(By.xpath("./../../../td/a")).getDomAttribute("title").contains("Collapse "+ section));
            }

            // check if different revisions are selectable
            if(type.equalsIgnoreCase("Items") || type.equalsIgnoreCase("Documents")) {
                // change the revision option
                WebElement element = Commons.findElementByLabel("Revision");
                String before = element.getText();
                String after = Commons.selectDifferentOption(new Select(element));
                Assert.assertFalse(before.equalsIgnoreCase(after));
            }

            // cannot save
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
        }
        else if(tab.equalsIgnoreCase("Affected Objects")) {
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.AFFECTED_OBJECTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // can drag columns and rearrange columns
            Assert.assertTrue(Commons.findElementByExactName("Description").findElement(By.xpath("./../..")).getDomAttribute("style").equalsIgnoreCase("cursor: move;"));

            // column sorting
            List<WebElement> manufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + AffectedObjectsTab.NAME_LINK_END + "']"));
            List<String> manufacturerPartNames = new ArrayList<>();
            for(WebElement element : manufacturerPartElements){
                manufacturerPartNames.add(element.getTagName());
            }
            Collections.sort(manufacturerPartNames);
            Commons.findElementByExactName("Manufacturer Part").click();
            Commons.sleepWait();
            List<WebElement> sortedManufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + AffectedObjectsTab.NAME_LINK_END + "']"));
            List<String> sortedManufacturerPartNames = new ArrayList<>();
            for(WebElement element : sortedManufacturerPartElements){
                sortedManufacturerPartNames.add(element.getTagName());
            }
            Assert.assertEquals(manufacturerPartNames, sortedManufacturerPartNames);

            // Modify view by adding or removing columns
            Commons.findElementByExactName("View").click();
            Commons.sleepWait();
            Commons.findElementByExactName("Columns").click();
            Commons.sleepWait();
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
            Commons.findElementByExactName("View").click();

            // Export structure to Excel, FILTER, DETACH enabled
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.EXPORT_TO_EXCEL_END + "']")).isEnabled());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.FILTER_END + "']")).isEnabled());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.DETACH_END + "']")).isEnabled());

            // check if Name is links
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.NAME_LINK_END + "']")).getTagName().equalsIgnoreCase("a"));

            // check if select and add remove options are disabled
            // icons
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.REMOVE_END + "']")).isEnabled());
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.SELECT_AND_ADD_END + "']")).isEnabled());
            // actions
            Commons.findElementsByExactName("'Actions").get(1).click();
            Commons.sleepWait();
            Assert.assertFalse(Commons.findElementsByExactName("Select and Add").get(1).isEnabled());
            Assert.assertFalse(Commons.findElementsByExactName("Remove").get(1).isEnabled());
        }
        else if(tab.equalsIgnoreCase("Tasks")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TASKS_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // check ability to move columns
            Assert.assertTrue(Commons.findElementByExactName("Description").findElement(By.xpath("./../..")).getDomAttribute("style").equalsIgnoreCase("cursor: move;"));

            // column sorting
            List<WebElement> manufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + TasksTab.DESCRIPTION_END + "']"));
            List<String> manufacturerPartNames = new ArrayList<>();
            for(WebElement element : manufacturerPartElements){
                manufacturerPartNames.add(element.getTagName());
            }
            Collections.sort(manufacturerPartNames);
            Commons.findElementByExactName("Manufacturer Part").click();
            Commons.sleepWait();
            List<WebElement> sortedManufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + TasksTab.DESCRIPTION_END + "']"));
            List<String> sortedManufacturerPartNames = new ArrayList<>();
            for(WebElement element : sortedManufacturerPartElements){
                sortedManufacturerPartNames.add(element.getTagName());
            }
            Assert.assertEquals(manufacturerPartNames, sortedManufacturerPartNames);

            // Modify view by adding or removing columns
            Commons.findElementByExactName("View").click();
            Commons.sleepWait();
            Commons.findElementByExactName("Columns").click();
            Commons.sleepWait();
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + TasksTab.VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
            Commons.findElementByExactName("View").click();

            // Export structure to Excel, FILTER, DETACH enabled
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + TasksTab.EXPORT_TO_EXCEL_END + "']")).isEnabled());
            Assert.assertTrue(Commons.findElementByExactName("Freeze").isEnabled());
            Assert.assertTrue(Commons.findElementByExactName("Detach").isEnabled());

            // cannot add or remove tasks. Cannot edit the tasks
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.ADD_END + "']")).isEnabled());
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.DELETE_END + "']")).isEnabled());

            // cannot save changes
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
        }
        else if(tab.equalsIgnoreCase("'Impact Analysis")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.IMPACT_ANALYSIS_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.assertPresent("Impact Analysis");
        }
        else if(tab.equalsIgnoreCase("Attachments")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // check ability to move columns
            Assert.assertTrue(Commons.findElementByExactName("Description").findElement(By.xpath("./../..")).getDomAttribute("style").equalsIgnoreCase("cursor: move;"));

            // Expand right pane
            driver.findElement(By.cssSelector("*[id$='" + AttachmentsTab.RIGHT_PANE_BTN_END + "']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + AttachmentsTab.RIGHT_PANE_CLOSE_END + "']")).click();

            // check if file can be downloaded
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FILE_DOWNLOAD_ICON_END+"']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(Commons.checkDownload(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_COLUMN_END+"']")).getText()));

//            // download files and opening URLs are to be done here


            // check if relevant filters are available
            Select mainFilter = new Select(driver.findElement(By.cssSelector("*[id$='" + AttachmentsTab.MAIN_FILTER_END + "']")));
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='" + AttachmentsTab.MAIN_FILTER_END + "']")), "mainFilter");

            mainFilter.selectByVisibleText("Type");
            Commons.sleepWait(2000);
            Commons.compareDropDown(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.SUB_FILTER_MID + "']")), "Type");

            mainFilter.selectByVisibleText("Category");
            Commons.sleepWait(2000);
            Commons.compareDropDown(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.SUB_FILTER_MID + "']")), "Category");

            // check if Sort options are correct and asc and desc is available
            Select sort  = new Select(driver.findElement(By.cssSelector("*[id*='" + AttachmentsTab.SORT_END + "']")));
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='" + AttachmentsTab.SORT_END + "']")), "Sort");

            sort.selectByVisibleText("Title");
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id*='" + AttachmentsTab.SORT_ORDER_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName("Sort Ascending");
            Commons.findElementByExactName("Sort Descending");

            if(type.equalsIgnoreCase("Parts")){
                // Check if the Silicon Expert files can be accessed as link
                Commons.reachEditPage("Manufacturer Parts", "GRM188R60J475KE19D");
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
                Commons.sleepWait(2000);
                Assert.assertTrue(Commons.findElementByExactName("Access Silicon Expert Attributes").getTagName().equalsIgnoreCase("a"));
                Commons.findElementByExactName("Cancel").click();
            }

            // edits should not be possible
            Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).isEnabled());

            // cannot save
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
        }
        else if(tab.equalsIgnoreCase("Structure")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.STRUCTURE_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // check ability to move columns
            Commons.findElementByExactName("Description").click();
            Commons.sleepWait(2000);
            Assert.assertTrue(Commons.findElementByExactName("Description").findElement(By.xpath("./../..")).getDomAttribute("style").equalsIgnoreCase("cursor: move;"));// cursor: col-resize;

            // expand right pane and check for all the tabs
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_EXPAND_END + "']")).click();
            Commons.sleepWait();
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_ATTACHMENTS_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_AML_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_CHANGES_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_QUALITY_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_ADDITIONAL_ATTRIBUTES_END + "']")).isDisplayed());
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_EXPAND_END + "']")).click();

            // Modify view by adding or removing columns
            Commons.findElementByExactName("View").click();
            Commons.sleepWait();
            Commons.findElementByExactName("Columns").click();
            Commons.sleepWait();
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
            Commons.findElementByExactName("View").click();

            // Export structure to Excel
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.EXPORT_TO_EXCEL_END + "']")).isEnabled());

            // can compare structures
            Commons.findElementsByExactName("Actions").get(1).click();
            Commons.sleepWait();
            Commons.findElementByExactName("Compare Structures").click();
            Commons.sleepWait();
            String title = driver.findElement(By.cssSelector("*[id$='" + StructuresTab.COMPARE_STRUCTURE_PAGE_TITLE_END + "']")).getText();
            boolean result = title.contains("Compare") && title.contains("Structures");
            Assert.assertTrue(result);
            Commons.findElementByExactName("Cancel").click();
            Commons.sleepWait();

            // right pane has necessary tabs for parent - already covered
            // for child
            driver.findElement(By.xpath("//*[@_afrrk='1']")).click();
            Commons.sleepWait();
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_EXPAND_END + "']")).click();
            Commons.sleepWait();
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_ATTACHMENTS_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_AML_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_CHANGES_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_QUALITY_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_ADDITIONAL_ATTRIBUTES_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_REFERENCE_DESIGNATOR_END + "']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_END + "']")).isDisplayed());
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.RIGHT_PANE_EXPAND_END + "']")).click();

            // cannot save changes
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());

        }
        else if(tab.equalsIgnoreCase("AML")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.AML_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // go to Structure AML and come back to Item AML
            Commons.findElementByExactName("Item AML").click();
            Commons.sleepWait();
            Assert.assertTrue(Commons.findElementByExactName("Item AML").getDomAttribute("class").contains("Selected"));
            Commons.findElementByExactName("Structure AML").click();
            Commons.sleepWait();
            Assert.assertTrue(Commons.findElementByExactName("Structure AML").getDomAttribute("class").contains("Selected"));
            Commons.findElementByExactName("Item AML").click();

            // Operations under Items AML
            // column sorting
            List<WebElement> manufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']"));
            List<String> manufacturerPartNames = new ArrayList<>();
            for(WebElement element : manufacturerPartElements){
                manufacturerPartNames.add(element.getTagName());
            }
            Collections.sort(manufacturerPartNames);
            Commons.findElementByExactName("Manufacturer Part").click();
            Commons.sleepWait();
            List<WebElement> sortedManufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']"));
            List<String> sortedManufacturerPartNames = new ArrayList<>();
            for(WebElement element : sortedManufacturerPartElements){
                sortedManufacturerPartNames.add(element.getTagName());
            }
            Assert.assertEquals(manufacturerPartNames, sortedManufacturerPartNames);

            // Modify view by adding or removing columns
            Commons.findElementByExactName("View").click();
            Commons.sleepWait();
            Commons.findElementByExactName("Columns").click();
            Commons.sleepWait();
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
            Commons.findElementByExactName("View").click();

            // Export structure to Excel, FILTER, DETACH enabled
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.EXPORT_TO_EXCEL_END + "']")).isEnabled());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.FILTER_END + "']")).isEnabled());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.DETACH_END + "']")).isEnabled());

            // check if Manufacturer and part are links
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']")).getTagName().equalsIgnoreCase("a"));
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_END + "']")).getTagName().equalsIgnoreCase("a"));

            // Operations under Structure AML
            Commons.findElementByExactName("Structure AML").click();
            Commons.sleepWait();
            // column sorting
            List<WebElement> structureManufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']"));
            List<String> structureManufacturerPartNames = new ArrayList<>();
            for(WebElement element : structureManufacturerPartElements){
                structureManufacturerPartNames.add(element.getTagName());
            }
            Collections.sort(structureManufacturerPartNames);
            Commons.findElementByExactName("Manufacturer Part").click();
            Commons.sleepWait();
            List<WebElement> sortedStructureManufacturerPartElements = driver.findElements(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']"));
            List<String> sortedStructureManufacturerPartNames = new ArrayList<>();
            for(WebElement element : sortedStructureManufacturerPartElements){
                sortedStructureManufacturerPartNames.add(element.getTagName());
            }
            Assert.assertEquals(sortedStructureManufacturerPartNames, structureManufacturerPartNames);

            // Modify view by adding or removing columns
            Commons.findElementByExactName("View").click();
            Commons.sleepWait();
            Commons.findElementByExactName("Columns").click();
            Commons.sleepWait();
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
            Commons.findElementByExactName("View").click();

            // Export structure to Excel, FILTER, DETACH enabled
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.EXPORT_TO_EXCEL_END + "']")).isEnabled());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.FILTER_END + "']")).isEnabled());
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.DETACH_END + "']")).isEnabled());

            // check if Manufacturer and part are links
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']")).getTagName().equalsIgnoreCase("a"));
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_END + "']")).getTagName().equalsIgnoreCase("a"));

            // cannot save changes
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());

        }
        else if(tab.equalsIgnoreCase("Changes")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGES_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            if(specialCase.equalsIgnoreCase("Item Changes")) {
                // can toggle between Item changes and Structure changes
                Commons.findElementByExactName("Item Changes").click();
                Commons.sleepWait();
                Assert.assertTrue(Commons.findElementByExactName("Item Changes").getDomAttribute("class").contains("Selected"));
                Commons.findElementByExactName("Structure Changes").click();
                Commons.sleepWait();
                Assert.assertTrue(Commons.findElementByExactName("Structure Changes").getDomAttribute("class").contains("Selected"));
                Commons.findElementByExactName("Item Changes").click();

                // Drag columns to change order

                // column sorting
                List<WebElement> nameElements = driver.findElements(By.cssSelector("*[id$='" + ChangesTab.PC_NUMBER_END + "']"));
                List<String> Names = new ArrayList<>();
                for (WebElement element : nameElements) {
                    Names.add(element.getTagName());
                }
                Collections.sort(Names);
                Commons.findElementByExactName("Name").click();
                Commons.sleepWait();
                List<WebElement> sortedNameElements = driver.findElements(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']"));
                List<String> sortedNames = new ArrayList<>();
                for (WebElement element : sortedNameElements) {
                    sortedNames.add(element.getTagName());
                }
                Assert.assertEquals(sortedNames, Names);

// Divider between pending changes and released changes can be moved

                // can collapse pending changes and maximise released changes
                driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_SECTION_COLLAPSE_END + "']")).click();
                Commons.sleepWait();
                Commons.assertNotPresent("Pending Changes");
                driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_SECTION_COLLAPSE_END + "']")).click();
                Commons.sleepWait();

                // pending changes section
                // modify view
                Commons.findElementByExactName("View").click();
                Commons.sleepWait();
                Commons.findElementByExactName("Columns").click();
                Commons.sleepWait();
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
                Commons.findElementByExactName("View").click();
                // Export structure to Excel, FILTER, DETACH , Show cancelled changes enabled
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_EXPORT_TO_EXCEL_END + "']")).isEnabled());
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_FILTER_END + "']")).isEnabled());
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_DETACH_END + "']")).isEnabled());
                Assert.assertTrue(Commons.findElementByLabel("Show Cancelled Changes").isEnabled());

                // Related changes section
                // modify view
                Commons.findElementByExactName("View").click();
                Commons.sleepWait();
                Commons.findElementByExactName("Columns").click();
                Commons.sleepWait();
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.RC_VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
                Commons.findElementByExactName("View").click();
                // Export structure to Excel, FILTER, DETACH , Show cancelled changes enabled
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.RC_EXPORT_TO_EXCEL_END + "']")).isEnabled());
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.RC_FILTER_END + "']")).isEnabled());
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.RC_DETACH_END + "']")).isEnabled());
                Assert.assertTrue(Commons.findElementByLabel(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.RC_SECTION_END + "']")), "Show Cancelled Changes").isEnabled());

                // checking if the Number is a link
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_NUMBER_END + "']")).getTagName().equalsIgnoreCase("a"));
            }
            else if(specialCase.equalsIgnoreCase("Structure Changes")) {

                // column sorting
                List<WebElement> nameElements = driver.findElements(By.cssSelector("*[id$='" + ChangesTab.PC_NUMBER_END + "']"));
                List<String> Names = new ArrayList<>();
                for (WebElement element : nameElements) {
                    Names.add(element.getTagName());
                }
                Collections.sort(Names);
                Commons.findElementByExactName("Name").click();
                Commons.sleepWait();
                List<WebElement> sortedNameElements = driver.findElements(By.cssSelector("*[id$='" + AMLTab.MANUFACTURER_PART_END + "']"));
                List<String> sortedNames = new ArrayList<>();
                for (WebElement element : sortedNameElements) {
                    sortedNames.add(element.getTagName());
                }
                Assert.assertEquals(sortedNames, Names);

                // can see open changes and it is a link
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_NUMBER_END + "']")).getTagName().equalsIgnoreCase("a"));
                // the item list is also a link
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_ITEM_NUM_END + "']")).getTagName().equalsIgnoreCase("a"));

                // modify view
                Commons.findElementByExactName("View").click();
                Commons.sleepWait();
                Commons.findElementByExactName("Columns").click();
                Commons.sleepWait();
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
                Commons.findElementByExactName("View").click();
                // Export structure to Excel, FILTER, DETACH , Show cancelled changes enabled
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_EXPORT_TO_EXCEL_END + "']")).isEnabled());
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_FILTER_END + "']")).isEnabled());
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ChangesTab.PC_DETACH_END + "']")).isEnabled());

            }
        }
        else if(tab.equalsIgnoreCase("Relationships")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // can filter relationship by type
            Commons.compareDropDown(Commons.findElementByLabel("Type"), "Relationship Type");

            // adding and updating a relationship is not possible
            // select and add is disabled
            Commons.assertElementNotPresent(RelationshipTab.SELECT_AND_ADD_END);
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + RelationshipTab.SELECT_AND_ADD_END + "']")).isEnabled());

            // cannot save changes
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());

// pending changes            // not able to add rules and change relationship types

        }
        else if(tab.equalsIgnoreCase("Team")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TEAM_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.assertPresent("Team");

            // to check and test
            Commons.assertTagPresent("Actions", "h3");

            // cannot save changes
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
        }
        else if(tab.equalsIgnoreCase("Workflow")){
            Commons.findElementByAttributeValue("title", "Workflow").click();
            Commons.sleepWait(2000);
            Commons.assertPresent("Workflow");

            // Workflow list is present
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + WorkflowTab.WORKFLOW_STATUS_LIST_END + "']")).isDisplayed());

            // Can click on the different stages and look at participants
            Commons.findElementByExactName("1 Prepare").click();
            Commons.sleepWait();
            Assert.assertTrue(Commons.findElementByExactName("Participant: Prepare").isDisplayed());
            Commons.findElementByExactName("2 Audit").click();
            Commons.sleepWait();
            Assert.assertTrue(Commons.findElementByExactName("Participant: Audit").isDisplayed());

            // Able to see workflow summary
            Assert.assertTrue(Commons.findElementByExactName("Workflow Summary").isDisplayed());

            // cannot add or remove participants
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + WorkflowTab.ADD_PARTICIPANTS_END + "']")).isEnabled());
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + WorkflowTab.DELETE_PARTICIPANTS_END + "']")).isEnabled());

            // cannot save changes
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
        }
        else if(tab.equalsIgnoreCase("Item Grade")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ITEM_GRADE_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // Select different options from the dropDown
            // select all
            Commons.findElementByLabel("Show").click();
            Commons.findElementByLabel("All").click();
            Commons.sleepWait(1000);
            Assert.assertTrue(Commons.findElementByLabel("Show").getDomAttribute("title").equalsIgnoreCase("All"));
            // select 2 options
            Commons.findElementByLabel("All").click();
            Commons.sleepWait(1000);
            Commons.findElementByLabel("Inactive Items").click();
            Commons.sleepWait(1000);
            Commons.findElementByLabel("No AML").click();
            Commons.sleepWait(1000);
            Assert.assertTrue(Commons.findElementByLabel("Show").getDomAttribute("title").equalsIgnoreCase("Inactive Items;No AML"));

            // get results and copy it
            driver.findElement(By.cssSelector("*[id$='" + ItemGradeTab.SEARCH_END + "']")).click();
            Commons.sleepWait(5000);
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + ItemGradeTab.COPY_END + "']")).isEnabled());
        }
        else if(tab.equalsIgnoreCase("Where Used")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WHERE_USED_TAB_END + "']")).click();
            Commons.sleepWait(2000);

            // drag  and rearrange columns
            Assert.assertTrue(Commons.findElementByExactName("Description").findElement(By.xpath("./../..")).getDomAttribute("style").equalsIgnoreCase("cursor: move;"));

            if(type.equalsIgnoreCase("Items")
                    || type.equalsIgnoreCase("Documents")) {
                // column sorting
                List<WebElement> nameElements = driver.findElements(By.cssSelector("*[id$='" + WhereUsedTab.ITEM_END + "']"));
                List<String> Names = new ArrayList<>();
                for (WebElement element : nameElements) {
                    Names.add(element.getTagName());
                }
                Collections.sort(Names);
                Commons.findElementByExactName("Name").click();
                Commons.sleepWait();
                List<WebElement> sortedNameElements = driver.findElements(By.cssSelector("*[id$='" + WhereUsedTab.ITEM_END + "']"));
                List<String> sortedNames = new ArrayList<>();
                for (WebElement element : sortedNameElements) {
                    sortedNames.add(element.getTagName());
                }
                Assert.assertEquals(sortedNames, Names);

                // structure level can be selected
                Assert.assertTrue(Commons.findElementByExactName("First level").isEnabled());
                Assert.assertTrue(Commons.findElementByExactName("Top level").isEnabled());
                Assert.assertTrue(Commons.findElementByExactName("All levels").isEnabled());

                // Show Items can be selected
                Assert.assertTrue(Commons.findElementByExactName("Implemented").isEnabled());
                Assert.assertTrue(Commons.findElementByExactName("All").isEnabled());

                // modify view
                Commons.findElementByExactName("View").click();
                Commons.sleepWait();
                Commons.findElementByExactName("Columns").click();
                Commons.sleepWait();
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + WhereUsedTab.VIEW_COLUMNS_LIST_END + "']")).isDisplayed());
                Commons.findElementByExactName("View").click();
                // Export structure to Excel, FILTER, DETACH , Show cancelled changes enabled
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + WhereUsedTab.EXPORT_TO_EXCEL_END + "']")).isEnabled());
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + WhereUsedTab.FILTER_END + "']")).isEnabled());
                Assert.assertTrue(Commons.findElementByExactName("Freeze").isEnabled());
                Assert.assertTrue(Commons.findElementByExactName("Detach").isEnabled());
                Assert.assertTrue(Commons.findElementByLabel("Include substitute components").isEnabled());

                // copy is possible
                Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + WhereUsedTab.COPY_END + "']")).isEnabled());
            }

            // items are links
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + WhereUsedTab.ITEM_END + "']")).getTagName().equalsIgnoreCase("a"));
        }
        else if(tab.equalsIgnoreCase("Security")){
            // need to see if this is to be merged with Teams
            System.out.println("dummy code for Security tab to be replaced later");
        }
        else if(tab.equalsIgnoreCase("History")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.HISTORY_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.assertPresent("History");
        }
        else if(tab.equalsIgnoreCase("Others")){
            // 1. under actions save as and cancel are not allowed
            Commons.findElementByExactName("Actions").click();
            Assert.assertFalse(Commons.findElementByExactName("Save As").isEnabled());
            Assert.assertFalse(Commons.findElementByExactName("Cancel").isEnabled());

            // 2. under actions save as and cancel are not allowed
            Commons.findElementByExactName("Actions").click();
            WebElement actionsOptions = driver.findElement(By.cssSelector("*[id$='" + CommonElements.ACTIONS_OPTIONS_END + "']"));
            List<WebElement> options = actionsOptions.findElements(By.xpath("./table/tbody/tr"));
            for(WebElement option : options){
                if(option.getText().equalsIgnoreCase("Save As") || option.getText().equalsIgnoreCase("Cancel")){
                    Assert.fail("Some options under Actions should not have been present but are present.");
                }
            }

            // Change status is not possible
            Assert.assertFalse(Commons.findElementByExactName("Change Status").isEnabled());


        }
    }

    @Then("^user checks minor information of (.*) in (.*) for (.*)$")
    public void validateMinorTab(String type, String tabs, String specialCase) {
        String[] tabsList = tabs.split(",");
        for(String tab : tabsList){
            if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.GENERAL_INFORMATION_TAB_END + "']")).click();
                Commons.sleepWait(2000);

                // cannot save
                Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
                Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
            }
            else if(tab.equalsIgnoreCase("Attachments")){
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
                Commons.sleepWait(2000);

                // add and remove should not be possible
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.DELETE_ATTACHMENT_ICON_END + "']")).isEnabled());

                // check out check in should not be possible
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.CHECK_IN_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.CHECK_OUT_END + "']")).isEnabled());

                // cannot save
                Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
                Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
            }
            else if(tab.equalsIgnoreCase("Structure")) {
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.STRUCTURE_TAB_END + "']")).click();
                Commons.sleepWait(2000);

                // add and remove objects should not be possible
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + StructuresTab.CREATE_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + StructuresTab.DELETE_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + StructuresTab.SELECT_AND_ADD_END + "']")).isEnabled());

                // cannot save changes
                Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
                Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
            }
            else if(tab.equalsIgnoreCase("AML")) {
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.AML_TAB_END + "']")).click();
                Commons.sleepWait(2000);

                // add and remove objects should not be possible
                driver.findElement(By.cssSelector("select[id*='" + AMLTab.EXPAND_OPTIONS_END + "']")).click();
                Commons.sleepWait();
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AMLTab.CREATE_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AMLTab.DELETE_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + StructuresTab.SELECT_AND_ADD_END + "']")).isEnabled());

                // cannot save changes
                Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
                Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
            }
            else if(tab.equalsIgnoreCase("Affected Objects")) {
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.AFFECTED_OBJECTS_TAB_END + "']")).click();
                Commons.sleepWait(2000);

                // check if select and add remove options are disabled
                // icons
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.REMOVE_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.SELECT_AND_ADD_END + "']")).isEnabled());
                // actions
                Commons.findElementsByExactName("'Actions").get(1).click();
                Commons.sleepWait();
                Assert.assertFalse(Commons.findElementsByExactName("Select and Add").get(1).isEnabled());
                Assert.assertFalse(Commons.findElementsByExactName("Remove").get(1).isEnabled());

                // cannot save changes
                Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
                Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
            }
            else if(tab.equalsIgnoreCase("Tasks")) {
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.TASKS_TAB_END + "']")).click();
                Commons.sleepWait(2000);

                // cannot add or remove tasks. Cannot edit the tasks
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.ADD_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.DELETE_END + "']")).isEnabled());

                // cannot save changes
                Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
                Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
            }
            else if(tab.equalsIgnoreCase("Workflow")) {
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
                Commons.sleepWait(2000);

                // cannot add or remove participants.
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + WorkflowTab.ADD_PARTICIPANTS_END + "']")).isEnabled());
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + WorkflowTab.DELETE_PARTICIPANTS_END + "']")).isEnabled());

                // change status is disabled
                Assert.assertFalse(Commons.findElementByExactName("Change Status").isEnabled());

                // cannot save changes
                Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
                Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
            }
        }
    }

    @When("^(.*) creates (.*) for case (.*)$")
    public void reachEditPage(String userType,String type, String specialCase){
        if(specialCase.equalsIgnoreCase("Audit Reject")){
            return;
        }
        if(type.equalsIgnoreCase(Commons.objectType)){
            return;
        }
        // create a new object with required object type and store in commons object
        String object = "";
        if(type.equalsIgnoreCase("Items")){
            object = "";
            Commons.reachEditPage(type, object); //to be hard coded later/
            // select an Antenna item which has attachments, revisions, part of change, AML, and other tabs information already loaded
        }
        else if(type.equalsIgnoreCase("Documents")){
            object = "";
            Commons.reachEditPage(type, object);
            // select an specification document
        }
        else if(type.equalsIgnoreCase("Manufacturer Parts")){
            object = "";
            Commons.reachEditPage(type, object);
        }
        else if(type.equalsIgnoreCase("Manufacturers")){
            Commons.reachEditPage(type, "");
        }
        else if(type.equalsIgnoreCase("SCR")){
            OverviewPage.reachOverviewPage();
            Change cr = new Change("CR", "Standard Change Request");
            cr = CreateChange.createChange(cr);
            // Commons.reachEditPage("Change Requests", "");

            object = cr.number;
        }
        else if(type.equalsIgnoreCase("SDV")){
            OverviewPage.reachOverviewPage();
            Change cr = new Change("CR", "Standard Deviation");
            cr = CreateChange.createChange(cr);
            // Commons.reachEditPage("Change Requests", "");

            object = cr.number;
        }
        else if(type.equalsIgnoreCase("SCO")){
            OverviewPage.reachOverviewPage();
            Change co = new Change("CO", "Standard Change Order");
            co = CreateChange.createChange(co);
            // Commons.reachEditPage("Change Requests", "");

            object = co.number;
        }
        else if(type.equalsIgnoreCase("NRU")){
            OverviewPage.reachOverviewPage();
            Change co = new Change("CO", "No Revision Update");
            co = CreateChange.createChange(co);
            // Commons.reachEditPage("Change Requests", "");

            object = co.number;
        }

        Commons.objectType = type;
        Commons.objectNumber = object;
    }

}
