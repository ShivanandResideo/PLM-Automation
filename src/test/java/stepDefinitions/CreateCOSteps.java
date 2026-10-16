package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import utils.*;
import utils.objects.*;
import webElements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateCOSteps {
    Item[] items ;
    Doc[] docs ;
    public static Change[] CO = new Change[2];
    public static String type = null;
    public static String subType = null;
    public static String existingChange = null;
    WebDriver driver = Commons.driver;

    @Then("^Then all relevant (.*) functionalities are working as expected for (.*)$")
    public void validateCO(String type, String specialCase) {

        if(specialCase.equalsIgnoreCase("GeneralCase")){
            // validate general information
            CreateChange.validateGeneralInformation();

            // validate change attributes
            CreateChange.validateChangeAttributes();

            Commons.scrollToBottom();

            // validate design team
            CreateChange.validateDesignTeam();

            // validate impacted manufacturing sites
            CreateChange.validateIMS();

            if(type.equalsIgnoreCase("CO")) {
                // validate change implementation details
                CreateChange.validateCID();
            }
        }
        else if(specialCase.equalsIgnoreCase("COAffectedObjects")){

            AffectedObjectsTab.validateParameters();

// not required as it is already taken care in previous line
            // CreateChange.validateAffectedObjects(CO, items, docs);

            AffectedObjectsTab.removeObject(items[1]);
            AffectedObjectsTab.removeObject(docs[1]);

//            Need to verify
            AffectedObjectsTab.validateRedline(items[0], docs[0]);

//            Need to Verify
            CreateChange.validateStructureOfAffectedObject(items, docs);

            CreateChange.validateAttachmentOfAffectedObjects();

            CreateChange.validateRedLineSummary();

            CreateChange.removeObject(items, docs);
        }
        else if(specialCase.equalsIgnoreCase("AffectedObjects")){
            driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.AFFECTED_OBJECTS_TAB_END + "']")).click();
            AffectedObjectsTab.removeObject(null);
        }
        else if(specialCase.equalsIgnoreCase("Tasks")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.TASKS_TAB_END + "']")).click();
            CreateChange.validateTasks(CO, items, docs);
        }
        else if(specialCase.equalsIgnoreCase("Attachments")){
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_ICON_END + "']")).click();
            CreateChange.validateAttachments(CO, items, docs);
        }
        else if(specialCase.equalsIgnoreCase("Workflow") && Commons.userType.equalsIgnoreCase("charlie")){
            driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
            List<String> statusList = new ArrayList<>();

            if(this.subType.equalsIgnoreCase("Standard Change Request")){
                statusList.add("Draft");
                statusList.add("1 Prepare");
                statusList.add("2 Audit");
                statusList.add("3 Screen");
                statusList.add("4 Test");
                statusList.add("5 Approve");
                statusList.add("6 Ready To Execute");
                statusList.add("7 Completed");
            }
            else if(this.subType.equalsIgnoreCase("Standard Deviation")){
                statusList.add("Draft");
                statusList.add("1 Prepare");
                statusList.add("2 Audit");
                statusList.add("3 Review");
                statusList.add("4 Authorized");
                statusList.add("5 Completed");
            }

            for(String status : statusList) {
                // goto a status --> repeat for all status
                WorkflowTab.validateParticipantsAdditionDraft(status);
            }
//            CreateChange.validateWorkflow(CO, items, docs);
//            CreateChange.validateStatusChange(CO, items, docs);
        }
        else if(specialCase.equalsIgnoreCase("Workflow")){
            driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
            List<String> statusList = new ArrayList<>();
            if(this.type.equalsIgnoreCase("CO")) {
                // statusList.add("1 Prepare"); // not required as CO is on Audit stage
                // statusList.add("2 Audit"); // bug PLM-4332 raised
                statusList.add("3 Rework");
                statusList.add("4 Confirm");
                statusList.add("5 Update Documentation");
                statusList.add("6 Approve Documentation");
                statusList.add("7 Ready To Release");
                statusList.add("8 Scheduled");
                statusList.add("9 Completed");
            }
            else if(this.subType.equalsIgnoreCase("Standard Change Request")){
                // statusList.add("2 Audit"); // bug PLM-4332 raised
                statusList.add("3 Screen");
                statusList.add("4 Test");
                statusList.add("5 Approve");
                statusList.add("6 Ready To Execute");
                statusList.add("7 Completed");
            }
            else if(this.subType.equalsIgnoreCase("Standard Deviation")){
                // statusList.add("2 Audit"); // bug PLM-4332 raised
                statusList.add("3 Review");
                statusList.add("4 Authorized");
                statusList.add("5 Completed");
            }

            for(String status : statusList) {
                CreateChange.reachWorkflowStage(type,subType,status);
                WorkflowTab.validateParticipantsAddition(status);
            }
//            CreateChange.validateWorkflow(CO, items, docs);
//            CreateChange.validateStatusChange(CO, items, docs);
        }
        else if(specialCase.equalsIgnoreCase("Relationships")){
            driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.RELATIONSHIPS_TAB_END +"']")).click();

            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_END +"']")).click();
