package com.force.five.app.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

public class ForceProperties {

    private static final Logger log = LoggerFactory.getLogger(ForceProperties.class);
    private static final Properties prop = new Properties();

    static {
        try {
            Resource res = new ClassPathResource("i18n/messages_en.properties");
            prop.load(res.getInputStream());
        } catch (Exception e) {
            log.error("InCyyteConstants : messages_en.properties file not found");
        }
    }

    private static String getProperty(String key) {
        String value = null;
        try {
            value = prop.getProperty(key).trim();
        } catch (Exception e) {
            log.error("Missing property entry for " + key, e);
            throw new RuntimeException("Missing property entry for " + key);
        }
        return value;
    }

    public static String REPORT_PATH = getProperty("reports.path");
}
