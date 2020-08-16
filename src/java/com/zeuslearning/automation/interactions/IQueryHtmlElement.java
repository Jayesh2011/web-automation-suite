package com.zeuslearning.automation.interactions;

import java.util.List;

public interface IQueryHtmlElement {

    /**
     * Find an element located by the specified path.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {Object} - Returns an element object located by `locator`.
     */
    Object findElement(Object locator);

    /**
     * Find all the elements located by the specified path.
     *
     * @param locator
     *            {Object} - Path to elements.
     * @return {List<Object>} - Returns a list of element objects located by
     *         `locator`.
     */
    List<Object> findElements(Object locator);

    /**
     * Find an element located by the specified path within another element.
     *
     * @param element
     *            {Object} - Parent Element.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {Object} - Returns an element object located by `locator`.
     */
    Object findElementWithinElement(Object element, Object locator);

    /**
     * Get the attribute `attributeName`.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param attributeName
     *            {String} - Attribute to be determined.
     *
     * @return {String} - Attribute attached to the element.
     */
    String getAttributes(Object locator, String attributeName);

    /**
     * Get the css property `propertyName`.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param propertyName
     *            {String} - Css property to be determined.
     *
     * @return {String} - Css property of the element.
     */
    String getCss(Object locator, String propertyName);

    /**
     * Get the title of the activity.
     *
     * @return {String} - Title of the activity.
     */
    public String getTitle();

    /**
     * Get the dimensions of the element.
     *
     * @param element
     *            {Object} - Path to the element.
     * @return {Object} - Returns the dimensions of the element.
     */
    public Object getDimensionsOfElementLocatedBy(Object element);

    /**
     * Get the height of the element.
     *
     * @param element
     *            {Object} - Path to the element.
     * @return {Object} - Returns the height of the element.
     */
    public Object getHeightOfElement(Object locator);

    /**
     * Get the width of the element.
     *
     * @param element
     *            {Object} - Path to the element.
     * @return {Object} - Returns the width of the element.
     */
    public Object getWidthOfElement(Object locator);

    /**
     * Get text present inside an element.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {String} - Returns element text.
     */
    public String getText(Object locator);

    /**
     * Get text present inside an element.
     * 
     * @param locator
     *            -
     *            {Object} - Path of elements.
     * @param index
     *            {int} - index of the element in list of elements found by the locator.
     * 
     * @return {String} - Returns element text.
     */
    public String getText(Object locator, int index);

    /**
     * Returns current page URL.
     *
     * @return {String} Current page URL.
     */
    public String getUrl();

    /**
     * Returns the coordinates of the top left-hand corner of the rendered element
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {Object} - Returns a point, containing the location of the top left-hand corner of the element
     */
    public Object getCoordinates(Object locator);

    /**
     * Returns the X coordinate of the top left-hand corner of the rendered element
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {Object} - Returns X coordinate value of the top left-hand corner of the element
     */

    public Object getXCoordinates(Object locator);

    /**
     * Returns the Y coordinate of the top left-hand corner of the rendered element
     *
     * @param locator
     *            {Object} - Path to the element.
     * @return {Object} - Returns Y coordinate value of the top left-hand corner of the element
     */

    public Object getYCoordinates(Object locator);

    /**
     * Returns browser name.
     * 
     * @return {String} - Name of the browser
     */
    public String getBrowserName();

    /**
     * Remove element from Dom.
     *
     * @param locator
     *            {Object} - Path to elements.
     */
    public void removeDomElement(Object locator);
}
