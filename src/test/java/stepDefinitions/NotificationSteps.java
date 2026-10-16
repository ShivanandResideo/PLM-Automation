package stepDefinitions;

import io.cucumber.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;
import utils.CreateChange;
import webElements.*;

import java.util.ArrayList;
import java.util.Arrays;

public class NotificationSteps {

    WebDriver driver = Commons.driver;

    // public static String tab = "";
    // public static String specialCase = "";
    public static String objectNumber = "";

    @And("^makes edits on (.*) tab in (.*) with respect to (.*) for (.*)$")
    public void makeEdits(String tab, String type, String userType, String specialCase) {
        // this.tab = tab;
        // this.specialCase = specialCase;

        if (specialCase.equalsIgnoreCase("Draft")) {
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }

//            WebElement column = row.findElements(By.className("xen")).get(4);
//            Commons.findElementByLabel(column, "Assigned To").sendKeys(user + Keys.TAB);

            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Current blank")) {
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }

            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();

            // change the status of the change to current state
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            WorkflowTab.changeStatus("Prepare", "SV_Gamma_Automation");
            Commons.sleepWait();

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Current")) {
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Prepare");

            // change the status of the change to current state
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            WorkflowTab.changeStatus("Prepare", "SV_Gamma_Automation");
            Commons.sleepWait();

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Future")) {
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Audit");

            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            WorkflowTab.changeStatus("Prepare", "SV_Gamma_Automation");
            Commons.sleepWait();

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Future Promoted")) {
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Audit");

