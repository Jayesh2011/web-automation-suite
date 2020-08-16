package com.zeuslearning.automation.interactions;

public interface IAlertOperations {

    /**
     * Dismiss the alerts / Click the `Cancel` button.
     *
     * @return {boolean} - returns `true` if dismiss operation is successful.
     */
    boolean dismissAlert();

    /**
     * Accept the alerts / Click the `Yes` or `OK` button.
     *
     * @return {boolean} - returns `true` if accept operation is successful.
     */
    public boolean acceptAlert();

    /**
     * Return text string on the alert popup.
     *
     * @return {String} - returns text string on the alert popup.
     */
    public String getAlertText();

    /**
     * Send a prompt text to the alert popup.
     *
     * @return {boolean} - returns `true` if prompt operation is successful.
     */
    public boolean sendPromptText(String text);

    /**
     * Accept the alerts / Click the `Yes` or `OK` button.
     *
     * @return {Object} - returns an `Alert` object if alert is found, else
     *         returns a `Null` object.
     */
    public Object switchToAlert();

}
