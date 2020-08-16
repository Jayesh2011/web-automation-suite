package com.zeuslearning.automation.unittests;

import org.apache.commons.lang3.ArrayUtils;

import com.google.gdata.data.PlainTextConstruct;
import com.zeuslearning.automation.io.ExcelFileOps;
import com.zeuslearning.automation.io.GoogleSpreadsheet;

public class oldMathUrlToNew {

    public static void main(String[] args) throws Exception {
        GoogleSpreadsheet gSheet = new GoogleSpreadsheet();

        gSheet.accessSpreadsheet("TET Current Release update");
        gSheet.selectWorksheet("Prod Links");
        String data[][] = gSheet.readCurrentWorksheet();
        String units = null, concepts = null;

        for (int i = 0; i < data.length; i++) {
            if (data[i][1] == null
                    || !(data[i][1].equalsIgnoreCase("Math TB") || data[i][1].equalsIgnoreCase("MATHTB"))) {
                continue;
            }
            String splitString[] = data[i][3].split("/|#");
            for (int x = 1; x < splitString.length; x++) {
                if (splitString[x - 1].equalsIgnoreCase("guidUnitId")) {
                    units = splitString[x];
                    break;
                }
                if (splitString[x - 1].equalsIgnoreCase("guidConceptId")) {
                    concepts = splitString[x];
                }
            }
            data[i][4] = splitString[0] + "//" + splitString[2] + "/learn/techbook/units/" + units + "/concepts/"
                    + concepts + "?cache_level=l0";
        }

        for (int i = 0; i < data.length; i++) {
            boolean delete = true;
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != null) {
                    delete = false;
                    continue;
                }
            }
            if (delete) {
                data = ArrayUtils.removeElement(data, data[i]);
                i--;
            }
        }

        String columnHeader[] = { "Description", "Techbook", "GUIDs", "Links", "Links for New Math",
                "Other info if required" };
        String writeBack[][] = new String[data.length + 1][data[0].length];
        writeBack[0] = columnHeader;

        for (int countI = 1; countI < data.length; countI++) {
            for (int countJ = 0; countJ < data[0].length; countJ++) {
                if (data[countI][countJ] == null) {
                    writeBack[countI][countJ] = new PlainTextConstruct("-").getPlainText();
                } else {
                    writeBack[countI][countJ] = new PlainTextConstruct(data[countI][countJ].trim()).getPlainText();
                }
            }
        }
        new ExcelFileOps().write( "./TET Current Release update.xlsx", writeBack);
        //gSheet.updateCurrentWorksheet(columnHeader, writeBack);
    }
}
