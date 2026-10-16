package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import webElements.*;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Commons {

    public static WebDriver driver = null;
    public static Properties creds = new Properties();
    public static Properties config = new Properties();

    public static String userType, userName, password, JIRATestPlan;

    public static List editable = new ArrayList<>();

    public static SoftAssert softAssert = new SoftAssert();

    public static Actions actions;

    public static String env = config.getProperty("ENV");

    // to be used to store the object name/number/type for next case
    public static String objectNumber = null;
    public static String objectDescription = null;
    public static String objectType = null;

    public Commons() {

        try {
            creds.load(new FileReader(System.getProperty("user.dir") + "/src/test/resources/credentials.properties"));
            config.load(new FileReader(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
        } catch (IOException e) {
            System.out.println("The test data file may not exist. Please check");
            throw new RuntimeException(e);
        }
//        userName = creds.getProperty("userName1");
//        PASSWORD = creds.getProperty("password1");
    }

    public static boolean checkDownload(String fileName) {

        String fullFilename = "";
        Commons.takeScreenshot(driver, 3000);
        // Verify if the file has been downloaded
        File dir = new File(creds.getProperty("DWD_LOC"));
        File[] dirContents = dir.listFiles();

        boolean isDownloaded = false;
        for (File file : dirContents) {
            if (file.getName().contains(fileName)) {
                isDownloaded = true;
                fullFilename = file.getName();
                break;
            }
        }

        if (isDownloaded) {
            System.out.println("File has been downloaded successfully.");
            boolean deleted = false;
            for (File file : dirContents) {
                if (file.getName().contains(fileName)) { // delete all files which has file name
                    File toDelete = new File(creds.getProperty("DWD_LOC") + "/" + fullFilename);
                    toDelete.delete();
                    deleted = true;
                }
            }

            if (deleted) {
                System.out.println("File Deleted");
            } else {
                System.out.println("File not Deleted. Delete manually before running nect test");
            }
            return true;
        } else {
            System.out.println("File download failed.");
            return false;
        }
    }

    public static String selectDifferentOption(Select dropdown) {
        WebElement selectedOption = dropdown.getFirstSelectedOption();

        // Get all options in the dropdown
        List<WebElement> options = dropdown.getOptions();

        // Find the index of the selected option
        int selectedIndex = -1;
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).equals(selectedOption)) {
                selectedIndex = i;
                break;
            }
        }
        dropdown.selectByIndex((selectedIndex + 1) % dropdown.getOptions().size());
        sleepWait(2000);
        try {
            String send = dropdown.getFirstSelectedOption().getText();
            return send;
        } catch (StaleElementReferenceException e) {
            return null;
        }
    }

    public static void assertTagPresent(String text, String tag) {
        List<WebElement> list = driver.findElements(By.xpath("//" + tag + "[contains(text(),'" + text + "')]"));
        Assert.assertTrue("Element not found!", list.isEmpty());
//
//        driver.findElements(By.xpath("//div[contains(@title, 'Actions')]/h3"));
//
    }

    public static void assertPresent(String text) {
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
        Assert.assertTrue("Element not found!", list.isEmpty());
    }

    public static void assertNotPresent(String text) {
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
        Assert.assertFalse("Element not found!", list.isEmpty());
    }

