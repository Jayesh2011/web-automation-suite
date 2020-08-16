package com.zeuslearning.automation.unittests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zeuslearning.automation.appium.interactions.Touch;
import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;

import io.appium.java_client.ios.IOSDriver;

public class ClassTestDemoMain {

    IDriver iDriver;
    IOSDriver<WebElement> driver;
    IMouseOperations touchHandle;
    IWindowOperations browser;

    @Test
    public void test() throws Exception {
        iDriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
        driver = (IOSDriver<WebElement>) iDriver.getDriver("safari-ios");
        WebDriver webDriver = driver;
        browser = new BrowserWindow(webDriver);
        touchHandle = new Touch(driver, browser);

        browser.open("https://www.w3schools.com");
        runSimpleTask();
    }

    public void runSimpleTask() throws InterruptedException {
        touchHandle.click(By.cssSelector("#myAccordion > div > a:nth-child(5)"));
        Thread.sleep(1000);
    }

    @AfterClass
    public void quit() {
        iDriver.quitDriver();
    }
}
