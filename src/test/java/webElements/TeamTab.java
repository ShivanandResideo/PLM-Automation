package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.Commons;

public class TeamTab {

    public static final String TEAM_TAB_END = ":Detail3::icon";
    public static final String SAVE_BTN_END = ":AP4:APsv2";

    public static WebDriver driver = Commons.driver;

    public static void validateTeamTab(){

        try{
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_END +"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_HEADING_END +"']")).getText();
            Assert.fail();
        }catch (NoSuchElementException e){
            Commons.softAssert.assertTrue(true);
        }

    }
}