//     for a certain beforeAttribute and afterAttribute a tab may be required... eg. value tab is not required and title tab is required

    //     We could press tab after each item adding new text and OR we could send save element also and click after each element
    public static void assertModifiable(String elementType, WebElement element, String random, String beforeAttribute, String afterAttribute) {
        String before, after, elementAfter;

        if (elementType.equalsIgnoreCase("text")) {
            if (beforeAttribute == null)
                before = element.getText();
            else
                before = element.getDomAttribute(beforeAttribute);

            if (before == null)
                before = "";
            if (random.isEmpty() || random == null || random.equalsIgnoreCase("")) {
                random = Commons.userRandomString("part");
            }

            if (random.equalsIgnoreCase("number"))
                after = "23";
            else if (random.equalsIgnoreCase("date past"))
                after = "06/23/2023";
            else if (random.equalsIgnoreCase("date time past"))
                after = "06/23/2023 12:00 AM";
            else if (random.equalsIgnoreCase("date future"))
                after = "06/23/2030";
            else if (random.equalsIgnoreCase("date time future"))
                after = "06/23/2030 12:00 AM";
            else if (random.equalsIgnoreCase("name"))
                after = "Shivanand K S";
            else
                after = random;
            Commons.sleepWait();
            element.clear();
            String id = element.getDomAttribute("id");
            element.sendKeys(after);
            Commons.sleepWait(2000);
            // to overcome stale element reference
            element = driver.findElement(By.id(id));
            if (afterAttribute == null)
                elementAfter = element.getText();
            else
                elementAfter = element.getDomProperty(afterAttribute);
            //Commons.takeFullScreenshot();

            try {
                Assert.assertFalse(before.equalsIgnoreCase(elementAfter));
                if (elementAfter.isEmpty())
                    throw new AssertionError();
            } catch (AssertionError e) {
                element.clear();
                element.sendKeys(after + Keys.TAB);
                Commons.sleepWait(4000);
                if (afterAttribute == null)
                    elementAfter = element.getText();
                else
                    elementAfter = element.getDomProperty(afterAttribute);
                Commons.takeFullScreenshot();
                Assert.assertFalse(before.equalsIgnoreCase(elementAfter));
            }
        } else if (elementType.equalsIgnoreCase("dropdown")) {
            Select selectObject = new Select(element);
            String id = element.getDomAttribute("id");
            before = selectObject.getFirstSelectedOption().getText();
            after = Commons.selectDifferentOption(selectObject);
            if (after == null) {
                Commons.sleepWait(2000);
                after = (new Select(driver.findElement(By.id(id)))).getFirstSelectedOption().getText();
            }
            //after = selectObject.getFirstSelectedOption().getText();
            //Commons.takeFullScreenshot();

            Assert.assertFalse(before.equalsIgnoreCase(after));
        } else if (elementType.equalsIgnoreCase("checkbox")) {
            boolean boolBefore, boolAfter;
            List checkedBefore = driver.findElements(By.cssSelector("input:checked[type='checkbox']"));

            element.click();

            List checkedAfter = driver.findElements(By.cssSelector("input:checked[type='checkbox']"));

            //Commons.takeFullScreenshot();

            Assert.assertFalse(checkedAfter.containsAll(checkedBefore) && checkedBefore.containsAll(checkedAfter));
        } else if (elementType.equalsIgnoreCase("url")) {
            if (beforeAttribute == null)
                before = element.getText();
            else
                before = element.getDomAttribute(beforeAttribute);
            after = before + "/after";
            element.clear();
            element.sendKeys(after);

            //Commons.takeFullScreenshot();
            // if else required for checking after dom attribute???
            Assert.assertFalse(before.equalsIgnoreCase(element.getDomAttribute(afterAttribute)));
        }
    }

    public static void tick(WebElement element) {
        if (!element.isSelected())
            element.click();
    }

    public static void untick(WebElement element) {
        if (element.isSelected())
            element.click();
    }

    public static void tickAnother(WebElement check, WebElement click) {
        if (!check.isSelected())
            click.click();
    }

    public static void scrollToView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToViewReverse(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static WebElement scrollTillFindElement(String visibleName) {
        int i = 1;
        while (true) {
            try {
                return findElementByExactName(driver, visibleName);
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, " + i * 250 + ")");
                i++;
                if (i > 10)
                    return null;
            }
        }
    }

    public static void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static String today() {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy EEEE");
        return formatter.format(today);
    }

