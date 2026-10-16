package utils;

import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static utils.Commons.*;

public class JIRAUtils {

    public static String accessToken = null;
    public static String userName = creds.getProperty("JIRA_USER_NAME");
    public static String APIToken = creds.getProperty("API_TOKEN");
    public static String encodedAPIToken = Base64.getEncoder().encodeToString((userName+":"+APIToken).getBytes());

    public static void authenticate() throws IOException, InterruptedException {

        JSONObject body = new JSONObject();
        body.put("client_id", creds.getProperty("CLIENT_ID"));
        body.put("client_secret", creds.getProperty("CLIENT_SECRET"));

        HttpRequest postRequest = HttpRequest.newBuilder()
                                    .uri(getURL("https://xray.cloud.getxray.app/api/v2/authenticate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        accessToken = postResponse.body().split("\"")[1];
    }

    public static String createTestPlan() throws IOException, InterruptedException {

        String TPName;
        JSONObject body = new JSONObject();

        JSONObject project = new JSONObject();
        JSONObject key = new JSONObject();
        key.put("key", "PLM");
        project.put("project", key);

        JSONObject issueType = new JSONObject();
        JSONObject name = new JSONObject();
        name.put("name", "Test Plan");
        issueType.put("issuetype", name);

        if(JIRATestPlan.equalsIgnoreCase("false")){
            TPName = userType + " 2025";
        }
        else{
            TPName = Commons.today();
        }
        JSONObject fields = new JSONObject();
        fields.put("project",  key);
        fields.put("summary",  "Test Plan for "+ TPName);
        fields.put("description",  "description");
        fields.put("issuetype",  name);

        body.put("fields", fields);

        if(accessToken==null){
            authenticate();
        }

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(getURL("https://resideo.atlassian.net/rest/api/2/issue"))
                .header("Authorization", "Basic "+ encodedAPIToken)
//                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        JSONObject result = new JSONObject(postResponse.body());
        System.out.println(result.toString());
        TPName = result.get("key").toString();
        if(JIRATestPlan.equalsIgnoreCase("false")){
            setTestPlan(TPName);
        }
        return TPName;
    }

    private static void setTestPlan(String tpName) {
        if(userType.equalsIgnoreCase("alpha")){
            config.setProperty("ALPHA_PLAN", tpName);
            System.out.println("Test plan for AlPHA_PLAN is updated to "+ tpName);
        }
        else if(userType.equalsIgnoreCase("beta")){
            config.setProperty("BETA_PLAN", tpName);
            System.out.println("Test plan for BETA_PLAN is updated to "+ tpName);
        }
        else if(userType.equalsIgnoreCase("charlie")){
            config.setProperty("CHARLIE_PLAN", tpName);
            System.out.println("Test plan for CHARLIE_PLAN is updated to "+ tpName);
        }
        else if(userType.equalsIgnoreCase("delta")){
            config.setProperty("DELTA_PLAN", tpName);
            System.out.println("Test plan for DELTA_PLAN is updated to "+ tpName);
        }
        else if(userType.equalsIgnoreCase("gamma")){
            config.setProperty("GAMMA_PLAN", tpName);
            System.out.println("Test plan for GAMMA_PLAN is updated to "+ tpName);
        }
        else if(userType.equalsIgnoreCase("echo")){
            config.setProperty("ECHO_PLAN", tpName);
            System.out.println("Test plan for ECHO_PLAN is updated to "+ tpName);
        }
    }

    private static URI getURL(String url){

            try {
                return new URI(url);
            } catch (URISyntaxException e) {
                System.out.println("The URL formation is not correct and threw exception");
                Assert.fail("The URL formation is not correct and threw exception");
                return null;
            }
    }

    public static void uploadResults(String file) throws IOException, InterruptedException {
        String testPlanID;
        if(config.getProperty("NEW_TEST_PLAN").equalsIgnoreCase("true") || Commons.JIRATestPlan.equalsIgnoreCase("false")){
            // create new test plan using API
            testPlanID = createTestPlan();
            System.out.println("New test plan created is " +testPlanID);
        }
        else{
            testPlanID = JIRATestPlan;
            System.out.println("Existing test plan selected is " +testPlanID);
        }
        Commons.sleepWait(10000);
        Path resultsPath = Paths.get(System.getProperty("user.dir") + "/target/"+ file);
        Path infoPath = Paths.get(System.getProperty("user.dir") + "/src/test/resources/info.json");
        JSONObject info = new JSONObject(new String(Files.readAllBytes(Paths.get(infoPath.toUri()))));

        info.getJSONObject("xrayFields").put("testPlanKey", testPlanID);
        info.getJSONObject("fields").put("summary", "Test Execution for "+ Commons.today());

        Files.write(Paths.get(infoPath.toUri()), info.toString().getBytes(StandardCharsets.UTF_8));

        // send http request with form data
        String boundary = "Boundary-" + System.currentTimeMillis();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(
                "--" + boundary + "\r\n" +
                        "Content-Disposition: form-data; name=\"info\"; filename=\"info.json\"\r\n" +
                        "Content-Type: application/json\r\n\r\n" +
                        Files.readString(infoPath) + "\r\n" +
                        "--" + boundary + "\r\n" +
                        "Content-Disposition: form-data; name=\"results\"; filename=\"cucumber-results.xml\"\r\n" +
                        "Content-Type: application/xml\r\n\r\n" +
                        Files.readString(resultsPath) + "\r\n" +
                        "--" + boundary + "--\r\n"
        );

        if(accessToken==null){
            authenticate();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(getURL("https://xray.cloud.getxray.app/api/v2/import/execution/junit/multipart"))
//                .header("Authorization", "Basic "+ encodedAPIToken)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(bodyPublisher)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response Code: " + response.statusCode());
        System.out.println(response.body());
    }
}
