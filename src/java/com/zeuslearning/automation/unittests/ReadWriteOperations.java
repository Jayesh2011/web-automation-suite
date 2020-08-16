package com.zeuslearning.automation.unittests;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

import com.zeuslearning.automation.driver.IDriver;
import com.zeuslearning.automation.interactions.IWindowOperations;
import com.zeuslearning.automation.io.TextFileOps;
import com.zeuslearning.automation.selenium.driver.Driver;
import com.zeuslearning.automation.selenium.interactions.BrowserWindow;

public class ReadWriteOperations {

    IDriver iDriver = new Driver("/src/java/com/zeuslearning/automation/Config.properties");
    Object driver = iDriver.getDriver("");
    IWindowOperations browser = new BrowserWindow(driver);
    String data = "This is the data to be added.";
    String filePath = "C:\\Users\\admin\\git\\automation-common\\resources\\IO documents\\testFile.txt";
    String pdfUrl = "file:///C:/Users/admin/Downloads/dotnet_TheRaceforEmpires.pdf";
    TextFileOps writeOps = new TextFileOps(driver);

    public void testWriteFunction() throws IOException {
        writeOps.write(new File(filePath), data);
    }

    public void lockWrite() throws IOException {
        writeOps.openFile(filePath);
        writeOps.append(data);
        writeOps.append(data);
    }

    public void testReadFunction() throws Exception {
        data = writeOps.readAll(new File(filePath));
        System.out.println(data);
    }

    @Test
    public void readPdfFile() throws Exception {
        browser.open(pdfUrl);
        data = writeOps.readPdf();
        System.out.println(data);
        iDriver.quitDriver();
    }
}
