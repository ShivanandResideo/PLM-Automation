package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import stepDefinitions.AdvancedSearchSteps;
import utils.Commons;

import java.util.List;

public class SearchManagePage {

    public static final String ITEM_INPUT_FIELD_END = ":efqrp:value00::content";

    public static final String SAVED_SEARCH_END = "::saveSearch::content";
    public static final String SAVE_OK_END = "::SSDlg::ok";
    public static final String PERSONALIZE_OK_END = ":efqrp::PSSDlg::ok";
    public static final String PERSONALIZE_APPLY_END = ":efqrp::apply";
    public static final String DELETE_SAVED_SEARCH_END = ":efqrp::delete";
    public static final String YES_DELETE_SAVED_SEARCH_END = ":efqrp::DWDlg::yes";
    public static final String ADVANCED_LEFT_FIRST_SEARCH_ITEM_END = ":criterion0";
    public static final String ADD_FIELDS_INPUT_END = ":ATR4:_ATTp:fit16::content";
    public static final String ADD_FIELDS_SELECT_ATTRIBUTE_END = ":cb1::icon";
    public static final String ADD_FIELDS_OK_END = ":okButtonAddFields";
    public static final String SAVE_OK_REPLACE_YES_END = "::OWD::yes";

    public static final String TOP_DD_INPUT_BOX_END = ":simplePanel1:inputComboboxListOfValues1::content";
    public static final String TOP_DD_BUTTON_END = ":simplePanel1:inputComboboxListOfValues1::lovIconId";
    public static final String TOP_DD_BOX_END = ":simplePanel1:inputComboboxListOfValues1::dropdownPopup::dropDownContent::db";

    public static final String SEARCH_POP_UP_INPUT_END = ":simplePanel1:inputComboboxListOfValues1::_afrLovInternalQueryId:value00::content";
    public static final String SEARCH_POP_UP_SEARCH_END = ":simplePanel1:inputComboboxListOfValues1::_afrLovInternalQueryId::search";
    public static final String SEARCH_POP_UP_RESULT_END = "_afrLovInternalTableId::db";
    public static final String SEARCH_POP_UP_OK_END = "::lovDialogId::ok";

    public static final String IMAGE_COLUMN_HEADER_END = ":rstab:_ATp:table1:image";
    public static final String ADD_COLUMNS_REMOVE_ALL_END = ":cb5";
    public static final String ADD_COLUMNS_MOVE_END = ":cb1";
    public static final String ADD_COLUMNS_FIND_END = ":ATR4:_ATTp:fit16::content";

    public static final String EXPORT_TO_EXCEL_END = ":rstab:_ATp:ATex";
    public static final String QUERY_BY_EXAMPLE_END = ":rstab:_ATp:_qbeTbr";
    public static final String DETACH_END = ":rstab:_ATp:_dchTbr";
    public static final String COPY_END = ":rstab:_ATp:clipBoardLink";
    public static final String IMG_HIDE_SHOW_END = ":rstab:_ATp:soc311::content";

    public static final String MANAGE_WATCHLIST_POPUP_END = ":p1::popup-container";

    public static final String RESULTS_COLUMNS_FIRST_EMPTY_HEADER_END = ":rstab:_ATp:table1:j_id__ctru121pc13";

    public static WebDriver driver = Commons.driver;
    public static Select select = AdvancedSearchSteps.select;

