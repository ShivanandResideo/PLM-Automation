package webElements;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.objects.Doc;
import utils.objects.Item;
import utils.objects.Type;

public class StructuresTab {

    public static final String SAVE_END = ":AP4:APsv2";

    public static final String STRUCTURE_END = "Detail9::icon";
    public static final String SELECT_AND_ADD_END = ":ATT1:_ATTp:ctb8::icon";
    public static final String SELECT_AND_ADD_BOX_END = ":s1:ip1:lovTxtId::content";
    public static final String SELECT_AND_ADD_FIRST_END = ":s1:ip1:lovTxtId::_fndSuggestPopup_sugg_Items_0";
    public static final String QUANTITY_RELATED_END = ":ATT1:_ATTp:ctb1_21::icon";
    public static final String ALIGN_QUANTITY_END = ":ATT1:_ATTp:ctb1_20::icon";
    public static final String CREATE_END = ":ATT1:_ATTp:create::icon";
    public static final String DELETE_END = ":ATT1:_ATTp:delete::icon";

    public static final String TABLE_ELEMENTS_MIDDLE = ":ATT1:_ATTp:ATTt1:";
    // the first child index starts from 1 and after all first level children are covered, then the second level children are indexed
    public static final String TABLE_COLUMN_EXPAND_CHILDREN_END = "::di";
    public static final String TABLE_ROWS_END = ":ott1";
    public static final String TABLE_COLUMN_QUANTITY_END = ":ot1::content";
    public static final String TABLE_COLUMN_CUOM_END = ":primaryUomCodeDisp1Id::content";
    public static final String TABLE_COLUMN_REFDES_BOX_END = ":it1_6::content";
    public static final String TABLE_COLUMN_REFDES_DOT_END = ":cb6";
    public static final String TABLE_COLUMN_SEQUENCE_END = ":ot4::content";
    public static final String TABLE_COLUMN_COMMENTS_END = ":it2::content"; // id changed element removed
    public static final String TABLE_COLUMN_FIND_NUM_END = ":it9::content";
    public static final String TABLE_COLUMN_SOS_END = ":itsos::content";
    public static final String TABLE_COLUMN_NAME_END = ":pgl24";

    public static final String REF_DES_DIALOGBOX_AREA_END = ":ATT1:it8::content";
    public static final String REF_DES_DIALOGBOX_QR_END = ":ATT1:selectBooleanCheckbox1::Label0";
    public static final String REF_DES_DIALOGBOX_AQ_END = ":ATT1:sbc1::Label0";
    public static final String REF_DES_DIALOGBOX_OK_END = ":ATT1:cb5";
    public static final String REF_DES_DIALOGBOX_CANCEL_END = ":ATT1:d7::cancel";

    public static final String POPUP_QUANTITY_MISMATCH_ERROR_END = "d1::msgDlg::_cnt";
    public static final String EXPECTED_ERROR_MSG = "the number of reference designators doesn't match the quantity value.";
    public static final String POPUP_OK_END = "d1::msgDlg::cancel";

    public static final String CREATE_CLASS_END = ":s2:iclov1::content";
    public static final String DD_FIRST_PRODUCT_END = ":s2:iclov1::su0";
    public static final String CREATE_DESC_END = ":it2::content";
    //*[@id="pt1:_FOr1:1:_FONSr2:0:MAt4:0:AP4:r1:0:r11:1:r1:0:ATT1:r1:0:s2:r2:1:it2::content"]
    public static final String CREATE_LOB_END = ":f1_businessLineItemEffBaseEO1_ATTRIBUTE_CHAR2::content";
    public static final String CREATE_DA_END = ":f1_designAuthorityItemEffBaseEO1_ATTRIBUTE_CHAR3::content";
    public static final String CREATE_PL_END = ":f1_productLineItemEffBaseEO1_ATTRIBUTE_CHAR15::content";
    public static final String CREATE_COTS_END = ":f1_customCotsItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
    public static final String CREATE_SAVE_CREATE_END = ":s2:cb2";
    public static final String CREATE_SAVE_CLOSE_END = ":s2:cb4";
    public static final String CREATE_CANCEL_END = ":s2:cb5";