//                Hardcoded value. Can be changed if required
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_TEXT_END +"']")).sendKeys("SCO-000437");
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_SEARCH_END +"']")).click();
            Commons.sleepWait(4000);
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_ICON_END +"']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_DONE_END +"']")).click();

            // check if row added
            Assert.assertEquals(1, driver.findElements(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIPS_TABLE_END +"'] > table > tbody > tr")).size());

            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIP_TYPE_END +"']")), null, null, null);

            // add rule
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RULE_END +"']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RULE_OK_END +"']")).click();
            Commons.sleepWait(2000);
            // verify addition. will throe NoSuchElement if not added
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.RULE_LIST_END +"']"));
            // remove rule
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.REMOVE_RULE_END +"']")).click();
            Commons.sleepWait(2000);
            // verify removal
            int i=0;
            try {
                driver.findElement(By.cssSelector("*[id$='" + RelationshipTab.RULE_LIST_END + "']"));
            } catch (Exception e) {
                Assert.assertTrue("Rule list not found as expected", true);
                i++;
            }
            if(i==0){
                Assert.fail("Rule list is still present. It should have been removed.");
            }
            Commons.sleepWait(2000);
            // remove relationship record
            driver.findElement(By.cssSelector("*[id$='" + RelationshipTab.DELETE_END + "']")).click();
            Commons.sleepWait(2000);
            // assert the removal of the relationship
            Assert.assertEquals(0, driver.findElements(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIPS_TABLE_END +"'] > table > tbody > tr")).size());

        }
        else if(specialCase.equalsIgnoreCase("Actions")){
            // save as comes with option to add affected object or not
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(Commons.driver, "Save As").click();
            Commons.sleepWait(2000);
            Select select = new Select(driver.findElement(By.cssSelector("*[id$='"+OverviewPage.SEVERITY_END +"']")));
            select.selectByVisibleText(CO[0].severity);
            Commons.scrollToBottom();
            if(!CO[0].subType.equalsIgnoreCase("Standard Deviation"))
            {
                select = new Select(driver.findElement(By.cssSelector("*[id*='" + OverviewPage.C_CODE_END + "']")));
                select.selectByVisibleText(CO[0].categoryCode);
                select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.J_CODE_END + "']")));
                select.selectByVisibleText(CO[0].justificationCode);
            }
            else{
                select = new Select(driver.findElement(By.cssSelector("*[id*='" + OverviewPage.C_CODE_3_END + "']")));
                select.selectByVisibleText(CO[0].categoryCode);
                select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.D_TYPE_END + "']")));
                select.selectByVisibleText(CO[0].deviationType);
            }

            Commons.assertModifiable("checkbox", driver.findElement(By.cssSelector("*[id*='" + CommonElements.TICK_CHANGE_ADDITIONAL_INFORMATION_END + "']")), null, null, null);
            Commons.assertModifiable("checkbox", driver.findElement(By.cssSelector("*[id*='" + CommonElements.TICK_CHANGE_ATTACHMENTS_END + "']")), null, null, null);
            Commons.assertModifiable("checkbox", driver.findElement(By.cssSelector("*[id*='" + CommonElements.TICK_CHANGE_RELATIONSHIPS_END + "']")), null, null, null);
            Commons.assertModifiable("checkbox", driver.findElement(By.cssSelector("*[id*='" + CommonElements.TICK_CHANGE_TASKS_END + "']")), null, null, null);
            Commons.assertModifiable("checkbox", driver.findElement(By.cssSelector("*[id*='" + CommonElements.TICK_CHANGE_AFFECTED_OBJECTS_END + "']")), null, null, null);
            Commons.sleepWait(2000);
            Commons.assertModifiable("checkbox", driver.findElement(By.cssSelector("*[id*='" + CommonElements.TICK_CHANGE_DESCRIPTIVE_FLEXFIELDS_END + "']")), null, null, null);

            driver.findElement(By.cssSelector("*[id*='" + CommonElements.CHANGE_SAVE_AND_EDIT_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertNotEquals("checking if new SCR is created using saveAS",CO[0].number, driver.findElement(By.cssSelector("*[id*='" + EditChangePage.NUMBER_END + "']")).getText());
//            check again
            // all sections are copied in the save as

            // cancel change order
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
            Commons.sleepWait(2000);
            Commons.findElementByExactName(Commons.driver, "Cancel Change Order").click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_CANCEL_SUBMIT_END + "']")).click();
            Commons.sleepWait(2000);
            Assert.assertEquals("Canceled", driver.findElement(By.cssSelector("*[id*='" + EditChangePage.STATUS_END + "']")).getText());
        }
        else if(specialCase.equalsIgnoreCase("Status")){
            String change = CO[0].number;
            List<String> statusList = new ArrayList<>();

            // get a new driver to sign-in with gamma to approve the change to next status
            WebDriver driver1 = null;
            driver1 = Commons.setDriver(driver1);
            Commons commons = new Commons();
            commons.setUser("gamma");
            commons.login(driver1);

            driver1.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
            driver1.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();

            Commons.reachEditPage(driver1, type, change);

            if(this.subType.equalsIgnoreCase("Standard Change Request")){
                statusList.add("Draft");
                statusList.add("Prepare");
                statusList.add("Audit");
                statusList.add("Screen");
                statusList.add("Test");
                statusList.add("Approve");
                statusList.add("Ready To Execute");
                statusList.add("Completed");
            }
            else if(this.subType.equalsIgnoreCase("Standard Deviation")){
                statusList.add("Draft");
                statusList.add("Prepare");
                statusList.add("Audit");
                statusList.add("Review");
                statusList.add("Authorized");
                statusList.add("Completed");
            }

            for(int i=0; i<statusList.size(); i++){
                // goto a status --> repeat for all status
                String status = statusList.get(i);
                System.out.println("Checking tests for status "+status);
                // change below status list as and when required
//                if(status.equalsIgnoreCase("werty") || status.equalsIgnoreCase("qwerty")) {
                    while (true) {
                        try {
                            Commons.findElementByExactName(driver, "Change Status");
                            break;
                        } catch (Exception e) {
                            System.out.println("waiting for approval to get reflected.");
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                            Commons.sleepWait(5000);
                        }
                    }
//                }
                if(status.equalsIgnoreCase("Draft")){
                    Commons.findElementByExactName(Commons.driver, "Change Status").click();
                    Commons.sleepWait(2000);
                    List<WebElement> options = driver.findElement(By.cssSelector("*[id$='"+ CommonElements.CHANGE_STATUS_OPTIONS_END +"']")).findElements(By.xpath("./table/tbody/tr"));
                    Assert.assertEquals(2, options.size());
                    for(WebElement option : options){
                        if(!option.getText().equalsIgnoreCase("Prepare") && !option.getText().equalsIgnoreCase("Cancel")){
                            Assert.fail("Expected just 'Prepare' or 'Cancel' but found "+ option.getText());
                        }
                    }
                }
                if(status.equalsIgnoreCase("Audit")){
                    try{
                        CreateChange.changeStage(status);
                    } catch (Exception e) {
                        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.POPUP_OK_END +"']")).click();
                        Commons.sleepWait(1000);
                        Commons.driver.findElement(By.cssSelector("*[id*='" + EditChangePage.CHANGE_STATUS_POPUP_CANCEL_END + "']")).click();
                        CreateChange.editDoubleStar();
                        CreateChange.changeStage(status);
                    }
                }

                if(status.equalsIgnoreCase("Completed")) {
                    return;
                }
                CreateChange.changeStage(statusList.get(i+1));
                Commons.sleepWait(2000);
                if(!status.equalsIgnoreCase("Draft")) {
                    CreateChange.approveStageChange(driver1);
                }
                //Commons.findElementByExactName()
                System.out.println("Process for status " + status + " is completed.");
            }
        }

        Commons.sleepWait();
        Commons.findElementByExactName("ave and Close").click();

    }

    @And("^user tries to create (.*) Changes of type (.*) and sub type (.*) priority as (.*), severity as (.*), category code as (.*), justification code as (.*) for (.*)$")
    public void createCO(int CONum, String type, String subType, String priority, String severity, String categoryCode, String justificationCode, String specialCase) {

        this.type = type;
        this.subType = subType;
        // create 2 change orders of the same type
        for(int i=0; i<CONum; i++) {
            CO[i] = new Change(type, subType, "NA", "NA", priority, severity, categoryCode, justificationCode);
            CO[i] = CreateChange.createChange(CO[i]);
            System.out.println(CO[i].name);
        }

        if(specialCase.equalsIgnoreCase("COAffectedObjects")){
            createItemsAndDocsArray(3,3,specialCase);

            List<Type> AO = new ArrayList<>();
            AO.add(items[0]);
            AO.add(docs[0]);
            // reach the SCO before reaching the next step
            // Commons.reachEditPage("", "");
            AffectedObjectsTab.addObjects(AO);
        }

//        Not required, covered in next step definition
//        else if(specialCase.equalsIgnoreCase("AffectedObjets")){
//            driver.findElement(By.cssSelector("*[id$='" + AffectedObjectsTab.AFFECTED_OBJECTS_TAB_END + "']")).click();
//            AffectedObjectsTab.addObject(null);
//        }

    }

    @When("^user creates Change of type (.*) and subtype (.*) and reaches to (.*) stage$")
    public void reachWorkflowStage(String changeType, String subType, String stage){

        this.subType = subType;
        this.type = changeType;
         Change co = new Change(changeType, subType);
          co = CreateChange.createChange(co);
          CO[0] = co;
        if(stage.equalsIgnoreCase("Draft") || stage.equalsIgnoreCase("NA")){
            // if the stage is draft, nothing more is required
            return;
        }
        CreateChange.editDoubleStar();
        CreateChange.changeStage("Prepare");
        if(stage.equalsIgnoreCase("Prepare")){
            return;
        }

        // create an item
        Item affectedItem = CreateItems.createItem(Commons.driver, new Item());
        // add attachments
        driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ATTACHMENTS_TAB_END +"']")).click();
        AttachmentsTab.addNecessaryAttachments("Zip");
        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_AND_CLOSE_END +"']")).click();
        // add item to affected object
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();
        AffectedObjectsTab.addObject(affectedItem);
        Commons.sleepWait(3000);
        // add attachment to change
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ATTACHMENTS_TAB_END +"']")).click();
        AttachmentsTab.addNecessaryAttachments("Zip");

        // assign the Change to gamma
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
        Commons.sleepWait();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ASSIGNED_TO_END +"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ASSIGNED_TO_END +"']")).sendKeys("Gamma Automation");
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
        Commons.sleepWait(2000);

        if(!stage.equalsIgnoreCase("Completed") && !stage.equalsIgnoreCase("Scheduled")) {
            CreateChange.changeStage(stage);
        }
    }

    @Then("^user is to be able to see and edit relevant objects as per (.*) under tab (.*)$")
    public void validateChangeWorkflow(String stage, String tab){
        if(this.type==null) {
            this.type = "replace";
            this.subType = "replace";
        }

        if(stage.equalsIgnoreCase("Audit")){
            if(tab.equalsIgnoreCase("General Information")){ // complete
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();

                // name, description, requestedBy, assignedTo
//                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.NAME_END +"']")), "after", "value", "title");
//                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.DESCRIPTION_END +"']")), "after", null, "title");
//                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REQUESTED_BY_END +"']")), "Delta Automation", "value", "title");
//                String elementType = driver.findElement(By.cssSelector("*[id$='"+EditChangePage.ASSIGNED_TO_END+"']")).getAriaRole();
//                Assert.assertTrue(Commons.editable.contains(elementType));
//
//                // SCO change attributes
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SEVERITY_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SEVERITY_END +"']")), "Severity");
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.C_CODE_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.C_CODE_END +"']")), "Category Code");
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.J_CODE_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.J_CODE_END +"']")), "Justification Code");
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FAST_TRACK_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FAST_TRACK_END +"']")), "Fast Track");
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REG_IMP_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REG_IMP_END +"']")), "Regulatory Impact");
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.CUS_IMP_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.CUS_IMP_END +"']")), "Customer Impact");
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_REQ_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_REQ_END +"']")), "Testing Required?");
//                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_RES_ATT_END +"']")), null, null, null);
//                Commons.compareDropDown(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_RES_ATT_END +"']")), "Test Results Attached");
//                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.APN_END +"']")), "52323", "value", "title");
//                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SC_END +"']")), "Automation", null, "title");
//                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.CJ_END +"']")), "Automation", null, "title");

                if(type.equalsIgnoreCase("CO")) {
                    // validate change implementation details
                    Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ESV_END +"']")));
                    CreateChange.validateCID();
                }
                else{
                    Commons.scrollToView(Commons.findElementByExactName(driver, "Manufacturing Site"));
                }

                // validate design team
                CreateChange.validateDesignTeam();

                // validate impacted manufacturing sites
                CreateChange.validateIMS();

            }
            else if(tab.equalsIgnoreCase("Affected Objects")){ // complete
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();

                Commons.sleepWait(2000);
                // create an Item
                Item affectedItem = CreateItems.createItem(Commons.driver, new Item());
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_AND_CLOSE_END +"']")).click();
                // Add the new object to affected objects
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_BOX_END +"']")).sendKeys(affectedItem.name);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FIRST_ELEMENT_END +"']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();

                // assert if new item is added
                Commons.findElementByExactName(driver, affectedItem.name);

                // create a doc
                Doc affectedDoc = CreateDocs.createDoc(driver, new Doc());
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_AND_CLOSE_END +"']")).click();
                // Add the new object to affected objects
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_BOX_END +"']")).sendKeys(affectedDoc.name);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FIRST_ELEMENT_END +"']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();

                // assert if new doc is added
                Commons.findElementByExactName(driver, affectedDoc.name);

                // validate affected objects
                WebElement affTable = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.getAffectedTable(this.type) +"']"));
                List<WebElement> rows = affTable.findElements(By.xpath("./table/tbody/tr"));
                // check if all items and docs are added
                Assert.assertEquals(3, rows.size()); // one row was added before and 1 item and document was added now. Total 3 rows should be present

                Commons.sleepWait(2000);
                // to overcome stale element reference
                rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.getAffectedTable(this.type) +"']")).findElements(By.xpath("./table/tbody/tr"));
