package com.fintech.internship.output;

import com.fintech.internship.data.pojo.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

import static com.fintech.internship.data.ConstantsUtil.columns;

public class PDFCreator {

    private List<User> users;
    private Format format;
    private Font headerFont;
    private Font cellFont;
    private PdfPTable table;

    public PDFCreator(List<User> userList) {
        this.users = userList;
        this.format = new SimpleDateFormat("dd-MM-yyyy");
    }


    private void setFont() throws IOException, DocumentException {
        BaseFont baseFont;
        baseFont = BaseFont.createFont(
                "src/main/resources/InconsolataCyr.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED);
        headerFont = new Font(baseFont, 12, Font.NORMAL);
        cellFont = new Font(baseFont, 10, Font.NORMAL);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of(columns)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, headerFont));
                    table.addCell(header);
                });
    }

    private void addRows(List<User> users) {

        for (User user : users) {
            PdfPCell[] userInf = new PdfPCell[14];
            userInf[0] = new PdfPCell(new Phrase(user.getFirstName(), cellFont));
            userInf[1] = new PdfPCell(new Phrase(user.getSecondName(), cellFont));
            userInf[2] = new PdfPCell(new Phrase(user.getLastName(), cellFont));
            userInf[3] = new PdfPCell(new Phrase(String.valueOf(user.getAge()), cellFont));
            userInf[4] = new PdfPCell(new Phrase(user.getGender(), cellFont));
            userInf[5] = new PdfPCell(new Phrase(format.format(user.getDateOfBirth()), cellFont));
            userInf[6] = new PdfPCell(new Phrase(user.getiNN(), cellFont));
            userInf[7] = new PdfPCell(new Phrase(String.valueOf(user.getZipcode()), cellFont));
            userInf[8] = new PdfPCell(new Phrase(user.getCountry(), cellFont));
            userInf[9] = new PdfPCell(new Phrase(user.getArea(), cellFont));
            userInf[10] = new PdfPCell(new Phrase(user.getCity(), cellFont));
            userInf[11] = new PdfPCell(new Phrase(user.getStreet(), cellFont));
            userInf[12] = new PdfPCell(new Phrase(String.valueOf(user.getHouse()), cellFont));
            userInf[13] = new PdfPCell(new Phrase(String.valueOf(user.getFlat()), cellFont));
            writePDFTable(userInf);
        }
    }

    private void writePDFTable(PdfPCell[] pdfPCells) {
        for (PdfPCell pdfPCell : pdfPCells) {
            table.addCell(pdfPCell);
        }
    }

    public void createFile(String dest) {
        Document document = new Document(PageSize.A3,
                0, 0, 0, 0);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));

            document.open();
            setFont();

            table = new PdfPTable(14);
            table.setWidthPercentage(100);
            table.setSpacingBefore(0f);
            table.setSpacingAfter(0f);
            table.setWidths(new float[]{1.0f, 1.2f, 1.0f, 0.8f, 0.4f, 0.9f,
                    1.0f, 0.9f, 1.2f, 1.3f, 1.3f, 0.9f, 0.5f, 0.9f});
            addTableHeader(table);
            addRows(users);

            document.add(table);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            document.close();
        }
    }
}