    public static final String RIGHT_PANE_EXPAND_END = ":ps1::i";
    public static final String RIGHT_PANE_ATTACHMENTS_END = ":attachment::icon";
    public static final String RIGHT_PANE_AML_END = ":aml::icon";
    public static final String RIGHT_PANE_ADDITIONAL_ATTRIBUTES_END = ":itemEffTab::icon";
    public static final String RIGHT_PANE_CHANGES_END = ":chgTab::icon";
    public static final String RIGHT_PANE_QUALITY_END = ":qualTab::icon";
    public static final String RIGHT_PANE_REFERENCE_DESIGNATOR_END = ":refDes::icon";
    public static final String RIGHT_PANE_ADDITIONAL_INFORMATION_END = ":addAtt::icon";
    public static final String RIGHT_PANE_SUBSTITUTE_COMPONENTS_END = ":substitute::icon";
    public static final String RIGHT_PANE_ATTACHMENTS_NAME_END = ":ph2::_afrTtxt";
    public static final String RIGHT_PANE_AML_NAME_END = ":ph5::_afrTtxt";
    public static final String RIGHT_PANE_ADDITIONAL_ATTRIBUTES_NAME_END = ":sdh1::_afrTtxt";
    public static final String RIGHT_PANE_CHANGES_NAME_END = ":np1:cni1";
    public static final String RIGHT_PANE_QUALITY_NAME_END = ":qnp1:qcni1";
    public static final String RIGHT_PANE_REFERENCE_DESIGNATOR_NAME_END = ":ph3::_afrTtxt";
    public static final String RIGHT_PANE_ADDITIONAL_INFORMATION_NAME_END = ":ph4::_afrTtxt";
    public static final String RIGHT_PANE_SUBSTITUTE_COMPONENTS_NAME_END = ":phsubstitute::_afrTtxt";
    public static final String RIGHT_PANE_REF_DES_DESC_END = ":it5::content";
    public static final String RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_ICON_END = ":AT2:_ATp:cil4::icon";
    public static final String RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_BOX_END = ":s1:ip1:lovTxtId::content";
    public static final String RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_FIRST_DD_END = ":s1:ip1:lovTxtId::_fndSuggestPopup_sugg_Items_0";
    public static final String RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_FIRST_ELEMENT_END = ":AT2:_ATp:ATt2:0:ot13";
    public static final String RIGHT_PANE_AML_SELECT_AND_ADD_ICON_END = ":AT2:_ATp:cil1::icon";
    public static final String RIGHT_PANE_AML_MN_END = ":AT2:manufacturerId_DispId::content";
    public static final String RIGHT_PANE_AML_MN_DD_END = ":AT2:manufacturerId_DispId::lovIconId";
    public static final String RIGHT_PANE_AML_MN_DD_FIRST_END = ":AT2:manufacturerId_DispId::content";
    public static final String RIGHT_PANE_AML_MPN_DD_LIST_END = ":mfgPartNumberId::dropdownPopup::dropDownContent::db";
    public static final String RIGHT_PANE_AML_MPN_END = ":AT2:mfgPartNumberId::content";
    public static final String RIGHT_PANE_AML_MPN_BOX_OK_END = ":AT2:mfgPartNumberId::lovDialogId::ok";
    public static final String RIGHT_PANE_AML_MPN_DD_END = ":AT2:mfgPartNumberId::content";
    public static final String RIGHT_PANE_AML_MPN_DD_FIRST_END = ":AT2:manufacturerId_DispId::content";
    public static final String RIGHT_PANE_AML_STATUS_END = ":AT2:soc1::content";
    public static final String RIGHT_PANE_AML_OK_END = ":AT2:saveACBtn1";
    public static final String RIGHT_PANE_AML_FIRST_ROW_END = ":AT2:_ATp:ATt2:0:it1";

    public static final String VIEW_COLUMNS_LIST_END = ":ATT1:_ATTp:_clmns::1755075241929";

    public static final String EXPORT_TO_EXCEL_END = ":ATT1:_ATTp:ctb1::icon";
    public static final String ACTIONS_COMPARE_STRUCTURES_END = "";

    public static final  String COMPARE_STRUCTURE_PAGE_TITLE_END = ":ap1:SPph::_afrTtxt";

