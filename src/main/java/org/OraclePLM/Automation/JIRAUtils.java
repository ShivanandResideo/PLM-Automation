package org.OraclePLM.Automation;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class JIRAUtils {
    public static String accessToken;
    public static String encodedAPIToken;
    public static String testPlan;
    public static String testExecution;
    public static String testSet;

    private static void authenticate() throws IOException, InterruptedException {

        JSONObject body = new JSONObject();
        body.put("client_id", "EE929A69D74C41B0B964AEA460F362CC");
        body.put("client_secret", "ae7bc1937116173ae39c350117e39cf5783f76b39dc332c410efa9898ae7db0c");

        String UserName = "SV_Oracleplmqa@Resideo.com";
        String APIToken = "ATATT3xFfGF0o4McvRhq-ihxdyiGEccVXqn_WgE6N_i_6iyNdmS-t1s_eFEvPVCTuHSDbWV26dNp6omXsfLvvn1YSmvNsqX-dSLJz5IPsVjPzB2MO4dxulIwVWm4nfWTTv4VUDMtApm19Uckqo8Kjdx8JPOQTatUc9F9r9p9hleb0Q-pOhDjhbo=A87F6C62";
        encodedAPIToken = Base64.getEncoder().encodeToString((UserName+":"+APIToken).getBytes());

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(getURL("https://xray.cloud.getxray.app/api/v2/authenticate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        accessToken = postResponse.body().split("\"")[1];
    }

    public static void createTestPlan() throws IOException, InterruptedException {
        authenticate();

        JSONObject body = new JSONObject();

        JSONObject key = new JSONObject();
        key.put("key", "PLM");

        JSONObject name = new JSONObject();
        name.put("name", "Test Plan");

        JSONObject fields = new JSONObject();
        fields.put("project",  key);
        fields.put("summary",  "TP1");
        fields.put("description",  "description");
        fields.put("issuetype",  name);

        body.put("fields", fields);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(getURL("https://resideo.atlassian.net/rest/api/2/issue"))
                .header("Authorization", "Basic "+ encodedAPIToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(body.toString());
        System.out.println(postResponse.body());
        System.out.println(accessToken);
        System.out.println(encodedAPIToken);
    }

    public static void addTestToTestPlan(){

    }

    private static URI getURL(String url){
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            System.out.println("The URL formation is not correct and threw exception");
            //Assert.fail("The URL formation is not correct and threw exception");
            return null;
        }
    }

//    public static void uploadResultFile() {
//
//        // send POST request to upload result to a test plan
//        String url = "https://xray.cloud.getxray.app/api/v2/import/execution/junit/multipart";
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiIzMDE1ODEyNS0wNGExLTMzMWMtOTEyYy0xZTA1ZTc3ZjE3YzIiLCJhY2NvdW50SWQiOiI3MTIwMjA6YTg3OWVmMzQtNWQzMC00Zjc1LWI0ZWMtMDVhZDI0M2UzODQ3IiwiaXNYZWEiOmZhbHNlLCJpYXQiOjE3NDAwMzczNDcsImV4cCI6MTc0MDEyMzc0NywiYXVkIjoiRUU5MjlBNjlENzRDNDFCMEI5NjRBRUE0NjBGMzYyQ0MiLCJpc3MiOiJjb20ueHBhbmRpdC5wbHVnaW5zLnhyYXkiLCJzdWIiOiJFRTkyOUE2OUQ3NEM0MUIwQjk2NEFFQTQ2MEYzNjJDQyJ9.aRGufLxi8G3vNn-GAfDFi31MJ8s5_AKCkzqhWUCch4Y";
//        String boundary = "Boundary-" + System.currentTimeMillis();
//
//        try {
//            URL obj = new URL(url);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Authorization", "Bearer " + token);
//            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//            con.setDoOutput(true);
//
//            try (DataOutputStream request = new DataOutputStream(con.getOutputStream())) {
//                // Add 'info' file
//                request.writeBytes("--" + boundary + "\r\n");
//                -data; name=\"info\"; filename=\"info.json\"\r\n");
//                request.writeBytes("Content-Type: application/json\r\n\r\n");
//                request.write(new FileInputStream(new File("C:/Users/Shivanand K S/Desktop/jira/info.json")).readAllBytes());
//                request.writeBytes("\r\n");
//
//                // Add 'results' file
//                request.writeBytes("--" + boundary + "\r\n");
//                request.writeBytes("Content-Disposition: form-data; name=\"results\"; filename=\"cucumber-results.xml\"\r\n");
//                request.writeBytes("Content-Type: application/xml\r\n\r\n");
//                request.write(new FileInputStream(new File("C:/Users/Shivanand K S/Desktop/jira/cucumber-results.xml")).readAllBytes());
//                request.writeBytes("\r\n");
//
//                request.writeBytes("--" + boundary + "--\r\n");
//                request.flush();
//            }
//
//            int responseCode = con.getResponseCode();
//            System.out.println("Response Code: " + responseCode);
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            System.out.println(response.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void uploadResults() throws IOException, InterruptedException {

        // Check properties file to see if the upload of the results is required
//        if (Commons.config.getProperty("JIRA_UPLOAD")) {

//        if(Commons.config.getProperty("NEW_TEST_PLAN")){

            // depending on the user (delta gamma etc) upload to a specific test plan

            // take the info.json file contents and edit the "fields.summary" to have today's date and time in human readable format

            // update the test plan as per user OR create new one if not present

            // upload the results

            String url = "https://xray.cloud.getxray.app/api/v2/import/execution/junit/multipart";
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiIzMDE1ODEyNS0wNGExLTMzMWMtOTEyYy0xZTA1ZTc3ZjE3YzIiLCJhY2NvdW50SWQiOiI3MTIwMjA6YTg3OWVmMzQtNWQzMC00Zjc1LWI0ZWMtMDVhZDI0M2UzODQ3IiwiaXNYZWEiOmZhbHNlLCJpYXQiOjE3NDAwMzczNDcsImV4cCI6MTc0MDEyMzc0NywiYXVkIjoiRUU5MjlBNjlENzRDNDFCMEI5NjRBRUE0NjBGMzYyQ0MiLCJpc3MiOiJjb20ueHBhbmRpdC5wbHVnaW5zLnhyYXkiLCJzdWIiOiJFRTkyOUE2OUQ3NEM0MUIwQjk2NEFFQTQ2MEYzNjJDQyJ9.aRGufLxi8G3vNn-GAfDFi31MJ8s5_AKCkzqhWUCch4Y";

            Path infoPath = Paths.get(System.getProperty("user.dir") + "/src/test/resources/info.json");
            Path resultsPath = Paths.get(System.getProperty("user.dir") + "/src/test/resources/cucumber-results.xml");

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

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(bodyPublisher)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Code: " + response.statusCode());
            System.out.println(response.body());
        }
//    }}

    public static void createBugs() {

        //extract all the failures only from test plan

        //create self-assigned bugs for all the failures

    }
}
