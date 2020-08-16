package com.zeuslearning.automation.selenium.interactions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.zeuslearning.automation.interactions.IDropDownOperations;
import com.zeuslearning.automation.interactions.IMouseOperations;

public class DropDown implements IDropDownOperations {

    private IMouseOperations _mouse;
    private QueryHtml _query;
    private WebDriver _driver;
    private Select _select;

    public DropDown(Object driver) {
        _driver = (WebDriver) driver;
        _mouse = new Mouse(_driver);
        _query = new QueryHtml(_driver);
    }

    public boolean clickOnHoverMenuOption(Object menuPath, Object menuOptionPath) {
        _mouse.mouseOver(menuPath);
        ((Actions) _mouse.mouseOver(menuOptionPath)).click().perform();
        return true;
    }

    public boolean selectOptionByText(Object locator, String text) {
        _select = new Select(_query.castLocator(locator));
        _select.selectByVisibleText(text);
        return true;
    }

    public boolean selectOptionByIndex(Object locator, int index) {
        _select = new Select(_query.castLocator(locator));
        _select.selectByIndex(index);
        return true;
    }

    public boolean selectOptionByValue(Object locator, String value) {
        _select = new Select(_query.castLocator(locator));
        _select.selectByValue(value);
        return true;
    }

    public boolean deselectAllOptions(Object locator) {
        _select = new Select(_query.castLocator(locator));
        _select.deselectAll();
        return true;
    }
}
