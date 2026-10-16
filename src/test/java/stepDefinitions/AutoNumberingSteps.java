package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;

import java.util.ArrayList;
import java.util.List;

public class AutoNumberingSteps {

    @When("^reach the Manage Item Classes page$")
    public void reachMIC(){
        try{
            Commons.findElementByExactName(Commons.driver, "Manage Item Classes");
            return;
        }
        catch (Exception ignored){

        }
        Commons.driver.findElement(By.id("pt1:_UIScmil2u")).click();
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, "Setup and Maintenance").click();
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, "Items").click();
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, "Manage Item Classes").click();
        Commons.sleepWait(2000);
    }

    @Then("^make necessary changes to (.*) to Item Number Generation Method to (.*)$")
    public void config(String part, String set){
        System.out.println("Starting for part "+ part);
        Commons.driver.findElement(By.cssSelector("*[id$=':value00::content']")).clear();
        Commons.driver.findElement(By.cssSelector("*[id$=':value00::content']")).sendKeys(part);
        Commons.sleepWait(2000);
        Commons.driver.findElement(By.cssSelector("*[id$=':ipt1:ap1:q1::search']")).click();
        Commons.sleepWait(2000);
        Commons.driver.findElement(By.cssSelector("*[id$=':ipt1:ap1:applicationsTreeTable2:cb3']")).click();
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, part).click();
        System.out.println("Changing config for "+part);
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, "Item Management").click();
        Commons.sleepWait(2000);
        Select select = new Select(Commons.driver.findElement(By.cssSelector("*[id$=':NumberGenerationId::content']")));
        System.out.println("Setting value to "+set);
        select.selectByVisibleText(set);
        Commons.sleepWait(2000);
        if(set.equalsIgnoreCase("Rule Generated")){
            select = new Select(Commons.driver.findElement(By.cssSelector("*[id$=':pgt1:ap1:ICMgmt:1:soc2::content']")));
            String value = Commons.readFromExcel("AutoNumbering", "Sheet1", part, "row").getLast();
            System.out.println("Setting value to "+ value);
            select.selectByVisibleText(value);
            Commons.sleepWait(2000);
        }

        // save and close
        Commons.driver.findElement(By.cssSelector("*[id$=':pgt1:ap1:ctb1']")).click();
        Commons.sleepWait(2000);
        System.out.println("Completed for part " + part);
    }

    public void configHard(String part, String IMGM, String ARS){
        List<String> docs = new ArrayList<>();
        docs.add("ERCV Document");
        docs.add("NPI Document");
        docs.add("Procedure");
        docs.add("Specification");

        Commons.findElementByExactName(Commons.driver, "Resideo").findElement(By.xpath("./../../..")).findElement(By.tagName("span")).click();
        Commons.sleepWait(2000);
        if(docs.contains(part)){
            if(Commons.config.getProperty("ENV").equalsIgnoreCase("UAT")){
                Commons.findElementByExactName(Commons.driver, "Document Class").findElement(By.xpath("./../../..")).findElement(By.tagName("span")).click();
            }
            else{
                Commons.findElementByExactName(Commons.driver, "Documents").findElement(By.xpath("./../../..")).findElement(By.tagName("span")).click();
            }
        }
        else{
            Commons.findElementByExactName(Commons.driver, "Parts").findElement(By.xpath("./../../..")).findElement(By.tagName("span")).click();
        }
        Commons.sleepWait(4000);
        Commons.scrollTillFindElement(part).click();
        Commons.scrollToView(Commons.findElementByExactName(Commons.driver, part));
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, part).click();
        Commons.sleepWait(2000);
        Commons.findElementByExactName(Commons.driver, "Item Management").click();
        Commons.sleepWait(2000);
        Select select = new Select(Commons.driver.findElement(By.cssSelector("*[id$=':NumberGenerationId::content']")));
        select.selectByVisibleText(IMGM);
        Commons.sleepWait(2000);
        select = new Select(Commons.driver.findElement(By.cssSelector("*[id$=':pgt1:ap1:ICMgmt:1:soc2::content']")));
        select.selectByVisibleText(ARS);
        Commons.sleepWait(2000);

        // save and close
        Commons.driver.findElement(By.cssSelector("*[id$=':pgt1:ap1:ctb1']")).click();
        Commons.sleepWait(2000);
    }
}
