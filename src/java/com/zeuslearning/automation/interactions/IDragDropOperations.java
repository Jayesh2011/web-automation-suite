package com.zeuslearning.automation.interactions;

public interface IDragDropOperations {

	/**
	 * Drag an Element from `source` to `target`.
	 *
	 * @param source
	 *            {Object} - Drag an Element present at the `source`.
	 * @param target
	 *            {Object} - Drag the Element to the `target`.
	 *
	 * @return {boolean} - returns `true` if drag operation is successful.
	 */
	public boolean dragDrop(Object source, Object target);

	/**
	 * Drag an Element from `source` to `target`. (Uses jquery-simulations,
	 * github url: https://github.com/j-ulrich/jquery-simulate-ext)
	 *
	 * @param source
	 *            {Object} - Drag an Element present at the `source`.
	 * @param target
	 *            {Object} - Drag the Element to the `target`.
	 *
	 * @return {boolean} - returns `true` if drag operation is successful.
	 */
	public boolean dragDropUsingCode(Object sourceLocator, Object targetLocator);

	/**
	 * Drag an Element from `source` to `target`. (Uses jquery-simulations,
	 * github url: https://github.com/j-ulrich/jquery-simulate-ext)
	 *
	 * @param source
	 *            {Object} - Drag an Element present at the `source`.
	 * @param target
	 *            {Object} - Drag the Element to the `target`.
	 * @param stepWidth
	 *            {Object} - divide the drag drop action into no of steps.
	 * @param stepDelay
	 *            {Object} - delay among steps.
	 *
	 * @return {boolean} - returns `true` if drag operation is successful.
	 */
	public boolean dragDropUsingCode(Object sourceLocator, Object targetLocator, int stepWidth, int stepDelay);

	/**
	 * Move an Element at `source` by `xOffset` along the X-Axis and by
	 * `yOffset` along the Y-Axis.
	 *
	 * @param source
	 *            {Object} - Drag an Element present at the `source`.
	 * @param xOffset
	 *            {int} - Offset along the X-Axis.
	 * @param yOffset
	 *            {int} - Offset along the Y-Axis.
	 *
	 * @return {boolean} - returns `true` if drag operation is successful.
	 */
	public boolean dragDrop(Object source, int xOffset, int yOffset);

	/**
	 * Move an Element at `source` by `xOffset` along the X-Axis and by
	 * `yOffset` along the Y-Axis. (Uses jquery-simulations, github url:
	 * https://github.com/j-ulrich/jquery-simulate-ext)
	 *
	 * @param source
	 *            {Object} - Drag an Element present at the `source`.
	 * @param xOffset
	 *            {int} - Offset along the X-Axis.
	 * @param yOffset
	 *            {int} - Offset along the Y-Axis.
	 * @param stepWidth
	 *            {Object} - divide the drag drop action into no of steps.
	 * @param stepDelay
	 *            {Object} - delay among steps.
	 *
	 * @return {boolean} - returns `true` if drag operation is successful.
	 */
	public boolean dragDropUsingCode(Object sourceLocator, int xOffset, int yOffset, int stepWidth, int stepDelay);

	/**
	 * Drag inside an element from one position`xOffset` along the X-Axis and by
	 * `yOffset` along the Y-Axis and move by `dx` along the X-Axis and by `dy`
	 * along the Y-Axis and drop.
	 * 
	 * @param locator
	 *            {Object} - Drag inside an Element present at the `locator`.
	 * @param xOffset
	 *            {int} - start position along the X-Axis.
	 * @param yOffset
	 *            {int} - end position along the Y-Axis.
	 * @param dx
	 *            {int} - pixels to move along the X-Axis.
	 * @param dy
	 *            {int} - pixels to move along the Y-Axis.
	 * @param stepWidth
	 *            {Object} - divide the drag drop action into no of steps.
	 * @param stepDelay
	 *            {Object} - delay among steps.
	 * @return
	 */
	public boolean dragDropUsingPostionsInsideElement(Object locator, int xOffset, int yOffset, int dx, int dy,
			int stepWidth, int stepDelay);
}
