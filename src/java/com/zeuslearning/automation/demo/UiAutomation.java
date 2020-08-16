package com.zeuslearning.automation.demo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zeuslearning.automation.interactions.IAutomationReports;
import com.zeuslearning.automation.interactions.IExecuteJavascript;
import com.zeuslearning.automation.interactions.IVerifications;
import com.zeuslearning.automation.interactions.IWaitOperations;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.AutomationReports;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.ExecuteJavascript;
import com.zeuslearning.automation.selenium.interactions.Mouse;
import com.zeuslearning.automation.selenium.interactions.Timeout;
import com.zeuslearning.automation.selenium.interactions.Verifications;

public class UiAutomation {
    WebDriver _driver = null;
    Mouse m = null;
    BrowserWindow browser;
    IAutomationReports automationReports;
    String _myBrowser = "";
    String _mode = "";
    String _resolution = "";
    IVerifications verify;
    List<String> tags;
    Map<String, Object> gspecData;
    IExecuteJavascript _js;
    IWaitOperations wait;

    @Parameters({ "myBrowser", "mode", "resolution" })
    @BeforeTest
    public void beforeTest(String myBrowser, String mode, String resolution) {
        System.setProperty("mode", mode);
        gspecData = new HashMap<String, Object>();
        tags = new ArrayList<String>();
        Driver idriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
        _driver = (WebDriver) idriver.getDriver(myBrowser);
        _js = new ExecuteJavascript(_driver);
        wait = new Timeout(_driver);
        m = new Mouse(_driver);
        automationReports = new AutomationReports(_driver);
        verify = new Verifications(_driver);
        _myBrowser = myBrowser;
        _mode = mode;
        tags.add(_myBrowser);
        tags.add(_mode);
        _resolution = resolution;
        tags.add(_resolution);
        if (verify.isStringMatch("hd", _resolution)) {
            browser = new BrowserWindow(_driver, 1920, 1080);
        } else if (verify.isStringMatch("laptop", _resolution)) {
            browser = new BrowserWindow(_driver, 1366, 768);
        } else if (verify.isStringMatch("tablet-landscape", _resolution)) {
            browser = new BrowserWindow(_driver, 1024, 768);
        } else if (verify.isStringMatch("tablet-portrait", _resolution)) {
            browser = new BrowserWindow(_driver, 768, 1024);
        } else if (verify.isStringMatch("mobile", _resolution)) {
            browser = new BrowserWindow(_driver, 320, 650);
        }
    }

    @BeforeMethod
    public void initReports(Method method, Object[] arguments) {
        automationReports.initReport(method, arguments);
        automationReports.updateTestInfo("Home Page Layout");
    }

    @Test
    public void test() {
        _driver.get("https://www.zeuslearning.com/");
        wait.waitForVisibilityOf(By.cssSelector(".tag-line-heading"));
        wait.waitForInvisibilityOf(By.cssSelector("#bg div.loader"));
        wait.waitForInvisibilityOf(By.cssSelector("#form1 div.loader"));
        if (verify.isElementDisplayed(By.cssSelector(".cookie"), 5)) {
            _js.executeScript("$('.cookie').remove()");
        }
        gspecData.put("isHamburgerclicked", false);
        gspecData.put("data", new String[] { "SERVICES", "KEPLERS", "About", "CAREERS", "CONTACT" });
        automationReports.verifyPageLayout("Verifying Zeus Learning Home Specs", "/com/zeuslearning/automation/unittests/specs/zeuslearninghome.gspec", tags,
                        gspecData);

        if (verify.isStringMatchIgnoreCase("mobile", _resolution)) {
            gspecData.put("isHamburgerclicked", true);
            tags.add("hamburger");
            m.click(By.cssSelector(".pull-left"));
            wait.waitForVisibilityOf(By.cssSelector("#navbarCollapse.in"));
            automationReports.verifyPageLayout("Verifying Zeus Learning Home Specs - Hamburger Menu",
                            "/com/zeuslearning/automation/unittests/specs/zeuslearninghomehamburger.gspec", tags, gspecData);
        }
    }

    @AfterTest
    public void kill() {
        automationReports.generateHtmlReport("HtmlReports/reports-" + _myBrowser + "-" + _mode + "-" + _resolution);
        _driver.quit();
    }
}
