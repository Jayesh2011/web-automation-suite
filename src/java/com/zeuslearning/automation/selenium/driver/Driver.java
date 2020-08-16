package com.zeuslearning.automation.selenium.driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.io.BasicFileOps;
import com.zeuslearning.automation.io.PropertiesOps;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

public class Driver implements IDriver {
    BasicFileOps file = new BasicFileOps();
    DesiredCapabilities capabilities;
    Properties prop;
    private String _configPath;
    String browser;
    String driverPath;
    private WebDriver _driver;

    public Driver(String configPath) {
        _configPath = configPath;
        _driver = null;
        prop = PropertiesOps.readProperties(_configPath);
    }

    public Properties getConfigProperties() {
        return prop;
    }

    public Object getDriver(String browserName) {
        if (_driver == null) {
            this.initializeDriver(browserName);
        }
        return _driver;
    }

    public void initializeDriver(String browserName) {

        browser = prop.getProperty("browser");
        String os = System.getProperty("os.name");

        if (browser.equalsIgnoreCase("parallel")) {
            browser = browserName;
        }

        if (browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("Google Chrome") || browser.equalsIgnoreCase("GoogleChrome")) {
            ChromeOptions cOptions = new ChromeOptions();
            if (os.contains("Windows")) {
                driverPath = file.createPath(prop.getProperty("pathToChromeDriverWindows"));
            } else if (os.equalsIgnoreCase("linux")) {
                cOptions.setHeadless(true);
                cOptions.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
                driverPath = file.createPath(prop.getProperty("pathToChromeDriverLinux"));
            } else {
                driverPath = file.createPath(prop.getProperty("pathToChromeDriverMac"));
            }
            System.setProperty("webdriver.chrome.driver", driverPath);

            if ("headless".equalsIgnoreCase(System.getProperty("mode"))) {
                cOptions.setHeadless(true);
                cOptions.addArguments("--headless", "window-size=1920,1080", "--no-sandbox");
            }

            cOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
            cOptions.addArguments("disable-infobars");
            cOptions.addArguments("--start-maximized");

            _driver = new ChromeDriver(cOptions);
            _driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("ie") || browser.equalsIgnoreCase("Internet Explorer") || browser.equalsIgnoreCase("InternetExplorer")) {

            driverPath = file.createPath(prop.getProperty("pathToIEDriver"));
            System.setProperty("webdriver.ie.driver", driverPath);
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
            capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
            capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
            InternetExplorerOptions ieOptions = new InternetExplorerOptions(capabilities);
            ieOptions.enableNativeEvents();
            ieOptions.takeFullPageScreenshot();
            ieOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
            ieOptions.elementScrollTo(ElementScrollBehavior.BOTTOM);
            _driver = new InternetExplorerDriver(ieOptions);
            _driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("edge")) {

            capabilities = DesiredCapabilities.edge();
            capabilities.setAcceptInsecureCerts(true);
            capabilities.setJavascriptEnabled(true);
            capabilities.setCapability("unexpectedAlertBehaviour", UnexpectedAlertBehaviour.DISMISS);
            capabilities.setCapability("applicationCacheEnabled", false);
            capabilities.setCapability("ignoreZoomSettings", true);
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.merge(capabilities);
            driverPath = file.createPath(prop.getProperty("pathToEdgeDriver"));
            System.setProperty("webdriver.edge.driver", driverPath);
            _driver = new EdgeDriver(edgeOptions);
            _driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("safari-ios") || browser.equalsIgnoreCase("ios")) {

            capabilities = new DesiredCapabilities();
            capabilities.setCapability("appium-version", "1.6.4");
            capabilities.setCapability("automationName", "xcuitest");
            capabilities.setCapability("xcodeConfigFile", "/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver"
                            + "/WebDriverAgent/Configurations/ProjectSettings.xcconfig");
            capabilities.setCapability("newCommandTimeout", "600000");
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("platformVersion", prop.getProperty("osVersion"));
            capabilities.setCapability("deviceName", prop.getProperty("iosDeviceName"));
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
            capabilities.setCapability("udid", prop.getProperty("udid"));

            try {
                _driver = new IOSDriver<IOSElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
            } catch (MalformedURLException e) {}
            _driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        } else if (browser.equalsIgnoreCase("grid")) {
            if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("Google Chrome") || browserName.equalsIgnoreCase("GoogleChrome")) {
                capabilities = DesiredCapabilities.chrome();
            } else if (browserName.equalsIgnoreCase("ie") || browserName.equalsIgnoreCase("Internet Explorer")
                            || browserName.equalsIgnoreCase("InternetExplorer")) {
                capabilities = DesiredCapabilities.internetExplorer();
            } else if (browserName.equalsIgnoreCase("safari")) {
                capabilities = DesiredCapabilities.safari();
            } else {
                capabilities = DesiredCapabilities.firefox();
            }
            try {
                _driver = new RemoteWebDriver(new URL(prop.getProperty("hubUrl")), capabilities);
            } catch (MalformedURLException e) {}
            _driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("safari")) {
            _driver = new SafariDriver();
            _driver.manage().window().maximize();
        } else {
            FirefoxProfile profile = new FirefoxProfile();
            FirefoxOptions ffOptions = new FirefoxOptions();
            if (os.contains("Windows")) {
                driverPath = file.createPath(prop.getProperty("pathToGeckoDriverWindows"));
            } else if (os.equalsIgnoreCase("linux")) {
                ffOptions.addArguments("--headless");
                driverPath = file.createPath(prop.getProperty("pathToGeckoDriverLinux"));
            } else {
                driverPath = file.createPath(prop.getProperty("pathToGeckoDriverMac"));
            }
            System.setProperty("webdriver.gecko.driver", driverPath);

            try {
                JavaScriptError.addExtension(profile);
            } catch (IOException e) {}
            profile.setPreference("security.fileuri.strict_origin_policy", false);
            if ("headless".equalsIgnoreCase(System.getProperty("mode"))) {
                ffOptions.addArguments("window-size=1920,1080");
                ffOptions.setHeadless(true);
                profile.setPreference("browser.sessionhistory.max_entries", 5);
                //profile.setPreference("browser.cache.disk.enable", false);
                //profile.setPreference("browser.cache.memory.enable", true);
                //profile.setPreference("browser.cache.offline.enable", false);
                profile.setPreference("browser.cache.disk.capacity", 1000);
                profile.setPreference("browser.cache.memory.capacity", 10000);
                profile.setPreference("browser.safebrowsing.phishing.enabled", false);
                profile.setPreference("browser.safebrowsing.malware.enabled", false);
                profile.setPreference("browser.tabs.remote.autostart", false);
                profile.setPreference("browser.tabs.remote.autostart2", false);
                profile.setPreference("dom.ipc.processCount", 1);
            }
            ffOptions.setProfile(profile);
            ffOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

            _driver = new FirefoxDriver(ffOptions);
            if ("headless".equalsIgnoreCase(System.getProperty("mode"))) {
                _driver.manage().window().setSize(new Dimension(1920, 1080));
            } else {
                _driver.manage().window().maximize();                
            }
        }
    }

    public void quitDriver() {
        if (_driver != null) {
            _driver.quit();
        }
    }
}
