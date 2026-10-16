package utils;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.objects.Manufacturer;
import webElements.OverviewPage;

public class CreateManufacturers {

    public Manufacturer createManufacturer(WebDriver driver, Manufacturer manufacturer) {

        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_DEVELOPMENT + "']")).click();

        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.TASK_LIST_END + "']")).click();

        // the below block needs to be compiled in a different function so that It can be used frequently.

        try {
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.CREATE_MANUFACTURER_END + "']")).click();
            System.out.println("Element is present");
        } catch (NoSuchElementException e) {
            if(Commons.userName.contains("Gamma") || Commons.userName.contains("Echo")){
                Assert.fail("The create manufacturers option not available");
            }else{
                System.out.println("Element is not present as expected");
                Assert.assertTrue("The create manufacturers option not available as expected", true);
            }
        } finally {
            // rest of the sequence

            return manufacturer;
        }
    }
}
