package com.zeuslearning.automation.interactions;

public interface IDropDownOperations {

    /**
     * Select an option from the dropdown list by visible text.
     *
     * @param element
     *            {Object} - Dropdown list.
     * @param option
     *            {String} - One of the visible options.
     *
     * @return {boolean} - returns `true` if selection is successful.
     */
    public boolean selectOptionByText(Object element, String option);

    /**
     * Select an option from the dropdown list by index.
     *
     * @param element
     *            {Object} - Dropdown list.
     * @param index
     *            {int} - index value of the option to be selected.
     *
     * @return {boolean} - returns `true` if selection is successful.
     */
    public boolean selectOptionByIndex(Object element, int index);

    /**
     * Select an option from the dropdown list by value.
     *
     * @param element
     *            {Object} - Dropdown list.
     * @param value
     *            {String} - index value of the option to be selected.
     *
     * @return {boolean} - returns `true` if selection is successful.
     */
    public boolean selectOptionByValue(Object element, String value);

    /**
     * Handles dropdown list that expand on mouse roll over.
     *
     * <p>
     * Operation: Move the mouse pointer on a dropdown list ('element') that
     * expands on mouse roll-over and select an option (`option`).
     * </p>
     *
     * @param element
     *            {Object} - Path to dropdown list.
     * @param option
     *            {object} - Path to one of the visible options.
     *
     * @return {boolean} - returns `true` if selection is successful.
     */
    public boolean clickOnHoverMenuOption(Object element, Object option);

    /**
     * Deselects all the selected options of a multi-select element.
     *
     * @param element
     *            {Object} - Path to dropdown list.
     *
     * @return {boolean} - returns `true` if deselection is successful.
     */
    public boolean deselectAllOptions(Object locator);
}
