package com.demo.report;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class ChineseFont {
	public static Font getChineseFont() throws DocumentException, IOException {
		return new Font(BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED));
	}
	
	public static Font getPassChineseFont() throws DocumentException, IOException {
		Font font = new Font(BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED));
		font.setColor(new BaseColor(0,153,0));
		return font;
	}
	
	public static Font getFailChineseFont() throws DocumentException, IOException {
		Font font = new Font(BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED));
		font.setColor(BaseColor.RED);
		return font;
	}
}
