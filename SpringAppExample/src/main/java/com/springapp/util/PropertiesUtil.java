package com.springapp.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties properties = new Properties();

    private PropertiesUtil(){

    }
    static {
        loadProperties();
    }

    public static String get(String key){
        return properties.getProperty(key);
    }

    private static void loadProperties() {
        try(var inputstream =  PropertiesUtil.class.getClassLoader().getResourceAsStream("spring.properties"))
        {
            properties.load(inputstream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
