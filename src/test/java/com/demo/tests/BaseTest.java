package com.demo.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.demo.page.startPage;
import com.demo.report.Reporter;
import com.demo.utils.AppiumUtils;
import com.demo.utils.PropertyUtils;
import com.demo.utils.ViewEnum;
import com.demo.utils.WaitUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;


@Test
public class BaseTest {
	public static AppiumDriver<WebElement> driver;
	public final static String APPIUM_SERVER_URL = PropertyUtils.getProperty("appium.server.url",
			"http://127.0.0.1:4723/wd/hub");
	public final static int IMPLICIT_WAIT = PropertyUtils.getIntegerProperty("implicitWait", 10);
	public static WaitUtils waitUtils = new WaitUtils();
	public static ArrayList<String> scrShots = new ArrayList<String>();

	/**
	 * 每次Test run都會呼叫這個方法
	 * 
	 * @param appiumDriverType
	 * @param installAppName(可選) => 要安裝的app檔
	 * @param reinstallAppName(可選) => 要另外安裝的app檔名
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws MalformedURLException 
	 */
	@BeforeTest
	@Parameters({ "appiumDriverType", "installAppName", "platform", "deviceName",
			"platformVersion", "iosDeviceUdid"})
	public void setUpAppium(String appiumDriverType, @Optional String installAppName,String platform,@Optional String deviceName,@Optional String platformVersion, @Optional String iosDeviceUdid) throws  InterruptedException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, MalformedURLException {
		
		//Device connect
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		switch (platform) {
		case MobilePlatform.IOS:
			setDesiredCapabilitiesForIos(capabilities, deviceName, platformVersion, iosDeviceUdid,installAppName);
			driver = new IOSDriver<WebElement>(new URL(APPIUM_SERVER_URL), capabilities);
			break;
		case MobilePlatform.ANDROID:
			setDesiredCapabilitiesForAndroid(capabilities, installAppName, deviceName, platformVersion);
			driver = new AndroidDriver<WebElement>(new URL(APPIUM_SERVER_URL), capabilities);
			break;
		}
		
		setTimeOuts(driver);
		Reporter.setPlatform(platform);
		// chrome app安裝後需要同意的按鈕
		startProcess();
	}
	
	@AfterTest
	public void afterTest() {
		quitDriver();
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(final ITestResult result, ITestContext ctx)  {
	}

	@AfterClass
	public void afterClass() {

	}

	/**
	 * 一個suite結束後呼叫此方法，將driver quit
	 */
	@AfterSuite
	public void tearDownAppium() {
		
	}

	/**
	 * set the DesiredCapabilities for android
	 *
	 * @param desiredCapabilities
	 */
	private void setDesiredCapabilitiesForAndroid(DesiredCapabilities desiredCapabilities, String installAppName,
			String deviceName, String platformVersion) {
		String APP_PACKAGE_NAME = PropertyUtils.getProperty("android.app.packageName");
		String APP_ACTIVITY_NAME = PropertyUtils.getProperty("android.app.activityName");
		String APP_FULL_RESET = PropertyUtils.getProperty("android.app.full.reset");
		String APP_NO_RESET = PropertyUtils.getProperty("android.app.no.reset");
		
		if (StringUtils.stripToNull(deviceName)==null) deviceName = PropertyUtils.getProperty("android.app.deviceName");
		if (StringUtils.stripToNull(platformVersion)==null) platformVersion = PropertyUtils.getProperty("android.app.platformVersion");
		
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		// 如果有傳入installAppName then setCapability(MobileCapabilityType.APP)
		
		
		if (StringUtils.stripToNull(installAppName)==null) {
			
			installAppName=PropertyUtils.getProperty("android.app.location")+"//"+PropertyUtils.getProperty("android.app.name");
			desiredCapabilities.setCapability(MobileCapabilityType.APP, installAppName);
		}

		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE_NAME);
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY_NAME);
		desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, APP_FULL_RESET);
		desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, APP_NO_RESET);
