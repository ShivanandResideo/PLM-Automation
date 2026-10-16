package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Commons;
import utils.objects.Type;
import webElements.AttachmentsTab;

public class AttachmentSteps {

    Type object;
    WebDriver driver = Commons.driver;

    @And("^makes necessary changes as per the case (.*) for (.*)$")
    public void addAttachments(String specialCase, String type) {

        if(type.equalsIgnoreCase("item")){
            object = CreateClassSteps.item;
        }else if(type.equalsIgnoreCase("document")){
            object = CreateDocSteps.doc;
        }

        Commons.sleepWait();
        driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ATTACHMENTS_TAB_END+"']")).click();
        Commons.sleepWait();
        AttachmentsTab.addNecessaryAttachments(object.specialCase);
    }

    @Then("^attachment page is to be reached with all relevant functionalities")
    public void verifyAttachmentsTab() {
        // driver.manage().window().maximize();
        WebElement element;
        String before, after;
        after = Commons.userRandomString("After");

        AttachmentsTab.verifyFilesAdded(object.specialCase);

        driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.RIGHT_PANE_CLOSE_END+"']"));
    }
}
