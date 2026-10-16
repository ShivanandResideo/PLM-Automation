package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.objects.Item;
import utils.objects.Type;

import java.util.List;

public class EditItemPage {

    // top bar
    public static final String PAGE_TITLE = "*[@id=\"pt1:_FOr1:1:_FONSr2:0:MAt3:0:AP4:SPph::_afrTtxt\"]/div";

    // General information

    public static final String NAME_END = ":it1::content";
    public static final String CLASS_END = ":soc2::content";
    public static final String LIFECYCLE_PHASE_END = ":soc4::content";
    public static final String CREATED_BY_END = ":plam1";
    public static final String CREATION_DATE_END = ":id2::content";
    public static final String EFFECTIVITY_DATE_END = ":id1::content";

    public static final String NAME = "pt1:_FOr1:1:_FONSr2:0:MAt3:0:AP4:r1:0:r4:0:r1:0:r1:0:it1::content";
    public static final String CLASS = "pt1:_FOr1:1:_FONSr2:0:MAt3:0:AP4:r1:0:r4:0:r1:0:r1:0:soc2::content";
    public static final String PUOM_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r1:0";
    public static final String PUOM_END = ":primaryUomCodeDispId::content";
    public static final String LOB_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String LOB_END = ":businessLine::content";
    public static final String DA_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String DA_END = ":designAuthority::content";
    public static final String CC_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String CC_END = ":customCots::content";
    public static final String BMOP_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String BMOP_END = ":brandMarkedOnPart::content";
    public static final String MBC_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
//    public static final String MBC_END = ":makeBuyCode::content"; id changed
    public static final String MBC_END = ":productionMakeBuyCode::content";
    public static final String IPOM_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
//    public static final String IPOM_END = ":initialPointOfManufacture::content"; id changed
    public static final String IPOM_END = ":primaryPointOfManufacture::content";
    public static final String SPLM_START = "";
    public static final String SPLM_END_FULL = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0:effplm33";
    public static final String PPAP_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
//    public static final String PPAP_END = ":ppapRequired::content"; id changed
    public static final String PPAP_END = ":ppaprequired::content";
    public static final String IPOU_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String IPOU_END = ":initialPointOfUse::content";

    public static final String ROHS_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID4:0";
    public static final String ROHS_END = ":rohsCompliance::content";
    public static final String AAR_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID4:0";
    public static final String AAR_END = ":agencyApprovalRequired::content";

    public static final String TYPE_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID5:0";
    public static final String TYPE_END = ":antennaType::content";
    public static final String FUNCTION_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID5:0";
    public static final String FUNCTION_END = ":antennaFunction::content";

    public static final String CABLE_ASSEMBLY_TYPE = ":connectorType::content";
    public static final String TAB_TITLE_END = ":SPph::_afrTtxt";
    public static final String ADD_IMAGE_END = ":a1:clLAdds::icon";
    public static final String ATTACH_IMAGE_END = ":ifPopup::content";
    public static final String ATTACH_OK_END = ":a1:dc_cb1";
    public static final String DESCRIPTION_END = ":it4::content";
    public static final String SOURCE_PLM_END = ":sourcePlm::content";
    public static final String LEGACY_PLM_ALIAS_END = ":legacyPlmAlias::content";
    public static final String PPT_END = ":plantProcurementType::content";
    public static final String CAD_REF_ID = ":cadReferenceId::content";
    public static final String CAD_REF_URL = ":cadReferenceUrl:dc_it1::content";
    public static final String ENOVIA_ATTR_URL_END = ":enoviaAttributes::content";

    // function to generate the css selector ID from class and the attribute
    public static String getClassAttributesID(String classType, String attributeName){
        String result;

        if(classType.contains(" ")) {
            String[] ID = classType.split(" ");
            result = ID[0].toLowerCase();
            classType = result + ID[1];
        }

        if(attributeName.contains(" ")) {
            String[] ID = attributeName.split(" ");
            result = ID[0].toLowerCase();
            result = result + ID[1];

        }else if(attributeName.equalsIgnoreCase("Dielectric")
                || attributeName.equalsIgnoreCase("Finish")){
            result = attributeName.toLowerCase();
        }else {
            classType = classType.toLowerCase();
            //classType.charAt(0) = classType.charAt(0);
            if(classType.contains("crystal")){
                classType = "crystOsc";
            }

            result = attributeName.substring(0,1).toUpperCase() + attributeName.substring(1).toLowerCase();
            result = classType+result;
        }
        return ":"+result+"::content";
    }

