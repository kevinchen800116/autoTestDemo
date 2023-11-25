package com.demo.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.codec.Base64;
import com.demo.tests.BaseTest;

public class TestListener implements ITestListener {
	private Font chineseFont = ChineseFont.getChineseFont();
	
	private Font failChineseFont = ChineseFont.getFailChineseFont();
	private Font passChineseFont = ChineseFont.getPassChineseFont();

	PdfPTable successStoryTable = null;

	PdfPTable failStoryTable = null;

	private HashMap<Integer, Throwable> throwableMap = null;

	private String testName;

	private String production;

	private ITestResult finalResult;

	public static String governmentId;
	
	public static String notes;

	public static Boolean isAnyMethodFailed = false;
	
	public static JSONObject resultJSON;
	
	int itemSize=1;

	public TestListener() throws DocumentException, IOException {
		log("JyperionListener()");

		this.throwableMap = new HashMap<Integer, Throwable>();
	}

	/**
	 * Invoked each time before a test will be invoked. The <code>ITestResult</code>
	 * is only partially filled with the references to class, method, start millis
	 * and status.
	 */
	public void onTestStart(ITestResult result) {
		
		System.out.println(
				"Method: " + result.getMethod().getMethodName() + " start...");
		if (isAnyMethodFailed) {
			throw new SkipException("Skipping Method: " + result.getMethod().getMethodName());
		}
	}

	/**
	 * Invoked each time a test succeeds.
	 */
	public void onTestSuccess(ITestResult result) {
		log("onTestSuccess(" + result + ")");
		System.out.println("Method: " + result.getMethod().getMethodName() + " success!");
		finalResult = result;
	}

	/**
	 * Invoked each time a test fails.
	 */
	public void onTestFailure(ITestResult result) {
		log("onTestFailure(" + result + ")");
		System.out.println("Method: " + result.getMethod().getMethodName() + " fail!");
		finalResult = result;
		BaseTest.takeScrShot();
		isAnyMethodFailed = true;
	}

	public void onTestSkipped(ITestResult result) {
		log("onTestSkipped(" + result + ")");
	}

	/**
	 * Invoked before running all the test methods belonging to the classes inside
	 * the <test> tag and calling all their Configuration methods.
	 * 
	 * production - 此次test的production
	 */
	public void onStart(ITestContext context) {
		try {
			this.production = context.getCurrentXmlTest().getParameter("production");
			this.testName = context.getName();
			System.out.println("執行測試: " + testName);
			isAnyMethodFailed = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Invoked after all the test methods belonging to the classes inside the <test>
	 * tag have run and all their Configuration methods have been called.
	 */
	public void onFinish(ITestContext context) {
		log("onFinish(" + context + ")");

		int status = finalResult.getStatus();
		PdfPTable table = null;
		try {
			if (status == ITestResult.SUCCESS) {
				table = Reporter.getSuccessStoryTable(this.production);
				Reporter.addSuccessNB();
			} else if (status == ITestResult.FAILURE) {
				table = Reporter.getFailStoryTable(this.production);
				Reporter.addFailNB();
			}
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

		// Story
		PdfPCell cell = new PdfPCell();
		cell.addElement(new Paragraph(testName, chineseFont));
		//cell.addElement(new Paragraph("備註：" + TestListener.notes, chineseFont));
		table.addCell(cell);

		// 狀態
		if (status == ITestResult.SUCCESS) {
			cell = new PdfPCell(new Paragraph("Success", chineseFont));
		} else if (status == ITestResult.FAILURE) {
			cell = new PdfPCell(new Paragraph("Fail", chineseFont));
		}
		table.addCell(cell);

		// 執行時間
		long excutionTime = TimeUnit.MILLISECONDS.toSeconds(context.getEndDate().getTime()- context.getStartDate().getTime());
		cell = new PdfPCell(new Paragraph("" + excutionTime));
		table.addCell(cell);

		// 成功或失敗結果截圖與描述
		Throwable throwable = finalResult.getThrowable();
		cell = new PdfPCell();

		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			System.out.println("Error: " + throwable);

			Paragraph description = new Paragraph("步驟：" + finalResult.getMethod().getDescription(), chineseFont);
			Paragraph excep = new Paragraph("Exception：" + throwable.toString(), chineseFont);

			cell.addElement(description);
			cell.addElement(excep);
		}

		for (String scrShot : BaseTest.scrShots) {
			Image screenShot = null;
			try {
		        // Create the Image object from the decompressed data
		        screenShot = Image.getInstance(Base64.decode(scrShot));
				screenShot.scalePercent(10f);
				screenShot.setAlignment(Element.ALIGN_LEFT);
				screenShot.setBorder(Rectangle.BOX);
				screenShot.setBorderWidth(1f);
				screenShot.setScaleToFitHeight(false);
				screenShot.setBorderColor(new BaseColor(35, 118, 190));
				cell.addElement(screenShot);
				
			} catch (Exception e) {
				// TODO: handle exception
			}

		        

		}

		cell.setPadding(0.25f);

		table.addCell(cell);
		BaseTest.cleanScrShots();
		
	}

	public static void log(Object o) {
	}
}
