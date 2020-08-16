package com.zeuslearning.automation.unittests;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

public class VpnDisconnectSikuli {
    public static void main(String[] args) throws FindFailed {

        Screen screen = new Screen();

        screen.click(screen.find("/resources/sikuli/screenshots/taskbar_icon.png"));
        screen.click(screen.find("/resources/sikuli/screenshots/disconnect_pulse.png"));
        screen.click(screen.find("/resources/sikuli/screenshots/close_pulse.png"));

    }
}
