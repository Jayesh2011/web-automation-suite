package com.zeuslearning.automation.interactions;

public interface IVerifications {

    /**
     * Checks if str1 contains str2.
     *
     * @param str1
     *            {String}
     * @param str2
     *            {String}
     * @return {boolean} Return true if str1 contains str2 and str1.length >
     *         str2.length, else return false.
     */
    public boolean contains(String str1, String str2);

    /**
     * Compare 2 strings (case-sensitive)
     *
     * <p>
     * Compares two strings lexicographically. The comparison is based on the
     * Unicode value of each character in the strings.
     * </p>
     *
     * @param string1
     *            {String}
     * @param string2
     *            {String}
     * @return {int} - case-sensitive comparison. returns positive integer, if
     *         `string1` lexicographically precedes `string2`. returns negative
     *         integer, if `string1` lexicographically follows `string2`.
     *         returns 0, if both the strings are identical. returns the
     *         difference between the lengths of `string1` and `string2`
     *         (`string1` - `string2`), If there is no index position at which
     *         they differ.
     */
    public int compareStrings(String string1, String string2);

    /**
     * Compare 2 strings (case-insensitive)
     *
     * <p>
     * Compares two strings lexicographically. The comparison is based on the
     * Unicode value of each character in the strings and on the length of the
     * strings.
     * </p>
     *
     * @param string1
     *            {String}
     * @param string2
     *            {String}
     * @return {int} - case-insensitive comparison. returns positive integer, if
     *         `string1` lexicographically precedes `string2`. returns negative
     *         integer, if `string1` lexicographically follows `string2`.
     *         returns 0, if both the strings are identical. returns the
     *         difference between the lengths of `string1` and `string2`
     *         (`string1` - `string2`), if there is no index position at which
     *         they differ.
     */
    public int compareStringsIgnoreCase(String string1, String string2);

    /**
     * Verify if alert pop-up is present.
     *
     * @return {boolean} - Returns `true` if alert is present, else returns
     *         `false`.
     */
    public boolean isAlertPresent();

    /**
     * Check if the element is displayed.
     *
     * @param element
     *            {Object} - Path to the element.
     * @return {boolean} - Returns `true` if element is displayed, else returns
     *         `false`.
     */
    public boolean isElementDisplayed(Object element);

    /**
     * Check if the element is displayed.
     *
     * @param element
     *            {Object} - Path to the element. *
     * @param timeout
     *            {int} - Wait time in seconds, before the check is performed.
     * @return {boolean} - Returns `true` if element is displayed, else returns
     *         `false`.
     */
    public boolean isElementDisplayed(Object element, int timeout);

    /**
     * Checks if element is displayed on the page or not.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {Boolean} - Return `true` if the element is not displayed else
     *         return false.
     */
    public boolean isElementNotDisplayed(Object locator);

    /**
     * Checks if element is displayed on the page or not.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param timeout
     *            {int} - Wait time in seconds, before the check is performed.
     * @return {Boolean} - Return `true` if the element is not displayed else
     *         return false.
     */
    public boolean isElementNotDisplayed(Object element, int timeout);

    /**
     * Checks if element is present on the DOM or not.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {Boolean} - Return `true` if the element is present on the DOM
     *         else return false.
     */
    public boolean isElementPresent(Object locator);

    /**
     * Checks if element is present on the DOM or not.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param timeout
     *            {int} - Wait time in seconds, before the check is performed.
     * @return {Boolean} - Return `true` if the element is present on the DOM
     *         else return false.
     */
    public boolean isElementPresent(Object locator, int timeout);

    /**
     * Check if the element is enabled.
     *
     * @param element
     *            {Object} - Path to the element.
     * @return {boolean} - Returns `true` if the element is enabled, else
     *         returns `false`.
     */
    public boolean isEnabled(Object element);

    /**
     * Verify if strings are equal (case-sensitive check).
     *
     * @param actualString
     *            {String}
     * @param expectedRegex
     *            {String} - Regular expression
     * @return {boolean} - Returns `true` if strings are equal (case-sensitive),
     *         else returns `false`.
     */
    public boolean isRegexMatch(String actualString, String expectedRegex);

    /**
     * Verify if strings are equal (case-sensitive check).
     *
     * @param actualString
     *            {String}
     * @param expectedRegex
     *            {String} - Regular expression
     * @return {boolean} - Returns `true` if strings are equal (case-sensitive),
     *         else returns `false`.
     */
    public boolean isRegexMatchIgnoreCase(String actualString, String expectedRegex);

    /**
     * Check if the element is in the selected state.
     *
     * @param element
     *            {Object} - Path to the element.
     * @return {boolean} - Returns `true` if element is in the selected state,
     *         else returns `false`.
     */
    public boolean isSelected(Object element);

    /**
     * Verify if strings are equal (case-sensitive check).
     *
     * @param actual
     *            {String}
     * @param expected
     *            {String}
     * @return {boolean} - Returns `true` if strings are equal (case-sensitive),
     *         else returns `false`.
     */
    public boolean isStringMatch(String actual, String expected);

    /**
     * Verify if strings are equal (case-insensitive check).
     *
     * @param actual
     *            {String}
     * @param expected
     *            {String}
     * @return {boolean} - Returns `true` if strings are equal
     *         (case-insensitive), else returns `false`.
     */
    public boolean isStringMatchIgnoreCase(String actual, String expected);

    /**
     * Check if the title of the activity is equal to the string passed.
     *
     * @param expectedTitle
     *            {String}
     * @return {boolean} - Returns `true` if titles match, else returns `false`.
     */
    public boolean isTitleEqualTo(String expectedTitle);

    /**
     * Verify if text is present on the page.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param partOfExpectedString
     *            {String}
     * @return {boolean} - Return `true` if expected string present on the page,
     *         else return `false`.
     */
    public boolean verifySubstringOnPage(Object locator, String partOfExpectedString);

    /**
     * Verify if text is present on the page.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param expected
     *            {String}
     * @return {boolean} - Return `true` if expected string present on the page,
     *         else return `false`.
     */
    public boolean verifyTextOnPage(Object locator, String expected);
}
