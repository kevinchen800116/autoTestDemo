package com.demo.utils;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;

import java.time.Duration;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSTouchAction;

/**
 * 
 *
 * @author David on 2021/01/18
 */
public class ActionUtils {

    /**
     *  tap on a element
     * @param androidElement
     * @param driver
     */
    public static void tapOnElement(MobileElement mobileElement, AppiumDriver<WebElement> driver) {
        new TouchAction<IOSTouchAction>(driver)
                .tap(tapOptions().withElement(element(mobileElement)))
                .perform();
    }

    /**
     * Tap to an element for particular amount of time.
     * @param androidElement
     * @param driver
     * @param millis
     */
    public static void tapOnElementForParticularDuration(MobileElement androidElement, AppiumDriver<WebElement> driver, long millis) {
        new TouchAction<IOSTouchAction>(driver)
                .tap(tapOptions().withElement(element(androidElement)))
                .waitAction(waitOptions(Duration.ofMillis(millis))).perform();
    }

    //Tap on coordinates
    public static void tapOnCoordinates(AppiumDriver<WebElement> driver, int x, int y) {
        new TouchAction<IOSTouchAction>(driver)
                .tap(point(x, y))
                .perform();
    }

    //Tap on coordinates for particular amount of time.
    public static void tapOnCoordinates(AppiumDriver<WebElement> driver, int x, int y, long millis) {
        new TouchAction<IOSTouchAction>(driver)
                .tap(point(x, y))
                .waitAction(waitOptions(Duration.ofMillis(millis))).perform();
    }


}