    public static void saveSearch(boolean old){

        try {
            Commons.findElementByExactName(driver, "Save...").click();
        }
        catch (NoSuchElementException e){
            Commons.findElementByExactName(driver, "Sa").click();
        }
        driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVE_OK_END + "']")).click();
        Commons.sleepWait(2000);
        // we set old to true when we are re-using the savedSearch name
        if(old){
            driver.findElement(By.cssSelector("*[id$='" + SAVE_OK_REPLACE_YES_END + "']")).click();
            Commons.sleepWait(2000);
        }
    }

    public static void deleteSearch(){
        select = new Select(driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SAVED_SEARCH_END + "']")));
        Commons.sleepWait(2000);
        select.selectByVisibleText("Personalize...");
        Commons.sleepWait(2000);
        // select the saved search to be deleted
        while(true) {
            try {
                new Select(Commons.findElementsByLabel("Saved Searches").getLast()).selectByContainsVisibleText("Application Default copy");
                System.out.println("Deleting the saved search " + new Select(Commons.findElementsByLabel("Saved Searches").getLast()).getFirstSelectedOption().getText());
            }
            catch (NoSuchElementException e) {
                break;
            }
            Commons.sleepWait();
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.DELETE_SAVED_SEARCH_END + "']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.YES_DELETE_SAVED_SEARCH_END + "']")).click();
            Commons.sleepWait(2000);
        }
        driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.PERSONALIZE_APPLY_END + "']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.PERSONALIZE_OK_END + "']")).click();
        Commons.sleepWait(2000);
        // Assert.assertEquals("Application Default", select.getFirstSelectedOption().getText());
    }

    public static void addSearchFields(String field){
        Commons.findElementByExactName(driver, "Add Fields").click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + ADD_FIELDS_INPUT_END + "']")).sendKeys(field);
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + ADD_FIELDS_SELECT_ATTRIBUTE_END + "']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + ADD_FIELDS_OK_END + "']")).click();
    }

    public static void compareTopDD(String type){
        driver.findElement(By.cssSelector("*[id$='" + TOP_DD_BUTTON_END + "']")).click();
        Commons.sleepWait(2000);
        List<WebElement> actualList = driver.findElement(By.cssSelector("*[class$='xem p_AFSelected']")).findElements(By.xpath("./../tr"));
        List<String> excelList = Commons.readFromExcel("DropDowns", "Manage", type, "column");
        for(WebElement element : actualList){
            //Commons.scrollToView();
            Assert.assertTrue(excelList.contains(element.findElement(By.xpath("./td")).getText()));
        }
    }

    public static void selectFromTopDD(String type) {
        // driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.TOP_DD_BUTTON_END + "']")).click();
        try {
            Commons.findElementByAttributeValue("title", "Search and Select").click();
        } catch (NoSuchElementException e){
            Commons.findElementByAttributeValue("title", "Search: Change Order Type").click();
        }
        Commons.sleepWait(2000);
        Commons.findElementByExactName(driver, "Search...").click();
        Commons.sleepWait(2000);

        if (type.equalsIgnoreCase("Items")) {
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SEARCH_POP_UP_INPUT_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SEARCH_POP_UP_INPUT_END + "']")).sendKeys("Antenna");
        }
        else if (type.equalsIgnoreCase("Documents")) {
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SEARCH_POP_UP_INPUT_END + "']")).clear();
            Commons.findElementByLabel(" Item Class").sendKeys("Specification");
        }
        else if (type.equalsIgnoreCase("Change Orders")) {
            Commons.findElementsByLabel(" Name").getLast().clear();
            Commons.findElementsByLabel(" Name").getLast().sendKeys("Change Order");
        }
        else if (type.equalsIgnoreCase("Change Requests")) {
            Commons.findElementsByLabel(" Name").getLast().clear();
            Commons.findElementsByLabel(" Name").getLast().sendKeys("Change Request");
        }
        Commons.findElementsByExactName("Search").getLast().click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SEARCH_POP_UP_RESULT_END + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.SEARCH_POP_UP_OK_END + "']")).click();
    }

    public static void compareColumns(String type){
        int before, after;
        if(type.equalsIgnoreCase("Items") || type.equalsIgnoreCase("Documents")) {
            before = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.RESULTS_COLUMNS_FIRST_EMPTY_HEADER_END + "']")).findElements(By.xpath("../th")).size();
            Commons.findElementByExactName(driver, "View").click();
            Commons.sleepWait();
            Commons.findElementsByExactName(driver, "Add Columns").get(1).click();
            Commons.sleepWait(2000);
            // Add basic columns
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADD_COLUMNS_FIND_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADD_COLUMNS_FIND_END + "']")).sendKeys("Basic");
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADD_COLUMNS_MOVE_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(driver,"O").click();
            Commons.sleepWait(2000);
            after = driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.RESULTS_COLUMNS_FIRST_EMPTY_HEADER_END + "']")).findElements(By.xpath("../th")).size();
            Assert.assertNotEquals(before, after);
        }
        else if(type.contains("Change")) {
            before = 0;
            Commons.findElementByExactName(driver, "View").click();
            Commons.sleepWait();
            Commons.findElementByExactName(driver, "Show more columns").click();
            Commons.sleepWait(2000);
            // Add basic columns
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADD_COLUMNS_FIND_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADD_COLUMNS_FIND_END + "']")).sendKeys("General");
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + SearchManagePage.ADD_COLUMNS_MOVE_END + "::icon']")).click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(driver,"O").click();
            Commons.sleepWait(2000);
            after = driver.findElement(By.cssSelector(".x1hq.x1hy")).findElements(By.xpath("./../th")).size();
            Assert.assertNotEquals(before, after);
        }
    }
}
