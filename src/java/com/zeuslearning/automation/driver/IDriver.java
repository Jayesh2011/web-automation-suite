package com.zeuslearning.automation.driver;

import java.util.Properties;

public interface IDriver {

    /**
     * Initialize driver.
     *
     * @param {String}
     *            browserName
     */
    void initializeDriver(String browserName);

    /**
     * Get the driver object.
     *
     * @param {String}
     *            browserName
     * @return {Object} - driver object.
     */
    Object getDriver(String browserName);

    /**
     * Quit the driver.
     */
    void quitDriver();

    /**
     * Read config properties
     * 
     * @return {Properties} - Returns all the config properties set for a project.
     */
    public Properties getConfigProperties();
}
