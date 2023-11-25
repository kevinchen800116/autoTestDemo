package com.demo.tests;


import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.demo.page.login.LoginPage;
import com.demo.utils.AppiumUtils;

public class Test2 extends BaseTest {
	
	@Test(description = "測試")
	@Parameters({"platform"})
	public static void name(String platform) {
		try {
			LoginPage loginPage=new LoginPage(driver);
			loginPage.getUrl("https://www.cathaybk.com.tw/cathaybk/");
			Assert.assertEquals(driver.getTitle(),"國泰世華銀行");
			takeScrShot();
			System.out.println("debug: 已進入國泰世華網站");
			
			// 點擊左上角菜單
			loginPage.click_siteMenu("左上角菜單");

			// 點擊 個人金融 子菜單
			loginPage.click_subMenu("個人金融");
			
			// 點擊 產品介紹 子菜單
			loginPage.click_productMenu("產品介紹");
			
			// 點擊信用卡  子菜單
			loginPage.click_productSubMenu("信用卡");
			takeScrShot();
			// 計算選單項目數量
			Assert.assertEquals(loginPage.countCreditCardMenuItem(),8);
			
			// 進入信用卡列表選單 計算(停發信用卡數量並截圖)
			//卡片介紹
			loginPage.click_CreditCardMenu("卡片介紹");
			Assert.assertEquals(driver.getTitle(),"信用卡介紹 - 信用卡 - 產品介紹 - 國泰世華銀行");
			System.out.println("已經進入[信用卡介紹 - 信用卡 - 產品介紹 - 國泰世華銀行]頁面");
						
			//點停發卡
			loginPage.click_StopCardMenu();
			
			//左滑計算停發卡數量
			System.out.println("共有停辦卡"+loginPage.scrollToleft_countStopCard()+"張");
			
			
			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
