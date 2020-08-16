package com.zeuslearning.automation.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LogToExternalFile {

    private String _filePath;
    File logFile;
    PrintWriter writer;
    SimpleDateFormat sdf;

    public LogToExternalFile() {
        sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        _filePath = "./errorLogs_" + sdf.format(new Timestamp(System.currentTimeMillis())) + ".log";
        try {
            logFile = new File(_filePath);
            writer = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {}
    }

    public LogToExternalFile(String filePath) {
        sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        _filePath = filePath;
        try {
            logFile = new File(_filePath);
            writer = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {}
    }

    void append(String message) {
        writer.println(sdf.format(new Timestamp(System.currentTimeMillis())) + " :: " + message);
        writer.println();
        writer.flush();
    }

    public void info(String information) {
        append("INFO:: " + information);
    }

    public void warn(String warning) {
        append("WARN:: " + warning);
    }

    public void error(String error) {
        append("ERROR:: " + error);
    }

    public void closeAll() {
        writer.println(sdf.format(new Timestamp(System.currentTimeMillis())) + " :: Closing Logger.");
        writer.close();
    }
}
