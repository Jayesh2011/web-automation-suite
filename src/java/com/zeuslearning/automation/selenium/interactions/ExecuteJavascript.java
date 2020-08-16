package com.zeuslearning.automation.selenium.interactions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.zeuslearning.automation.interactions.IExecuteJavascript;

public class ExecuteJavascript implements IExecuteJavascript {

    private static JavascriptExecutor _js;
    private WebDriver _driver;

    public ExecuteJavascript(Object driver) {
        _driver = (WebDriver) driver;
        _js = (JavascriptExecutor) _driver;
    }

    public Object executeScript(String script, Object... args) {
        return _js.executeScript(script, args);
    }

    public Object executeAsyncScript(String script, Object... args) {
        return _js.executeScript(script, args);
    }
}
