package com.zeuslearning.automation.demo;

import java.io.File;
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
import com.zeuslearning.automation.io.ScreenCapture;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.AutomationReports;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.ExecuteJavascript;
import com.zeuslearning.automation.selenium.interactions.Mouse;
import com.zeuslearning.automation.selenium.interactions.QueryHtml;
import com.zeuslearning.automation.selenium.interactions.Timeout;
import com.zeuslearning.automation.selenium.interactions.Verifications;

public class UiAutomationImageBased {
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
    private int _imageCounter = 1;
    private String _mode = "";
    private String _myBrowser = "";
    private String _resolution = "";
    ScreenCapture screenCapture;
    WebDriver _driver = null;

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
        Driver idriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
        _driver = (WebDriver) idriver.getDriver(myBrowser);
        automationReports = new AutomationReports(_driver);
        js = new ExecuteJavascript(_driver);
        mouse = new Mouse(_driver);
        query = new QueryHtml(_driver);
        screenCapture = new ScreenCapture(_driver);
        verify = new Verifications(_driver);
        wait = new Timeout(_driver);
        if (verify.isStringMatch("hd", _resolution)) {
            browser = new BrowserWindow(_driver, data.fullHD.get("width"), data.fullHD.get("height"));
        } else if (verify.isStringMatch("laptop", _resolution)) {
            browser = new BrowserWindow(_driver, data.laptop.get("width"), data.laptop.get("height"));
        } else if (verify.isStringMatch("tablet-landscape", _resolution)) {
            browser = new BrowserWindow(_driver, data.tabletLandscape.get("width"), data.tabletLandscape.get("height"));
        } else if (verify.isStringMatch("tablet-portrait", _resolution)) {
            browser = new BrowserWindow(_driver, data.tabletPortrait.get("width"), data.tabletPortrait.get("height"));
        } else if (verify.isStringMatch("mobile", _resolution)) {
            browser = new BrowserWindow(_driver, data.mobile.get("width"), data.mobile.get("height"));
        }
    }

    @BeforeMethod
    public void initReports(Method method, Object[] arguments) {
        automationReports.initReport(method, arguments);
        automationReports.updateTestInfo("Image Based UI Automation");
    }

    @Test
    public void testImageBasedApproach() {
        String imageFolderPath = System.getProperty("user.dir") + "/images/ImageBasedApproach";
        browser.open("http://www.zeuslearning.com/contact-us");

        try {
            wait.waitForVisibilityOf(ZeusLearningHomeLocators.bgLoader, 5);
        } catch (Exception e) {
            wait.waitForInvisibilityOf(ZeusLearningHomeLocators.bgLoader, 60);
            wait.waitForInvisibilityOf(ZeusLearningHomeLocators.formLoader, 5);
        }

        if (verify.isElementDisplayed(ZeusLearningHomeLocators.cookieFooter, 5)) {
            mouse.click(ZeusLearningHomeLocators.cookieDismiss);
        }

        wait.waitForElementToBePresent(ZeusLearningContactTabLocators.nameTextField);

        eyes(imageFolderPath, _resolution);

        if (verify.isStringMatchIgnoreCase("mobile", _resolution)) {
            mouse.click(ZeusLearningHomeLocators.hamburgerIcon);
            wait.waitForVisibilityOf(ZeusLearningHomeLocators.hamburgerMenu);
            eyes(imageFolderPath, _resolution);
        }
        mouse.click(ZeusLearningHomeLocators.serviceTab);

        try {
            wait.waitForVisibilityOf(ZeusLearningHomeLocators.bgLoader, 5);
        } catch (Exception e) {
            wait.waitForInvisibilityOf(ZeusLearningHomeLocators.bgLoader, 60);
        }
        wait.waitForVisibilityOf(ZeusLearningServicesTabLocators.servicesHome);

        try {
            wait.waitForVisibilityOf(ZeusLearningServicesTabLocators.contentAnimationAnime, 2);
            wait.waitForVisibilityOf(ZeusLearningServicesTabLocators.designUsabilityAnime, 2);
            wait.waitForVisibilityOf(ZeusLearningServicesTabLocators.lmsCmsTeiSystemsAnime, 2);
            wait.waitForVisibilityOf(ZeusLearningServicesTabLocators.mobileHtml5DevelopmentAnime, 2);
        } catch (Exception e) {} finally {
            wait.waitForInvisibilityOf(ZeusLearningServicesTabLocators.contentAnimationAnime, 2);
            wait.waitForInvisibilityOf(ZeusLearningServicesTabLocators.designUsabilityAnime, 2);
            wait.waitForInvisibilityOf(ZeusLearningServicesTabLocators.lmsCmsTeiSystemsAnime, 2);
            wait.waitForInvisibilityOf(ZeusLearningServicesTabLocators.mobileHtml5DevelopmentAnime, 2);
        }

        eyes(imageFolderPath, _resolution);
    }

    @AfterMethod
    public void appendReports() {
        automationReports.appendReport();
    }

    @AfterTest
    public void kill() {
        automationReports.generateHtmlReport("HtmlReports/ImageBased/reports-" + _myBrowser + "-" + _mode + "-" + _resolution);
        _driver.quit();
    }

    public void eyes(String pathToImagesFolder, String resolution) {
        pathToImagesFolder = pathToImagesFolder.replaceAll("\\\\", "/");

        pathToImagesFolder += "/" + _resolution;
        pathToImagesFolder = pathToImagesFolder.replace("//", "/");

        File imagesFolder = new File(pathToImagesFolder);
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }

        String filePath = pathToImagesFolder + "/screen-" + _imageCounter + ".png";

        gspecData.put("screenNumber", _imageCounter);
        gspecData.put("resolution", resolution);
        File file = new File(filePath);
        if (file.exists()) {
            String gspecPath = System.getProperty("user.dir") + "/specs/screenverifications.gspec";
            automationReports.verifyPageLayout("Image Verification screen " + _imageCounter, gspecPath, tags, gspecData);
        } else {
            screenCapture.getViewPort(filePath);
            automationReports.logInfo("Captured View Port", new File(filePath));
        }
        _imageCounter++;
    }
}
