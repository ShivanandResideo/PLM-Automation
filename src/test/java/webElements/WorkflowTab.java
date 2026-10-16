package webElements;

import cucumber.runtime.junit.ScenarioOutlineRunner;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;

import java.util.ArrayList;
import java.util.List;

public class WorkflowTab {

    public static String WORKFLOW_STATUS_LIST_END = ":wc1:dc_pgl5"; // navigate to different status using this. IDs are not consistent to the status but their position is

    //  IDs of below have changed id changed
    public static String ALPHA_APPROVER_ERROR_POPUP_MSG_END = "::msgDlg::_cnt";
    public static String ALPHA_APPROVER_ERROR_POPUP_OK_END = "::msgDlg::cancel";

    public static String ADD_PARTICIPANTS_END = ":AT1:_ATp:create::icon";
    public static String ADD_PARTICIPANTS_POP_UP_END = ":AT1:p4::popup-container";
    public static String POP_UP_HEADER_END = ":AT1:d1::_ttxt";
    public static String DELETE_PARTICIPANTS_END = ":AT1:_ATp:delete::icon";
    public static String ACTIVITY_TYPE_END = ":soc2::content";
    public static String POP_UP_ACTIVITY_TYPE_END = ":soc1::content";

    public static String POP_UP_DELETE_USER_CONTINUE_END = ":drawarn::ok";
    public static String status_u = "";


