package utils;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import stepDefinitions.CreateCOSteps;
import utils.objects.Change;
import utils.objects.Doc;
import utils.objects.Item;
import utils.objects.Type;
import webElements.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CreateChange {

    public static Commons commons = new Commons();
    public static WebDriver driver = Commons.driver;
    public static WebElement affTable; // = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFF_OBJ_TABLE_END +"']"));
    public static List<WebElement> rows; // = affTable.findElements(By.xpath("./table/tbody/tr"));

    public CreateChange(){

    }

    public static Change createChange(Change change){
        // needs cleaning once the create change dialog box is finalized
        Select select;
        try {
             Commons.findElementByAttributeValue("title", "Home").click();
//            Commons.home();
            Commons.sleepWait();

            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        }
        catch (ElementClickInterceptedException | NoSuchElementException e){
            driver.navigate().refresh();
            Commons.sleepWait();
            try{
                Commons.findElementByExactName("Yes").click();
            }
            catch (NoSuchElementException e1){

            }
            Commons.findElementByAttributeValue("title", "Home").click();
            Commons.sleepWait();

            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        }
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();
        Commons.sleepWait();
        if(change.type.equalsIgnoreCase("CO")){
            Commons.findElementByExactName("Create Change Order").click();
        }
        else if(change.type.equalsIgnoreCase("CR")){
            Commons.findElementByExactName("Create Change Request").click();
        }
        Commons.sleepWait(2000);
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CHANGE_TYPE_END +"']")));
        Commons.sleepWait(2000);
        select.selectByVisibleText(change.subType);
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.NAME_END +"']")).sendKeys(Commons.userRandomString("Name"));
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.DESC_END +"']")).sendKeys(Commons.userRandomString("Description"));
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+OverviewPage.PRIORITY_END +"']")));
        select.selectByIndex(0);
        if(change.subType.equalsIgnoreCase("Factory Implementation Tracker")){
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CHANGE_SAVE_AND_EDIT_END +"']")).click();
            Commons.sleepWait(5000);

            change.number = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.NUMBER_END +"']")).getText();
            System.out.println(change.number);
            return change;
        }
        else if(change.subType.equalsIgnoreCase("Admin Data Update")){
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CHANGE_SAVE_AND_EDIT_END +"']")).click();
            Commons.sleepWait(5000);

            change.number = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.NUMBER_END +"']")).getText();
            System.out.println(change.number);
            return change;
        }
        else if(!change.subType.equalsIgnoreCase("Standard Deviation")){
//            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.J_CODE_END + "']")));
        }else {
            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.D_TYPE_END + "']")));
        }
        Commons.sleepWait(2000);
