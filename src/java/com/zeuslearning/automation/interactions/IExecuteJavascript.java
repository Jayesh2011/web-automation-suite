package com.zeuslearning.automation.interactions;

public interface IExecuteJavascript {

    /**
     * Executes JavaScript in the context of the currently selected frame or window. The script fragment provided will be executed as the body of an anonymous
     * function.
     * Within the script, use document to refer to the current document. Note that local variables will not be available once the script has finished executing,
     * though global variables will persist.
     * 
     * @param {String}
     *            script - Javascript to be executed
     * @param {Object
     *            List} args
     * @return {Object}
     *         For an HTML element, this method returns a WebElement
     *         For a number, a Long is returned
     *         For a boolean, a Boolean is returned
     *         For all other cases, a String is returned.
     *         For an array, return a List<Object> with each object following the rules above. We support nested lists.
     *         Unless the value is null or there is no return value, in which null is returned
     */
    public Object executeScript(String script, Object... args);

    /**
     * Execute an asynchronous piece of JavaScript in the context of the currently selected frame or window. Unlike executing synchronous JavaScript, scripts
     * executed with this method must explicitly signal they are finished by invoking the provided callback. This callback is always injected into the executed
     * function as the last argument.
     *
     * @param {Object
     *            List} args
     * @return {Object}
     *         For an HTML element, this method returns a WebElement
     *         For a number, a Long is returned
     *         For a boolean, a Boolean is returned
     *         For all other cases, a String is returned.
     *         For an array, return a List<Object> with each object following the rules above. We support nested lists.
     *         Unless the value is null or there is no return value, in which null is returned
     */
    public Object executeAsyncScript(String script, Object... args);
}
