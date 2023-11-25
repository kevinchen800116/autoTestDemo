package com.demo.report;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;

public class Table {
	private PdfPTable pdfTable = null;

	private String currentProduction = null;

	private int currentProductionFirstRow;

	private Font chineseFont = ChineseFont.getChineseFont();

	public Table() throws DocumentException, IOException {
		
	}

	public Table createGeneralTable() throws DocumentException, IOException {
		pdfTable = new PdfPTable(new float[] { .1f, .2f, .1f, .1f, .5f });

		pdfTable.setSpacingBefore(15f);
		pdfTable.setSpacingAfter(15f);
		pdfTable.setWidthPercentage(100f);

		PdfPCell cell = new PdfPCell();
		cell = new PdfPCell(new Paragraph("產品", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("功能", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("結果", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("執行時間(秒)", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("截圖", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfTable.addCell(cell);
		return this;
	}
	
	public Table createApiTable() throws DocumentException, IOException {
		pdfTable = new PdfPTable(new float[] { .1f, .2f, .2f, .2f, .2f, .1f });

		pdfTable.setSpacingBefore(15f);
		pdfTable.setSpacingAfter(15f);
		pdfTable.setWidthPercentage(100f);

		PdfPCell cell = new PdfPCell();
		cell = new PdfPCell(new Paragraph("產品", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPaddingBottom(5f);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("功能", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPaddingBottom(5f);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("項目", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPaddingBottom(5f);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("預期結果", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPaddingBottom(5f);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("實際結果", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPaddingBottom(5f);
		pdfTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("結果", chineseFont));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPaddingBottom(5f);
		pdfTable.addCell(cell);
		return this;
	}
	
	/**
	 * 新增表格列時，確認此次test的production是否跟上一次test相同
	 * 若相同則prodution的格子加高，若不同則新增一格prodution的格子
	 * 
	 * @param production - 此次test的production
	 */
	public void newRow(String production) {
		if (production != null && production.equals(currentProduction)) {
			PdfPRow lastRow = pdfTable.getRow(currentProductionFirstRow);
			int currentRowSpan = lastRow.getCells()[0].getRowspan();
			lastRow.getCells()[0].setRowspan(currentRowSpan + 1);
		} else {
			PdfPCell cell = new PdfPCell(new Paragraph(production, chineseFont));
			pdfTable.addCell(cell);
			currentProduction = production;
			currentProductionFirstRow = pdfTable.size();
		}
	}
	
	/**
	 * 新增表格列時，確認此次test的production是否跟上一次test相同
	 * 若相同則prodution的格子加高，若不同則新增一格prodution的格子
	 * 
	 * @param production - 此次test的production
	 */
	public void newApiRow(String production, int size) {
		if (production != null && production.equals(currentProduction)) {
			PdfPRow lastRow = pdfTable.getRow(currentProductionFirstRow);
			int currentRowSpan = lastRow.getCells()[0].getRowspan();
			lastRow.getCells()[0].setRowspan(currentRowSpan + size);
		} else {
			PdfPCell cell = new PdfPCell(new Paragraph(production, chineseFont));
			cell.setRowspan(size);
			pdfTable.addCell(cell);
			currentProduction = production;
			currentProductionFirstRow = pdfTable.size();
		}
	}

	public PdfPTable getpdfTable() {
		return pdfTable;
	}
}
