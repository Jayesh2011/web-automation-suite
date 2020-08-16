package com.zeuslearning.automation.io;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class BasicFileOps implements IInputOutputOps {

    protected File _fileHandle;

    public boolean close() {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public boolean open(Object source) throws IOException {

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (((File) source).exists()) {
                desktop.open((File) source);
            }
            return true;
        }
        System.out.println("Desktop is not supported");
        return false;
    }

    public boolean open(String source) {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public Object read() {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public String readAll() {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public boolean reset() {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public boolean write(Object data) {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public boolean write(String data) {
        throw new UnsupportedOperationException("The method is not implemented yet.");
    }

    public String createPath(String relativePath) {
        FileSystem path = FileSystems.getDefault();
        String absolutePath = path.getPath(System.getProperty("user.dir"), relativePath).toString();
        try {
            path.close();
        } catch (Exception e) {}
        return absolutePath;
    }
}