//                Already covered in Delta
                int i = 0;
                List<String> _afrrk = new ArrayList<>(3);
                for(WebElement element : rows){
                    _afrrk.add(element.getDomAttribute("_afrrk"));
                }

                for(String value : _afrrk){
                    WebElement element = driver.findElement(By.xpath("//*[@_afrrk='" + value + "']"));
                    Commons.sleepWait(2000);
                    if(this.type.equalsIgnoreCase("CO"))
                        CreateChange.verifyRowValues(element);
                    else if(this.type.equalsIgnoreCase("CR"))
                        CreateChange.additionalInformationModifiable(element);
                    else
                        Assert.fail("Change type is invalid. Check and run again");
                }

                // validate removal of objects
                int before = rows.size();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REMOVE_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REMOVE_OK +"']")).click();
                Commons.sleepWait(4000);
                int after = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.getAffectedTable(this.type) +"']")).findElements(By.xpath("./table/tbody/tr")).size();
                Assert.assertEquals(before-1, after);

//                Needs to verify
//                already covered in Delta
                // validateRedlines
//                AffectedObjectsTab.validateRedline(affectedItem, affectedDoc);
            }
            else if(tab.equalsIgnoreCase("Tasks")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TASKS_TAB_END +"']")).click();

                TasksTab.addTask(driver);

                int seqNum = Integer.parseInt(driver.findElement(By.cssSelector("*[id$='"+ TasksTab.SEQUENCE_COLUMN_END +"']")).getDomAttribute("value"));
                Assert.assertTrue(seqNum > 0);

                // Check combination of required and Complete_by_status
                TasksTab.requiredTest();
                Commons.sleepWait(3000);
                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ TasksTab.START_BY_STATUS_COLUMN_END +"']")), null, null, null);
