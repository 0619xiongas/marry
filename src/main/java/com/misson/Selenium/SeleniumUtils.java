package com.misson.Selenium;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.internal.WrapsDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SeleniumUtils {
    private WebDriver driver;
    private ChromeOptions options;

    /**
     * 初始化driver 做一些配置
     */
    public SeleniumUtils() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        this.options.addArguments("no-sandbox");
        this.options.setExperimentalOptions("useAutomationExtension", false);
        this.options.setExperimentalOptions("excludeSwitches", Collections.singletonList("enable-automation"));
        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
        this.driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * 对web页面的元素进行截图 保存在本地文件 并返回截图文件名
     */
    public static String elementSnapShot(WebElement element) {
        //创建全屏截图
        WrapsDriver wrapsDriver = (WrapsDriver) element;
        File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage image = ImageIO.read(screen);
            //获取元素的高度、宽度
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            //创建一个矩形使用上面的高度，和宽度
            Rectangle rect = new Rectangle(width, height);
            //元素坐标
            Point p = element.getLocation();
            BufferedImage img = image.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
            ImageIO.write(img, "png", screen);
            FileUtils.copyFile(screen, new File("src/main/resources/snap/webImg.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "src/main/resources/snap/webImg.png";
    }
    public static boolean isAlert(WebDriver driver){
        try{
            driver.switchTo().alert();
            return true;
        }catch (NoAlertPresentException e){
            return false;
        }
    }
}
