package com.zeuslearning.automation.unittests;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Clock;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

public class vpnLoginSikuli {
    public static void main(String[] args) throws FindFailed, InterruptedException {

        Screen screen = new Screen();

        screen.click(screen.find("/resources/sikuli/screenshots/cortana.png"));
        screen.type(screen.find("/resources/sikuli/screenshots/cortana_text_field.png"), "pulse");
        screen.click(screen.find("/resources/sikuli/screenshots/pulse_start.png"));
        screen.click(screen.find("/resources/sikuli/screenshots/connect.png"));
        Thread.sleep(1000);
        screen.type(screen.find("/resources/sikuli/screenshots/username.png"), "Simranjeet_Chawla@discovery.com");
        screen.type(screen.find("/resources/sikuli/screenshots/password.png"), "Lucky@1234");
        screen.click(screen.find("/resources/sikuli/screenshots/accept_connection.png"));
        Thread.sleep(2500);
        screen.type(screen.find("/resources/sikuli/screenshots/enter_response.png"), "1");
        screen.click(screen.find("/resources/sikuli/screenshots/connection_hover.png"));
        Thread.sleep(1000);
        Clock otpClock = new Clock(30);
        Totp totp = new Totp("YXXNJGG5NPOIXHOU", otpClock);
        screen.type(screen.find("/resources/sikuli/screenshots/enter_response.png"), totp.now());
        screen.click(screen.find("/resources/sikuli/screenshots/connection_hover.png"));
        Thread.sleep(15000);
        try {
            screen.click(screen.find("/resources/sikuli/screenshots/b2b_math.png"));
            screen.click(screen.find("/resources/sikuli/screenshots/connection_hover.png"));
        } catch (Exception e) {}
    }
}
