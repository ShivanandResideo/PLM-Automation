package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import webElements.EditChangePage;
import webElements.OracleHome;
import webElements.OverviewPage;
import webElements.SearchManagePage;

import java.util.List;

public class AdvancedSearchSteps {

    WebDriver driver = Commons.driver;
    public static Select select;

    @When("^user reaches Advanced Search page for (.*)$")
    public void advancedSearchPage(String type){
        driver.navigate().refresh();
        driver.findElement(By.id(OracleHome.HOME)).click();
        Commons.sleepWait(3000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
        Commons.sleepWait();
        Select select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_DD_END + "']")));
        select.selectByVisibleText(type);
        Commons.sleepWait(2000);
        Commons.findElementByExactName(driver, "Advanced").click();
        Commons.sleepWait(2000);
    }

    @Then("^user checks advanced search features of (.*) for (.*)$")
    public void checkAdvancedSearchFeatures(String type, String specialCase) {

        select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
        Assert.assertEquals("Application Default", select.getFirstSelectedOption().getText());

        // save new search
        SearchManagePage.saveSearch(false);
        // check if the page loaded after save
        Commons.findInputElementByParameterName("Saved Search").getDomAttribute("class");
        select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
        Assert.assertTrue(select.getFirstSelectedOption().getText().contains("Application Default copy"));

        // go to default search
        select.selectByVisibleText("Application Default");
        Commons.sleepWait(2000);
        // relocate element after page refresh
        select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
        select.selectByContainsVisibleText("Application Default copy");
        Commons.sleepWait(2000);

        // Add fields
        int before = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADVANCED_LEFT_FIRST_SEARCH_ITEM_END + "']")).findElements(By.xpath("./../tr")).size();
        int i = 0;
        if (type.equalsIgnoreCase("Items") || type.equalsIgnoreCase("Documents")) {
            SearchManagePage.addSearchFields("Approval Status");
            Commons.sleepWait();
            SearchManagePage.addSearchFields("Manufacturer Part Number");
            Commons.sleepWait();
            int after = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADVANCED_LEFT_FIRST_SEARCH_ITEM_END + "']")).findElements(By.xpath("./../tr")).size();
            Assert.assertEquals(before, after - 1);
        } else if (type.contains("Change")) {
            SearchManagePage.addSearchFields("Created By");
            Commons.sleepWait();
            SearchManagePage.addSearchFields("Organization Name");
            Commons.sleepWait();
            int after = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADVANCED_LEFT_FIRST_SEARCH_ITEM_END + "']")).findElements(By.xpath("./../tr")).size();
            Assert.assertEquals(before, after - 1);
        }


        // different logic for manufacturers for adding and deleting fields
        if (type.contains("Manufacturer")) {
            before = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADVANCED_LEFT_FIRST_SEARCH_ITEM_END + "']")).findElements(By.xpath("./../tr")).size();
            ;
            Commons.findElementByExactName(driver, "Add ").click();
            Commons.sleepWait(2000);
            Commons.findElementsByExactName(driver, "Created By").getLast().click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':sms1::move']")).click();
            i++;
            Commons.sleepWait(2000);
            Commons.findElementsByExactName(driver, "Creation Date").getLast().click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':sms1::move']")).click();
            i++;
            Commons.sleepWait(2000);
            Commons.findElementsByExactName(driver, "O").getLast().click();
            Commons.sleepWait();
            int after = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADVANCED_LEFT_FIRST_SEARCH_ITEM_END + "']")).findElements(By.xpath("./../tr")).size();
            try {
                Assert.assertTrue(before < after);
            } finally {
                // remove the newly added search parameters
                Commons.findElementByExactName(driver, "Add ").click();
                Commons.sleepWait(2000);
                Commons.findElementsByExactName(driver, "Created By").getLast().click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$=':sms1::remove']")).click();
                Commons.sleepWait(2000);
                Commons.findElementsByExactName(driver, "Creation Date").getLast().click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$=':sms1::remove']")).click();
                Commons.sleepWait(2000);
                Commons.findElementsByExactName(driver, "O").getLast().click();
                Commons.sleepWait();
                SearchManagePage.saveSearch(true);
                Commons.sleepWait();
                after = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADVANCED_LEFT_FIRST_SEARCH_ITEM_END + "']")).findElements(By.xpath("./../tr")).size();
                if (!(before == after)) {
                    System.out.println("Removing search fields has failed.");
                }
            }
        }

        // save updated search
        SearchManagePage.saveSearch(true);
        // check if the page loaded after save
        Commons.findInputElementByParameterName("Saved Search").getDomAttribute("class");
        select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
        Assert.assertTrue(select.getFirstSelectedOption().getText().contains("Application Default copy"));

        //



//        to do incomplete. only passing in UAT
        // SearchManagePage.compareTopDD(type);

        if (!type.contains("Manufacturer")) {
            // select from top drop down
            SearchManagePage.selectFromTopDD(type);
            Commons.sleepWait(2000);
            select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
            Assert.assertTrue(select.getFirstSelectedOption().getText().contains("Application Default"));
            Commons.sleepWait(2000);
            SearchManagePage.saveSearch(false);
            Commons.sleepWait(2000);
            select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
            Assert.assertTrue(select.getFirstSelectedOption().getText().contains("Application Default copy"));
        }

        // Select an item class from top left

        // Adding and removing columns from a view item reflected into the results section
        Commons.findElementByExactName(driver, "View").click();
        Commons.sleepWait(2000);
        if (type.equalsIgnoreCase("Items") || type.equalsIgnoreCase("Documents")) {
            Commons.findElementByExactName(driver, "Add Columns").click();
        } else if (type.contains("Change")) {
            Commons.findElementByExactName(driver, "Show more columns").click();
        } else if (type.contains("Manufacturer")) {
            Commons.findElementByExactName(driver, "Columns").click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(driver, "Manage Columns...").click();
        }
        Commons.sleepWait(2000);



        // reset to minimum columns
        if (type.contains("Manufacturer")) {
            driver.findElement(By.cssSelector("*[id$=':_shwClmDS::removeall']")).click();
        }
        else{
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADD_COLUMNS_REMOVE_ALL_END + "']")).click();
        }
        Commons.sleepWait(2000);
        // click OK button
        try {
            Commons.findElementByExactName(driver, "O").click();
        }
        catch(Exception e){
            Commons.findElementByExactName(driver.findElement(By.cssSelector("*[id$=':_PCPop::popup-container']")), "OK").click();
        }
        Commons.sleepWait(2000);

        SearchManagePage.compareColumns(type);



        // check if all the items are present in the top left corner dropdown