            // promote the change to Audit
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            WorkflowTab.changeStatus("Prepare", "SV_Gamma_Automation");
            Commons.sleepWait();
            // make changes in general information to be able to promote to other status
            Commons.findElementByAttributeValue("title", "General Information").click();
            Commons.sleepWait(2000);
            CreateChange.editDoubleStar(type);
            // add one item to affected items list
            Commons.findElementByAttributeValue("title", "Affected Objects").click();
            Commons.sleepWait(2000);
            AffectedObjectsTab.addObject(null);
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            WorkflowTab.changeStatus("Audit", "SV_Gamma_Automation");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Audit")) {
            WorkflowTab.changeStatus("Prepare");
            Commons.sleepWait();
            // promote the change to Audit
            WorkflowTab.changeStatus("Audit", "SV_Beta_Automation");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
            objectNumber = Commons.objectNumber; // will be used in next step
        }
        else if (specialCase.equalsIgnoreCase("Audit Reject")) {
            Commons.reachEditPage("SCO", objectNumber);
            // or
            Commons.reachEditPage("SCO", Commons.objectNumber);

            // rejected change to be put to prepare and then to audit
            WorkflowTab.changeStatus("Prepare");
            Commons.sleepWait();
            // promote the change to Audit
            WorkflowTab.changeStatus("Audit", "SV_Beta_Automation");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Audit Promoted")) {
            WorkflowTab.changeStatus("Prepare");
            Commons.sleepWait();
            // promote the change to Audit
            WorkflowTab.changeStatus("Audit", "SV_Beta_Automation");

            // add a task to beta user
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Audit");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Confirm")) {
            WorkflowTab.changeStatus("Prepare");
            Commons.sleepWait();
            // promote the change to Audit
            WorkflowTab.changeStatus("Confirm", "SV_Beta_Automation");

            // add a task to beta user
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Confirm");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Update Documentation")) {
            WorkflowTab.changeStatus("Prepare");
            Commons.sleepWait();
            // promote the change to Audit
            WorkflowTab.changeStatus("Update Documentation", "SV_Beta_Automation");

            // add a task to beta user
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Update Documentation");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Approve Documentation")) {
            WorkflowTab.changeStatus("Prepare");
            Commons.sleepWait();
            // promote the change to Audit
            WorkflowTab.changeStatus("Approve Documentation", "SV_Beta_Automation");

            // add a task to beta user
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Approve Documentation");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
        else if (specialCase.equalsIgnoreCase("Ready to Release")) {
            WorkflowTab.changeStatus("Prepare");
            Commons.sleepWait();
            // promote the change to Audit
            WorkflowTab.changeStatus("Ready to Release", "SV_Beta_Automation");

            // add a task to beta user
            WebElement row = TasksTab.addTask(driver);
            String user = "";
            if (userType.equalsIgnoreCase("Beta")) {
                user = "Beta Automation";
            }
            row.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).sendKeys(user + Keys.TAB);
            Commons.sleepWait();
            Select select = new Select(Commons.findElementByLabel("Start-by Status"));
            select.selectByVisibleText("Ready to Release");

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();
        }
    }

    @And("^check for notifications and other features on (.*) for user (.*) for case (.*)$")
    public void userNotificationTest(String object, String userType, String specialCase){

        if(specialCase.equalsIgnoreCase("Draft")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("bell"));
            Assert.assertTrue(location.contains("thingsToFinish"));
            Assert.assertTrue(location.contains("worklist"));
        }
        else if(specialCase.equalsIgnoreCase("Current Blank")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));
        }
        else if(specialCase.equalsIgnoreCase("Current")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));
        }
        else if(specialCase.equalsIgnoreCase("Future")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations as expected
            Assert.assertFalse(location.contains("true"));
            Assert.assertTrue(location.contains("bell"));
            Assert.assertTrue(location.contains("thingsToFinish"));
            Assert.assertTrue(location.contains("worklist"));
        }
        else if(specialCase.equalsIgnoreCase("Future Promoted")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));
        }
        else if(specialCase.equalsIgnoreCase("Audit")){
            String result = Notifications.checkNotification(driver, "Approve");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // Reject change
            boolean res = Notifications.interactNotification("Reject", true);
            Assert.assertTrue("Checking if the notifications have reduced after the rejection of the change.", res);
        }
        else if(specialCase.equalsIgnoreCase("Audit Reject")){
            String result = Notifications.checkNotification(driver, "Approve");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // Approve change
            boolean res = Notifications.interactNotification("Approve", true);
            Assert.assertTrue("Checking if the notifications have reduced after the approval of the change.", res);
        }
        else if(specialCase.equalsIgnoreCase("Audit Promoted")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // check if the beta user can edit tasks tab
            Commons.reachEditPage("SCO", Commons.objectNumber);
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TASKS_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            // cannot add or remove tasks. Cannot edit the tasks

            // edit tasks pending
            // cannot save changes

            // not able to add or remove attachments
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).isEnabled());

            // not able to add participants
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + WorkflowTab.ADD_PARTICIPANTS_END + "']")).isEnabled());

            // edit relationships
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + RelationshipTab.ADD_RULE_END + "']")).isEnabled());

            // cannot save changes
            Assert.assertFalse(Commons.findElementByExactName("Save").isDisplayed());
            Assert.assertFalse(Commons.findElementByExactName("Save and Close").isDisplayed());
        }
        else if(specialCase.equalsIgnoreCase("Confirm")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // approval notification received
            result = Notifications.checkNotification(driver, "Task");
            location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // able to add tasks
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.ADD_END + "']")).isEnabled());

            // not able to remove tasks
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + TasksTab.DELETE_END + "']")).isEnabled());

            // able to add attachments
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).isEnabled());

            // able to add participants
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + WorkflowTab.ADD_PARTICIPANTS_END + "']")).isEnabled());

            // add relationships
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + RelationshipTab.ADD_RULE_END + "']")).isEnabled());

        }
        else if(specialCase.equalsIgnoreCase("Update Documentation")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // approval notification received
            result = Notifications.checkNotification(driver, "Task");
            location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // able to add tasks
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.ADD_END + "']")).isEnabled());

            // not able to remove tasks
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + TasksTab.DELETE_END + "']")).isEnabled());

            // able to add attachments
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).isEnabled());

            // able to add participants
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + WorkflowTab.ADD_PARTICIPANTS_END + "']")).isEnabled());

            // add relationships
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + RelationshipTab.ADD_RULE_END + "']")).isEnabled());

        }
        else if(specialCase.equalsIgnoreCase("Approve Documentation")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // check for approval notifications
            result = Notifications.checkNotification(driver, "Task");
            location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // able to add tasks
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + TasksTab.ADD_END + "']")).isEnabled());

            // not able to remove tasks
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.DELETE_END + "']")).isEnabled());

            // not able to add attachments
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).isEnabled());

            // able to add participants
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + WorkflowTab.ADD_PARTICIPANTS_END + "']")).isEnabled());

            // add relationships
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + RelationshipTab.ADD_RULE_END + "']")).isEnabled());

        }
        else if(specialCase.equalsIgnoreCase("Ready to Release")){
            String result = Notifications.checkNotification(driver, "Task");
            ArrayList<String> location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // check for approval notifications
            result = Notifications.checkNotification(driver, "Task");
            location = new ArrayList<>(Arrays.asList(result.split(" ")));
            // the notification was not present in the below locations
            Assert.assertTrue(location.contains("true"));

            // able to add tasks
            Assert.assertTrue(driver.findElement(By.cssSelector("*[id$='" + TasksTab.ADD_END + "']")).isEnabled());

            // not able to remove tasks
            Assert.assertFalse(driver.findElement(By.cssSelector("*[id$='" + TasksTab.DELETE_END + "']")).isEnabled());

            // not able to add attachments
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertFalse(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.ADD_ATTACHMENT_ICON_END + "']")).isEnabled());

            // able to add participants
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + WorkflowTab.ADD_PARTICIPANTS_END + "']")).isEnabled());

            // add relationships
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(driver.findElement(By.cssSelector("select[id*='" + RelationshipTab.ADD_RULE_END + "']")).isEnabled());
        }
    }
}
