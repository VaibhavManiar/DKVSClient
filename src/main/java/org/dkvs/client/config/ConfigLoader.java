package org.dkvs.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private final String configFile;

    public ConfigLoader(String configFile) {
        this.configFile = configFile;
    }

    public Properties load() {
        logger.info("Loading configuration. Config File : [" + configFile + "]. ");
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(this.configFile));
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("Error while loading configuration. Config File : [" + configFile + "]. " + e.getMessage(), e);
        }
    }
}