    public static void validateParticipantsAddition(String status){
        status_u = status;
        System.out.println("Validating for status "+ status);
        Commons.sleepWait(3000);
        int i=0;
        while(i<23){
            try{
                Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(3000);
                if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                    Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
                    Commons.sleepWait(3000);
//                    Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
//                    Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                    break;
                }
            }
            catch (NoSuchElementException | ElementClickInterceptedException e){
                try {
                    Commons.sleepWait(2000);
                    if (Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_POPUP_YES_END + "']")).isDisplayed()) {
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_POPUP_YES_END + "']")).click();
                    }
                }
                catch (NoSuchElementException ignore){

                }
            }
            catch(StaleElementReferenceException ignored){
                System.out.println("Stale element found during refresh, so we have to retry");
            }
            Commons.sleepWait();
            i++;
        }
        // may not be required but having this for safety
        try {
            if (Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_POPUP_YES_END + "']")).isDisplayed()) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_POPUP_YES_END + "']")).click();
            }
        }
        catch (NoSuchElementException ignore){

        }
        Commons.sleepWait(5000);
        Commons.takeScreenshot(Commons.driver);
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ATTACHMENTS_TAB_END + "']")).click();
        Commons.sleepWait(5000);
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
        Commons.sleepWait(5000);
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.WORKFLOW_TAB_END + "']")).click();
        Commons.sleepWait(5000);

        try {
            Commons.findElementByExactName(Commons.driver.findElement(By.cssSelector("*[id*='" + WORKFLOW_STATUS_LIST_END + "']")), status).click();
        }
        catch (NoSuchElementException e) {
            Commons.driver.findElement(By.cssSelector("span[title='" + status + "']")).click();
        }

        List<String> users = new ArrayList<>();
        users.add("SV_Alpha_Automation");
        users.add("SV_Beta_Automation");
        users.add("SV_Charlie_Automation");
        users.add("SV_Delta_Automation");
        users.add("SV_Gamma_Automation");
        users.add("SV_Echo_Automation");
        for(String user : users) {
            System.out.println("Checking the user addition for user "+user);
            addParticipants(user);
            Commons.sleepWait(2000);
            validateActivityChange(user);
            Commons.sleepWait(2000);
            removeParticipants();
            Commons.sleepWait(2000);
        }
    }

    public static void validateParticipantsAdditionDraft(String status){
        status_u = status;
        System.out.println("Validating for status "+ status);

        Commons.sleepWait(5000);
        Commons.takeScreenshot(Commons.driver);
        // Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
//        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ATTACHMENTS_TAB_END + "']")).click();
//        Commons.sleepWait(5000);
//        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
//        Commons.sleepWait(5000);
//        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.WORKFLOW_TAB_END + "']")).click();
//        Commons.sleepWait(5000);

        try {
            Commons.findElementByExactName(Commons.driver.findElement(By.cssSelector("*[id*='" + WORKFLOW_STATUS_LIST_END + "']")), status).click();
        }
        catch (NoSuchElementException e) {
            Commons.driver.findElement(By.cssSelector("span[title='" + status + "']")).click();
        }

        Commons.sleepWait(2000);

        if(status.equalsIgnoreCase("draft")){
            //  adding and removing participants is not allowed in draft phase
            Commons.driver.findElement(By.cssSelector("*[id$='" + ADD_PARTICIPANTS_END + "']")).click();
            Commons.sleepWait(2000);
            try{
                Commons.driver.findElement(By.cssSelector("*[id$='" + ADD_PARTICIPANTS_POP_UP_END + "']"));
                Assert.fail("Add participant option should be not enabled in draft phase");
            }
            catch (NoSuchElementException ignored){

            }

//            if(Commons.driver.findElement(By.cssSelector("*[id$='" + DELETE_PARTICIPANTS_END+ "']")).isEnabled()){
//                Assert.fail("Remove participant option should be not enabled in draft phase");
//            }
            return;
        }

        List<String> users = new ArrayList<>();
        users.add("SV_Alpha_Automation");
        users.add("SV_Beta_Automation");
        users.add("SV_Charlie_Automation");
        users.add("SV_Delta_Automation");
        users.add("SV_Gamma_Automation");
        users.add("SV_Echo_Automation");
        for(String user : users) {
            System.out.println("Checking the user addition for user "+user);
            addParticipants(user);
            Commons.sleepWait(2000);
            validateActivityChange(user);
            Commons.sleepWait(2000);
            removeParticipants();
            Commons.sleepWait(2000);
        }
    }

    public static void validateActivityChange(String user) {

        if (status_u.equalsIgnoreCase("2 Audit") || status_u.equalsIgnoreCase("4 Confirm")
                || status_u.equalsIgnoreCase("5 Update Documentation") || status_u.equalsIgnoreCase("6 Approve Documentation")
                || status_u.equalsIgnoreCase("7 Ready To Release") || status_u.equalsIgnoreCase("3 Screen")
                || status_u.equalsIgnoreCase("4 Test") || status_u.equalsIgnoreCase("5 Approve")
                || status_u.equalsIgnoreCase("6 Ready To Execute") || status_u.equalsIgnoreCase("3 Review")
                || status_u.equalsIgnoreCase("4 Authorized")) {

            (new Select(Commons.driver.findElement(By.cssSelector("*[id$='" + ACTIVITY_TYPE_END + "']")))).selectByVisibleText("Review");
            Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
            Commons.sleepWait(2000);
            (new Select(Commons.driver.findElement(By.cssSelector("*[id$='" + ACTIVITY_TYPE_END + "']")))).selectByVisibleText("Approval");
            Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
            Commons.sleepWait(2000);
            if(user.equalsIgnoreCase("Alpha_Automation")){
                String errorMsg = "Approver Alpha_Automation doesn't have the privilege to approve a change. Remove this approver from the list and resubmit. (ACA-3600231)";
                Assert.assertEquals(errorMsg, Commons.driver.findElement(By.cssSelector("*[id$='" + ALPHA_APPROVER_ERROR_POPUP_MSG_END + "']")).getText());
                Commons.driver.findElement(By.cssSelector("*[id$='" + ALPHA_APPROVER_ERROR_POPUP_OK_END + "']")).click();
                Commons.sleepWait(2000);
            }

            try{
                (new Select(Commons.driver.findElement(By.cssSelector("*[id$='" + ACTIVITY_TYPE_END + "']")))).selectByVisibleText("Optional approval");
            }
            catch(NoSuchElementException e){
                if(status_u.equalsIgnoreCase("Audit")){
                    return;
                }
                else{
                    Assert.fail("We expected Optional Approval Activity type to be present in this stage but is not present. Please check");
                }
            }
            Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
            Commons.sleepWait(2000);
            if(user.equalsIgnoreCase("Alpha_Automation")){
                String errorMsg = "Approver Alpha_Automation doesn't have the privilege to approve a change. Remove this approver from the list and resubmit. (ACA-3600231)";
                Assert.assertEquals(errorMsg, Commons.driver.findElement(By.cssSelector("*[id$='" + ALPHA_APPROVER_ERROR_POPUP_MSG_END + "']")).getText());
                Commons.driver.findElement(By.cssSelector("*[id$='" + ALPHA_APPROVER_ERROR_POPUP_OK_END + "']")).click();
            }
        }
    }

    // to be called after the step where the '+' sign is visible in popup
    public static void addParticipants(String user) {
        for(WebElement create : Commons.driver.findElements(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_ADD_PARTICIPANTS_ICON_END + "']"))){
            try{
                create.click();
                break;
            }
            catch (Exception ignore){

            }
        }
        selectUser(user);
    }

    public static void selectUser(String user){
        Commons.sleepWait(6000);
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_USERNAME_END + "']")).clear();
        // below id changed label mapping is wrong
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_USERNAME_END + "']")).sendKeys(user);
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_SEARCH_END + "']")).click();
        Commons.findElementByExactName(Commons.driver, user).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_APPLY_END + "']")).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_DONE_END + "']")).click();
        try {
            Commons.sleepWait(2000);
//            (new Select(Commons.driver.findElement(By.cssSelector("*[id$='" + POP_UP_ACTIVITY_TYPE_END + "']")))).selectByVisibleText("Review");
            Commons.sleepWait(2000);
            Commons.driver.findElement(By.cssSelector("*[id*='" + EditChangePage.CHANGE_STATUS_POPUP_SUBMIT_END + "']")).click();
        } catch (Exception ignore) {

        }
        try{
            Commons.sleepWait(2000);
            Commons.driver.findElement(By.cssSelector("*[id*='" + EditChangePage.CHANGE_STATUS_POPUP_SUBMIT_END + "']")).click();
        }catch (Exception ignore) {

        }
        Commons.sleepWait(2000);
    }

    // to be called after the step where the 'x' sign is visible
    public static void removeParticipants() {
        Commons.driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).click();
        Commons.sleepWait(1500);
        Commons.driver.findElement(By.cssSelector("*[id$='" + DELETE_PARTICIPANTS_END + "']")).click();
        Commons.sleepWait(1500);
        try{
            Commons.driver.findElement(By.cssSelector("*[id$='" + POP_UP_DELETE_USER_CONTINUE_END + "']")).click();
            Commons.sleepWait(2000);
        }
        catch (NoSuchElementException ignore){

        }
    }

    // wrote code to add participants in popup but may not be required
    public static void addParticipantsPopUp(Boolean popUp, String user) {
        if(popUp){ // popup is a boolean signifying if the '+' sign is in popUp
            WebElement popUp1 = Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_END + "']"));
            popUp1.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_ADD_PARTICIPANTS_ICON_END + "']")).click();
        }
        else{
            Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_ADD_PARTICIPANTS_ICON_END + "']")).click();
        }

        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_USERNAME_END + "']")).sendKeys(user);
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_SEARCH_END + "']")).click();
        Commons.findElementByExactName(Commons.driver, user).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_APPLY_END + "']")).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_DONE_END + "']")).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.CHANGE_STATUS_POPUP_SUBMIT_END + "']")).click();
        Commons.sleepWait(2000);
    }

    // promote a change to a specific status with participants
    public static void changeStatus(String status, String participant){

        Commons.findElementByExactName("Change Status").click();
        Commons.sleepWait();

        try {
            Commons.findElementByExactName(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "']")), status).click();
        } catch (NoSuchElementException e) {
            Commons.findElementByExactName(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_1_END + "']")), status).click();
        }
        Commons.sleepWait();
        addParticipants(participant);
        Commons.findElementByExactName("Save").click();
    }

    // promote a change to a specific status without participants
    public static void changeStatus(String status){
        Commons.findElementByExactName("Change Status").click();
        Commons.sleepWait();
        Commons.findElementByExactName(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "']")) ,status).click();
        Commons.findElementByExactName("Save").click();
    }
}
