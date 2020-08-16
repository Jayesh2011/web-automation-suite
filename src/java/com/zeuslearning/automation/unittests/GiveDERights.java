package com.zeuslearning.automation.unittests;

import org.junit.Test;
import org.openqa.selenium.By;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IKeyboardOperations;
import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.io.GoogleSpreadsheet;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.Keyboard;
import com.zeuslearning.automation.selenium.interactions.Mouse;
import com.zeuslearning.automation.selenium.interactions.Timeout;

public class GiveDERights {
	@Test
	public void test() throws Exception {

		IDriver sdriver = new Driver("./src/java/com/zeuslearning/automation/Config.properties");
		Object driver = sdriver.getDriver("firefox");
		IWindowOperations browser = new BrowserWindow(driver);
		IMouseOperations mouse = new Mouse(driver);
		IKeyboardOperations keyboard = new Keyboard(driver);

		browser.open("http://editorial.qa.discoveryeducation.com/");

		keyboard.type(By.cssSelector("input[name='username']"), "automationtestuserzeus");
		keyboard.type(By.cssSelector("input[name='password']"), "zeus@123");
		mouse.click(By.cssSelector("input[type='submit']"));

		GoogleSpreadsheet gs = new GoogleSpreadsheet();
		gs.accessSpreadsheet("Flash Interactives");
		gs.selectWorksheet("Sheet 1");
		Timeout waitF = (new Timeout(driver, 30));
		String data[][] = gs.readCurrentWorksheet();
		for (int i = 0; i < data.length; i++) {

			String editorialUrl = "http://editorial.qa.discoveryeducation.com/asset/assetEdit.cfm?guidAssetId="
					+ data[i][0] + "#/tab-4";

			browser.open(editorialUrl);
			mouse.click(By.cssSelector("#tab-4"));

			System.out.println(i);

			waitF.waitForVisibilityOf(By.cssSelector(".SUR"));
			waitF.waitForElementToBeClickable(By.cssSelector("#checkboxServiceUsageRights14"));
			mouse.click(By.cssSelector("#checkboxServiceUsageRights14"));
			waitF.waitForVisibilityOf(By.cssSelector("#checkboxServiceUsageRights14-wrap"));
			for (int x = 1; x <= 20; x++) {
				String selector = "#checkboxServiceUsageRights14-wrap label:nth-child(" + x
						+ ") input[name='media_group_id']";
				waitF.waitForVisibilityOf(By.cssSelector(selector));
				mouse.click(By.cssSelector(selector));
			}
			mouse.click(By.cssSelector("#asset-tabs-wrap input[type='submit']"));
			waitF.waitForVisibilityOf(By.cssSelector(".successMsg"));

		}
	}
}
