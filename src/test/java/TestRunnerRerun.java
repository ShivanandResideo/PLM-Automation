import org.junit.AfterClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utils.Commons;
import utils.JIRAUtils;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@target/rerun.txt", // Path to the file with failed scenarios
        glue = {"stepDefinitions"},
        plugin = {
                "pretty"
                ,"html:target/reports/RerunHTMLReports.html"
                ,"junit:target/cucumber-rerunResults.xml"
        }
)
public class TestRunnerRerun {

        @AfterClass
        public static void terminate(){
                System.out.println(" logout logic is yet to be written");
                if(Commons.config.getProperty("JIRA_UPLOAD").equalsIgnoreCase("true")){
                        try{
                                JIRAUtils.uploadResults("cucumber-rerunResults.xml");
                        }
                        catch(IOException | InterruptedException e){
                                System.out.println("Exception occurred while uploading results to JIRA");
                                e.printStackTrace();
                        }
                }
                else{
                        System.out.println("The flag in the config.properties to upload results to JIRA is set to false, hence skipping results upload.");
                }
        }

}