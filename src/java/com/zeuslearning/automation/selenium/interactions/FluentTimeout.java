package com.zeuslearning.automation.selenium.interactions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.zeuslearning.automation.interactions.IExecuteJavascript;
import com.zeuslearning.automation.interactions.IWaitOperations;

public class FluentTimeout implements IWaitOperations {

    private int _defaultTimeout;
    private Wait<WebDriver> _waitTimeout;
    private WebDriver _driver;
    private By _elementLocator;
    private WebElement _element;

    public FluentTimeout(Object driver) {
        _defaultTimeout = 20;
        _driver = (WebDriver) driver;
    }

    public FluentTimeout(Object driver, int timeout) {
        _defaultTimeout = timeout;
        _driver = (WebDriver) driver;
    }

    private void setTimeout(int timeout) {
        _waitTimeout = new FluentWait<WebDriver>(_driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(50, TimeUnit.MILLISECONDS)
                        .ignoring(NoSuchElementException.class);
    }

    public boolean waitForVisibilityOf(Object locator) {
        return waitForVisibilityOf(locator, _defaultTimeout);
    }

    public boolean waitForVisibilityOf(Object locator, int timeout) {
        setTimeout(timeout);

        try {
            _elementLocator = (By) locator;
            _waitTimeout.until(ExpectedConditions.visibilityOfElementLocated(_elementLocator));
        } catch (ClassCastException e) {
            _element = (WebElement) locator;
            _waitTimeout.until(ExpectedConditions.visibilityOf(_element));
        }

        return true;
    }

    public boolean waitForInvisibilityOf(Object locator) {
        return waitForInvisibilityOf(locator, _defaultTimeout);
    }

    public boolean waitForInvisibilityOf(Object locator, int timeout) {
        setTimeout(timeout);

        try {
            _elementLocator = (By) locator;
            _waitTimeout.until(ExpectedConditions.invisibilityOfElementLocated(_elementLocator));
        } catch (ClassCastException e) {
            _element = (WebElement) locator;
            _waitTimeout.until(ExpectedConditions.invisibilityOf(_element));
        }

        return true;
    }

    public boolean waitForElementToBeClickable(Object locator) {
        return waitForElementToBeClickable(locator, _defaultTimeout);
    }

    public boolean waitForElementToBeClickable(Object locator, int timeout) {
        setTimeout(timeout);

        try {
            _elementLocator = (By) locator;
            _waitTimeout.until(ExpectedConditions.elementToBeClickable(_elementLocator));
        } catch (ClassCastException e) {
            _element = (WebElement) locator;
            _waitTimeout.until(ExpectedConditions.elementToBeClickable(_element));
        }

        return true;
    }

    public boolean waitForElementToBePresent(Object locator) {
        return waitForElementToBePresent(locator, _defaultTimeout);
    }

    public boolean waitForElementToBePresent(Object locator, int timeout) {
        _elementLocator = (By) locator;

        setTimeout(timeout);
        _waitTimeout.until(ExpectedConditions.presenceOfElementLocated(_elementLocator));
        return true;
    }

    public boolean waitForNoOfWindows(int number) {
        setTimeout(_defaultTimeout);
        _waitTimeout.until(ExpectedConditions.numberOfWindowsToBe(number));
        return true;
    }

    public boolean waitForScriptValueMatch(String str, String script, IExecuteJavascript jsExec, int timeout) {
        setTimeout(timeout);
        _waitTimeout.until(ExpectedJsConditions.scriptValueMatches(str, script, jsExec));
        return true;
    }

    public boolean waitForScriptValueChange(String str, String script, IExecuteJavascript jsExec, int timeout) {
        setTimeout(timeout);
        _waitTimeout.until(ExpectedJsConditions.scriptValueChanges(str, script, jsExec));
        return true;
    }
}
