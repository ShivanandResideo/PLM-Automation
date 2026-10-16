package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.CreateChange;
import utils.LoginCreds;
import utils.objects.Change;
import webElements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OverviewStep{

    WebDriver driver = Commons.driver;

    @When("^user reaches overview page$")
    public void overviewPage(){
        driver.navigate().refresh();
        driver.findElement(By.id(OracleHome.HOME)).click();
        Commons.sleepWait(3000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();
    }

    @When("^user reaches (.*) edit page$")
    public void editPage(String type){
        overviewPage();
        Commons.sleepWait(2000);
        Commons.reachEditPage(type, "SV_Gamma_Automation");
    }

    @When("^user reaches new edit page for type (.*)$")
    public void reachEditPage(String type){
        overviewPage();
        Commons.sleepWait(2000);
        Commons.reachEditPage(type);
    }

    @Then("^user checks availability of search features for (.*)$")
    public void checkQuickSearch(String type){
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
        Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_DD_END + "']")), "Search Options");

        // set the search type
        Select select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_DD_END + "']")));
        select.selectByVisibleText(type);
        Commons.sleepWait(2000);

        if(type.equalsIgnoreCase("Items") || type.equalsIgnoreCase("Documents")) {

            // start of the description
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(Commons.objectDescription.substring(0, 10));
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            Commons.sleepWait(2000);

            // middle of a description
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(Commons.objectDescription.substring(10, 20));
            Commons.sleepWait(2000);

            // possible bug - reported Nov 2025
//            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            Commons.sleepWait(2000);

            // % wildcard not allowed
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("%Desc");
            Commons.sleepWait(2000);
            boolean result = false;
            try {
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            } catch (NoSuchElementException ignore) {
                result = true;
            }
            Assert.assertTrue("Checking for wildcards in search.Wildcards should not be allowed in search.", result);
            Commons.sleepWait(2000);

            // start of the number.
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            if(type.equalsIgnoreCase("items"))
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("205-");
            else if(type.equalsIgnoreCase("documents"))
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("930-");
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());

            // multiple items and documents search allowed
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
            Commons.sleepWait();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
            Commons.sleepWait();
            Commons.findElementByPartialName("Search Multiple ").click();
            Commons.sleepWait(2000);
            List<String> items = new ArrayList<>();
            if (type.equalsIgnoreCase("Items")){
                items = Commons.readFromExcel("List", "Alpha", "Items_50", "column");
            }else if (type.equalsIgnoreCase("Documents")){
                items = Commons.readFromExcel("List", "Alpha", "Documents_50", "column");
            }
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_TEXT_AREA_END + "']")).clear();
            for(String item : items){
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_TEXT_AREA_END + "']")).sendKeys(item + Keys.ENTER);
            }
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BUTTON_END + "']")).click();
            Commons.sleepWait(2000);
            if (type.equalsIgnoreCase("Items")){
                Assert.assertTrue(Commons.findElementsByExactName("Manage Items").size() == 3);
                Assert.assertTrue(Commons.findElementsByExactName("Manage Items").getLast().getTagName().equalsIgnoreCase("h1"));
            }else if (type.equalsIgnoreCase("Documents")){
                Assert.assertTrue(Commons.findElementsByExactName("Manage Documents").size() == 3);
                Assert.assertTrue(Commons.findElementsByExactName("Manage Documents").getLast().getTagName().equalsIgnoreCase("h1"));
            }

            // enter key takes us to the advanced search page
            Commons.sleepWait(2000);
            Commons.findElementByExactName("ne").click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
            Commons.findElementByPartialName("Search Single ").click();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(Keys.ENTER);
            Commons.sleepWait(2000);
            // search for the title element and if not present will throw exception
            Commons.findElementByExactName("Advanced Search");
            if (type.equalsIgnoreCase("Items")){
                Assert.assertTrue(Commons.findElementsByExactName("Manage Items").size() == 3);
                Assert.assertTrue(Commons.findElementsByExactName("Manage Items").getLast().getTagName().equalsIgnoreCase("h1"));
            }else if (type.equalsIgnoreCase("Documents")){
                Assert.assertTrue(Commons.findElementsByExactName("Manage Documents").size() == 3);
                Assert.assertTrue(Commons.findElementsByExactName("Manage Documents").getLast().getTagName().equalsIgnoreCase("h1"));
            }
            Commons.findElementByExactName("ne").click();

        }
        else if(type.equalsIgnoreCase("Change Orders") || type.equalsIgnoreCase("Change Requests")){
            // search with description should not work
            // middle of a description
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("_Desc");
            Commons.sleepWait(2000);
            boolean result = false;
            try {
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            }
            catch(NoSuchElementException ignore){
                result = true;
            }
            Assert.assertTrue("Checking if description in search will work. For CO and CR it should not work.", result);
            Commons.sleepWait(2000);

            // start of the number
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            if(type.equalsIgnoreCase("Change Orders"))
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("SCO-");
            else if(type.equalsIgnoreCase("Change Requests"))
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("SCR-");
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            Commons.sleepWait(2000);
            // enter key takes us to the advanced search page
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(Keys.ENTER);
            Commons.sleepWait(2000);
//            Assert.assertTrue(driver.findElement(By.className("xnw")).getText().contains("Manage"));
            // search for the title element and if not present will throw exception
            Commons.findElementByExactName("Advanced Search");
        }
        else if(type.equalsIgnoreCase("Manufacturer Parts")){
            // search with description should not work
            // middle of a description
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("_Desc");
            Commons.sleepWait(2000);
            try {
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            }
            catch(NoSuchElementException ignore){

            }
            Commons.sleepWait(2000);
            // start of the number
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("101");
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            Commons.sleepWait(2000);
            // enter key takes us to the advanced search page
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(Keys.ENTER);
            Commons.sleepWait(2000);
//            Assert.assertTrue(driver.findElement(By.className("xnw.")).getText().contains("Manage"));
            // search for the title element and if not present will throw exception
            Commons.findElementByExactName("Manage Manufacturer Parts");
        }
        else if(type.equalsIgnoreCase("Manufacturers")){
            //description
            // start of a description
            select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_DD_END + "']")));
            select.selectByVisibleText(type);
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
//            visit again after page loading issue is fixed
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("Automation");
            Commons.sleepWait(2000);
            try {
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            }
            catch(NoSuchElementException ignore){

            }
            Commons.sleepWait(2000);
            // name middle
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("INC");
            Commons.sleepWait(2000);
            try {
                Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            }
            catch(NoSuchElementException ignore){

            }
            Commons.sleepWait(2000);
            // name starting
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
//            visit again after page loading issue is fixed
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("Automation");
            Commons.sleepWait(2000);
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_RESULT_POPUP_END + "']")).isDisplayed());
            Commons.sleepWait(2000);
            // name enter
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(Keys.ENTER);
            // validate if the correct page is reached after the pge loading issue is fixed.
        }
        else if(type.equalsIgnoreCase("Advanced")){
            // able to open advanced search option from the search
            Commons.findElementByExactName(driver, "Advanced").click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.className("xnw ")).getText().contains("Manage"));
        }

        // under tasks there is option to manage objects
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();
        Commons.findElementByExactName("Manage " + type).click();
        Commons.sleepWait();
        // check if advanced search page is reached. The heading is one of the 2 mentioned below
        try {
            Commons.findElementByExactName("Advanced Search");
        } catch(Exception e) {
            Commons.findElementByExactName("Search Criteria");
        }
    }

    @Then("^user checks availability of task features$")
    public void checkTaskList(){
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();
        Commons.sleepWait();
        if(Commons.userType.equalsIgnoreCase("charlie") || Commons.userType.equalsIgnoreCase("Alpha") || Commons.userType.equalsIgnoreCase("Beta")){
            try{
                Commons.findElementByExactName(driver, "Create Item");
                Assert.fail("'Create Item' feature should not have been present");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(true);
            }
            try{
                Commons.findElementByExactName(driver, "Create Document");
                Assert.fail("'Create Document' feature should not have been present");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(true);
            }
            try{
                Commons.findElementByExactName(driver, "Create Change Order");
                Assert.fail("'Create Change Order' feature should not have been present");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(true);
            }
            try{
                Commons.findElementByExactName(driver, "Create Manufacturer Part");
                Assert.fail("'Create Manufacturer Part' feature should not have been present");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(true);
            }
        }
        if(Commons.userType.equalsIgnoreCase("Alpha") || Commons.userType.equalsIgnoreCase("Beta")){
            try{
                Commons.findElementByExactName(driver, "Create Change Request");
                Assert.fail("'Create Change Request' feature should not have been present");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(true);
            }
        }
        if(Commons.userType.equalsIgnoreCase("Beta")){
            try{
                Commons.findElementByExactName(driver, "Create Manufacturer");
                Assert.fail("'Create Manufacturer' feature should not have been present");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(true);
            }
        }
        if(Commons.userType.equalsIgnoreCase("Delta")){
            try{
                Commons.findElementByExactName(driver, "Create Change Order").click();
                new Select(Commons.findElementByLabel("Change Type")).selectByVisibleText("Admin Data Update");
                Assert.fail("'Admin Data Update' Change Type should not have been present for Delta");
            } catch (NoSuchElementException e) {
                Assert.assertTrue(true);
            }
        }
    }

    @Then("^(.*) works as expected for (.*)$")
    public void overviewFunctionalities(String functionality, String specialCase) {

        driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.WAITING_FOR_REVIEW_END +"']")).click();
        int i=0;
        while(true) {
            try {
                i++;
                Commons.sleepWait(5000);
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.WFR_ELEMENT_ROW_END + "']")).findElement(By.cssSelector("*[id$='" + OverviewPage.WFR_ELEMENT_LINK_OPEN_NEW_END + "']")).getText().split(" ");
                break;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                if(i > 7){
                    break;
                }
                System.out.println("Element not loaded properly. trying again...");
            }
        }

        String[] linkText = driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.WFR_ELEMENT_ROW_END +"']")).findElement(By.cssSelector("*[id$='" + OverviewPage.WFR_ELEMENT_LINK_OPEN_NEW_END + "']")).getText().split(" ");
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.WFR_ELEMENT_APPROVE_END +"']")).isEnabled());
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.WFR_ELEMENT_REJECT_END +"']")).isEnabled());

        driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.WFR_ELEMENT_ROW_END +"']")).findElement(By.cssSelector("*[id$='" + OverviewPage.WFR_ELEMENT_LINK_OPEN_END + "']")).click();
        Commons.sleepWait(3000);
        String title = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TITLE_END +"']")).getText();
        Assert.assertTrue(title.contains(linkText[0]));
        Assert.assertTrue(title.contains(linkText[1]));
        Assert.assertTrue(title.contains(linkText[2]));

        // close the new tab and return to overview
        driver.findElement(By.cssSelector("*[id$='"+ OracleHome.CLOSE_TAB_END +"']")).click();

        driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.WFR_ELEMENT_ROW_END +"']")).findElement(By.cssSelector("*[id$='" + OverviewPage.WFR_ELEMENT_LINK_OPEN_NEW_END + "']")).click();
        Commons.sleepWait(3000);
        Set<String> allWindowHandles = driver.getWindowHandles();
        String mainWindowHandle = driver.getWindowHandle();
        Commons.switchWindow(allWindowHandles, mainWindowHandle);

        // Perform actions on the new window
        System.out.println(driver.getWindowHandle());
        driver.switchTo().defaultContent();
        driver.switchTo().frame(0);
        // verifying link and link text

        Assert.assertEquals("a", Commons.findElementByExactName(linkText[2]).findElement(By.xpath("./..")).getTagName());
        // Assert.assertEquals(linkText[2], driver.findElement(By.className("c21")).getText());
        // check if the section with element line details is present
        Commons.scrollToView(Commons.findElementByExactName("Line details"));
        Commons.scrollToViewReverse(driver.findElement(By.xpath("//*[text()='Approve']")));
        Assert.assertEquals("button", driver.findElement(By.xpath("//*[text()='Approve']")).getTagName());
        Assert.assertEquals("button", driver.findElement(By.xpath("//*[text()='Reject']")).getTagName());
        Assert.assertEquals("button", driver.findElement(By.xpath("//*[text()='Claim']")).getTagName());

        //Check if all sections present
        List<WebElement> popUpHeaders = driver.findElements(By.className("c12"));
        for(WebElement element : popUpHeaders){
            System.out.println(element.getText());
        }
        Commons.scrollToView(Commons.findElementByExactName("Header details"));
        Commons.scrollToView(Commons.findElementByExactName("Line details"));
        Commons.scrollToView(Commons.findElementByExactName("Approvers"));
        // id changed Previously it was Approval History, now it is Approvers

        // Close the new window
        driver.close();
        // Switch back to the main window
        driver.switchTo().window(mainWindowHandle);
        Commons.sleepWait(3000);