//                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")), "Gamma_Automation", "title", "title");
//                driver.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_END +"']")).clear();
                driver.findElement(By.cssSelector("*[id$='"+ TasksTab.ASSIGNED_TO_COLUMN_SEARCH_END +"']")).click();
                TasksTab.selectUser("SV_Gamma_Automation");

                Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ TasksTab.NEED_BY_DATE_COLUMN_END +"']")), "date time future", null, "title");
                // Commons.assertNonModifiable(driver.findElement(By.cssSelector("*[id$='"+ TasksTab.TASK_STATUS_COLUMN_END +"']")), "Open");
                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ TasksTab.TASK_STATUS_COLUMN_END +"']")), null, null, null);

                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.SAVE +"']")).click();
                Commons.sleepWait(2000);
                (new Select(driver.findElement(By.cssSelector("*[id$='"+ TasksTab.TASK_STATUS_COLUMN_END +"']")))).selectByVisibleText("Open");
                int before = driver.findElements(By.cssSelector("*[id$='" + TasksTab.TABLE_END + "'] > table > tbody > tr")).size();
                driver.findElement(By.cssSelector("*[id$='"+ TasksTab.DELETE_END +"']")).click();
                Commons.sleepWait(4000);
                driver.findElement(By.cssSelector("*[id$='"+ TasksTab.DELETE_POPUP_YES_END +"']")).click();
                Commons.sleepWait(2000);
                int after = driver.findElements(By.cssSelector("*[id$='" + TasksTab.TABLE_END + "'] > table > tbody > tr")).size();

                Assert.assertEquals(before-1, after);
            }
            else if(tab.equalsIgnoreCase("Attachments")){ // complete
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ATTACHMENTS_TAB_END +"']")).click();
                int before, after;
                before = driver.findElements(By.cssSelector("*[id$='" + AttachmentsTab.ATTACHMENT_TABLE_END + "'] > table > tbody > tr")).size();
                driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_COLUMN_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DELETE_ATTACHMENT_ICON_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DIALOG_YES_END +"']")).click();
                Commons.sleepWait(2000);
                after = driver.findElements(By.cssSelector("*[id$='" + AttachmentsTab.ATTACHMENT_TABLE_END + "'] > table > tbody > tr")).size();
                Assert.assertEquals(before-1, after);
                AttachmentsTab.addNecessaryAttachments("Text Miscellaneous");
                Commons.sleepWait(2000);
                after = driver.findElements(By.cssSelector("*[id$='" + AttachmentsTab.ATTACHMENT_TABLE_END + "'] > table > tbody > tr")).size();
                Assert.assertEquals(before, after);
            }
            else if(tab.equalsIgnoreCase("Workflow")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                List<String> statusList = new ArrayList<>();
                if(this.type.equalsIgnoreCase("CO")) {
                    // statusList.add("1 Prepare"); // not required as CO is on Audit stage
                    // statusList.add("2 Audit"); // bug PLM-4332 raised
                    statusList.add("3 Rework");
                    statusList.add("4 Confirm");
                    statusList.add("5 Update Documentation");
                    statusList.add("6 Approve Documentation");
                    statusList.add("7 Ready To Release");
                    statusList.add("8 Scheduled");
                    statusList.add("9 Completed");
                }
                else if(this.subType.equalsIgnoreCase("Standard Change Request")){
                    // statusList.add("2 Audit"); // bug PLM-4332 raised
                    statusList.add("3 Screen");
                    statusList.add("4 Test");
                    statusList.add("5 Approve");
                    statusList.add("6 Ready To Execute");
                    statusList.add("7 Completed");
                }
                else if(this.subType.equalsIgnoreCase("Standard Deviation")){
                    // statusList.add("2 Audit"); // bug PLM-4332 raised
                    statusList.add("3 Review");
                    statusList.add("4 Authorized");
                    statusList.add("5 Completed");
                }

                for(String status : statusList) {
                    // goto a status --> repeat for all status
                    WorkflowTab.validateParticipantsAddition(status);
                }
            }
            else if(tab.equalsIgnoreCase("Relationships")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.RELATIONSHIPS_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_END +"']")).click();
                Commons.sleepWait(2000);
