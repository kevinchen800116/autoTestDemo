package com.demo.utils;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demo.tests.BaseTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;


public class AppiumUtils {
	public static WaitUtils waitUtils = new WaitUtils();
	
	
	public static void scrollLeftToclickElement(AppiumDriver<WebElement> driver,WebElement ele) throws Exception {
	String platform=(String) driver.getCapabilities().getCapability(MobileCapabilityType.PLATFORM_NAME);
	
	Dimension size = driver.manage().window().getSize();
	//int start_Y=(int) (size.getWidth() * 0.45);
	int start_Y=1294;
	int start_X= platform.equals("Android") ? (int) (size.getHeight() * 0.9) : (int) (size.getHeight() * 0.85);
	int end_X=platform.equals("Android") ? (int) (size.getHeight() * 0.15) : (int) (size.getHeight() * 0.15);
	
	boolean tag=true;
	int i=0;
	while (tag) {
		if (i>=10) {
			throw new Exception("已經滑"+i+"次,還是沒點到...");
		}
		try {
			ele.click();
			tag=false;
		} catch (Exception e) {
			scroll(start_X, start_Y, end_X, start_Y, driver, 1700);
			System.out.println("滑第" + (i + 1) + "次");
		}
	}
		
		

}
	public static void scrollToLeft(AppiumDriver<WebElement> driver,int androidScrollCount,int iosScrollCount) {
		String platform=(String) driver.getCapabilities().getCapability(MobileCapabilityType.PLATFORM_NAME);
		
		Dimension size = driver.manage().window().getSize();
		int start_Y=1317;
		int start_X=platform.equals("Android") ? (int) (size.getHeight() * 1.5) : (int) (size.getHeight() * 0.85);
		int end_X=platform.equals("Android") ? (int) (size.getHeight() * 0.05) : (int) (size.getHeight() * 0.15);
		

		for (int i = 0; i < (platform.equals("Android") ? androidScrollCount : iosScrollCount); i++) {
			scroll(start_X, start_Y, end_X, start_Y, driver, 1700);
			//System.out.println("滑第" + (i + 1) + "次");
		}
	}
	/**
	 * 確認element是否出現於畫面
	 *
	 * @param element
	 * @return
	 */
	public static boolean isElementDisplayed(WebElement element) {
		boolean isPresent = false;
		try {
			element.isDisplayed();
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent && element.isDisplayed();
	}
	
	public static boolean isElementDisplayed(WebElement element,AppiumDriver<WebElement> driver) {
		try {
			 new WebDriverWait(driver,5).until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 往下滑動到特定元素出現
	 * 
	 * @param element
	 * @param driver
	 */
	public static void scrollToElement(MobileElement element, AppiumDriver<WebElement> driver) {
		int i = 0;
		do {
			i++;
			TouchAction<IOSTouchAction> swipe = new TouchAction<IOSTouchAction>(driver)
					.press(PointOption.point(200, 500)).waitAction(waitOptions(ofSeconds(2)))
					.moveTo(PointOption.point(200, -100)).release();
			swipe.perform();
		} while (!isElementDisplayed((IOSElement) element)); // (i==2);
		System.out.println(i);
	}

	/**
	 * 判斷某個元素(WebElement)是否出現
	 * 
	 * @param driver
	 * @param webElement
	 * @return
	 */
	public static boolean isWebElementDisplayed(AppiumDriver<WebElement> driver, WebElement webElement) {
		// 滑動過程會切到 NativeView，要使用 WebElement.isDisplayed() 要先切回 WebView
		AppiumUtils.switchView(driver, ViewEnum.WebView);
		boolean isWebElementDisplayed = false;
		try {
			webElement.isDisplayed();
			isWebElementDisplayed = webElement.isDisplayed();
		} catch (Exception e) {
			isWebElementDisplayed = false;
		}
		AppiumUtils.switchView(driver, ViewEnum.NativeView);
		return isWebElementDisplayed;
	}

	/**
	 * 往上滑動直到該元素出現(isDisplayed() = true)
	 * 
	 * @param element 該元素(WebElement)
	 * @param driver
	 */
	@SuppressWarnings("rawtypes")
	public static void scrollUpToElement(WebElement element, AppiumDriver<WebElement> driver) {
		int i = 0;
		Dimension size = driver.manage().window().getSize();
		int start_Y = (int) (size.getHeight() * 0.8);
		int end_Y = (int) (size.getHeight() * 0.7);
		int start_end_X = (int) (size.getWidth() * 0.5);
		while (!isWebElementDisplayed(driver, element)) {
			new TouchAction(driver).press(PointOption.point(start_end_X, start_Y))
					.waitAction(waitOptions(ofMillis(500))).moveTo(PointOption.point(start_end_X, end_Y)).release()
					.perform();
			i++;
			System.out.println("滑第" + i + "次");
		}
	}
	/**
	 * 	往下滑動
	 */
	public static void scrollDownTimes(AppiumDriver<WebElement> driver,int androidScrollCount,int iosScrollCount) {
		
		String platform=(String) driver.getCapabilities().getCapability(MobileCapabilityType.PLATFORM_NAME);
		
		Dimension size = driver.manage().window().getSize();
		int start_end_X = (int) (size.getWidth() * 0.5);
		int start_Y = platform.equals("Android") ? (int) (size.getHeight() * 0.9) : (int) (size.getHeight() * 0.85);
		int end_Y = platform.equals("Android") ? (int) (size.getHeight() * 0.15) : (int) (size.getHeight() * 0.15);
		
		for (int i = 0; i < (platform.equals("Android") ? androidScrollCount : iosScrollCount); i++) {
			scroll(start_end_X, start_Y, start_end_X, end_Y, driver, 1700);
			System.out.println("滑第" + (i + 1) + "次");
		}
	}

	/**
	 * 長截圖：擷取多張圖，直到最底部的元素
	 * 
	 * @param driver
	 * @param webElement
	 */
	public static void fullScreenshot(AppiumDriver<WebElement> driver, String platform, int androidScrollCount,
			int iosScrollCount) {
		AppiumUtils.switchView(driver, ViewEnum.NativeView);

		Dimension size = driver.manage().window().getSize();
		int start_end_X = (int) (size.getWidth() * 0.5);
		int start_Y = platform.equals("Android") ? (int) (size.getHeight() * 0.9) : (int) (size.getHeight() * 0.85);
		int end_Y = platform.equals("Android") ? (int) (size.getHeight() * 0.15) : (int) (size.getHeight() * 0.15);

		BaseTest.takeScrShot();
		for (int i = 0; i < (platform.equals("Android") ? androidScrollCount : iosScrollCount); i++) {
			scroll(start_end_X, start_Y, start_end_X, end_Y, driver, 1700);
			System.out.println("滑第" + (i + 1) + "次");
			BaseTest.takeScrShot();
		}

		AppiumUtils.switchView(driver, ViewEnum.WebView);
	}

	/**
	 * 滑動：從start (X,Y) 滑動到 end(X,Y)
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param driver
	 */
	@SuppressWarnings("rawtypes")
	public static void doSwipe(int startX, int startY, int endX, int endY, AppiumDriver<WebElement> driver) {
		new TouchAction(driver).press(PointOption.point(startX, startY)).waitAction(waitOptions(ofMillis(500)))
				.moveTo(PointOption.point(endX, endY)).release().perform();
	}

	/**
	 * 滑動：可以自訂滑動時間，從start (X,Y) 滑動到 end (X,Y)
	 * 
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param driver
	 * @param millisecond
	 */
	@SuppressWarnings("rawtypes")
	public static void scroll(int startX, int startY, int endX, int endY, AppiumDriver<WebElement> driver,
			int millisecond) {
		new TouchAction(driver).press(PointOption.point(startX, startY)).waitAction(waitOptions(ofMillis(millisecond)))
				.moveTo(PointOption.point(endX, endY)).release().perform();
	}

	/**
	 * 對特定的位置(X,Y) click
	 * 
	 * @param x
	 * @param y
	 * @param driver
	 */
	@SuppressWarnings("rawtypes")
	public static void clickOnPoint(int x, int y, AppiumDriver<WebElement> driver) {
		new TouchAction(driver).press(PointOption.point(x, y)).release().perform();
	}

	/**
	 * 對任意位置(X,Y)做點擊(tap)
	 * 
	 * @param x
	 * @param y
	 * @param driver
	 */
	@SuppressWarnings("rawtypes")
	public static void tapOnPoint(AppiumDriver<WebElement> driver, int x, int y) {
		new TouchAction(driver).tap(PointOption.point(x, y)).release().perform();
	}

	/**
	 * 對某個WebElement做點擊
	 * 
	 * @param driver
	 * @param webElement
	 */
	@SuppressWarnings("rawtypes")
	public static void tapOnWebElement(AppiumDriver<WebElement> driver, WebElement webElement) {
		new TouchAction(driver).tap(TapOptions.tapOptions().withElement(ElementOption.element(webElement))).release()
				.perform();
	}

	/**
	 * 切換view
	 * 
	 * @param driver
	 * @param viewEnum
	 * @throws Exception
	 */
	public static void switchView(AppiumDriver<WebElement> driver, ViewEnum viewEnum) throws NoSuchContextException {
		String viewName = viewEnum.getViewName();
		int waitTimes = 0;
		switchViewLoop: while (true) {
			if (waitTimes == 600) {
				break;
			}

			Set<String> views = driver.getContextHandles();
			if (views.size() >= 1) {
				for (String view : views) {
					String viewTmp = view;
					//System.out.println(viewTmp);
					if (viewTmp.toUpperCase().contains(viewName)) {
						driver.context(view);
						break switchViewLoop;
					}
				}
			} else {
				throw new NoSuchContextException("No such Context:" + viewName + "all you have are" + views);
			}

			waitUtils.staticWait(100);

			waitTimes++;
		}
	}

	/**
	 * 切換 window
	 * 
	 * @param driver
	 * @param windowTitle
	 */
	public static void switchToWindow(AppiumDriver<WebElement> driver, String windowTitle) {
		Set<String> allHandles = driver.getWindowHandles();

		for (String handle : allHandles) {
			if (!driver.getWindowHandle().equals(handle)) {
				driver.switchTo().window(handle);
			}

			if (driver.getTitle().equals(windowTitle)) {
				break;
			}
		}
	}

	
	

}