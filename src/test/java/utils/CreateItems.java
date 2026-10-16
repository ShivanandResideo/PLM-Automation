package utils;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.objects.Item;
import webElements.EditItemPage;
import webElements.OracleHome;
import webElements.OverviewPage;

import java.util.ArrayList;
import java.util.List;

public class CreateItems {

    private static int  puom = 1;
    //private static int

    public static Item createItem(){
        Item item = new Item();
        return createItem(Commons.driver, item);
    }

    public static Item createItem(WebDriver driver, Item item){
        try{
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();
        }
        catch (Exception e){
            driver.findElement(By.id(OracleHome.HOME)).click();
            Commons.sleepWait();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();
            Commons.sleepWait();
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();
        }

        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CREATE_ITEM_END + "']")).click();
        Commons.sleepWait();
        // pop up appears
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CLASS_END+"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CLASS_END+"']")).sendKeys(item.className);
        Commons.sleepWait(1000);
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.DD_FIRST_PRODUCT_END+"']")).click();

        Commons.takeScreenshot(driver, 3000);
        // Assert if name is rule generated

        item.description = Commons.userRandomString("Description");
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.DESCRIPTION_END +"']")).sendKeys(item.description);

//        Select PUOM = new Select(driver.findElement(By.cssSelector("*[id^='"+OverviewPage.PUOM_START+"'][id$='"+OverviewPage.PUOM_END+"']")));
//        PUOM.selectByValue(item.PUOM);
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.PUOM_END+"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.PUOM_END+"']")).sendKeys(item.PUOM);
//        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.DD_FIRST_PUOM_END+"']")).click();

        if(item.partGenMtd > 0){

            Select PGM = new Select(driver.findElement(By.cssSelector("*[id$='"+OverviewPage.PGM_END+"']")));
            PGM.selectByIndex(item.partGenMtd - 1);

            if(item.partGenMtd == 2){
                WebElement element = Commons.findElementByLabel("2 Existing Family");
                element.clear();
                element.sendKeys(item.existingFamily);
                element = Commons.findElementByLabel("2 Specify Family Option / Variant");
                element.clear();
                element.sendKeys(item.familyOption);
                element = Commons.findElementByLabel("2 Specify Family Version");
                element.clear();
                element.sendKeys("02");
            }
            else if(item.partGenMtd == 3){
                if(Commons.config.getProperty("ENV").equalsIgnoreCase("DEV")){
                    // Do we need to configure for different environments at this level - NO
                }
                WebElement legacy = Commons.findElementByLabel("3 Legacy Family Part Number");
                legacy.clear();
                item.legacyPartNumber = Commons.userRandomString("Part");
                legacy.sendKeys(item.legacyPartNumber);
            }
        }

//        Below ladder  is created because the DEV team made regular change without informing even Resideo Team (Amit and Vijay).
        try{
            new Select(Commons.findElementByLabel("Line of Business")).selectByVisibleText(item.lineOfBusiness);
        }catch (Exception ignored){

        }
        try{
            new Select(Commons.findElementByLabel("Design Authority")).selectByVisibleText(item.designAuthority);
        }catch (Exception ignored){

        }
        try{
            new Select(Commons.findElementByLabel("Custom/COTS")).selectByVisibleText(item.customCOTS);
        }catch (Exception ignored){

        }
        try{
            new Select(Commons.findElementByLabel("Product Line")).selectByIndex(2);
        }catch (Exception ignored){

        }

        // below try catch for SIT only
        try {
            if (item.className.equalsIgnoreCase("Subassembly")) {
                new Select(Commons.findElementByLabel("Contents")).selectByIndex(1);
            }
            if (item.className.equalsIgnoreCase("Label")) {
                new Select(Commons.findElementByLabel("Content")).selectByIndex(1);
            }
        }catch (Exception e){

        }

        if(item.className.equalsIgnoreCase("Product")) {
            Commons.findElementByLabel("Part Number").sendKeys(Commons.userRandomString("Part"));
        }

//        Should we exploit all the options available here
        if(item.className.equalsIgnoreCase("Packaging")) {
            try {
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PACKAGING_TYPE_END)).sendKeys("Bag");
            }
            catch (NoSuchElementException e){
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PACKAGING_TYPE_1_END)).sendKeys("Bag");
            }
        }

        if(item.specialCase.contains("cancel")){
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CANCEL_END+"']")).click();
            return item;
        }

        Commons.takeScreenshot(driver);
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.SAVE_AND_CLOSE_END+"']")).click();
        if(item.partGenMtd==1){
            item.name = "";
            int i=0;
            while(item.name.length() < 5 && i<5){
                Commons.sleepWait(3000);
                i++;
                item.name = driver.findElement(By.cssSelector("*[id$='"+EditItemPage.NAME_END + "']")).getText();
            }
            System.out.println("item name is "+item.name);
            String[] itemName = item.name.split("-");
            item.prefix = itemName[0];
            item.existingFamily = itemName[1];
            item.familyOption = itemName[2];
            item.familyVersion = itemName[3];
        }
        System.out.println("The item created is :"+item.name);
        return item;
    }

    // a function to verify the class specific attributes
    public void verifyEditItemPage(WebDriver driver, Item item){

        List excelList;
        List pageList;
        Commons commons = new Commons();
        List<String> columns = new ArrayList<>();
        if(item.className.equalsIgnoreCase("Crystal/Oscillator")){
            item.className = "CrystalOscillator";
        } else if(item.className.equalsIgnoreCase("Mfg Jig/Fixture/Tool")){
            item.className = "Mfg JigFixtureTool";
        } else if(item.className.equalsIgnoreCase("Wire/Conductor")){
            item.className = "WireConductor";
        }
        columns = commons.readFromExcel("EditItemPage",item.className, item.className, "column");
        for(String column: columns){
            if(column.equalsIgnoreCase("UOMF") || column.equalsIgnoreCase("")){
                // there might be a bug or change in design. need to clarify
                continue;
            }
            excelList = commons.readFromExcel("EditItemPage",item.className, column, "column");
            System.out.println("Trying to validate dropdown "+ EditItemPage.getClassAttributesID(item.className, column));
            Select selectObject = new Select(driver.findElement(By.cssSelector("*[id$='"+EditItemPage.getClassAttributesID(item.className, column)+"']")));
            pageList = commons.getVisibletextFromDropDown(selectObject);
            Assert.assertTrue(commons.compareList(pageList,excelList));

            // comparison logic is different for material substance class need to be added here
        }
    }
}