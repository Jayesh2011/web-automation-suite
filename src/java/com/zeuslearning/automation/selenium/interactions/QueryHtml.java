package com.zeuslearning.automation.selenium.interactions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zeuslearning.automation.interactions.IQueryHtmlElement;

public class QueryHtml implements IQueryHtmlElement {

    protected WebDriver _driver;
    protected Timeout _timeout;
    private WebElement _element;
    ExecuteJavascript js;

    public QueryHtml(Object driver) {
        _driver = (WebDriver) driver;
        _timeout = new Timeout(_driver);
        js = new ExecuteJavascript(driver);
    }

    public Object findElementWithoutWait(Object locator) {
        return _driver.findElement((By) locator);
    }
    
    public Object findElement(Object locator) {
        _timeout.waitForElementToBePresent(locator);

        return _driver.findElement((By) locator);
    }

    public Object findElementWithinElement(Object element, Object locator) {
        WebElement ele = castLocator(element);
        By loc = (By) locator;

        return ele.findElement(loc);
    }

    public String getAttributes(Object locator, String attributeName) {
        _element = castLocator(locator);
        return _element.getAttribute(attributeName);
    }

    public String getCss(Object locator, String propertyName) {
        _element = castLocator(locator);
        return _element.getCssValue(propertyName);
    }

    public String getTitle() {
        return _driver.getTitle();
    }

    public Object getDimensionsOfElementLocatedBy(Object locator) {
        _element = castLocator(locator);
        return _element.getSize();
    }

    public Object getHeightOfElement(Object locator) {
        return ((Dimension) getDimensionsOfElementLocatedBy(locator)).height;
    }

    public Object getWidthOfElement(Object locator) {
        return ((Dimension) getDimensionsOfElementLocatedBy(locator)).width;
    }

    public List<Object> findElements(Object locator) {
        _timeout.waitForElementToBePresent(locator);

        List<WebElement> elements = _driver.findElements((By) locator);
        List<Object> webElements = new ArrayList<Object>(elements.size());
        for (WebElement element : elements) {
            webElements.add(element);
        }

        return webElements;
    }

    public String getText(Object locator) {
        int attempts = 3;

        _element = castLocator(locator);

        while (attempts > 0) {
            try {
                return _element.getText();
            } catch (StaleElementReferenceException e) {
                attempts--;
            }
        }
        return null;
    }

    public String getText(Object locator, int index) {
        int attempts = 3;

        while (attempts > 0) {
            try {
                return getText(findElements(locator).get(index));
            } catch (StaleElementReferenceException e) {
                attempts--;
            }
        }
        return null;
    }

    public String getUrl() {
        return _driver.getCurrentUrl();
    }

    public Object getCoordinates(Object locator) {
        _element = castLocatorWithoutWait(locator);
        return _element.getLocation();
    }

    private WebElement castLocatorWithoutWait(Object locator) {
        WebElement ele;

        try {
            ele = (WebElement) locator;
        } catch (ClassCastException e) {
            ele = (WebElement) findElementWithoutWait(locator);
        }

        return ele;
    }

    public Object getXCoordinates(Object locator) {
        return ((Point) getCoordinates(locator)).x;
    }

    public Object getYCoordinates(Object locator) {
        return ((Point) getCoordinates(locator)).y;
    }

    public String getBrowserName() {
        Capabilities cap = ((RemoteWebDriver) _driver).getCapabilities();
        return cap.getBrowserName();
    }

    public WebElement castLocator(Object locator) {
        WebElement ele;

        try {
            ele = (WebElement) locator;
        } catch (ClassCastException e) {
            ele = (WebElement) findElement(locator);
        }

        return ele;
    }

    public void removeDomElement(Object locator) {
        WebElement ele = castLocator(locator);

        js.executeScript("$(arguments[0].remove())", ele);
    }
}