//                Hardcoded value. Can be changed if required
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_TEXT_END +"']")).sendKeys("SCO-000437");
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_SEARCH_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_ICON_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_DONE_END +"']")).click();

                Commons.sleepWait(2000);
                // check if row added
                Assert.assertEquals(1, driver.findElements(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIPS_TABLE_END +"'] > table > tbody > tr")).size());

                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIP_TYPE_END +"']")), null, null, null);

                // add rule
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RULE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RULE_OK_END +"']")).click();
                Commons.sleepWait(2000);
                // verify addition. will throw NoSuchElement if not added
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.RULE_LIST_END +"']"));
                // remove rule
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.REMOVE_RULE_END +"']")).click();
                Commons.sleepWait(2000);
                // verify removal
                try {
                    driver.findElement(By.cssSelector("*[id$='" + RelationshipTab.RULE_LIST_END + "']"));
                    Assert.fail("Rule list is still present. It should have been removed.");
                } catch (Exception e) {
                    Assert.assertTrue("Rule list not found as expected", true);
                }
                Commons.sleepWait(2000);
                // remove relationship record
                driver.findElement(By.cssSelector("*[id$='" + RelationshipTab.DELETE_END + "']")).click();
                Commons.sleepWait(2000);
                // assert the removal of the relationship
                Assert.assertEquals(0, driver.findElements(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIPS_TABLE_END +"'] > table > tbody > tr")).size());
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                // bug
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // Reject CO
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REJECT_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("textarea[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_REJECT_END + "']")).click();

                // move change to Prepare
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                // move change to Prepare
                Commons.sleepWait(2000);
                CreateChange.changeStage("Prepare");

                // move to hold and then back to prepare
                Commons.sleepWait(2000);
                CreateChange.changeStage("Hold");
                Commons.sleepWait(2000);
                CreateChange.changeStage("Continue");

                // move back to prepare will happen automatically after continue
                Commons.sleepWait(2000);
                CreateChange.changeStage("Audit");

                Commons.sleepWait(2000);
                // approve audit state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                // approve as rezi group
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(5000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(5000);

                // change to confirm state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                String expectedStage = "";
                if(this.type.equalsIgnoreCase("CO")) {
                    CreateChange.changeStage("Confirm");// failing. check if refresh is enough or approve is required.
                    expectedStage = "Confirm";
                }
                else if(this.subType.equalsIgnoreCase("Standard Change Request")) {
                    CreateChange.changeStage("Screen");// failing. check if refresh is enough or approve is required.
                    expectedStage = "Screen";
                }
                else if(this.subType.equalsIgnoreCase("Standard Deviation")) {
                    CreateChange.changeStage("Review");// failing. check if refresh is enough or approve is required.
                    expectedStage = "Review";
                }
                Commons.sleepWait(2000);
                Assert.assertEquals(expectedStage, CreateChange.getCurrentStage());

            }
            else{
                System.out.println("The tab name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Confirm")){
            if(tab.equalsIgnoreCase("Workflow")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                WorkflowTab.addParticipants("SV_Echo_Automation");
                Commons.sleepWait(2000);
                int before = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                WorkflowTab.removeParticipants();
                Commons.sleepWait();
                int after = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                Assert.assertEquals(before-1, after);

            }
            else if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();
                Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ESV_END +"']")));

                // validate design team
                CreateChange.validateDesignTeam();

                // validate impacted manufacturing sites
                CreateChange.validateIMS();

                if(type.equalsIgnoreCase("CO")) {
                    // validate change implementation details
                    CreateChange.validateCID();
                }

            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(5000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
                    }
                }

                // change to UpdateDocumentation state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                        System.out.println("out of 23 attempts this was attemp number "+ i);
                    }
                    Commons.sleepWait();
                    i++;
                }
                CreateChange.changeStage("Update Documentation");
                Commons.sleepWait(2000);
                Assert.assertEquals("Update Documentation", CreateChange.getCurrentStage());

            }
            else{
                System.out.println("The tab name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Update Documentation")){
            if(tab.equalsIgnoreCase("Workflow")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                WorkflowTab.addParticipants("SV_Echo_Automation");
                Commons.sleepWait(2000);
                int before = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                WorkflowTab.removeParticipants();
                int after = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                Assert.assertEquals(before-1, after);

            }
            else if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();
                Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ESV_END +"']")));

                // validate design team
                CreateChange.validateDesignTeam();

                // validate impacted manufacturing sites
                CreateChange.validateIMS();

                if(type.equalsIgnoreCase("CO")) {
                    // validate change implementation details
                    CreateChange.validateCID();
                }
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
                    }
                }

                // change to Approve Documentation state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(CreateChange.getCurrentStage().equalsIgnoreCase("Approve Documentation")){
                            break;
                        }
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                if(!CreateChange.getCurrentStage().equalsIgnoreCase("Approve Documentation")) {
                    CreateChange.changeStage("Approve Documentation");
                }
                Commons.sleepWait(2000);
                Assert.assertEquals("Approve Documentation", CreateChange.getCurrentStage());

            }
            else{
                System.out.println("The tab name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Approve Documentation")){
            if(tab.equalsIgnoreCase("Workflow")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                WorkflowTab.addParticipants("SV_Echo_Automation");
                Commons.sleepWait(2000);
                int before = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                WorkflowTab.removeParticipants();
                int after = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                Assert.assertEquals(before-1, after);

            }
            else if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();
                Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ESV_END +"']")));

                // validate design team
                CreateChange.validateDesignTeam();

                // validate impacted manufacturing sites
                CreateChange.validateIMS();

                if(type.equalsIgnoreCase("CO")) {
                    // validate change implementation details
                    CreateChange.validateCID();
                }
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
                    }
                }

                // change to Ready to Release state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                CreateChange.changeStage("Ready To Release");
                Commons.sleepWait(2000);
                Assert.assertEquals("Ready To Release", CreateChange.getCurrentStage());

            }
            else{
                System.out.println("The tab name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Ready To Release")){
            if(tab.equalsIgnoreCase("Workflow")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                WorkflowTab.addParticipants("SV_Echo_Automation");
                Commons.sleepWait(2000);
                int before = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                WorkflowTab.removeParticipants();
                int after = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                Commons.sleepWait(2000);
                Assert.assertEquals(before-1, after);
            }
            else if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();

                CreateChange.validateChangeAttributes();
                Commons.scrollToView(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ESV_END +"']")));

                // validate design team
                CreateChange.validateDesignTeam();

                // validate impacted manufacturing sites
                CreateChange.validateIMS();

                if(type.equalsIgnoreCase("CO")) {
                    // validate change implementation details
                    CreateChange.validateCID();
                }
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                //

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

//                // validate the change Status options to not have completed and scheduled
//                int i=0;
//                while(i<23){
//                    try{
//                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
//                        Commons.sleepWait(2000);
//                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
//                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
//                            Commons.sleepWait(2000);
//                            break;
//                        }
//                    }
//                    catch(Exception e){
//                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
//                    }
//                    Commons.sleepWait();
//                    i++;
//                }
//                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
//                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
//                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
//                    }
//                }

//                may not be required
                // change to Ready to Release state
