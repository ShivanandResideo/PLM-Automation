package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Commons;

public class Notifications {

    public static final String DUMMY = "";

    public static final String ASSIGNED_TO_ME_TAB = ":up1:UPsp1:np1:ni1::disclosureAnchor";
    public static final String CREATED_BY_ME_TAB = ":up1:UPsp1:np1:ni2::disclosureAnchor";
    public static final String ALL_TAB = ":up1:UPsp1:np1:ni3::disclosureAnchor";

    public static final String ATM_TABLE = ":lv4::db";
    public static final String ATM_ELEMENTS = ":listItem2";
    public static final String ATM_ELEMENT_LINK = ":cl4";
    public static final String ALL_TABLE = ":lv1::db";
    public static final String ALL_ELEMENTS = ":litem2";
    public static final String DISMISS_ALL = ":cb4";
    public static final String CHANGE_LINK = ":cl4";

    public static final String TOP_NOTIFICATION_ICON = ":cil1::icon";

    public static final String POP_UP_CHANGE_LINK = "c21";

    public static int before_TTF = -1;
    public static int before_worklist = -1;
    public static int before_bell = -1;

    public static int after_TTF = -1;
    public static int after_worklist = -1;
    public static int after_bell = -1;

    public static String checkNotification(WebDriver driver, String notificationType){

        boolean TTF, bell, worklist;
        String expectedMessage = "";

        if(before_TTF<0) {
            Commons.sleepWait();
            before_TTF = Integer.parseInt(driver.findElement(By.cssSelector("*[id$='" + OracleHome.ASSIGNED_TO_ME_NUMBER + "'] > span")).getText());
        }

        if(before_bell<0) {
            Commons.sleepWait();
            if(driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_NUMBER_END + "']")).getText().isEmpty()){
                before_bell = 0;
            }
            else{
                before_bell = Integer.parseInt(driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_NUMBER_END + "']")).getText());
            }
        }

        Commons.home();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();
        Commons.sleepWait();

        if(before_worklist<0) {
            Commons.sleepWait();
            before_worklist = Integer.parseInt(Commons.findElementByAttributeValue("title", "Waiting for review").getText());
        }

        System.out.println("Waiting for 2 minutes to get the notification.");
        Commons.sleepWait(120000);
        System.out.println("Wait for 2 minutes complete.");

        driver.navigate().refresh();

        // -------------------------------------- after notification tests ----------------------------------------------------------
        // worklist
        after_worklist = Integer.parseInt(Commons.findElementByAttributeValue("title", "Waiting for review").getText());
        try{
            // Commons.findElementByExactName(Commons.objectNumber);
            worklist = after_worklist == before_worklist - 1;

            expectedMessage = expectedMessageOfNotification(notificationType);
            WebElement element = driver.findElement(By.cssSelector("*[id$='" + OverviewPage.WFR_ELEMENT_LINK_OPEN_NEW_END + "']"));
            worklist = worklist && (expectedMessage.equalsIgnoreCase(element.getText()));
            // check if the message is a link
            if(!element.getTagName().equalsIgnoreCase("a")) {
                System.out.println("The message is not a link");
                worklist = false;
            }
            // check if buttons are active
            element = driver.findElement(By.cssSelector("*[id$='" + OverviewPage.WFR_FIRST_ROW_END + "']"));
            if(notificationType.equalsIgnoreCase("Task")){
                worklist = worklist && Commons.findElementByExactName(element, "Dismiss").isEnabled();
            }
            else if(notificationType.equalsIgnoreCase("Approve")){
                worklist = worklist && Commons.findElementByExactName(element, "Approve").isEnabled();
                worklist = worklist && Commons.findElementByExactName(element, "Reject").isEnabled();
            }
        }
        catch (NoSuchElementException e){
            worklist = false;
        }

        Commons.home();
        Commons.sleepWait(4000);
        // TTF
        after_TTF = Integer.parseInt(driver.findElement(By.cssSelector("*[id$='" + OracleHome.ASSIGNED_TO_ME_NUMBER + "'] > span")).getText());
        try{
            Commons.findElementByExactName(Commons.objectNumber);
//            if(after_TTF != before_TTF-1){
//                TTF = false;
//            }
//            else {
//                TTF = true;
//            }
            TTF = (after_TTF == (before_TTF - 1));
            expectedMessage = expectedMessageOfNotification(notificationType);
            WebElement element = driver.findElement(By.cssSelector("*[id$='" + OracleHome.TTF_TILE_MESSAGES_END + "']"));
            TTF = TTF && (expectedMessage.equalsIgnoreCase(element.getText()));
            if(!element.getTagName().equalsIgnoreCase("a")) {
                System.out.println("The message is not a link");
                TTF = false;
            }
            // check if buttons are active
            element = driver.findElement(By.cssSelector("*[id$='" + OracleHome.TTF_TILE_BUTTONS_END + "']"));
            if(notificationType.equalsIgnoreCase("Task")){
                TTF = TTF && Commons.findElementByExactName(element, "Dismiss").isEnabled();
            }
            else if(notificationType.equalsIgnoreCase("Approve")){
                TTF = TTF && Commons.findElementByExactName(element, "Approve").isEnabled();
                TTF = TTF && Commons.findElementByExactName(element, "Reject").isEnabled();
            }
        }
        catch (NoSuchElementException e){
            TTF = false;
        }

        // bell
        Commons.sleepWait();
        if(driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_NUMBER_END + "']")).getText().isEmpty()){
            after_bell = 0;
        }
        else{
            after_bell = Integer.parseInt(driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_NUMBER_END + "']")).getText());
        }
        driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_BELL_END + "']")).click();
        Commons.sleepWait();
        try{
            Commons.findElementByExactName(driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_POPUP_AREA_END + "']")), Commons.objectNumber);
            bell = after_bell == before_bell - 1;
            expectedMessage = expectedMessageOfNotification(notificationType);
            WebElement element = driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_MESSAGES_END + "']"));
            bell = bell && (expectedMessage.equalsIgnoreCase(element.getText()));
            if(!element.getTagName().equalsIgnoreCase("a")) {
                System.out.println("The message is not a link");
                bell = false;
            }
            // check if buttons are active
            element = driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_TILE_BUTTONS_END + "']"));
            if(notificationType.equalsIgnoreCase("Task")){
                bell = bell &&Commons.findElementByExactName(element, "Dismiss").isEnabled();
            }
            else if(notificationType.equalsIgnoreCase("Approve")){
                bell = bell && Commons.findElementByExactName(element, "Approve").isEnabled();
                bell = bell && Commons.findElementByExactName(element, "Reject").isEnabled();
            }
        }
        catch (NoSuchElementException e){
            bell = false;
        }

        // resetting for next iteration
        before_bell = after_bell;
        before_TTF = after_TTF;
        before_worklist = after_worklist;

        if(bell && TTF && worklist)
            return "true";
        else {
            System.out.println("Notifications not present in below locations");
            String result = "";
            if(!bell){
                result = result.concat("bell ");
            }
            if(!TTF){
                result = result.concat("thingsToFinish ");
            }
            if(!worklist){
                result = result.concat("worklist");
            }
            System.out.println(result);
            return result;
        }
    }

    public static String expectedMessageOfNotification(String notificationType){
        String message = "";

        // only dismiss button
        // bell message for task "Task Assigned for Change Order SCO-00057"
        // TTF message for task "Task Assigned for Change Order SCO-00057"
        // worklist message for task "" -> no notification

        // Approve and reject button
        // bell message for approval "Change Order SCO-000778 Requires Approval"
        // TTF message for approval "Change Order SCO-000778 Requires Approval"
        // worklist message for approval "Change Request SDV-000136 Requires Approval"

        // Expected Message
        if (notificationType.equalsIgnoreCase("Task")) {
            if (Commons.objectType.equalsIgnoreCase("SCO") || Commons.objectType.equalsIgnoreCase("NRU")) {
                message = "Task Assigned for Change Order " + Commons.objectNumber;
            }
            else if (Commons.objectType.equalsIgnoreCase("SCR") || Commons.objectType.equalsIgnoreCase("SDV")) {
                message = "Task Assigned for Change Request " + Commons.objectNumber;
            }
        }
        else if(notificationType.equalsIgnoreCase("Approve")){
            if(Commons.objectType.equalsIgnoreCase("SCO") || Commons.objectType.equalsIgnoreCase("NRU")){
                message = "Change Order " + Commons.objectNumber + "Requires Approval";
            }
            else if(Commons.objectType.equalsIgnoreCase("SCR") || Commons.objectType.equalsIgnoreCase("SDV")){
                message = "Change Request " + Commons.objectNumber + "Requires Approval";
            }
        }

        return message;
    }

    // below function checked for the buttons on the notification to interact
    public static boolean interactNotification(String action, boolean verify){
        Commons.home();
        Commons.sleepWait();
        Commons.findElementByExactName(action).click();

//      need to check if there is a popup and code accordingly. currently throwing error

        if(!verify){
            return true;
        }

        // verify the decrease in the notifications after 2 minutes
        Commons.sleepWait(120000);
        Commons.driver.navigate().refresh();
        after_bell = Integer.parseInt(Commons.driver.findElement(By.cssSelector("*[id$='" + OracleHome.NOTIFICATION_NUMBER_END + "']")).getText());
        after_TTF = Integer.parseInt(Commons.driver.findElement(By.cssSelector("*[id$='" + OracleHome.ASSIGNED_TO_ME_NUMBER + "'] > span")).getText());
        Commons.home();
        Commons.driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        Commons.driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();
        Commons.sleepWait();
        after_worklist = Integer.parseInt(Commons.findElementByAttributeValue("title", "Waiting for review").getText());

        // assert
        boolean res = true;
        res = res && after_bell == before_bell; // notification in bell stays but with only dismiss button
        res = res && after_TTF == before_TTF - 1;
        res = res && after_worklist == before_worklist - 1;
        return res;
    }
}
