package com.zeuslearning.automation.unittests;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.io.TextFileOps;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.DragDrop;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.TouchAction;
import io.appium.java_client.PerformsTouchActions;
import org.openqa.selenium.WebElement;

public class RunDragDropOnIpad {
    public AppiumDriver<IOSElement> driver;
    public DragDrop drag;
    public BrowserWindow browse;
    // public SafariDriver driver;
    // public WebDriver driver;
    IDriver iDriver;
    protected String jqueryRange, jquery, jquerySimulations, jquerySimulationsSequence, jquerySimulationsCombo, jQueryDragDrop;
    // WebDriver driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();
    PerformsTouchActions performsTouchActions;
    AppiumDriverLocalService service;
    protected JavascriptExecutor js;
    protected TextFileOps textFile;

    // TouchSequence touchActions;
    @BeforeTest()
    public void init() throws MalformedURLException, InterruptedException {

        // iDriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
        // driver = (WebDriver) iDriver.getDriver("safari");

        /*
         * service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
         * .withIPAddress("0.0.0.0")
         * .usingPort(4723));
         * service.start();
         * Thread.sleep(5000);
         */
        capabilities.setCapability("appium-version", "1.6.3");
        capabilities.setCapability("automationName", "xcuitest");
        capabilities.setCapability("xcodeConfigFile",
                        "/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver" + "/WebDriverAgent/Configurations/ProjectSettings.xcconfig");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "9.3");

        // capabilities.setCapability("deviceName", "iPo5-2");
        capabilities.setCapability("deviceName", "iM1-3");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        // capabilities.setCapability("udid", "41740182ce294a42f3b547bf13534fa09cfe53f4");
        capabilities.setCapability("udid", "e31509043d1cf18111ba63dfac7b3265bd943af9");
        System.setProperty("webdriver.chrome.driver", "/Users/dotnet/Documents/git/automation-common/resources/chromedriver/mac64/chromedriver");
        // driver = new SafariDriver();

        driver = new IOSDriver<IOSElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        drag = new DragDrop(driver);
        browse = new BrowserWindow(driver);
        js = driver;
        textFile = new TextFileOps(driver);
    }

    @Test
    public void googleTest() throws Exception {

        // System.out.println("Appium started: "+service.isRunning());
        // browse.open("http://192.168.21.50:8090/ipadDragNDrop/Dragipad.htm");
        browse.open("http://192.168.21.50:8090/dragNdropTouchpunch/dranNdrop.html");
        // browse.open("http://code.makery.ch/library/dart-drag-and-drop/");
        // browse.open("http://marcojakob.github.io/dart-dnd/basic/web/");
        /*
         * jquery = textFile.readJsFiles("./resources/jquery/jquery-3.1.1.min.js");
         * jquerySimulations = textFile.readJsFiles("/Users/dotnet/Documents/git/automation-common/resources/dragdrop/jquery-simulate.js");
         */
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

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        // document.getElementById('draggable').offsetLeft
        // Object ele3 = driver.executeScript("$('#draggable').simulate('drag-n-drop', {dx: 100, dy:100 , interpolation: {stepWidth: 15, stepDelay: 150}});");
        // Object ele3 = driver.executeScript("var x= $('#droppable');
        // x.text($('#draggable').offset().left)");//driver.findElement(By.id("draggable")).getLocation().getX()
        Object ele3 = driver.executeScript("var x= $('#droppable');$('#draggable').simulate('drag-n-drop', {dragTarget: x});");
        // Object ele3 = driver.executeScript("$('#idBox1').simulate('mousedown');");
        // Object ele4 = driver.executeScript("$('#idBox1').simulate('mousemove', {clientX: 100, clientY: 100});");
        // Object ele5 = driver.executeScript("$('#idBox1').simulate('mouseup');");

        // drag.dragDropUsingCode((Object)By.cssSelector("#dragger"), (Object)By.cssSelector(".item:nth-child(5)"));
        // drag.dragDropUsingCode((Object)By.cssSelector("#dragger"), 100, 100);
        // var a = $("iframe[src='http://marcojakob.github.io/dart-dnd/basic/web/']");
        // WebElement element = driver.findElement(By.cssSelector(".trash"));
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".container .document:nth-child(5)")));
        // js.executeScript("$('#idBox1').simulate('drag-n-drop', {dx: 100, dy:100 , interpolation: {stepWidth: 15, stepDelay: 150}})");
        // js.executeScript("var a = $('.article-content > iframe:nth-child(9)'); var innerDoc = a[0].contentDocument || a[0].contentWindow.document;
        // $(window.innerDoc.document.querySelectorAll('.container .document:nth-child(4)')).simulate('drag-n-drop', {dx: 450})");
        // js.executeScript("$(document.querySelectorAll('.container .document:nth-child(5)')).simulate('drag-n-drop', {dx: 450})", element);
        // drag.dragDropUsingCode((Object)By.cssSelector(".draggable:nth-child(1)"), 100, 100);
        Thread.sleep(50000);
    }

    @AfterTest
    public void afterTest() {
        // service.stop();
        driver.quit();
    }
}