//        Commons.findElementByAttributeValue("title", "Search and Select").click();
//        List<WebElement> rows = Commons.findElementsByAttributeValue()
                // will pick it up later as the list id dynamically loading cannot be done in one shot



        // check if ascending and descending is visible
//        Commons.findElementByExactName(driver, "View").click();
//        Commons.sleepWait(1000);
//        Commons.findElementsByExactName(driver,"Sort").getLast().click();
//        Commons.sleepWait(1000);
//        Commons.findElementByExactName(driver,"Ascending");
//        Commons.findElementByExactName(driver,"Descending");
//        Commons.findElementByExactName(driver, "View").click();

        // reorder columns visible and active
//        Commons.findElementByExactName(driver, "View").click();
//        Commons.sleepWait(2000);
//        Commons.findElementByExactName(driver,"Reorder Columns...").click();
//        Commons.sleepWait(1000);
//        Commons.findElementByExactName(driver,"Cancel").click();
//        Commons.sleepWait(1000);
//        Commons.findElementByExactName(driver, "View").click();

        // Export to Excel
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.EXPORT_TO_EXCEL_END + "']")).isEnabled());

        // Filter
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.QUERY_BY_EXAMPLE_END + "']")).isEnabled());

        // Detach
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.DETACH_END + "']")).isEnabled());

        // Copy
        // Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.COPY_END + "']")).isEnabled());

        // Img hide/show
//        Select image = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.IMG_HIDE_SHOW_END + "']")));
//        Assert.assertTrue(image.getOptions().get(0).getText().equalsIgnoreCase("Hide"));
//        Assert.assertTrue(image.getOptions().get(1).getText().equalsIgnoreCase("Show"));

        // Add saved search to watch list
        if(type.contains("Change")){
            select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
            select.selectByContainsVisibleText("Application Default copy");
            Commons.sleepWait(2000);
            Commons.scrollToViewReverse(Commons.findElementByExactName("Manage Watchlist"));
            Commons.findElementByExactName("Manage Watchlist").click();
            // Commons.scrollTillFindElement("Manage Watchlist").click();
            Commons.sleepWait(2000);
            // Commons.findElementsByExactName(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.MANAGE_WATCHLIST_POPUP_END + "']")), "Application Default copy").click();
            Commons.findElementsByPartialName("Application Default copy").getLast().click();
            Commons.sleepWait(1000);
            Commons.findElementByExactName(driver, "ave and Close").click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(driver, "Manage Watchlist").click();
            Commons.sleepWait(2000);
            String checkboxId = Commons.findElementsByPartialName("Application Default copy").getLast().getDomAttribute("for");
                    // Commons.findElementByExactName(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.MANAGE_WATCHLIST_POPUP_END + "']")), "Application Default copy").getDomAttribute("for");
            WebElement checkbox = driver.findElement(By.id(checkboxId));
            Commons.sleepWait();
            Assert.assertTrue(checkbox.isSelected());
            Commons.scrollToView(Commons.findElementByExactName("ancel"));
            Commons.findElementsByExactName("ancel").getLast().click();
            // Commons.findElementByExactName(driver, "ancel").click();
            Commons.sleepWait(2000);
        }

        // delete all the saved searches created
        // delete not working BUG??? Delete process is going through but in next iteration the deleted tests are still visible
//        while(true){
//            try {
//                select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
//                select.selectByContainsVisibleText("Application Default copy");
//                Commons.sleepWait();
//            } catch (NoSuchElementException e) {
//                break;
//            }
//            SearchManagePage.deleteSearch();
//        }

        // not working after env independent code
//        to-do
        if(!type.contains("Manufacturer")) {
            // send to alpha
            Commons.reachEditPage(type);
            Commons.sleepWait();
            Commons.findElementByExactName(driver, "Actions").click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(driver, "Send Object").click();
            Commons.sleepWait(2000);
            WebElement popUp = driver.findElement(By.className("AFPopupSelector"));
            Commons.findElementByAttributeValue("title","Add").click();
            Commons.sleepWait(2000);
            WebElement element = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_USERNAME_END + "']"));
            element.clear();
            element.sendKeys("SV_Alpha_Automation");
            Commons.sleepWait();
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_SEARCH_END + "']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_RESULT_SECTION_END + "']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.SELECT_AND_ADD_APPLY_AND_CLOSE_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(Commons.findElementByExactName(popUp, "alpha Automation").isDisplayed());
            Commons.findElementByLabel("Message").sendKeys(Commons.userRandomString("Message"));
            Commons.sleepWait(2000);
            Commons.findElementByExactName(popUp, "end").click();

            // check increase in the notification count
            // pending as it is taking time to reflect
            Commons.sleepWait(20000);

        }

        // click on done to close the tab
        Commons.findElementByExactName("ne").click();

    }
}
