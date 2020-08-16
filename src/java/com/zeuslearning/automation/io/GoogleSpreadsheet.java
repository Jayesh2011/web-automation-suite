package com.zeuslearning.automation.io;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleSpreadsheet extends ExcelFileOps {

    private SpreadsheetEntry spreadsheet = null;
    private URL SPREADSHEET_FEED_URL = null;
    private WorksheetEntry worksheet = null;
    private WorksheetFeed worksheetFeed = null;
    private List<WorksheetEntry> worksheets = null;
    private SpreadsheetService service = null;
    private URL worksheetFeedUrl = null;

    public GoogleSpreadsheet() throws GeneralSecurityException, IOException {
        SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

        // Personal Information Exchange Credentials
        File p12 = new File("./resources/permissionsToGoogleSpreadsheets/Automation Tests-a0a9989637e9.p12");

        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        String[] SCOPESArray = { "https://spreadsheets.google.com/feeds",
                "https://spreadsheets.google.com/feeds/spreadsheets/private/full", "https://docs.google.com/feeds" };
        final List<String> SCOPES = Arrays.asList(SCOPESArray);
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                // Service account email-id
                .setServiceAccountId("gspreadsheets@automation-tests-149808.iam.gserviceaccount.com")
                .setServiceAccountScopes(SCOPES).setServiceAccountPrivateKeyFromP12File(p12).build();

        service = new SpreadsheetService("Test");

        service.setOAuth2Credentials(credential);
    }

    public GoogleSpreadsheet(String pathToPermissions, String serviceAccountId)
            throws GeneralSecurityException, IOException {
        SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

        // Personal Information Exchange Credentials
        File p12 = new File(pathToPermissions);

        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        String[] SCOPESArray = { "https://spreadsheets.google.com/feeds",
                "https://spreadsheets.google.com/feeds/spreadsheets/private/full", "https://docs.google.com/feeds" };
        final List<String> SCOPES = Arrays.asList(SCOPESArray);
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                // Service account email-id
                .setJsonFactory(jsonFactory).setServiceAccountId(serviceAccountId).setServiceAccountScopes(SCOPES)
                .setServiceAccountPrivateKeyFromP12File(p12).build();

        service = new SpreadsheetService("Test");

        service.setOAuth2Credentials(credential);
    }

    public void accessSpreadsheet(String spreadsheetName) throws IOException, ServiceException {
        List<SpreadsheetEntry> spreadsheets = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class).getEntries();

        if (spreadsheets.size() == 0) {
            return;
        }

        for (int i = 0; i < spreadsheets.size(); i++) {
            spreadsheet = spreadsheets.get(i);
            worksheetFeedUrl = spreadsheet.getWorksheetFeedUrl();
            if (spreadsheet.getTitle().getPlainText().equals(spreadsheetName)) {
                break;
            }
        }

        worksheetFeed = service.getFeed(worksheetFeedUrl, WorksheetFeed.class);
        worksheets = worksheetFeed.getEntries();
        worksheet = worksheets.get(0);
    }

    public void selectWorksheet(String worksheetName) {
        for (int i = 0; i < worksheets.size(); i++) {
            worksheet = worksheets.get(i);
            if (worksheet.getTitle().getPlainText().equals(worksheetName)) {
                break;
            }
        }
    }

    public void addNewWorksheet(String worksheetName) throws IOException, ServiceException {
        WorksheetEntry newWorksheet = new WorksheetEntry();
        newWorksheet.setTitle(new PlainTextConstruct(worksheetName));
        newWorksheet.setColCount(5);
        newWorksheet.setRowCount(5);
        service.insert(worksheetFeedUrl, newWorksheet);
        selectWorksheet(worksheetName);
    }

    void updateWorksheetTitle(String worksheetTitle) throws ServiceException, IOException {
        worksheet.setTitle(new PlainTextConstruct(worksheetTitle));
        worksheet.update();
    }

    void deleteWorksheet() throws IOException, ServiceException {
        worksheet.delete();
    }

    void setWorksheetSize(int noOfRows, int noOfColumns) {
        worksheet.setRowCount(noOfRows);
        worksheet.setRowCount(noOfColumns);
    }

    void setWorksheetSize(String worksheetName, int noOfRows, int noOfColumns) {
        selectWorksheet(worksheetName);
        worksheet.setRowCount(noOfRows);
        worksheet.setRowCount(noOfColumns);
    }

    String getWorksheetName() {
        return worksheet.getTitle().getPlainText();
    }

    int getNoOfRows() {
        return worksheet.getRowCount();
    }

    int getNoOfColumns() {
        return worksheet.getColCount();
    }

    public String[][] readCurrentWorksheet() throws IOException, ServiceException, URISyntaxException {
        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

        int i = 0, j = 0, rows = getNoOfRows(), columns = getNoOfColumns();
        String data[][] = new String[rows][columns];
        for (ListEntry row : listFeed.getEntries()) {
            j = 0;
            for (String tag : row.getCustomElements().getTags()) {
                data[i][j] = row.getCustomElements().getValue(tag);
                j++;
            }
            i++;
        }
        return data;
    }

    public void writeToCurrentWorkSheet(String columnHeader[], String data[][]) throws IOException, ServiceException {
        URL cellFeedUrl = worksheet.getCellFeedUrl();
        CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

        int headerCount = 0;

        for (CellEntry cell : cellFeed.getEntries()) {
            if (cell.getId().contains("R1")) {
                cell.changeInputValueLocal(data[0][headerCount]);
                cell.update();
                headerCount++;
            }
            if (headerCount == data[0].length) {
                break;
            }
        }

        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

        List<ListEntry> rows = listFeed.getEntries();
        for (int x = 0; x < rows.size(); x++) {
            rows.get(0).delete();
        }

        for (int i = 0; i < data.length; i++) {
            ListEntry row = new ListEntry();
            for (int j = 0; j < columnHeader.length; j++) {
                row.getCustomElements().setValueLocal(columnHeader[j], data[i][j]);
            }
            row = service.insert(listFeedUrl, row);
        }
    }

    public void updateCurrentWorksheet(String columnHeader[], String data[][]) throws IOException, ServiceException {
        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

        List<ListEntry> rows = listFeed.getEntries();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < columnHeader.length; j++) {
                rows.get(i).getCustomElements().setValueLocal(columnHeader[j], data[i][j]);
            }
            rows.get(i).update();
        }
    }

    public void addNewSpreadsheet(String spreadsheetTitle) throws IOException, ServiceException {
        SpreadsheetEntry s = new SpreadsheetEntry();
        s.setTitle(new PlainTextConstruct(spreadsheetTitle));
        addNewWorksheet("workSheet");
        service.insert(SPREADSHEET_FEED_URL, s);
    }
}
