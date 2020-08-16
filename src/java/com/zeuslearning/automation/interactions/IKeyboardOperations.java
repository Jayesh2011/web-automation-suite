package com.zeuslearning.automation.interactions;

public interface IKeyboardOperations {

    /**
     * Clear the existing text and type into text fields.
     *
     * @param locator
     *            {Object} - Path to text field.
     * @param value
     *            [String] - Text string to be passed/typed.
     *
     * @return {boolean} - Returns `true` if typing is successful.
     */
    boolean typeAfterClear(Object locator, String value);

    /**
     * Type into text fields.
     *
     * @param locator
     *            {Object} - Path to text field.
     * @param value
     *            [String] - Text string to be passed/typed.
     *
     * @return {boolean} - Returns `true` if typing is successful.
     */
    boolean type(Object locator, String value);

    /**
     * Press multiple keys at once.
     *
     * @param locator
     *            {Object}: Path to the text field.
     * @param keys
     *            {String}: pass the keys to be pressed at once. For e.g.
     *            keys = "ctrl+A" will select all the text
     * 
     *            Control = ctrl
     *            Shift = shift
     *            Alt = alt
     * @return
     *         {Boolean} - Returns `true` if the keypress is successful.
     */
    boolean multipleKeyStrokes(Object locator, String keys);

    /**
     * Press multiple keys at once.
     *
     * @param locator
     *            {Object}: Path to the text field.
     * @param keys
     *            {String}: pass the keys to be pressed at once. For e.g.
     *            keys = "ctrl+A" will select all the text
     * 
     *            Control = ctrl
     *            Shift = shift
     *            Alt = alt
     * @param loopCount
     *            {Integer}: Number of times the keys are to be pressed.
     * 
     * @return
     *         {Boolean} - Returns `true` if the keypress is successful.
     */
    boolean multipleKeyStrokes(Object locator, String keys, int loopCount);
    
    /**
     * Clears the text field.
     *
     * @param locator
     *            {Object}: Path to the text field.
     * @return {boolean} - Returns `true` if clearing is successful.
     */
    public boolean clearTextField(Object locator);
    
    /**
     * Key down event
     * 
     * @param keycode
     *            {int}: pass the keycode of the key to be pressed. Key code can be found by using the java class `java.awt.event.KeyEvent` For e.g.
     *            Control = KeyEvent.VK_CONTROL
     *            Shift = KeyEvent.VK_SHIFT
     *            Alt = KeyEvent.VK_ALT
     *            and so on.
     * @return
     *         {Boolean} - Returns `true` if the keydown is successful.
     */
    boolean keyDown(int keycode);

    /**
     * Key down event
     * 
     * @param keycode
     *            {int}: pass the keycode of the key to be released. Key code can be found by using the java class `java.awt.event.KeyEvent` For e.g.
     *            Control = KeyEvent.VK_CONTROL
     *            Shift = KeyEvent.VK_SHIFT
     *            Alt = KeyEvent.VK_ALT
     *            and so on.
     * @return
     *         {Boolean} - Returns `true` if the keyup is successful.
     */
    boolean keyUp(int keycode);
}
