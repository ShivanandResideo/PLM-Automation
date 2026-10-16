package utils;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.objects.Doc;
import webElements.EditDocumentPage;
import webElements.OverviewPage;

public class CreateDocs {

    Commons commons = new Commons();

    public static Doc createDoc(){
        Doc doc = new Doc();
        return createDoc(Commons.driver, doc);
    }

    public static Doc createDoc(WebDriver driver, Doc doc) {
        try {
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();
        } catch (Exception e) {
            Commons.home();

            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();

            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();
        }
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CREATE_DOCUMENT_END + "']")).click();

        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CLASS_END + "']")).clear();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CLASS_END + "']")).sendKeys(doc.className);
        Commons.sleepWait(1500);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.DD_FIRST_PRODUCT_END + "']")).click();

        Commons.takeScreenshot(driver, 3000);

        doc.description = Commons.userRandomString("Description");
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.DESCRIPTION_END + "']")).sendKeys(doc.description);

        //        Below ladder  is created because the DEV team made regular change without informing even Resideo Team (Amit and Vijay).
        try{
            new Select(Commons.findElementByLabel("Line of Business")).selectByVisibleText(doc.lineOfBusiness);
        }catch (Exception ignored){

        }
        try{
            new Select(Commons.findElementByLabel("Design Authority")).selectByVisibleText(doc.designAuthority);
        }catch (Exception ignored){

        }
        try{
            new Select(Commons.findElementByLabel("Custom/COTS")).selectByVisibleText(doc.customCOTS);
        }catch (Exception ignored){

        }
        try{
            new Select(Commons.findElementByLabel("Product Line")).selectByIndex(2);
        }catch (Exception ignored){

        }

        if (doc.className.equalsIgnoreCase("Specification")) {

            Select PGM = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.DGM_END + "']")));
            PGM.selectByIndex(doc.docGenMtd - 1);

            if (doc.docGenMtd == 2) {
                Select prefixSelect = new Select(Commons.findElementByLabel("2 Prefix (Same as related part)"));
                prefixSelect.selectByVisibleText(doc.relatedItem);
                Commons.findElementByLabel("2 Root Part / Family (Same as related part)").clear();
                Commons.findElementByLabel("2 Root Part / Family (Same as related part)").sendKeys(doc.rootPartFamily);
                Commons.findElementByLabel("2 Version (Same as related part)").clear();
                Commons.findElementByLabel("2 Version (Same as related part)").sendKeys(doc.version);
            } else if (doc.docGenMtd == 3) {
                Commons.findElementByLabel("3 Legacy Document Number").clear();
                Commons.findElementByLabel("3 Legacy Document Number").sendKeys(Commons.userRandomString("Part"));
            }
        } else if (doc.className.equalsIgnoreCase("Operational Documents") || doc.className.equalsIgnoreCase("System Documents")) {
            new Select(Commons.findElementByLabel("Sub Type")).selectByIndex(2);
        } else if (doc.className.equalsIgnoreCase("Quality Records")) {
            new Select(Commons.findElementByLabel("Subtype")).selectByIndex(2);
        }
        if (doc.specialCase.contains("cancel")) {
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CANCEL_END + "']")).click();
            return doc;
        }

        if (doc.className.equalsIgnoreCase("ERCV Document")
            || doc.className.equalsIgnoreCase("NPI Document")
             || doc.className.equalsIgnoreCase("Procedure")
              || doc.className.equalsIgnoreCase("Specification")) {

            //        Below ladder  is created because the DEV team made regular change without informing even Resideo Team (Amit and Vijay).
            try{
                new Select(Commons.findElementByLabel("Line of Business")).selectByVisibleText(doc.lineOfBusiness);
            }catch (Exception ignored){

            }
            try{
                new Select(Commons.findElementByLabel("Design Authority")).selectByVisibleText(doc.designAuthority);
            }catch (Exception ignored){

            }
            try{
                new Select(Commons.findElementByLabel("Custom/COTS")).selectByVisibleText(doc.customCOTS);
            }catch (Exception ignored){

            }
            try{
                new Select(Commons.findElementByLabel("Product Line")).selectByIndex(2);
            }catch (Exception ignored){

            }
        }
        Commons.takeScreenshot(driver);
        Commons.findElementByExactName("ave and Close").click();
        Commons.sleepWait(4000);

        doc.name = driver.findElement(By.cssSelector("*[id$='"+EditDocumentPage.NAME_END+"']")).getText();

//        if(doc.docGenMtd == 1 || doc.docGenMtd == 0){
//
//        }
//        if(doc.name.equalsIgnoreCase("") || doc.name.equalsIgnoreCase("NA"))
//            Assert.fail("Failed to create document. Please check");
        System.out.println("The document created is :"+doc.name);

        // save and close the document
        // Commons.findElementByExactName("ave and Close").click();
        // Commons.sleepWait(2000);
        return doc;
    }
}
