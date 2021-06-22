package rambler;

import com.google.common.io.Resources;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rambler.pageObjects.MainPage;


public class testRambler {
    private static final String id = "2775848@rambler.ru";
    private static final String password = "1";

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private WebDriverWait wait;

    @BeforeAll
    static void setUp(){
        System.setProperty("webdriver.chrome.driver", Resources.getResource("chromedriver.exe").getPath());
    }
    @BeforeEach
    public void createDriver(){
        driver.set( new ChromeDriver( new ChromeOptions()
                .setExperimentalOption("excludeSwitches", new String[]{"enable-automation"})
        ));
        wait = new WebDriverWait(driver.get(), 5, 200);
        driver.get().manage().window().maximize();
    }

    @Test
    @DisplayName("Вход зарегистрированного пользователя")
    public void testLogin() {
        Assertions.assertTrue(
                new MainPage(driver.get())
                        .loginAs(id, password)
                        .isLoggedUserId(id)
        );
    }

    @Test
    @DisplayName("Открытие профиля пользователя")
    public void testOpenProfile() {
        Assertions.assertTrue(
                new MainPage(driver.get())
                        .loginAs(id, password)
                        .openProfile()
                        .isProfileOpened()
        );
    }


    @AfterEach
    public void closeDriver() {
        driver.get().quit();
    }
}
