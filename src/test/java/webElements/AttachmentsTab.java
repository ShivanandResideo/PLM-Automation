package webElements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Commons;

public class AttachmentsTab {

    public static final String ATTACHMENTS_TAB_END = ":Detail2::icon";
    public static final String SAVE_BTN_END = ":AP4:APsv2";

    public static final String ADD_ATTACHMENT_ICON_END = ":a1:avgAdd::icon";
    public static final String DELETE_ATTACHMENT_ICON_END = ":a1:fatdDel::icon";
    public static final String ATTACHMENT_TYPE_END = ":a1:dciAdd:sfadd:socAddType::content";
    public static final String ATTACHMENT_CATEGORY_END = ":a1:dciAdd:sfadd:socAddCategory::content";
    public static final String ZIP_TICKBOX_END = ":a1:dciAdd:sfadd:sbcUnzip::Label0";
//    public static final String ZIP_TICKBOX_END = ":a1:dciAdd:sfadd:sbcUnzip::content";

    public static final String CHECK_OUT_END = ":a1:ctbChOut::icon";
    public static final String CHECK_IN_END = ":a1:ctbChIn::icon";
    public static final String CANCEL_CHECK_OUT_END = ":a1:ctbUnCO::icon";

    public static final String CHOOSE_FILE_END = ":a1:dciAdd:sfadd:ifAddFile::if";
    public static final String ADD_ATTACHMENT_CLOSE_END = ":a1:dciAdd:cilCloseAdd";
    public static final String ADD_ATTACHMENT_ADD_END = ":a1:dciAdd:sfadd:cbAddInd";
    public static final String ADD_ATTACHMENT_TEXT_END = ":a1:dciAdd:sfadd:rteAddText::content";
    public static final String ADD_ATTACHMENT_TITLE_END = ":a1:dciAdd:sfadd:itAddTitle::content";
    public static final String ADD_ATTACHMENT_LINK_END = ":a1:dciAdd:sfadd:itAddLink::content";
    public static final String ATTACHMENT_TABLE_END = ":a1:dciTable:tblAttach::db";
    // /table/tbody/tr will get all the elements of the table.
    public static final String FOLDER_DD_END = ":a1:dciAdd:sfadd:dciDcPc:bRefFf::popEl";
    public static final String FOLDER_DD_FIRST_END = ":a1:dciAdd:sfadd:dciDcPc:itFld:0:cmiFld";
    public static final String FOLDER_LIST_FIRST_END = ":a1:dciAdd:sfadd:dciDcPc:tRepFf:0:lFoName";
    public static final String DIALOG_YES_END = ":a1:confirm";
    public static final String NO_ATTACH_MESSAGE_END = ":a1:dciTable:tblAttach::db";

    public static final String TITLE_COLUMN_END = ":a1:dciTable:tblAttach:0:otTitle";
    public static final String TITLE_COLUMN_FULL_END = ":otTitle";
    public static final String CATEGORY_COLUMN_END = ":a1:dciTable:tblAttach:0:socCatName";
    public static final String RIGHT_PANE_BTN_END = ":a1:dc_ps1::i";
//    public static final String EXPAND_RIGHT_PANE_END = ":a1:dc_ps1::i";
    public static final String RIGHT_PANE_CLOSE_END = ":a1:dciDtl:cilCloseDtl";

    public static final String TABLE_ELEMENTS_MIDDLE = ":a1:dciTable:tblAttach:";
    // the "integer" after is the row in the table of the attached files
    public static final String FILE_DOWNLOAD_ICON_END = ":imgDwn";
    public static final String FILE_DELETE_ICON_END = ":cilDel::icon";
    public static final String LINK_END = ":glTitle";

