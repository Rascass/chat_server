package com.solvd.automation.lab.fall.io;

import com.solvd.automation.lab.fall.constant.IOConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private Properties props = new Properties();

    private static PropertyReader instance;

    public synchronized static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    private PropertyReader() {
        try (InputStream fis = new FileInputStream(new File(IOConstant.PROPERTIES_PATH).getAbsoluteFile())
        ) {
            this.props.load(fis);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String getValue(String key) {
        return this.props.getProperty(key);
    }
}

