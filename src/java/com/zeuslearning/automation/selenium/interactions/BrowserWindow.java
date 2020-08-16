package com.zeuslearning.automation.selenium.interactions;

import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import com.zeuslearning.automation.interactions.IExecuteJavascript;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.io.TextFileOps;

public class BrowserWindow implements IWindowOperations {

    protected IExecuteJavascript js;
    protected int attempts;
    protected QueryHtml query;
    protected String jquery, jquerySimulations;
    protected TextFileOps textFile;
    protected Timeout timeout;
    protected WebDriver _driver;
    public int clientX = 0;
    public int clientY = 0;

    public BrowserWindow(Object driver) {
        _driver = (WebDriver) driver;
        js = new ExecuteJavascript(_driver);
        query = new QueryHtml(_driver);
        textFile = new TextFileOps(_driver);
        readJsFiles();
        timeout = new Timeout(_driver);
    }

    public BrowserWindow(Object driver, int width, int height) {
        _driver = (WebDriver) driver;
        js = new ExecuteJavascript(_driver);
        query = new QueryHtml(_driver);
        textFile = new TextFileOps(_driver);
        readJsFiles();
        setSize(width, height);
        timeout = new Timeout(_driver);
    }

    public String[] getAllWindowHandles() {
        Set<String> handles = _driver.getWindowHandles();
        return handles.toArray(new String[handles.size()]);
    }

    public Object getSize() {
        return _driver.manage().window().getSize();
    }

    public boolean injectSimulations() {

        js.executeScript(jquery);
        js.executeScript(jquerySimulations);
        return true;
    }

    public boolean maximizeWindow() {
        _driver.manage().window().maximize();
        return true;
    }

    public boolean navigateBack() {
        _driver.navigate().back();
        return true;
    }

    public boolean open(String url) {
        attempts = Integer.parseInt(System.getProperty("numOfAttempts", "3"));

        while (attempts > 0) {
            try {
                _driver.get(url);
                return true;
            } catch (Exception e) {
                --attempts;
            }
        }
        return false;
    }

    private void readJsFiles() {
        String path;
        if (System.getProperty("user.dir").contains("automation-common")) {
            path = "./resources";
        } else {
            path = "./automation-common/resources";
        }
        jquery = textFile.readJsFiles(path + "/jquery/jquery.min.js");
        jquery += textFile.readJsFiles(path + "/jquery/jquery-ui-1.11.4.min.js");
        jquerySimulations = textFile.readJsFiles(path + "/dragdrop/jquery-simulate.js");
        jquerySimulations += textFile.readJsFiles(path + "/dragdrop/bililiteRange.js");
        jquerySimulations += textFile.readJsFiles(path + "/dragdrop/jquery-simulate-key-combo.js");
        jquerySimulations += textFile.readJsFiles(path + "/dragdrop/jquery-simulate-key-sequence.js");
    }

    public boolean refresh() {
        _driver.navigate().refresh();
        return true;
    }

    public boolean setSize(int width, int height) {
        _driver.manage().window().setSize(new Dimension(width, height));
        return true;
    }

    public boolean switchToDefault() {
        setDefaultPostion();
        _driver.switchTo().defaultContent();
        return true;
    }

    public boolean switchToNewFrame(Object element) {
        Point positionOfIframe = (Point) query.getCoordinates(element);
        setContextPosition(positionOfIframe.x, positionOfIframe.y);
        _driver.switchTo().frame(query.castLocator(element));
        return true;
    }

    public boolean switchToNewFrame(Object element, int period) {
        Point positionOfIframe = (Point) query.getCoordinates(element);
        setContextPosition(positionOfIframe.x, positionOfIframe.y);
        _driver.switchTo().frame(query.castLocator(element));
        return true;
    }

    public boolean switchToNewFrameByIndex(int frameIndex) {
        _driver.switchTo().frame(frameIndex);
        return true;
    }

    public boolean switchToNewFrameByName(String frameName) {
        _driver.switchTo().frame(frameName);
        return true;
    }

    public boolean switchToWindow(String windowHandle) {
        _driver.switchTo().window(windowHandle);
        return true;
    }

    public void checkAndInjectSimulations() {
        try {
            String definition = (String) js.executeScript("return typeof($('body').simulate)");
            if ("undefined".contentEquals(definition)) {
                injectSimulations();
            }
        } catch (Exception e) {
            injectSimulations();
        }
    }

    public boolean closeWindow() {
        if (_driver.getWindowHandles().size() > 1) {
            _driver.close();
        }
        return true;
    }

    public int getClientX() {
        return this.clientX;
    }

    public int getClientY() {
        return this.clientY;
    }

    public void setContextPosition(int x, int y) {
        this.clientX += x;
        this.clientY += y;
    }

    public void setDefaultPostion() {
        this.clientX = 0;
        this.clientY = 0;
    }
}