    public static WebDriver driver = Commons.driver;
    public static Type sample;

    public static void addChildElements(Item[] items, Doc[] docs, String parent){

        if(parent.equalsIgnoreCase("Items")){
            sample = items[0];
            Commons.reachEditPage(items[0].type, items[0].name);
            Commons.sleepWait(3000);
            Type.reachTab("structure");
            for(int i=1; i< items.length; i++){
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_END +"']")).click();
                Commons.sleepWait(4000);
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_BOX_END +"']")).clear();
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_BOX_END +"']")).sendKeys(items[i].name);
                Commons.sleepWait(4000);
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_FIRST_END +"']")).click();
                Commons.sleepWait();
            }
            for (Doc doc : docs) {
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_END + "']")).click();
                Commons.sleepWait(6000);
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_BOX_END + "']")).clear();
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_BOX_END + "']")).sendKeys(doc.name);
                Commons.sleepWait(4000);
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_FIRST_END + "']")).click();
                Commons.sleepWait();
            }
        }
        else if(parent.equalsIgnoreCase("Documents")){
            sample = docs[0];
            Commons.reachEditPage(docs[0].type, docs[0].name);
            Commons.sleepWait(3000);
            Type.reachTab("structure");
            for(int i=1; i< docs.length; i++){
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_BOX_END +"']")).clear();
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_BOX_END +"']")).sendKeys(docs[i].name);
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SELECT_AND_ADD_FIRST_END +"']")).click();
            }
            for (Item item : items) {
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_END + "']")).click();
                Commons.sleepWait(4000);
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_BOX_END + "']")).clear();
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_BOX_END + "']")).sendKeys(item.name);
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SELECT_AND_ADD_FIRST_END + "']")).click();
                Commons.sleepWait();
            }
        }
    }

    public static void validateParameters(int rowID){
//        WebElement element = driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_CUOM_END+"']"));
//        Commons.sleepWait(2000);
//        Commons.assertNonModifiable(element, "after");
        Commons.sleepWait();
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ rowID+ StructuresTab.TABLE_COLUMN_QUANTITY_END+"']")), "number", "value","value");
        // after attribute value should probably be value instead of title so hard coded it to 23
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_REFDES_BOX_END+"']")), "number", "value","value");

        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_SEQUENCE_END+"']")), "number", "value","value");

        Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_SOS_END+"']")));
//        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_COMMENTS_END+"']")), "number", "value","value");

        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_SOS_END+"']")), "number", "value","value");

        // Find number is editable only once and once saved will not be allowed to change after save
        Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_FIND_NUM_END+"']")), "number", "value","value");
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SAVE_END +"']")).click();
        Commons.sleepWait(5000);
        // js.executeScript("arguments[0].scrollBy(50,0); arguments[1].scrollIntoView(true);", driver.findElement(By.id("pt1:_FOr1:1:_FONSr2:0:MAt4:0:AP4:r1:0:r11:1:r1:0:ATT1:_ATTp:ATTt1::scroller")), driver.findElement(By.id("pt1:_FOr1:1:_FONSr2:0:MAt4:0:AP4:r1:0:r11:1:r1:0:ATT1:_ATTp:ATTt1:1:itsos::content")))
        String elementType = driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_FIND_NUM_END +"']")).getAriaRole();
        System.out.println("the Find number AraiRole is :" + elementType);
        Assert.assertFalse(Commons.editable.contains(elementType));

        driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_REFDES_DOT_END +"']")).click();
        Commons.untick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_QR_END +"']")));
        Commons.untick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AQ_END +"']")));
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).sendKeys("A1,A2,A3");
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_OK_END +"']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SAVE_END +"']")).click();
        Commons.sleepWait(2000);
        // takes in comma separated values with quantity related not Align quantity -> saved
        driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_REFDES_DOT_END +"']")).click();
        Commons.tick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_QR_END +"']")));
        Commons.untick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AQ_END +"']")));
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).sendKeys("A1,A2,A3");
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_OK_END +"']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SAVE_END +"']")).click();
        Commons.sleepWait(2000);
