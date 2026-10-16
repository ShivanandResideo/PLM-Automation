package stepDefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import utils.Commons;
import utils.objects.User;

import java.util.ArrayList;
import java.util.List;

import static utils.Commons.driver;
import static utils.Commons.findElementByExactName;

public class UserCreationSteps {

    public static Commons commons = new Commons();

    public static String email;
    public static String firstName, lastName, PID;

    public static ArrayList<User> users = new ArrayList<>();

    @Given("^read user details from excel$")
    public static void getUser(){
        List<String> emails = commons.readFromExcel("PLMUsersTest", "Total", "Email", "column");
        emails.removeFirst();
        int i=0;
        for(String email : emails){
            List<String> details = commons.readFromExcel("PLMUsersTest", "Total", email, "row");
            User user = new User();
            user.email = details.get(0);
            user.lastName = details.get(1);
            user.firstName = details.get(2);
            user.pid = details.get(3).split("\\.")[0];
            user.readonly = details.get(4);

            users.add(i++, user);
        }
    }

    @And("^create worker record$")
    public static void createWorkerRecord(){

        reachCreateUserPage();

        for(User user : users){
            createUser(user.lastName, user.firstName, user.email, user.pid, user.location, user.locationCode, user.department, user.manager);
        }
    }

    @Then("^re-assign role (.*) to worker record with pid (.*)$")
    public static void reAssignRole(String role, String pid){

        reachUserEditPage(pid);

        removeAllRoles();

        addAllRoles(role);
    }

    @Then("^remove all roles for (.*)$")
    public static void removeRoles(String pid) {

        reachUserEditPage(pid);

        removeAllRoles();
    }

    @And("^create worker record for (.*) with name (.*) , (.*) with PID (.*) in location (.*) with code (.*) under department (.*) with manager (.*)$")
    public static void createWorkerRecord(String email, String firstName, String lastName, String PID, String location, String locationCode, String department, String manager){

        createWorkerRecord();

        createUser(lastName, firstName, email, PID, location, locationCode, department, manager);
    }

