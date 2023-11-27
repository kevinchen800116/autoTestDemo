package com.demo.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Reporter implements IReporter {
	
	private static Table successStoryTable = null;

	private static Table failStoryTable = null;

	private static int successNB = 0;

	private static int failNB = 0;

	private static String platform;
	
	static boolean continuetagForFail=false;
	static boolean continuetagForSuccess=false;
	private static ArrayList<Table> subFailTableList=new ArrayList<Table>();
	private static ArrayList<Table> subSuccessTableList=new ArrayList<Table>();
	/**
	 * 取得成功story的表格
	 * 
	 * @param production - 此次test的production
	 */
	public static PdfPTable getSuccessStoryTable(String production) throws DocumentException, IOException {
		if (successStoryTable == null) {
			successStoryTable = new Table();
			successStoryTable.createGeneralTable();
		}
		
		successStoryTable.newRow(production);
		
		return successStoryTable.getpdfTable();
	}

	/**
	 * 取得失敗story的表格
	 * 
	 * @param production - 此次test的production
	 */
	public static PdfPTable getFailStoryTable(String production) throws DocumentException, IOException {
		if (failStoryTable == null) {
			failStoryTable = new Table();
			failStoryTable.createGeneralTable();
		}
		failStoryTable.newRow(production);

		return failStoryTable.getpdfTable();
	}

	public static void addSuccessNB() {
		successNB++;
	}

	public static void addFailNB() {
		failNB++;
	}

	@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String outputDirectory) {
		int sum = successNB + failNB;
		try {
			Document successReport = new Document();

			PdfWriter.getInstance(successReport, new FileOutputStream("App QA Report - Success.pdf"));
			successReport.open();

			Font chineseFont = ChineseFont.getChineseFont();

			Paragraph p = new Paragraph("App 自動化測試報告", chineseFont);
			successReport.add(p);
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date now = new Date();
			successReport.add(new Paragraph(sdFormat.format(now).toString()));
			p = new Paragraph("手機型號：" + platform, chineseFont);
			successReport.add(p);
			p = new Paragraph("測試總數：" + sum + ", 成功數：" + successNB, chineseFont);
			successReport.add(p);

			if (successStoryTable != null) {
				successReport.add(successStoryTable.getpdfTable());
			}
			if (continuetagForSuccess) {
				for (int i = 0; i < subSuccessTableList.size(); i++) {
					continueReport(subSuccessTableList.get(i), true, i);
				}
			}

			successReport.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Document failReport = new Document();

			PdfWriter.getInstance(failReport, new FileOutputStream("App QA Report - Fail.pdf"));
			failReport.open();

			Font chineseFont = ChineseFont.getChineseFont();

			Paragraph p = new Paragraph("App 自動化測試報告", chineseFont);
			failReport.add(p);
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date now = new Date();
			failReport.add(new Paragraph(sdFormat.format(now).toString()));
			p = new Paragraph("測試總數：" + sum + ", 失敗數：" + failNB, chineseFont);
			failReport.add(p);

			if (failStoryTable != null) {
				failReport.add(failStoryTable.getpdfTable());
			}
			if (continuetagForFail) {
				for (int i = 0; i < subFailTableList.size(); i++) {
					continueReport(subFailTableList.get(i), false, i);
				}
			}

			failReport.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setPlatform(String platform) {
		Reporter.platform = platform;
	}
	
	public static PdfPTable getSubPdfPTable(String production,ArrayList<Table>Tablelist,boolean continueTag) {
		try {
			if (Tablelist.size()==0) {
				Tablelist.add(new Table().createGeneralTable());
			}else if (Tablelist.size()>0 && continueTag==true && Tablelist.get(Tablelist.size()-1).getpdfTable().size()>50) {
				Tablelist.add(new Table().createGeneralTable());
			}
			
			Tablelist.get(Tablelist.size()-1).newRow(production);
			return Tablelist.get(Tablelist.size()-1).getpdfTable();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void continueReport(Table Tablelist,boolean testResult,int number) {
		int sum=successNB+failNB;
		try {
			Document continueReport = new Document();
			
			if (testResult) {
				PdfWriter.getInstance(continueReport, new FileOutputStream("App QA Report - Success"+number+".pdf"));
			}else {
				PdfWriter.getInstance(continueReport, new FileOutputStream("App QA Report - fail"+number+".pdf"));
			}
			
			continueReport.open();

			Font chineseFont = ChineseFont.getChineseFont();

			Paragraph p = new Paragraph("App 自動化測試報告", chineseFont);
			continueReport.add(p);
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date now = new Date();
			continueReport.add(new Paragraph(sdFormat.format(now).toString()));
			p = new Paragraph("手機型號：" + platform, chineseFont);
			continueReport.add(p);
			p = new Paragraph("測試總數：" + sum + ", 成功數：" + successNB, chineseFont);
			continueReport.add(p);

			if (Tablelist != null) {
				continueReport.add(Tablelist.getpdfTable());
			}

			continueReport.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
