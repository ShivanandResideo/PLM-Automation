package webElements;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.CreateChange;
import utils.objects.Doc;
import utils.objects.Item;
import utils.objects.Type;

import java.util.List;

import static utils.Commons.driver;
import static webElements.CommonElements.SAVE;

public class AffectedObjectsTab {

    public static final String AFFECTED_OBJECTS_TAB_END = ":AP2:sdi5::icon";
    public static final String VIEW_REDLINE_SUMMARY_END = ":AP2:r5:1:ctb1::icon";

    public static final String NAME_LINK_END = ":cl1";
    public static final String STRUCTURE_REMOVE_END = ":AT1:_ATp:cil007::icon";
    public static final String STRUCTURE_REPLACE_END = ":AT1:_ATp:cil2::icon";
    public static final String SELECT_AND_ADD_END = ":pc1:cil1::icon";
    public static final String SELECT_AND_ADD_BOX_END = ":s1:ip1:lovTxtId::content";
    public static final String SELECT_AND_ADD_FIRST_DD_END = ":s1:ip1:lovTxtId::_fndSuggestPopup_sugg_Items_0";

    public static final String REMOVE_END = ":pc1:cil2::icon";
    public static final String REMOVE_OK_END = ":pc1:cb5";

    public static final String TABLE_END = ":pc1:ATt1::db";    // gets the whole table
    public static final String TABLE_AT_END = ":pc1:t1::db";
    public static final String TABLE_ROWS_END = "> table > tbody > tr"; // will get all the rows in the table
    public static final String OBJECT_ICON_END = "ik4"; // id was ik3 but now changed to ik4
    public static final String OLD_REVISION_END = ":fromRevision::content";
    public static final String NEW_REVISION_END = ":it2::content";
    public static final String NEW_LIFECYCLE_PHASE_END = ":soc1::content";  // verify drop down
    public static final String EFFECTIVITY_DATE_END = ":of1::content";
    public static final String OPO_END = ":df1_ChangeLineDFFIteratoropenPurchaseOrders::content";
    public static final String OWO_END = ":df1_ChangeLineDFFIteratoropenWorkOrders::content";
    public static final String IS_END = ":df1_ChangeLineDFFIteratorinStock::content";
    public static final String FR_END = ":df1_ChangeLineDFFIteratorfieldReturn::content";
    public static final String SAVE_AND_CLOSE = ":AP2:APsv2";
    public static final String WARNING_OK_END = ":AP2:d1::ok";

    public static final String VIEW_COLUMNS_LIST_END = ":_clmns::ScrollBox";

    public static final String DETAILS_TAB_END = ":AP1:sdi1::icon";
    public static final String DETAILS_NAME_END = ":AP1:r1:0:plam1";
    public static final String DETAILS_CLASS_END = ":AP1:r1:0:plam3";
    public static final String DETAILS_DESC_END = ":it1::content";
    public static final String DETAILS_LP_END = ":soc3::content";
    public static final String DETAILS_DA_END = ":designAuthority::content";
    public static final String DETAILS_PN_END = ":projectNumber::content";
    public static final String DETAILS_SPLM_END = ":sourcePlm::content";
    public static final String DETAILS_TYPE_END = ":specificationType::content";
    public static final String DETAILS_AI_END = ":additionalInformation::content";
    public static final String DETAILS_EA_END = ":enoviaAttributes::content";
    public static final String STRUCTURE_TAB_END = ":AP1:sdi2::icon";
    public static final String STRUCTURE_TABLE_END = ":AT1:_ATp:ATt1::db";
    public static final String STRUCTURE_QUANTITY_END = ":it25::content";
    public static final String STRUCTURE_REF_DES_END = ":it1_new::content";
    public static final String STRUCTURE_ITEM_NUM_END = ":cl1";
    public static final String TOP_GREEN_END = ":AT1:_ATp:ATt1:3:ik49";
    public static final String MID_RED_END = ":AT1:_ATp:ATt1:0:ik39";
    public static final String LAST_ORANGE_END = ":AT1:_ATp:ATt1:1:ik29";
    public static final String REPLACE_GREEN_END = ":AT1:_ATp:ATt1:4:ik50";

