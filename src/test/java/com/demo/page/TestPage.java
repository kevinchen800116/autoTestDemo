package com.demo.page;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.demo.tests.BaseTest;
import com.demo.utils.AppiumUtils;
import com.demo.utils.ViewEnum;

import io.appium.java_client.AppiumDriver;

public class TestPage extends BasePage{
	int count=0;

	public TestPage(AppiumDriver<WebElement> driver) {
		super(driver);
	}
	/**
	 * 登入按鈕
	 */
	@FindBy(css = "a.cubre-m-button.-login")
	WebElement loginBtn;
	/**
	 * 左上主選菜單
	 */
	@FindBy(css = "a.cubre-a-burger")
	WebElement siteMenu;
	
	@FindBy(css = "div.cubre-o-channel__item")
	List<WebElement> siteSubMenu;
	/**
	 * 產品介紹
	 */
	@FindBy(css ="div.cubre-a-menuSortBtn.-l1")
	List<WebElement> productMenu;
	/**
	 * 產品介紹 子菜單
	 */
	@FindBy(css = "div.cubre-o-menu__item.is-L1open>div.cubre-o-menu__content>div.cubre-o-menuLinkList>div.cubre-o-menuLinkList__item[data-menul2-item]>div.cubre-o-menuLinkList__btn>div.cubre-a-menuSortBtn[data-menul2-btn]")
	List<WebElement> productSubMenu;
	/**
	 * 信用卡 子菜單
	 */
	@FindBy(css = "div.cubre-o-menuLinkList__item.is-L2open>div.cubre-o-menuLinkList__content>a.cubre-a-menuLink")
	List<WebElement> creditCardSubMenu;
	/**
	 * 停發卡 菜單 
	 * 	 
	 */
	@FindBy(css = "div.swiper-wrapper>a+a+a+a+a+a>p")
	WebElement stopPublicCardMenu;
	/**
	 * 停發卡
	 * 	 
	 */
	@FindBy(css = "section+section+section+section+section+section>div>div+div>div>div>div")
	List<WebElement> card10;
	/**
	 * 停發卡 文字描述
	 * 	 
	 */
	@FindBy(css = "section+section+section+section+section+section>div>div+div>div>div>div>div>div+div+div.cubre-m-compareCard__content  >div+div>div>div>div>div>div+div")
	List<WebElement> cardDescription;
	
	/**
	 * 往左滑並查看描述是否為本卡已停止申辦
	 * 	 
	 */
	public int scrollToleft_countStopCard() {
		for (int i = 0; i < card10.size(); i++) {
			if (cardDescription.get(i).getText().equals("本卡已停止申辦")) {
				BaseTest.takeScrShot();
				count++;
			}
			AppiumUtils.scrollToLeft(driver,1,1);
		}
		return count;
	}
	/**
	 * 往左滑 點擊 停發卡菜單
	 * 	 
	 */
	public void click_StopCardMenu() throws Exception {
		AppiumUtils.scrollLeftToclickElement(driver,stopPublicCardMenu);
	}
	/**
	 *  點擊 信用卡列表內的選單
	 *  EX:卡片介紹 
	 * 	 
	 */
	public void click_CreditCardMenu(String target) throws Exception {
		for (int i = 0; i < creditCardSubMenu.size(); i++) {
			if (creditCardSubMenu.get(i).getText().equals(target)) {
				elementClick(creditCardSubMenu.get(i),target);
				break;
			}
		}
		AppiumUtils.switchToWindow(driver,"信用卡介紹 - 信用卡 - 產品介紹 - 國泰世華銀行");
	}
	/**
	 * 計算信用卡菜單內有多少項目	 
	 */
	public int countCreditCardMenuItem() {
		System.out.println("信用卡菜單內共有"+creditCardSubMenu.size()+"項目!");
		return creditCardSubMenu.size();
	}
	/**
	 * 點擊 產品介紹內的選單項目
	 */
	public void click_productSubMenu(String target) throws Exception {
		for (int i = 0; i < productSubMenu.size(); i++) {
			if (productSubMenu.get(i).getText().equals(target)) {
				elementClick(productSubMenu.get(i),target);
				break;
			}
		}
	}
	/**
	 * 點擊 產品介紹
	 */
	public void click_productMenu(String target) throws Exception {
		for (int i = 0; i < productMenu.size(); i++) {
			if (productMenu.get(i).getText().equals(target)) {
				elementClick(productMenu.get(i),target);
				break;
			}
		}
	}
	/**
	 * 驗證 菜單是否有出現
	 */
	public void checkSubMenuDisplay(String targetSubMenu) throws Exception {
		for (int i = 0; i < siteSubMenu.size(); i++) {
			if (siteSubMenu.get(i).getText().equals(targetSubMenu)) {
				System.out.println("菜單["+targetSubMenu+"]出現了!");
				break;
			}
		}
	}
	/**
	 * 點擊左上角菜單
	 */
	public void click_siteMenu(String targetMenu) throws Exception {
		elementClick(siteMenu,targetMenu);
	}
	/**
	 * 到指定url頁面
	 */
	public void getUrl(String Url,String window) {
		System.out.println("準備進入"+window+"網站");
		driver.get(Url);
		System.out.println("已進入"+window+"網站");
		AppiumUtils.tapOnPoint(driver,100,100);
		AppiumUtils.switchView(driver,ViewEnum.WebView);
		AppiumUtils.switchToWindow(driver,window);
		System.out.println("已經轉換到"+window+"頁面");
		waitUtils.waitForElementToBeVisible(loginBtn,30,driver);
	}

}
