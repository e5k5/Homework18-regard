package regard.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TovarPage extends AbstractPageWithLMenu{
    public Logger log = LogManager.getLogger(TovarPage.class.getName());

    public TovarPage addToCart() {
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id='cart_btn']"));
        driver.click(addToCartButton);

        String idPath = "//div[@class='goods_id']";
        String tovarID = driver.findElement(By.xpath(idPath)).getText().trim();
        CartPage.add(tovarID);
        return this;
    }

    public void openCartFromAddedTovarPage() {
        log.debug("Проверяем состояние кнопки 'Перейти в корзину' по атрибуту 'class'");
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id='cart_btn' and contains(@class,'btn_blue')]"));
        log.debug("Этот товар уже был добавлен в корзину - проверили состояние кнопки 'Перейти в корзину' по атрибуту 'class' - " + addToCartButton.getAttribute("class"));
        driver.click(addToCartButton);
    }
}
