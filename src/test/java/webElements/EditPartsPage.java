package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.objects.Part;

import java.util.List;

public class EditPartsPage {

    // top bar
    public static final String PAGETITLE_END = ":AP1:ph1::_afrTtxt";

    // General information
    public static final String MPN_END = ":AP1:it23::content";
    public static final String DESCRIPTION_END = ":AP1:it20::content";
//    public static final String MANUFACTURER_END = ":AP1:gl_redwood4"; // id changed label missing for
    public static final String MANUFACTURER_END = ":AP1:tradingPartnerIdId";
    public static final String STATUS_END = ":AP1:soc2::content";
    public static final String CREATED_FROM_END = ":AP1:noViewSaveAsMPNPrivPopup"; // id changed label missing

    // Additional information
    public static final String REACH_COMPLIANT_END = ":AP1:df1_MfgPartNumberDFFIteratorreachCompliant__FLEX_EMPTY::content";
    public static final String REACH_COMPLIANT_DATE_END = ":AP1:df1_MfgPartNumberDFFIteratorreachCompliantDate__FLEX_EMPTY::content";
    public static final String LIFECYCLE_PHASE_END = ":AP1:df1_MfgPartNumberDFFIteratorlifecyclePhase__FLEX_EMPTY::content";
    public static final String EU_ROHS_COMPLIANT_END = ":AP1:df1_MfgPartNumberDFFIteratoreuRohsCompliant__FLEX_EMPTY::content";
    public static final String SVHS_LIST_END = ":AP1:df1_MfgPartNumberDFFIteratorsvhcList__FLEX_EMPTY::content";
    public static final String LSCU_DATE_END = ":AP1:df1_MfgPartNumberDFFIteratorsiliconExpertLastUpdateDate__FLEX_EMPTY::content";
    public static final String DATE_CREATED_END = ":AP1:df1_MfgPartNumberDFFIteratordateCreated__FLEX_EMPTY::content";
    public static final String SEC_ID_END = ":AP1:df1_MfgPartNumberDFFIteratorsiliconExpertComponentId__FLEX_EMPTY::content";

    public static final String ATTACHMENTS_TAB_END = ":AP1:Detail2::icon";
    public static final String ADD_ATTACHMENT_END = ":AP1:av1:avgAdd::icon";
    public static final String CHOOSE_FILE_END = ":AP1:av1:dciAdd:sfadd:ifAddFile::if";
    public static final String DELETE_FIRST_END = ":AP1:av1:dciTable:tblAttach:0:cilDel::icon";
    public static final String DELETE_YES_END = ":AP1:av1:confirm";
    private static final String DELETE_ATTACHMENT_END = ":AP1:av1:fatdDel::icon";
    private static final String FIRST_ATTACHMENT_END = ":AP1:av1:dciTable:tblAttach::db";

    public static void generalInformation(WebDriver driver, Part part){

        // webElement = driver.findElement(By.cssSelector("*[id$='"++"']"));
        String before, after, elementType;
        WebElement webElement = driver.findElement(By.cssSelector("*[id$='"+PAGETITLE_END+"']"));
        Assert.assertTrue(webElement.getText().contains("Details: General Information"));

        webElement = driver.findElement(By.cssSelector("*[id$='"+DESCRIPTION_END+"']"));
        before = webElement.getText();
        after = Commons.userRandomString("Description After");
        webElement.clear();
        webElement.sendKeys(after);
        Assert.assertFalse(before.equalsIgnoreCase(webElement.getDomAttribute("title")));

        webElement = driver.findElement(By.cssSelector("*[id$='"+STATUS_END+"']"));
        Select selectObject = new Select(webElement);
        before = selectObject.getFirstSelectedOption().getText();
        Commons.selectDifferentOption(selectObject);
        after = selectObject.getFirstSelectedOption().getText();
        Assert.assertFalse(after.equalsIgnoreCase(before));

        // check all the non modifiable entities of the general information section
        elementType = driver.findElement(By.cssSelector("*[id$='"+MPN_END+"']")).getAriaRole();
        Assert.assertFalse(Commons.editable.contains(elementType));


        elementType = driver.findElement(By.cssSelector("*[id$='"+MANUFACTURER_END+"']")).getAriaRole();
        Assert.assertFalse(Commons.editable.contains(elementType));

        elementType = driver.findElement(By.cssSelector("*[id$='"+CREATED_FROM_END+"']")).getAriaRole();
        Assert.assertFalse(Commons.editable.contains(elementType));

    }

    public static void additionalInformation(WebDriver driver, Part part) {

        WebElement webElement;
        String before, after, elementType;

        webElement = driver.findElement(By.cssSelector("*[id$='"+REACH_COMPLIANT_END+"']"));
        before = webElement.getText();
        after = Commons.userRandomString("Reach Compliant");
        webElement.sendKeys(after);
        Assert.assertFalse(before.equalsIgnoreCase(webElement.getDomAttribute("title")));

        webElement = driver.findElement(By.cssSelector("*[id$='"+REACH_COMPLIANT_DATE_END+"']"));
        before = webElement.getText();
        after = "06/23/93";
        webElement.sendKeys(after);
        Assert.assertFalse(before.equalsIgnoreCase(webElement.getDomAttribute("title")));

        webElement = driver.findElement(By.cssSelector("*[id$='"+LIFECYCLE_PHASE_END+"']"));
        before = webElement.getText();
        after = Commons.userRandomString("Lifecycle Phase");
        webElement.sendKeys(after);
        Assert.assertFalse(before.equalsIgnoreCase(webElement.getDomAttribute("title")));

        webElement = driver.findElement(By.cssSelector("*[id$='"+EU_ROHS_COMPLIANT_END+"']"));
        Select selectObject = new Select(webElement);
        before = selectObject.getFirstSelectedOption().getText();
        Commons.selectDifferentOption(selectObject);
        after = selectObject.getFirstSelectedOption().getText();
        Assert.assertFalse(after.equalsIgnoreCase(before));

        webElement = driver.findElement(By.cssSelector("*[id$='"+SVHS_LIST_END+"']"));
        before = webElement.getText();
        after = Commons.userRandomString("SVHS List");
        webElement.sendKeys(after);
        Assert.assertFalse(before.equalsIgnoreCase(webElement.getDomAttribute("title")));

        elementType = driver.findElement(By.cssSelector("*[id$='"+LSCU_DATE_END+"']")).getAriaRole();
        Assert.assertFalse(Commons.editable.contains(elementType));

        elementType = driver.findElement(By.cssSelector("*[id$='"+DATE_CREATED_END+"']")).getAriaRole();
        Assert.assertFalse(Commons.editable.contains(elementType));

        elementType = driver.findElement(By.cssSelector("*[id$='"+SEC_ID_END+"']")).getAriaRole();
        Assert.assertFalse(Commons.editable.contains(elementType));
    }



    public static void attachFile(WebDriver driver, Part part){
        driver.findElement(By.cssSelector("*[id$='"+ATTACHMENTS_TAB_END+"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ADD_ATTACHMENT_END+"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+CHOOSE_FILE_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/Antenna.jpg");
        Commons.takeFullScreenshot();

        driver.findElement(By.cssSelector("*[id$='"+FIRST_ATTACHMENT_END+"'")).click();
        driver.findElement(By.cssSelector("*[id$='"+DELETE_ATTACHMENT_END+"'")).click();
        driver.findElement(By.cssSelector("*[id$='"+DELETE_YES_END+"'")).click();
        Commons.takeFullScreenshot();
    }
}