//                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
//                CreateChange.changeStage("Ready To Release");
            }
            else{
                System.out.println("The tab name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Completed")){

        }
        else if(stage.equalsIgnoreCase("Scheduled")){

        }
        else if(stage.equalsIgnoreCase("Screen")){
            if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();
                CreateChange.validateDesignTeam();
                CreateChange.validateIMS();
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
                    }
                }

                // change to UpdateDocumentation state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                CreateChange.changeStage("Test");
                Commons.sleepWait(2000);
                Assert.assertEquals("Test", CreateChange.getCurrentStage());

            }
            else{
                System.out.println("The tab/actions name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Test")){
            if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();
                CreateChange.validateDesignTeam();
                CreateChange.validateIMS();
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                Commons.sleepWait(2000);
                Commons.tick(Commons.findElementByExactName(driver, "Individual"));
                Commons.sleepWait(2000);
                for (WebElement item : Commons.findElementsByExactName(driver, "Rezi PLM Change Analyst")){
                    try {
                        item.click();
                        break;
                    } catch (Exception ignored) {

                    }
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(3000);
                try {
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
                    Commons.sleepWait(2000);
                }
                catch (ElementClickInterceptedException e){
                    Commons.tick(Commons.findElementByExactName(driver, "Individual"));
                    Commons.sleepWait(2000);
                    driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                    Commons.sleepWait(3000);
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_END + "']")).click();
                    Commons.sleepWait(2000);
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
                    }
                }

                // change to next state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                CreateChange.changeStage("Approve");
                Commons.sleepWait(2000);

                Assert.assertEquals("Approve", CreateChange.getCurrentStage());
            }
            else{
                System.out.println("The tab/actions name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Approve")){
            if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();
                CreateChange.validateDesignTeam();
                CreateChange.validateIMS();
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
                    }
                }

                // change to next state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                CreateChange.changeStage("Ready To Execute");
                Commons.sleepWait(2000);
                Assert.assertEquals("Ready To Execute", CreateChange.getCurrentStage());

            }
            else{
                System.out.println("The tab/actions name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Ready To Execute")) {
            if(tab.equalsIgnoreCase("General Information")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.GENERAL_INFORMATION_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                CreateChange.validateChangeAttributes();
                CreateChange.validateDesignTeam();
                CreateChange.validateIMS();
            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(4000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                Commons.sleepWait();

                // wait for some time. The change is to move to completed automatically
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        Commons.findElementByExactName("Status Changed to Completed");
                        break;
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                Assert.assertEquals("Completed", CreateChange.getCurrentStage());
            }
        }
        else if(stage.equalsIgnoreCase("Review")){
            if(tab.equalsIgnoreCase("Workflow")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                WorkflowTab.addParticipants("SV_Echo_Automation");
                Commons.sleepWait(2000);
                int before = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                WorkflowTab.removeParticipants();
                Commons.sleepWait();
                int after = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                Assert.assertEquals(before-1, after);

            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(5000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                for(WebElement element : driver.findElements(By.cssSelector("*[id$='" + EditChangePage.TOP_CHANGE_STATUS_SCROLL_LIST_END + "'] > table > tbody > tr"))){
                    if(element.getText().equalsIgnoreCase("Scheduled") || element.getText().equalsIgnoreCase("Completed")){
                        Assert.fail("The option "+ element.getText() + " should not have been present. Hence failing the test.");
                    }
                }

                // change to UpdateDocumentation state
                i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.findElementByExactName(Commons.driver, "Change Status").isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                CreateChange.changeStage("Authorized");
                Commons.sleepWait(2000);
                Assert.assertEquals("Authorized", CreateChange.getCurrentStage());

            }
            else{
                System.out.println("The tab name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Authorized")){
            if(tab.equalsIgnoreCase("Workflow")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){

                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                WorkflowTab.addParticipants("SV_Echo_Automation");
                Commons.sleepWait(2000);
                int before = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                WorkflowTab.removeParticipants();
                Commons.sleepWait();
                int after = Commons.driver.findElements(By.cssSelector("*[id$='"+ EditChangePage.WORKFLOW_PARTICIPANTS_TABLE_END +"'] > table > tbody > tr")).size();
                Assert.assertEquals(before-1, after);

            }
            else if(tab.equalsIgnoreCase("Actions")){
                WebElement cancel = driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_CANCEL_END + "']"));
                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // cancel and terminate enabled
                Commons.scrollToViewReverse(driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")));
                driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                Commons.sleepWait(2000);
                try {
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
                catch (AssertionError | NoSuchElementException e){
                    driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGE_ACTIONS_END + "']")).click();
                    cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_CANCEL_CO_END + "']"));
                    Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());
                }
//                cancel = driver.findElement(By.cssSelector("*[id$='" + EditChangePage.ACTIONS_TERMINATE_CO_END + "']"));
//                Assert.assertTrue(cancel.isDisplayed() && cancel.isEnabled());

                // approve and change status
                // approve as individual
                int i=0;
                while(i<23){
                    try{
                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait(2000);
                        if(Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).isEnabled()){
                            driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_APPROVE_END + "']")).click();
                            Commons.sleepWait(2000);
                            break;
                        }
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.POP_UP_COMMENTS_END + "']")).sendKeys(Commons.userRandomString("Comment"));
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.APPROVE_END + "']")).click();
                Commons.sleepWait(5000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                Commons.sleepWait(5000);

                // validate the change Status options to not have completed and scheduled
                i=0;
                while(i<23){
                    try{

                        Commons.driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_REFRESH_END + "']")).click();
                        Commons.sleepWait();
                        Commons.findElementByExactName("Completed");
                        Commons.sleepWait(2000);
                        break;
                    }
                    catch(Exception e){
                        System.out.println("No Element or Stale element found during refresh, so we have to retry");
                    }
                    Commons.sleepWait();
                    i++;
                }

                Assert.assertEquals("Completed", CreateChange.getCurrentStage());
            }
            else{
                System.out.println("The tab name mentioned may not be existing. Please check...");
                Assert.fail();
            }
        }
        else if(stage.equalsIgnoreCase("Draft")) // FIT test and ADM
        {
            if(tab.equalsIgnoreCase("Affected Objects")){
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();

                Commons.sleepWait(2000);
                // create an Item
                Item affectedItem = CreateItems.createItem(Commons.driver, new Item());
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_AND_CLOSE_END +"']")).click();
                // Add the new object to affected objects
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_BOX_END +"']")).sendKeys(affectedItem.name);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FIRST_ELEMENT_END +"']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();

                // assert if new item is added
                Commons.findElementByExactName(driver, affectedItem.name);

                // create a doc
                Doc affectedDoc = CreateDocs.createDoc(driver, new Doc());
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_AND_CLOSE_END +"']")).click();
                // Add the new object to affected objects
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.AFFECTED_OBJECTS_TAB_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.SELECT_AND_ADD_BOX_END +"']")).sendKeys(affectedDoc.name);
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FIRST_ELEMENT_END +"']")).click();
                Commons.sleepWait(3000);
                driver.findElement(By.cssSelector("*[id$='"+ CommonElements.TOP_SAVE_END +"']")).click();

                // assert if new doc is added
                Commons.findElementByExactName(driver, affectedDoc.name);


                List<WebElement> rows = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.getAffectedTable(this.type) +"']")).findElements(By.xpath("./table/tbody/tr"));

                // validate removal of objects
                int before = rows.size();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REMOVE_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REMOVE_OK +"']")).click();
                Commons.sleepWait(4000);
                int after = driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.getAffectedTable(this.type) +"']")).findElements(By.xpath("./table/tbody/tr")).size();
                Assert.assertEquals(before-1, after);

            }
            else if(tab.equalsIgnoreCase("Attachments")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ATTACHMENTS_TAB_END +"']")).click();
                int before, after;
                before = driver.findElements(By.cssSelector("*[id$='" + AttachmentsTab.ATTACHMENT_TABLE_END + "'] > table > tbody > tr")).size();
                driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_COLUMN_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DELETE_ATTACHMENT_ICON_END +"']")).click();
                driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DIALOG_YES_END +"']")).click();
                Commons.sleepWait(2000);
                after = driver.findElements(By.cssSelector("*[id$='" + AttachmentsTab.ATTACHMENT_TABLE_END + "'] > table > tbody > tr")).size();
                Assert.assertEquals(before-1, after);
                AttachmentsTab.addNecessaryAttachments("Text Miscellaneous");
                Commons.sleepWait(2000);
                after = driver.findElements(By.cssSelector("*[id$='" + AttachmentsTab.ATTACHMENT_TABLE_END + "'] > table > tbody > tr")).size();
                Assert.assertEquals(before, after);
            }
            else if(tab.equalsIgnoreCase("Relationships")){
                driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.RELATIONSHIPS_TAB_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_END +"']")).click();
                Commons.sleepWait(2000);
