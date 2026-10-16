package webElements;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.objects.Part;

public class AMLTab {

    public static final String AML_TAB_END = ":Detail10::icon";

    public static final String EXPAND_OPTIONS_END = ":AT2:_ATp:ATtb1::eoi";
    public static final String SELECT_AND_ADD_END = ":AT2:_ATp:cil1::icon";
    public static final String DELETE_END = ":AT2:_ATp:cil3::icon";
    public static final String CREATE_END = ":AT2:_ATp:cil32::icon";

    public static final String MN_END = ":AT2:manufacturerId_DispId::content";
    public static final String MPN_END = ":AT2:mfgPartNumberId::content";
    public static final String STATUS_END = ":AT2:soc1::content";
    public static final String OK_END = ":AT2:saveACBtn1";
    public static final String CANCEL_END = ":AT2:cancelBtn1";

    public static final String MANUFACTURER_PART_END = ":it1";
    public static final String MANUFACTURER_END = ":gl_redwood4";
    public static final String VIEW_COLUMNS_LIST_END = ":_clmns::ScrollBox";

    public static final String EXPORT_TO_EXCEL_END = ":AT2:_ATp:ATex::icon";
    public static final String FILTER_END = ":AT2:_ATp:_qbeTbr::icon";
    public static final String DETACH_END = ":AT2:_ATp:_dchTbr::icon";

    public static void createAML(WebDriver driver, Part part){

    }

    public static void selectAML(WebDriver driver, Part part){
        try {
            driver.findElement(By.cssSelector("*[id$='" + AMLTab.SELECT_AND_ADD_END + "']")).click();
        } catch (NoSuchElementException | ElementNotInteractableException e){
            driver.findElement(By.cssSelector("*[id$='" + AMLTab.EXPAND_OPTIONS_END + "']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + AMLTab.SELECT_AND_ADD_END + "']")).click();
        }

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ AMLTab.MN_END +"']")).sendKeys("AUTOMATION COMPONENTS INC" + Keys.TAB);
        Commons.sleepWait(2000);
        Commons.findElementByAttributeValue("title", "Search: Manufacturer Part Number").click();
        Commons.sleepWait();
        String partNumber = Commons.findElementByAttributeValue( driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MPN_DD_LIST_END +"']")), "_afrrk", "1").getText();
        Commons.findElementByAttributeValue( driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_MPN_DD_LIST_END +"']")), "_afrrk", "1").click();
        Commons.sleepWait();
        Select select = new Select(driver.findElement(By.cssSelector("*[id$='"+ AMLTab.STATUS_END +"']")));
        select.selectByIndex(2);
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ AMLTab.OK_END +"']")).click();
        Commons.sleepWait(3000);
        driver.findElement(By.cssSelector("*[id$='" + StructuresTab.SAVE_END + "']")).click();
        Commons.sleepWait(4000);
        Assert.assertEquals(partNumber , driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.RIGHT_PANE_AML_FIRST_ROW_END +"']")).getText());
    }

}
