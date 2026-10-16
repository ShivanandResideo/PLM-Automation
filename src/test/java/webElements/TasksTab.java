package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import stepDefinitions.CreateCOSteps;
import utils.Commons;

import java.util.ArrayList;
import java.util.List;

public class TasksTab {

    public static final String ADD_END = ":tasksTable1:_ATp:create::icon";
    public static final String DELETE_END = ":tasksTable1:_ATp:delete::icon";

    public static final String TABLE_END = ":tasksTable1:_ATp:t1::db";

    // table > tbody > tr[]
    public static final String SEQUENCE_COLUMN_END = ":tasksTable1:_ATp:t1:0:it7::content";
    public static final String NAME_COLUMN_END = ":tasksTable1:_ATp:t1:0:it4::content";
    public static final String DESCRIPTION_COLUMN_END = ":tasksTable1:_ATp:t1:0:it11::content";
    public static final String DESCRIPTION_END = ":it11::content";
    public static final String REQUIRED_COLUMN_END = ":tasksTable1:_ATp:t1:0:sbc1::Label0";
    public static final String REQUIRED_COLUMN_TICK_END = ":tasksTable1:_ATp:t1:0:sbc1::content";
    public static final String REQUIRED_COLUMN_CHECK_END = ":sbc1::content";
    public static final String ASSIGNED_TO_COLUMN_END = ":tasksTable1:_ATp:t1:0:it14::content";
    public static final String ASSIGNED_TO_COLUMN_SEARCH_END = ":it14::lovIconId";
    public static final String START_BY_STATUS_COLUMN_END = ":tasksTable1:_ATp:t1:0:startafterstatus::content";
    public static final String COMPLETE_BY_STATUS_COLUMN_END = ":tasksTable1:_ATp:t1:0:soc2::content";
    public static final String NEED_BY_DATE_COLUMN_END = ":tasksTable1:_ATp:t1:0:id41::content";
    public static final String TASK_STATUS_COLUMN_END = ":tasksTable1:_ATp:t1:0:TaskStatusId::content";

    public static final String DELETE_POPUP_YES_END = ":tasksTable1:cb7";

//    ID changed
//    public static final String SAVE_POPUP_MSG_END = "_FOd1::msgDlg::contentContainer";
    public static final String SAVE_POPUP_OK_END = "::msgDlg::cancel";
    public static final String SAVE_POPUP_MSG_END = "::msgDlg::contentContainer";

    public static final String CHANGE_STATUS_POPUP_USERNAME_END = ":it14::_afrLovInternalQueryId:value00::content";
    public static final String CHANGE_STATUS_POPUP_SEARCH_END = ":it14::_afrLovInternalQueryId::search";
    public static final String CHANGE_STATUS_POPUP_RESULT_SECTION_END = ":resId2::db";
    public static final String CHANGE_STATUS_POPUP_OK_END = ":it14::lovDialogId::ok";
    public static final String CHANGE_STATUS_POPUP_DONE_END = ":AT1:FAsc4";
    public static final String CHANGE_STATUS_POPUP_SUBMIT_END = ":r4:0:cb1";
    public static final String CHANGE_STATUS_POPUP_CANCEL_END = ":r4:0:cb3";

    public static final String EXPORT_TO_EXCEL_END = ":tasksTable1:_ATp:ctb1::icon";
    public static final String FILTER_END = ":AT2:_ATp:_qbeTbr::icon";
    public static final String DETACH_END = ":AT2:_ATp:_dchTbr::icon";

    public static final String VIEW_COLUMNS_LIST_END = ":_clmns::ScrollBox";

    //    public static final String element = "";

