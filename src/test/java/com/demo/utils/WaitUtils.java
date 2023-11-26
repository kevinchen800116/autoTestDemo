package com.demo.utils;

import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 有關於wait相關的方法都在這個類別
 * <p>
 * 隱式等待(explicitWait)定義在.properties檔
 *
 */
public class WaitUtils {

    public final int explicitWaitDefault = PropertyUtils.getIntegerProperty("explicitWait", 10);

    /**
     * 等同於Thread.sleep
     *
     * @param millis
     */
    public void staticWait(final long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (final InterruptedException e) {
        }
    }

    /**
     * 等待按鈕可以被click
     *
     * @param driver
     * @param element
     */
    public void waitForElementToBeClickable(final WebElement element,int second, final WebDriver driver) {
        new WebDriverWait(driver,second)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * 等待元素消失於畫面
     *
     * @param driver
     * @param locator
     */
    public void waitForElementToBeInvisible(final WebElement locator, final WebDriver driver) {
//        long s = System.currentTimeMillis();
        new WebDriverWait(driver, this.explicitWaitDefault)
                .until(ExpectedConditions.invisibilityOf(locator));
    }

    /**
     * 等待元素出現在POM上
     * <p>
     *  ==present 不同於 visible==
     *
     * @param driver
     * @param locator
     */
    public void waitForElementToBePresent(final By locator, final WebDriver driver) {
        new WebDriverWait(driver, this.explicitWaitDefault)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * 等待元素出現於畫面
     *
     * @param driver
     * @param element
     */
    public void waitForElementToBeVisible(final WebElement element, final WebDriver driver) {
//        long s = System.currentTimeMillis();
        new WebDriverWait(driver, this.explicitWaitDefault).until(ExpectedConditions.visibilityOf(element));
    }
    /**
     * 等待元素出現於畫面
     *
     * @param driver
     * @param element
     */
    public void waitForElementToBeVisible(final WebElement element,int second, final WebDriver driver) {
//        long s = System.currentTimeMillis();
        new WebDriverWait(driver,second).until(ExpectedConditions.visibilityOf(element));
    }
    /**
     * 等待特定秒數(time)直到元素出現於畫面
     *
     * @param element
     * @param driver
     * @param time
     */
    public void waitForElementToBeVisible(final IOSElement element, final WebDriver driver, int time) {
        new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementsToBeInvisible(final List<WebElement> elements, final WebDriver driver) {
//        final long s = System.currentTimeMillis();
        new WebDriverWait(driver, this.explicitWaitDefault)
                .until(ExpectedConditions.invisibilityOfAllElements(elements));
    }

    public void waitForElementToBeNotPresent(final By element, WebDriver driver) {
//        long s = System.currentTimeMillis();
        new WebDriverWait(driver, this.explicitWaitDefault)
                .until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(element)));
    }

    public void waitUntilNestedElementPresent(WebElement element, By locator, WebDriver driver) {
        new WebDriverWait(driver, explicitWaitDefault)
                .until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, locator));
    }

}
