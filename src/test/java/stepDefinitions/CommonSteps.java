package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;
import utils.objects.Doc;
import utils.objects.Item;
import utils.objects.Type;
import webElements.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static webElements.AffectedObjectsTab.*;

public class CommonSteps {

    private static final Logger log = LoggerFactory.getLogger(CommonSteps.class);
    Commons commons = new Commons();
    static WebDriver driver = null;

    public static Item item;

    @Given("^upload results to JIRA$")
    public static void resultUpload(){
        if(Commons.config.getProperty("JIRA_UPLOAD").equalsIgnoreCase("true")){
            try{
                JIRAUtils.uploadResults("cucumber-results.xml");
            }
            catch(IOException | InterruptedException e){
                System.out.println("Exception occurred while uploading results to JIRA");
                e.printStackTrace();
            }
        }
        else{
            System.out.println("The flag in the config.properties to upload results to JIRA is set to false, hence skipping results upload.");
        }
    }

    @Given("^the (.*) user login is successful$")
    public void userLogin(String userType) {

        // checking the maven command line arguments in pom
//        String param = System.getProperty("parameter");
//        System.out.println(param);
//        String param1 = System.getProperty("parameter1");
//        System.out.println(param1);

        if (driver == null){
            driver = commons.setDriver();
            // load the necessary user credentials
            commons.setUser(userType);
            if(userType.equalsIgnoreCase("administrator")) commons.login();
            else Commons.SSOlogin();
        } else if (!(userType.equalsIgnoreCase(Commons.userType) || userType.equalsIgnoreCase(Commons.userName))){
            Commons.logout();
            commons.setUser(userType);
            if(userType.equalsIgnoreCase("administrator")) commons.login();
            else Commons.SSOlogin();
        }
        System.out.println("Login for " + userType + " is complete. UserName was " + Commons.userName + " and password was " + Commons.password+".");
    }

    @And("^user tries to create object of type (.*)$")
    public void createType(String type){
        if(type.equalsIgnoreCase("items")){
            Item item = new Item();
            CreateItems.createItem(Commons.driver, item);
            // Commons.findElementByExactName("ave and Close").click();
            System.out.println(item.name);
            Commons.objectType = type;
            Commons.objectNumber = item.name;
            Commons.objectDescription = item.description;
            System.out.println(item.description);
        }
        else if(type.equalsIgnoreCase("Documents")){
            Doc doc = new Doc();
            CreateDocs.createDoc(Commons.driver, doc);
            // Commons.findElementByExactName("ave and Close").click();
            System.out.println(doc.name);
            Commons.objectType = type;
            Commons.objectNumber = doc.name;
            Commons.objectDescription = doc.description;
            System.out.println(doc.description);
        }
    }

    @And("^close open tabs if any")
    public void closeTabs(){
        Commons.takeFullScreenshot();
        int i=0;
            while(i<10){
                i++;
                try {
                    Commons.findElementByExactName("ave and Close").click();
                    Commons.sleepWait();
                }
                catch (NoSuchElementException e){
                    return;
                }
                catch (Exception ignored){

                }
                // incase any pop-up occurs
                new Actions(driver).sendKeys(Keys.ESCAPE).build().perform();
                Commons.sleepWait();
                try {
                    Commons.findElementByExactName("ancel").click();
                    Commons.sleepWait();
                }
                catch (Exception ignored) {

                }
                try {
                    Commons.findElementByExactName("Yes").click();
                    Commons.sleepWait();
                }
                catch (Exception ignored) {

                }

            }
    }

    @And("^logout$")
    public void logout(){
        Commons.logout();
    }

    @When ("^test$")
    public void test(){
        WebElement element, lastElement;
        //load all elements

        List<WebElement> actualList = driver.findElement(By.cssSelector("*[class^='xem']")).findElements(By.xpath("./../tr"));
        try{
            for(int i = 9; ;i=i+9) {
                Commons.scrollToView(actualList.get(i));
                Commons.sleepWait(2000);
            }
        }catch(Exception ignore){

        }
        actualList = driver.findElement(By.cssSelector("*[class^='xem']")).findElements(By.xpath("./../tr"));
        for(int i=0; i< actualList.size();i++){
            element = actualList.get(i);
            if(element.findElement(By.xpath("./td")).getText().isEmpty()){
                Commons.scrollToView(actualList.get(i-1));
                Commons.sleepWait(2000);
            }
            System.out.println(element.findElement(By.xpath("./td")).getText());
        }

        System.out.println("Completed...");

//        CreateCOSteps test = new CreateCOSteps();
//        test.reachWorkflowStage("CO", "Standard Change Order", "Audit");
//        test.validateChangeWorkflow("Audit", "Affected Objects");


    }

    @When("^dropdown options$")
    public void testDD(){
        Select select = new Select(driver.findElement(By.cssSelector("select[id*='" + AttachmentsTab.SORT_END + "']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }

        select = new Select(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.C_CODE_END +"']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.J_CODE_END +"']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.FAST_TRACK_END +"']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.REG_IMP_END +"']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.CUS_IMP_END +"']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_REQ_END +"']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }
        select = new Select(driver.findElement(By.cssSelector("*[id$='"+ EditChangePage.TEST_RES_ATT_END +"']")));
        for (WebElement element : select.getOptions()){
            System.out.println(element.getText());
        }

    }

@And("testing")
        public void testing(){
        Commons.reachEditPage("Items", "300-00692-001-00");
        Commons.sleepWait(3000);
        Type.reachTab("structure");

        WebElement wParent = driver.findElement(By.cssSelector("*[id$='"+ ":ATT1:_ATTp::_ahCt" +"']"));
//        wParent.click();



        driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.CLIPBOARD_END +"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.RECENT_ITEMS_END +"']")).click();
        WebElement child = driver.findElement(By.cssSelector("*[id$='" + OverviewPage.RECENT_LIST_20_END +"']"));

//        child.click();
//
//        wParent.click();

        Commons.actions.dragAndDrop(child, wParent).build().perform();
        //(new Actions(driver)).dragAndDrop(child, wParent).build().perform();
        Commons.actions.dragAndDropBy(child, -1522, -505).build().perform();

        Commons.actions.dragAndDropBy(child, -1522, -505).perform();


        Actions builder = new Actions(driver);

        Action dragAndDrop = builder.moveToElement(child)
                .clickAndHold()
                .moveByOffset(-15, -5).build();

        Commons.sleepWait(10000);

        dragAndDrop.perform();

//                .release()
//                .build();

        dragAndDrop.perform();

        Commons.sleepWait(5000);



        // mouse actions
        (new Actions(driver)).moveToElement(child)
                        .clickAndHold()
                                .moveByOffset(-1522, -505)
                                        .release().build().perform();

        (new Actions(driver)).moveToElement(child)
                .clickAndHold()
                .moveByOffset(-1522, -505)
                .release().perform();



        Commons.assertPresent(child.getText().split(" ")[0]);


//        driver.findElement(By.cssSelector("*[id$='"+ StructuresTab.SAVE_END +"']")).click();

        Commons.assertPresent(child.getText().split(" ")[0]);
    }
}
