package com.zeuslearning.automation.appium.interactions;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.selenium.interactions.QueryHtml;

import io.appium.java_client.AppiumDriver;

public class Touch implements IMouseOperations {

    private AppiumDriver<WebElement> _driver;
    private WebElement _element;
    private QueryHtml _query;
    private WebDriver webDriver;
    private IWindowOperations _browser;
    private JavascriptExecutor js;
    private double screenHeight;
    private double serviceUrlBar;
    private double elementNativeViewX;
    private double elementNativeViewY;
    private int _attempts;
    private String webViewContext;
    private Actions _action;
    final double tapCount1 = 1.0;
    final double touchCount1 = 1.0;
    final double tapCount2 = 2.0;
    final double touchCount2 = 2.0;
    final private String altKeyId = "18";
    final private String ctrlKeyId = "17";
    final private String shiftKeyId = "16";
    final private ByCssSelector zeusEngineIframe = new ByCssSelector("#zeusengine");
    final private ByCssSelector openKeyboard = new ByCssSelector("[class=\"openkeyboard\"]");
    final private ByCssSelector altKey = new ByCssSelector("#keyboard #keylist li[id='18']");
    final private ByCssSelector ctrlKey = new ByCssSelector("#keyboard #keylist li[id='17']");
    final private ByCssSelector shiftKey = new ByCssSelector("#keyboard #keylist li[id='16']");
    final private ByCssSelector closeKeyboard = new ByCssSelector("#keyboard #keylist li[id='508']");

    public Touch() {
        _driver = null;
    }

    public Touch(Object driver, IWindowOperations browser) {
        _driver = (AppiumDriver<WebElement>) driver;
        _action = new Actions(_driver);
        webDriver = (WebDriver) driver;
        _query = new QueryHtml(webDriver);
        _browser = browser;
        js = (JavascriptExecutor) webDriver;
        setDefaultMeasurements();
    }

    private void setDefaultMeasurements() {
        screenHeight = webDriver.manage().window().getSize().getHeight();

        serviceUrlBar = 100; // As tested in iPad Pro
    }

    public void calculateInteractionPoints(boolean center) {

        // Element Coordinates in WebView
        int elementwebViewX = _element.getLocation().getX();
        int elementwebViewY = _element.getLocation().getY();
        int elementWidth = _element.getSize().width;
        int elementHeight = _element.getSize().height;

        // Switching to Native view to use the native supported methods
        webViewContext = _driver.getContext();
        _driver.context("NATIVE_APP");
        setDefaultMeasurements();
        double xOffset = _browser.getClientX();
        double yOffset = _browser.getClientY() + serviceUrlBar;

        elementNativeViewX = elementwebViewX + xOffset;
        elementNativeViewY = elementwebViewY + yOffset;

        if (center) {
            elementNativeViewX += (elementWidth / 2);
            elementNativeViewY += (elementHeight / 2);
        } else {
            // Adding 2 just to remove the 0.9999999 error
            elementNativeViewX += 2;
            elementNativeViewY += 2;
        }
    }

    private void switchContextBackToWebview() {
        // Switching back to Web View
        _driver.context(webViewContext);
    }

    public void tapOnElement() {
        js.executeScript("mobile: tap", new HashMap<String, Double>() {
            {
                put("tapCount", 1.0);
                put("touchCount", 1.0);
                put("duration", 0.5);
                put("x", elementNativeViewX);
                put("y", elementNativeViewY);
            }
        });
        switchContextBackToWebview();
    }

    public void pressAndHold() {
        js.executeScript("mobile: touchAndHold", new HashMap<String, Double>() {
            {
                put("tapCount", 1.0);
                put("touchCount", 1.0);
                put("duration", 2.0);
                put("x", elementNativeViewX);
                put("y", elementNativeViewY);
            }
        });
        switchContextBackToWebview();
    }

    public void doubleTap() {
        js.executeScript("mobile: doubleTap", new HashMap<String, Double>() {
            {
                put("tapCount", 2.0);
                put("touchCount", 2.0);
                put("duration", 0.5);
                put("x", elementNativeViewX);
                put("y", elementNativeViewY);
            }
        });
        switchContextBackToWebview();
    }

    public boolean click(Object locator) {
        _element = _query.castLocator(locator);
        calculateInteractionPoints(true);
        tapOnElement();
        return true;
    }

    public boolean click(Object locator, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey) {
        setModifierKeys(ctrlKey, altKey, shiftKey);
        click(locator);
        setModifierKeys(false, false, false);
        return true;
    }

    private void setModifierKeys(boolean bCtrlKey, boolean bAltKey, boolean bShiftKey) {
        openKeyBoard();
        setCtrlMode(bCtrlKey);
        setAltMode(bAltKey);
        setShiftMode(bShiftKey);
        closeKeyBoard();
    }

