package com.zeuslearning.automation.selenium.interactions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zeuslearning.automation.interactions.IExecuteJavascript;
import com.zeuslearning.automation.interactions.IWaitOperations;

public class Timeout implements IWaitOperations {

    By elementLocator;
    int defaultTimeout;
    WebDriver driver;
    WebDriverWait waitTimeout;
    WebElement element;

    public Timeout(Object _driver) {
        defaultTimeout = 10;
        driver = (WebDriver) _driver;
    }

    public Timeout(Object _driver, int timeout) {
        defaultTimeout = timeout;
        driver = (WebDriver) _driver;
    }

    public boolean waitForVisibilityOf(Object locator) {
        return waitForVisibilityOf(locator, defaultTimeout);
    }

    public boolean waitForVisibilityOf(Object locator, int timeout) {
        waitTimeout = new WebDriverWait(driver, timeout);

        try {
            elementLocator = (By) locator;
            waitTimeout.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        } catch (ClassCastException e) {
            element = (WebElement) locator;
            waitTimeout.until(ExpectedConditions.visibilityOf(element));
        }

        return true;
    }

    public boolean waitForInvisibilityOf(Object locator) {
        return waitForInvisibilityOf(locator, defaultTimeout);
    }

    public boolean waitForInvisibilityOf(Object locator, int timeout) {
        waitTimeout = new WebDriverWait(driver, timeout);

        try {
            elementLocator = (By) locator;
            waitTimeout.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
        } catch (ClassCastException e) {
            element = (WebElement) locator;
            waitTimeout.until(ExpectedConditions.invisibilityOf(element));
        }

        return true;
    }

    public boolean waitForElementToBeClickable(Object locator) {
        return waitForElementToBeClickable(locator, defaultTimeout);
    }

    public boolean waitForElementToBeClickable(Object locator, int timeout) {
        waitTimeout = new WebDriverWait(driver, timeout);

        try {
            elementLocator = (By) locator;
            waitTimeout.until(ExpectedConditions.elementToBeClickable(elementLocator));
        } catch (ClassCastException e) {
            element = (WebElement) locator;
            waitTimeout.until(ExpectedConditions.elementToBeClickable(element));
        }

        return true;
    }

    public boolean waitForElementToBePresent(Object locator) {
        return waitForElementToBePresent(locator, defaultTimeout);
    }

    public boolean waitForElementToBePresent(Object locator, int timeout) {
        try {
            elementLocator = (By) locator;

            waitTimeout = new WebDriverWait(driver, timeout);
            waitTimeout.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
        } catch (ClassCastException e) {}
        return true;
    }

    public boolean waitForNoOfWindows(int number) {
        waitTimeout = new WebDriverWait(driver, defaultTimeout);
        waitTimeout.until(ExpectedConditions.numberOfWindowsToBe(number));
        return true;
    }

    public boolean waitForScriptValueMatch(String str, String script, IExecuteJavascript jsExec, int timeout) {
        waitTimeout = new WebDriverWait(driver, timeout);
        waitTimeout.until(ExpectedJsConditions.scriptValueMatches(str, script, jsExec));
        return true;
    }

    public boolean waitForScriptValueChange(String str, String script, IExecuteJavascript jsExec, int timeout) {
        waitTimeout = new WebDriverWait(driver, timeout);
        waitTimeout.until(ExpectedJsConditions.scriptValueChanges(str, script, jsExec));
        return true;
    }
}
