package regard;

import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import regard.pageObjects.CartPage;
import regard.pageObjects.GroupPage;
import regard.pageObjects.MainPage;
import regard.pageObjects.TovarPage;
import regard.util.WebDriverWrapper;

import static org.junit.jupiter.api.Assertions.fail;


public class TestRegard {

    static MainPage mainPage;
    static GroupPage groupPage;
    static TovarPage tovarPage;
    static CartPage cartPage;

    @BeforeEach
     void setUp(){
        mainPage = new MainPage("https://www.regard.ru/");
        groupPage = new GroupPage();
        tovarPage = new TovarPage();
        cartPage = new CartPage();
    }

    @Test
    @DisplayName("Добавляем несколько товаров в корзину и проверяем, что они добавились")
    void testAddToCart(){
        try {
            mainPage
                .open()
                .clickLMenuItem("Материнские платы", "Intel Socket 1200");
            groupPage
                .addToCartByIndex(5)
                .clickLMenuItem("Корпуса", "AEROCOOL")
                .clickLMenuItem("Материнские платы", "Intel Socket 1200")
                .addToCartByIndex(4)
                .openTovarPageByIndex(10);
            tovarPage
                .addToCart()
                .openCartFromAddedTovarPage();
            cartPage
                .checkGoodsInCart();

        } catch (NoSuchElementException e){
            fail(e.getMessage());
        }
    }
    @Test
    @DisplayName("Добавляем случайный товар в корзину и проверяем, что он добавился")
    void testAddRandomToCard(){
        mainPage
            .open()
            .clickRandomMenuItem();
        groupPage
            .openTovarPageByIndex(1);
        tovarPage
            .addToCart()
            .openCartFromAddedTovarPage();
        cartPage
            .checkGoodsInCart();
    }

    @AfterEach
    void tearDown(){
        WebDriverWrapper.getInstance().quit();
    }

}
