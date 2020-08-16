package com.zeuslearning.automation.unittests;

import java.util.Scanner;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IQueryHtmlElement;
import com.zeuslearning.automation.interactions.IVerifications;
import com.zeuslearning.automation.interactions.IWaitOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.io.ScreenCapture;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.Mouse;
import com.zeuslearning.automation.selenium.interactions.QueryHtml;
import com.zeuslearning.automation.selenium.interactions.Timeout;
import com.zeuslearning.automation.selenium.interactions.Verifications;

public class ObserveModeMultiTask {

    IDriver iDriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
    WebDriver driver = (WebDriver) iDriver.getDriver("");
    IWindowOperations browser = new BrowserWindow(driver);
    IMouseOperations mouseHandle = new Mouse(driver);
    IQueryHtmlElement queryHtml = new QueryHtml(driver);
    IVerifications verify = new Verifications(driver);
    IWaitOperations wait = new Timeout(driver);
    ScreenCapture screenShots = new ScreenCapture(driver);
    Scanner scanner = new Scanner(System.in);
    Point point;
    String trainingModeUrl;

    @Test
    public void modeObserve() throws Exception {
        int taskCount = 1;
        System.out.println("Please insert URL");
        trainingModeUrl = scanner.next();
        browser.open(trainingModeUrl);
        waitforFileTab();
        injectJsScript();
        System.out.println(findNoOfTask());

        for (int i = 0; i < findNoOfTask(); i++) {
            launchModes("#toolbarContainer > div > div.col-100.mode-button-container.kepler > button:nth-child(2)");
            // button.mode-buttons:nth-child(2)
            while (!(boolean) ((JavascriptExecutor) driver).executeScript("return Kepler.Engine.Core.EngineApp.get_engine().get_isTerminatingFrame();")) {
                point = (Point) queryHtml.getCoordinates(By.cssSelector("#balloon > div"));
                // System.out.println(point);
                if (point.x <= 0 && point.y <= 0) {
                    // System.out.println(point);
                    screenShots.getScreenshotCompletePage("resources/Observescreenshots/1.png");
                }
                if (verify.contains(queryHtml.getAttributes(By.cssSelector("#balloon"), "class"), "informative_balloon")) {
                    // System.out.println("Invisible");
                    wait.waitForInvisibilityOf(By.cssSelector(".informative_balloon"));
                } else if (!verify.contains(queryHtml.getAttributes(By.cssSelector("#balloon"), "class"), "informative_balloon")) {
                    // System.out.println("Visible");
                    try {
                        wait.waitForElementToBePresent(By.cssSelector(".informative_balloon"), 20);
                    } catch (Exception e) {
                        break;
                    }
                }
                // System.out.println(((JavascriptExecutor) driver).executeScript("return
                // Kepler.Engine.Core.EngineApp.get_engine().get_isTerminatingFrame();"));
            }
            browser.switchToDefault();
            wait.waitForVisibilityOf(By.cssSelector("body > div:nth-child(4) > div > div"));
            mouseHandle.click(By.cssSelector("#modalDialogDiv > div.modal-footer > input[type='button']"));
            mouseHandle.click(By.cssSelector("#toolbarContainer > div > div.middle-wrap > div:nth-child(1) > div > button.next"));
            waitforFileTab();
            System.out.println("Task " + taskCount + " completed");
            taskCount++;
        }
        System.out.println("**************************************");
    }

    private void launchModes(String path) throws Exception {
        // waitforFileTab();
        mouseHandle.click(By.cssSelector(path));
        mouseHandle.click(By.cssSelector("#playButtonContainerDiv > button"));
        browser.switchToNewFrame(By.cssSelector("#zeusengine"));
        // System.out.println("Selenium helper is ready = " + ((JavascriptExecutor) driver).executeScript("return Kepler.Selenium.Core.Helper.IsReady()"));
    }

    private void waitforFileTab() {
        // browser.switchToNewFrame(By.cssSelector("#zeusengine"));
        try {
            wait.waitForVisibilityOf(By.cssSelector(".load-wrap"));
            wait.waitForInvisibilityOf(By.cssSelector(".load-wrap"));
        } catch (Exception e) {
            wait.waitForInvisibilityOf(By.cssSelector(".load-wrap"));
        }
        browser.switchToDefault();
    }

    private void injectJsScript() {
        browser.switchToNewFrame(By.cssSelector("#zeusengine"));
        ((JavascriptExecutor) driver).executeScript(
                        "var script = document.createElement('script');script.type = 'text/javascript';script.src='selenium1/scripts/release/selenium.js';document.head.appendChild(script);");
        browser.switchToDefault();
    }

    private int findNoOfTask() {
        String taskCount = queryHtml.getText(By.cssSelector("#taskCounter > p > span.task-counter"));
        return Integer.parseInt(taskCount.substring(5));
    }

    /*
     * @After
     * public void tearDown()
     * {
     * driver.quit();
     * }
     */

    /*
     * while(!verify.contains(queryHtml.getAttributes(By.cssSelector("#toolbarContainer > div > div.middle-wrap > div:nth-child(1) > div > button:nth-child(2)"
     * ), "class"), "disabled"));
     */

    /*
     * for (int i = 1; i <= balloonCount; i++)
     * {
     * point = queryHtml.getCoordinates(By.cssSelector("#balloon > div"));
     * System.out.println(point);
     * if (point.x <= 0 && point.y <= 0)
     * {
     * System.out.println(point);
     * screenShots.getFullScreenshot("resources/Observescreenshots/1.png");
     * }
     * if(i != balloonCount)
     * {
     * if(verify.contains(queryHtml.getAttributes(By.cssSelector("#balloon"), "class"), "informative_balloon"))
     * {
     * System.out.println("Invisible");
     * wait.waitForInvisibilityOf(By.cssSelector(".informative_balloon"));
     * } else if (!verify.contains(queryHtml.getAttributes(By.cssSelector("#balloon"), "class"), "informative_balloon"))
     * {
     * System.out.println("Visible");
     * wait.waitForElementToBePresent(By.cssSelector(".informative_balloon"));
     * }
     * }
     * }
     */
}
