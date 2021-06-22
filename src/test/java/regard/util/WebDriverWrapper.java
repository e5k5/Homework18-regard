package regard.util;

import com.google.common.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class WebDriverWrapper {
    private static WebDriverWrapper instance;
    private static Logger log = LogManager.getRootLogger();

    private WebDriver driver;
    private WebDriverWait wait;


    private WebDriverWrapper() {
        var options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        System.setProperty("webdriver.chrome.driver","bin/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", Resources.getResource("chromedriver.exe").getPath());

        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
        this.wait = new WebDriverWait(driver,5, 500);

    }

    public static WebDriverWrapper getInstance() {
        if (instance == null){
            instance = new WebDriverWrapper();
            log.debug("Создан экземпляр WebDriverWrapper");
        }
        return instance;
    }

    public void get(String url) {
        log.debug(String.format("Открываю страницу: %s", url));
        driver.get(url);
        log.debug(String.format("Открыл страницу: %s", url));
    }

    public void close() {
        log.debug(String.format("Закрываю вкладку: %s", driver.getCurrentUrl()));
        driver.close();
        driver.getWindowHandles().size()
        log.debug("Вкладка закрыта");
    }
    public void quit() {
        log.debug(String.format("Закрываю все вкладки, текущая: %s", driver.getCurrentUrl()));
        driver.quit();
        instance = null;//обнуляем, чтобы следующий запрос getinstance() создал новый экземпляр драйвера
        log.debug("Все вкладки закрыты");
    }

    public WebElement findElement(By locator) throws NoSuchElementException {
        log.debug(String.format("Ищу элемент по локатору: %s", locator));
        WebElement webElement;
        try {
            webElement = wait.until( ExpectedConditions.presenceOfElementLocated(locator) );
            // подсветка найденного элемента
            ((JavascriptExecutor) driver).executeScript("arguments[0]['style']['box-shadow']='0 0 10px 1px aqua';", webElement);
        } catch (TimeoutException e){
            log.error(String.format("Не найден элемент по локатору: %s", locator));
            throw new NoSuchElementException(String.format("Не найден элемент по локатору: %s", locator));
        }
        log.debug(String.format("Найден элемент по локатору: %s", locator));
        return webElement;
    }

    public List<WebElement> findElements(By locator) throws NoSuchElementException {
        log.debug(String.format("Ищу элементы по локатору: %s", locator));
        List<WebElement> webElementsList;
        try {
            webElementsList = wait.until( ExpectedConditions.presenceOfAllElementsLocatedBy(locator) );
            webElementsList.stream() // подсветка найденных элементов
                .forEach(el -> ((JavascriptExecutor) driver).executeScript("arguments[0]['style']['box-shadow']='0 0 10px 1px aqua';", el));
        } catch (TimeoutException e){
            log.error(String.format("Не найдены элементы по локатору: %s", locator));
            throw new NoSuchElementException(String.format("Не найдены элементы по локатору: %s", locator));
        }
        log.debug(String.format("Найдены элементы по локатору: %s", locator));
        return webElementsList;
    }

    public WebElement findNestedElement(WebElement rootElement, By locator) {
        log.debug(String.format("Ищу вложенный элемент по локатору: %s", locator));
        WebElement webElement;

        try {
            webElement = wait.until( ExpectedConditions.presenceOfNestedElementLocatedBy(rootElement, locator) );
            ((JavascriptExecutor) driver).executeScript("arguments[0]['style']['box-shadow']='0 0 10px 1px aqua';", webElement);
        } catch (TimeoutException e){
            log.error(String.format("Не найден элемент внутри %s по локатору: %s", rootElement, locator));
            throw new NoSuchElementException(String.format("Не найден элемент внутри %s по локатору: %s", rootElement, locator));
        }
        log.debug(String.format("Найден вложенный элемент по локатору: %s", locator));
        return webElement;
    }
//    public List<WebElement> findNestedElements(WebElement rootElement, By locator) throws NoSuchElementException {
//        log.debug(String.format("Ищу вложенные элементы по локатору: %s", locator));
//        List<WebElement> webElementsList;
//        try {
//            webElementsList = wait.until( ExpectedConditions.presenceOfNestedElementsLocatedBy((By) rootElement, locator) );
//            webElementsList.stream() // подсветка найденных элементов
//                    .forEach(el -> ((JavascriptExecutor) driver).executeScript("arguments[0]['style']['box-shadow']='0 0 10px 1px aqua';", el));
//        } catch (TimeoutException e){
//            log.error(String.format("Не найдены элементы внутри %s по локатору: %s", rootElement, locator));
//            throw new NoSuchElementException(String.format("Не найдены элементы по локатору: %s", locator));
//        }
//        log.debug(String.format("Найдены вложенные элементы по локатору: %s", locator));
//        return webElementsList;
//    }

    public void click(WebElement webElement) {
        log.debug(String.format("Клик на элемент: %s", webElement));
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        //прокручиваем элемент в область видимости браузера
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", webElement);
        webElement.click();
        log.debug(String.format("Кликнут элемент: %s", webElement));

    }


}