    // right pane attributes
    public static final String FILE_NAME_END = ":a1:dciDtl:sfdtl:planFName";
    public static final String UPDATE_BTN_END = ":a1:dciDtl:sfdtl:cbDtlApp";
    public static final String UPDATE_END = ":a1:dciDtl:sfdtl:ifUpd::content";
    public static final String TITLE_END = ":a1:dciDtl:sfdtl:itTitle::content";
    public static final String DESCRIPTION_END = ":a1:dciDtl:sfdtl:itDesc::content";
    public static final String CATEGORY_END = ":a1:dciDtl:sfdtl:socCatName::content";
    public static final String SHARED_END = ":a1:dciDtl:sfdtl:sbcFleShrd::Label1";
    public static final String MORE_INFORMATION_END = ":a1:dciDtl:sfdtl:sdhMoreInfo::_afrTtxt";
    public static final String CLOSE_END = ":a1:dciDtl:cilCloseDtl";
    public static final String TEXT_END = ":a1:dciDtl:sfdtl:rteText::content";
    public static final String URL_END = ":a1:dciDtl:sfdtl:otUrl::content";

    public static final String MAIN_FILTER_END = ":a1:qryAFlt::criteriaItems::content";
    public static final String SUB_FILTER_MID = ":a1:qryAFlt:criterionValue";
    public static final String FILTER_END = ":a1:qryAFlt::search_icon";

    public static final String SORT_END = ":a1:socSortCol::content";
    public static final String SORT_ORDER_END = ":a1:ctbSortDir";
//    public static final String SORT_END = ""

    public static WebDriver driver = Commons.driver;

    public static void addNecessaryAttachments(String scenario){
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_ICON_END+"']")).click();
        Select typeDD = new Select(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ATTACHMENT_TYPE_END+"']")));
        Select category = new Select(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ATTACHMENT_CATEGORY_END+"']")));

        if(scenario.contains("Zip")){
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ZIP_TICKBOX_END+"']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.CHOOSE_FILE_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/zip.zip");
            Commons.sleepWait(2000);

        }else if(scenario.contains("File Document")){
            typeDD.selectByVisibleText("File");
            category.selectByVisibleText("Document");
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.CHOOSE_FILE_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/FileDocument.txt");
        }else if(scenario.contains("Repository Manufacturing")){
            category.selectByVisibleText("Manufacturing Files");
            typeDD.selectByVisibleText("Repository File/Folder");
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FOLDER_DD_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FOLDER_DD_FIRST_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FOLDER_LIST_FIRST_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_ADD_END+"']")).click();
        }else if(scenario.contains("Text Miscellaneous")){
            typeDD.selectByVisibleText("Text");
            category.selectByVisibleText("Miscellaneous");
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_TEXT_END+"']")).sendKeys(Commons.userRandomString("text"));
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_TITLE_END+"']")).sendKeys(Commons.userRandomString("part"));
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_ADD_END+"']")).click();
        }else if(scenario.contains("URL Source")){
            typeDD.selectByVisibleText("URL");
            category.selectByVisibleText("Source");
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_LINK_END+"']")).sendKeys("https://www.resideo.com/");
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_TITLE_END+"']")).sendKeys(Commons.userRandomString("part"));
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_ADD_END+"']")).click();
        }
