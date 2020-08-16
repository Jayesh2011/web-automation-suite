package com.zeuslearning.automation.selenium.interactions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.zeuslearning.automation.interactions.IDragDropOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;

public class DragDrop implements IDragDropOperations {

    private WebDriver _driver;
    private Actions action;
    private QueryHtml query;
    private JavascriptExecutor js;
    private WebElement sourceElement, targetElement;
    private IWindowOperations _browser;

    public DragDrop(Object driver) {
        _driver = (WebDriver) driver;
        action = new Actions(_driver);
        action.build();
        query = new QueryHtml(_driver);
        js = (JavascriptExecutor) _driver;
        _browser = new BrowserWindow(driver);
    }

    public boolean dragDrop(Object sourceLocator, Object targetLocator) {
        sourceElement = query.castLocator(sourceLocator);
        targetElement = query.castLocator(targetLocator);
        action.moveToElement(sourceElement).perform();
        action.clickAndHold().perform();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
        action.moveToElement(targetElement).perform();
        action.release(targetElement).perform();
        return true;
    }

    public boolean dragDrop(Object sourceLocator, int xOffset, int yOffset) {
        action.dragAndDropBy(query.castLocator(sourceLocator), xOffset, yOffset).perform();
        return true;
    }

    public boolean dragDropUsingCode(Object sourceLocator, Object targetLocator) {
        sourceElement = query.castLocator(sourceLocator);
        targetElement = query.castLocator(targetLocator);

        _browser.checkAndInjectSimulations();
        js.executeScript("$(arguments[0]).simulate('drag-n-drop',{dragTarget:arguments[1]});", sourceElement, targetElement);
        return true;
    }

    public boolean dragDropUsingCode(Object sourceLocator, Object targetLocator, int stepWidth, int stepDelay) {
        sourceElement = query.castLocator(sourceLocator);
        targetElement = query.castLocator(targetLocator);

        _browser.checkAndInjectSimulations();
        js.executeScript(
                        "$(arguments[0]).simulate('drag-n-drop',{dragTarget:arguments[1], interpolation: {stepWidth: arguments[2], stepDelay: arguments[3]}});",
                        sourceElement, targetElement, stepWidth, stepDelay);

        return true;
    }

    public boolean dragDropUsingCode(Object sourceLocator, int dx, int dy, int stepWidth, int stepDelay) {
        sourceElement = query.castLocator(sourceLocator);

        _browser.checkAndInjectSimulations();
        try {
            js.executeScript(
                            "$(arguments[0]).simulate('drag-n-drop',{dx:arguments[1],dy:arguments[2],interpolation: {stepWidth: arguments[3], stepDelay: arguments[4]}});",
                            sourceElement, dx, dy, stepWidth, stepDelay);
        } catch (Exception e) {}
        return true;
    }

    public boolean dragDropUsingPostionsInsideElement(Object locator, int xOffset, int yOffset, int dx, int dy, int stepWidth, int stepDelay) {
        sourceElement = query.castLocator(locator);

        _browser.checkAndInjectSimulations();
        try {
            js.executeScript(
                            "$(arguments[0]).simulate('drag-n-drop',{clientX:arguments[1],clientY:arguments[2],dx:arguments[3],dy:arguments[4],interpolation: {stepWidth: arguments[5], stepDelay: arguments[6]}});",
                            sourceElement, xOffset, yOffset, dx, dy, stepWidth, stepDelay);
        } catch (Exception e) {}
        return true;
    }
}