    public static void generalInformation(WebDriver driver, Item item){
        Commons commons = new Commons();

        // checking tab title
        String itemName = driver.findElement(By.cssSelector("*[id$='"+EditItemPage.TAB_TITLE_END+"']")).getText();
        Assert.assertTrue(itemName.contains("Edit Item:"));

        // checking the item Name
        Assert.assertTrue(itemName.contains(driver.findElement(By.id(EditItemPage.NAME)).getText()));
        itemName = driver.findElement(By.id(EditItemPage.NAME)).getText();
        // checking item type
        Assert.assertEquals(item.className, driver.findElement(By.id(EditItemPage.CLASS)).getText());

        String[] item_Name = itemName.split("-");

        if(!(item.className.equalsIgnoreCase("Product") || item.partGenMtd==3)){
            // checking item class number
            String expectedClassNumber = commons.readFromExcel("EditItemPage","GeneralInformation", item.className, "row").get(1);
            if(item.className.equalsIgnoreCase("Packaging")){
                Assert.assertEquals(expectedClassNumber.substring(0,2), item_Name[0].substring(0,2));
            }
            else {
                Assert.assertEquals(expectedClassNumber.split("\\.")[0], item_Name[0]);
            }
            if(item.partGenMtd == 1 || item.partGenMtd == 0){
                // checking item revision number
                Assert.assertEquals("00", item_Name[3].split(" ")[0]);
            }
        }else if(item.partGenMtd == 2){
            // checking item family number
            Assert.assertEquals(item.familyOption, item_Name[1]);

            // checking item version number
            Assert.assertEquals(item.familyVersion, item_Name[2]);
        }

        // checking item PUOM
        Assert.assertEquals(item.PUOM, driver.findElement(By.cssSelector("*[id$='"+EditItemPage.PUOM_END+"']")).getText());

    }

    public static void commonPartAttributes(WebDriver driver, Item item){

        Commons commons = new Commons();

        // checking item LOB. Putting in try catch incase the field is optional while creating the item
        Select selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.LOB_END+"']")));
        try{
            Assert.assertEquals(item.lineOfBusiness, selectObject.getFirstSelectedOption().getText());
        } catch (AssertionError e) {
            selectObject.selectByVisibleText(item.lineOfBusiness);
            Commons.sleepWait();
            Assert.assertEquals(item.lineOfBusiness, selectObject.getFirstSelectedOption().getText());
        }

        // checking item DA
        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.DA_END+"']")));
        Assert.assertEquals(item.designAuthority, selectObject.getFirstSelectedOption().getText());

        // checking item CC
        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.CC_END+"']")));
        Assert.assertEquals(item.customCOTS, selectObject.getFirstSelectedOption().getText());

        // validate commonpart attributes drop downs
        // validate BMOP
        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.BMOP_END+"']")));
        List pageList = commons.getVisibletextFromDropDown(selectObject);
        List excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "BMOP", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

        // validate MBC
        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.MBC_END+"']")));
        pageList = commons.getVisibletextFromDropDown(selectObject);
        excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "MBC", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));


        // validate IPOM
        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.IPOM_END+"']")));
        pageList = commons.getVisibletextFromDropDown(selectObject);
        excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "IPOM", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

        // validate SPLM -> possible bug in implementation - reported
//        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.SPLM_END+"']")));
//        Assert.assertTrue(selectObject.getOptions().equals(
//                commons.readFromExcel("EditItemPage","GeneralInformation", "SPLM", "column")));

        // validate PPAP
        selectObject = new Select(Commons.findElementByLabel("PPAP Required"));