//    public static boolean notEditable(WebElement element) {
//        try{
//            String before = element.getText();
//            element.sendKeys("after");
//            String after = element.getText();
//            return before.equalsIgnoreCase(after);
//        } catch (Exception e) {
//            return true;
//        }
//    }

    private static void initialSetup() {
        if (!editable.isEmpty())
            return;
        editable.add("text box");
        editable.add("text area");
        editable.add("combo box");
        editable.add("textbox");
        editable.add("textarea");
        editable.add("combobox");
    }

    public WebDriver setDriver() {
        driver = setDriver(driver);
        return driver;
    }

    public static WebDriver setDriver(WebDriver driver1) {
        Commons.sleepWait(2000);
        // below line makes sure chrome version is matched
        WebDriverManager.chromedriver().clearDriverCache().setup();

        // browser should not know it is an automation running
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//        options.setExperimentalOption("useAutomationExtension", false);
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        driver1 = WebDriverManager.chromedriver().capabilities(options).create();

        // when browser can know it is an automation running
        driver1 = WebDriverManager.chromedriver().create();

        driver1.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.browserWait));
        driver1.manage().window().maximize();
        initialSetup();
        // actions = new Actions(driver);
        return driver1;
    }

    public void setUser(String userType) {
        this.userType = userType;
        if (userType.equalsIgnoreCase("alpha")) {
            userName = creds.getProperty("alphauser");
            password = creds.getProperty("alphapwd");
            JIRATestPlan = config.getProperty("ALPHA_PLAN");
        } else if (userType.equalsIgnoreCase("beta")) {
            userName = creds.getProperty("betauser");
            password = creds.getProperty("betapwd");
            JIRATestPlan = config.getProperty("BETA_PLAN");
        } else if (userType.equalsIgnoreCase("charlie")) {
            userName = creds.getProperty("charlieuser");
            password = creds.getProperty("charliepwd");
            JIRATestPlan = config.getProperty("CHARLIE_PLAN");
        } else if (userType.equalsIgnoreCase("delta")) {
            userName = creds.getProperty("deltauser");
            password = creds.getProperty("deltapwd");
            JIRATestPlan = config.getProperty("DELTA_PLAN");
        } else if (userType.equalsIgnoreCase("gamma")) {
            userName = creds.getProperty("gammauser");
            password = creds.getProperty("gammapwd");
            JIRATestPlan = config.getProperty("GAMMA_PLAN");
        } else if (userType.equalsIgnoreCase("echo")) {
            userName = creds.getProperty("echouser");
            password = creds.getProperty("echopwd");
            JIRATestPlan = config.getProperty("ECHO_PLAN");
        } else if (userType.equalsIgnoreCase("administrator")) {
            userName = creds.getProperty("adminuser");
            password = creds.getProperty("adminpwd");
            JIRATestPlan = config.getProperty("ADMIN_PLAN");
        } else if (userType.equalsIgnoreCase("adminSSO")) {
            userName = creds.getProperty("adminuser");
            password = creds.getProperty("adminpwd");
            JIRATestPlan = config.getProperty("ADMIN_PLAN");
        } else {
            Commons.userType = "Default";
            userName = creds.getProperty("userName1");
            password = creds.getProperty("password1");
            JIRATestPlan = config.getProperty("DEFAULT_PLAN");
        }

        System.out.println("username is " + userName);
        System.out.println("PASSWORD is " + password);
//        System.out.println("JIRA Test Plan is " + JIRATestPlan);
    }

    public static void SSOlogin() {
        driver.navigate().to(URL(config.getProperty("ENV")));

        // to-do find out why system error occurs when navigate() used
        // manually enter URL

        // Commons.sleepWait(2000);
        //Commons.findElementByExactName("Company Single Sign-On").click();
        Commons.findElementByExactName("Sign in with Resideo SSO").click();
//        try{
//            Commons.findElementByExactName(userName).click();
//        }
//        catch (Exception e){
//            try{
//                Commons.findElementByExactName("Use another account").click();
//            }
//            catch(Exception ignore){
//
//            }
//            driver.findElement(By.id("i0116")).sendKeys(userName);
//        }


        // to be used when cache is not tobe refreshed so that correct user is logged in
//        try{
//            findElementByExactName("Use another account").click();
//            sleepWait();
//        }
//        catch (NoSuchElementException ignored){
//
//        }
        Commons.sleepWait(3000);
        try {
            driver.findElement(By.id("i0116")).sendKeys(userName);
        }
        catch (Exception e) {
            Commons.findElementByExactName("Use another account").click();
            Commons.sleepWait(3000);
            driver.findElement(By.id("i0116")).sendKeys(userName);
        }
        System.out.println("username and password are");
        driver.findElement(By.id("idSIButton9")).click();
        Commons.sleepWait(3000);
        System.out.println(userName + " : " + password);
        driver.findElement(By.id("i0118")).sendKeys(password);
        Commons.sleepWait(3000);
        driver.findElement(By.id("idSIButton9")).click();
        Commons.sleepWait(3000);
        driver.findElement(By.cssSelector("*[id$='" + OracleHome.HOME + "']")).click();
    }

    public void login(WebDriver driver) {
        driver.navigate().to(URL(config.getProperty("ENV")));

        driver.findElement(By.id(LoginPage.USERID)).clear();
        driver.findElement(By.id(LoginPage.USERID)).sendKeys(userName);

        driver.findElement(By.id(LoginPage.PASSWORD)).clear();
        driver.findElement(By.id(LoginPage.PASSWORD)).sendKeys(password);

        driver.findElement(By.id(LoginPage.SIGNIN)).click();

        //driver.manage().window().maximize();

        //driver.findElement(By.id(LoginPage.HOME_BUTTON)).click();
    }

    public void login() {
        driver.navigate().to(URL(config.getProperty("ENV")));

        /*
        new page for sign-in
        driver.findElement(By.id(LoginPage.USERID)).clear();
        driver.findElement(By.id(LoginPage.USERID)).sendKeys(userName);

        driver.findElement(By.id(LoginPage.PASSWORD)).clear();
        driver.findElement(By.id(LoginPage.PASSWORD)).sendKeys(password);

        driver.findElement(By.id(LoginPage.SIGNIN)).click();

        //driver.manage().window().maximize();

        //driver.findElement(By.id(LoginPage.HOME_BUTTON)).click();
        */

        driver.findElement(By.id(LoginPage.USERNAME_IDCS)).clear();
        driver.findElement(By.id(LoginPage.USERNAME_IDCS)).sendKeys(userName);

        driver.findElement(By.id(LoginPage.PASSWORD_IDCS)).clear();
        driver.findElement(By.id(LoginPage.PASSWORD_IDCS)).sendKeys(password);

        Commons.findElementByExactName("Next").click();
    }

    public static void login(LoginCreds loginCreds) {
        driver.navigate().to(loginCreds.URL);

        driver.findElement(By.id(LoginPage.USERID)).clear();
        driver.findElement(By.id(LoginPage.USERID)).sendKeys(loginCreds.userName);

        driver.findElement(By.id(LoginPage.PASSWORD)).clear();
        driver.findElement(By.id(LoginPage.PASSWORD)).sendKeys(loginCreds.password);

        driver.findElement(By.id(LoginPage.SIGNIN)).click();
    }

    public static String URL(String env) {
        if (env.equalsIgnoreCase("SIT"))
            return Constants.SIT_LOGIN_URL;
        else if (env.equalsIgnoreCase("UAT"))
            return Constants.UAT_LOGIN_URL;
        else if (env.equalsIgnoreCase("PRD"))
            return Constants.PRD_LOGIN_URL;
        else if (env.equalsIgnoreCase("DEV1"))
            return Constants.DEV1_LOGIN_URL;
        return Constants.DEV_LOGIN_URL;
    }

    public static void home() {
        try{
            Commons.findElementByExactName("ave and Close").click();
        }
        catch (NoSuchElementException e1){

        }
        try {
            Commons.findElementByAttributeValue("title", "Home").click();
            Commons.sleepWait();

            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
        }
        catch (ElementClickInterceptedException | NoSuchElementException e){
            driver.navigate().refresh();
            Commons.sleepWait();
            try{
                Commons.findElementByExactName("Yes").click();
            }
            catch (NoSuchElementException e1){

            }
            Commons.findElementByAttributeValue("title", "Home").click();
            Commons.sleepWait();

            try {
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
            }
            catch(NoSuchElementException e1){
                Commons.findElementByExactName("Yes").click();
                Commons.sleepWait();
                Commons.findElementByAttributeValue("title", "Home").click();
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$='" + OverviewPage.PRODUCT_MANAGEMENT + "']")).click();
            }
        }
    }

    public static List<List<String>> readMatchingRowsFromExcel(String fileName, String sheetName, String header) {
        String excelFilePath = System.getProperty("user.dir") + "/src/test/projectData/" + fileName + ".xlsx";
        List<List<String>> matchingRows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            for (Row row : sheet) {
                // Check the first cell (Column index 0)
                Cell firstCell = row.getCell(0);
                String cellValue = firstCell.getStringCellValue();

                // Match logic (case-insensitive for robustness)
                if (cellValue != null && cellValue.equalsIgnoreCase(header)) {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
                        rowData.add(cell.getStringCellValue());
                    }
                    matchingRows.add(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(header);
        return matchingRows;
    }

    public static List<String> readFromExcel(String fileName, String sheetName, String header, String direction)
    {

        // direction will let us know if we read the columns or rows


//        List<String> columns = new ArrayList<String>();
//        FileInputStream inputStream = new FileInputStream(new File(fileName));
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet firstSheet = workbook.getSheetAt(0);
//
//        Iterator<Row> iterator = firstSheet.iterator();

        String excelFilePath = System.getProperty("user.dir") + "/src/test/projectData/" + fileName + ".xlsx";
        int columnIndex = -1;
        int rowIndex = -1;

        ArrayList<String> directionData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            //Sheet sheet = workbook.getSheetAt(0); // Assuming you want to read the first sheet

            Sheet sheet = workbook.getSheet(sheetName);

            if (direction.equalsIgnoreCase("column")) { // read a column with certain column header
                Row firstRow = sheet.getRow(0);
                for (Cell cell : firstRow) {
                    if (cell.getStringCellValue().equalsIgnoreCase(header)) {
                        columnIndex = cell.getColumnIndex();
                        break;
                    }
                }

                if (columnIndex == -1) {
                    System.out.println("Column " + header + " not found in sheet " + sheetName + " in excel workbook " + fileName);
                    return directionData;
                }

                directionData.add("");
                for (Row row : sheet) {
                    Cell cell = row.getCell(columnIndex);
                    boolean isHeader = false;
                    if (cell == null) {
                        isHeader = false;
                    } else if (cell.getCellType().toString().equals("STRING")) {
                        isHeader = cell.getStringCellValue().equalsIgnoreCase(header);
                    }
                    if (cell != null && !isHeader) {
                        switch (cell.getCellType()) {
                            case STRING:
                                directionData.add(cell.getStringCellValue());
                                break;
                            case NUMERIC: // adding for future cases
                                directionData.add(String.valueOf(cell.getNumericCellValue()));
                                break;
                            case BOOLEAN:
                                directionData.add(String.valueOf(cell.getBooleanCellValue()));
                                break;
                            default:
                                directionData.add("Unsupported cell type");
                        }
                    }
                }
            } else { // read a row with certain row header
                for (Row row : sheet) {
                    Cell firstCell = row.getCell(0);
                    String firstCell1;
                    if(firstCell.getCellType().equals(CellType.NUMERIC)){
                        firstCell1 = firstCell.toString().split("\\.")[0];
                    }else{
                        firstCell1 = firstCell.toString();
                    }
                    if (firstCell != null && firstCell1.equals(header)) {
                        for (Cell cell : row) {
                            //directionData.add(cell.toString());
                            switch (cell.getCellType()) {
                                case STRING:
                                    directionData.add(cell.getStringCellValue());
                                    break;
                                case NUMERIC: // adding for future cases
                                    directionData.add(String.valueOf(cell.getNumericCellValue()));
                                    break;
                                case BOOLEAN:
                                    directionData.add(String.valueOf(cell.getBooleanCellValue()));
                                    break;
                                default:
                                    directionData.add("Unsupported cell type");
                            }
                        }
                        break; // Exit loop after finding the row
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(header);
        return directionData;

        // Print the values to verify
//        for (String value : directionData) {
//            System.out.println(value);
//        }
    }

//    public List<String> readRowFromExcel(String fileName, String sheetName, String rowName) throws IOException {
//
//
//    }

    public static void sleepWait(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException ignore) {

        }
    }

    public static void sleepWait() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {

        }
    }

    public static String takeScreenshot(WebDriver driver) {
        try {
            //Thread.sleep(5000);
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filename = Commons.userRandomString("file");
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/screenshots/" + filename + ".png"));
            System.out.println("the Screenshot file name is " + filename);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String takeScreenshot(WebDriver driver, int waitTime) {
        try {
            Thread.sleep(waitTime);
            return takeScreenshot(driver);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String takeFullScreenshot() {

        // https://www.javacodegeeks.com/2019/07/using-selenium-webdriver-full-page-screenshots.html#google_vignette
        String filename = Commons.userRandomString("file");

        try {
            Thread.sleep(1000);
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "PNG", new File(System.getProperty("user.dir") + "/target/screenshots/" + filename + ".png"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("the Screenshot file name is " + filename);
        return filename;
    }

    public static void logout() {
        driver.findElement(By.id(OracleHome.HOME)).click();
        sleepWait(2000);
        driver.findElement((By.id(OracleHome.SETTINGS_AND_ACTIONS))).click(); // check again if ID is correct
        sleepWait();
        driver.findElement((By.id(OracleHome.SIGNOUT))).click();
        Commons.sleepWait();
        try {
            driver.findElement((By.id(OracleHome.CONFIRM))).click();
            Commons.sleepWait();
        }
        catch (NoSuchElementException ignore){

        }
    }

    public static String userRandomString(String custom) {
        if (custom.equalsIgnoreCase("part")) {
            return "" + Calendar.getInstance().getTimeInMillis();
        }
        return userName + "_" + custom + "_automation" + "-" + Calendar.getInstance().getTimeInMillis();
    }

    public List getVisibletextFromDropDown(Select selectObject) {
        List<String> readable = new ArrayList<>();
        for (WebElement elements : selectObject.getOptions()) {
            readable.add(elements.getText());
        }
        return readable;
    }

    public static boolean compareList(List list1, List list2) {

        if (list1.get(0).equals("")) {
            list1.remove(0);
        }
        if (list2.get(0).equals("")) {
            list2.remove(0);
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        Collections.sort(list1);
        Collections.sort(list2);

        return list1.equals(list2);
//        for(int i=0; i< pageList.size(); i++){
//            if(!pageList.get(i).equals(excelList.get(i))){
//                return false;
//            }
//        }
//        return true;
    }

    public static void assertNonModifiable(WebElement element, String input) {
        String before;
        try {
            before = element.getText();
            element.sendKeys(input);
            Assert.assertEquals(before, element.getText());
        } catch (NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e) {
            System.out.println("element could not be edited");
        }
    }

    public static void assertElementNotPresent(String element) {
        try {
            driver.findElement(By.cssSelector("*[id$='" + element + "']")).click();
        } catch (NoSuchElementException e) {
            System.out.println("element not present as expected");
        }
    }

    // below function to create a new type and stay in the general information page
    public static void reachEditPage(String type) {
        String number;
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
        Commons.sleepWait(2000);
        Select select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_DD_END + "']")));
        select.selectByVisibleText(type);
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
        if (type.equalsIgnoreCase("Change Orders")) {
            number = "SCO-";
        } else if (type.equalsIgnoreCase("Change Requests")) {
            number = "SCR-";
        } else if (type.equalsIgnoreCase("Manufacturer Parts")) {
            number = "101";
        } else {
            number = "Automation";
        }
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(number + Keys.ENTER);
        Commons.sleepWait(4000);
        try {
            driver.findElement(By.cssSelector("*[id*='" + OverviewPage.SEARCH_FIRST_RESULT_END + "']")).click();
        } catch (Exception ignore) {

        }
        sleepWait(2000);
    }

    public static void reachEditPage(String type, String number) {
        objectNumber = number;
        objectType = type;

        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
        Commons.sleepWait(2000);
        Select select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_DD_END + "']")));

        select.selectByVisibleText(type);
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
        if(type.equalsIgnoreCase("Manufacturer Parts")){
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys("101" + Keys.ENTER);
            Commons.sleepWait(4000);
            Commons.findElementByPartialName("101").click();
            Commons.sleepWait();
            return;
        }
        if (number.contains("Automation")) {
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(number + Keys.ENTER);
            Commons.sleepWait(4000);
            try {
                driver.findElement(By.cssSelector("*[id*='" + OverviewPage.SEARCH_FIRST_RESULT_END + "']")).click();
            } catch (Exception ignore) {

            }
            sleepWait(2000);
        } else {
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(number + Keys.ENTER);
        }

    }

    public static void reachEditPage(WebDriver driver, String type, String number) {
        if (type.equalsIgnoreCase("CR")) {
            type = "Change Requests";
        } else if (type.equalsIgnoreCase("CO")) {
            type = "Change Orders";
        }
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_END + "']")).click();
        Commons.sleepWait(2000);
        Select select = new Select(driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_DD_END + "']")));
        select.selectByVisibleText(type);
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).clear();
        if (number.contains("Automation")) {
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(number + Keys.ENTER);
            Commons.sleepWait(4000);
            try {
                driver.findElement(By.cssSelector("*[id*='" + OverviewPage.SEARCH_FIRST_RESULT_END + "']")).click();
            } catch (Exception ignore) {

            }
            sleepWait(2000);
        } else {
            driver.findElement(By.cssSelector("*[id$='" + OverviewPage.SEARCH_BOX_END + "']")).sendKeys(number + Keys.ENTER);
        }

    }

    public static void switchWindow(Set<String> allWindowHandles, String mainWindowHandle) {
        for (String handle : allWindowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                // Switch to the new window
                driver.switchTo().window(handle);
            }
        }
    }


    public static void xmlTagEdit(String[] args) {
        try {
            // Path to the XML file
            String filePath = "path/to/your/file.xml";
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parse the XML file and create a Document object
            Document document = builder.parse(new File(filePath));
            // Get the list of elements with the tag name "yourTagName"
            NodeList nodeList = document.getElementsByTagName("yourTagName");
            // Check if the tag exists
            if (nodeList.getLength() > 0) {
                // Get the first element with the tag name "yourTagName"
                Element element = (Element) nodeList.item(0);
                // Edit the content of the tag
                element.setTextContent("newContent");
                // Save the changes to the XML file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(filePath));
                transformer.transform(source, result);
                System.out.println("XML tag updated successfully.");
            } else {
                System.out.println("Tag not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compareDropDown(WebElement element, String header) {

        List<String> excel;
        List<String> actual = new ArrayList<>();
        Select select;

        excel = readFromExcel("DropDowns", "DropDowns", header, "column");
        select = new Select(element);
        for (WebElement option : select.getOptions()) {
            actual.add(option.getText());
        }
        Assert.assertTrue(compareList(excel, actual));
    }

    public static WebElement relativeLocators(String refElement, String direction, String tagName) {
        WebElement element = null;
        if (direction.equalsIgnoreCase("right")) {
            element = driver.findElement(RelativeLocator.with(By.tagName(tagName)).toRightOf(By.cssSelector("*[id$='" + refElement + "']")));
        } else if (direction.equalsIgnoreCase("left")) {
            element = driver.findElement(RelativeLocator.with(By.tagName(tagName)).toLeftOf(By.cssSelector("*[id$='" + refElement + "']")));
        }
        return element;
    }

    public static void linkJIRATestsToExecution() {
        // reach the results xml file

        // read its contents

        // array of the tag <testcase>

        // in tag test case get the attribute "name" -> this is what will be going to the description

        //
    }

    public static WebElement findElementByLabel(String text) {
        WebElement element = null;
        for (WebElement labels : findElementsByExactName(text)) {
            if (labels.getTagName().equalsIgnoreCase("label")) {
                if ((labels.getDomAttribute("for") == null)) {
                    continue;
                } else {
                    element = labels;
                    try {
                        WebElement element1 = driver.findElement(By.id(element.getDomAttribute("for")));
                        return element1;
                    } catch (NoSuchElementException e) {
                        continue;
                    }
                }
            }
        }
        if (element == null) {
            return null;
        }
        return null;
    }

    public static List<WebElement> findElementsByLabel(String text) {

        List<WebElement> list = new ArrayList<>();
        WebElement element = null;
        for (WebElement labels : findElementsByExactName(text)) {
            if (labels.getTagName().equalsIgnoreCase("label")) {
                if ((labels.getDomAttribute("for") == null)) {
                    continue;
                } else {
                    element = labels;
                    list.add(driver.findElement(By.id(element.getDomAttribute("for"))));
                }
            }
        }
        if (element == null) {
            return null;
        }
        return list;
    }

    public static WebElement findElementByLabel(SearchContext driver, String text) {
        return driver.findElement(By.id(findElementByExactName(text).getDomAttribute("for")));
    }

    public static WebElement findParentOfElement(WebElement element){
        return element.findElement(By.xpath("./.."));
    }

    public static WebElement findElementByExactName(String name) {
        return driver.findElement(By.xpath("//*[text()='" + name + "']"));
    }

    public static WebElement findElementByExactName(SearchContext driver, String name) {
        return driver.findElement(By.xpath(".//*[text()='" + name + "']"));
    }

    public static List<WebElement> findElementsByExactName(String name) {
        return driver.findElements(By.xpath("//*[text()='" + name + "']"));
    }

    public static List<WebElement> findElementsByExactName(SearchContext driver, String name) {
        return driver.findElements(By.xpath(".//*[text()='" + name + "']"));
    }

    public static WebElement findElementByPartialName(String name) {
        return driver.findElement(By.xpath("//*[contains(text(), '" + name + "')]"));
    }

    public static WebElement findElementByPartialName(SearchContext driver, String name) {
        return driver.findElement(By.xpath("//*[contains(text(), '" + name + "')]"));
    }

    public static List<WebElement> findElementsByPartialName(String name) {
        return driver.findElements(By.xpath("//*[contains(text(), '" + name + "')]"));
    }

    public static List<WebElement> findElementsByPartialName(SearchContext driver, String name) {
        return driver.findElements(By.xpath("//*[contains(text(), '" + name + "')]"));
    }

    public static WebElement findElementByAttributeValue(String attribute, String value) {
        return driver.findElement(By.cssSelector("[" + attribute + "='" + value + "']"));
    }

    public static WebElement findElementByAttributeValue(SearchContext driver, String attribute, String value) {
        return driver.findElement(By.cssSelector("[" + attribute + "='" + value + "']"));
    }

    public static List<WebElement> findElementsByAttributeValue(String attribute, String value) {
        return driver.findElements(By.cssSelector("[" + attribute + "='" + value + "']"));
    }

    public static List<WebElement> findElementsByAttributeValue(SearchContext driver, String attribute, String value) {
        return driver.findElements(By.cssSelector("[" + attribute + "='" + value + "']"));
    }

    public static WebElement findInputElementByParameterName(String parameter) {
        WebElement attributeInput = Commons.findElementByLabel(parameter);
        if (attributeInput == null) {
            // label is not loaded properly. call function to get the row and get the second column.
            return findElementByExactName(parameter).findElement(By.xpath("./../../td[2]")).findElement(By.cssSelector("*[id$='::content"));
        }
        return attributeInput;
    }

    public static String getStatus() {
        return driver.findElement(By.cssSelector("*[id$='" + EditChangePage.TOP_STATUS_END + "'] > tbody > tr > td[3]")).getText();
    }

    // function to send email

}