//        select = new Select(driver.findElement(By.cssSelector("*[id$='"+OverviewPage.SEVERITY_END +"']")));
//        select.selectByVisibleText(change.severity);
        select = new Select(Commons.findElementByLabel("Priority Code"));
        select.selectByVisibleText(change.severity);
        // below logic needs cleaning once the business decides on the final parameters
        if(!change.subType.equalsIgnoreCase("Standard Deviation"))
        {
            if(change.type.equalsIgnoreCase("CO")) {
                select = new Select(driver.findElement(By.cssSelector("*[id*='" + OverviewPage.C_CODE_END + "']")));
                select.selectByVisibleText(change.categoryCode);
            }
            else{
                // nothing for now
            }
//            select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.J_CODE_END + "']")));
//            select.selectByVisibleText(change.justificationCode);
        }
        else{
            select = new Select(driver.findElement(By.cssSelector("*[id*='" + OverviewPage.C_CODE_3_END + "']")));
            select.selectByVisibleText(change.categoryCode);
            select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.D_TYPE_END + "']")));
            select.selectByVisibleText(change.deviationType);
        }
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CHANGE_SAVE_AND_EDIT_END +"']")).click();
        Commons.sleepWait(5000);

        change.number = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.NUMBER_END +"']")).getText();
        System.out.println(change.number);
        return change;
    }

    public static void addAffectedObjects(Change CO[], Item items[], Doc docs[]){

        // go to the first change order
        Commons.reachEditPage(CO[0].type, CO[0].number);
        // add items and docs to the affected object
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();
        for(Item item : items){
            addToChange(item.name);
        }
        for(Doc doc : docs){
            addToChange(doc.name);
        }
    }

    public static void addToChange(String name){
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_BOX_END +"']")).sendKeys(name);
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FIRST_ELEMENT_END +"']")).click();
    }

    public static void validateGeneralInformation(){
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.STATUS_END +"']")).getText().equalsIgnoreCase("Draft"));
//        Need to check why the signIn username is different from the 'Created By' and 'Requested by' names
//        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REQUESTED_BY_END +"']")).getDomAttribute("value").equalsIgnoreCase(Commons.userName));
//        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ASSIGNEE_ROLE_END +"']")).getDomAttribute("value").equalsIgnoreCase("Rezi PLM Change Analyst"));
//        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ASSIGNED_TO_END +"']")).getDomAttribute("value").contains("Gamma Automation"));

        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SEC_GI_END +"']")).getText().equalsIgnoreCase("General Information"));
        Assert.assertTrue(driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.PAGE_SECTIONS_HEADING_END +"']")).get(0).getText().contains("Change Attributes"));
        Assert.assertTrue(driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.PAGE_SECTIONS_HEADING_END +"']")).get(1).getText().contains("Design Team"));
        Assert.assertTrue(driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.PAGE_SECTIONS_HEADING_END +"']")).get(2).getText().contains("Impacted Manufacturing Sites"));
        if(CreateCOSteps.type.equalsIgnoreCase("CO")) {
            Assert.assertTrue(driver.findElements(By.cssSelector("*[id$='" + EditChangePage.PAGE_SECTIONS_HEADING_END + "']")).get(3).getText().contains("Change Implementation Details"));
        }
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.NAME_END +"']")), "random", "value", "title" );
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.DESCRIPTION_END +"']")), "random", "value", "title" );
        Commons.selectDifferentOption(new Select(Commons.findElementByLabel("** Reason Code")));
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REQUESTED_BY_END +"']")), "Gamma Automation", "value", "title" );

    }

    public static void validateChangeAttributes(){
        // ensure list values are correct
        // ensure the list values can be changed

        Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SEVERITY_END +"']")), "Severity");
        Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.C_CODE_END +"']")), "Category Code");
        Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.CUS_IMP_END +"']")), "Customer Impact");
        Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SEVERITY_END +"']")), "", "", "");
        Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.C_CODE_END +"']")), "", "", "");
        Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.CUS_IMP_END +"']")), "", "", "");
        if(!CreateCOSteps.subType.equalsIgnoreCase("Standard Deviation")){
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.J_CODE_END + "']")), "Justification Code");
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REG_IMP_END +"']")), "Regulatory Impact");
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_REQ_END +"']")), "Testing Required?");
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.J_CODE_END +"']")), "", "", "");
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REG_IMP_END +"']")), "", "", "");
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_REQ_END +"']")), "", "", "");

            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.APN_END +"']")), "number", null, "title");
        }
        else {
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.D_TYPE_END + "']")), "Deviation Type");
        }

        if(CreateCOSteps.type.equalsIgnoreCase("CO")){
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FAST_TRACK_END +"']")), "Fast Track");
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_RES_ATT_END +"']")), "Test Results Attached");
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FAST_TRACK_END +"']")), "", "", "");
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_RES_ATT_END +"']")), "", "", "");

            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.CJ_END +"']")), "text", null, "title");
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.RID_END +"']")), "date future", null, "title");
        }

        if(CreateCOSteps.subType.equalsIgnoreCase("Standard Change Request")){
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.R_TYPE_END +"']")), "Request Type");
            Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REC_EFF_END +"']")), "Recommended Effectivity");
        }

        // assert modifiable for all the above select
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SC_END +"']")), "text", null, "title");
    }

    public static void validateDesignTeam(){
        WebElement section = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.DT_SECTIONS_END +"']"));
        // add rows
        section.findElement(By.cssSelector("*[id$='"+ EditChangePage.ADD_DT_IMS_END +"']")).click();
        Commons.sleepWait(2000);
        String DTName = Commons.userRandomString("part");
        section.findElement(By.cssSelector("*[id$='"+ EditChangePage.NAME_DESIGN_TEAM_END +"']")).sendKeys(DTName + Keys.TAB);
        Commons.sleepWait(2000);
        Assert.assertEquals(DTName, driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.NAME_DESIGN_TEAM_END +"']")).getDomAttribute("title"));

        // delete rows
        section.findElement(By.cssSelector("*[id$='"+ EditChangePage.DEL_DT_IMS_END +"']")).click();
        Commons.sleepWait();
        Commons.assertElementNotPresent(EditChangePage.NAME_DESIGN_TEAM_END);
        Commons.sleepWait();
    }

    public static void validateIMS(){
        WebElement section = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.IMS_SECTIONS_END +"']"));
        // add rows
        section.findElement(By.cssSelector("*[id$='"+ EditChangePage.ADD_DT_IMS_END +"']")).click();
        Commons.sleepWait(2000);
        Select select = new Select(section.findElement(By.cssSelector("*[id$='"+ EditChangePage.IMS_DD_END +"']")));
        String selectedValue = Commons.selectDifferentOption(select);
        Commons.sleepWait(2000);
        Assert.assertEquals(selectedValue, select.getFirstSelectedOption().getText());

        // delete rows
        section.findElement(By.cssSelector("*[id$='"+ EditChangePage.DEL_DT_IMS_END +"']")).click();
        Commons.sleepWait();
        Commons.assertElementNotPresent(EditChangePage.IMS_DD_END);
    }

    public static void validateCID(){
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.PID_END +"']")), "date future", null, "title");

        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ESV_END +"']")), "number", null, "value");
    }

    public static void validateAffectedObjects(Change CO[], Item items[], Doc docs[]){

        affTable = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFF_OBJ_TABLE_END +"']"));
        rows = affTable.findElements(By.xpath("./table/tbody/tr"));
        // check if all items and docs are added
        Assert.assertEquals(items.length + docs.length, rows.size());

        for(WebElement element : rows){
            verifyRowValues(element);
        }
    }

    public static void removeObject(Item items[], Doc docs[]) {
        rows.get(0).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REMOVE_END +"']"));
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REMOVE_OK +"']"));
        rows = affTable.findElements(By.xpath("./table/tbody/tr"));
        Assert.assertEquals(items.length + docs.length - 1, rows.size());
    }

    public static void verifyRowValues(WebElement element){

        String _afrrk = element.getDomAttribute("_afrrk");
        element.click();Commons.sleepWait(4000);
        element = driver.findElement(By.xpath("//*[@_afrrk='" + _afrrk + "']"));
        String rowType = element.findElement(By.cssSelector("*[id$=':ik3']")).getDomAttribute("title");

        Commons.sleepWait(2000);
        if(!CreateCOSteps.subType.equalsIgnoreCase("No Revision Update")) {
            // new revision has default value of 01 and is editable
            String newRevision = element.findElement(By.cssSelector("*[id$='" + EditChangePage.NEW_REVISION_END + "']")).getText();
            if (newRevision.equalsIgnoreCase("")) {
                newRevision = element.findElement(By.cssSelector("*[id$='" + EditChangePage.NEW_REVISION_END + "']")).getDomAttribute("value");
            }
            Assert.assertEquals("01", newRevision);
        }
        Select select;
        // new lifecycle phase is preliminary and is editable and list contains all values (different list for items and docs)
        select = new Select(element.findElement(By.cssSelector("*[id$='"+ EditChangePage.NLP_END +"']")));
        Assert.assertEquals("Preliminary", select.getFirstSelectedOption().getText());
        Commons.assertModifiable("dropdown", element.findElement(By.cssSelector("*[id$='"+ EditChangePage.NLP_END +"']")), "", "",  "");
        List pageList = commons.getVisibletextFromDropDown(select);
        List excelList = commons.readFromExcel("DropDowns","DropDowns", rowType + " New Lifecycle Phase", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));
        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();

        Commons.sleepWait(6000);
        // handle stale element reference
        // can modify effective date to future
        element = driver.findElement(By.xpath("//*[@_afrrk='" + _afrrk + "']"));
        Commons.sleepWait(2000);
        Commons.assertModifiable("text", element.findElement(By.cssSelector("*[id$='"+ EditChangePage.EFF_DATE_END +"']")), "date time future", "value", "title");

        additionalInformationModifiable(element);
        // can modify open, purchase order, open work order, in stock, field return


        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
        Commons.sleepWait(4000);
    }

    public static void additionalInformationModifiable(WebElement element){

        List pageList;
        List excelList;
        WebElement OPO, OWO, IS, FR;
        OPO = element.findElement(By.cssSelector("*[id$='"+ EditChangePage.getOPO(CreateCOSteps.type) +"']"));
        OWO = element.findElement(By.cssSelector("*[id$='"+ EditChangePage.getOWO(CreateCOSteps.type) +"']"));
        IS = element.findElement(By.cssSelector("*[id$='"+ EditChangePage.getIS(CreateCOSteps.type) +"']"));
        FR = element.findElement(By.cssSelector("*[id$='"+ EditChangePage.getFR(CreateCOSteps.type) +"']"));
        Commons.scrollToView(FR);

        Commons.assertModifiable("dropdown", OPO, "", "",  "");
        Select select = new Select(OPO);
        pageList = commons.getVisibletextFromDropDown(select);
        excelList = commons.readFromExcel("DropDowns","DropDowns", "Open Purchase Orders", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

        Commons.assertModifiable("dropdown", OWO, "", "",  "");
        select = new Select(OWO);
        pageList = commons.getVisibletextFromDropDown(select);
        excelList = commons.readFromExcel("DropDowns","DropDowns", "Open Work Orders", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

        Commons.assertModifiable("dropdown", IS, "", "",  "");
        select = new Select(IS);
        pageList = commons.getVisibletextFromDropDown(select);
        excelList = commons.readFromExcel("DropDowns","DropDowns", "In Stock", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

        Commons.assertModifiable("dropdown", FR, "", "",  "");
        select = new Select(FR);
        pageList = commons.getVisibletextFromDropDown(select);
        excelList = commons.readFromExcel("DropDowns","DropDowns", "Field Return", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

    }

    public static void validateStructureOfAffectedObject(Item[] items, Doc[] docs) {

        WebElement row;
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFF_OBJ_STRUCTURE_TAB_END +"']")).click();

        addObjectToStructure(items[0]);
//        addObjectToStructure(items[0]);

        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SAVE_END +"']")).click();

        // check modify quantities, ref_des,
        row = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_TABLE_END +"']")).findElement(By.xpath("./table/tbody/tr[0]"));
        row.click();
        // assert for
        Commons.scrollToView(row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_COMMENTS_END + "']")));
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_QUANTITY_END + "']")), "number", "value", "title");
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_REF_DES_END + "']")), "number", null, "title");
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_IS_END + "']")), "number", "value", "title");
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_COMMENTS_END + "']")), "number", null, "title");
//        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_PS_END + "']")), "number", "value", "title");
        Commons.scrollToView(row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_SOS_END + "']")));
        Commons.assertModifiable("dropdown", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_EIR_END + "']")), "", "", "");
        Commons.assertModifiable("dropdown", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_BASIS_END + "']")), "", "", "");
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_PP_END + "']")), "number", "value", "title");
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_YIELD_END + "']")), "number", "value", "title");
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_FN_END + "']")), "number", "value", "title");
        Commons.assertModifiable("text", row.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_SOS_END + "']")), "number", null, "title");

        row.findElement(By.cssSelector("*[id$='"+ EditChangePage.SAVE_END +"']"));


        // remove one of the object
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_TABLE_END +"']")).findElement(By.xpath("./table/tbody/tr[1]")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_REMOVE_END +"']")).click();

        // Assert the red, green and orange
        Set<String> expected = new TreeSet<>();
        Set<String> actual = new TreeSet<>();

        expected.add("Addition_background-color:rgb(0,94,0); padding:6.0px;");
        expected.add("Modification_background-color:rgb(196,86,14); padding:6.0px;");
        expected.add("Remove_background-color:rgb(168,0,0); padding:6.0px;");

        WebElement element;
        List<WebElement> rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        for(WebElement singleRow : rows){
            try{
                element = singleRow.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_CO_ADD_END +"']"));
                actual.add(element.getDomAttribute("title") + "_" + element.getDomAttribute("style"));
            } catch (Exception e1) {
                try {
                    element = singleRow.findElement(By.cssSelector("*[id$='" + EditChangePage.AO_ST_CO_EDIT_END + "']"));
                    actual.add(element.getDomAttribute("title") + "_" + element.getDomAttribute("style"));
                } catch (Exception e2){
                    try {
                        element = singleRow.findElement(By.cssSelector("*[id$='" + EditChangePage.AO_ST_CO_REMOVE_END + "']"));
                        actual.add(element.getDomAttribute("title") + "_" + element.getDomAttribute("style"));
                    } catch (Exception e3){
                        Assert.fail("Image OR colour of may have been changed");
                    }
                }
            }
        }

        Assert.assertEquals(expected, actual);
    }

    public static void addObjectToStructure(Type type){

        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_CREATE_AND_ADD_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_CLASS_END +"']")).sendKeys(type.className);
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_NAME_END +"']")).sendKeys(type.name);
//        names should be autogenerated. Could be a bug??
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_DESC_END +"']")).sendKeys(Commons.userRandomString("description"));
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_ST_SAVE_AND_CLOSE_END +"']")).click();

        // driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SAVE_END +"']")).click();
    }

    // check the deleting and addition of files is possible
    public static void validateAttachmentOfAffectedObjects(){
        List<WebElement> rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        int before = rows.size();
        int after;
        String color;
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFF_OBJ_ATTACHMENT_TAB_END +"']")).click();
        rows.get(0).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_DELETE_ICON_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_DELETE_OK_END +"']")).click();
        color = rows.get(0).findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_FILENAME_END +"']")).getCssValue("color");
        Assert.assertTrue(color.contains("Red"));
        rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        after = rows.size();
        Assert.assertEquals(before-1, after);

        before = after;
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_ADD_ICON_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_CHOOSE_FILES_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/FileDocument.txt");
        color = rows.get(0).findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_FILENAME_END +"']")).getCssValue("color");
        Assert.assertTrue(color.contains("Green"));
        rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        after = rows.size();
        Assert.assertEquals(before+1, after);
    }

    public static void validateRedLineSummary(){
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SAVE_AND_CLOSE_END +"']")).click();

        rows.get(0).findElement(By.cssSelector("*[id$='"+ EditChangePage.RED_DOT_END +"']")).click();
        Commons.takeFullScreenshot();
        rows.get(0).findElement(By.cssSelector("*[id$='"+ EditChangePage.ADDNL_INFO_END +"']")).click();
        Commons.takeFullScreenshot();
        rows.get(0).findElement(By.cssSelector("*[id$='"+ EditChangePage.SUMMARY_CHANGES_CLOSE_END +"']")).click();
    }

    public static void validateTasks(Change[] co, Item[] items, Doc[] docs) {
        int before = driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TABLE_END +"']" + AffectedObjectsTab.TABLE_ROWS_END)).size();
        WebElement row;
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TAB_END +"']")).click();

        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_ADD_END +"']")).click();
        Commons.sleepWait(2000);

        row = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TABLE_END +"']" + AffectedObjectsTab.TABLE_ROWS_END));

        // check sequence is auto generated
        WebElement element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_SEQUENCE_END +"']"));
        Assert.assertTrue(element.getDomAttribute("value")!=null);
        // check if some fields are editable
        Commons.assertModifiable("text", element, "number", "value", "title");

        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_NAME_END +"']"));
        Commons.assertModifiable("text", element, "part", "value", "title");

        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_DESCRIPTION_END +"']"));;
        Commons.assertModifiable("text", element, Commons.userRandomString(""), "value", "title");

        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_REQUIRED_END +"']"));;
        Commons.assertModifiable("checkbox", element, "checkbox", "value", "title");
        element.click();

