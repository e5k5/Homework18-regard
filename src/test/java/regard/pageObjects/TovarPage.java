package regard.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TovarPage extends AbstractPageWithLMenu{
    public Logger log = LogManager.getLogger(TovarPage.class.getName());

    public TovarPage addToCart() {
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id='add_cart']"));
        driver.click(addToCartButton);

        String idPath = "//div[@class='goods_id']";
        String tovarID = driver.findElement(By.xpath(idPath)).getText().trim();
        CartPage.add(tovarID);
        return this;
    }

    public void openCartFromAddedTovarPage() {
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id='add_cart']"));
        if ( !addToCartButton.getText().equalsIgnoreCase("Перейти в корзину") ) {
            log.debug("Этот товар еще не добавлен в корзину - " + addToCartButton.getText());
            return;
        }
        driver.click(addToCartButton);
    }
}
