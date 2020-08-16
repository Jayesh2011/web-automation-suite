package com.zeuslearning.automation.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelFileOps extends BasicFileOps {

    String[][] excelData = null;
    int totalNoOfRows = 0;
    int totalNoOfCols = 0;
    ArrayList<String> arrayValue;
    ArrayList<ArrayList<String>> dataValues;

    public String[][] readFile(String filePath, String sheetName) {
        excelData = null;
        totalNoOfRows = 0;
        totalNoOfCols = 0;
        arrayValue = new ArrayList<String>();
        dataValues = new ArrayList<ArrayList<String>>();

        String fileExtension = FilenameUtils.getExtension(filePath);
        String[][] fileData = null;

        InputStream excelFileToRead = null;
        try {
            excelFileToRead = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {}

        if ("xls".equalsIgnoreCase(fileExtension)) {
            fileData = readXLSFile(excelFileToRead, sheetName);
        }
        if ("xlsx".equalsIgnoreCase(fileExtension)) {
            fileData = readXLSXFile(excelFileToRead, sheetName);
        }

        if (excelFileToRead != null) {
            try {
                excelFileToRead.close();
            } catch (IOException e) {}
        }
        return fileData;
    }

    public boolean write(String filePath, String[][] data) {
        int rowCount = 0;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        for (String[] aBook : data) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (String field : aBook) {
                Cell cell = row.createCell(columnCount++);
                if (field != null) {
                    cell.setCellValue(field);
                }
            }
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
        } catch (Exception e) {} finally {
            try {
                workbook.close();
            } catch (IOException e) {}

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
        }

        return true;
    }

    private String[][] readXLSXFile(InputStream excelFileToRead, String sheetName) {
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(excelFileToRead);
        } catch (IOException e) {}

        if (wb != null) {
            XSSFSheet sheet = wb.getSheet(sheetName);
            Row row;
            Cell cell;

            totalNoOfRows = sheet.getLastRowNum();
            Iterator<Row> rows = sheet.rowIterator();

            arrayValue = new ArrayList<String>();
            dataValues = new ArrayList<ArrayList<String>>();

            while (rows.hasNext()) {
                row = rows.next();
                totalNoOfCols = row.getLastCellNum();
                if (totalNoOfCols < 0) {
                    totalNoOfCols = 0;
                }
                arrayValue = new ArrayList<String>(totalNoOfCols);
                boolean toBeAdded = false;
                for (int colNo = (totalNoOfCols - 1); colNo >= 0; colNo--) {
                    cell = row.getCell(colNo);
                    try {
                        cell.getCellTypeEnum();
                    } catch (NullPointerException e) {
                        if (toBeAdded) {
                            arrayValue.add(0, "");
                        }
                        continue;
                    }
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            if (!toBeAdded && "".equals(cell.getStringCellValue())) {
                                break;
                            }
                            arrayValue.add(0, cell.getStringCellValue());
                            toBeAdded = true;
                            break;
                        case NUMERIC:
                            arrayValue.add(0, String.valueOf(cell.getNumericCellValue()));
                            toBeAdded = true;
                            break;
                        case BOOLEAN:
                            arrayValue.add(0, String.valueOf(cell.getBooleanCellValue()));
                            toBeAdded = true;
                            break;
                        case BLANK:
                            if (toBeAdded) {
                                arrayValue.add(0, cell.getStringCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                }
                dataValues.add(arrayValue);
            }
            arrayListToString(dataValues);
        }
        if (wb != null) {
            try {
                wb.close();
            } catch (IOException e) {}
        }
        return excelData;
    }

    private String[][] readXLSFile(InputStream excelFileToRead, String sheetName) {
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(excelFileToRead);
        } catch (Exception e) {}

        // To get the access to the sheet
        if (wb != null) {
            Sheet sh = wb.getSheet(sheetName);

            // To get the number of rows present in sheet
            totalNoOfRows = sh.getRows();
            // To get the number of columns present in sheet
            totalNoOfCols = sh.getColumns();

            dataValues = new ArrayList<ArrayList<String>>();

            boolean rowToBeAdded = false;
            for (int row = 0; row < totalNoOfRows; row++) {
                arrayValue = new ArrayList<String>();
                boolean cellToBeAdded = false;
                for (int col = totalNoOfCols - 1; col >= 0; col--) {
                    String cellValue = sh.getCell(col, row).getContents();
                    if (!cellToBeAdded) {
                        if ("".equals(cellValue)) {
                            continue;
                        }
                        cellToBeAdded = true;
                    }
                    if (cellValue == null || cellValue.length() == 0) {
                        arrayValue.add(0, "");
                    } else {
                        arrayValue.add(0, cellValue);
                    }
                }
                if (arrayValue.size() == 0 && !rowToBeAdded) {
                    continue;
                }
                dataValues.add(row, arrayValue);
                rowToBeAdded = true;
            }
            arrayListToString(dataValues);
        }

        if (wb != null) {
            wb.close();
        }

        return excelData;
    }

    private void arrayListToString(ArrayList<ArrayList<String>> dataToBeConverted) {
        boolean toBeAdded = false;
        for (int rowNo = dataToBeConverted.size() - 1; rowNo >= 0; rowNo--) {
            String[] rowData = dataToBeConverted.get(rowNo).toArray(new String[dataToBeConverted.get(rowNo).size()]);
            if (toBeAdded) {
                break;
            }
            for (int x = 0; x < rowData.length; x++) {
                if ("".equals(rowData[x])) {
                    toBeAdded = false;
                    if (x == rowData.length - 1) {
                        dataToBeConverted.remove(rowNo);
                    }
                } else {
                    toBeAdded = true;
                    break;
                }
            }
        }
        excelData = new String[dataToBeConverted.size()][];
        for (int rowNo = dataToBeConverted.size() - 1; rowNo >= 0; rowNo--) {
            excelData[rowNo] = dataToBeConverted.get(rowNo).toArray(new String[dataToBeConverted.get(rowNo).size()]);
        }
        arrayValue = new ArrayList<String>();
        dataValues = new ArrayList<ArrayList<String>>();
    }
}
