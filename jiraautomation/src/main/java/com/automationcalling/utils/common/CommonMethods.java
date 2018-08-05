package com.automationcalling.utils.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonMethods {

    private CommonMethods() {
        throw new IllegalStateException("CommonUtil class");
    }

    public static String returnProperties(String filePath, String keyName) throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream(filePath);
        prop.load(input);
        return prop.getProperty(keyName);
    }
}
