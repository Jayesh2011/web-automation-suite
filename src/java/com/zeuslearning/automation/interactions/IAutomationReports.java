package com.zeuslearning.automation.interactions;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface IAutomationReports {

    /**
     * Verifies the layout of the page (Uses Galen Framework)
     * 
     * @param layoutDesc
     *            {String} - Description for the layout verification.
     * 
     * @param specPath
     *            {String} - Path to UI specifications
     * 
     * @param includedTags
     *            {List} - A list of tags that should be included in specifications.
     * 
     * @return {String} - Returns a list of UI errors in a String format.
     */
    public String verifyPageLayout(String layoutTitle, String specPath, List<String> includedTags);

    /**
     * Verifies the layout of the page (Uses Galen Framework)
     * 
     * @param layoutDesc
     *            {String} - Description for the layout verification.
     * 
     * @param specPath
     *            {String} - Path to UI specifications
     * 
     * @param includedTags
     *            {List} - A list of tags that should be included in specifications.
     * 
     * @param vars
     *            {Map<String, Object>} - JavaScript variables that will be available in special galen spec expressions
     * 
     * @return {String} - Returns a list of UI errors in a String format.
     */
    public String verifyPageLayout(String layoutTitle, String specPath, List<String> includedTags, Map<String, Object> vars);

    /**
     * Verifies the layout of the page (Uses Galen Framework)
     * 
     * @param layoutDesc
     *            {String} - Description for the layout verification.
     * 
     * @param specPath
     *            {String} - Path to UI specifications
     * 
     * @param sectionFilter
     *            {Object} - A filter that is used for "@on" filtering in specification files.
     * 
     * @param properties
     *            {Properties} - A set of properties that will be accessible in special galen spec expressions.
     * 
     * @param vars
     *            {Map<String, Object>} - JavaScript variables that will be available in special galen spec expressions
     * 
     * @return {String} - Returns a list of UI errors in a String format.
     */
    public String verifyPageLayout(String layoutDesc, String specPath, Object sectionFilter, Properties properties, Map<String, Object> vars);

    /**
     * Generates UI verification reports in HTML format
     * 
     * @param relativePathToHtmlReportFolder
     *            {String} - Path to Html report folder
     */
    public void generateHtmlReport(String relativePathToHtmlReportFolder);

    /**
     * Generates UI verification reports in HTML format
     * 
     * @param relativePathToHtmlReportFolder
     *            {String} - Path to Html report folder
     * @param doesReportExist
     *            {Boolean} - If `true` add tests to existing reports, else generate a new HtmlReport
     */
    public void generateHtmlReport(String relativePathToHtmlReportFolder, boolean doesReportExist);

    /**
     * Initializes the TestReport instance with the name of current test method and stores it in {@link ThreadLocal}
     * 
     * @param method
     *            {java.lang.reflect.Method}
     * @param arguments
     */
    public void initReport(Method method, Object[] arguments);

    /**
     * Re-initialize the list of Galen tests.
     */
    public void resetTests();

    /**
     * Append test info to the HTML report.
     */
    public void appendReport();

    /**
     * Update test name that is displayed on the HTML report.
     * 
     * @param method
     *            {java.lang.reflect.Method}
     */
    public void updateTestInfo(Method method);

    /**
     * Update test name that is displayed on the HTML report.
     * 
     * @param method
     *            {String}
     */
    public void updateTestInfo(String method);

    /**
     * Log information messages to the HTML report.
     * 
     * @param details
     *            {String}
     * @param files
     *            {File} - List of files to be attached in the log.
     */
    public void logInfo(String details, File... files);

    /**
     * Log warnings to the HTML report.
     * 
     * @param details
     *            {String}
     * @param files
     *            {File} - List of files to be attached in the log.
     */
    public void logWarn(String details, File... files);

    /**
     * Log error to the HTML report.
     * 
     * @param details
     *            {String}
     * @param files
     *            {File} - List of files to be attached in the log.
     */
    public void logError(String details, File... files);

    /**
     * Split the HTML reports into multiple section for better readability
     * 
     * @param sectionName
     *            {String} - Name for that section
     */
    public void startSection(String sectionName);

    /**
     * End HTML report section.
     */
    public void endSection();
}
