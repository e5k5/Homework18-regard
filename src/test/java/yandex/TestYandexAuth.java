package yandex;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestYandexAuth {
    static  WebDriver driver;
    static  WebDriverWait wait;

    @BeforeAll
    static void setUp(){
        var options = new ChromeOptions();

        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        System.setProperty("webdriver.chrome.driver", "bin/chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5, 200);
    }

    @Test
    void testAuthWithoutId() {
        driver.get("https://yandex.ru/");
        driver
            .findElement(By.xpath("//div[@aria-label='Авторизация']//a[contains(@href,'yandexid')]"))
            .click();
        driver
            .findElement(By.xpath("//button[@type='submit']"))
            .click();

        WebElement errorHint =  wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.xpath("//div[contains(@class,'Textinput-Hint_state_error')]")));

        Assertions.assertEquals("Логин не указан", errorHint.getText());
    }


    @AfterAll
    static void tearDown() {
        driver.close();
    }

}
