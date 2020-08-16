package com.zeuslearning.automation.unittests;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zeuslearning.automation.interactions.IAutomationReports;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.AutomationReports;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.Mouse;

public class testSeleniumGrid {

    WebDriver _driver = null;
    Mouse m = null;
    BrowserWindow browser;
    IAutomationReports automationReports;
    String platform = "";
    List<String> tags;
    Map<String, Object> gspecData;

    @Parameters("myBrowser")
    @BeforeTest
    public void beforeTest(String myBrowser) {
        tags = new ArrayList<String>();
        gspecData = new HashMap<String, Object>();
        Driver idriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
        _driver = (WebDriver) idriver.getDriver(myBrowser);
        System.out.println(_driver.getWindowHandle());
        m = new Mouse(_driver);
        browser = new BrowserWindow(_driver);
        automationReports = new AutomationReports(_driver);
        platform = myBrowser;
        tags.add(platform);
        tags.add("hd");
    }

    @BeforeMethod
    public void initReports(Method method, Object[] arguments) {
        automationReports.initReport(method, arguments);
    }

    @Test
    public void test() {
        _driver.get("https://www.zeuslearning.com/");
        (new WebDriverWait(_driver, 30))
                        .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#navbarCollapse > ul > li.menu-content-list.contact-tab > a")));
        /*
         * driver.findElement(By.cssSelector("#navbarCollapse > ul > li.menu-content-list.contact-tab > a")).click();
         * m.doubleClickUsingCode(By.cssSelector("#navbarCollapse > ul > li.menu-content-list.contact-tab > a"));
         */

        gspecData.put("data", new String[] { "SERVICES", "KEPLERS", "About", "CAREERS", "CONTACT" });
        automationReports.updateTestInfo("Home Page Layout");
        automationReports.verifyPageLayout("Verifying Zeus Learning Home Specs", System.getProperty("user.dir") + "/specs/zeuslearninghome.gspec", tags,
                        gspecData);
        automationReports.startSection("New section1");
        automationReports.logError("Error hai");
        automationReports.startSection("New section2");
        automationReports.logInfo("Info hai");
        automationReports.endSection();
        automationReports.logWarn("Warn kar raha hoon.");
        automationReports.endSection();
        File file = new File(System.getProperty("user.dir") + "/pom.xml");
        automationReports.logWarn("File Attachmemt", file, file);
    }

    @AfterTest
    public void kill() {
        automationReports.generateHtmlReport("testOutput/reports-" + platform);
        _driver.quit();
    }
}
