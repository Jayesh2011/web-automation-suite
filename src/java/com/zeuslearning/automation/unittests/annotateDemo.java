package com.zeuslearning.automation.unittests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IKeyboardOperations;
import com.zeuslearning.automation.interactions.IMouseOperations;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;
import com.zeuslearning.automation.selenium.interactions.Keyboard;
import com.zeuslearning.automation.selenium.interactions.Mouse;

public class annotateDemo {

    public static void main(String[] args) throws InterruptedException {
        IDriver iDriver = new Driver("src/java/com/zeuslearning/automation/Config.properties");
        WebDriver driver = (WebDriver) iDriver.getDriver("chrome");
        IWindowOperations browser = new BrowserWindow(driver);
        IKeyboardOperations keyboardHandle = new Keyboard(driver);
        IMouseOperations mouse = new Mouse(driver);

        browser.open("http://192.168.9.26:8081/");
        mouse.click(By.cssSelector("a.login"));
        keyboardHandle.typeAfterClear(By.cssSelector("#txtUsername"), "pooja");
        keyboardHandle.typeAfterClear(By.cssSelector("#txtPassword"), "password1");
        mouse.click(By.cssSelector("#btnSubmit"));
        Thread.sleep(3000);
        mouse.click(By.cssSelector("#XIconButton1298"));
        mouse.click(By.cssSelector("[id='1401']"));
        keyboardHandle.typeAfterClear(By.cssSelector("input.input-widget[id*='ZInputWidget50']"), "password1");
    }
}
