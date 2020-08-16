package com.zeuslearning.automation.selenium.interactions;

import java.util.List;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.zeuslearning.automation.interactions.IExecuteJavascript;
import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;

public class Mouse implements IMouseOperations {

    private WebDriver _driver;
    private IExecuteJavascript _js;
    private int _attempts;
    private QueryHtml _query;
    private Actions _action;
    private Timeout _timeout;
    private String _script;
    private WebElement _element;
    private IWindowOperations _browser;
    private String _browserName = "";

    public Mouse() {
        _driver = null;
        _js = null;
        _attempts = 0;
        _query = null;
        _action = null;
        _timeout = null;
        _script = "";
        _element = null;
    }

    public Mouse(Object driver) {
        _driver = (WebDriver) driver;
        _query = new QueryHtml(_driver);
        _js = new ExecuteJavascript(_driver);
        _action = new Actions(_driver);
        _action.build();
        _timeout = new Timeout(_driver);
        _element = null;
        _browser = new BrowserWindow(driver);
        _browserName = _query.getBrowserName().toLowerCase();
    }

    public boolean click(Object locator) {

        _attempts = 5;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                if (!_query.getBrowserName().equalsIgnoreCase("Internet Explorer")) {
                    scroll(_element);
                }
                _timeout.waitForVisibilityOf(_element);
                _timeout.waitForElementToBeClickable(_element);
                _element.click();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public void simulateClick(Object locator) {
    	 _attempts = 5;

         while (_attempts > 0) {
             try {
                 _timeout.waitForElementToBePresent(locator);
                 _element = _query.castLocator(locator);
                 _browser.checkAndInjectSimulations();
                 _js.executeScript("$(arguments[0]).simulate('mousemove').simulate('mousedown').simulate('mouseup');", _element);
                 return;
             } catch (Exception e) {
                 if (_attempts < 2) {
                     throw new RuntimeException(e.getLocalizedMessage());
                 }
                 --_attempts;
             }
         }
    }
    
    public boolean click(Object locator, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript(
                        "$(arguments[0]).simulate('mousedown', {ctrlKey:arguments[1],altKey:arguments[2],shiftKey:arguments[3],metaKey:arguments[4],button:0})",
                        _element, ctrlKey, altKey, shiftKey, metaKey);
                _js.executeScript(
                        "$(arguments[0]).simulate('mouseup', {ctrlKey:arguments[1],altKey:arguments[2],shiftKey:arguments[3],metaKey:arguments[4],button:0})",
                        _element, ctrlKey, altKey, shiftKey, metaKey);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    private void click(WebElement elementValue) {
        _script = "arguments[0].click()";
        try {
            _element.click();
        } catch (ElementNotVisibleException e) {
            _js.executeScript(_script, elementValue);
        }
    }

    public boolean clickAllElementsLocatedBy(Object locator) {
        List<Object> elements = _query.findElements(locator);
        boolean flag = true;

        for (Object elementValue : elements) {
            _timeout.waitForVisibilityOf(elementValue);
            _timeout.waitForElementToBeClickable(elementValue);
            _attempts = 3;
            while (_attempts > 0) {
                try {
                    click(elementValue);
                    break;
                } catch (Exception e) {
                    if (_attempts < 2) {
                        throw new RuntimeException(e.getLocalizedMessage());
                    }
                    --_attempts;
                }
            }
        }
        return flag;
    }

    public boolean clickUsingOffset(Object locator, int x, int y) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                int xPos = (Integer) _query.getXCoordinates(locator);
                int yPos = (Integer) _query.getYCoordinates(locator);
                _element = (WebElement) _query.findElement(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('mousemove', {clientX:arguments[1],clientY:arguments[2]})",
                        _element, xPos + x, yPos + y);
                _js.executeScript(
                        "$(arguments[0]).simulate('mousedown', {clientX:arguments[1],clientY:arguments[2],button:0})",
                        _element, xPos + x, yPos + y);
                _js.executeScript(
                        "$(arguments[0]).simulate('mouseup', {clientX:arguments[1],clientY:arguments[2],button:0})",
                        _element, xPos + x, yPos + y);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean clickWithoutScrolling(Object locator) {

        _attempts = 5;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _timeout.waitForVisibilityOf(_element);
                _timeout.waitForElementToBeClickable(_element);
                click(_element);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean contextClick(Object locator) {
        _element = _query.castLocator(locator);
        if ("safari".equalsIgnoreCase(_query.getBrowserName()) || "firefox".equalsIgnoreCase(_query.getBrowserName())) {
            mouseOver(locator);
            int xPos = (Integer) _query.getXCoordinates(locator) + (Integer) _query.getWidthOfElement(locator) / 2;
            int yPos = (Integer) _query.getYCoordinates(locator) + (Integer) _query.getHeightOfElement(locator) / 2;
            _js.executeScript(
                    "$(arguments[0]).simulate('mousedown', {clientX:arguments[1],clientY:arguments[2],button:2}).simulate('mouseup', {clientX:arguments[1],clientY:arguments[2],button:2})",
                    _element, xPos, yPos);
            return true;
        }

        _attempts = 3;

        while (_attempts > 0) {
            try {
                if (!_query.getBrowserName().equalsIgnoreCase("Internet Explorer")) {
                    scroll(_element);
                }
                _timeout.waitForVisibilityOf(_element);
                _timeout.waitForElementToBeClickable(_element);
                _element = _query.castLocator(locator);       
                ((Actions) mouseOver(locator)).contextClick().perform();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean contextClickUsingCode(Object locator) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('click', {button:2})", _element);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean contextClickUsingOffset(Object locator, int x, int y) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                int xPos = (Integer) _query.getXCoordinates(locator);
                int yPos = (Integer) _query.getYCoordinates(locator);
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('mousemove', {clientX:arguments[1],clientY:arguments[2]})",
                        _element, xPos + x, yPos + y);
                _js.executeScript(
                        "$(arguments[0]).simulate('mousedown', {clientX:arguments[1],clientY:arguments[2],button:2})",
                        _element, xPos + x, yPos + y);
                _js.executeScript(
                        "$(arguments[0]).simulate('mouseup', {clientX:arguments[1],clientY:arguments[2],button:2})",
                        _element, xPos + x, yPos + y);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean contextClickWithoutScrolling(Object locator) {
        _element = _query.castLocator(locator);
        if ("safari".equalsIgnoreCase(_query.getBrowserName())) {
            mouseOver(locator);
            int xPos = (Integer) _query.getXCoordinates(locator) + (Integer) _query.getWidthOfElement(locator) / 2;
            int yPos = (Integer) _query.getYCoordinates(locator) + (Integer) _query.getHeightOfElement(locator) / 2;
            _js.executeScript("return $(arguments[0]).simulate('click',{clientX:arguments[1],clientY:arguments[2],button:2})", _element, xPos, yPos);
            return true;
        }
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _timeout.waitForElementToBeClickable(locator);
                _element = _query.castLocator(locator);
                _timeout.waitForVisibilityOf(_element);
                ((Actions) mouseOver(locator)).contextClick().perform();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean clickUsingOffsetWithKeys(Object locator, int x, int y, boolean ctrlKey, boolean altKey,
            boolean shiftKey, boolean metaKey) {
        _attempts = 3;
        while (_attempts > 0) {
            try {
                int xPos = (Integer) _query.getXCoordinates(locator);
                int yPos = (Integer) _query.getYCoordinates(locator);
                _element = (WebElement) _query.findElement(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript(
                        "$(arguments[0]).simulate('mousemove', {clientX:arguments[1],clientY:arguments[2],ctrlKey:arguments[3],altKey:arguments[4],shiftKey:arguments[5],metaKey:arguments[6]})",
                        _element, xPos + x, yPos + y, ctrlKey, altKey, shiftKey, metaKey);
                _js.executeScript(
                        "$(arguments[0]).simulate('mousedown', {clientX:arguments[1],clientY:arguments[2],ctrlKey:arguments[3],altKey:arguments[4],shiftKey:arguments[5],metaKey:arguments[6],button:0})",
                        _element, xPos + x, yPos + y, ctrlKey, altKey, shiftKey, metaKey);
                _js.executeScript(
                        "$(arguments[0]).simulate('mouseup', {clientX:arguments[1],clientY:arguments[2],ctrlKey:arguments[3],altKey:arguments[4],shiftKey:arguments[5],metaKey:arguments[6],button:0})",
                        _element, xPos + x, yPos + y, ctrlKey, altKey, shiftKey, metaKey);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean doubleClick(Object locator) {
        _element = _query.castLocator(locator);
        if ("safari".equalsIgnoreCase(_query.getBrowserName()) || "firefox".equalsIgnoreCase(_query.getBrowserName())) {
            String locatorString = locator.toString();
            locatorString = locatorString.split(": ")[1];
            int xPos = (Integer) _query.getXCoordinates(locator) + (Integer) _query.getWidthOfElement(locator) / 2;
            int yPos = (Integer) _query.getYCoordinates(locator) + (Integer) _query.getHeightOfElement(locator) / 2;
            _browser.checkAndInjectSimulations();
            _js.executeScript(
                    "$(arguments[0]).simulate('mousedown',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('mouseup',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('mousedown',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('mouseup',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('dblclick', { clientX: arguments[1], clientY: arguments[2], button: 0})",
                    locatorString, xPos, yPos);
            return true;
        }

        _attempts = 3;

        while (_attempts > 0) {
            try {
                if (!_query.getBrowserName().equalsIgnoreCase("Internet Explorer")) {
                    scroll(_element);
                }
                _timeout.waitForVisibilityOf(locator);
                _timeout.waitForElementToBeClickable(locator);
                ((Actions) mouseOver(locator)).doubleClick().perform();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean doubleClickUsingCode(Object locator) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('dblclick')", _element);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean doubleClickUsingCodeWithTwoClicks(Object locator) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                mouseDown(locator);
                mouseUp(locator);
                mouseDown(locator);
                mouseUp(locator);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean doubleClickUsingOffset(Object locator, int x, int y) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                int xPos = (Integer) _query.getXCoordinates(locator);
                int yPos = (Integer) _query.getYCoordinates(locator);
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript(
                        "$(arguments[0]).simulate('mousedown',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('mouseup',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('mousedown',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('mouseup',{clientX: arguments[1], clientY: arguments[2], button: 0}).simulate('dblclick', { clientX: arguments[1], clientY: arguments[2], button: 0})",
                        _element, xPos + x, yPos + y);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean simulateDoubleClickUsingOffset(Object locator, int x, int y) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                int xPos = (Integer) _query.getXCoordinates(locator);
                int yPos = (Integer) _query.getYCoordinates(locator);
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript(
                        "$(arguments[0]).simulate('dblclick', { clientX: arguments[1], clientY: arguments[2], button: 0})",
                        _element, xPos + x, yPos + y);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean doubleClickWithoutScrolling(Object locator) {
        _element = _query.castLocator(locator);
        if ("safari".equalsIgnoreCase(_query.getBrowserName()) || "firefox".equalsIgnoreCase(_query.getBrowserName())) {
            String locatorString = locator.toString();
            locatorString = locatorString.split(": ")[1];
            int xPos = (Integer) _query.getXCoordinates(locator) + (Integer) _query.getWidthOfElement(locator) / 2;
            int yPos = (Integer) _query.getYCoordinates(locator) + (Integer) _query.getHeightOfElement(locator) / 2;
            _browser.checkAndInjectSimulations();
            _js.executeScript("$(arguments[0]).simulate('dblclick', { clientX: arguments[1], clientY: arguments[2], button: 0})", locatorString, xPos,
                            yPos);
            return true;
        }
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _timeout.waitForVisibilityOf(locator);
                _timeout.waitForElementToBeClickable(locator);
                ((Actions) mouseOver(locator)).doubleClick().perform();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean dragdropUsingMouse(Object locator, int offsetX1, int offsetY1, int width, int height) {
        try {
            _element = _query.castLocator(locator);
            int xPos = (Integer) _query.getXCoordinates(locator);
            int yPos = (Integer) _query.getYCoordinates(locator);
            int xPos1 = xPos + offsetX1;
            int yPos1 = yPos + offsetY1;
            int xPos2 = xPos + offsetX1 + width;
            int yPos2 = yPos + offsetY1 + height;
            _browser.checkAndInjectSimulations();
            _js.executeScript(
                    "$(arguments[0]).simulate('mousedown', {clientX:arguments[1],clientY:arguments[2],button:0}).simulate('mousemove', {clientX:arguments[3],clientY:arguments[4]}).simulate('mousemove', {clientX:arguments[3],clientY:arguments[4]}).simulate('mouseup', {clientX:arguments[3],clientY:arguments[4],button:0})",
                    _element, xPos1, yPos1, xPos2, yPos2);
        } catch (Exception e) {
        }
        return true;
    }

    public boolean listClick(Object locator, int index) {

        _attempts = 5;

        while (_attempts > 0) {
            _element = (WebElement) _query.findElements(locator).get(index);
            if (!_query.getBrowserName().equalsIgnoreCase("Internet Explorer")) {
                scroll(_element);
            }
            _timeout.waitForElementToBeClickable(locator);
            try {
                click(_element);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                _attempts--;
            }
        }
        return false;
    }

    public boolean mouseDown(Object locator) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('mousedown')", _element);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean mouseDown(Object locator, int x, int y) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('mousedown', {clientX:arguments[1],clientY:arguments[2]})",
                        _element, x, y);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean mouseMove(Object locator, int x, int y) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('mousemove', {clientX:arguments[1],clientY:arguments[2]})",
                        _element, x, y);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public Object mouseOver(Object locator) {
        _element = _query.castLocator(locator);
        if ("safari".equalsIgnoreCase(_query.getBrowserName()) || "firefox".equalsIgnoreCase(_query.getBrowserName())) {

            int xPos = (Integer) _query.getXCoordinates(locator) + (Integer) _query.getWidthOfElement(locator) / 2;
            int yPos = (Integer) _query.getYCoordinates(locator) + (Integer) _query.getHeightOfElement(locator) / 2;
            _browser.checkAndInjectSimulations();
            _js.executeScript("$(arguments[0]).simulate('mouseover', {clientX:arguments[1],clientY: arguments[2]})",
                    _element, xPos, yPos);
            return true;
        }

        _attempts = 3;

        while (_attempts > 0) {
            try {
                _action.moveToElement(_element).perform();
                return _action;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean mouseUp(Object locator) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('mouseup',{button:0})", _element);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean mouseUp(Object locator, int x, int y) {
        _attempts = 3;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _browser.checkAndInjectSimulations();
                _js.executeScript("$(arguments[0]).simulate('mouseup', {clientX:arguments[1],clientY:arguments[2]})",
                        _element, x, y);
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean scroll(int xVal, int yVal) {
        _script = "window.scrollBy(arguments[0], arguments[1])";
        _js.executeScript(_script, xVal, yVal);
        return true;
    }

    public boolean scroll(Object locator) {
        Object pageElement = _query.castLocator(locator);

        if (_browserName.matches("chrome|firefox")) {
            _js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'end', inline: 'nearest'});",
                    pageElement);
        } else {
            _js.executeScript("arguments[0].scrollIntoView(false);", pageElement);
        }
        return true;
    }

    public boolean clickNtimes(Object locator, int times) {
        _attempts = 3;
        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _action.moveToElement(_element);
                for (int looper = 0; looper < times; looper += 1) {
                    _action.click();
                }
                _action.perform();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

    public boolean clickNtimes(Object locator, int times, int xOffset, int yOffset) {
        _attempts = 3;
        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                _action.moveToElement(_element, xOffset, yOffset);
                for (int looper = 0; looper < times; looper += 1) {
                    _action.click();
                }
                _action.perform();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }
    
    public boolean clickWithScroll(Object locator) {
        _attempts = 5;

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                scroll(_element);
                _timeout.waitForVisibilityOf(_element);
                _timeout.waitForElementToBeClickable(_element);
                _element.click();
                return true;
            } catch (Exception e) {
                if (_attempts < 2) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                --_attempts;
            }
        }
        return false;
    }

}
