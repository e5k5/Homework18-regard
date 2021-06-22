package regard.pageObjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import java.util.*;

public class CartPage extends AbstractPageWithLMenu {
    private static Map<String, Integer> cart = new HashMap<>();

    public CartPage(){
        cart.clear();
    }

    public static void add(String id){
        int count = cart.containsKey(id) ? (cart.get(id) + 1) : 1;
        cart.put(id, count);
    }

    public boolean isCartEmpty(){
        try {
            driver.findElements(By.xpath("//*[@id='table-basket']//tr[@class = 'goods_line']"));
        } catch (NoSuchElementException e) {
            return true;
        }
        return false;
    }

    public void checkGoodsInCart() throws NoSuchElementException {
//        var tcart = Map.of("355862", 1, "382111", 1);

        if (isCartEmpty()) {
            Assertions.assertTrue(cart.size() == 0, "Корзина не должна быть пустой");
            return;
        }

        List<WebElement> cartElementsList = driver.findElements(By.xpath("//*[@id='table-basket']//tr[@class = 'goods_line']"));
        Assertions.assertEquals(cart.size(), cartElementsList.size(), "Количество видов товаров в корзине не соответствует добавленным");

        for (var el: cartElementsList) {
            String id = driver.findNestedElement(el, By.xpath("./td[1]")).getText();
            int count = Integer.parseInt(driver.findNestedElement(el, By.xpath("./td/input[@type='text']")).getAttribute("value"));
            Assertions.assertEquals(cart.get(id), count, String.format("Количество товара ID:%s не соответствует добавленному", id));
        }
    }


}
