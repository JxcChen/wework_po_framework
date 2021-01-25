package com.wework.page_action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * ClassName: BasePageAction
 * date: 2021/1/15 18:14
 * @author JJJJ
 * Description:
 */
public class BasePageAction {

    public static WebDriver webDriver;
    public static By locator;
    public WebElement currentElement;


    /**
     * 通用获取元素方法
     * @param locator 元素定位符
     */

    public WebElement findElementVisibility(By locator) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement findElementClickable(By locator) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void clickElement(By locator) {
        findElementClickable(locator).click();
    }

    public void sendKey(By locator, String key) {
        findElementVisibility(locator).sendKeys(key);
    }
}
