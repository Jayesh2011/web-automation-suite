package com.zeuslearning.automation.unittests;

import org.junit.Test;
import org.openqa.selenium.By;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IAlertOperations;
import com.zeuslearning.automation.interactions.IKeyboardOperations;
import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IWaitOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.io.ExcelFileOps;
import com.zeuslearning.automation.io.GoogleSpreadsheet;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.HtmlAlertOps;
import com.zeuslearning.automation.selenium.interactions.Keyboard;
import com.zeuslearning.automation.selenium.interactions.Mouse;
import com.zeuslearning.automation.selenium.interactions.Timeout;

public class FlashInteractives {

    @Test
    public void test() throws Exception {

        IDriver sdriver = new Driver("./src/java/com/zeuslearning/automation/Config.properties");
        Object driver = sdriver.getDriver("firefox");
        IWindowOperations browser = new BrowserWindow(driver);
        IMouseOperations mouse = new Mouse(driver);
        IKeyboardOperations keyboard = new Keyboard(driver);
        IWaitOperations wait = new Timeout(driver, 30);
        ExcelFileOps excel = new ExcelFileOps();
        IAlertOperations alert = new HtmlAlertOps(driver);

        browser.open("https://app.qa.discoveryeducation.com");
        keyboard.typeAfterClear(By.cssSelector("#username"), "rdubey");
        keyboard.typeAfterClear(By.cssSelector("#password"), "discovery1");
        mouse.click(By.cssSelector("#loginButton"));
        wait.waitForVisibilityOf(By.cssSelector("#header-product-logo"));

        GoogleSpreadsheet gs = new GoogleSpreadsheet();
        gs.accessSpreadsheet("Flash Interactives");
        gs.selectWorksheet("Sheet 1");
        String data[][] = gs.readCurrentWorksheet();
        String op[][] = new String[data.length][data[0].length + 1];
        for (int i = 0; i < data.length; i++) {
            System.out.println(i);

            for (int j = 0; j < data[0].length; j++) {
                op[i][j] = data[i][j];
            }

            browser.open(data[i][6]);
            alert.acceptAlert();
            try {
                wait.waitForVisibilityOf(By.cssSelector(".player-error"), 5);
                op[i][7] = "Is not accessible under your current license";
                System.out.println(op[i][6] + "  ::  " + op[i][7]);
                continue;
            } catch (Exception e) {}
            try {
                String[] title = op[i][2].split("'|:");
                String frame = "iframe[title*='" + title[0] + "']";
                browser.switchToNewFrame(By.cssSelector(frame), 3);
                try {
                    wait.waitForVisibilityOf(By.cssSelector("embed[src]"));
                    op[i][7] = "Flash Interactive";
                } catch (Exception e) {
                    op[i][7] = "HTML Interactive";
                }
            } catch (Exception e) {}
            System.out.println(op[i][6] + "  ::  " + op[i][7]);

        }
        excel.write("./Flash Interactives.xlsx", op);
    }
}
