import com.github.mkolisnyk.cucumber.runner.AfterSuite;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import stepDefinitions.CommonSteps;
import utils.Commons;
import utils.JIRAUtils;

import java.io.IOException;

@RunWith(Cucumber.class)
//@RunWith(ExtendedCucumber.class)
//@ExtendedCucumberOptions(retryCount = 3 // Number of retries
//                        ,detailedReport = true
//                        ,detailedAggregatedReport = true
//                        ,overviewReport = true
//                        ,usageReport = true
//                        ,toPDF = true
//                        ,outputFolder = "target"
//)
@CucumberOptions(features="src/test/java/features"
                    ,glue={"stepDefinitions"}
//                    ,monochrome = true
                    ,plugin = {"pretty"
                                ,"html:target/reports/HTMLReports.html"
                                ,"rerun:target/rerun.txt"
                                ,"json:target/cucumber.json"
                                ,"junit:target/cucumber-results.xml"
//                                ,"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"

                                }
                    ,tags = "@Delta"
                                               // available tags:
                                              // @Smoke, @User, @Now, @test, @UserRenameInactive
                                             // @ChangeOrder, @Items, @Docs, @Home, @Manufacturer, @MParts, @All
                                            // @Delta, @Gamma, @Charlie, @Alpha
                                           // May28, ApprovalMatrix, DesignTeam
                                          // "@Gamma or @Delta"
                                         // autoNumbering, userCreation, Beta1
                                        // uploadResult
)

public class TestRunner {

    @AfterClass
    public static void terminate(){
        System.out.println("Logout logic is yet to be written");

        CommonSteps.resultUpload();

    }
}
