package com.zeuslearning.automation.unittests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.applitools.eyes.Eyes;

import java.net.URISyntaxException;

public class applitoolsVisualTestDemo {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {

        WebDriver driver = new FirefoxDriver();

        Eyes eyes = new Eyes();
        // This is your api key, make sure you use it in all your tests.
        eyes.setApiKey("lsDLLV2ynGLHyYWsQSxytPOeDNBVc111ViCfc88b8j107tE110");

        try {
            // Start visual testing with browser viewport set to 1024x768.
            // Make sure to use the returned driver from this point on.
            driver = eyes.open(driver, "Applitools", "Zeus Web Page");

            driver.get("https://www.zeuslearning.com");

            // Visual validation point #1
            eyes.checkWindow("Main Page");

            //driver.findElement(By.cssSelector(".features>a")).click();

            // Visual validation point #2
            eyes.checkWindow("Features page");

            // End visual testing. Validate visual correctness.
            eyes.close();
        } finally {
            // Abort test in case of an unexpected error.
            eyes.abortIfNotClosed();
            driver.close();
        }
    }
}
