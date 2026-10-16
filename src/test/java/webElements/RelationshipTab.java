package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.Commons;

public class RelationshipTab {

    public static final String RELATIONSHIP_TAB_END = ":Detail6::icon";
    public static final String SAVE_BTN_END = ":AP4:APsv2";

    public static final String RELATIONSHIPS_TABLE_END = ":at1:_ATp:tab1::db";
    public static final String RELATIONSHIP_TYPE_END = ":soc_21::content";
//    id changed
//    public static final String ADD_RULE_END = ":gil1::icon";
    public static final String ADD_RULE_END = ":gil171::icon";
    public static final String ADD_RULE_OK_END = ":d3::ok";
    public static final String RULE_LIST_END = ":pgl161";
    public static final String REMOVE_RULE_END = ":at1:_ATp:tab1:0:cil1::icon";

    public static final String SELECT_AND_ADD_END = ":at1:_ATp:ctb2::icon";
    public static final String SELECT_AND_ADD_HEADING_END = ":d2::_ttxt";
    public static final String DELETE_END = ":at1:_ATp:ctb4::icon";

    public static final String ADD_RELATIONSHIPS_TEXT_END = ":s9:it1::content";
    public static final String ADD_RELATIONSHIPS_SEARCH_END = ":s9:cil3::icon";
    public static final String SELECT_AND_ADD_ICON_END = ":lv1:0:cil1::icon";
    public static final String ADD_RELATIONSHIPS_DONE_END = ":cb2";

    public static WebDriver driver = Commons.driver;

    public static void validateRelationshipTab(){
        driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_END +"']")).click(); // this button is expected to be disabled for Delta
        Commons.sleepWait(2000);
        try{
            String popUpTitle = driver.findElement(By.cssSelector("*[id$='"+ RelationshipTab.SELECT_AND_ADD_HEADING_END +"']")).getText();
            Assert.assertEquals("Add Relationships", popUpTitle);
        }catch (NoSuchElementException e){
            Assert.fail("'Select and Add' button is not enabled and hence throwing error"); // button is enabled and hence throwing error
        }
    }
}