//        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.PPAP_END+"']")));
        pageList = commons.getVisibletextFromDropDown(selectObject);
        excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "PPAP", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

        // validate IPOU
        selectObject = new Select(Commons.findElementByLabel("Initial Point of Use"));
        pageList = commons.getVisibletextFromDropDown(selectObject);
        excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "IPOU", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

    }

    public static void ERCVInformation(WebDriver driver, Item item){
        Commons commons = new Commons();

        // validate ERCV

        // validate ROHS
        Select selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ROHS_END+"']")));
        List pageList = commons.getVisibletextFromDropDown(selectObject);
        List excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "ROHS", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));

        // validate AAR
        selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.AAR_END+"']")));
        pageList = commons.getVisibletextFromDropDown(selectObject);
        excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "AAR", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));
    }

    public static void classSpecificInformation(WebDriver driver, Item item){

    }

    public static void ESGInformation(WebDriver driver, Item item){

    }

    public static void legacyAttributes(WebDriver driver, Item item){

    }

    public static void modify(WebDriver driver, Item item){
        if(item.className.equalsIgnoreCase("Antenna")) {

            String before, after, elementType;

            // check if image can be uploaded
            driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ADD_IMAGE_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ATTACH_IMAGE_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/Antenna.jpg");
            driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ATTACH_OK_END+"']")).click();
            Commons.takeScreenshot(driver, 1000);

            // check if description can be modified
            Commons.assertModifiable("Text", driver.findElement(By.cssSelector("*[id$='"+EditItemPage.DESCRIPTION_END+"']")), "Description_After", null, "title" );

            // non modifiable attributes
            elementType = driver.findElement(By.cssSelector("*[id$='"+NAME_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+CLASS_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+LIFECYCLE_PHASE_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

//            elementType = driver.findElement(By.cssSelector("*[id$='"+PUOM_END+"']")).getAriaRole();
//            Assert.assertFalse(Commons.editable.contains(elementType));
            Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+PUOM_END+"']")), "after");

            elementType = driver.findElement(By.cssSelector("*[id$='"+CREATED_BY_END+"']")).findElement(By.className("xk")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+CREATION_DATE_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+EFFECTIVITY_DATE_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+SOURCE_PLM_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+LEGACY_PLM_ALIAS_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

//            elementType = driver.findElement(By.cssSelector("*[id$='"+PPT_END+"']")).getAriaRole();
//            Assert.assertFalse(Commons.editable.contains(elementType));
            Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+PPT_END+"']")), "after");

            elementType = driver.findElement(By.cssSelector("*[id$='"+CAD_REF_ID+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+CAD_REF_URL+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

//            elementType = driver.findElement(By.cssSelector("*[id$='"+ENOVIA_ATTR_URL_END+"']")).getAriaRole();
//            Assert.assertFalse(Commons.editable.contains(elementType));
            Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+ENOVIA_ATTR_URL_END+"']")), "after");
        }
    }

    public static void setDAC(Type item, String expectedDAC){
        List<List<String>> excel;
        excel = Commons.readMatchingRowsFromExcel("ApprovalMatrix", "DAC", item.className);

        String actualDAC = Commons.findInputElementByParameterName("Design Approval Category").getText();
        if (!actualDAC.equalsIgnoreCase(expectedDAC)) {
            for (List<String> row : excel) {
                if (row.get(1).equalsIgnoreCase(expectedDAC)) {
                    new Select(Commons.findInputElementByParameterName(row.get(2).toString())).selectByVisibleText(row.get(3).toString());
                    break;
                }
            }
        }
        Commons.sleepWait();
        Commons.findElementByExactName("Save").click();
        Commons.sleepWait();
        actualDAC = Commons.findInputElementByParameterName("Design Approval Category").getText();
        if(!actualDAC.equalsIgnoreCase(expectedDAC)){
            Assert.fail("Mismatch in the values. Setting the value failed. \n Expected was "+ expectedDAC +" but got "+ actualDAC);
        }
    }

    public static void setDT(String expectedDT){
        List<String> excel;
        excel = Commons.readFromExcel("ApprovalMatrix", "setDT", expectedDT, "row");

        new Select(Commons.findElementByLabel("Line of Business")).selectByVisibleText(excel.get(1));
        new Select(Commons.findElementByLabel("Design Authority")).selectByVisibleText(excel.get(2));
        // new Select(Commons.findElementByLabel("Product Line")).selectByVisibleText(excel.get(3)); //comment this for DEV1

        Commons.sleepWait();
        Commons.findElementByExactName("Save").click();
        Commons.sleepWait();
        String actualDT = Commons.findElementByLabel("Design Team").getDomAttribute("value");
        if(!actualDT.equalsIgnoreCase(expectedDT)){
            Assert.fail("Mismatch in the values. Setting the value failed. \n Expected was "+ expectedDT +" but got "+ actualDT);
        }
    }

    public static void verifyDAC(Type item){
        WebElement DAC = Commons.findInputElementByParameterName("Design Approval Category");

        // read the row and then decide what to do from there depending on the entries
        List excel;
        excel = Commons.readFromExcel("ApprovalMatrix", "DAC", item.className, "row");
        String DACExpected = excel.get(1).toString();
        try {
            String attributeName = excel.get(2).toString();
            if(attributeName.equalsIgnoreCase("Unsupported cell type")){
                throw new IndexOutOfBoundsException();
            }
            String attributeValue = excel.get(3).toString();
            WebElement attributeInput = Commons.findInputElementByParameterName(attributeName);

            Select select = new Select(attributeInput);
            WebElement element;
            int size = select.getOptions().size();
            for(int i=0;i<size;i++){
                DACExpected = excel.get(1).toString();
                try {
                    element = select.getOptions().get(i);

                } catch (Exception e) {
                    attributeInput = Commons.findInputElementByParameterName(attributeName);
                    select = new Select(attributeInput);
                    element = select.getOptions().get(i);
                }
                String option = element.getText();
                if(option.isEmpty()){
                    continue;
                }
                System.out.println("Selecting the option "+option);
                select.selectByVisibleText(element.getText());
                Commons.sleepWait();
                Commons.findElementByExactName("Save").click();
                Commons.sleepWait(4000);

                // change expected for different options selected
                if(attributeName.equalsIgnoreCase("Custom/COTS")){
                    if(option.equalsIgnoreCase("COTS")){
                        DACExpected = "Mechanical-COTS";
                    }
                    else if(option.equalsIgnoreCase("Custom") || option.equalsIgnoreCase("Source Controlled")){
                        DACExpected = "Mechanical-Custom";
                    }
                    else {
                        DACExpected = "";
                    }
                }
                else if(attributeName.equalsIgnoreCase("Content")){
                    if(option.equalsIgnoreCase("Barcodes only")){
                        DACExpected = "Label-ID/Shipping";
                    }
                    else{
                        DACExpected = DACExpected.split("-")[0] + "-" + option;
                    }
                }
                else if(attributeName.equalsIgnoreCase("Content to be printed")){
                    if(option.equalsIgnoreCase("Branded artwork")){
                        DACExpected = "Packaging-Product Info";
                    }
                    else{
                        DACExpected = DACExpected.split("-")[0] + "-Basic";
                    }
                }
                else if(attributeName.equalsIgnoreCase("Contents")) {
                    DACExpected = DACExpected.split("-")[0] + "-" + option;
                }
                else if(attributeName.equalsIgnoreCase("Specification Category")){
                    if (option.equalsIgnoreCase("Elec Component")){
                        DACExpected = "Component";
                    }
                    else if (option.equalsIgnoreCase("Packaging-Printed Artwork")){
                        DACExpected = "Packaging-Product Info";
                    }
                    else if (option.contains("Subassembly")){
                        DACExpected = "Assembly-" + option.split("y-")[1];
                    }
                    else {
                        DACExpected = option;
                    }
                }

                //validate DAC
                DAC = Commons.findInputElementByParameterName("Design Approval Category");
                Assert.assertEquals(DACExpected, DAC.getText());
                Commons.sleepWait();
            }
        }
        catch (IndexOutOfBoundsException e){
            // validate the DAC
            Assert.assertEquals(DACExpected, DAC.getText());
        }
    }

    // below code not required. To be deleted.
    public static void setDC(Type item, String DAC) {
        // read the row and then decide what to do from there depending on the entries
        WebElement DACE = Commons.findInputElementByParameterName("Design Approval Category");

        List excel;
        excel = Commons.readFromExcel("ApprovalMatrix", "DAC", item.className, "row");

        try {
            String attributeName = excel.get(2).toString();
            if (attributeName.equalsIgnoreCase("Unsupported cell type")) {
                throw new IndexOutOfBoundsException();
            }
            new Select(Commons.findInputElementByParameterName(excel.get(3).toString())).selectByVisibleText(DAC);
        }
        catch(IndexOutOfBoundsException e){
            // do nothing. we need it to be set to 'Component' and it by default is 'Component'
        }

        if(!DAC.equalsIgnoreCase(DACE.getText())){
            System.out.println("Mismatch in the values. Setting the value failed.");
            Assert.fail();
        }
    }
}