    public boolean clickAllElementsLocatedBy(Object locator) {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean clickUsingOffset(Object locator, int x, int y) {
        _element = _query.castLocator(locator);
        calculateInteractionPoints(false);
        elementNativeViewX += x;
        elementNativeViewY += y;
        tapOnElement();
        return true;
    }

    public boolean clickWithoutScrolling(Object locator) {
        click(locator);
        return true;
    }

    public boolean contextClick(Object locator) {
        _element = _query.castLocator(locator);
        calculateInteractionPoints(true);
        pressAndHold();
        return true;
    }

    public boolean contextClickUsingCode(Object locator) {
        return contextClick(locator);
    }

    public boolean contextClickUsingOffset(Object locator, int x, int y) {
        _element = _query.castLocator(locator);
        calculateInteractionPoints(false);
        elementNativeViewX += x;
        elementNativeViewY += y;
        pressAndHold();
        return true;
    }

    public boolean contextClickWithoutScrolling(Object locator) {
        return contextClick(locator);
    }

    public boolean doubleClick(Object locator) {
        _element = _query.castLocator(locator);
        calculateInteractionPoints(true);
        doubleTap();
        return true;
    }

    public boolean doubleClickUsingCode(Object locator) {
        return doubleClick(locator);
    }

    public boolean doubleClickUsingCodeWithTwoClicks(Object locator) {
        return doubleClick(locator);
    }

    public boolean doubleClickUsingOffset(Object locator, int x, int y) {
        _element = _query.castLocator(locator);
        calculateInteractionPoints(false);
        elementNativeViewX += x;
        elementNativeViewY += y;
        doubleTap();
        return true;
    }

    public boolean doubleClickWithoutScrolling(Object locator) {
        return doubleClick(locator);
    }

    public boolean dragdropUsingMouse(Object locator, int offsetX1, int offsetY1, int offsetX2, int offsetY2) {
        _element = _query.castLocator(locator);
        calculateInteractionPoints(false);
        elementNativeViewX += offsetX1;
        elementNativeViewY += offsetY1;
        dragFromToForDuration(offsetX2, offsetY2);
        return true;
    }

    private void dragFromToForDuration(final int offsetX2, final int offsetY2) {
        js.executeScript("mobile: dragFromToForDuration", new HashMap<String, Double>() {
            {
                put("duration", 1.2);
                put("fromX", elementNativeViewX);
                put("fromY", elementNativeViewY);
                put("toX", elementNativeViewX + offsetX2);
                put("toY", elementNativeViewY + offsetY2);
            }
        });
        switchContextBackToWebview();
    }

    public boolean listClick(Object locator, int index) {
        List<Object> elements = _query.findElements(locator);
        for (Object element : elements) {
            click(element);
        }
        return true;
    }

    public boolean mouseDown(Object locator) {
        _element = _query.castLocator(locator);
        return true;
    }

    public boolean mouseDown(Object locator, int xOffset, int yOffset) {
        _element = _query.castLocator(locator);
        return true;
    }

    public boolean mouseMove(Object locator, int xOffset, int yOffset) {
        // TODO check for mousedown action if exists, dragAndDrop
        return true;
    }

    public Object mouseOver(Object locator) {
        return null;
    }

    public boolean mouseUp(Object locator) {
        // TODO if mouse moved than nothing else if down on same locator, tap on locator
        // click(locator);
        return true;
    }

    public boolean mouseUp(Object locator, int clientX, int clientY) {
        // TODO if mouse moved than nothing else if down on same locator, tap on locator
        clickUsingOffset(locator, clientX, clientY);
        return true;
    }

    public boolean scroll(int xVal, int yVal) {
        return true;
    }

    public boolean scroll(Object locator) {
        return true;
    }

    // functions below are specific to Cengage SAM automation
    // this includes
    // 1. void sendKey(ByCssSelector locator)
    // 2. void setAltMode(boolean enable)
    // 3. void setCtrlMode(boolean enable)
    // 4. void setShiftMode(boolean enable)
    // 5. void openKeyBoard()
    // 6. void closeKeyBoard()
    // 7. boolean checkKeyStatus(String id)

    private void sendKey(ByCssSelector locator) {
        WebElement temp = _element;
        _element = _driver.findElement(locator);
        calculateInteractionPoints(true);
        tapOnElement();
        _element = temp;
    }

    private void setAltMode(boolean enable) {
        if (checkKeyStatus(altKeyId) != enable) {
            sendKey(altKey);
        }
    }

    private void setCtrlMode(boolean enable) {
        if (checkKeyStatus(ctrlKeyId) != enable) {
            sendKey(ctrlKey);
        }
    }

    private void setShiftMode(boolean enable) {
        if (checkKeyStatus(shiftKeyId) != enable) {
            sendKey(shiftKey);
        }
    }

    private void openKeyBoard() {
        _browser.switchToDefault();
        _query.castLocator(openKeyboard).click();
        _browser.switchToNewFrame(zeusEngineIframe);
    }

    private void closeKeyBoard() {
        sendKey(closeKeyboard);
    }

    private boolean checkKeyStatus(String id) {
        String className = "";
        String script = "return $('#keyboard #keylist li[id=\"" + id + "\"] > div:nth-child(1)').attr('class');";
        try {
            className = (String) js.executeScript(script);
        } catch (Exception e) {
        }
        return className == "key_active_img_div";
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean clickUsingOffsetWithKeys(Object locator, int x, int y, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean simulateDoubleClickUsingOffset(Object locator, int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }
}