//                Hardcoded value. Can be changed if required
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_TEXT_END +"']")).sendKeys("SCO-000437");
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_SEARCH_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_ICON_END +"']")).click();
                Commons.sleepWait(2000);
                driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.ADD_RELATIONSHIPS_DONE_END +"']")).click();

                Commons.sleepWait(2000);
                // check if row added
                Assert.assertEquals(1, driver.findElements(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIPS_TABLE_END +"'] > table > tbody > tr")).size());

                Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIP_TYPE_END +"']")), null, null, null);

                Commons.sleepWait(2000);
                // remove relationship record
                driver.findElement(By.cssSelector("*[id$='" + RelationshipTab.DELETE_END + "']")).click();
                Commons.sleepWait(2000);
                // assert the removal of the relationship
                Assert.assertEquals(0, driver.findElements(By.cssSelector("*[id$='"+ RelationshipTab.RELATIONSHIPS_TABLE_END +"'] > table > tbody > tr")).size());
            }
            else if(tab.equalsIgnoreCase("General Information")) {
                driver.findElement(By.cssSelector("*[id$='" + EditChangePage.GENERAL_INFORMATION_TAB_END + "']")).click();
                Commons.sleepWait(2000);
                WebElement section;

                // verify if the reason code is editable
                Commons.assertModifiable("dropdown", Commons.findInputElementByParameterName("** Reason Code"), "", "", "");
                Commons.findElementByExactName("Save").click();
                Commons.sleepWait(4000);

                if(CO[0].number.contains("FIT")) {
                    // verify if the options for factory is loaded properly and is editable
                    Commons.compareDropDown(Commons.findInputElementByParameterName("Factory Line"), "Factory Line");
                    Commons.assertModifiable("dropdown", Commons.findInputElementByParameterName("Factory Line"), "", "", "");
                    Commons.findElementByExactName("Save").click();

                    // check if the task can be added using last name(search result not appearing in SIT.
                    EditFITPage.addAssigneeToStepTasks("2", "Person", "Basic", "Value", "Aut");
                    // check if the task can be added using PID
                    EditFITPage.addAssigneeToStepTasks("2", "Person", "Basic", "Description", "236");
                    // check if the task can be added using first name and advanced search
                    EditFITPage.addAssigneeToStepTasks("2", "Person", "Advanced", "Value", "Gam");
                    // check if the task can be added using
                    EditFITPage.addAssigneeToStepTasks("2", "Person", "skip", "", "");
                    // to already existing task assign the task to another new person
                    section = Commons.findElementByExactName("Step 2 Tasks").findElement(By.xpath("./../../../../../../.."));
                    section.findElement(By.cssSelector("*[id$='" + EditFITPage.ASSIGNEE_DROPDOWN_END + "']")).click();
                    Commons.sleepWait();
                    Commons.findElementByAttributeValue(section, "_afrrk", "4");
                    Commons.sleepWait();
                    Commons.findElementByExactName("Save").click();

                    // check if the task can be added using
                    EditFITPage.addAssigneeToStepTasks("2", "Role", "skip", "", "");
                    // to already existing task assign the task to another new person
                    section = Commons.findElementByExactName("Step 2 Tasks").findElement(By.xpath("./../../../../../../.."));
                    section.findElement(By.cssSelector("*[id$='" + EditFITPage.ASSIGNEE_DROPDOWN_END + "']")).click();
                    Commons.sleepWait();
                    Commons.findElementByAttributeValue(section, "_afrrk", "4");
                    Commons.sleepWait();
                    Commons.findElementByExactName("Save").click();
                    // check if the task can be added using role name and advanced search
                    EditFITPage.addAssigneeToStepTasks("2", "Role", "Advanced", "Value", ""); // need input data


                    // to already existing task assign the task to another new Role

                    // to already existing task assign the task from person to role
                    // to already existing task assign the task from role to person

                    // remove tasks

                    // Task status drop down verification
                    Commons.compareDropDown(Commons.findInputElementByParameterName("Task Status"), "Task Status");
                    Commons.sleepWait(2000);
                    Commons.findElementByExactName("Save").click();
                    Commons.sleepWait(2000);
                    new Select(Commons.findElementByLabel("Task Status")).selectByVisibleText("Completed");
                    Commons.sleepWait(2000);
                    Commons.findElementByExactName("Save").click();
                    Commons.sleepWait(2000);
                    new Select(Commons.findInputElementByParameterName("Task Status")).selectByVisibleText("Not Required");
                }
                else if(CO[0].number.contains("ADU")) {
                    // check if the fields are editable

                    // verify if the priority is editable
                    Commons.assertModifiable("dropdown", Commons.findInputElementByParameterName("Priority"), "", "", "");
                    Commons.findElementByExactName("Save").click();
                    Commons.sleepWait(4000);

                    // verify if the name and other fields is modifiable
                    Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.NAME_END +"']")), "after", "value", "title");
                    Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.DESCRIPTION_END +"']")), "after", null, "title");
                    Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REQUESTED_BY_END +"']")), "Delta Automation", "value", "title");
                    Commons.findElementByLabel("Requested By").sendKeys("Gamma Automation");
                    String elementType = driver.findElement(By.cssSelector("*[id$='"+EditChangePage.ASSIGNED_TO_END+"']")).getAriaRole();
                    Assert.assertTrue(Commons.editable.contains(elementType));

                    // yet to check with JENN
                    CreateChange.validateCID();
