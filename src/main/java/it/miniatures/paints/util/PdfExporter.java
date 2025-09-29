package it.miniatures.paints.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import it.miniatures.paints.entity.Paint;

import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PdfExporter {

    private List<Paint> paints;

    public PdfExporter(List<Paint> paints) {
        this.paints = paints;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Brand", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Color Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Code", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Paint paint : paints) {
            table.addCell(String.valueOf(paint.getId()));
            table.addCell(paint.getBrand());
            table.addCell(paint.getColorName());
            table.addCell(paint.getType());
            table.addCell(paint.getCode() != null ? paint.getCode() : "");
            table.addCell(String.valueOf(paint.getQuantity()));
            table.addCell(paint.getData().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Paints Inventory", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(title);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}
