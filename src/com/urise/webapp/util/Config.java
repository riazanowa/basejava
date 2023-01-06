package com.urise.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    protected static final File PROPS = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();
    Properties prop = new Properties();
    private final File storageDir;

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config(){
        try (InputStream is = new FileInputStream(PROPS)) {
            // load properties files
            prop.load(is);

            // get the property value
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword =prop.getProperty("db.password");

            storageDir = new File(prop.getProperty("storage.dir"));

        } catch (IOException e) {
            throw new IllegalStateException("Invlaid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
