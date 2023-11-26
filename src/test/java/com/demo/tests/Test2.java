package com.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.demo.page.TestPage;

public class Test2 extends BaseTest {
	TestPage Page;
	
	@Test(description = "進入國泰世華銀行網站")
	@Parameters({"url","title"})
	public void getURL(String url,String title) {
		try {
			Page=new TestPage(driver);
			Page.getUrl(url,"國泰世華銀行");
			Assert.assertEquals(driver.getTitle(),title);
			takeScrShot();
		} catch (Exception e) {
			Assert.fail(e.getMessage()+",錯誤位置: "+e.getStackTrace()[0].getFileName()+"錯誤行數: "+e.getStackTrace()[0].getLineNumber());
		}
	}
	
	@Test(description = "計算信用卡選單內項目")
	@Parameters({"menu","menu1","menu2","menu3"})
	public void countMenuItem(String menu,String menu1,String menu2,String menu3) {
		try {
			// 點擊左上角菜單
			Page.click_siteMenu(menu);

			// 驗證 個人金融 子菜單 出現
			Page.checkSubMenuDisplay(menu1);
			
			// 點擊 產品介紹 子菜單
			Page.click_productMenu(menu2);
			
			// 點擊信用卡  子菜單
			Page.click_productSubMenu(menu3);
			takeScrShot();
			// 計算選單項目數量
			Assert.assertEquals(Page.countCreditCardMenuItem(),8);
		} catch (Exception e) {
			Assert.fail(e.getMessage()+",錯誤位置: "+e.getStackTrace()[0].getFileName()+"錯誤行數: "+e.getStackTrace()[0].getLineNumber());
		}
	}
	
	@Test(description = "計算停發卡片數量")
	@Parameters({"menu4","title2"})
	public void countStopCard(String menu4,String title2) {
		try {
			//卡片介紹
			Page.click_CreditCardMenu(menu4);
			Assert.assertEquals(driver.getTitle(),title2);
			System.out.println("已經進入["+title2+"]頁面");
						
			//點停發卡
			Page.click_StopCardMenu();
			
			//左滑計算停發卡數量
			System.out.println("共有停辦卡"+Page.scrollToleft_countStopCard()+"張");
				
		} catch (Exception e) {
			Assert.fail(e.getMessage()+",錯誤位置: "+e.getStackTrace()[0].getFileName()+"錯誤行數: "+e.getStackTrace()[0].getLineNumber());
		}
	}
	
	@Test(description = "測試")
	public static void demoTest() {
		try {
			TestPage Page=new TestPage(driver);
			Page.getUrl("https://www.cathaybk.com.tw/cathaybk/","國泰世華銀行");
			Assert.assertEquals(driver.getTitle(),"國泰世華銀行");
			takeScrShot();
			
			// 點擊左上角菜單
			Page.click_siteMenu("左上角菜單");

			// 驗證 個人金融 子菜單 出現
			Page.checkSubMenuDisplay("個人金融");
			
			// 點擊 產品介紹 子菜單
			Page.click_productMenu("產品介紹");
			
			// 點擊信用卡  子菜單
			Page.click_productSubMenu("信用卡");
			takeScrShot();
			// 計算選單項目數量
			Assert.assertEquals(Page.countCreditCardMenuItem(),8);
			
			// 進入信用卡列表選單 計算(停發信用卡數量並截圖)
			//卡片介紹
			Page.click_CreditCardMenu("卡片介紹");
			Assert.assertEquals(driver.getTitle(),"信用卡介紹 - 信用卡 - 產品介紹 - 國泰世華銀行");
			System.out.println("已經進入[信用卡介紹 - 信用卡 - 產品介紹 - 國泰世華銀行]頁面");
						
			//點停發卡
			Page.click_StopCardMenu();
			
			//左滑計算停發卡數量
			System.out.println("共有停辦卡"+Page.scrollToleft_countStopCard()+"張");
			
			
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
