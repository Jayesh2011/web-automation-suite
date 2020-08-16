package com.zeuslearning.automation.selenium.interactions;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.zeuslearning.automation.interactions.IVerifications;

public class Verifications implements IVerifications {

    protected HtmlAlertOps alert;
    protected QueryHtml query;
    protected Timeout timeout;
    protected WebDriver _driver;

    public Verifications(Object driver) {
        _driver = (WebDriver) driver;
        alert = new HtmlAlertOps(_driver);
        query = new QueryHtml(_driver);
        timeout = new Timeout(_driver);
    }

    public int compareStrings(String string1, String string2) {
        return string1.compareTo(string2);
    }

    public int compareStringsIgnoreCase(String string1, String string2) {
        return string1.compareToIgnoreCase(string2);
    }

    public boolean contains(String str1, String str2) {
        return str1.length() >= str2.length() && str1.contains(str2);
    }

    public boolean isAlertPresent() {
        if (alert.switchToAlert() != null) {
            return true;
        }
        return false;
    }

    public boolean isElementDisplayed(Object element) {
        try {
            timeout.waitForVisibilityOf(element);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isElementDisplayed(Object element, int period) {
        try {
            timeout.waitForVisibilityOf(element, period);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isElementNotDisplayed(Object element) {
        try {
            timeout.waitForInvisibilityOf(element);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isElementNotDisplayed(Object element, int period) {
        try {
            timeout.waitForInvisibilityOf(element, period);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isElementPresent(Object element) {
        try {
            timeout.waitForElementToBePresent(element);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isElementPresent(Object element, int period) {
        try {
            timeout.waitForElementToBePresent(element, period);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isEnabled(Object locator) {
        return query.castLocator(locator).isEnabled();
    }

    public boolean isRegexMatch(String actualString, String expectedRegex) {
        return actualString.matches(expectedRegex);
    }

    public boolean isRegexMatchIgnoreCase(String actualString, String expectedRegex) {
        return actualString.matches("(?i)" + expectedRegex);
    }

    public boolean isSelected(Object locator) {
        return query.castLocator(locator).isSelected();
    }

    public boolean isStringMatch(String actual, String expected) {
        return actual.equals(expected);
    }

    public boolean isStringMatchIgnoreCase(String actual, String expected) {
        return actual.equalsIgnoreCase(expected);
    }

    public boolean isTitleEqualTo(String expectedTitle) {
        return isStringMatch(expectedTitle, query.getTitle());
    }

    public boolean verifyTextOnPage(Object locator, String expected) {
        return isStringMatch(expected, query.getText(locator));
    }

    public boolean verifySubstringOnPage(Object locator, String expected) {
        String wholeString = query.getText(locator);
        return contains(wholeString, expected);
    }
}
