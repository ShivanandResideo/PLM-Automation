package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;

public class CommonElements {

    public static final String TOP_TAB_AREA_END = ":MAtbs::tabh::cbc"; // all tabs including overview is present here

    public static final String GENERAL_INFORMATION_TAB_END = ":Detail0::icon";
    public static final String AFFECTED_OBJECTS_TAB_END = ":AP2:sdi5::icon";
    public static final String IMPACT_ANALYSIS_END = ":AP2:sdi6::icon";
    public static final String TASKS_TAB_END = ":AP2:sdi9::icon";
    public static final String ATTACHMENTS_TAB_END = ":Detail2::icon";
    public static final String ATTACHMENTS_TAB_ICON_END = ":AP2:sdi2::icon";
    public static final String STRUCTURE_TAB_END = ":Detail9::icon";
    public static final String AML_TAB_END = ":Detail10::icon";
    public static final String CHANGES_TAB_END = ":Detail8::icon";
    public static final String QUALITY_TAB_END = ":Detail11::icon";
    public static final String RELATIONSHIP_TAB_END = ":Detail6::icon";
    public static final String TEAM_TAB_END = ":Detail3::icon";
    public static final String ITEM_GRADE_TAB_END = ":Detail5::icon";
    public static final String WHERE_USED_TAB_END = ":Detail1::icon";
    public static final String WORKFLOW_TAB_END = ":AP2:sdi4::icon";
    public static final String HISTORY_TAB_END = ":Detail12::icon";

    public static final String ACTIONS_END = ":ItemActions";
    public static final String ACTIONS_OPTIONS_END = ":AP2:ChangeActions::ScrollBox";
    public static final String CHANGE_ACTIONS_END = ":ChangeActions";
    public static final String CHANGE_STATUS_OPTIONS_END= ":AP2:changeStatusActionsdraft::ScrollBox";
    public static final String SAVE_AS_END = ":AP4:cmi8";

    public static final String DESC_END = ":it4::content";
    public static final String TICK_STRUCTURE_END = ":sbc2::Label0";
    public static final String TICK_ATTACHMENTS_END = ":sbc1::Label0";
    public static final String TICK_AML_END = ":sbc3::Label0";
    public static final String SELECT_PGM_END = ":f1_selectPartGenerationMethodItemEffBaseEO1_ATTRIBUTE_CHAR1::content";
    public static final String SAVE_AND_CLOSE_END = ":cb1";
    public static final String SAVE = ":APsv2";
    public static final String CANCEL_END = ":cb3";
    public static final String REVISION_END = ":AP4:soc007::content";

    public static final String POPUP_OK_END = "::msgDlg::cancel";

    public static final String TICK_CHANGE_ADDITIONAL_INFORMATION_END = ":sb5::Label0";
    public static final String TICK_CHANGE_ATTACHMENTS_END = ":sb2::Label0";
    public static final String TICK_CHANGE_RELATIONSHIPS_END = ":sb3::Label0";
    public static final String TICK_CHANGE_TASKS_END = ":sb6::Label0";
    public static final String TICK_CHANGE_AFFECTED_OBJECTS_END = ":sb1::Label0";
    public static final String TICK_CHANGE_DESCRIPTIVE_FLEXFIELDS_END = ":sb4::Label0";
    public static final String CHANGE_SAVE_AND_EDIT_END = ":s2:cb6";

    public static final String CHANGE_CANCEL_SUBMIT_END = ":AP2:cb15";

    public static final String TOP_SAVE_END = ":APsv2";
    public static final String TOP_SAVE_AND_CLOSE_END = ":APscl2";
    // id CHANGED
    public static final String TOP_CANCEL_END = ":SPc";

    public static final String EDIT_PAGE_FAVORITE_STAR_END = ":AP2:cil3";

    public static final String PLUS_CREATE_ICON_END = ":AT1:_ATp:create::icon";
    public static final String X_DELETE_ICON_END = ":AT1:_ATp:delete::icon";

    public static WebDriver driver = Commons.driver;
    public static String parent = null;

    // Save as
    public static void saveAs() {
        saveAs(true, true, true);
    }