//        The below line might not be required but once fully automated have to check with team
//        create another window -> sign-in with delta user -> create a SCO -> Put it in Audit status -> store the SCO number -> change the status to audit
        // driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "n");
//        Actions action = new Actions(driver);
//        action.sendKeys(Keys.CONTROL + "n").perform();

//        driver.switchTo().newWindow(WindowType.WINDOW);

        // sign-out -> Sign-in using delta -> create SCO -> sign-out -> sign-in using Gamma
        Commons.sleepWait(3000);
//        allWindowHandles = driver.getWindowHandles();
//        Commons.switchWindow(allWindowHandles, mainWindowHandle);

//        Hard coding the audit state SCO in Constants class
//        Commons.logout();
//
//        Commons.login(new LoginCreds("Delta"));
//        Change CO = new Change();
//        CO = CreateChange.createChange(CO);
//        // Change the SCO to Audit

        // Check the options under the actions menu
        Commons.findElementByExactName("Actions").click();
        WebElement dropDown = driver.findElement(By.cssSelector("*[id$=':m1::ScrollBox']"));
        Assert.assertEquals("Approve", dropDown.findElement(By.cssSelector("tr[id$=':cmi1']")).getText());
        Assert.assertEquals("Reject", dropDown.findElement(By.cssSelector("tr[id$=':cmi2']")).getText());
        Assert.assertEquals("Show in New Tab", dropDown.findElement(By.cssSelector("tr[id$=':cmi3']")).getText());
        Assert.assertEquals("Open Task", dropDown.findElement(By.cssSelector("tr[id$=':cmi4']")).getText());

    }

    @Then("^user checks editability for (.*)$")
    public void checkEditability(String type){
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.GENERAL_INFORMATION_TAB_END + "']")).click();
        try {
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
            Assert.fail("The Save button should not be present");
        }
        catch(NoSuchElementException | ElementNotInteractableException e){
            Assert.assertTrue(true);
        }
        try {
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
            Assert.fail("The Save And Close button should not be present");
        }
        catch(NoSuchElementException | ElementNotInteractableException e){
            Assert.assertTrue(true);
        }

        // attachment tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
        try {
            driver.findElement(By.cssSelector("*[id$='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).click();
            Assert.fail("The Add Attachment icon button should not be present");
        }
        catch(NoSuchElementException | ElementNotInteractableException e){
            Assert.assertTrue(true);
        }

        if(type.equalsIgnoreCase("Manufacturer Parts")) {
            // not other tests for this type
            return;
        }

        // structure tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.STRUCTURE_TAB_END + "']")).click();
        try {
            if(!driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_END + "']")).findElement(By.xpath("./..")).getDomAttribute("aria-disabled").equalsIgnoreCase("true")){
                Assert.fail("The Add Attachment icon button should not be present/active");
            }
            Assert.assertTrue(true);
        }
        catch(NoSuchElementException | ElementNotInteractableException e){
            Assert.assertTrue(true);
        }

        // relationship tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
        try {
            if(!driver.findElement(By.cssSelector("*[id$='" + RelationshipTab.SELECT_AND_ADD_END + "']")).findElement(By.xpath("./..")).getDomAttribute("aria-disabled").equalsIgnoreCase("true")){
                Assert.fail("The Add Relationship icon button should not be present/Active");
            }
            Assert.assertTrue(true);
        }
        catch(NoSuchElementException | ElementNotInteractableException e){
            Assert.assertTrue(true);
        }

        if(type.equalsIgnoreCase("Documents"))
            return;

        // AML tab
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.AML_TAB_END + "']")).click();
        try {
            if(!driver.findElement(By.cssSelector("*[id$='" + AMLTab.SELECT_AND_ADD_END + "']")).findElement(By.xpath("./..")).getDomAttribute("aria-disabled").equalsIgnoreCase("true")){
                Assert.fail("The Add Relationship icon button should not be present/Active");
            }
            Assert.assertTrue(true);
        }
        catch(NoSuchElementException | ElementNotInteractableException e){
            Assert.assertTrue(true);
        }
    }

    @Then("^user checks the ability to add favorites for (.*)$")
    public void addToFavorites(String type){
        String favorite = driver.findElement(By.cssSelector("*[id$='" +  CommonElements.EDIT_PAGE_FAVORITE_STAR_END + "']")).getDomAttribute("title");
        if(favorite.equalsIgnoreCase("Remove from Favorites")){
            driver.findElement(By.cssSelector("*[id$='" +  CommonElements.EDIT_PAGE_FAVORITE_STAR_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" +  CommonElements.EDIT_PAGE_FAVORITE_STAR_END + "']")).getDomAttribute("title").equalsIgnoreCase("Mark as Favorite"));
        }
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.EDIT_PAGE_FAVORITE_STAR_END + "']")).click();
        Commons.sleepWait(2000);
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" +  CommonElements.EDIT_PAGE_FAVORITE_STAR_END + "']")).getDomAttribute("title").equalsIgnoreCase("Remove from Favorites"));

        // store number to refer again in the list of favorites
        String partNumber;
        if(type.equalsIgnoreCase("Manufacturer Parts")){
            partNumber = Commons.findElementByLabel("Manufacturer Part Number").getText();
        }
        else {
            partNumber = Commons.findElementByLabel("Name").getText();
        }

        // favorite appears in Ribbon STAR
        driver.findElement(By.cssSelector("*[id$='" + OracleHome.FRI_STAR + "']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + OracleHome.FAVORITES_END + "']")).click();
        Commons.sleepWait(2000);
        boolean result = false;
        List<WebElement> favoriteItems = driver.findElement(By.cssSelector("*[id$='" + OracleHome.FAVORITES_AREA_END + "']")).findElements(By.xpath("./div"));
        for(WebElement row : favoriteItems){
            if(row.getText().equalsIgnoreCase(partNumber)) {
                result = true;
                break;
            }
        }
        Assert.assertTrue(result);

        if(type.equalsIgnoreCase("Items") || type.equalsIgnoreCase("Documents")) {
            // favorite appears in clipBoard favorites
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CLIPBOARD_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(driver, "Favorites").click();
            Commons.sleepWait(1000);
            result = false;
            for(WebElement row : driver.findElements(By.cssSelector("*[id$='" + OverviewPage.FAVORITES_ITEMS_LIST_ITEMS_END + "']"))){
                if(row.getText().contains(partNumber)){
                    result = true;
                    break;
                }
            }
            Assert.assertTrue(result);

            // favorites appear in the Overview page
            Commons.findElementByExactName(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_TAB_AREA_END + "']")), "Overview").click();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.EXPAND_MFI_END + "']")).click();
            Commons.sleepWait(2000);
            try {
                Commons.findElementByExactName(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.MFI_AREA_END + "']")), partNumber);
            }
            catch (Exception e){
                Assert.fail("The part number which was added to favorites is not present in the Overview -> My Favorite Items section. Hence, failing the test.");
            }
        }
    }

    @Then("^user checks the ability to edit different sections for (.*)$")
    public void editPageFunctionality(String type){

    }
}
