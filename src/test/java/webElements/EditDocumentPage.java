package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.objects.Doc;
import utils.objects.Item;

import java.util.List;

public class EditDocumentPage {

    // top bar
    public static final String PAGETITLE = "//*[@id=\"pt1:_FOr1:1:_FONSr2:0:MAt3:0:AP4:SPph::_afrTtxt\"]/div";

    //    // General information

    public static final String NAME_END = ":it1::content";
    public static final String CLASS_END = ":soc2::content";
    public static final String LIFECYCLE_PHASE_END = ":soc4::content";
    public static final String PUOM_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r1:0";
    public static final String PUOM_END = ":primaryUomCodeDispId::content";
    public static final String CREATED_BY_END = ":plam1";
    public static final String CREATION_DATE_END = ":id2::content";
    public static final String EFFECTIVITY_DATE_END = ":id1::content";

    public static final String LOB_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String LOB_END = ":businessLine::content";
    public static final String DA_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String DA_END = ":designAuthority::content";
    public static final String CC_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String CC_END = ":customCots::content";
    public static final String BMOP_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String BMOP_END = ":brandMarkedOnPart::content";
    public static final String MBC_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String MBC_END = ":makeBuyCode::content";
    public static final String IPOM_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String IPOM_END = ":initialPointOfManufacture::content";
    public static final String SPLM_START = "";
    public static final String SPLM_END_FULL = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0:effplm33";
    public static final String PPAP_START = "pt1:_FOr1:0:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r2:0:rgAG_ID3:0";
    public static final String PPAP_END = ":ppapRequired::content";
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
    public static final String TAB_TITLE_END = ":AP4:SPph::_afrTtxt";

    public static final String LP_START = "pt1:_FOr1:1:_FONSr2:0:MAt2:0:AP4:r1:0:r4:0:r1:0:r1:0";
    public static final String LP_END = ":soc4::content";


    public static void generalInformation(WebDriver driver, Doc doc){
        Commons commons = new Commons();

        Commons.sleepWait(2000);
        // checking tab title
        String docName = driver.findElement(By.cssSelector("*[id$='"+EditItemPage.TAB_TITLE_END+"']")).getText();
        Assert.assertTrue(docName.contains("Edit Document:"));

        // checking the item Name
        Assert.assertTrue(docName.contains(driver.findElement(By.id(EditItemPage.NAME)).getText()));
        docName = driver.findElement(By.id(EditItemPage.NAME)).getText();
        // checking item type
        Assert.assertEquals(doc.className, driver.findElement(By.id(EditItemPage.CLASS)).getText());

        String[] doc_Name = docName.split("-");

        if(doc.className.equalsIgnoreCase("Operational Documents") || doc.className.equalsIgnoreCase("System Documents") || doc.className.equalsIgnoreCase("Quality Records")){
            // no validation rules for the above three doc types hence skipping other validations
            return;
        }

        if(doc.docGenMtd == 2){
            Assert.assertEquals(doc.relatedItem, doc_Name[0]);

            // checking item family number
            Assert.assertEquals(doc.rootPartFamily, doc_Name[1]);

//            // checking item version number
//            Assert.assertEquals(doc.version, doc_Name[2]);
        }else if(doc.docGenMtd == 1 || doc.docGenMtd == 0){
            // checking item class number
            String expectedClassNumber = commons.readFromExcel("EditItemPage","GeneralInformation", doc.className, "row").get(1);
            Assert.assertEquals(expectedClassNumber.split("\\.")[0], doc_Name[0]);

            // checking item revision number
            Assert.assertEquals("00", doc_Name[3].split(" ")[0]);
        }

        // checking item PUOM
//        Assert.assertEquals("REF", driver.findElement(By.cssSelector("*[id$='"+EditItemPage.PUOM_END+"']")).getText());
// the above field missing after approval matrix deployment

        // checking lifecycle phase
        Assert.assertEquals("Preliminary", driver.findElement(By.cssSelector("*[id$='"+EditDocumentPage.LP_END+"']")).getText());

    }

    public static void commonPartAttributes(WebDriver driver, Doc doc){
        Commons commons = new Commons();
        Select selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.DA_END+"']")));
        List pageList = commons.getVisibletextFromDropDown(selectObject);
        List excelList = commons.readFromExcel("EditItemPage","GeneralInformation", "DA", "column");
        Assert.assertTrue(commons.compareList(pageList,excelList));
    }

    public static void legacyAttributes(WebDriver driver,Doc doc){

    }

    public static void modify(WebDriver driver, Doc doc){
        if(doc.className.equalsIgnoreCase("Specification")) {

            String before, after, elementType;

            Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ADD_IMAGE_END+"']")));

            // check if image can be uploaded
            driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ADD_IMAGE_END+"']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ATTACH_IMAGE_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/Antenna.jpg");
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+EditItemPage.ATTACH_OK_END+"']")).click();
            //Commons.takeScreenshot(driver, 1000);

            // check if description can be modified
            WebElement description = driver.findElement(By.cssSelector("*[id$='"+EditItemPage.DESCRIPTION_END+"']"));
            before = description.getText();
            after = Commons.userRandomString("DescriptionAfter");
            description.clear();
            Commons.sleepWait();
            description.sendKeys(after);
            Assert.assertFalse(before.equalsIgnoreCase(description.getDomAttribute("title")));

            Commons.takeFullScreenshot();

            // non modifiable attributes
            elementType = driver.findElement(By.cssSelector("*[id$='"+NAME_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+CLASS_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+LIFECYCLE_PHASE_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

//            elementType = driver.findElement(By.cssSelector("*[id$='"+PUOM_END+"']")).getAriaRole();
//            Assert.assertFalse(Commons.editable.contains(elementType));
            // Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+PUOM_END+"']")), "after");

            elementType = driver.findElement(By.cssSelector("*[id$='"+CREATED_BY_END+"']")).findElement(By.className("xk")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+CREATION_DATE_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));

            elementType = driver.findElement(By.cssSelector("*[id$='"+EFFECTIVITY_DATE_END+"']")).getAriaRole();
            Assert.assertFalse(Commons.editable.contains(elementType));
//
//            elementType = driver.findElement(By.cssSelector("*[id$='"+DATE_CREATED_END+"']")).getAriaRole();
//            Assert.assertFalse(Commons.editable.contains(elementType));

        }
    }

}
