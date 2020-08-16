package com.zeuslearning.automation.selenium.interactions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.reports.nodes.TestReportNode;
import com.galenframework.speclang2.pagespec.SectionFilter;
import com.galenframework.support.LayoutValidationException;
import com.galenframework.testng.GalenTestNgTestBase;
import com.galenframework.tests.GalenEmptyTest;
import com.zeuslearning.automation.interactions.IAutomationReports;
import com.zeuslearning.automation.io.CompressFolders;
import com.zeuslearning.automation.io.ScreenCapture;
import com.zeuslearning.automation.io.TextFileOps;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

public class AutomationReports extends GalenTestNgTestBase implements IAutomationReports {
    public LinkedList<GalenTestInfo> tests;
    GalenTestInfo test;
    WebDriver _driver;
    CompressFolders zip;
    ScreenCapture screenCapture;
    String title;
    boolean isNew = false;
    TextFileOps textFileOps;

    public AutomationReports(Object driver) {
        _driver = (WebDriver) driver;
        tests = new LinkedList<GalenTestInfo>();
        zip = new CompressFolders();
        screenCapture = new ScreenCapture(_driver);
        textFileOps = new TextFileOps(_driver);
    }

    public String verifyPageLayout(String layoutDesc, String specPath, List<String> includedTags) {
        title = layoutDesc;
        return verifyPageLayout(layoutDesc, specPath, new SectionFilter(includedTags, Collections.<String>emptyList()), new Properties(), null);
    }

    public String verifyPageLayout(String layoutDesc, String specPath, List<String> includedTags, Map<String, Object> vars) {
        title = layoutDesc;
        return verifyPageLayout(layoutDesc, specPath, new SectionFilter(includedTags, Collections.<String>emptyList()), new Properties(), vars);
    }

    public String verifyPageLayout(String layoutDesc, String specPath, Object sectionFilter, Properties properties, Map<String, Object> vars) {
        title = layoutDesc;

        String errors = "";

        try {
            checkLayout(specPath, (SectionFilter) sectionFilter, properties, vars);
        } catch (LayoutValidationException e) {
            System.out.println(e.getMessage());
            errors = e.getMessage();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return errors;
    }

    @Override
    public WebDriver createDriver(Object[] args) {
        return _driver;
    }

    @Override
    public void checkLayout(String specPath, SectionFilter sectionFilter, Properties properties, Map<String, Object> vars) throws IOException {
        LayoutReport layoutReport = Galen.checkLayout(_driver, specPath, sectionFilter, properties, vars);
        test.getReport().layout(layoutReport, title);

        if (layoutReport.errors() > 0) {
            throw new LayoutValidationException(specPath, layoutReport, sectionFilter);
        }
    }

    public void logInfo(String details, File... files) {

        TestReportNode node = test.getReport().info(details);
        for (File file : files) {
            node.withAttachment(file.getName(), file);
        }
    }

    public void logWarn(String details, File... files) {
        TestReportNode node = test.getReport().warn(details).withAttachment("viewport.png", ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE));
        for (File file : files) {
            node.withAttachment(file.getName(), file);
        }
    }