//		desiredCapabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		desiredCapabilities.setCapability("autoDismissAlerts", true);
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);

		desiredCapabilities.setCapability(AndroidMobileCapabilityType.UNLOCK_TYPE, "pattern");
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.UNLOCK_KEY, "32147");
		// 不加timeout 超過60秒 就會被kill
		desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "3000");
	}

	/**
	 * set the DesiredCapabilities iOS
	 *
	 * @param desiredCapabilities
	 */
	private void setDesiredCapabilitiesForIos(DesiredCapabilities desiredCapabilities, String deviceName,
			String platformVersion, String iosDeviceUdid, String installAppName) {
		String APP_FULL_RESET = PropertyUtils.getProperty("ios.app.full.reset");
		String APP_NO_RESET = PropertyUtils.getProperty("ios.app.no.reset");
		String BUNDLE_ID = PropertyUtils.getProperty("ios.app.bundleId");

		// 如果有傳入installAppName then setCapability(MobileCapabilityType.APP)
		if (StringUtils.stripToNull(installAppName)==null) {
			installAppName = PropertyUtils.getProperty("ios.app.location")+PropertyUtils.getProperty("ios.app.name");
		}
		
		// 如果有傳入installAppName then setCapability(MobileCapabilityType.APP)
		if (StringUtils.stripToNull(iosDeviceUdid)==null) {
			iosDeviceUdid = PropertyUtils.getProperty("ios.device.udid");
		}
		
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
		desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, APP_FULL_RESET);
		desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, APP_NO_RESET);
		desiredCapabilities.setCapability(MobileCapabilityType.APP, installAppName);

		desiredCapabilities.setCapability(MobileCapabilityType.UDID, iosDeviceUdid);
		desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, BUNDLE_ID);

		// 不加timeout 超過60秒 就會被kill
		desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "3000");
	}
	


	/**
	 * 全域性等待，請在properties中設定秒數
	 *
	 * @param driver
	 */
	private static void setTimeOuts(AppiumDriver<WebElement> driver) {
		// Use a higher value if your mobile elements take time to show up
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
	}



	/**
	 * This will quite the android driver instance
	 */
	private void quitDriver() {
		try {
			BaseTest.driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AppiumDriver<WebElement> getDriver() {
		return driver;
	}

	/**
	 * 截圖
	 */
	public static void takeScrShot() {
		AppiumUtils.switchView(driver, ViewEnum.NativeView);
		// before take a screenshot wait a minute
		waitUtils.staticWait(1000);

		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) BaseTest.getDriver());

		// Call getScreenshotAs method to create image file
		String SrcFile = scrShot.getScreenshotAs(OutputType.BASE64);
		scrShots.add(SrcFile);
		waitUtils.staticWait(1000);
		AppiumUtils.switchView(driver, ViewEnum.WebView);
	}
	
//	 public static byte[] compressData(byte[] inputData) throws Exception {
//	        Deflater deflater = new Deflater();
//	        deflater.setInput(inputData);
//
//	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(inputData.length);
//	        deflater.finish();
//
//	        byte[] buffer = new byte[1024];
//	        while (!deflater.finished()) {
//	            int count = deflater.deflate(buffer);
//	            outputStream.write(buffer, 0, count);
//	        }
//
//	        outputStream.close();
//	        return outputStream.toByteArray();
//	    }
//	 
//	    public static byte[] decompressString(String compressedInput) throws Exception {
//	        byte[] compressedData = Base64.getDecoder().decode(compressedInput);
//	        
//	        Inflater inflater = new Inflater();
//	        inflater.setInput(compressedData);
//	        
//	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(compressedData.length);
//	        
//	        byte[] buffer = new byte[1024];
//	        while (!inflater.finished()) {
//	            int count = inflater.inflate(buffer);
//	            outputStream.write(buffer, 0, count);
//	        }
//	        
//	        outputStream.close();
//	        byte[] decompressedData = outputStream.toByteArray();
//	        
//	        //return new String(decompressedData, "UTF-8");
//	        return decompressedData;
//	    }

	/**
	 * 清空截圖
	 */
	public static void cleanScrShots() {
		scrShots.clear();
	}
	
	public void startProcess() {
		startPage startPage=new startPage(driver);
		startPage.click_UseWithOutAccBtn();
		startPage.click_GotitBtn();
	}
	
}
