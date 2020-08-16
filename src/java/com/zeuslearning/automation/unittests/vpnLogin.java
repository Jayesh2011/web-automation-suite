package com.zeuslearning.automation.unittests;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Clock;
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

public class vpnLogin {

    public static void main(String[] args) throws Exception {
        IDriver iDriver = new Driver("/src/java/com/zeuslearning/automation/Config.properties");
        WebDriver driver = (WebDriver) iDriver.getDriver("");
        IWindowOperations browser = new BrowserWindow(driver);
        IMouseOperations mouse = new Mouse(driver);
        IKeyboardOperations keyboard = new Keyboard(driver);

        browser.open("https://portico.discovery.com/b2b");
        System.out.println("Launch successful.");
        keyboard.typeAfterClear(By.cssSelector("#username"), "harshal_sawala@discovery.com");
        keyboard.typeAfterClear(By.cssSelector("#password"), "learn@1234");
        mouse.click(By.cssSelector("#btnSubmit_6"));
        System.out.println("Sign In Successful.");
        keyboard.typeAfterClear(By.cssSelector("#password_5"), "1");
        mouse.click(By.cssSelector("#btnAction_3"));

        System.out.println("Type verification successful.");
        Clock otpClock = new Clock(30);
        Totp totp = new Totp("IX7CGWQIVU6O5Q4P", otpClock);
        System.out.println(totp.now());
        keyboard.typeAfterClear(By.cssSelector("#password_5"), totp.now());
        mouse.click(By.cssSelector("#btnAction_3"));
        System.out.println("Log in successful.");

        Thread.sleep(2000);
        String keys = "enter";
        mouse.click(By.linkText("here"));

        try {
            keyboard.multipleKeyStrokes(By.linkText("here"), keys);
            System.out.println("Click Here.");
        } catch (Exception e) {}
        Thread.sleep(1500);

        mouse.click(By.cssSelector("#btnNCStart"));
        Thread.sleep(20000);

        iDriver.quitDriver();
    }
}
