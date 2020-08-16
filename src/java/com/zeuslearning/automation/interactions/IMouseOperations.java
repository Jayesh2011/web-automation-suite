package com.zeuslearning.automation.interactions;

public interface IMouseOperations {

    /**
     * Performs a click operation on the element.
     *
     * @param locator
     *            {Object} - Path to the element to be clicked.
     *
     * @return {boolean} - returns `true` if click operation is successful.
     */
    boolean click(Object locator);
    
    /**
     * Performs a click using simulate library.
     * 
     * @param locator 
     * 			  {Object} - Path to the element to be clicked.	
     */
    public void simulateClick(Object locator);
    
    /**
     * Performs a click operation on the element.
     * 
     * @param locator
     *            {Object} - Path to the element.
     *@param x
     *            {int} - Offset along the X-Axis w.r.t. element.
     * @param y
     *            {int} - Offset along the Y-Axis w.r.t. element.           
     * @param ctrlKey
     *            {boolean} - Press control key if `true`
     * @param altKey
     *            {boolean} - Press alt key if `true`
     * @param shiftKey
     *            {boolean} - Press shift key if `true`
     * @param metaKey
     *            {boolean} - Press meta key if `true`
     * @return {boolean} - returns `true` if click operation is successful.
     */

    boolean clickUsingOffsetWithKeys(Object locator, int x, int y, boolean ctrlKey, boolean altKey, boolean shiftKey,
            boolean metaKey);

    /**
     * Performs a click operation on the element.
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @param ctrlKey
     *            {boolean} - Press control key if `true`
     * @param altKey
     *            {boolean} - Press alt key if `true`
     * @param shiftKey
     *            {boolean} - Press shift key if `true`
     * @param metaKey
     *            {boolean} - Press meta key if `true`
     * @return {boolean} - returns `true` if click operation is successful.
     */
    boolean click(Object locator, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey);

    /**
     * Performs a click operation on all the element located by the path specified
     * by `locator`.
     *
     * @param locator
     *            {Object} - Path to the element.
     *
     * @return {boolean} - returns `true` if click operation is successful.
     */
    boolean clickAllElementsLocatedBy(Object locator);

    /**
     * Performs a click on an element at x and y.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param x
     *            {int} - Offset along the X-Axis w.r.t. element.
     * @param y
     *            {int} - Offset along the Y-Axis w.r.t. element.
     *
     * @return {boolean} - returns `true` if operation is successful.
     */
    boolean clickUsingOffset(Object locator, int x, int y);

    /**
     * Performs a click on the specified element without scrolling it
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @return {boolean} - returns `true` if click operation is successful.
     */
    boolean clickWithoutScrolling(Object locator);

    /**
     * Performs a context click on the specified element
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @return {boolean} - returns `true` if context click operation is successful.
     */
    boolean contextClick(Object locator);

    boolean contextClickUsingCode(Object locator);

    /**
     * Performs a context click on an element at x and y.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param x
     *            {int} - Offset along the X-Axis w.r.t. element.
     * @param y
     *            {int} - Offset along the Y-Axis w.r.t. element.
     *
     * @return {boolean} - returns `true` if operation is successful.
     */
    boolean contextClickUsingOffset(Object locator, int x, int y);

    /**
     * Performs a context click on the specified element without scrolling it
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @return {boolean} - returns `true` if context click operation is successful.
     */
    boolean contextClickWithoutScrolling(Object locator);

    /**
     * Performs a double-click operation on the element.
     *
     * @param locator
     *            {Object} - Path to the element to be double-clicked.
     *
     * @return {boolean} - returns `true` if double-click operation is successful.
     */
    boolean doubleClick(Object locator);

    /**
     * Performs a double-click operation on the element.
     *
     * @param locator
     *            {Object} - Path to the element to be double-clicked.
     *
     * @return {boolean} - returns `true` if double-click operation is successful.
     */
    boolean doubleClickUsingCode(Object locator);

    /**
     * Performs a double-click operation on the element.
     * 
     * @param locator
     *            {Object} - Path to the element to be double-clicked.
     * @return {boolean} - returns `true` if double-click operation is successful.
     */
    public boolean doubleClickUsingCodeWithTwoClicks(Object locator);

    /**
     * Performs a double click using mouse up and mouse down on an element at x and y.
     *
     * @param locator
     *            {Object} - Path to the element.
     * @param x
     *            {int} - Offset along the X-Axis w.r.t. element.
     * @param y
     *            {int} - Offset along the Y-Axis w.r.t. element.
     *
     * @return {boolean} - returns `true` if operation is successful.
     */
    boolean doubleClickUsingOffset(Object locator, int x, int y);
    
