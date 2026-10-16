package utils.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import utils.Commons;
import webElements.CommonElements;

public class Type {

    public String type;
    public String className;
    public String name;
    public String description;
    public String PUOM;

    public int partGenMtd = 0;
    public int docGenMtd = 0;
    public String rootPartFamily;
    public String version;
    public String existingFamily;
    public String familyOption;
    public String familyVersion;
    public String legacyPartNumber;

    public String lineOfBusiness;
    public String designAuthority;
    public String customCOTS;

    public String prefix;
    public String specialCase;
    public String relatedItem;

    public static void reachTab(String tab) {
        try {
            if (tab.equalsIgnoreCase("relationship")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.RELATIONSHIP_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("team")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.TEAM_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("general information")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.GENERAL_INFORMATION_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("attachments")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.ATTACHMENTS_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("structure")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.STRUCTURE_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("aml")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.AML_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("quality")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.QUALITY_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("changes")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.CHANGES_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("item grade")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.ITEM_GRADE_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("where used")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.WHERE_USED_TAB_END + "']")).click();
            } else if (tab.equalsIgnoreCase("history")) {
                Commons.driver.findElement(By.cssSelector("*[id$='" + CommonElements.HISTORY_TAB_END + "']")).click();
            }
        }
        catch (NoSuchElementException e) {
            Commons.findElementByAttributeValue("title", tab).click();
        }
    }
}