//                    Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.PID_END +"']")), "date future", null, "title");
//                    Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.ESV_END +"']")), "number", null, "value");

                }
            }
        }
        else{
            System.out.println("The stage/status name "+stage+" mentioned may not be existing. Please check...");
            Assert.fail();
        }

        Commons.findElementByExactName("ave and Close").click();
        Commons.sleepWait(2000);
    }

    // the browser is to be in the edit page of the  item for this to work
    @And("^item is added to a change of type (.*) for (.*)$")
    public void addTypeToChange(String changeType, String specialCase){
        if(changeType.equalsIgnoreCase("SCO")){
            changeType = "Standard Change Order";
        }
        Commons.findElementByExactName("Actions").click();
        Commons.sleepWait();
        Commons.findElementByExactName("Assign to Change Order").click();
        Commons.sleepWait();
        if(specialCase.equalsIgnoreCase("existing")){
            new Select(Commons.findElementByLabel("Change Order")).selectByVisibleText("Search and Select");
            Commons.sleepWait();
            Commons.findElementsByLabel("Change Order").get(1).sendKeys(existingChange);
            Commons.sleepWait();
            Commons.findElementByExactName("O").click();
            Commons.sleepWait();
            return;
        }

        new Select(Commons.findElementByLabel("Change Type")).selectByVisibleText(changeType);
        Commons.sleepWait();
        Commons.findElementByLabel("Name").sendKeys(Commons.userRandomString(Commons.userType));
        Commons.findElementByLabel("Description").sendKeys(Commons.userRandomString(Commons.userType));
        new Select(Commons.findElementByLabel("Priority Code")).selectByIndex(1);
        Commons.sleepWait();
        // different code in different environments. hence using try catch.
        try {
            new Select(Commons.findElementByLabel("Category Code")).selectByIndex(1);
            Commons.sleepWait();
        }
        catch (Exception e){

        }
        Commons.findElementByExactName("ave and Edit").click();
        Commons.sleepWait(10000);

        for(int i=0; i<5; i++) {
            existingChange = Commons.findInputElementByParameterName("Number").getText();
            if(existingChange.contains("-"))
                break;
            Commons.sleepWait();
        }
        System.out.println("The SCO created was " + existingChange);

        // special cases at SCO level handling
        if(specialCase.equalsIgnoreCase("Reason Code NPI")){
            new Select(Commons.findInputElementByParameterName("** Reason Code")).selectByVisibleText("NPI Project");
        }
        else if(specialCase.equalsIgnoreCase("Reason Code not NPI")){
            new Select(Commons.findInputElementByParameterName("** Reason Code")).selectByVisibleText("Audit");
        }
        else if(specialCase.equalsIgnoreCase("Testing Required")){
            new Select(Commons.findInputElementByParameterName("** Testing Required?")).selectByVisibleText("Yes");
        }
        else if(specialCase.equalsIgnoreCase("First Release")){

        }
        else if(specialCase.equalsIgnoreCase("Update Release")){

        }
        else if(specialCase.equalsIgnoreCase("NA")){

        }
        else{
            System.out.println("The Special case do not match the expected cases. please check.");
            Assert.fail("Special case not matched");
        }

        Commons.sleepWait();
        Commons.findElementByExactName("Save").click();
        Commons.sleepWait();
    }

    @Then("^approvers (.*) added in stage (.*)$")
    public void validateApprovers(String approvers, String workflowStage){
        // assign approvers and come back to main tab
        String mainWindow = driver.getWindowHandle();
        try {
            Commons.findElementByExactName("Assign Approvers").click();
        }
        catch (Exception e){
            Commons.findElementByExactName("AssignApprovers").click();
        }
        Commons.sleepWait(20000);
        Set<String> allWindowHandles = driver.getWindowHandles();
        for(String newWindow : allWindowHandles){
            if(!newWindow.equalsIgnoreCase(mainWindow)){
                driver.switchTo().window(newWindow);
                driver.close();
                driver.switchTo().window(mainWindow);
                driver.navigate().refresh();
                Commons.sleepWait(5000);
                break;
            }
        }

        // get the expected approvers in the list format
        List<String> approverList = List.of(approvers.split(","));

        // wait for 30 seconds more for SIT - temporary
        Commons.sleepWait(20000);

        // validate if all the approvers are present in the mentioned stage
        driver.findElement(By.cssSelector("*[id$='" + CommonElements.WORKFLOW_TAB_END + "']")).click();
        if(workflowStage.equalsIgnoreCase("Confirm")){
            Commons.findElementByExactName("4 Confirm").click();
        }

        // check the number of approvers is same
//        approverList.size() |
//                Commons.findElementByAttributeValue()

        String currentApprover = approverList.get(0);
        if(currentApprover.isEmpty()){
            try{
                while(true){
                    Commons.findElementByExactName("ave and Close").click();
                    Commons.sleepWait();
                }
            }
            catch (NoSuchElementException e){
                return;
            }
        }
        try {
            // click each approver confirming that they exist in the approver list
            for (String approver : approverList) {
                currentApprover = approver;
                Commons.findElementByExactName(approver).click();
                Commons.sleepWait();
            }
        }
        catch (NoSuchElementException e){
            Assert.fail("'" + currentApprover + "' expected approver(s) missing. Hence failing the test.");
        }

        // close the tabs so that it does not interfere with the next tests
        try{
            while(true){
                Commons.findElementByExactName("ave and Close").click();
                Commons.sleepWait();
            }
        }
        catch (NoSuchElementException e){
            return;
        }
    }

    public void createItemsAndDocsArray(Integer iNum, Integer dNum, String specialCase){
        if (specialCase.contains("AffectedObjects")) {

            this.items = new Item[iNum];
            this.docs = new Doc[dNum];
            CreateItems createItem = new CreateItems();
            CreateDocs createDocs = new CreateDocs();

            for (int i = 0; i < iNum; i++) {
                this.items[i] = new Item();
                this.items[i] = CreateItems.createItem(this.driver, this.items[i]);
            }
            for (int i = 0; i < dNum; i++) {
                this.docs[i] = new Doc();
                this.docs[i] = CreateDocs.createDoc(this.driver, this.docs[i]);
            }

            if(specialCase.contains("CO")){
                // add 3 objects under each of the item and docs created...

                // reach edit page
                Commons.reachEditPage(items[0].type, items[0].name);
                Commons.sleepWait(2000);
                // reach the structures tab
                Type.reachTab("structure");
                Commons.sleepWait(2000);

                // add 2 objects to structure
                // one to delete, one to edit, one to replace
                StructuresTab.addNewChildObjects("item");
                Commons.sleepWait(2000);
                StructuresTab.addNewChildObjects("document");
                Commons.sleepWait(2000);
                StructuresTab.addNewChildObjects("item");
                Commons.sleepWait(3000);

                // add attachments
                Type.reachTab("attachments");
                AttachmentsTab.addNecessaryAttachments("Zip");
                Commons.sleepWait(3000);

                // add AML
                Type.reachTab("aml");
                AMLTab.selectAML(driver, new Part("101","AUTOMATION COMPONENTS INC", "Active", "NA", "NA"));
                Commons.sleepWait(3000);

                driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
                Commons.sleepWait(2000);
                // reach edit page
                Commons.reachEditPage(docs[0].type, docs[0].name);
                Commons.sleepWait(2000);
                // reach the structures tab
                Type.reachTab("structure");
                Commons.sleepWait(2000);

                // add 2 objects
                StructuresTab.addNewChildObjects("item");
                StructuresTab.addNewChildObjects("document");
                StructuresTab.addNewChildObjects("item");

                // add attachments
                Type.reachTab("attachments");
                AttachmentsTab.addNecessaryAttachments("Zip");

                driver.findElement(By.cssSelector("*[id$='" + CommonElements.TOP_SAVE_AND_CLOSE_END + "']")).click();
                Commons.sleepWait();
            }
        }
    }
}
