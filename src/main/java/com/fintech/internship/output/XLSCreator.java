package com.fintech.internship.output;

import com.fintech.internship.data.pojo.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.fintech.internship.data.ConstantsUtil.DATE_FORMAT;

public class XLSCreator {

    private Workbook workbook;
    private Sheet sheet;
    private Font headerFont;
    private CellStyle headerCellStyle;
    public String[] columns;

    public XLSCreator(String[] columns) {
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet("Пользователи");
        this.headerFont = initHeaderFont();
        this.headerCellStyle = initHeaderStyle();
        this.columns = columns;
    }

    private Font initHeaderFont() {
        Font resultFont = workbook.createFont();
        resultFont.setBold(true);
        resultFont.setColor(IndexedColors.BLUE_GREY.getIndex());
        return resultFont;
    }

    private CellStyle initHeaderStyle() {
        CellStyle resultCellStyle = workbook.createCellStyle();
        resultCellStyle.setFont(headerFont);
        return resultCellStyle;
    }


    private void setHeader() {
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void setContent(List<User> users) {

        int rowNum = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(user.getFirstName());
            row.createCell(1).setCellValue(user.getSecondName());
            row.createCell(2).setCellValue(user.getLastName());
            row.createCell(3).setCellValue(user.getAge());
            row.createCell(4).setCellValue(user.getGender());

            Cell dateOfBirthCell = row.createCell(5);
            dateOfBirthCell.setCellValue(DATE_FORMAT.format(user.getDateOfBirth()));

            row.createCell(6).setCellValue(user.getiNN());
            row.createCell(7).setCellValue(user.getZipcode());
            row.createCell(8).setCellValue(user.getCountry());
            row.createCell(9).setCellValue(user.getArea());
            row.createCell(10).setCellValue(user.getCity());
            row.createCell(11).setCellValue(user.getStreet());
            row.createCell(12).setCellValue(user.getHouse());
            row.createCell(13).setCellValue(user.getFlat());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private File writeXLSFile(String pathToFile) throws IOException {
        File outputFile = new File(pathToFile);
        FileOutputStream fileOut = new FileOutputStream(outputFile);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        return outputFile;
    }

    public File populateAndWriteToXLS(List<User> users, String pathToFile) {
        setHeader();
        setContent(users);
        File outputFile = null;
        try {
            outputFile = writeXLSFile(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Файл создан. Путь: " + outputFile.getAbsolutePath());
        return outputFile;
    }

}
