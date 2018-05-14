package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OurProperties {
    private static OurProperties ourProperties;

    static {
        try {
            ourProperties = new OurProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String DBUser = "";
    private String DBPassword = "";
    private String DBUrl = "";


    private OurProperties() throws IOException {
        InputStream inputStream= null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            DBUser = prop.getProperty("DBUser");
            DBPassword = prop.getProperty("DBPassword");
            DBUrl = prop.getProperty("DBUrl");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }

    public static OurProperties getInstance(){
        return ourProperties;
    }

    public String getDBUser() {
        return DBUser;
    }

    public String getDBPassword() {
        return DBPassword;
    }

    public String getDBUrl() {
        return DBUrl;
    }
}
