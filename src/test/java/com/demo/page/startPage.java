package com.demo.page;

import org.openqa.selenium.WebElement;

import com.demo.utils.AppiumUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class startPage extends BasePage{

	public startPage(AppiumDriver<WebElement> driver) {
		super(driver);
	}
	@AndroidFindBy(id = "com.android.chrome:id/ack_button")
	WebElement gotItBtn;
	
	@AndroidFindBy(id = "com.android.chrome:id/signin_fre_dismiss_button")
	WebElement UseWithOutAccBtn;
	
	public void click_GotitBtn() {
		try {
			for (int i = 0; i < 6; i++) {
				if (AppiumUtils.isElementDisplayed(gotItBtn)) {
					gotItBtn.click();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("沒有出現gotIt按鈕");
		}
	}
	
	public void click_UseWithOutAccBtn() {
		try {
			for (int i = 0; i < 6; i++) {
				if (AppiumUtils.isElementDisplayed(UseWithOutAccBtn)) {
					UseWithOutAccBtn.click();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("沒有出現UseWithOutAcc按鈕");
		}
	}

}
