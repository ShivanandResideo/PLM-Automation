package webElements;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;

public class EditFITPage {

    public static final String STEP_SECTION_END = "";

    public static final String ASSIGNEE_DROPDOWN_END = ":assignee::cntnrSpan";

    public static final String ASSIGNEE_RESULT_END = ":assignee_afrLovInternalTableId::db";

    // public static final String

    public static void addAssigneeToStepTasks(String step, String type, String searchType, String VD, String input) {
        WebElement section = Commons.findElementByExactName("Step " + step + " Tasks").findElement(By.xpath("./../../../../../../.."));
        section.findElement(By.cssSelector("*[id$='"+ CommonElements.PLUS_CREATE_ICON_END +"']")).click();
        Commons.sleepWait();
        new Select(Commons.findElementByLabel("Assignee Type")).selectByVisibleText(type);
        Commons.sleepWait();
        section = Commons.findElementByExactName("Step " + step + " Tasks").findElement(By.xpath("./../../../../../../.."));
        section.findElement(By.cssSelector("*[id$='"+ EditFITPage.ASSIGNEE_DROPDOWN_END +"']")).click();
        Commons.findElementByExactName("Search...").click();
        Commons.sleepWait();
        if(searchType.equalsIgnoreCase("Advanced")){
            Commons.findElementByExactName("vanced").click();
            new Select(Commons.findElementByLabel(VD + " Operator")).selectByVisibleText("Contains");
            Commons.findElementByLabel(VD).sendKeys(input + Keys.RETURN);
        }
        else if(searchType.equalsIgnoreCase("Basic")){
            Commons.findElementByLabel("All").findElement(By.xpath("./../label[1]")).click();
            Commons.findElementByLabel(" " + VD).sendKeys(input + Keys.RETURN); // search for all users with last name as automation
        }

        // select the person to add, then click OK
        Commons.driver.findElement(By.cssSelector("*[id$='"+ EditFITPage.ASSIGNEE_RESULT_END +"']")).click();
        Commons.sleepWait();
        Commons.findElementByExactName("OK").click();
        Commons.sleepWait();
        Commons.findElementByExactName("Save").click();
        Commons.sleepWait();
    }

    //incomplete
    public static void reassignToTasks(String step){
        WebElement section = Commons.findElementByExactName("Step " + step + " Tasks").findElement(By.xpath("./../../../../../../.."));
        Commons.findElementByAttributeValue("_afrrk", "4");
    }
}
