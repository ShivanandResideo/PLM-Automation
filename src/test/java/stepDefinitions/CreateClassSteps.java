package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import utils.Commons;
import utils.CreateItems;
import utils.objects.Item;
import utils.objects.Type;
import webElements.EditItemPage;

import java.util.List;

import static webElements.RelationshipTab.validateRelationshipTab;
import static webElements.TeamTab.validateTeamTab;

public class CreateClassSteps {

    Commons commons = new Commons();
    static WebDriver driver = Commons.driver;

    public static Item item;

    @When("^user tries to create (.*) item with PUOM as (.*) partGenMtd as (.*) existing family as (.*) Option as (.*) version as (.*) Legacy (.*) LOB as (.*) DA as (.*) CC as (.*) for (.*)$")
    public void createItem(String itemType, String PUOM, int partGenMtd, String EF, String FO, String FV, String legacy, String LOB, String DA, String CC, String specialCase) {
        // go to tasks
        commons.home();

        CreateItems tasksItems = new CreateItems();
        item = new Item(itemType, null, null, PUOM, partGenMtd, EF, FO, FV, legacy, LOB, DA, CC, specialCase);

        try{
            if(!(item.partGenMtd == 2)){
                tasksItems.createItem(driver, item);
            }else{
                item.partGenMtd = 1;
                item = tasksItems.createItem(driver, item);
                item.partGenMtd = 2;
                commons.home();
                tasksItems.createItem(driver, item);
            }
        }
        catch(NoSuchElementException e){
            Commons.takeScreenshot(driver);
            e.printStackTrace();
            Assert.fail("Some element which was expected is not found. Failing the test. Screenshot taken with file name"+ Commons.takeScreenshot(driver));

        }
    }

    @When("^user tries to create (.*) part with Design Team as (.*) and Design Approval Category as (.*)$")
    public void userTriesToCreateItem(String part, String DT,String DAC){
        Item item = new Item();
        item.className = part;
        // write code to decide the PGM using excel
        item.partGenMtd = (int) Double.parseDouble(Commons.readFromExcel("EditItemPage", "GeneralInformation", part, "row").get(2));
        item = CreateItems.createItem(driver, item);

        // set DT
        // Below implementation works on UAT as of 16 June 2026
        // different environments have different implementation. hence try catch.
        try {
            new Select(Commons.findInputElementByParameterName("Design Team")).selectByVisibleText(DT);
        } catch (UnexpectedTagNameException e){
            Commons.findElementByLabel("Design Team").clear();
            Commons.findElementByLabel("Design Team").sendKeys(DT + Keys.TAB);
        }
        // Below implementation works on SIT as of 16 June 2026
        EditItemPage.setDT(DT);

        // set DAC
        if(!DAC.equalsIgnoreCase("Component"))
            EditItemPage.setDAC(item, DAC);

        Commons.findElementByExactName("Save").click();
        Commons.sleepWait();
    }

    @Then("edit item page is to be reached with all relevant attributes")
    public void verifyEditItemPageAttributes() {

        Commons.takeScreenshot(driver);

        EditItemPage.generalInformation(driver, item);

        EditItemPage.commonPartAttributes(driver, item);

        EditItemPage.ERCVInformation(driver, item);

        if(item.className.equalsIgnoreCase("Packaging")){
            EditItemPage.ESGInformation(driver,item);
        }

        EditItemPage.legacyAttributes(driver, item);

        // modify parameters
        EditItemPage.modify(driver, item);

        // class specific attributes dropdowns
        // Disabled for now as the class specific attributes are not loaded completely yet
//        CreateItems validateItems = new CreateItems();
//
//        validateItems.verifyEditItemPage(driver, item);
//

        // approval matrix DAC
        System.out.println("verifying DAC...");
        EditItemPage.verifyDAC(item);

    }

    @Then("^edit item page is to be reached to verify Design Team dropdown options$")
    public void verifyDesignTeamDropdown() {
        //
        Commons.compareDropDown(Commons.findInputElementByParameterName("Design Team"), "Design team");
    }

    @And("^navigate to (.*) tab$")
    public void reachTab(String tab) {
        Type.reachTab(tab);
    }

    @Then("^(.*) tab is to be reached with all relevant attributes$")
    public void validateTabs(String tab) {

        if(tab.equalsIgnoreCase("relationship")){
            validateRelationshipTab();
        }else if(tab.equalsIgnoreCase("team")){
            validateTeamTab();
        }
    }

    @Then("^edit item page is to be reached to verify Design Team combination$")
    public void verifyDesignTeamChange() {
        // set the three drop-downs to a certain value from excel and then see what does Design team displays
        int i=1;
        boolean result = true;
        boolean failed = false;
        while(true){
            List<String> DT = Commons.readFromExcel("ApprovalMatrix", "DT", Integer.toString(i), "row");
            // if the end of list has reached, then break
            if(DT.size() < 4){
                break;
            }
            try{
                Commons.findElementByExactName("General Information");
            }
            catch(NoSuchElementException e){
                Commons.sleepWait(10000);
            }
            new Select(Commons.findElementByLabel("Line of Business")).selectByVisibleText(DT.get(1));
            new Select(Commons.findElementByLabel("Design Authority")).selectByVisibleText(DT.get(2));
            new Select(Commons.findElementByLabel("Product Line")).selectByVisibleText(DT.get(3));

            Commons.findElementByExactName("Save").click();
            Commons.sleepWait();

            // the below code is in SIT as of 10 June 2026
            String actualDT = Commons.findElementByLabel("Design Team").getDomAttribute("value");
            // the below code is in UAT as of 10 June 2026
            // String actualDT = new Select(Commons.findElementByLabel("Design Team")).getFirstSelectedOption().getText();

            System.out.println("Expected DT was "+ DT.get(4));
            System.out.println("Actual DT was "+ actualDT);

            result = result && actualDT.equalsIgnoreCase(DT.get(4));
            i++;
        }
        Assert.assertEquals(result,true);
    }
}