    private static final String STRUCTURE_CREATE_END = ":AT1:_ATp:cil5::icon";
    private static final String STRUCTURE_DD_FIRST_PRODUCT_END = ":s2:iclov1::su0";
    private static final String STRUCTURE_CREATE_CLASS_END = ":s2:iclov1::content";
    private static final String STRUCTURE_CREATE_LOB_END = ":f1_businessLineItemEffBaseEO1_ATTRIBUTE_CHAR2::content";
    private static final String STRUCTURE_CREATE_DA_END = ":f1_designAuthorityItemEffBaseEO1_ATTRIBUTE_CHAR3::content";
    private static final String STRUCTURE_CREATE_COTS_END = ":f1_customCotsItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
    private static final String STRUCTURE_CREATE_SAVE_CLOSE_END = ":s2:cb4";

    private static final String STRUCTURE_SAVE_END = ":AP1:APsv2";
    private static final String STRUCTURE_SAVE_CLOSE_END = ":AP1:APscl2";
    private static final String STRUCTURE_CANCEL_END = ":AP1:SPc";

    public static final String EXPORT_TO_EXCEL_END = ":AT2:_ATp:ATex::icon";
    public static final String FILTER_END = ":AT2:_ATp:_qbeTbr::icon";
    public static final String DETACH_END = ":AT2:_ATp:_dchTbr::icon";

    public static final String AML_TAB_END = ":AP1:sdi4::icon";

    public static final String ATTACHMENTS_TAB_END = ":AP1:sdi3::icon";

    public static Select select;

    public static void addObjects(List<Type> AO) {
        driver.findElement(By.cssSelector("*[id$='"+ AFFECTED_OBJECTS_TAB_END +"']")).click();
        // if the table is not expanded we need to make sure it is open for addition
        try{
            driver.findElement(By.cssSelector("*[id$='"+ SELECT_AND_ADD_END +"']"));
        } catch (Exception e) {
            driver.findElement(By.cssSelector("*[id$='"+ VIEW_REDLINE_SUMMARY_END +"']")).click();
        }

        for (Type type : AO){
            addObject(type);
        }

        Commons.findElementByExactName("Save").click();
    }

    public static void addObject(Type type){
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ SELECT_AND_ADD_END +"']")).click();
        Commons.sleepWait(2000);
        if(type==null){
            driver.findElement(By.cssSelector("*[id$='"+ SELECT_AND_ADD_BOX_END +"']")).sendKeys("Automation");
            //  verify the dropdowns
        }
        else {
            driver.findElement(By.cssSelector("*[id$='" + SELECT_AND_ADD_BOX_END + "']")).sendKeys(type.name);
        }
        Commons.sleepWait(4000);
        driver.findElement(By.cssSelector("*[id$='"+ SELECT_AND_ADD_FIRST_DD_END +"']")).click();
        Commons.sleepWait(2000);
        try{
            Commons.findElementByExactName("es").click(); // if the warning appears. Confirm Yes to add the item
        }
        catch (Exception ignore){

        }
    }

