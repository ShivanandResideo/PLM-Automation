package utils;

import static utils.Commons.config;
import static utils.Commons.creds;

public class LoginCreds {

    public String URL, userName, password;

    public LoginCreds(String userType){
        Commons commons = new Commons();
        URL = commons.URL(config.getProperty("ENV"));
        if(userType.equalsIgnoreCase("alpha")){
            userName = creds.getProperty("alphauser");
            password = creds.getProperty("alphapwd");
        }
        else if(userType.equalsIgnoreCase("beta")){
            userName = creds.getProperty("betauser");
            password = creds.getProperty("betapwd");
        }
        else if(userType.equalsIgnoreCase("charlie")){
            userName = creds.getProperty("charlieuser");
            password = creds.getProperty("charliepwd");
        }
        else if(userType.equalsIgnoreCase("delta")){
            userName = creds.getProperty("deltauser");
            password = creds.getProperty("deltapwd");
        }
        else if(userType.equalsIgnoreCase("gamma")){
            userName = creds.getProperty("gammauser");
            password = creds.getProperty("gammapwd");
        }
        else if(userType.equalsIgnoreCase("echo")){
            userName = creds.getProperty("echouser");
            password = creds.getProperty("echopwd");
        }
        else {
            userName = creds.getProperty("userName1");
            password = creds.getProperty("password1");
        }
    }

}
