package it.miniatures.paints.util;

import it.miniatures.paints.entity.Paint;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.List;

public class ExcelExporter {

    public static void exportPaints(List<Paint> paints, OutputStream out) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Paints");

        // HEADER delle colonne
        Row header = sheet.createRow(0);
        String[] columns = {"ID", "Brand", "Color Name", "Type", "Code", "Quantity", "Date"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (Paint p : paints) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getBrand());
            row.createCell(2).setCellValue(p.getColorName());
            row.createCell(3).setCellValue(p.getType());
            row.createCell(4).setCellValue(p.getCode() != null ? p.getCode() : "");
            row.createCell(5).setCellValue(p.getQuantity());
            row.createCell(6).setCellValue(p.getData().toString());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(out);
        workbook.close();
    }
}
