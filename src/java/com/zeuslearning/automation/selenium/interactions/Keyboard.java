package com.zeuslearning.automation.selenium.interactions;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.zeuslearning.automation.interactions.IKeyboardOperations;

public class Keyboard implements IKeyboardOperations {

    private Actions _action;
    private ExecuteJavascript _js;
    private int _attempts;
    private QueryHtml _query;
    private String _script;
    private WebDriver _driver;
    private WebElement _element;
    private Verifications _verify;
    private Mouse _mouse;
    private BrowserWindow _browser;
    private Robot _robot;
    private String regexContainsNumbers = "\\d";

    public Keyboard(Object driver) {
        _driver = (WebDriver) driver;
        _action = new Actions(_driver);
        _action.build();
        _js = new ExecuteJavascript(_driver);
        _query = new QueryHtml(_driver);
        _verify = new Verifications(driver);
        _mouse = new Mouse(_driver);
        _browser = new BrowserWindow(driver);
        try {
            _robot = new Robot();
        } catch (AWTException e) {
        }
    }

    public boolean multipleKeyStrokes(Object locator, String keys) {
        _element = _query.castLocator(locator);

        _browser.checkAndInjectSimulations();
        _js.executeScript("$(arguments[0]).simulate('key-combo', {combo: arguments[1]})", _element, keys);
        return true;
    }

    public boolean type(Object locator, String value) {
        _attempts = 3;
        String tagName;
        _script = "arguments[0].value=arguments[1];";

        while (_attempts > 0) {
            try {
                _element = _query.castLocator(locator);
                tagName = _element.getTagName();
                if (tagName.matches("input|div|span|p|body")) {
                    switch (_query.getBrowserName()) {
                        case "safari": {
                            Matcher matcher = Pattern.compile("([^0-9]*)(\\d+)").matcher(value);
                            while (matcher.find()) {
                                System.out.println(matcher.group());
                                if (!"".contentEquals(matcher.group(1))) {
                                    _element.sendKeys(matcher.group(1));
                                }
                                if (!"".contentEquals(matcher.group(2))) {
                                    char[] numberChars = matcher.group(2).toCharArray();

                                    for (int charIndex = 0; charIndex < numberChars.length; charIndex++) {
                                        _js.executeScript(
                                                "arguments[0].dispatchEvent(new KeyboardEvent('keydown',{'keyCode':arguments[1],'which':arguments[1],'charCode':arguments[1]}));arguments[0].dispatchEvent(new KeyboardEvent('keyup',{'keyCode':arguments[1],'which':arguments[1],'charCode':arguments[1]}));arguments[0].dispatchEvent(new KeyboardEvent('keypress',{'keyCode':arguments[1],'which':arguments[1],'charCode':arguments[1]}))",
                                                _element, 48 + (numberChars[charIndex]));
                                        Thread.sleep(200);
                                    }
                                }
                            }
                            break;
                        }
                        case "firefox": {
                            String[] stringWordsArray = value.split(" ");
                            _element.sendKeys(stringWordsArray[0]);
                            for (int i = 1; i < stringWordsArray.length; i++) {
                                _js.executeScript(
                                        "$(arguments[0])[0].dispatchEvent(new KeyboardEvent('keypress',{'keyCode':32,'which':32,'charCode':32}))",
                                        _element);
                                _element.sendKeys(stringWordsArray[i]);
                            }
                            break;
                        }
                        default: {
                            _element.sendKeys(value);
                        }
                    }
                } else {
                    _js.executeScript(_script, _element, value);
                }
                return true;
            } catch (Exception e) {
                --_attempts;
            }
        }
        return false;
    }

    public boolean typeAfterClear(Object locator, String value) {
        _element = _query.castLocator(locator);
        clearTextField(locator);
        type(locator, value);

        return true;
    }

    public boolean clearTextField(Object locator) {
        _element = _query.castLocator(locator);
        _element.click();
        if (toBeCleared(_element)) {
            _element.clear();
        }
        if (toBeCleared(_element)) {
            _mouse.click(locator);
            multipleKeyStrokes(locator, "end");
            multipleKeyStrokes(locator, "shift+home");
            multipleKeyStrokes(locator, "backspace");
        }
        return true;
    }

    private boolean toBeCleared(Object element) {
        boolean flag = true;

        String jText = (String) _js.executeScript("return $(arguments[0]).val();", element);
        String text = _query.getText(element);

        if (_verify.isStringMatch("", jText) && _verify.isStringMatch("", text)) {
            flag = false;
        }

        return flag;
    }

    public boolean multipleKeyStrokes(Object locator, String keys, int loopCount) {
        for (int i = 0; i < loopCount; i++) {
            multipleKeyStrokes(locator, keys);
        }
        return true;
    }

    public boolean keyDown(int keycode) {
        _robot.keyPress(keycode);
        return true;
    }

    public boolean keyUp(int keycode) {
        _robot.keyRelease(keycode);
        return true;
    }
}
