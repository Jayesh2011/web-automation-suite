package com.zeuslearning.automation.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesOps {

    public static Properties readProperties(String filePath) {
        try {
            File file = new File(filePath);

            FileInputStream fileInput = new FileInputStream(file);

            Properties prop = new Properties();

            prop.load(fileInput);

            fileInput.close();

            return prop;
        } catch (Exception e) {
            return null;
        }
    }
}