//    bad design. Need to separate adding object and removing objects as different methods
    public static void removeObject(Type type){

        int before, after;
        before = driver.findElements(By.cssSelector("*[id$='"+ TABLE_END + "']" + TABLE_ROWS_END )).size();
        addObject(type);

        if(type==null){
            try {
                CreateChange.additionalInformationModifiable(driver.findElement(By.cssSelector("*[id$='" + TABLE_END + "']" + TABLE_ROWS_END)));
            }
            catch(NoSuchElementException e){
                CreateChange.additionalInformationModifiable(driver.findElement(By.cssSelector("*[id$='" + TABLE_AT_END + "']" + TABLE_ROWS_END)));
            }
        }

        driver.findElement(By.cssSelector("*[id$='"+ REMOVE_END +"']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ REMOVE_OK_END +"']")).click();
        Commons.sleepWait(2000);
        after = driver.findElements(By.cssSelector("*[id$='"+ TABLE_END + "']" + TABLE_ROWS_END )).size();

        Assert.assertEquals(before, after);

    }

    public static void validateParameters(){
        List<WebElement> rows = driver.findElements(By.cssSelector("*[id$='"+ TABLE_END + "']" + TABLE_ROWS_END ));
        WebElement NLP = null;
        for(int i=0; i< rows.size(); i++){
            System.out.println("Iteration Number "+ i);
            WebElement row = rows.get(i);
            row.click();
            Commons.sleepWait(2000);
            String type;
            try{
                 type = row.findElement(By.cssSelector("*[id$='"+ OBJECT_ICON_END + "']")).getDomAttribute("title");
            } catch (StaleElementReferenceException e) {
                row = driver.findElements(By.cssSelector("*[id$='"+ TABLE_END + "']" + TABLE_ROWS_END )).get(i);
                row.click();
                Commons.sleepWait(2000);
                 type = row.findElement(By.cssSelector("*[id$='"+ OBJECT_ICON_END + "']")).getDomAttribute("title");
            }
            WebElement FR = row.findElement(By.cssSelector("*[id$='"+ FR_END + "']"));
            WebElement IS = row.findElement(By.cssSelector("*[id$='"+ IS_END + "']"));
            WebElement OWO = row.findElement(By.cssSelector("*[id$='"+ OWO_END + "']"));
            WebElement OPO = row.findElement(By.cssSelector("*[id$='"+ OPO_END + "']"));
            WebElement EDE = row.findElement(By.cssSelector("*[id$='"+ EFFECTIVITY_DATE_END + "']"));
            NLP = row.findElement(By.cssSelector("*[id$='"+ NEW_LIFECYCLE_PHASE_END + "']"));
            WebElement NR = row.findElement(By.cssSelector("*[id$='"+ NEW_REVISION_END + "']"));
            WebElement ORE = row.findElement(By.cssSelector("*[id$='"+ OLD_REVISION_END + "']"));
            Commons.scrollToView(ORE);

            select = new Select(ORE);
            int oldRevision = Integer.parseInt(select.getFirstSelectedOption().getText());
            int newRevision = Integer.parseInt(NR.getDomAttribute("value"));
            Assert.assertEquals(0, oldRevision);
            Assert.assertEquals(oldRevision+1, newRevision);
            Commons.assertModifiable("text", NR, "number", "value", "title");
            //Commons.driver.findElement(By.cssSelector("*[id$='"+ SAVE + "']")).click();

            // WebElement NLP = row.findElement(By.cssSelector("*[id$='"+ NEW_LIFECYCLE_PHASE_END + "']"));
            select = new Select(NLP);
            Assert.assertEquals("Preliminary", select.getFirstSelectedOption().getText());
            if(type.contains("Item")){
                Commons.compareDropDown(NLP, "Item New Lifecycle Phase");
            }
            else if(type.contains("Document")){
                Commons.compareDropDown(NLP, "Document New Lifecycle Phase");
            }

            Commons.scrollToView(EDE);
            Commons.assertModifiable("text", EDE, "date time future", "", "title");
            Commons.sleepWait(2000);
            Commons.scrollToView(OPO);
            Commons.compareDropDown(OPO, "Open Purchase Orders");
            Commons.assertModifiable("dropdown", OPO, "", "", "");
            Commons.sleepWait(2000);
            Commons.scrollToView(OWO);
            Commons.compareDropDown(OWO, "Open Work Orders");
            Commons.assertModifiable("dropdown", OWO, "", "", "");
            Commons.sleepWait(2000);
            Commons.scrollToView(IS);
            Commons.compareDropDown(IS, "In Stock");
            Commons.assertModifiable("dropdown", IS, "", "", "");
            Commons.sleepWait(2000);
            Commons.scrollToView(FR);
            Commons.compareDropDown(FR, "Field Return");
            Commons.assertModifiable("dropdown", FR, "", "", "");
            Commons.sleepWait(2000);
        }
        Commons.assertModifiable("dropdown", NLP, "", "", "");
        select.selectByVisibleText("Preliminary");
        driver.findElement(By.cssSelector("*[id$='"+ SAVE + "']")).click();
        Commons.sleepWait(2000);
        try{
            driver.findElement(By.cssSelector("*[id$='"+ WARNING_OK_END + "']")).click();
            Commons.sleepWait(2000);
        }
        catch(Exception ignored){

        }
    }

    public static void validateRedline(Item items, Doc docs){
        int docNum = 0, itemNum = 0;
        List<WebElement> rows = driver.findElements(By.cssSelector("*[id$='"+ TABLE_END + "']" + TABLE_ROWS_END ));
        // for the item type check the relevant sections are editable or not

        for(int i=0; i<rows.size(); i++){
            WebElement row = rows.get(i);
            String rowType;
            try{
                rowType = row.findElement(By.cssSelector("*[id$='"+ OBJECT_ICON_END + "']")).getDomAttribute("title");
            } catch (StaleElementReferenceException e) {
                row = driver.findElements(By.cssSelector("*[id$='"+ TABLE_END + "']" + TABLE_ROWS_END )).get(i);
                row.click();
                Commons.sleepWait(2000);
                rowType = row.findElement(By.cssSelector("*[id$='"+ OBJECT_ICON_END + "']")).getDomAttribute("title");
            }
            row.findElement(By.xpath("./td[4]")).click();
            Commons.sleepWait(3000);

            if(rowType.contains("Document")){
                Commons.driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.NEW_REVISION_END+ "']")).sendKeys(Keys.ESCAPE);
                Commons.sleepWait(2000);
                driver.findElement(By.xpath("//*[text()= '"+ docs.name +"']")).click();
                Commons.sleepWait(3000);

                verifyDetailsTab(); // completed
                verifyStructureTab(); // completed
                verifyAttachmentsTab(); // in progress


            }
            else if(rowType.contains("Item")){
                Commons.driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.NEW_REVISION_END+ "']")).sendKeys(Keys.ESCAPE);
                driver.findElement(By.xpath("//*[text()= '"+ items.name +"']")).click();

                Commons.sleepWait(3000);

                verifyDetailsTab(); // completed
                verifyStructureTab(); // in progress
                verifyAttachmentsTab(); // yet to start
                // verifyAMLTab(); // yet to start

                EditItemPage.generalInformation(driver, items);
                EditItemPage.commonPartAttributes(driver, items);
                EditItemPage.ERCVInformation(driver, items);
//              //  Class specific changes are to be added in later stages
//                EditItemPage.classSpecificInformation(driver, items[itemNum]);
                EditItemPage.ESGInformation(driver, items);
                EditItemPage.legacyAttributes(driver, items);
                EditItemPage.modify(driver, items);
            }
            driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SAVE_END +"']")).click();

