package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.objects.Part;
import webElements.OverviewPage;

public class CreateParts {

    public Part createPart(WebDriver driver, Part part) {

        // Commons.takeScreenshot(driver, 3000);

        // driver.findElement(By.cssSelector("*[id$='"+OverviewPage.PRODUCT_MANAGEMENT+"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.PRODUCT_DEVELOPMENT+"']")).click();

        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.TASK_LIST_END+"']")).click();
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.CREATE_PART_END+"']")).click();

        if(part.specialCase.equalsIgnoreCase("search manufacturer")){
            //   search for part.manufacturer
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_DD_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_DD_SEARCH_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_SEARCH_BOX_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_SEARCH_BOX_END+"']")).clear();
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_SEARCH_BOX_END+"']")).sendKeys(part.manufacturer.split(" ")[0]);
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_SEARCH_BOX_SEARCH_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_FIRST_SEARCH_RESULT_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_SEARCH_BOX_OK_END+"']")).click();
        }else{
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_END+"']")).clear();
            driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_END+"']")).sendKeys(part.manufacturer);
        }

        if(part.specialCase.equalsIgnoreCase("MPN special char")){
            part.MPN = part.MPN + Commons.userRandomString("Part");
        }
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.MPN_END + "']")).clear();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.MPN_END + "']")).sendKeys(part.MPN);

        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.DESCRIPTION_END +"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.DESCRIPTION_END +"']")).sendKeys(part.description);
        Commons.sleepWait(1000);
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_END+"']")).clear();
        driver.findElement(By.cssSelector("*[id$='"+OverviewPage.MANUFACTURER_END+"']")).sendKeys(part.manufacturer);

        Select status = new Select(driver.findElement(By.cssSelector("*[id$='"+OverviewPage.STATUS_END+"']")));
        if(part.status.equalsIgnoreCase("Empty")){
            status.selectByIndex(0);
        }else {
            status.selectByVisibleText(part.status);
        }

        // Commons.takeScreenshot(driver);
        driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.PART_SAVE_AND_CLOSE_END +"']")).click();

        return part;
    }
}