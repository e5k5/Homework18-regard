package regard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class GroupPage extends AbstractPageWithLMenu {

    public GroupPage addToCartByIndex(int index) {
        String addToCartButtonPath = String.format("//*[@id='hits']//div[@class='block'][%d]//*[@class='cart']", index);
        WebElement addToCartButton = driver.findElement(By.xpath(addToCartButtonPath));
        driver.click(addToCartButton);

        String idPath = String.format("//*[@id='hits']//div[@class='block'][%d]//*[@class='code']", index);
        String tovarID = driver.findElement(By.xpath(idPath)).getText().substring(4).trim();
        CartPage.add(tovarID);

        return this;
    }

    @Override
    public GroupPage clickLMenuItem(String... menuItems) {
        super.clickLMenuItem(menuItems);
        return this;
    }

    public void openTovarPageByIndex(int index) {
        String tovarPagePath = String.format("//*[@id='hits']//div[@class='block'][%d]//*[@class='header']", index);
        WebElement tovarPageLink = driver.findElement(By.xpath(tovarPagePath));
        driver.click(tovarPageLink);
    }
}
