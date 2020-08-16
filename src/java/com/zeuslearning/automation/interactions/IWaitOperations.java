package com.zeuslearning.automation.interactions;

public interface IWaitOperations {

    /**
     * Wait until the element located by the locator is visible (Max. timeout =
     * default timeout).
     *
     * @param element
     *            {Object} - Path to the element.
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForVisibilityOf(Object, int)
     */
    boolean waitForVisibilityOf(Object element);

    /**
     * Wait until the element located by the locator is visible (Max. timeout =
     * timeout {param}).
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param timeout
     *            {int} - Maximum timeout in seconds.
     *
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForVisibilityOf(Object)
     */
    boolean waitForVisibilityOf(Object locator, int timeout);

    /**
     * Wait until the element is visible (Max. timeout = default timeout).
     *
     * @param element
     *            {Object} - Path to the element.
     *
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForInvisibilityOf(Object, int)
     */
    boolean waitForInvisibilityOf(Object element);

    /**
     * Wait until the element is visible (Max. timeout = timeout {param}).
     *
     * @param element
     *            {Object} - Path to the element.
     * @param timeout
     *            {int} - Maximum timeout in seconds.
     *
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForInvisibilityOf(Object)
     */
    boolean waitForInvisibilityOf(Object element, int timeout);

    /**
     * Wait until the element is not clickable (Max. timeout = default timeout).
     *
     * @param element
     *            {Object} - Path to the element.
     *
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForElementToBeClickable(Object, int)
     */
    boolean waitForElementToBeClickable(Object element);

    /**
     * Wait until the element is not clickable (Max. timeout = timeout {param}).
     *
     * @param element
     *            {Object} - Path to the element.
     * @param timeout
     *            {int} - Maximum timeout in seconds.
     *
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForElementToBeClickable(Object)
     */
    boolean waitForElementToBeClickable(Object element, int timeout);

    /**
     * Wait until the element is not present on the DOM (Max. timeout = default
     * timeout).
     *
     * @param element
     *            {Object} - Path to the element.
     *
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForElementToBePresent(Object, int)
     */
    boolean waitForElementToBePresent(Object element);

    /**
     * Wait until the element is not present on the DOM (Max. timeout = timeout
     * {param}).
     *
     * @param element
     *            {Object} - Path to the element.
     * @param timeout
     *            {int} - Maximum timeout in seconds.
     *
     * @return {boolean} - return `true` once timed out.
     *
     * @see #waitForElementToBePresent(Object)
     */
    boolean waitForElementToBePresent(Object element, int timeout);

    /**
     * Wait until a new window is available.
     *
     * @param numberOfWindows
     *            {int} - Number of windows
     * @return {boolean} - Returns `true` once timed out.
     */
    boolean waitForNoOfWindows(int numberOfWindows);

    boolean waitForScriptValueMatch(String str, String script, IExecuteJavascript jsExec, int timeout);
    
    boolean waitForScriptValueChange(String str, String script, IExecuteJavascript jsExec, int timeout);
}