    @When("edit a worker record with email (.*) to add department (.*) and location (.*) with code (.*) to existing user")
    public static void editWorkerRecordWithEmail(String email, String department, String location, String locationCode) {
        Commons commons = new Commons();
        Commons.home();
        Commons.sleepWait(2000);
        try{
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
            Commons.sleepWait(1500);
        } catch (Exception e) {
            commons.home();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
            Commons.sleepWait(1500);
        }

        driver.findElement(By.cssSelector("*[id$=':AP1:qq1:criterionValue0::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':AP1:qq1:criterionValue0::content']")).sendKeys(email);
        driver.findElement(By.cssSelector("*[id$=':AP1:qq1::search_icon']")).click();
        Commons.sleepWait();
        driver.findElement(By.cssSelector("*[id$=':pgl30']")).click();
        Commons.sleepWait();
        // location and department
        if(!locationCode.equalsIgnoreCase("<Department>")) {
            Commons.findElementByLabel("Department").sendKeys(department + Keys.TAB);
            Commons.sleepWait(2000);
        }
        if(!location.equalsIgnoreCase("<Location>")) {
            Commons.findElementByLabel("Location").sendKeys(location + Keys.TAB);
            Commons.sleepWait();
            if(Commons.findElementByExactName("Search and Select: Location").isEnabled()){
                // driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId:value00::content']")).sendKeys(location);
                driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId:value10::content']")).sendKeys(locationCode);
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId::search']")).click();
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$=':locationId_afrLovInternalTableId::db']")).click();
                Commons.sleepWait(1000);
                Commons.findElementByExactName("OK").click();
                Commons.sleepWait();
            }

            Commons.findElementByExactName("ave and Close").click();

        }

    }

    @And("^set password (.*) for user (.*)$")
    public static void setPassword(String password, String pid){
        if(pid.matches("[0-9]{6}")){
            System.out.println("Password reset not required for "+pid);
            return;
        }
        reachUserSearchPage();

        resetPassword(password, pid);
    }

    @Then("^assign role (.*) to worker record with PID (.*)$")
    public static void assignRole(String role, String PID){

        reachUserEditPage(PID);

        // assignRoleToUser(PID, role, email);
        if(role.equalsIgnoreCase("Alpha")){
            role = "Rezi PLM View Only + Rezi PLM Read Only";
        }
        else if(role.equalsIgnoreCase("Beta")){
            role = "Rezi PLM View Only + Rezi PLM Approve Changes";
        }
        else if(role.equalsIgnoreCase("Charlie")){
            role = "Rezi PLM View Only + Rezi PLM Approve Changes + Rezi PLM Request Changes";
        }
        else if(role.equalsIgnoreCase("Delta")){
            role = "Rezi PLM View Only + Rezi PLM Approve Changes + Rezi PLM Request Changes + Rezi PLM Author";
        }
        else if(role.equalsIgnoreCase("Gamma")){
            role = "Rezi PLM View Only + Rezi PLM Approve Changes + Rezi PLM Request Changes + Rezi PLM Author + Rezi PLM Change Analyst";
        }
        else if(role.equalsIgnoreCase("Echo")){
            role = "Rezi PLM View Only + Rezi PLM Approve Changes + Rezi PLM Request Changes + Rezi PLM Author + Rezi PLM Business Admin";
        }

        addAllRoles(role);
    }

    @And ("^inactivate user (.*)$")
    public static void deactivateUser(String pid){
        reachUserSearchPage();

        uncheckActive(pid);
    }

    @And ("^activate user (.*)$")
    public static void activateUser(String pid){
        reachUserSearchPage();

        checkActive(pid);
    }

    public static void resetPassword(String pwd, String pid){
        // click on user tab, clear and search user
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).sendKeys(pid + Keys.RETURN);
        Commons.sleepWait(2000);

        // select first user always
        // driver.findElement(By.cssSelector("*[id$=':sp1:usrList:0:cl1']")).click();

        // click user manually
        // driver.findElement(By.cssSelector("*[id$=':sp1:plam13']"));

        // click user in automation
        driver.findElement(By.xpath("//*[text()='"+ pid +"']")).click();

        driver.findElement(By.cssSelector("*[id$=':sp1:cb7']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':ase1Sp:sor1:_1']")).findElement(By.xpath("..")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':ase1Sp:nPwIp::content']")).sendKeys(pwd);
        driver.findElement(By.cssSelector("*[id$=':ase1Sp:nPwIpC::content']")).sendKeys(pwd);
        Commons.sleepWait(2000);

        driver.findElement(By.cssSelector("*[id$=':ase1Sp:rpSub']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':sp1:cb2']")).click();
    }

    public static void reachCreateUserPage(){
        Commons commons = new Commons();
        commons.home();
        Commons.sleepWait(2000);
//        try{
//            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
//            Commons.sleepWait(1500);
//            driver.findElement(By.cssSelector("*[id$=':nvgpgl2_groupNode_manager_resources']")).click();
//            Commons.sleepWait(1500);
//            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
//            Commons.sleepWait(1500);
//        } catch (Exception e) {
//            commons.home();
//            Commons.sleepWait(2000);
//            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
//            Commons.sleepWait(1500);
//            driver.findElement(By.cssSelector("*[id$=':nvgpgl2_groupNode_manager_resources']")).click();
//            Commons.sleepWait(1500);
//            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
//            Commons.sleepWait(1500);
//        }
        try{
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
            Commons.sleepWait(1500);
        } catch (Exception e) {
            commons.home();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
            Commons.sleepWait(1500);
        }

        // create plus button
        driver.findElement(By.cssSelector("*[id$=':AP1:gallerySearchVOCriteriaQueryResultId:_ATp:ctb3::icon']")).click();
    }

    private static void createUser(String lastName, String firstName, String email, String pid, String location, String locationCode, String department,String manager) {

        Commons.sleepWait(2000);
        // last first name, email PID
        driver.findElement(By.cssSelector("*[id$=':it4::content']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("*[id$=':it3::content']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("*[id$=':it13::content']")).sendKeys(email);
        driver.findElement(By.cssSelector("*[id$=':it1::content']")).sendKeys(pid);

        // tickbox
        driver.findElement(By.cssSelector("*[id$=':sbc1::Label0']")).click();

        // employee information
        driver.findElement(By.cssSelector("*[id$=':panel1:r2:0:soc1::drop']")).click();
        //driver.findElement(By.id("_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:0:panel1:r2:0:soc1::drop")).click();
        // _FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:panel1:r2:0:soc1::drop
        driver.findElement(By.xpath("//*[text()='Employee']")).click();
        //Commons.sleepWait(5000);
        int i=0;
        while(i<1){
            try{
                driver.findElement(By.cssSelector("*[id$=':panel1:r2:0:legalEmployerId::lovIconId']")).click();
                i++;
            }
            catch (StaleElementReferenceException ignore){

            }
        }
        driver.findElement(By.xpath("//*[text()='Ademco Inc']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':panel1:r2:0:businessUnitId::lovIconId']")).click();
        driver.findElement(By.xpath("//*[text()='Ademco Inc BU']")).click();
        Commons.sleepWait(2000);
        // location and department
        if(!department.equalsIgnoreCase("<Department>")) {
            Commons.findElementByLabel("Department").sendKeys(department + Keys.TAB);
            Commons.sleepWait(2000);
        }
        if(!location.equalsIgnoreCase("<Location>")) {
            Commons.findElementByLabel("Location").clear();
            Commons.findElementByLabel("Location").sendKeys(location + Keys.TAB);
            Commons.sleepWait();
            try{
                Commons.findElementByExactName("Search and Select: Location");
                // driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId:value00::content']")).sendKeys(location);
                driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId:value10::content']")).sendKeys(locationCode);
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId::search']")).click();
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$=':locationId_afrLovInternalTableId::db']")).click();
                Commons.sleepWait(3000);
                Commons.findElementByExactName("OK").click();
                Commons.sleepWait();
            } catch (NoSuchElementException ignore) {

            }
        }
        if(!manager.equalsIgnoreCase("<Manager>")) {
            Commons.findElementByLabel("Manager").clear();
            Commons.findElementByLabel("Manager").sendKeys(manager + Keys.TAB);
            Commons.sleepWait();
            try{
                // if the search dialog box opens close it.
                Commons.findElementByExactName("Cancel").click();
            } catch (Exception ignore) {

            }
        }
        // save and create another
//        driver.findElement(By.cssSelector("*[id$=':panel1:SaveAndCloseButton::popEl']")).click();
//        Commons.sleepWait(2000);
        // _FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:panel1:SaveAndCloseButton::popEl
//        driver.findElement(By.cssSelector("*[id$=':panel1:SaveAndAnotherMenuItem']")).click();
        Commons.findElementByExactName("Save and Close").click();
        Commons.sleepWait(2000);

        // check if the user is already created
        if(driver.findElement(By.cssSelector("*[id$='_FOd1::msgDlg']")).isDisplayed()){
            // close dialog box
            driver.findElement(By.cssSelector("*[id$='_FOd1::msgDlg::cancel']")).click();
            Commons.sleepWait(1500);
            // cancel the form
            driver.findElement(By.cssSelector("*[id$=':panel1:SPc']")).click();
            Commons.sleepWait(1500);
            // create plus button
//            driver.findElement(By.cssSelector("*[id$=':AP1:gallerySearchVOCriteriaQueryResultId:_ATp:ctb3::icon']")).click();
//            System.out.println(" worker record already exists for "+ pid);

            return;
        }

        Commons.sleepWait(10000);
        System.out.println(" worker record created for "+ email);
        // driver.findElement(By.cssSelector("*[id$='"+ OverviewPage.CLIPBOARD_END +"']"))
    }

    public static void removeAllRoles(){
        // edit
        driver.findElement(By.cssSelector("*[id$=':sp1:cb6']")).click();

        // remove all roles
        driver.findElement(By.cssSelector("*[id$=':sp1:Ase1Bn']")).click();

        // ok
        driver.findElement(By.cssSelector("*[id$=':sp1:Ase2Bn']")).click();
        Commons.sleepWait(2000);
        // save and done
        if(driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).isEnabled()) {
            driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).click();
        }else{
            driver.findElement(By.cssSelector("*[id$=':sp1:canBtn']")).click();
        }
        // driver.findElement(By.cssSelector("*[id$=':sp1:cb2']")).click();
        Commons.sleepWait(2000);
    }

    private static void reachUserEditPage(String pid) {
        Commons commons = new Commons();
        commons.home();
        Commons.sleepWait(2000);

        try {
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if (driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More")) {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")));
            Commons.sleepWait(1500);
            driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")).click();
            Commons.sleepWait(1500);
        } catch (Exception e) {
            commons.home();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if (driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More")) {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")));
            Commons.sleepWait(1500);
            driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")).click();
            Commons.sleepWait(1500);
        }

        Commons.sleepWait(2000);

        // user tab
        driver.findElement(By.cssSelector("*[id$=':_FOTsdiASE_FUSE_UM_WORKAREA::ti']")).click();

        // search for user here
        // click on user tab, clear and search user
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).sendKeys(pid + Keys.RETURN);
        Commons.sleepWait(2000);

        // click user manually
        // driver.findElement(By.cssSelector("*[id$=':sp1:plam13']"));

        // click user in automation
        try {
            driver.findElement(By.xpath("//*[text()='" + pid + "']")).click();
        }
        catch (Exception e){
            Commons.scrollToView(driver.findElement(By.xpath("//*[text()='" + pid + "']")));
            driver.findElement(By.xpath("//*[text()='" + pid + "']")).click();
        }
    }

    private static void reachUserSearchPage() {
        Commons commons = new Commons();
        commons.home();
        Commons.sleepWait(2000);
//        try{
//            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
//            Commons.sleepWait(2000);
//            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$=':nvgpgl2_nvmOthersCustomGrp']")));
//            driver.findElement(By.cssSelector("*[id$=':nvgpgl2_groupNode_tools']")).click();
//            Commons.sleepWait(2000);
//            driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")).click();
//        } catch (Exception e) {
//            commons.home();
//            Commons.sleepWait(2000);
//            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
//            Commons.sleepWait(2000);
//            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$=':nvgpgl2_groupNode_tools']")));
//            System.out.println("scrolling function reached");
//            driver.findElement(By.cssSelector("*[id$=':nvgpgl2_groupNode_tools']")).click();
//            Commons.sleepWait(2000);
//            System.out.println("tools clicked");
//            driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")).click();
//            System.out.println("security console clicked");
//        }

        try{
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")));
            Commons.sleepWait(1500);
            driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")).click();
            Commons.sleepWait(1500);
        } catch (Exception e) {
            commons.home();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            Commons.scrollToView(driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")));
            Commons.sleepWait(1500);
            driver.findElement(By.cssSelector("*[id$=':nv_ASE_FUSE_SECURITY_CONSOLE::icon']")).click();
            Commons.sleepWait(1500);
        }

        Commons.sleepWait(2000);
        // close any dialog box if open
//        try {
//            if (driver.findElement(By.cssSelector("*[id$=':MAyes']")).isDisplayed()) {
//                driver.findElement(By.cssSelector("*[id$=':MAyes']")).click();
//            }
//        } catch (Exception ignored) {
//
//        }
//        Commons.sleepWait(2000);
//        try {
//            if(driver.findElement(By.cssSelector("*[id$='_FOd1::msgDlg::close']")).isDisplayed()){
//                driver.findElement(By.cssSelector("*[id$='_FOd1::msgDlg::close']")).click();
//            }
//        } catch (Exception ignored) {
//
//        }
//        Commons.sleepWait(2000);

        // user tab
        driver.findElement(By.cssSelector("*[id$=':_FOTsdiASE_FUSE_UM_WORKAREA::ti']")).click();
    }

    public static void uncheckActive(String pid){
        // click on user tab, clear and search user
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).sendKeys(pid + Keys.RETURN);
        Commons.sleepWait(2000);

        try {
            driver.findElement(By.xpath("//*[text()='" + pid + "']")).click();
        }
        catch (Exception e){
            Commons.scrollToView(driver.findElement(By.xpath("//*[text()='" + pid + "']")));
            driver.findElement(By.xpath("//*[text()='" + pid + "']")).click();
        }
        // edit
        driver.findElement(By.cssSelector("*[id$=':sp1:cb6']")).click();
        if (driver.findElement(By.cssSelector("*[id$=':sp1:sbc4::content']")).isSelected()) {
            // make inactive
            driver.findElement(By.cssSelector("*[id$=':sp1:sbc4::Label0']")).click();
            Commons.sleepWait(2000);
        }
        if (driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).isEnabled()) {
            driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).click();
        } else {
            driver.findElement(By.cssSelector("*[id$=':sp1:canBtn']")).click();
        }
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':sp1:cb2']")).click();

        System.out.println("User " + pid + " is inactivated");

    }

    public static void checkActive(String pid){
        // click on user tab, clear and search user
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).sendKeys(pid + Keys.RETURN);
        Commons.sleepWait(2000);

        try {
            driver.findElement(By.xpath("//*[text()='" + pid + "']")).click();
        }
        catch (Exception e){
            Commons.scrollToView(driver.findElement(By.xpath("//*[text()='" + pid + "']")));
            driver.findElement(By.xpath("//*[text()='" + pid + "']")).click();
        }

        // edit
        driver.findElement(By.cssSelector("*[id$=':sp1:cb6']")).click();
        if (!driver.findElement(By.cssSelector("*[id$=':sp1:sbc4::content']")).isSelected()) {
            // make active
            driver.findElement(By.cssSelector("*[id$=':sp1:sbc4::Label0']")).click();
            Commons.sleepWait(2000);
        }
        if (driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).isEnabled()) {
            driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).click();
        } else {
            driver.findElement(By.cssSelector("*[id$=':sp1:canBtn']")).click();
        }
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':sp1:cb2']")).click();

        System.out.println("User " + pid + " is activated");

    }

    private static void assignRoleToUser(String pid, String role, String email) {
        // click on user tab, clear and search user
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).sendKeys(pid + Keys.RETURN);
        Commons.sleepWait(2000);
        //driver.findElement(By.cssSelector("*[id$=':sp1:usrList:0:cl1']")).click();

        // manually select the user required

        //edit and add role button
        driver.findElement(By.cssSelector("*[id$=':sp1:cb6']")).click();


        if(role.equalsIgnoreCase("Rezi BMS View Only")) {
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS View Only");
            assignSelectedRole(role, pid);
        }
        else if(role.equalsIgnoreCase("Rezi BMS Approver")) {
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS View Only");
            assignSelectedRole(role, pid);

            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS Approver");
            assignSelectedRole(role, pid);
        }
        else if(role.equalsIgnoreCase("Rezi BMS Request Changes")) {
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS View Only");
            assignSelectedRole(role, pid);

            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS Approver");
            assignSelectedRole(role, pid);

//            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
//            Commons.sleepWait(2000);
//            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi PLM Request Changes");
//            assignSelectedRole(role, pid);
        }
        else if(role.equalsIgnoreCase("Rezi BMS Author")) {
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS View Only");
            assignSelectedRole(role, pid);

            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS Approver");
            assignSelectedRole(role, pid);

//            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
//            Commons.sleepWait(2000);
//            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi PLM Request Changes");
//            assignSelectedRole(role, pid);

            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS Author");
            assignSelectedRole(role, pid);
        }
        else if(role.equalsIgnoreCase("Rezi BMS Analyst")) {
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS View Only");
            assignSelectedRole(role, pid);

            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS Approver");
            assignSelectedRole(role, pid);

//            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
//            Commons.sleepWait(2000);
//            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi PLM Request Changes");
//            assignSelectedRole(role, pid);

            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS Author");
            assignSelectedRole(role, pid);

            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:cb1']")).click();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys("Rezi BMS Analyst");
            assignSelectedRole(role, pid);
        }
        // save and close -> done
        if(driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).isEnabled()){
            driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).click();
        }
        else{
            driver.findElement(By.cssSelector("*[id$=':sp1:canBtn']")).click();
        }
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':sp1:cb2']")).click();

        System.out.println("Role "+role+" assigned for "+ email);
    }

    public static void addAllRoles(String role){
        String[] roles = role.split("\\+");

        // driver.findElement(By.cssSelector("*[id$='ID_HERE']")).click();

        Commons.sleepWait(2000);
        // edit
        driver.findElement(By.cssSelector("*[id$=':sp1:cb6']")).click();

        // add role
        driver.findElement(By.cssSelector("*[id$=':sp1:cb1']")).click();
        boolean roleAdded = false;

        for(String userRole : roles){
            // remove spaces if any in the roles
            userRole = userRole.trim();
            boolean assignment = assignSingleRole(userRole);
            roleAdded = (roleAdded || assignment);
        }

        // done
        driver.findElement(By.cssSelector("*[id$=':sp1:rhSrCan']")).click();
        Commons.sleepWait(2000);
        // save and close -> done
        if(roleAdded)
            driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).click();
        else
            driver.findElement(By.cssSelector("*[id$=':sp1:canBtn']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':sp1:cb2']")).click();

    }

    public static boolean assignSingleRole(String role){

        // search for roles
        driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':_FOTr1:3:sp1:urSrcBx::content']")).sendKeys(role);

        Commons.sleepWait(2000);
        // select the role
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:cil121::icon']")).click();
        Commons.sleepWait(2000);
        // click on required role
        try {
            // driver.findElement(By.cssSelector("*[id$=':sp1:rhRsLst:0:li1']")).click();
            role = role.toLowerCase();
            driver.findElement(By.xpath("//*[translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + role + "']")).click();
        }
        catch(Exception e){
            System.out.println("The role '" + role + "'either does not exist or already assigned. Taking screenshot. Refer manually");
            Commons.takeFullScreenshot();
            return false;
        }
        // add role membership
        driver.findElement(By.cssSelector("*[id$=':sp1:rhSrAdd']")).click();
        return true;
    }

    private static void assignSelectedRole(String role, String pid){
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:cil121::icon']")).click();
        Commons.sleepWait(2000);
        try{
            // If the user is already assigned that role then exception will occur

            // click on the second option
            // driver.findElement(By.cssSelector("*[id$=':sp1:rhRsLst:1:li1']")).click();

            // click on the first option
            if(driver.findElement(By.cssSelector("*[id$=':sp1:rhRsLst:0:li1']")).getText().contains("DNU")){
                driver.findElement(By.cssSelector("*[id$=':sp1:rhSrCan']")).click();
                Commons.sleepWait(2000);
                // add role
                return;
            }
            driver.findElement(By.cssSelector("*[id$=':sp1:rhRsLst:0:li1']")).click();
        }
        catch(Exception e){
            driver.findElement(By.cssSelector("*[id$=':sp1:rhSrCan']")).click();
            try{
//                    UAT
//                    if(driver.findElement(By.cssSelector("*[id$=':sp1:rlsTab::db']")).findElement(By.className("x2hl")).getText().equalsIgnoreCase("Rezi PLM View Only")){
//                        System.out.println("read role already assigned for "+ pid);
//                    }
//                    PRD
                if(driver.findElement(By.cssSelector("*[id$=':sp1:rlsTab::db']")).findElement(By.className("x2hi")).getText().equalsIgnoreCase("Rezi PLM View Only")){
                    System.out.println("read role already assigned for "+ pid);
                }
            } catch (Exception ex) {
                Commons.takeFullScreenshot();
                Assert.fail("Screenshot taken. Please check manually if the role is already assigned");
            }

            // cancel and Done
            driver.findElement(By.cssSelector("*[id$=':sp1:canBtn']")).click();
            driver.findElement(By.cssSelector("*[id$=':sp1:cb2']")).click();
            Commons.sleepWait(2000);
            return;
        }

        Commons.sleepWait(2000);
        // add and done
        driver.findElement(By.cssSelector("*[id$=':sp1:rhSrAdd']")).click();
        driver.findElement(By.cssSelector("*[id$=':sp1:rhSrCan']")).click();

        Commons.sleepWait(2000);

        System.out.println("Role "+role+" assigned for "+ pid);
    }

    @Then("^username (.*) is reset with DNU$")
    public static void userNameReset(String userName){

        reachUserSearchPage();

        renameUser(userName);
    }

    public static void renameUser(String userName){
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':sp1:f1:it1::content']")).sendKeys(userName + Keys.RETURN);
        Commons.sleepWait(2000);

        // manually select the username from the list

        // edit
        driver.findElement(By.cssSelector("*[id$=':sp1:cb6']")).click();
        driver.findElement(By.cssSelector("*[id$=':sp1:itUsrLogin::content']")).click();
        driver.findElement(By.cssSelector("*[id$=':sp1:itUsrLogin::content']")).sendKeys("_DNU");
        driver.findElement(By.cssSelector("*[id$=':sp1:eitPhone::content']")).click();
        Commons.sleepWait(2000);
        driver.findElement(By.cssSelector("*[id$=':sp1:saveBtn']")).click();
    }

    @When("^edit a worker record with email (.*) to add department (.*) and location (.*) with code (.*) with manager (.*)$")
    public static void editWorkerRecord (String email, String department, String location, String locationCode, String manager) {
        Commons commons = new Commons();
        Commons.home();
        Commons.sleepWait(2000);
        try{
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
            Commons.sleepWait(1500);
        } catch (Exception e) {
            commons.home();
            Commons.sleepWait(2000);
            driver.findElement(By.cssSelector("*[id*='UISmmLink']")).click();
            Commons.sleepWait(1500);
            if(driver.findElement(By.cssSelector("*[id$=':nvcl1']")).getText().equalsIgnoreCase("Show More"))
            {
                driver.findElement(By.cssSelector("*[id$=':nvcl1']")).click();
                Commons.sleepWait(1500);
            }
            driver.findElement(By.cssSelector("*[id$=':nv_itemNode_manager_resources_manage_users::icon']")).click();
            Commons.sleepWait(1500);
        }

        driver.findElement(By.cssSelector("*[id$=':AP1:qq1:criterionValue0::content']")).clear();
        driver.findElement(By.cssSelector("*[id$=':AP1:qq1:criterionValue0::content']")).sendKeys(email);
        driver.findElement(By.cssSelector("*[id$=':AP1:qq1::search_icon']")).click();
        Commons.sleepWait();
        try {
            driver.findElement(By.cssSelector("*[id$=':pgl30']")).click();
            Commons.sleepWait();
        } catch (NullPointerException | NoSuchElementException e){
            System.out.println("The user may not belong to Resideo. Using just the first and last name combination.");
            driver.findElement(By.cssSelector("*[id$=':AP1:qq1:criterionValue0::content']")).clear();
            driver.findElement(By.cssSelector("*[id$=':AP1:qq1:criterionValue0::content']")).sendKeys(email.split("@")[0]);
            driver.findElement(By.cssSelector("*[id$=':AP1:qq1::search_icon']")).click();
            Commons.sleepWait();
            driver.findElement(By.cssSelector("*[id$=':pgl30']")).click();
            Commons.sleepWait();
        }
        // location and department
        if(!department.equalsIgnoreCase("<Department>")) {
            Commons.findElementByLabel("Department").clear();
            Commons.findElementByLabel("Department").sendKeys(department + Keys.TAB);
            Commons.sleepWait(2000);
        }
//        if(!locationCode.equalsIgnoreCase("<LocationCode>")) {
//            Commons.findElementByLabel("Location").clear();
//            Commons.findElementByLabel("Location").sendKeys(department + Keys.TAB);
//            Commons.sleepWait(2000);
//        }
        if(!location.equalsIgnoreCase("<Location>")) {
            Commons.findElementByLabel("Location").clear();
            Commons.findElementByLabel("Location").sendKeys(location + Keys.TAB);
            Commons.sleepWait();
            try{
                Commons.findElementByExactName("Search and Select: Location");
                // driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId:value00::content']")).sendKeys(location);
                driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId:value10::content']")).sendKeys(locationCode);
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$=':locationId::_afrLovInternalQueryId::search']")).click();
                Commons.sleepWait();
                driver.findElement(By.cssSelector("*[id$=':locationId_afrLovInternalTableId::db']")).click();
                Commons.sleepWait(2000);
                Commons.findElementByExactName("OK").click();
                Commons.sleepWait();
            } catch (NoSuchElementException ignore) {

            }
        }
        if(!manager.equalsIgnoreCase("<Manager>")) {
            String assignedManager = Commons.findElementByLabel("Manager").getDomAttribute("value");
            if(assignedManager != null && assignedManager.equalsIgnoreCase(manager)){
                System.out.println("Manager already assigned.");
            }
            else {
                Commons.findElementByLabel("Manager").clear();
                Commons.findElementByLabel("Manager").sendKeys(manager.split(",")[0] + Keys.TAB);
                Commons.sleepWait();
                assignedManager = Commons.findElementByLabel("Manager").getDomAttribute("value");
                if (assignedManager == null) {
                    Commons.findElementByExactName("Cancel").click();
                    Commons.findElementByExactName("ancel").click();

                    Commons.sleepWait();
                    Assert.fail("Manager was not found.");
                    return;
                }
                if (!assignedManager.equalsIgnoreCase(manager)) {
                    System.out.println("Assigned manager: " + assignedManager);
                    System.out.println("Manager in Excel: " + manager);
                    Commons.findElementByExactName("ancel").click();
                    Commons.sleepWait();
                    Assert.fail("Manager was not assigned. Difference in name OR same last name for multiple records could be the issue.");
                    return;
                }
            }
        }
        Commons.findElementByExactName("ave and Close").click();
    }
}
