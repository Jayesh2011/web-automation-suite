package com.zeuslearning.automation.unittests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.io.ScreenCapture;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;

public class TestScreenCapture {
    IDriver iDriver = null;

    @Test
    public void screenTest() throws Exception {
        iDriver = new Driver(System.getProperty("user.dir") + "/src/java/com/zeuslearning/automation/Config.properties");
        Object driver = iDriver.getDriver("chrome");
        ScreenCapture captcha = new ScreenCapture(driver);
        IWindowOperations browser = new BrowserWindow(driver);
        String ScreenshotPath = "D:/Screenshot.png";
        Object element = By.cssSelector("#nav1 > div > div.zeus-logo-tab > a > img");

        browser.open("http://www.zeuslearning.com");
        captcha.getViewPort(ScreenshotPath);
        captcha.getScreenshotByOffset(400, 800, ScreenshotPath);
        captcha.getScreenshotByElement(element, ScreenshotPath);
        captcha.getScreenshotCompletePage("D:/FullPageScreenshot.png");
    }

    @AfterClass
    public void tearDown() {
        if (iDriver != null) {
            iDriver.quitDriver();
        }
    }
}