//        May have to change it to the Commons.username
        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_AT_END +"']"));;
        Commons.assertModifiable("text", element, "delta Automation", "value", "title");
        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_SBS_END +"']"));;
        Commons.assertModifiable("dropdown", element, "number", "value", "title");
        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_CBS_END +"']"));;
        Commons.assertModifiable("dropdown", element, "number", "value", "title");
        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_NBD_END +"']"));
        Commons.assertModifiable("text", element, "date time future", "value", "title");
        // commented before
        Commons.sleepWait();
        Commons.findElementByExactName("Save").click();
        Commons.sleepWait(5000);
        row = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TABLE_END +"']" + AffectedObjectsTab.TABLE_ROWS_END));
        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TS_END +"']"));
        Commons.assertModifiable("dropdown", element, "number", "value", "title");
        Commons.sleepWait();
        row = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TABLE_END +"']" + AffectedObjectsTab.TABLE_ROWS_END));
        element = row.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TS_END +"']"));
        Select select = new Select(element);
        select.selectByVisibleText("Open");
        Commons.sleepWait();

        // check if the deleting a row works
        //commented before
        List<WebElement> rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        row.click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_DELETE_END +"']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_DELETE_YES_END +"']")).click();
        Commons.sleepWait(2000);

        Assert.assertEquals(before, driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TABLE_END +"']" + AffectedObjectsTab.TABLE_ROWS_END)).size());
    }

    public static void validateAttachments(Change[] co, Item[] items, Doc[] docs){
        // Add attachments
        int before = 0;
        int after;
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_ADD_ICON_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_CHOOSE_FILES_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/FileDocument.txt");
        Commons.sleepWait(2000);
        rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        after = rows.size();
        Assert.assertEquals(before+1, after);

        // Delete attachments
        Commons.findElementByExactName(driver,"FileDocument.txt").click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_DELETE_ICON_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_DELETE_OK_END +"']")).click();
        Commons.sleepWait(2000);
        // List<WebElement> rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        before = after;
        rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AO_AT_TABLE_END +"']")).findElements(By.xpath("./table/tbody/tr"));
        after = rows.size();
        Assert.assertEquals(before-1, after);
    }

    public static void validateWorkflow(Change[] co, Item[] items, Doc[] docs){
        // check the status to be in draft
        // check the change options to be available just 2 (prepare,  cancel)
        // cannot add participants in draft status

        // click on prepare
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers

        // click on audit
        // Rezi PLM change analystis a default approver and cannot be removed
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers
        // cannot add alpha as approvers
        // can add beta, charlie, delta, gamma, echo as approvers
        // can add beta, charlie, delta, gamma, echo as optional approvers

        // click on Rework
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers
        // cannot add alpha as approvers
        // can add beta, charlie, delta, gamma, echo as approvers
        // can add beta, charlie, delta, gamma, echo as optional approvers

        // click on Confirm
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers
        // cannot add alpha as approvers
        // can add beta, charlie, delta, gamma, echo as approvers
        // can add beta, charlie, delta, gamma, echo as optional approvers

        // click on update Documentation
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers
        // cannot add alpha as approvers
        // can add beta, charlie, delta, gamma, echo as approvers
        // can add beta, charlie, delta, gamma, echo as optional approvers

        // click on Approve Documentation
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers
        // cannot add alpha as approvers
        // can add beta, charlie, delta, gamma, echo as approvers
        // can add beta, charlie, delta, gamma, echo as optional approvers

        // click on Ready to Release
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers
        // cannot add alpha as approvers
        // can add beta, charlie, delta, gamma, echo as approvers
        // can add beta, charlie, delta, gamma, echo as optional approvers

        // click on Scheduled
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers

        // click on Completed
        // can add alpha, beta, charlie, delta, gamma, echo as reviewers

    }

    public static void validateStatusChange(Change[] CO, Item[] items, Doc[] docs){
        // change the status from draft to Prepare
        // check dialog box to appear, add some reviewers, add some comments -> save
        // reviewer should be found in participant list
        // comments are captured in workflow summary
        // check the change options to be available just 8 (Audit, Rework, Confirm, Update Documentation, Approve Doc, Ready to release, hold , cancel)

        // change the status from Prepare to audit
        // check dialog box to appear, add some reviewers, add some comments -> save
        // reviewer should be found in participant list
        // comments are captured in workflow summary
        // check the change options to be available just 8 (Audit, Rework, Confirm, Update Documentation, Approve Doc, Ready to release, hold , cancel)
        // check ability to modify below parameters in audit state
        // relationship can be modified
        // cover page attributes cannot be modified
        // affected objects and redlines cannot be modified
        // tasks cannot be modified (add remove update not possible)
        // change attachments cannot be modified
        // participants cannot be modified
        // status cannot be modified

        // change the status from Audit to confirm only by gamma user (may need to have another driver window for the gamma user sign-in)
        // check dialog box to appear, add some reviewers, add some comments -> save
        // reviewer should be found in participant list
        // comments are captured in workflow summary
        // check the change options to be available just 8 (Audit, Rework, Confirm, Update Documentation, Approve Doc, Ready to release, hold , cancel)
        // check ability to modify below parameters in Confirm state
        // can modify tasks -> add and update
        // cannot delete tasks
        // change attachments
        // add participants
        // cannot remove participants
        // can modify relationships
        // cannot change cover page attributes
        // cannot change affected objects and redlines
        // cannot change status

        // change the status from Confirm to Update Documentation only by gamma user (may need to have another driver window for the gamma user sign-in)
        // check dialog box to appear, add some reviewers, add some comments -> save
        // reviewer should be found in participant list
        // comments are captured in workflow summary
        // check the change options to be available just 8 (Audit, Rework, Confirm, Update Documentation, Approve Doc, Ready to release, hold , cancel)
        // check ability to modify below parameters in 'Update Documentation' state
        // can modify tasks -> add and update
        // cannot delete tasks
        // change attachments
        // add participants
        // cannot remove participants
        // can modify relationships
        // cannot change cover page attributes
        // cannot change affected objects and redlines
        // cannot change status

        // change the status from Update Documentation to Approve Documentation only by gamma user (may need to have another driver window for the gamma user sign-in)
        // check dialog box to appear, add some reviewers, add some comments -> save
        // reviewer should be found in participant list
        // comments are captured in workflow summary
        // check the change options to be available just 8 (Audit, Rework, Confirm, Update Documentation, Approve Doc, Ready to release, hold , cancel)
        // check ability to modify below parameters in 'Update Documentation' state
        // can modify tasks -> add and update
        // cannot delete tasks
        // cannot change attachments
        // add participants
        // cannot remove participants
        // can modify relationships
        // cannot change cover page attributes
        // cannot change affected objects and redlines
        // cannot change status

        // change the status from 'Approve Documentation' to 'Ready to Release' only by gamma user (may need to have another driver window for the gamma user sign-in)
        // check dialog box to appear, add some reviewers, add some comments -> save
        // reviewer should be found in participant list
        // comments are captured in workflow summary
        // check the change options to be available just 8 (Audit, Rework, Confirm, Update Documentation, Approve Doc, Ready to release, hold , cancel)
        // check ability to modify below parameters in 'Update Documentation' state
        // can modify tasks -> add and update
        // cannot delete tasks
        // cannot change attachments
        // add participants
        // cannot remove participants
        // can modify relationships
        // cannot change cover page attributes
        // cannot change affected objects and redlines
        // cannot change status

        // All effected dates of the affected objects are in the past
        // Change the status from 'Ready to Release' to 'scheduled' only by gamma user (may need to have another driver window for the gamma user sign-in and approval)
        // The status will change

        // change the status from 'Ready to Release' to 'scheduled' only by gamma user (may need to have another driver window for the gamma user sign-in and approval)
        // check dialog box to appear, add some reviewers, add some comments -> save
        // reviewer should be found in participant list
        // comments are captured in workflow summary
        // check the change options to be available just 8 (Audit, Rework, Confirm, Update Documentation, Approve Doc, Ready to release, hold , cancel)
        // check ability to modify below parameters in 'Update Documentation' state
        // can modify tasks -> add and update
        // cannot delete tasks
        // cannot change attachments
        // add participants
        // cannot remove participants
        // can modify relationships
        // cannot change cover page attributes
        // cannot change affected objects and redlines
        // cannot change status

        // Change status from scheduled to Completed (mostly automatic)
        // No change is possible

        // Create new CO or use one from array
        // cancel CO
        // verify Change order is cancelled

    }

    public static void validateRelationships(Change[] co, Item[] items, Doc[] docs){

        System.out.println("Reached the relationship tab");
        // add co[1] under co[0]
        // goto select and add and put in the co[1] number and then select the first from dropdown
        // verify if the CO is added by checking the number of rows

        // change the type of the relationship

        // add rule to trigger a certain workflow status in one CO when changes are made in other change order

        // Remove a rule

        // remove a relationship

    }

    public static void validateSaveAs(Change[] co, Item[] items, Doc[] docs){

        // Save as and include nothing -> warning that some of the objects are already included should be shown
        // verify the above save as

        // Save as and include Additional information
        // verify the above save as

        // Save as and include Attachments
        // verify the above save as

        // Save as and include Relationships
        // verify the above save as

        // Save as and include Tasks
        // verify the above save as

        // Save as and include Affected objects
        // verify the above save as

        // Save as and include Descriptive flexfields
        // verify the above save as
    }

    // the control will have to be present in the general information tab of the Change for this to work
    public static void editDoubleStar() {

        // fill required fields
        if(CreateCOSteps.type.equalsIgnoreCase("CO")) {
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.FAST_TRACK_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.REG_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CUS_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TEST_REQ_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TEST_RES_ATT_END + "']"))));
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APN_END + "']")).sendKeys("23235");
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.SC_END + "']")).sendKeys("Automation test");
        }
        else if (CreateCOSteps.type.equalsIgnoreCase("CR") && CreateCOSteps.subType.equalsIgnoreCase("Standard Change Request")) {
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.REG_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CUS_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TEST_REQ_END + "']"))));
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APN_END + "']")).sendKeys("23235");
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.SC_END + "']")).sendKeys("Automation test");
        }
        else if (CreateCOSteps.type.equalsIgnoreCase("CR") && CreateCOSteps.subType.equalsIgnoreCase("Standard Deviation")) {
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.REG_APP_REQ_END + "']"))));
            //Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CUS_IMP_END + "']"))));
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.C_ACT_END + "']")).sendKeys("Automation test");
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.P_ACT_END + "']")).sendKeys("Automation test");
            Commons.findInputElementByParameterName("**Effective From").sendKeys("06/23/23");
            Commons.findInputElementByParameterName("**Effective To").sendKeys("06/23/26");
        }

        Commons.selectDifferentOption(new Select(Commons.findInputElementByParameterName("** Reason Code")));
    }

    // the control will have to be present in the general information tab of the Change for this to work
    public static void editDoubleStar(String type) {
        // fill required fields
        if(type.equalsIgnoreCase("SCO") || type.equalsIgnoreCase("NRU")) {
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.FAST_TRACK_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.REG_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CUS_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TEST_REQ_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TEST_RES_ATT_END + "']"))));
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APN_END + "']")).sendKeys("23235");
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.SC_END + "']")).sendKeys("Automation test");
        }
        else if (type.equalsIgnoreCase("SCR")) {
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.REG_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CUS_IMP_END + "']"))));
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TEST_REQ_END + "']"))));
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APN_END + "']")).sendKeys("23235");
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.SC_END + "']")).sendKeys("Automation test");
        }
        else if (type.equalsIgnoreCase("SDV")) {
            Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.REG_APP_REQ_END + "']"))));
            //Commons.selectDifferentOption(new Select(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CUS_IMP_END + "']"))));
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.C_ACT_END + "']")).sendKeys("Automation test");
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.P_ACT_END + "']")).sendKeys("Automation test");
            Commons.findInputElementByParameterName("** Effective From").sendKeys("06/23/23");
            Commons.findInputElementByParameterName("** Effective To").sendKeys("06/23/26");
        }
    }

    public static void changeStage(String stage) {
        Commons.sleepWait(2000);
        if(!stage.equalsIgnoreCase("Continue")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
            Commons.sleepWait(4000);
        }
        if(stage.equalsIgnoreCase("Ready To Release")) {
            // change something in the affected object for the state to change to ready to release
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.AFFECTED_OBJECTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.selectDifferentOption(new Select(Commons.findElementByLabel("LifecyclePhase")));
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
            Commons.sleepWait(2000);
        }

// Click on the dropdown matching the stage name. In case any other element has same name, then try again.
        for(WebElement item : Commons.findElementsByExactName(driver, stage)){
            try{
                Commons.findElementByExactName(Commons.driver, "Change Status").click();
                Commons.sleepWait(2000);
                item.click();
                Commons.sleepWait();
                if(Commons.findElementByExactName("Activity Type").getText().equalsIgnoreCase("Activity Type")){
                    break;
                }
            }
            catch(Exception ignored){
                new Actions(driver).sendKeys(Keys.ESCAPE).build().perform();
            }
        }

        Commons.sleepWait(2000);
        if(stage.equalsIgnoreCase("Audit")){
            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_SUBMIT_END + "']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
            return;
        }
        else if(stage.equalsIgnoreCase("Hold") || stage.equalsIgnoreCase("Continue")){
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.SAVE_AND_CLOSE_END + "']")).click();
            try{
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + WorkflowTab.ALPHA_APPROVER_ERROR_POPUP_OK_END + "']")).click();
                Commons.sleepWait(10000);
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.SAVE_AND_CLOSE_END + "']")).click();
            }
            catch (Exception ignore){

            }
            return;
        }
        // Commons.findElementByName(popUp1, "Actions");
        // add persons icon
        WorkflowTab.addParticipants("SV_Gamma_Automation");
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
    }

    public static void approveStageChange(WebDriver driver){
        int i=0;
        while(i<23){
            try{
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(2000);
                if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                    driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                    Commons.sleepWait(2000);
                    break;
                }
            }
            catch(Exception e){
                System.out.println("No Element or Stale element found during refresh, so we have to retry");
            }
            Commons.sleepWait();
            i++;
        }

        // approve as rezi group
        driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
        driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
        Commons.sleepWait(5000);

    }

    public static void reachWorkflowStage(String changeType, String subtype, String stage){
        if(stage.equalsIgnoreCase("Draft")){
            return;
        }
        Type.reachTab("General Information");
        CreateChange.editDoubleStar();
        CreateChange.changeStage("Prepare");
        if(stage.equalsIgnoreCase("Prepare")){
            return;
        }

        // create an item
        Item affectedItem = CreateItems.createItem(Commons.driver, new Item());
        // add attachments
        driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ATTACHMENTS_TAB_END +"']")).click();
        AttachmentsTab.addNecessaryAttachments("Zip");
        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_AND_CLOSE_END +"']")).click();
        // add item to affected object
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();
        AffectedObjectsTab.addObject(affectedItem);
        Commons.sleepWait(3000);
        // add attachment to change
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ATTACHMENTS_TAB_END +"']")).click();
        AttachmentsTab.addNecessaryAttachments("Zip");

        // assign the Change to delta
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ASSIGNED_TO_END +"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ASSIGNED_TO_END +"']")).sendKeys("Gamma Automation");
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
        Commons.sleepWait(2000);

        if(!stage.equalsIgnoreCase("Completed") && !stage.equalsIgnoreCase("Scheduled")) {
            CreateChange.changeStage(stage);
        }
    }

    public static String getCurrentStage(){
        List<WebElement> elements = Commons.driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_ACTIONS_ROW_END + "'] > table > tbody > tr > td"));
        elements.get(3).getText().equalsIgnoreCase("Approve");
        if(elements.get(2).getText().length() > 1){
            return elements.get(2).getText();
        }else{
            return elements.get(3).getText();
        }
    }
}