    public void logError(String details, File... files) {
        TestReportNode node = test.getReport().error(details).withAttachment("viewport.png", ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE));
        for (File file : files) {
            node.withAttachment(file.getName(), file);
        }
    }

    public void startSection(String sectionName) {
        test.getReport().sectionStart(sectionName);
    }

    public void endSection() {
        test.getReport().sectionEnd();
    }

    public void appendReport() {
        if (!"".equals(test.getName())) {
            if (isNew) {
                startSection("Path to Expected Images: " + System.getProperty("user.dir") + "\\images\\");
                endSection();
                isNew = false;
            }
            test.setEndedAt(new Date());
            tests.add(test);
        }
        test = new GalenTestInfo("", new GalenEmptyTest("", Arrays.asList("")));
    }

    public void updateTestInfo(String testName) {
        if (!(test == null || "".equals(test.getName()))) {
            appendReport();
        }
        int totalNumberOftests = tests.size();

        if (totalNumberOftests == 0) {
            test = GalenTestInfo.fromString(testName);
            test.setStartedAt(new Date());
            isNew = true;
        } else {
            boolean isTestPresent = false;
            for (int i = 0; i < totalNumberOftests; i++) {
                GalenTestInfo addedTest = tests.get(i);
                if (testName.equals(addedTest.getName())) {
                    isTestPresent = true;
                    test = addedTest;
                    break;
                }
            }
            if (!isTestPresent) {
                test = GalenTestInfo.fromString(testName);
                test.setStartedAt(new Date());
                isNew = true;
            }
        }
    }

    public void updateTestInfo(Method method) {
        if (!(test == null || "".equals(test.getName()))) {
            appendReport();
        }
        test = GalenTestInfo.fromMethod(method);
        test.setStartedAt(new Date());
    }

    public void generateHtmlReport(String relativePathToHtmlReportFolder) {
        appendReport();

        String currentDirectory = System.getProperty("user.dir").replaceAll("\\\\", "/");
        relativePathToHtmlReportFolder = relativePathToHtmlReportFolder.replaceAll("\\\\", "/");

        if (!relativePathToHtmlReportFolder.startsWith("/")) {
            relativePathToHtmlReportFolder = "/" + relativePathToHtmlReportFolder;
        }

        String pathToDirectory = currentDirectory + relativePathToHtmlReportFolder;
        File folderOnLocal = new File(pathToDirectory);
        if (!folderOnLocal.exists()) {
            folderOnLocal.mkdirs();
        }
        logJsErrors(pathToDirectory + "/jserrors");

        try {
            new HtmlReportBuilder().build(tests, pathToDirectory);
        } catch (IOException e) {}

        String relativePathToZip = "";
        String pathToZip = "\\\\fileserver\\automation-reports";
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("linux")) {
            pathToZip = "/home/kenil-fadia/share/automation-reports";
        } else if (osName.contains("windows") || osName.contains("mac")) {
            pathToZip = "\\\\fileserver\\automation-reports";
        }

        if (pathToDirectory.toLowerCase().contains("git")) {
            relativePathToZip = pathToDirectory.split("(git)|(GIT)|(Git)")[1];
        } else {
            relativePathToZip = new File(pathToDirectory).getName();
        }

        String[] folderList = relativePathToZip.split("/");

        for (int i = 0; i < folderList.length; i++) {
            if (!"".equals(folderList[i])) {
                pathToZip += "\\" + folderList[i];
            }
        }

        pathToZip = pathToZip.replaceAll("\\\\", "/");
        pathToDirectory = pathToDirectory.replaceAll("\\\\", "/");
        File fileOnServer = new File(pathToZip);
        if (!fileOnServer.exists()) {
            fileOnServer.mkdirs();
        }

        try {
            new HtmlReportBuilder().build(tests, pathToZip);
        } catch (IOException e) {}
    }

    public void generateHtmlReport(String relativePathToHtmlReportFolder, boolean doesReportExist) {
        if (doesReportExist) {
            tests.add(test);
            test = new GalenTestInfo("", new GalenEmptyTest("", Arrays.asList("")));
        }
        generateHtmlReport(relativePathToHtmlReportFolder);
    }

    public void resetTests() {
        tests = new LinkedList<GalenTestInfo>();
    }

    public void logJsErrors(String filePath) {
        String browser = ((RemoteWebDriver) _driver).getCapabilities().getBrowserName();
        String[][] errors = null;
        int logLength = 0;
        StringBuilder sb = new StringBuilder();
        if (browser.equalsIgnoreCase("firefox")) {
            List<JavaScriptError> Errors = JavaScriptError.readErrors(_driver);
            logLength = Errors.size();
            if (logLength > 0) {
                errors = new String[logLength][5];
                // Print Javascript Errors one by one from array.
                for (int i = 0; i < logLength; i++) {
                    JavaScriptError error = Errors.get(i);
                    errors[i][0] = error.getErrorMessage();
                    sb.append(errors[i][0]).append("\n");
                    errors[i][1] = Integer.toString(error.getLineNumber());
                    sb.append(errors[i][1]).append("\n");
                    errors[i][2] = error.getSourceName();
                    sb.append(errors[i][2]).append("\n");
                    errors[i][3] = error.getStack();
                    sb.append(errors[i][3]).append("\n");
                    errors[i][4] = error.getErrorCategory();
                    sb.append(errors[i][4]).append("\n\n\n");
                }
            }
        } else if (browser.equalsIgnoreCase("chrome")) {
            LogEntries logEntries = _driver.manage().logs().get(LogType.BROWSER);
            List<LogEntry> logs = logEntries.getAll();
            logLength = logs.size();
            if (logLength > 0) {
                errors = new String[logLength][3];
                for (int i = 0; i < logLength; i++) {
                    LogEntry log = logs.get(i);
                    errors[i][0] = log.getLevel().toString();
                    sb.append(errors[i][0]).append("\n");
                    errors[i][1] = log.getMessage();
                    sb.append(errors[i][1]).append("\n");
                    errors[i][2] = Long.toString(log.getTimestamp());
                    sb.append(errors[i][2]).append("\n\n\n");
                }
            }
        }
        if (errors != null && errors.length > 0) {
            textFileOps.write(new File(filePath + ".txt"), sb.toString());
            updateTestInfo("Browser Console Logs / JS errors - " + browser);
            test.getReport().error("Please find the Console Logs / Javascript Errors in the attached files").withAttachment(new File(filePath).getName(),
                            new File(filePath + ".txt"));
            appendReport();
        }
    }
}
