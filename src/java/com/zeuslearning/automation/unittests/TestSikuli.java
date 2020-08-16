package com.zeuslearning.automation.unittests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Clock;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import com.google.gdata.util.ServiceException;
import com.zeuslearning.automation.io.GoogleSpreadsheet;

public class TestSikuli {
    static Screen screen = new Screen();

    public static void main(String[] args) throws FindFailed, GeneralSecurityException, IOException, ServiceException, URISyntaxException {

        GoogleSpreadsheet gSheet = new GoogleSpreadsheet();
        gSheet.accessSpreadsheet("DE VPN auto-login");
        String[][] autoLoginData = gSheet.readCurrentWorksheet();
        System.setProperty("sikuli.Debug", "0");

        for (int i = 0; i < autoLoginData.length; i++) {

            if (autoLoginData[i][3] == null) {
                continue;
            }
            try {
                clickScreen("/resources/libraries/sikuli/screenshots/cortanaIcon.png");
            } catch (FindFailed e) {
                clickScreen("/resources/libraries/sikuli/screenshots/cortana.png");
            }
            typeTextOnScreen("/resources/libraries/sikuli/screenshots/cortana_text_field.png", "pulse");
            clickScreen("/resources/libraries/sikuli/screenshots/pulse_start.png");
            addNewConnection();
            clickScreen("/resources/libraries/sikuli/screenshots/connect.png");
            typeTextOnScreen("/resources/libraries/sikuli/screenshots/username.png", autoLoginData[i][0]);
            typeTextOnScreen("/resources/libraries/sikuli/screenshots/password.png", autoLoginData[i][1]);
            clickScreen("/resources/libraries/sikuli/screenshots/accept_connection.png");
            typeTextOnScreen("/resources/libraries/sikuli/screenshots/enter_response.png", "1");
            screen.click(screen.find("/resources/libraries/sikuli/screenshots/connection_hover.png"));
            Clock otpClock = new Clock(30);
            Totp totp = new Totp(autoLoginData[i][3], otpClock);
            typeTextOnScreen("/resources/libraries/sikuli/screenshots/enter_response.png", totp.now());
            clickScreen("/resources/libraries/sikuli/screenshots/connection_hover.png");
            try {
                clickScreen("/resources/libraries/sikuli/screenshots/b2b_math.png");
                clickScreen("/resources/libraries/sikuli/screenshots/connection_hover.png");
            } catch (Exception e) {}
            screen.wait("/resources/libraries/sikuli/screenshots/connectionSuccessful.png");
            clickScreen("/resources/libraries/sikuli/screenshots/disconnect_pulse.png");
            clickScreen("/resources/libraries/sikuli/screenshots/deleteConnection.png");
            screen.wait("/resources/libraries/sikuli/screenshots/deleteConfirmationPopup.png");
            clickScreen("/resources/libraries/sikuli/screenshots/deleteConnectionConfirmationOK.png");
        }
    }

    public static void clickScreen(String element) throws FindFailed {
        screen.wait(element, 30);
        screen.click(element);
    }

    public static void typeTextOnScreen(String element, String text) throws FindFailed {
        screen.wait(element, 30);
        screen.type(element, text);
    }

    public static void addNewConnection() throws FindFailed {
        clickScreen("/resources/libraries/sikuli/screenshots/addNewConnection.png");
        typeTextOnScreen("/resources/libraries/sikuli/screenshots/newConnectionName.png", "Discovery VPN (portico.discovery.com/b2b)");
        typeTextOnScreen("/resources/libraries/sikuli/screenshots/newConnectionServer.png", "portico.discovery.com/b2b");
        clickScreen("/resources/libraries/sikuli/screenshots/newConnectionAddButton.png");
        clickScreen("/resources/libraries/sikuli/screenshots/deselectedConnection.png");
    }
}