//            maybe writing the modifiable checking code and saving together is a better option
//            Need to check
            // check for elements which are modified to have the redline for the before values (attribute "style" will have colour and line-through set

        }
    }

    private static void verifyAttachmentsTab() {
//        will have to code to remove already attached file and verify it as red. AttachmentsTab.verifyFilesAdded() function for navigating files or check line 313 for more information
        System.out.println("Check if code is working as expected from excel");
        driver.findElement(By.cssSelector("*[id$='" + ATTACHMENTS_TAB_END + "'")).click();
        AttachmentsTab.addNecessaryAttachments("File Document");
        AttachmentsTab.verifyFilesAdded("File Document");
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_COLUMN_END +"']")).getDomAttribute("style").contains("background-color:rgb(0,94,0)"));
        // if the above does nto work try with element.getCssValue("color")
    }

    public static void verifyStructureTab() {
        Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_TAB_END + "']")));
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_TAB_END + "'")).click();
        Commons.sleepWait(3000);

        // remove a row
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_REMOVE_END + "'")).click();
        Commons.sleepWait(3000);
        List<WebElement> rows = driver.findElements(By.cssSelector("*[id$='"+ STRUCTURE_TABLE_END + "']" + TABLE_ROWS_END ));
        WebElement row = rows.get(1);
        row.findElement(By.cssSelector("*[id$='" + STRUCTURE_QUANTITY_END + "']")).click();
        Commons.sleepWait(3000);

        // edit a row
        // quantity
        Commons.assertModifiable("text", driver.findElements(By.cssSelector("*[id$='"+ STRUCTURE_QUANTITY_END +"']")).get(1), "number", "value","value");
        // after attribute value should probably be value instead of title so hard coded it to 23
        // reference designator
        Commons.assertModifiable("text", driver.findElements(By.cssSelector("*[id$='"+ STRUCTURE_REF_DES_END +"']")).get(1), "number", "value","value");

        // add a new row
        addNewItem();

        // replace with itself
        driver.findElements(By.cssSelector("*[id$='"+ STRUCTURE_QUANTITY_END +"']")).get(3).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ STRUCTURE_REPLACE_END +"']")).click();
        Commons.sleepWait(3000);
        driver.findElement(By.cssSelector("*[id$='"+ SELECT_AND_ADD_BOX_END+"']")).sendKeys(driver.findElements(By.cssSelector("*[id$='"+ STRUCTURE_ITEM_NUM_END +"']")).get(3).getText());
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ SELECT_AND_ADD_FIRST_DD_END +"']")).click();
        Commons.sleepWait(2000);

        // validate the red orange and green items
        // Assert shape
        Assert.assertEquals("Addition",driver.findElement(By.cssSelector("*[id$='"+ TOP_GREEN_END +"']")).getDomAttribute("title"));
        Assert.assertEquals("Remove",driver.findElement(By.cssSelector("*[id$='"+ MID_RED_END +"']")).getDomAttribute("title"));
        Assert.assertEquals("Modification",driver.findElement(By.cssSelector("*[id$='"+ LAST_ORANGE_END +"']")).getDomAttribute("title"));
        Assert.assertEquals("Replacement",driver.findElement(By.cssSelector("*[id$='"+ REPLACE_GREEN_END +"']")).getDomAttribute("title"));

        // assert color
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ TOP_GREEN_END +"']")).getDomAttribute("style").contains("background-color:rgb(0,94,0)"));
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ MID_RED_END +"']")).getDomAttribute("style").contains("background-color:rgb(168,0,0);"));
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ LAST_ORANGE_END +"']")).getDomAttribute("style").contains("background-color:rgb(196,86,14);"));
        Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='"+ REPLACE_GREEN_END +"']")).getDomAttribute("style").contains("background-color:rgb(0,94,0)"));

        // saving before the asserts caused the items order to change
        driver.findElement(By.cssSelector("*[id$='"+ STRUCTURE_SAVE_END +"']")).click();
        Commons.sleepWait(3000);
    }

    private static void addNewItem(){
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_END + "']")).click();
        Commons.sleepWait(2000);
        // create child
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_CLASS_END + "']")).clear();
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_CLASS_END + "']")).sendKeys("Battery");
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_DD_FIRST_PRODUCT_END + "']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_CLASS_END + "']")).sendKeys(Keys.TAB + Commons.userRandomString("Description"));
        // driver.findElement(By.cssSelector("*[id$='"+ EditItemStructuresPage.CREATE_DESC_END +"']")).sendKeys(Commons.userRandomString("Description"));
        Select LOB = new Select(driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_LOB_END)));
        LOB.selectByIndex(1);
        Select DA = new Select(driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_DA_END)));
        DA.selectByIndex(1);
        Select CC = new Select(driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_COTS_END)));
        CC.selectByIndex(1);
        driver.findElement(By.cssSelector("*[id$='" + STRUCTURE_CREATE_SAVE_CLOSE_END + "']")).click();
        Commons.sleepWait(3000);
    }

    private static void verifyDetailsTab() {
        Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+ DETAILS_NAME_END +"']")).findElement(By.xpath("./td[2]")),"After");
        Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+ DETAILS_CLASS_END +"']")).findElement(By.xpath("./td[2]")),"After");
        Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+ DETAILS_SPLM_END +"']")),"After");
        Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+ DETAILS_EA_END +"']")),"After");

        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ DETAILS_DESC_END +"']")), "after", null, "title");
        Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ DETAILS_LP_END +"']")), null, null, null);
        Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ DETAILS_DA_END +"']")), null, null, null);
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ DETAILS_PN_END +"']")), "23235", null, "title");
        Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ DETAILS_TYPE_END +"']")), null, null, null);
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ DETAILS_AI_END +"']")), "after", null, "title");

        driver.findElement(By.cssSelector("*[id$='"+ SAVE +"']")).click();
        Commons.sleepWait(5000);

        // validate red-lines (relative locators are not working as expected, hence trying out the old fashioned route
