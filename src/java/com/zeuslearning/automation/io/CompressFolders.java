package com.zeuslearning.automation.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressFolders {

    public void zipDirectory(String pathToDirectory) {
        File directoryToZip = new File(pathToDirectory);

        zipDirectory(pathToDirectory, directoryToZip.getAbsolutePath() + ".zip");
    }

    public void zipDirectory(String pathToDirectory, String pathToZip) {
        File directoryToZip = new File(pathToDirectory);

        List<File> fileList = new ArrayList<File>();
        getAllFiles(directoryToZip, fileList);
        writeZipFile(directoryToZip, fileList, new File(pathToZip).getAbsolutePath());
    }

    public List<File> getAllFiles(File dir) {
        List<File> fileList = new ArrayList<File>();
        getAllFiles(dir, fileList);
        return fileList;
    }

    private void getAllFiles(File dir, List<File> fileList) {
        File[] files = dir.listFiles();
        for (File file : files) {
            fileList.add(file);
            if (file.isDirectory()) {
                getAllFiles(file, fileList);
            }
        }
    }

    private void writeZipFile(File directoryToZip, List<File> fileList, String pathToZip) {

        try {
            FileOutputStream fos = new FileOutputStream(pathToZip);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (File file : fileList) {
                if (!file.isDirectory()) { // we only zip files, not directories
                    addToZip(directoryToZip, file, zos);
                }
            }

            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream(file);

        // we want the zipEntry's path to be a relative path that is relative
        // to the directory being zipped, so chop off the rest of the path
        String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
        ZipEntry zipEntry = new ZipEntry(zipFilePath);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
}