//      Verify the error message in the pop-up and then press OK.
        String errorMessage = driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.POPUP_QUANTITY_MISMATCH_ERROR_END +"']")).getText();
        Assert.assertTrue(errorMessage.contains(StructuresTab.EXPECTED_ERROR_MSG));
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.POPUP_OK_END +"']")).click();

        // takes in a range without quantity related but Align quantity -> saved
        driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_REFDES_DOT_END +"']")).click();
        Commons.untick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_QR_END +"']")));
        Commons.tick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AQ_END +"']")));
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).sendKeys("A1-A5");
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_OK_END +"']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SAVE_END +"']")).click();
        Commons.sleepWait(2000);
        // takes in a range with quantity related and Align quantity -> saved
        driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_REFDES_DOT_END +"']")).click();
        Commons.tick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_QR_END +"']")));
        Commons.tick(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AQ_END +"']")));
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_AREA_END +"']")).sendKeys("A1-A4"); // changed previously was A1;A4 but now it is A1-A4
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.REF_DES_DIALOGBOX_OK_END +"']")).click();

        Commons.sleepWait(5000);
        // Verify if the quantity changed to desired value
        int value = Integer.parseInt(driver.findElement(By.cssSelector("*[id$='"+ rowID + StructuresTab.TABLE_COLUMN_QUANTITY_END+"']")).getDomAttribute("value"));
        System.out.println("expected is 4 and the value in the page displayed is "+ value);
        Assert.assertTrue(value == 4);
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SAVE_END +"']")).click();
    }

    public static void validateRightPane(String level) {
        String row="0";
        Commons.sleepWait(2000);
        if(level.equalsIgnoreCase("parent")){
            row = "0";
        }else if(level.equalsIgnoreCase("child")){
            row = "1";
        }
        driver.findElement(By.cssSelector("*[id$='"+ row + StructuresTab.TABLE_ROWS_END +"']")).findElement(By.xpath("./../..")).click();

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_EXPAND_END +"']")).click();

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_ATTACHMENTS_END +"']")).click();
        Assert.assertEquals("Attachments", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_ATTACHMENTS_NAME_END +"']")).getText());

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_END +"']")).click();
        Assert.assertEquals("AML", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_NAME_END +"']")).getText());

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_ADDITIONAL_ATTRIBUTES_END +"']")).click();
        Assert.assertEquals("Additional Attributes", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_ADDITIONAL_ATTRIBUTES_NAME_END +"']")).getText());

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_CHANGES_END +"']")).click();
        Assert.assertEquals("Item Changes", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_CHANGES_NAME_END +"']")).getText());

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_QUALITY_END +"']")).click();
        Assert.assertEquals("Quality Issues and Actions", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_QUALITY_NAME_END +"']")).getText());

        Commons.sleepWait(2000);
        if(level.equalsIgnoreCase("child")){
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_REFERENCE_DESIGNATOR_END +"']")).click();
            Assert.assertEquals("Reference Designator", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_REFERENCE_DESIGNATOR_NAME_END +"']")).getText());

            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_ADDITIONAL_INFORMATION_END +"']")).click();
            Assert.assertEquals("Additional Information", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_ADDITIONAL_INFORMATION_NAME_END +"']")).getText());

            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_END +"']")).click();
            Assert.assertEquals("Substitute Components", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_NAME_END +"']")).getText());

            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_ATTACHMENTS_END +"']")).click();
            Commons.sleepWait(2000);
            AttachmentsTab.addNecessaryAttachments("Zip");
            Commons.sleepWait(2000);
            AttachmentsTab.verifyFilesAdded("Zip");

            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_REFERENCE_DESIGNATOR_END +"']")).click();
            Commons.sleepWait(2000);
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_REF_DES_DESC_END +"']")), "number", "value", "value");

            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_END +"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_ICON_END +"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_BOX_END +"']")).sendKeys(sample.name.split("-")[0] + "-");
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_FIRST_DD_END +"']")).click();
            Commons.sleepWait(2000);
            Assert.assertEquals(sample.name.split("-")[0], driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_SUBSTITUTE_COMPONENTS_SELECT_AND_ADD_FIRST_ELEMENT_END +"']")).getText().split("-")[0]);

            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_END +"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_SELECT_AND_ADD_ICON_END +"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MN_END +"']")).sendKeys("AUTOMATION COMPONENTS INC" + Keys.TAB);
            Commons.sleepWait(2000);
            Commons.findElementByAttributeValue("title", "Search: Manufacturer Part Number").click();
            Commons.sleepWait();
            String partNumber = Commons.findElementByAttributeValue( driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MPN_DD_LIST_END +"']")), "_afrrk", "1").getText();
            Commons.findElementByAttributeValue( driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MPN_DD_LIST_END +"']")), "_afrrk", "1").click();
            Commons.sleepWait();
