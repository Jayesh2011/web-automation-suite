package com.zeuslearning.automation.io;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.zeuslearning.automation.selenium.interactions.QueryHtml;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedWriter;

public class TextFileOps extends BasicFileOps {

    private QueryHtml query;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private String text;
    private File file;

    public TextFileOps() {
        query = null;
    }

    public TextFileOps(Object driver) {
        query = new QueryHtml(driver);
    }

    public void openFile(String fileName) throws IOException {
        fileWriter = new FileWriter(fileName, true);
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    public String readAll(String filePath) throws Exception {
        file = new File(filePath);
        text = readAll(file);
        return text;
    }

    public String readAll(File fileName) throws Exception {
        // Reads docx files.

        FileInputStream fis = null;
        XWPFDocument document = null;
        try {
            fis = new FileInputStream(fileName.getAbsolutePath());

            document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            String textString = "";
            for (XWPFParagraph para : paragraphs) {
                textString += para.getText();
            }
            fis.close();
            return textString;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (document != null) {
                document.close();
            }
        }
    }

    public String[] readAllParagraphs(File fileName) throws Exception {
        // Reads docx files.

        FileInputStream fis = null;
        XWPFDocument document = null;
        try {
            fis = new FileInputStream(fileName.getAbsolutePath());

            document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            int totalNumberOfParagraphs = paragraphs.size();
            String[] paragraphsString = new String[totalNumberOfParagraphs];
            for (int i = 0; i < totalNumberOfParagraphs; i++) {
                paragraphsString[i] = paragraphs.get(i).getParagraphText();
            }
            return paragraphsString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (document != null) {
                document.close();
            }
        }
    }

    public String[] readAllParagraphs(String filePath) throws Exception {
        file = new File(filePath);
        String[] paragraphs = readAllParagraphs(file);
        return paragraphs;
    }

    public String readJsFiles(String filePath) {
        file = new File(filePath);
        String data = null;

        try {
            data = FileUtils.readFileToString(file, StandardCharsets.UTF_8.name());
        } catch (IOException e) {}

        return data;
    }

    public String readPdf() throws IOException {
        URL url = new URL(query.getUrl());
        InputStream urlOpenStream = url.openStream();
        BufferedInputStream fileToParse = new BufferedInputStream(urlOpenStream);
        PDDocument document = null;
        try {
            document = PDDocument.load(fileToParse);
            text = new PDFTextStripper().getText(document);
        } catch (Exception e) {} finally {
            if (document != null) {
                document.close();
            }
            fileToParse.close();
            urlOpenStream.close();
        }
        return text;
    }

    public boolean write(File fileName, String data) {
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {}
        bufferedWriter = new BufferedWriter(fileWriter);
        try {
            bufferedWriter.write(data);
        } catch (IOException e) {}
        try {
            bufferedWriter.close();
        } catch (IOException e) {}
        try {
            fileWriter.close();
        } catch (IOException e) {}
        return true;
    }

    public boolean append(String data) throws IOException {
        bufferedWriter.write(data + "\n");
        bufferedWriter.flush();
        return true;
    }

    public boolean closeFile() throws IOException {
        bufferedWriter.close();
        fileWriter.close();
        return true;
    }
}
