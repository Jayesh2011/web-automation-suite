package com.zeuslearning.automation.demo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.zeuslearning.automation.interactions.IAutomationReports;
import com.zeuslearning.automation.interactions.IExecuteJavascript;
import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IQueryHtmlElement;
import com.zeuslearning.automation.interactions.IVerifications;
import com.zeuslearning.automation.interactions.IWaitOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.AutomationReports;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.ExecuteJavascript;
import com.zeuslearning.automation.selenium.interactions.Mouse;
import com.zeuslearning.automation.selenium.interactions.QueryHtml;
import com.zeuslearning.automation.selenium.interactions.Timeout;
import com.zeuslearning.automation.selenium.interactions.Verifications;

public class UiAutomationSpecBased {
    CommonData data;
    IAutomationReports automationReports;
    IExecuteJavascript js;
    IMouseOperations mouse;
    IQueryHtmlElement query;
    IVerifications verify;
    IWaitOperations wait;
    IWindowOperations browser;
    List<String> tags;
    Map<String, Object> gspecData;
    private String _mode = "";
    private String _myBrowser = "";
    private String _resolution = "";
    String pathToHamburgerSpec = "";
    String pathToHomeSpec = "";
    WebDriver driver = null;

    @Parameters({ "myBrowser", "mode", "resolution" })
    @BeforeTest
    public void beforeTest(String myBrowser, String mode, String resolution) {
        System.setProperty("mode", mode);
        data = new CommonData();
        gspecData = new HashMap<String, Object>();
        tags = new ArrayList<String>();
        _mode = mode;
        _myBrowser = myBrowser;
        _resolution = resolution;
        tags.add(_mode);
        tags.add(_myBrowser);
        tags.add(_resolution);
        Driver idriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
        driver = (WebDriver) idriver.getDriver(myBrowser);
        automationReports = new AutomationReports(driver);
        js = new ExecuteJavascript(driver);
        mouse = new Mouse(driver);
        query = new QueryHtml(driver);
        verify = new Verifications(driver);
        wait = new Timeout(driver);
        if (verify.isStringMatch("hd", _resolution)) {
            browser = new BrowserWindow(driver, data.fullHD.get("width"), data.fullHD.get("height"));
        } else if (verify.isStringMatch("laptop", _resolution)) {
            browser = new BrowserWindow(driver, data.laptop.get("width"), data.laptop.get("height"));
        } else if (verify.isStringMatch("tablet-landscape", _resolution)) {
            browser = new BrowserWindow(driver, data.tabletLandscape.get("width"), data.tabletLandscape.get("height"));
        } else if (verify.isStringMatch("tablet-portrait", _resolution)) {
            browser = new BrowserWindow(driver, data.tabletPortrait.get("width"), data.tabletPortrait.get("height"));
        } else if (verify.isStringMatch("mobile", _resolution)) {
            browser = new BrowserWindow(driver, data.mobile.get("width"), data.mobile.get("height"));
        }
        String baseSpecPath = System.getProperty("user.dir");
        pathToHamburgerSpec = baseSpecPath + "/specs/zeuslearninghomehamburger.gspec";
        pathToHomeSpec = baseSpecPath + "/specs/zeuslearninghome.gspec";
    }

    @BeforeMethod
    public void initReports(Method method, Object[] arguments) {
        automationReports.initReport(method, arguments);
        automationReports.updateTestInfo("Spec Based UI Automation");
    }

    @Test
    public void test() {
        driver.get("https://www.zeuslearning.com/");
        wait.waitForVisibilityOf(ZeusLearningHomeLocators.tagLineHeading);
        wait.waitForInvisibilityOf(ZeusLearningHomeLocators.bgLoader);
        wait.waitForInvisibilityOf(ZeusLearningHomeLocators.formLoader, 60);
        if (verify.isElementDisplayed(ZeusLearningHomeLocators.cookieFooter, 5)) {
            mouse.click(ZeusLearningHomeLocators.cookieDismiss);
        }
        gspecData.put("data", ZeusLearningData.navbarHeaders);
        automationReports.verifyPageLayout("Verifying Zeus Learning Home Specs", pathToHomeSpec, tags, gspecData);

        if (verify.isStringMatchIgnoreCase("mobile", _resolution)) {
            gspecData.put("isHamburgerclicked", true);
            tags.add("hamburger");
            mouse.click(ZeusLearningHomeLocators.hamburgerIcon);
            wait.waitForVisibilityOf(ZeusLearningHomeLocators.hamburgerMenu);
            automationReports.verifyPageLayout("Verifying Zeus Learning Home Specs - Hamburger Menu", pathToHamburgerSpec, tags, gspecData);
        }
    }

    @AfterMethod
    public void appendReports() {
        automationReports.appendReport();
    }

    @AfterTest
    public void kill() {
        automationReports.generateHtmlReport("HtmlReports/SpecBased/reports-" + _myBrowser + "-" + _mode + "-" + _resolution);
        driver.quit();
    }
}
