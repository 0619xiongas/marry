package com.misson;

import com.misson.API.Check;
import com.misson.Javacv.Tess4j;
import com.misson.Javacv.cvOperate;
import com.misson.Selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
/**
 * 1130 165
 */
public class Client {
    public static void main(String[] args) throws Exception{
        System.setProperty("webdriver.chrome.driver","C:/Program Files/Google/Chrome/Application/chromedriver.exe");
        WebDriver driver;
        ChromeOptions option = new ChromeOptions();

        option.addArguments("no-sandbox");
        option.setExperimentalOptions("useAutomationExtension", false);
        option.setExperimentalOptions("excludeSwitches", Collections.singletonList("enable-automation"));

        driver = new ChromeDriver(option);
        driver.get("https://so.gushiwen.cn/user/login.aspx");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Actions action = new Actions(driver);
        Thread.sleep(500);
        WebElement login_input = driver.findElement(By.id("email"));
        WebElement pwd_input = driver.findElement(By.id("pwd"));
        WebElement code_input = driver.findElement(By.id("code"));
        WebElement img = driver.findElement(By.id("imgCode"));
        WebElement button = driver.findElement(By.id("denglu"));
        do {
            login_input.clear();
            pwd_input.clear();
            code_input.clear();
            // 把imgae标签截图存在本地。
            String imgPath = SeleniumUtils.elementSnapShot(img);

            // 使用API接口对图片进行识别 得到验证码
            String code_API = Check.getResult(Check.checkFile(imgPath));

            // 使用后tess4j进行识别

            // 使用Javacv中包中的cvOperate中的方法对本地图片进行灰化和二值化;
            String cvImgPath = cvOperate.getGaryAndBinary(imgPath);
//        String binary = BinaryOperate.execute(imgPath);

            // 使用Tess4j中的识别方法对操作后的图片进行识别，cvImgPath
            String code_javacv = Tess4j.executeOcr(cvImgPath).trim();
//        String b = Tess4j.executeOcr(binary).trim();
//        System.out.println(b+"  binary");
            System.out.println(code_API + "    api ");
            System.out.println(code_javacv + "    javacv");


            login_input.click();
            login_input.sendKeys("1651871305@qq.com");
            Thread.sleep(1000);
            pwd_input.click();
            pwd_input.sendKeys("1651871305");
            Thread.sleep(1000);
            code_input.click();
            code_input.sendKeys(code_javacv);
            Thread.sleep(1000);
            action.moveToElement(button).perform();
            button.click();
            if(SeleniumUtils.isAlert(driver)) {
                driver.switchTo().alert().accept();
            }
        }while (!driver.getCurrentUrl().equals("https://so.gushiwen.cn/user/collect.aspx"));
    }
}
