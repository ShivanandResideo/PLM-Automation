//package utils;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Properties;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.honeywell.commons.coreframework.FrameworkGlobalVariables;
//import com.honeywell.commons.coreframework.SuiteConstants;
//import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
//import com.honeywell.commons.coreframework.TestCaseInputs;
//import com.honeywell.commons.coreframework.TestCases;
//import com.honeywell.commons.mobile.Mobile;
//import com.honeywell.commons.report.PublishReportOffline.TestInformation;
//import com.honeywell.commons.report.rebot.ReportConstants;
//
//import okhttp3.Credentials;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class XRayUtils {
//    private static String JIRA_URL;
//    private static String jiraUserID;
//    private static String JIRA_AUTH_CODE;
//    public static String PLAN_NAME = null;
//    public static HashMap<String, String> XRAY_PLAN_IDS = new HashMap<>();
//    private static Semaphore semaphore = new Semaphore(1);
//    private static int MAX_RETRY = 3;
//    public static HashMap<String, Boolean> ADD_TESTCASE = new HashMap<>();
//    public static boolean PRESERVE_XRAY_REPORT = false;
//    public static JSONObject testCaseUpdate=new JSONObject();
//    public static boolean testcaseupdateFirst=true;
//    public static String Test_Plan_ID="";
//    public static String Test_Execution_ID="";
//    // private static String FIXED_XRAY_PLAN = FrameworkGlobalVariables.BLANK;
//
//    public static int handleXrayPlanName(String[] commandLineArguments, int currentIndex){
//        ++currentIndex;
//        if (currentIndex < commandLineArguments.length) {
//
//            String xRayPlanName = commandLineArguments[currentIndex].trim();
//
//            if (xRayPlanName.isEmpty() && xRayPlanName.startsWith("--")) {
//                System.out.println(
//                        "XRay Plan Name parameter needs value 'Xray Name'. Required format - XRay Plan Name to be created.");
//                --currentIndex;
//            } else {
//                PLAN_NAME = xRayPlanName;
//            }
//        }
//
//        return currentIndex;
//    }
//
//    public static String getPlanName(){
//        return System.getProperty("testPlanName");
//    }
//
////    public static void uploadResultOffLine(String rootFolder) {
////
////        if(initJIRACredentials()){
////            PublishReportOffline publishOffline = new PublishReportOffline(rootFolder);
////
////            List<TestInformation> testInfo = publishOffline.getTestCases();
////
////            String user = "aterBuild";
////
////            try{
////                user = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "User");
////            }catch(Exception e){
////                // User default user.
////            }
////
////
////            HelperThreads[] threads = new HelperThreads[testInfo.size()];
////
////            for (int threadIndex = 0; threadIndex < testInfo.size(); ++threadIndex) {
////                threads[threadIndex] = new HelperThreads(testInfo.get(threadIndex), user);
////                threads[threadIndex].start();
////            }
////
////            for (int threadIndex = 0; threadIndex < testInfo.size(); ++threadIndex) {
////                try {
////                    threads[threadIndex].join();
////                } catch (InterruptedException e) {
////                    // Nothing we can do for this exception
////                }
////            }
////        }
////
////        System.exit(0);
////    }
//
//    public static String getXrayPlanID(TestCases testCase) {
//        return getPlanID(testCase);
//    }
//
//    public static void setXrayPlanID(String platform, String xRayID) {
//        XRAY_PLAN_IDS.put(platform.toUpperCase(), xRayID);
//    }
//
//    public static void preserveResultJSON() {
//        PRESERVE_XRAY_REPORT = true;
//    }
//
//    public static int xRayPlanIDParser(String[] commandLineArguments, int currentIndex) {
//        ++currentIndex;
//        if (currentIndex < commandLineArguments.length) {
//
//            String xRayPlanIDs = commandLineArguments[currentIndex].trim();
//
//            if (xRayPlanIDs.isEmpty() && xRayPlanIDs.startsWith("--")) {
//                System.out.println(
//                        "XRay Plan ID parameter needs value 'Xray ID'. Required format - <Platform1>:<XRay-ID>,<Platform2>:<XRay-ID>...");
//                --currentIndex;
//            } else {
//
//                String[] splittedXrayIds = xRayPlanIDs.split(",");
//                String[] splittedPlatformXrayID;
//                for (String splittedXrayId : splittedXrayIds) {
//                    splittedPlatformXrayID = splittedXrayId.split(":");
//                    if (splittedPlatformXrayID.length > 0) {
//                        XRAY_PLAN_IDS.put(splittedPlatformXrayID[0].toUpperCase(), splittedPlatformXrayID[1]);
//                    } else {
//                        System.out.println(
//                                "XRay Plan ID parameter needs value 'Xray ID' in format - <Platform1>:<XRay-ID>,<Platform2>:<XRay-ID>...");
//
//                    }
//                }
//            }
//        }
//
//        return currentIndex;
//    }
//
//    public static String offlineRootFolder(String[] commandLineArguments, int currentIndex) {
//        ++currentIndex;
//        if (currentIndex < commandLineArguments.length) {
//
//            String rootFolder = commandLineArguments[currentIndex].trim();
//
//            if (rootFolder.isEmpty() && rootFolder.startsWith("--")) {
//                System.out.println(
//                        "Root Folder path is needed to upload the result offline.");
//                System.exit(0);
//            } else {
//                return rootFolder;
//            }
//        }
//
//        return FrameworkGlobalVariables.BLANK;
//    }
//
//    public static String getTestPlanURL(TestCases testCase) {
//        return String.format("%s/browse/%s", JIRA_URL, getPlanID(testCase));
//    }
//
//    public static String getTestExecutionURL(TestCases testCase) {
//        String testExecID = testCase.getTestExecutionID();
//        if (testExecID.isEmpty()) {
//            return testExecID;
//        } else {
//            return String.format("%s/browse/%s", JIRA_URL, testExecID);
//        }
//
//    }
//
//    public static int setJIRACredentials(String[] commandLineArguments, int currentIndex) {
//
//        ++currentIndex;
//        if (currentIndex < commandLineArguments.length) {
//
//            String credentials = commandLineArguments[currentIndex].trim();
//            if (credentials.isEmpty() && credentials.startsWith("--")) {
//                System.out.println("Jira Credentials should be provided in <UserID>:<Password> format.");
//                --currentIndex;
//            } else {
//
//                Pattern pattern = Pattern.compile(".+:.+");
//                Matcher matcher = pattern.matcher(credentials);
//                if (matcher.find()) {
//                    jiraUserID = credentials.split(":")[0];
//                    JIRA_AUTH_CODE = new String("Basic " + Base64.getEncoder().encodeToString(credentials.getBytes()));
//                } else {
//                    System.out.println("Jira Credentials should be provided in <UserID>:<Password> format.");
//                }
//            }
//        }
//
//        return currentIndex;
//    }
//
//    private static String getPlanID(TestCases testCase) {
//
//        // return FIXED_XRAY_PLAN;
//        String planID = FrameworkGlobalVariables.BLANK;
//        if (FrameworkGlobalVariables.PUBLISH_RESULT) {
//            synchronized (XRayUtils.class) {
//
//                String testTargetPlatform = getTargetPlatform(testCase);
//
//                if (XRAY_PLAN_IDS.containsKey(testTargetPlatform)) {
//                    planID = XRAY_PLAN_IDS.get(testTargetPlatform);
//                } else {
//                    if (FrameworkGlobalVariables.PUBLISH_RESULT) {
//                        try {
//                            planID = createTestPlan(0, testCase);
//                            if (planID == null) {
//                                // Nothing to do skip
//                            } else {
//                                if (planID.isEmpty()) {
//                                } else {
//                                    XRAY_PLAN_IDS.put(testTargetPlatform, planID);
//                                }
//                            }
//
//                        } catch (Exception e) {
//                        }
//                    }
//                }
//            }
//        }
//
//        return planID;
//
//    }
//
//    public static String getTargetPlatform(TestCases testCase) {
//        String targetName = FrameworkGlobalVariables.BLANK;
//        TestCaseInputs inputs = testCase.getTestCaseInputs();
//
//        if (inputs.getInputValue(TestCaseInputs.DEVICE_TYPE).toUpperCase().contains("BROWSER")) {
//            targetName = String.format("%s-%s", inputs.getInputValue(TestCaseInputs.OS_NAME),
//                    inputs.getInputValue(TestCaseInputs.DEVICE_NAME));
//        } else {
//            targetName = inputs.getInputValue(TestCaseInputs.OS_NAME);
//        }
//
//        return targetName.toUpperCase();
//    }
//
//    public static void addTestToTestPlan(TestCases testCase) {
//
//
//        initJIRACredentials();
//
//        if (ADD_TESTCASE.containsKey(getTargetPlatform(testCase))) {
//            if (ADD_TESTCASE.get(getTargetPlatform(testCase))) {
//
//            } else {
//                return;
//            }
//        }
//
//        boolean hasPassKey = false;
//        try {
//
//            hasPassKey = semaphore.tryAcquire(1, TimeUnit.MINUTES);
//
//            String testID = testCase.getXRayTestID();
//
//            if (testID == null || testID.isEmpty()) {
//                return;
//            }
//
//            String planID = FrameworkGlobalVariables.BLANK;
//
//            synchronized (XRayUtils.class) {
//                planID = getPlanID(testCase);
//            }
//
//            if (planID == null || planID.isEmpty()) {
//                return;
//            }
//
//            String jiraUrl = String.format("%s/rest/raven/1.0/api/testplan/%s/test", JIRA_URL, planID);
//
//            JSONObject fields = new JSONObject();
//            JSONArray tests = new JSONArray();
//            tests.put(testID);
//            fields.put("add", tests);
//
//            HttpURLConnection con = doPost(jiraUrl, fields);
//
//            int responseCode = con.getResponseCode();
//
//            if (responseCode == 201 || responseCode == 200) {
//            } else {
//            }
//        } catch (Exception e) {
//        } finally {
//            if (hasPassKey) {
//                semaphore.release();
//            }
//        }
//
//    }
//
//    public static boolean createTestPlan() throws Exception {
//
//        JIRA_URL = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_API_CreatePlan");
//        String jiraUrl = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "XRay_Project_ID");
//        String projectKey = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "XRay_Project_ID");
//        String testPlanName ="";
//        if(PLAN_NAME!=null)
//        {
//            testPlanName=PLAN_NAME;
//        }
//        else
//        {
//            testPlanName=SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Name");
//        }
//        String testPlanDescription = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Desc");
//        String testLabel = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Label");
//        String testPlanID = FrameworkGlobalVariables.BLANK;
//
//        boolean bolSuccessTestPlanId=false;
//
//        JSONObject fields = new JSONObject();
//        fields.put("summary", testPlanName);
//        fields.put("description", testPlanDescription);
//
//        JSONObject project = new JSONObject();
//        project.put("key", projectKey);
//        fields.put("project", project);
//        project = null;
//
//        JSONObject issueType = new JSONObject();
//        issueType.put("name", "Test Plan");
//        fields.put("issuetype", issueType);
//        issueType = null;
//
//        JSONObject testPlanObject = new JSONObject();
//        testPlanObject.put("fields", fields);
//        fields = null;
//
//        try {
//
//            bolSuccessTestPlanId = doPostTestPlan(jiraUrl, testPlanObject);
//
//        } catch (Exception e) {
////			if (retryCount < MAX_RETRY) {
////				return createTestPlan(++retryCount, testCase);
//        }
////		}
//
//        return bolSuccessTestPlanId;
//    }
//
//    public static JSONObject createTestExecutionInfo() throws Exception {
//
//        JIRA_URL = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_URL");
//        String projectKey = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "XRay_Project_ID");
//        String testPlanName ="";
//        // dynamic naming or from config file
//        if(PLAN_NAME!=null)
//        {
//            testPlanName=PLAN_NAME;
//        }
//        else
//        {
//            testPlanName=SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Name");
//        }
//
//        String testPlanDescription = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Desc");
//        String testPlanID = Test_Plan_ID;
//
//        JSONObject fields = new JSONObject();
//        fields.put("summary", testPlanName);
//        fields.put("description", testPlanDescription);
//
//        JSONObject project = new JSONObject();
//        project.put("key", projectKey);
//        fields.put("project", project);
//        project = null;
//
//        JSONObject xrayFields = new JSONObject();
//        xrayFields.put("testPlanKey", testPlanID);
//        project = null;
//
//        JSONObject issueType = new JSONObject();
//        issueType.put("name", "Test Execution");
//        fields.put("issuetype", issueType);
//        issueType = null;
//
//
//        JSONObject testPlanObject = new JSONObject();
//        testPlanObject.put("fields", fields);
//        testPlanObject.put("xrayFields", xrayFields);
//        fields = null;
//
//        return testPlanObject;
//    }
//
//    public static JSONObject UpdateLabelInfo() throws Exception {
//
//        String strLabel = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Label");
//
//        JSONObject update = new JSONObject();
//        JSONArray labels = new JSONArray();
//        JSONObject label = new JSONObject();
//        JSONObject add = new JSONObject();
//
//        add.put("add", strLabel);
//        labels.put(add);
//        label.put("labels",labels);
//        update.put("update", label);
//        label = null;
//        labels = null;
//        add =null;
//
//        final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//        String credentials= Credentials.basic(SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_UserID"), SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_TOKEN"));
//
//        String endpointUrl = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_URL")+"/rest/api/2/issue/"+Test_Execution_ID;
//
//        RequestBody requestBody = null;
//        try {
//            requestBody = RequestBody.create(MEDIA_TYPE_JSON,update.toString());
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            throw e1;
//        }
//        Request request = new Request.Builder().url(endpointUrl).method("PUT", requestBody).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").addHeader("Authorization", credentials).build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//            response.close();
//
//        } catch (Exception e) {
//            throw e;
//        }
//
//        return update;
//    }
//
//    public static JSONObject UpdateAssigneeInfo() throws Exception {
//
//        String strAccountID = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "ACCOUNT_ID");
//
//        JSONObject accountid = new JSONObject();
//        accountid.put("accountId", strAccountID);
//
//        final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//        String credentials= Credentials.basic(SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_UserID"), SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_TOKEN"));
//
//        String endpointUrl = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_URL")+"/rest/api/3/issue/"+Test_Execution_ID+"/assignee?";
//
//        RequestBody requestBody = null;
//        try {
//            requestBody = RequestBody.create(MEDIA_TYPE_JSON,accountid.toString());
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            throw e1;
//        }
//        Request request = new Request.Builder().url(endpointUrl).method("PUT", requestBody).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").addHeader("Authorization", credentials).build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//            response.close();
//
//        } catch (Exception e) {
//            throw e;
//        }
//
//        return accountid;
//    }
//    public static String createTestPlan(int retryCount, TestCases testCase) throws Exception {
//
//        TestCaseInputs inputs = testCase.getTestCaseInputs();
//
//        String platform = inputs.getInputValue(TestCaseInputs.OS_NAME).toUpperCase();
//        String environment = inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT);
//
//        JIRA_URL = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_URL");
//
//        String jiraUrl = String.format("%s/rest/api/2/issue/", JIRA_URL);
//
//        if (JIRA_AUTH_CODE == null) {
//
//            jiraUserID = System.getenv("JIRA_USERID");
//
//            String credential = "aterbuild:aterbuild@123";
//
//            if (jiraUserID == null) {
//                jiraUserID = "aterbuild";
//            } else {
//                String jiraPassword = System.getenv("JIRA_PASSWORD");
//                if (jiraPassword == null) {
//                    jiraUserID = "aterbuild";
//                    credential = "aterbuild:aterbuild@123";
//                } else {
//                    credential = jiraUserID + ":" + jiraPassword;
//                }
//            }
//
//            JIRA_AUTH_CODE = new String("Basic " + Base64.getEncoder().encodeToString(credential.getBytes()));
//        }
//
//        String projectKey = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "XRay_Project_ID");
//
//        String testPlanName = String.format("[%s] %s", platform.toUpperCase(),
//                PLAN_NAME==null?SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Name"):PLAN_NAME);
//
//        String testPlanDescription = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_Plan_Desc");
//
//        String testPlanID = FrameworkGlobalVariables.BLANK;
//
//        JSONObject fields = new JSONObject();
//        fields.put("summary", testPlanName);
//        fields.put("description", testPlanDescription);
//
//        JSONObject project = new JSONObject();
//        project.put("key", projectKey);
//        fields.put("project", project);
//        project = null;
//
//        JSONObject issueType = new JSONObject();
//        issueType.put("name", "Test Plan");
//        fields.put("issuetype", issueType);
//        issueType = null;
//
//        JSONArray labels = new JSONArray();
//        platform = platform.toUpperCase().replaceAll("\\s+", "_");
//        labels.put(platform);
//
//        labels.put(environment.toUpperCase().replaceAll("\\s+", "_"));
//        labels.put(platform + "-"
//                + FrameworkGlobalVariables.APP_Versions.get(platform).toUpperCase().replaceAll("\\s+", "_"));
//
//        platform = null;
//
//        if (FrameworkGlobalVariables.SOLUTION_TYPE.isEmpty()) {
//
//        } else {
//            labels.put(FrameworkGlobalVariables.SOLUTION_TYPE.replaceAll("\\s+", "_"));
//        }
//
//        fields.put("labels", labels);
//
//        JSONObject assignee = new JSONObject();
//        assignee.put("name", jiraUserID);
//
//        fields.put("assignee", assignee);
//
//        JSONObject testPlanObject = new JSONObject();
//        testPlanObject.put("fields", fields);
//        fields = null;
//
//        try {
//            HttpURLConnection con = doPost(jiraUrl, testPlanObject);
//
//            int responseCode = con.getResponseCode();
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//            if (responseCode >= 200 && responseCode < 300) {
//                StringBuilder line = new StringBuilder();
//
//                for (int c; (c = rd.read()) >= 0;) {
//                    line.append((char) c);
//                }
//
//                JSONObject response = new JSONObject(line.toString());
//
//                if (response.has("key")) {
//                    testPlanID = response.getString("key");
//                } else {
//                }
//
//                response = null;
//                rd.close();
//            } else {
//            }
//
//        } catch (Exception e) {
//            if (retryCount < MAX_RETRY) {
//                return createTestPlan(++retryCount, testCase);
//            }
//        }
//
//        ADD_TESTCASE.put(getTargetPlatform(testCase), true);
//
//        return testPlanID;
//    }
//
//    private static boolean doPostTestPlan(String jiraUrl, JSONObject inputs) throws Exception {
//
//
//        final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
//        boolean bolTestPlanIdSet=false;
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//        String credentials= Credentials.basic(SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_UserID"), SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_TOKEN"));
//
//        String endpointUrl = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_API_CreatePlan");
//
//        RequestBody requestBody = null;
//        try {
//            requestBody = RequestBody.create(MEDIA_TYPE_JSON,inputs.toString());
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            throw e1;
//        }
//        Request request = new Request.Builder().url(endpointUrl).post(requestBody).addHeader("Authorization", credentials).build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            String responseBody = response.body().string();
//
//            if (response.isSuccessful()){
//                JSONObject responseObj = new JSONObject(responseBody);
//                try{
//                    // System.out.println("Test Plan Link: https://resideo.atlassian.net/browse/"+responseObj.getString("key"));
//                    Test_Plan_ID=responseObj.getString("key");
//                    bolTestPlanIdSet =true;
//
//                } catch (Exception e) {
//                    throw e;
//                }
//                //              return(responseBody);
//            } else {
//                //              throw new IOException("Unexpected HTTP code " + response);
//            }
//
//            response.close();
//            return bolTestPlanIdSet;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public static void doPostTestExecution() {
//        try {
//
//            final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
//            String clientId = System.getenv().getOrDefault("CLIENT_ID", SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "CLIENT_ID"));
//            String clientSecret = System.getenv().getOrDefault("CLIENT_SECRET", SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "CLIENT_SECRET"));
//            String authenticateUrl =SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "XRAY_AUTHENTICATION_URL");
//            String ExecutionKey="";
//
//            System.out.println("Importing a Xray JSON report to a Xray Cloud instance...");
//
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectTimeout(60, TimeUnit.SECONDS)
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .writeTimeout(60, TimeUnit.SECONDS)
//                    .build();
//            String authenticationPayload = "{ \"client_id\": \"" + clientId +"\", \"client_secret\": \"" + clientSecret +"\" }";
//            RequestBody body = RequestBody.create( MEDIA_TYPE_JSON,authenticationPayload);
//            Request request = new Request.Builder().url(authenticateUrl).post(body).build();
//            Response response = null;
//            String authToken = null;
//
//            response = client.newCall(request).execute();
//            //System.out.println("First Request Complete");
//
//            String responseBody = response.body().string();
//            response.close();
//
//            if (response.isSuccessful()){
//                authToken = responseBody.replace("\"", "");
//            } else {
//                //throw new IOException("failed to authenticate " + response);
//            }
//
//
//            String credentials = "Bearer " + authToken;
//
//            String endpointUrl =  SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "XRAY_EXECUTION_URL");
//            RequestBody requestBody = null;
//            try {
//                JSONObject reportContent = createTestExecutionInfo();
//                //FileWriter file1 = new FileWriter("C:\\Jira\\TestCaseupdate.json");
//                FileWriter file1 = new FileWriter("src/test/resources/xray/TestCaseupdate.json");
//                file1.write(testCaseUpdate.toString());
//                file1.close();
//
//                FileWriter file2 = new FileWriter("src/test/resources/xray/info.json");
//                file2.write(reportContent.toString());
//                file2.close();
//
//                OkHttpClient client1 = new OkHttpClient.Builder()
//                        .connectTimeout(60, TimeUnit.SECONDS)
//                        .readTimeout(60, TimeUnit.SECONDS)
//                        .writeTimeout(60, TimeUnit.SECONDS)
//                        .build();
//
//                MediaType mediaType = MediaType.parse("text/plain");
//                requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                        .addFormDataPart("results","TestCaseupdate.json",
//                                RequestBody.create(MediaType.parse("application/octet-stream"),
//                                        new File("src/test/resources/xray/TestCaseupdate.json")))
//                        .addFormDataPart("info","info.json",
//                                RequestBody.create(MediaType.parse("application/octet-stream"),
//                                        new File("src/test/resources/xray/info.json")))
//                        .build();
//
//                Request request1 = new Request.Builder().url(endpointUrl).post(requestBody).addHeader("results", "").addHeader("Authorization", credentials).build();
//                Response response1 = null;
//
//                response1 =  client1.newCall(request1).execute();
//                System.out.println("Second Request Complete");
//                String responseBody1 = response1.body().string();
//
//                if (response1.isSuccessful()){
//                    JSONObject responseObj = new JSONObject(responseBody1);
//                    //System.out.println("Test Execution: " + responseObj.get("key").toString());
//                    System.out.println("ExecutionKey link : "+"https://resideo.atlassian.net/browse/"+responseObj.get("key").toString());
//                    System.out.println("ExecutionKey link : "+"https://resideo.atlassian.net/browse/"+Test_Plan_ID);
//                    ExecutionKey =responseObj.get("key").toString();
//                    Test_Execution_ID =responseObj.get("key").toString();
//
//                    response.close();
//
//                    UpdateAssigneeInfo();
//                    UpdateLabelInfo();
//
//                } else {
//                    //	throw new IOException("Unexpected HTTP code " + response);
//                }
//
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                //		throw e1;
//            }
//
//            request = new Request.Builder().url(endpointUrl).post(requestBody).addHeader("Authorization", credentials).build();
//            response = null;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//    }
//
//    public static boolean initJIRACredentials(){
//        try {
//
//            if(JIRA_URL!=null){
//                return true;
//            }
//
//            JIRA_URL = SuiteConstants.getConstantValue(SuiteConstantTypes.XRAY, "JIRA_URL");
//
//            if (JIRA_AUTH_CODE == null) {
//
//                jiraUserID = System.getenv("JIRA_USERID");
//
//                String credential = "aterbuild:aterbuild@123";
//
//                if (jiraUserID == null) {
//                    jiraUserID = "aterbuild";
//                } else {
//                    String jiraPassword = System.getenv("JIRA_PASSWORD");
//                    if (jiraPassword == null) {
//                        jiraUserID = "aterbuild";
//                        credential = "aterbuild:aterbuild@123";
//                    } else {
//                        credential = jiraUserID + ":" + jiraPassword;
//                    }
//                }
//
//                JIRA_AUTH_CODE = new String("Basic " + Base64.getEncoder().encodeToString(credential.getBytes()));
//                return true;
//            }
//        } catch (Exception e) {
//
//        }
//
//        return false;
//
//    }
//
//    public static void updateExecutionResults(TestCases testCase) {
//
//        JSONObject finalResultJSON;
//
//        //Set JIRA Credentials
//        //initJIRACredentials();
//
//        if (FrameworkGlobalVariables.XRAY_CONFIG != null) {
//            boolean hasPassKey = false;
//            try {
//
//                hasPassKey = semaphore.tryAcquire(1, TimeUnit.MINUTES);
//
//                TestCaseInputs inputs = testCase.getTestCaseInputs();
//
//                String comment = String.format("Detailed Execution Report :: %s",
//                        String.format("http://%s/results/%s/%s/report.html", ReportConstants.REPORT_SERVER_URL,
//                                FrameworkGlobalVariables.CURRENT_EXECUTION_SUB_FOLDER,
//                                new File(FrameworkGlobalVariables.REBOT_FOLDER).getName()));
//
//                JSONArray tagsArray = new JSONArray();
//
//                tagsArray.put((inputs.getInputValue(TestCaseInputs.OS_NAME).equalsIgnoreCase(Mobile.ANDROID)
//                        ? SuiteConstants.getConstantValue(SuiteConstantTypes.MOBILE_APP, "Android_APP_Version")
//                        : SuiteConstants.getConstantValue(SuiteConstantTypes.MOBILE_APP, "IOS_APP_Version")));
//
//                tagsArray.put(inputs.getInputValue(TestCaseInputs.OS_NAME).replaceAll("\\s+", "_"));
//                tagsArray.put(inputs.getInputValue(TestCaseInputs.OS_VERSION).replaceAll("\\s+", "_"));
//                tagsArray.put(inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).replaceAll("\\s+", "_"));
//                tagsArray.put(inputs.getInputValue(TestCaseInputs.EXEC_LOCATION).replaceAll("\\s+", "_"));
//
//                if (inputs.isInputAvailable(TestCaseInputs.USER_ID)) {
//                    tagsArray.put(inputs.getInputValue(TestCaseInputs.USER_ID));
//                }
//
//                int index = 2;
//
//                String useridName = String.format("%%", TestCaseInputs.USER_ID, index);
//
//                while (inputs.isInputAvailable(useridName)) {
//                    tagsArray.put(inputs.getInputValue(useridName).replaceAll("\\s+", "_"));
//                    ++index;
//                    useridName = String.format("%%", TestCaseInputs.USER_ID, index);
//                }
//
//                if (FrameworkGlobalVariables.SOLUTION_TYPE.isEmpty()) {
//
//                } else {
//                    tagsArray.put(FrameworkGlobalVariables.SOLUTION_TYPE);
//                }
//
//                String status = testCase.isTestSuccessful() ? "PASSED" : "FAILED";
//
//                String testCaseName = testCase.getTestCaseName();
//                String xrayID = testCase.getXRayTestID();
//                String startTime = testCase.getStartTimeXRayTest();
//
//                JSONObject testInform = new JSONObject();
//
//                testInform.put("testKey", xrayID);
//                testInform.put("start", startTime);
//                testInform.put("finish", XRayUtils.getTimeStamp());
//                testInform.put("comment", comment);
//                testInform.put("status", status);
//
//                JSONArray testArray = new JSONArray();
//                finalResultJSON = new JSONObject();
//                testArray.put(testInform);
//                if(testcaseupdateFirst==true){
//
//                    testcaseupdateFirst=false;
//                    createTestPlan();
//
//
//				/*JSONObject infoObject = getInfoTag(testCase, testCaseName, startTime,
//						FrameworkGlobalVariables.XRAY_CONFIG);*/
//
//                    JSONObject infoObject = new JSONObject();
//                    Properties xrayProperties = FrameworkGlobalVariables.XRAY_CONFIG;
//
//                    infoObject.put("summary", testCaseName);
//                    infoObject.put("testPlanKey", Test_Plan_ID);
//                    infoObject.put("description",xrayProperties.get("Description"));
//
//                    if(testCase.getTestCaseInputs().getInputValue(TestCaseInputs.PLATFORM).equalsIgnoreCase(Mobile.ANDROID)){
//                        infoObject.put("revision", FrameworkGlobalVariables.APP_Versions.get("ANDROID").replace("_", "."));
//                    }else{
//                        infoObject.put("revision", FrameworkGlobalVariables.APP_Versions.get("IOS").replace("_", "."));
//                    }
//
//                    infoObject.put("startDate", startTime);
//                    infoObject.put("finishDate", getTimeStamp());
//                    finalResultJSON.put("info", infoObject);
//                    testCaseUpdate.put("info", infoObject);
//                    finalResultJSON.put("tests", testArray);
//
//                    testCaseUpdate.put("tests", testArray);
//                }else{
//                    testCaseUpdate.append("tests", testInform);
//                    //	System.out.println(testCaseUpdate);
//                }
//
//                try {
//                } catch (Exception e) {
//                }
//
//                finalResultJSON = null;
//
//            } catch (Exception e) {
//                System.out.println(e);
//            } finally {
//                if (hasPassKey) {
//                    semaphore.release();
//                }
//            }
//        }
//    }
//
//    static String updateToXRay(JSONObject parameters) throws Exception {
//
//        initJIRACredentials();
//
//        String jiraUrl = String.format("%s/rest/raven/1.0/import/execution", JIRA_URL);
//
//        try {
//
//            HttpURLConnection con = doPost(jiraUrl, parameters);
//
//            InputStream ips = con.getInputStream();
//
//            BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
//
//            int responseCode = con.getResponseCode();
//
//            if (responseCode == 200) {
//                StringBuilder sb = new StringBuilder();
//                String s;
//                while (true) {
//                    s = buf.readLine();
//                    if (s == null || s.length() == 0)
//                        break;
//                    sb.append(s);
//
//                }
//                buf.close();
//                ips.close();
//
//                JSONObject response = new JSONObject(sb.toString());
//
//                if (response.has("testExecIssue")) {
//                    JSONObject testExecIssue = response.getJSONObject("testExecIssue");
//                    if (testExecIssue.has("key")) {
//                        return testExecIssue.getString("key");
//                    }
//                }
//
//            } else {
//            }
//            con.disconnect();
//        } catch (Exception e) {
//        }
//
//        return FrameworkGlobalVariables.BLANK;
//    }
//
//    private static HttpURLConnection doPost(String jiraUrl, JSONObject inputs) throws Exception {
//
//        URL obj = new URL(jiraUrl);
//
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        try {
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json");
//            con.setRequestProperty("Authorization", JIRA_AUTH_CODE);
//            con.setDoOutput(true);
//
//            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//            wr.writeBytes(inputs.toString());
//            wr.flush();
//            wr.close();
//
//            return con;
//
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    public static String getTimeStamp() {
//        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//        return simpleDate.format(new Date());
//    }
//
//    private static JSONObject getInfoTag(TestCases testCase, String testCaseName, String startTime,
//                                         Properties xrayProperties) {
//
//        JSONObject info = new JSONObject();
//
//        info.put("summary", testCaseName);
//		/*info.put("description", FrameworkGlobalVariables.SuiteMessage.isEmpty() ? xrayProperties.get("Description")
//				: FrameworkGlobalVariables.SuiteMessage);*/
//        info.put("description",xrayProperties.get("Description"));
//
//        //info.put("user", xrayProperties.get("User"));
//        info.put("version", xrayProperties.get("Version"));
//
//		/*if(testCase.getTestCaseInputs().getInputValue(TestCaseInputs.PLATFORM).equalsIgnoreCase(Mobile.ANDROID)){
//			info.put("revision", FrameworkGlobalVariables.APP_Versions.get("ANDROID").replace("_", "."));
//		}else{
//			info.put("revision", FrameworkGlobalVariables.APP_Versions.get("IOS").replace("_", "."));
//		}*/
//
//        info.put("startDate", startTime);
//        info.put("finishDate", getTimeStamp());
//        String platform = getTargetPlatform(testCase);
//        //String planID = FrameworkGlobalVariables.BLANK;
//        String planID = getPlanID(testCase);
//		/*if (XRAY_PLAN_IDS.containsKey(platform)) {
//			planID = XRAY_PLAN_IDS.get(platform);
//		} else {
//			planID = getPlanID(testCase);
//			XRAY_PLAN_IDS.put(platform, planID);
//		}*/
//
//        info.put("testPlanKey", planID);
//        return info;
//    }
//}
//
//class HelperThreads extends Thread{
//
//    JSONObject execution;
//    JSONObject test;
//    String startTime;
//    String endTime;
//    JSONArray tests;
//    TestInformation testInformation;
//    String user;
//
//    public HelperThreads(TestInformation testInfo, String user) {
//        this.testInformation = testInfo;
//        this.user = user;
//    }
//
//    @Override
//    public void run() {
//
//        execution = new JSONObject();
//
//        tests = new JSONArray();
//
//        JSONObject infoJSon = new JSONObject();
//
//        infoJSon.put("summary", testInformation.getTestName());
//        infoJSon.put("description", FrameworkGlobalVariables.SuiteMessage);
//
//        infoJSon.put("user", user);
//        test = new JSONObject();
//        startTime = testInformation.getStartTime();
//        endTime = testInformation.getEndTime();
//
//        infoJSon.put("testEnvironments", testInformation.getTags());
//
////		@ TODO: XRAY_PLAN_IDS
//        System.out.println(testInformation.getPlatform());
//        infoJSon.put("testPlanKey", XRayUtils.XRAY_PLAN_IDS.get(testInformation.getPlatform()));
//
//        // "testKey" : "DEMO-6",
//        test.put("testKey", testInformation.getXrayID());
//
//        // "start" : "2014-08-30T11:47:35+01:00",
//        infoJSon.put("startDate", startTime);
//        test.put("start", startTime);
//
//        // "finish" : "2014-08-30T11:50:56+01:00",
//        infoJSon.put("finishDate", endTime);
//        test.put("finish", endTime);
//
//        // "comment" : "Successful execution",
//        test.put("comment", testInformation.getMessage());
//
//        // "status" : "PASS"
//        test.put("status", testInformation.getTestStatus());
//
//        tests.put(test);
//
//        execution.put("info", infoJSon);
//        execution.put("tests", tests);
//
//        try {
//            XRayUtils.updateToXRay(execution);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println(execution);
//
//    }
//}
