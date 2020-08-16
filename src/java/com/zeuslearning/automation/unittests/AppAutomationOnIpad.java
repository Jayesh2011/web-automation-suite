package com.zeuslearning.automation.unittests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSDriver;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IAlertOperations;
import com.zeuslearning.automation.interactions.IDragDropOperations;
import com.zeuslearning.automation.interactions.IExecuteJavascript;
import com.zeuslearning.automation.interactions.IWaitOperations;
import com.zeuslearning.automation.io.TextFileOps;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.DragDrop;
import com.zeuslearning.automation.selenium.interactions.ExecuteJavascript;
import com.zeuslearning.automation.selenium.interactions.HtmlAlertOps;
import com.zeuslearning.automation.selenium.interactions.Timeout;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsActions;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AppAutomationOnIpad implements PerformsActions<TouchAction> {
    public AppiumDriver<IOSElement> driver;
    IDriver iDriver;
    protected String jqueryRange, jquery, jquerySimulations, jquerySimulationsSequence, jquerySimulationsCombo, jQueryDragDrop;
    DesiredCapabilities capabilities = new DesiredCapabilities();
    protected TextFileOps textFile;
    protected IExecuteJavascript js;
    protected IWaitOperations timeout;
    protected IAlertOperations alertHandle;
    protected IDragDropOperations dragDrop;
    TouchAction action;

    @BeforeTest()
    public void init() throws MalformedURLException, InterruptedException {

        // capabilities.setCapability("appium-version", "1.6.3");
        capabilities.setCapability("automationName", "xcuitest");
        // capabilities.setCapability("automationName", "Appium");
        /*
         * capabilities.setCapability("xcodeConfigFile",
         * "/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver"
         * + "/WebDriverAgent/Configurations/ProjectSettings.xcconfig");
         */
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "9.3");

        // capabilities.setCapability("deviceName", "iPo5-2");
        capabilities.setCapability("deviceName", "iM1-3");
        // capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        // capabilities.setCapability("udid", "41740182ce294a42f3b547bf13534fa09cfe53f4");
        capabilities.setCapability("udid", "e31509043d1cf18111ba63dfac7b3265bd943af9");
        // capabilities.setCapability("bundleId", "com.zeuslearning.natgeo.ourworld");
        // capabilities.setCapability("bundleId", "com.google.Gmail");
        // capabilities.setCapability("app", "/Users/dotnet/Documents/ourworld.app");
        // capabilities.setCapability("timeout", "30000");

        capabilities.setCapability("bundleId", "com.amazon.Amazon");
        // System.setProperty("webdriver.chrome.driver", "/Users/dotnet/Documents/git/automation-common/resources/chromedriver/mac64/chromedriver");

        driver = new IOSDriver<IOSElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextNames); // prints out something like NATIVE_APP \n WEBVIEW_1
        }
        driver.context((String) contextNames.toArray()[1]);

        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        textFile = new TextFileOps(driver);
        js = new ExecuteJavascript(driver);
        timeout = new Timeout(driver);
        alertHandle = new HtmlAlertOps(driver);
        dragDrop = new DragDrop(driver);
        action = new TouchAction(driver);
    }

    // @SuppressWarnings("deprecation")
    @Test
    public void googleTest() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 60);
        // driver.context("WEBVIEW_29");

        /*
         * NSString *jqueryCDN = @"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js";
         * NSData *jquery = [NSData dataWithContentsOfURL:[NSURL URLWithString:jqueryCDN]];
         * NSString *jqueryString = [[NSMutableString alloc] initWithData:jquery encoding:NSUTF8StringEncoding];
         * [webView stringByEvaluatingJavaScriptFromString:jqueryString];
         */

        // System.out.println(x);
        /*
         * jquery = textFile.readJsFiles("./resources/jquery/jquery.min.js");
         * jquerySimulations = textFile.readJsFiles("/Users/dotnet/Documents/git/automation-common/resources/dragdrop/jquery-simulate.js");
         * js.executeScript(jquery);
         * js.executeScript(jquerySimulations);
         */
        Actions actions = new Actions(driver);
        // jQueryDragDrop = textFile.readJsFiles("/Users/dotnet/Documents/git/automation-common/resources/dragdrop.js");
        // WebElement eleDest =
        // driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[5]"));

        // WebElement eleSrc =
        // driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"));

        // btn locators and touch clicks*********************
        /*
         * WebElement eleBtn = driver.findElement(By.cssSelector("[id='btn']"));
         * WebElement eleSrc = driver.findElement(By.cssSelector("#idBox1"));
         * WebElement eleDest = driver.findElement(By.cssSelector("#idBox5"));
         */
        // eleBtn.click();
        // eleSrc.click();
        // List<IOSElement> eleSrcByTagName = driver.findElements(By.tagName("div"));
        // WebElement iOseleSrc = eleSrcByTagName.get(0);
        // js.executeScript(jQueryDragDrop, eleSrc, 10, 10, 100, 100);
        // actions.clickAndHold(eleDest);
        // Thread.sleep(300);
        // actions.moveByOffset(100, 100);
        // Thread.sleep(300);
        // actions.release().build().perform();
        // actions.dragAndDropBy(eleDest, 100, 100).build().perform();
        //
        // eleSrc.click();
        //
        // dragDrop.dragDropUsingCode(By.cssSelector("[id='idBox1']"), 400, 500);
        //
        // action.longPress(eleDest);
        // Thread.sleep(500);
        // action.moveTo(100, 100).release();
        // action.perform();
        // action.tap(eleSrc);
        // action.longPress(eleSrc, 3);

        // driver.executeScript("$('#idBox1').simulate('drag-n-drop', {dx: 100, dy:100 , interpolation: {stepWidth: 15, stepDelay: 150}})", "");
        // eleBtn.click();
        // action.tap(eleSrc);

        // Inject Javascript***********************
        jqueryRange = textFile.readJsFiles("/Users/dotnet/Desktop/ios/www/dist/bililiteRange.js");
        jquery = textFile.readJsFiles("/Users/dotnet/Desktop/ios/www/dist/jquery-3.2.0.min.js");
        jquerySimulations = textFile.readJsFiles("/Users/dotnet/Desktop/ios/www/dist/jquery-simulate.js");
        jquerySimulationsSequence = textFile.readJsFiles("/Users/dotnet/Desktop/ios/www/dist/jquery-simulate-key-sequence.js");
        jquerySimulationsCombo = textFile.readJsFiles("/Users/dotnet/Desktop/ios/www/dist/jquery-simulate-key-combo.js");

        js.executeScript(jqueryRange);
        js.executeScript(jquery);
        js.executeScript(jquerySimulations);
        js.executeScript(jquerySimulationsSequence);
        js.executeScript(jquerySimulationsCombo);

        // Execute scripts ************************

        // driver.executeScript("var x = 2;");

        // Object ele2 = driver.executeScript("$('#btn').simulate('click');");
        // Object ele3 = driver.executeScript("$('#idBox1').simulate('drag-n-drop', {dx: 100, dy:100 , interpolation: {stepWidth: 15, stepDelay: 150}});");

        // js.executeScript("$('#btn').trigger('click',[true]);");
        // js.executeScript("arguments[0].trigger('click',[true]);", eleBtn);

        // selenium actions**************************
        // actions.click(eleBtn).build().perform();
        // actions.doubleClick(eleBtn).build().perform();
        // actions.dragAndDrop(eleSrc, eleDest).build().perform();

        // Appium touchaction***********************

        // action.press(eleSrc).moveTo(eleDest).release().perform();

        // dragDrop.dragDrop(driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther")),
        // driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[5]")));

        /*
         * WebElement ele1 = driver.findElement(By.xpath(
         * "//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"
         * ));
         * WebElement ele3 = driver.findElement(By.xpath(
         * "//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[5]/XCUIElementTypeButton"
         * ));
         * ele1.click();
         * //driver.findElement(By.xpath(
         * "//XCUIElementTypeApplication/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeButton"
         * )).click();
         * 
         * //Thread.sleep(5000);
         * //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
         * "//XCUIElementTypeApplication/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeAlert/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeButton"
         * )));
         * alertHandle.acceptAlert();
         * wait.until(ExpectedConditions.elementToBeClickable(ele3));
         * ele3.click();
         * alertHandle.acceptAlert();
         */

        // js.executeScript("$(arguments[0]).simulate('click')", eleDest);
        // WebElement eleDest = driver.findElement(By.cssSelector("#droppable"));
        // js.executeScript("$('#draggable').simulate('drag-n-drop', {dragTarget: arguments[0]});", eleDest);
        // js.executeScript("var x= $('#droppable');$('#draggable').simulate('drag-n-drop', {dragTarget: x});");
        // System.out.println(driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow[2]/XCUIElementTypeStatusBar/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]")).getAttribute("class"));
        // WebElement ele =
        // driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow[2]/XCUIElementTypeStatusBar/XCUIElementTypeOther[2]/XCUIElementTypeOther[2]"));
        /*
         * Point loc = ele.getLocation();
         * int x = loc.getX();
         * int y = loc.getY();
         * driver.tap(x, ele, y);
         */
        // driver.performMultiTouchAction(multiAction);
        // TouchAction().press(ele).wait().release();
        // js.executeScript("$(document.querySelectorAll('.btn')).simulate('click')");
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".container .document:nth-child(5)")));
        // driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[5]")).click();
        // System.out.println(driver.findElement(By.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[5]")).getClass().toString());
        Thread.sleep(10000);
    }

    @AfterTest
    public void afterTest() {
        // service.stop();
        driver.quit();
    }

    @Override
    public TouchAction perform() {
        // TODO Auto-generated method stub
        return null;
    }
}
