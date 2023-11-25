package com.demo.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.demo.utils.AppiumUtils;
import com.demo.utils.PropertyUtils;
import com.demo.utils.WaitUtils;

import java.time.Duration;
import java.util.Iterator;


public class BasePage {
    public final static int IMPLICIT_WAIT = PropertyUtils.getIntegerProperty("implicitWait", 30);
    public WaitUtils waitUtils;
    protected final AppiumDriver<WebElement> driver;
    protected final JavascriptExecutor js;

    protected BasePage(AppiumDriver<WebElement> driver){
        this.driver = driver;
        waitUtils = new WaitUtils();
        initElements();
        loadProperties();
    	this.js = (JavascriptExecutor) driver;
    }

    private void initElements() {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(IMPLICIT_WAIT)), this);
    }

    private void loadProperties() {
        //TODO: Add the properties.
    }
    
    public void elementClick(WebElement ele,String msg) throws Exception {
		try {
			System.out.println("準備開始找尋["+msg+"]按鈕");
			if (AppiumUtils.isElementDisplayed(ele)) {
				waitUtils.waitForElementToBeClickable(ele,180,driver);
				ele.click();
				System.out.println("點擊["+msg+"]成功");
			}
		} catch (Exception e) {
			throw new Exception("發生錯誤,錯誤訊息: "+e.getMessage()+" ,錯誤行數: "+e.getStackTrace()[0].getFileName()+e.getStackTrace()[0].getLineNumber());
		}
	}

}
