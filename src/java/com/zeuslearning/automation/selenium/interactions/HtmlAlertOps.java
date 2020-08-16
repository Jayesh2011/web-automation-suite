package com.zeuslearning.automation.selenium.interactions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.zeuslearning.automation.interactions.IAlertOperations;

public class HtmlAlertOps implements IAlertOperations {

    private WebDriver _driver;

    public HtmlAlertOps() {
        _driver = null;
    }

    public HtmlAlertOps(Object driver) {
        _driver = (WebDriver)driver;
    }

    public boolean dismissAlert() {
        try {
            ((Alert) switchToAlert()).dismiss();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean acceptAlert() {
        try {
            ((Alert) switchToAlert()).accept();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAlertText() {
        try {
            return ((Alert) switchToAlert()).getText();
        } catch (NoAlertPresentException e) {
            return e.getMessage();
        }
    }

    public boolean sendPromptText(String text) {
        try {
            ((Alert) switchToAlert()).sendKeys(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object switchToAlert() {
        int attempts = 3;

        while (attempts > 0) {
            try {
                return _driver.switchTo().alert();
            } catch (Exception e) {
                --attempts;
            }
        }
        return false;
    }
}