//        WebElement element = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(By.cssSelector("*[id$='"+ DETAILS_DESC_END +"']")));
//        String text = element.getText();
//        String textFormat = element.getDomAttribute("style");
//        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));
//
//        element = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(By.cssSelector("*[id$='"+ DETAILS_LP_END +"']")));
//        text = element.getText();
//        textFormat = element.getDomAttribute("style");
//        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));
//
//        element = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(By.cssSelector("*[id$='"+ DETAILS_DA_END +"']")));
//        text = element.getText();
//        textFormat = element.getDomAttribute("style");
//        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));
//
//        element = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(By.cssSelector("*[id$='"+ DETAILS_PN_END +"']")));
//        text = element.getText();
//        textFormat = element.getDomAttribute("style");;
//        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));
//
//        element = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(By.cssSelector("*[id$='"+ DETAILS_TYPE_END +"']")));
//        text = element.getText();
//        textFormat = element.getDomAttribute("style");;
//        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));
//
//        element = driver.findElement(RelativeLocator.with(By.tagName("span")).toRightOf(By.cssSelector("*[id$='"+ DETAILS_AI_END +"']")));
//        text = element.getText();
//        textFormat = element.getDomAttribute("style");
//        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));

        String textFormat = driver.findElement(By.cssSelector("*[id$='"+AffectedObjectsTab.DETAILS_DESC_END+"']")).findElement(By.xpath("./../../..")).findElements(By.tagName("td")).get(1).findElement(By.tagName("span")).getDomAttribute("style");
        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));

        textFormat = driver.findElement(By.cssSelector("*[id$='"+AffectedObjectsTab.DETAILS_LP_END+"']")).findElement(By.xpath("./../../..")).findElements(By.tagName("td")).get(1).findElement(By.tagName("span")).getDomAttribute("style");
        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));

        textFormat = driver.findElement(By.cssSelector("*[id$='"+AffectedObjectsTab.DETAILS_DA_END+"']")).findElement(By.xpath("./../../..")).findElements(By.tagName("td")).get(2).findElement(By.tagName("span")).getDomAttribute("style");
        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));

        textFormat = driver.findElement(By.cssSelector("*[id$='"+AffectedObjectsTab.DETAILS_PN_END+"']")).findElement(By.xpath("./../../..")).findElements(By.tagName("td")).get(2).findElement(By.tagName("span")).getDomAttribute("style");
        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));

        textFormat = driver.findElement(By.cssSelector("*[id$='"+AffectedObjectsTab.DETAILS_TYPE_END+"']")).findElement(By.xpath("./../../..")).findElements(By.tagName("td")).get(2).findElement(By.tagName("span")).getDomAttribute("style");
        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));

        textFormat = driver.findElement(By.cssSelector("*[id$='"+AffectedObjectsTab.DETAILS_AI_END+"']")).findElement(By.xpath("./../../..")).findElements(By.tagName("td")).get(2).findElement(By.tagName("span")).getDomAttribute("style");
        Assert.assertTrue(textFormat.contains("text-decoration:line-through") && (textFormat.contains("color:rgb(168,0,0)") || textFormat.contains("color:#a80000")));

    }
}