//            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MPN_END +"']")).clear();
//            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MPN_END +"']")).sendKeys("101" + Keys.TAB);
//            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MPN_BOX_OK_END +"']")).click();
//            Commons.sleepWait(2000);
            Select select = new Select(driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_STATUS_END +"']")));
            select.selectByIndex(1);
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_OK_END +"']")).click();
            Commons.sleepWait(2000);
            // check if the entry has been made and element is present
            Assert.assertEquals(partNumber , driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_FIRST_ROW_END +"']")).getText());

        }
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_EXPAND_END +"']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SAVE_END +"']")).click();
    }

    public static void addNewChildObjects(String type){
        if(type.equalsIgnoreCase("item")) {
            Commons.sleepWait(3000);
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_END + "']")).click();
            Commons.sleepWait(2000);
            // create child
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_CLASS_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_CLASS_END + "']")).sendKeys("Battery");
            Commons.sleepWait();
            // sometimes the item loads without click hence the try catch
            try {
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.DD_FIRST_PRODUCT_END + "']")).click();
                Commons.sleepWait(2000);
            } catch (Exception e) {

            }
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_CLASS_END + "']")).sendKeys(Keys.TAB + Commons.userRandomString("Description"));
            // driver.findElement(By.cssSelector("*[id$='"+ EditItemStructuresPage.CREATE_DESC_END +"']")).sendKeys(Commons.userRandomString("Description"));
            try{
                new Select(Commons.findElementByLabel("Line of Business")).selectByIndex(1);
            }
            catch (Exception ignored){

            }

            try{
                new Select(Commons.findElementByLabel("Design Authority")).selectByIndex(1);
            }
            catch (Exception ignored){

            }
            try{
                new Select(Commons.findElementByLabel("Product Line")).selectByIndex(1);
            }
            catch (Exception ignored){

            }
            try{
                new Select(Commons.findElementByLabel("Custom/COTS")).selectByIndex(1);
            }
            catch (Exception ignored){

            }

            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_SAVE_CLOSE_END + "']")).click();
            Commons.sleepWait(3000);
        }
        else if(type.equalsIgnoreCase("document")){
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_END + "']")).click();
            Commons.sleepWait(2000);
            // create child
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_CLASS_END + "']")).clear();
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_CLASS_END + "']")).sendKeys("Specification");
            // sometimes the item loads without click hence the try catch
            try {
                driver.findElement(By.cssSelector("*[id$='" + StructuresTab.DD_FIRST_PRODUCT_END + "']")).click();
                Commons.sleepWait(2000);
            } catch (Exception ignored) {

            }Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_CLASS_END + "']")).sendKeys(Keys.TAB + Commons.userRandomString("Description"));
            // driver.findElement(By.cssSelector("*[id$='"+ EditItemStructuresPage.CREATE_DESC_END +"']")).sendKeys(Commons.userRandomString("Description"));
            Commons.sleepWait();

            try{
                new Select(Commons.findElementByLabel("Line of Business")).selectByIndex(1);
            }
            catch (Exception ignored){

            }

            try{
                new Select(Commons.findElementByLabel("Design Authority")).selectByIndex(1);
            }
            catch (Exception ignored){

            }

            try{
                new Select(Commons.findElementByLabel("Product Line")).selectByIndex(1);
            }
            catch (Exception ignored){

            }
            try{
                new Select(Commons.findElementByLabel("Custom/COTS")).selectByIndex(1);
            }
            catch (Exception ignored){

            }
            driver.findElement(By.cssSelector("*[id$='" + StructuresTab.CREATE_SAVE_CLOSE_END + "']")).click();
            Commons.sleepWait(3000);
        }
    }
}
