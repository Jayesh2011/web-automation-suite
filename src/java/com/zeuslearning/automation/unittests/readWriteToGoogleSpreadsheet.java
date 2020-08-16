package com.zeuslearning.automation.unittests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import com.google.gdata.util.ServiceException;
import com.zeuslearning.automation.io.GoogleSpreadsheet;

public class readWriteToGoogleSpreadsheet {

    public static void main(String[] args) throws GeneralSecurityException, IOException, ServiceException, URISyntaxException {

        // String data[][] = { { "firstname", "lastname", "age", "height" }, { "kenil", "Fadia", "25", "160" }, { "kenil", "Fadia", "25", "160" } };
        GoogleSpreadsheet abc = new GoogleSpreadsheet();
        abc.accessSpreadsheet("Dummy Project");
        abc.readCurrentWorksheet();
        // abc.writeToCurrentWorkSheet(data);
    }
}