//        driver.findElement(By.cssSelector("*[id$='"+EditItemAttachmentsPage.CATEGORY_COLUMN_END+"']"));
        Commons.takeScreenshot(driver, 5000);
        WebElement element = driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.ADD_ATTACHMENT_CLOSE_END+"']"));
        Commons.scrollToView(element);
        Commons.sleepWait(5000);
        element.click();
    }

    public static void verifyFilesAdded(String scenario){

        String before;
        if(scenario.contains("Zip")){
            Commons.sleepWait(5000);
            String[] fileNames = AttachmentsTab.TITLE_COLUMN_END.split("0");
            Assert.assertEquals("ZipFile3of3.txt", driver.findElement(By.cssSelector("*[id$='"+ fileNames[0] + "0" + fileNames[1] +"']")).getText());
            Assert.assertEquals("ZipFile2of3.txt", driver.findElement(By.cssSelector("*[id$='"+ fileNames[0] + "1" + fileNames[1] +"']")).getText());
            Assert.assertEquals("ZipFile1of3.txt", driver.findElement(By.cssSelector("*[id$='"+ fileNames[0] + "2" + fileNames[1] +"']")).getText());

        }
        else if(scenario.contains("File Document")){
//            WebElement table = driver.findElement(By.cssSelector("*[id$='"+ EditItemAttachmentsPage.ATTACHMENT_TABLE_END+"']"));
//            List<WebElement> files = table.findElements(By.xpath(".//table/tbody/tr"));
            Assert.assertTrue(scenario.contains(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.CATEGORY_COLUMN_END+"']")).getText()));

            // check if file can be downloaded
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FILE_DOWNLOAD_ICON_END+"']")).click();
            Assert.assertTrue(Commons.checkDownload(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_COLUMN_END+"']")).getText()));

            // check if right pane is relevant
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.RIGHT_PANE_BTN_END+"']")).click();

            // modifiable
            //Title
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_END+"']")), "title_After", "value","title");

            //Description // case 1
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DESCRIPTION_END+"']")), "Description_After", null,"title");
            //Category
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.CATEGORY_END+"']")), "", "","");

            //Shared
            Commons.assertModifiable("checkbox", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.SHARED_END+"']")), "", "","");

            // file name
            before = driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FILE_NAME_END+"']")).getText();
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.UPDATE_END+"']")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/AfterFileDocument.txt");
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.UPDATE_BTN_END+"']")).click();
            Commons.takeScreenshot(driver, 3000);
            Assert.assertFalse(before.equalsIgnoreCase(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FILE_NAME_END+"']")).getText()));
        }
        else if(scenario.contains("Repository Manufacturing")){
            Commons.sleepWait(3000);
            // check right pane
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.RIGHT_PANE_BTN_END+"']")).click();

            // modifiable
            //Title
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_END+"']")), "title_After", "value","title");
            // Description
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DESCRIPTION_END+"']")), "Description_After", null,"title");
            // category
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.CATEGORY_END+"']")), "", "","");
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.UPDATE_BTN_END+"']")).click();
            Commons.takeScreenshot(driver, 3000);

            // delete attachment
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FILE_DELETE_ICON_END+"']")).click();
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DIALOG_YES_END+"']")).click();
            Commons.sleepWait(3000);
            Assert.assertEquals("No data to display.", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.NO_ATTACH_MESSAGE_END+"']")).getText());
        }
        else if(scenario.contains("Text Miscellaneous")){
            Commons.sleepWait(3000);
            // check right pane
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.RIGHT_PANE_BTN_END +"']")).click();

            // modifiable
            //Title
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_END+"']")), "Title_After", "value","title");
            // Description // case 3
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DESCRIPTION_END+"']")), "Description_After", null,"title");
            // Category
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.CATEGORY_END+"']")), "", "","");
            // Text
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TEXT_END+"']")), "Text_After", null,"title");

            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.UPDATE_BTN_END+"']")).click();

            Commons.takeScreenshot(driver, 3000);
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.RIGHT_PANE_BTN_END+"']")).click();

            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.SAVE_BTN_END+"']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.FILE_DOWNLOAD_ICON_END+"']")).click();
            Commons.sleepWait(2000);
            Assert.assertTrue(Commons.checkDownload(driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_COLUMN_FULL_END+"']")).getText()));

        }
        else if(scenario.contains("URL Source")){
            Commons.sleepWait(3000);
            // check right pane
            driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.RIGHT_PANE_BTN_END +"']")).click();

            // modifiable
            //Title
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.TITLE_END+"']")), "Title_After", "value","title");
            // Description // case 4
            Commons.assertModifiable("text", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.DESCRIPTION_END+"']")), "Description_After", null,"title");
            // Category
            Commons.assertModifiable("dropdown", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.CATEGORY_END+"']")), "", "","");
            // URL
            Commons.assertModifiable("url", driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.URL_END+"']")), "Text_After", "value","title");

            Commons.sleepWait(3000);
            String tableLink = driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.LINK_END +"']")).getDomAttribute("href");
            String detailsLink = driver.findElement(By.cssSelector("*[id$='"+ AttachmentsTab.URL_END+"']")).getDomAttribute("value");
            // check URL is a href and equal to the before value
            Assert.assertTrue(tableLink.equalsIgnoreCase(detailsLink));

        }
    }
}