    public static void requiredTest(){
        List<String> popUp = new ArrayList<>(3);
        List<String> noPopUp = new ArrayList<>(5);

        popUp.add("BLANK");
        popUp.add("Completed");
        if(CreateCOSteps.type.equalsIgnoreCase("CO")){
            popUp.add("Scheduled");
        }

        // the Audit got removed around 'June 23, 2025' release
        noPopUp.add("Audit");
        if(CreateCOSteps.type.equalsIgnoreCase("CO")) {
            noPopUp.add("Rework");
            noPopUp.add("Confirm");
            noPopUp.add("Update Documentation");
            noPopUp.add("Approve Documentation");
            noPopUp.add("Ready To Release");
        }
        else if(CreateCOSteps.subType.equalsIgnoreCase("Standard Change Request")){
            noPopUp.add("Screen");
            noPopUp.add("Test");
            noPopUp.add("Approve");
            noPopUp.add("Ready To Execute");
        }
        else if(CreateCOSteps.subType.equalsIgnoreCase("Standard Deviation")){
            noPopUp.add("Review");
            noPopUp.add("Authorized");
        }

        for(String status : popUp){
            Commons.tickAnother(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.REQUIRED_COLUMN_CHECK_END +"']")), Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.REQUIRED_COLUMN_END +"']")));

            if(status.equalsIgnoreCase("BLANK"))
                (new Select(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.START_BY_STATUS_COLUMN_END +"']")))).selectByIndex(0);
            else
                (new Select(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.START_BY_STATUS_COLUMN_END +"']")))).selectByVisibleText(status);

            Commons.driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
            Commons.sleepWait(2000);
            String errorMsg = Commons.driver.findElement(By.cssSelector("*[id$='"+ SAVE_POPUP_MSG_END +"']")).getText();
            Assert.assertTrue(errorMsg.contains("The task"));
            if(CreateCOSteps.type.equalsIgnoreCase("CO")){
                Assert.assertTrue(errorMsg.contains("can't be marked as required because Complete Before Status is set as blank, Prepare, Scheduled, or Completed. (EGO-2745823)"));
            }
            else if(CreateCOSteps.type.equalsIgnoreCase("CO")){
                Assert.assertTrue(errorMsg.contains("can't be marked as required because Complete Before Status is set as blank, Prepare, or Completed. (EGO-2745824)"));
            }
            Commons.driver.findElement(By.cssSelector("*[id$='"+ SAVE_POPUP_OK_END +"']")).click();
            Commons.sleepWait(2000);
        }

        for(String status : noPopUp){
            Commons.sleepWait(1000);
            Commons.tick(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.REQUIRED_COLUMN_END +"']")));
            Commons.sleepWait(1000);
            if(status.equalsIgnoreCase("BLANK"))
                (new Select(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.START_BY_STATUS_COLUMN_END +"']")))).selectByIndex(0);
            else
                (new Select(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.START_BY_STATUS_COLUMN_END +"']")))).selectByVisibleText(status);

            Commons.driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
            Commons.sleepWait(1000);
        }

        (new Select(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.START_BY_STATUS_COLUMN_END +"']")))).selectByIndex(0);
        Commons.sleepWait(1000);
        if(Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.REQUIRED_COLUMN_TICK_END +"']")).isSelected())
            Commons.driver.findElement(By.cssSelector("*[id$='"+ TasksTab.REQUIRED_COLUMN_END +"']")).click();

    }

    public static void selectUser(String user) {
        Commons.driver.findElement(By.cssSelector("*[id$='" + CHANGE_STATUS_POPUP_USERNAME_END + "']")).sendKeys(user);
        Commons.driver.findElement(By.cssSelector("*[id$='" + CHANGE_STATUS_POPUP_SEARCH_END + "']")).click();
        Commons.findElementByExactName(Commons.driver, user).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + CHANGE_STATUS_POPUP_OK_END + "']")).click();
        Commons.sleepWait(2000);
    }

    public static WebElement addTask(WebDriver driver){
        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TASKS_TAB_END +"']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ TasksTab.ADD_END +"']")).click();
        Commons.sleepWait(2000);

        int seqNum = Integer.parseInt(driver.findElement(By.cssSelector("*[id$='"+ TasksTab.SEQUENCE_COLUMN_END +"']")).getDomAttribute("value"));
        Assert.assertTrue(seqNum > 0);
        driver.findElement(By.cssSelector("*[id$='"+ TasksTab.NAME_COLUMN_END +"']")).sendKeys(Commons.userRandomString("part"));
        driver.findElement(By.cssSelector("*[id$='"+ TasksTab.DESCRIPTION_COLUMN_END +"']")).sendKeys(Commons.userRandomString("description"));

        return  driver.findElement(By.cssSelector("*[id$='" + TasksTab.TABLE_END + "'] > table > tbody > tr > td:nth-of-type(2)"));
    }
}