    public static void saveAsDoc(boolean structure, boolean attachments) {
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.ACTIONS_END + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.SAVE_AS_END + "']")).click();

        driver.findElement(By.cssSelector("*[id$='" + CommonElements.DESC_END + "']")).sendKeys(Commons.userRandomString("Description"));
        // driver.findElement(By.cssSelector("*[id$='"+ CommonElements.SAVE_AS_END +"']")).click();

        if (structure) {
            Commons.tick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_STRUCTURE_END + "']")));
        } else {
            Commons.untick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_STRUCTURE_END + "']")));
        }

        if (attachments) {
            Commons.tick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_ATTACHMENTS_END + "']")));
        } else {
            Commons.untick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_ATTACHMENTS_END + "']")));
        }

        driver.findElement(By.cssSelector("*[id$='" + CommonElements.SAVE_AND_CLOSE_END + "']")).click();
    }

    public static void saveAs(boolean structure, boolean attachments, boolean AML) {
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.ACTIONS_END + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.SAVE_AS_END + "']")).click();

        driver.findElement(By.cssSelector("*[id$='" + CommonElements.DESC_END + "']")).sendKeys(Commons.userRandomString("Description"));
        // driver.findElement(By.cssSelector("*[id$='"+ CommonElements.SAVE_AS_END +"']")).click();

        if (structure) {
            Commons.tick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_STRUCTURE_END + "']")));
        } else {
            Commons.untick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_STRUCTURE_END + "']")));
        }

        if (attachments) {
            Commons.tick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_ATTACHMENTS_END + "']")));
        } else {
            Commons.untick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_ATTACHMENTS_END + "']")));
        }
        if (parent.equalsIgnoreCase("Items")) {
            if (AML) {
                Commons.tick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_AML_END + "']")));
            } else {
                Commons.untick(driver.findElement(By.cssSelector("*[id$='" + CommonElements.TICK_AML_END + "']")));
            }
        }

//        Select select = new Select(driver.findElement(By.cssSelector("*[id$='" + CommonElements.SELECT_PGM_END + "']")));
//        select.selectByIndex(1);

        driver.findElement(By.cssSelector("*[id$='" + CommonElements.SAVE_AND_CLOSE_END + "']")).click();
    }

    public static void verifySaveAs(boolean structure, boolean attachments, boolean AML) {
        String title;

        Commons.sleepWait();
        title = driver.findElement(By.cssSelector("*[id$='" + CommonElements.STRUCTURE_TAB_END + "']")).getDomAttribute("title");
        System.out.println(title);
        if (structure) {
            assert title != null;
            Assert.assertTrue(title.contains("Has Content"));
        } else {
            assert title != null;
            Assert.assertFalse(title.contains("Has Content"));
        }

        Commons.sleepWait();
        title = driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).getDomAttribute("title");
        System.out.println(title);
        if (attachments) {
            assert title != null;
            Assert.assertTrue(title.contains("Has Content"));
        } else {
            assert title != null;
            Assert.assertFalse(title.contains("Has Content"));
        }

        if (parent.equalsIgnoreCase("Items")) {
            Commons.sleepWait();
            title = driver.findElement(By.cssSelector("*[id$='" + CommonElements.AML_TAB_END + "']")).getDomAttribute("title");
            System.out.println(title);
            if (AML) {
                assert title != null;
                Assert.assertTrue(title.contains("Has Content"));
            } else {
                assert title != null;
                Assert.assertFalse(title.contains("Has Content"));
            }
        }
    }

    public static void searchEntityInStatus(String type, String subType, String status){
        if(type.equalsIgnoreCase("CO"))
            type = "Change Orders";
        else if (type.equalsIgnoreCase("CR"))
            type = "Change Requests";

        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
        Select select = new Select(driver.findElement(By.cssSelector("*[id$='" + CommonElements.AML_TAB_END + "']")));
        select.selectByVisibleText(type);
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, "Advanced").click();
        Commons.sleepWait(2000);

        if(driver.findElement(By.cssSelector("*[id$=':efqrp::_afrDscl']")).getDomAttribute("aria-expanded").equalsIgnoreCase("false")){
            driver.findElement(By.cssSelector("*[id$=':efqrp::_afrDscl']")).click();
            Commons.sleepWait(2000);
        }
        // configure search to get the correct change in the status

        System.out.println("Selecting already saved search for automation");
        select = new Select(driver.findElement(By.cssSelector("*[id$=':efqrp::saveSearch::content']")));
        select.selectByVisibleText("Automation");
        Commons.sleepWait(2000);
        if(subType.equalsIgnoreCase("Standard Change Order")){
            driver.findElement(By.cssSelector("*[id$=':efqrp:value00::content']")).sendKeys("SCO-");
        }
        else if(subType.equalsIgnoreCase("No Revision Update")){
            driver.findElement(By.cssSelector("*[id$=':efqrp:value00::content']")).sendKeys("NRU-");
        }
        else if(subType.equalsIgnoreCase("Standard Change Request")){
            driver.findElement(By.cssSelector("*[id$=':efqrp:value00::content']")).sendKeys("SCR-");
        }
        else if(subType.equalsIgnoreCase("Standard Deviation")){
            driver.findElement(By.cssSelector("*[id$=':efqrp:value00::content']")).sendKeys("SDV-");
        }


    }
}
