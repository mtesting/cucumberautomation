package util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XssReader {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public XssReader(FileInputStream inputFile, String sheetName) throws IOException {
        workbook = new XSSFWorkbook(inputFile);
        sheet = workbook.getSheet(sheetName);
        inputFile.close();
    }

    public XssReader(FileInputStream inputFile) throws IOException {
        workbook = new XSSFWorkbook(inputFile);
        inputFile.close();
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public XSSFSheet getSheet(String sheetName) {
        return workbook.getSheet(sheetName);
    }

    public List<XSSFRow> getCurrentSheetRows(){
        List<XSSFRow> xssfRows = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        for (int r = 1; r <= rows; r++) {
            xssfRows.add(sheet.getRow(r));
        }
        return xssfRows;
    }

}