    /**
     * Performs a double click using simulate on an element at x and y.
     * @param locator
     *          {Object} - Path to the element.
     * @param x
     *          {int} - Offset along the X-Axis w.r.t. element.
     * @param y
     *          {int} - Offset along the Y-Axis w.r.t. element.
     * @return  {boolean} - returns `true` if operation is successful.
     */
    boolean simulateDoubleClickUsingOffset(Object locator, int x, int y);

    /**
     * Performs a double-click operation on the element without scrolling it.
     *
     * @param locator
     *            {Object} - Path to the element to be double-clicked.
     *
     * @return {boolean} - returns `true` if double-click operation is successful.
     */
    boolean doubleClickWithoutScrolling(Object locator);

    /**
     * Drag inside an element from one position`offsetX1` along the X-Axis and by
     * `offsetY1` along the Y-Axis and move to `offsetX2` along the X-Axis and to
     * `offsetY2` along the Y-Axis and drop.
     * 
     * @param locator
     *            {Object} - Drag inside an Element present at the `locator`.
     * @param offsetX1
     *            {int} - start position along the X-Axis.
     * @param offsetY1
     *            {int} - start position along the Y-Axis.
     * @param offsetX2
     *            {int} - end position along the X-Axis.
     * @param offsetY2
     *            {int} - end position along the Y-Axis.
     * @return {boolean} - returns `true` if drag operation is successful.
     */
    public boolean dragdropUsingMouse(Object locator, int offsetX1, int offsetY1, int offsetX2, int offsetY2);

    /**
     * Clicks on the element found using findElements.
     *
     * @param locator
     *            {Object} - Path to the elements.
     * @param index
     *            {int} - index number of the element to be clicked.
     *
     * @return {boolean} - returns `true` if click operation is successful.
     */
    boolean listClick(Object locator, int index);

    /**
     * Trigger mouse down event on the element.
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @return {boolean} - Returns `true` if mouse over was successful, else
     *         `false`.
     */
    boolean mouseDown(Object locator);

    /**
     * Trigger mouse down event on the element at `x` and `y` offset.
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @param xOffset
     *            {int}
     * @param yOffset
     *            {int}
     * @return {boolean} - Returns `true` if mouse over was successful, else
     *         `false`.
     */
    boolean mouseDown(Object locator, int xOffset, int yOffset);

    /**
     * Trigger mouse move event.
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @param xOffset
     *            {int}
     * @param yOffset
     *            {int}
     * @return {boolean} - Returns `true` if mouse move is successful, else `false`.
     */
    boolean mouseMove(Object locator, int xOffset, int yOffset);

    /**
     * Performs a mouse-over operation on the element.
     *
     * @param locator
     *            {Object} - Path to the element.
     *
     * @return {Object} - Returns a Mouse pointer positioning object. Returns
     *         `false` if mouse-over operation is not successful.
     */
    Object mouseOver(Object locator);

    /**
     * Trigger mouse up event.
     * 
     * @param locator
     *            {Object} - Path to the element
     * @return {boolean} - Returns `true` if mouse up is successful, else false.
     */
    boolean mouseUp(Object locator);

    /**
     * Trigger mouse up event on the element at `x` and `y` client positions.
     * 
     * @param locator
     *            {Object} - Path to the element.
     * @param clientX
     *            {int}
     * @param clientY
     *            {int}
     * @return {boolean} - Returns `true` if mouse over was successful, else
     *         `false`.
     */
    boolean mouseUp(Object locator, int clientX, int clientY);

    /**
     * Scrolls the page by an offset.
     *
     * @param xVal
     *            {int} - Offset along the X-Axis.
     * @param yVal
     *            {int} - Offset along the Y-Axis.
     *
     * @return {boolean} - returns `true` if scroll operation is successful.
     */
    boolean scroll(int xVal, int yVal);

    /**
     * Scrolls the page such that the element is now in the view port.
     *
     * @param locator
     *            {Object} - Path to the element.
     *
     * @return {boolean} - returns `true` if scroll operation is successful.
     */
    boolean scroll(Object locator);

    /**
     * Performs n number of consecutive click operations on the element.
     *
     * @param locator
     *            {Object} - Path to the element to be clicked.
     *
     * @return {boolean} - returns `true` if click operation is successful.
     */
    boolean clickNtimes(Object locator, int times);

    /**
     * Performs n number of consecutive click operations on the element.
     *
     * @param locator
     *            {Object} - Path to the element to be clicked.
     * @param x
     *            {int} - Offset along the X-Axis w.r.t. element.
     * @param y
     *            {int} - Offset along the Y-Axis w.r.t. element.
     * @return {boolean} - returns `true` if click operation is successful.
     */
    boolean clickNtimes(Object locator, int times, int xOffset, int yOffset);
    
    /**
     * Performs click after scrolling. Specifically made for IE scroll issue.
     * 
     * @param locator
     *          {Object} - Path to the element to be clicked.
     * @return
     *          {boolean} - returns 'true' if click operation is successful.
     */
    boolean clickWithScroll(Object locator);
}
