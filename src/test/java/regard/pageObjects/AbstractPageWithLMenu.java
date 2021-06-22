package regard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import regard.util.WebDriverWrapper;

import java.util.Random;

public abstract class AbstractPageWithLMenu {
    protected WebDriverWrapper driver = WebDriverWrapper.getInstance();

    public void clickRandomMenuItem() {
        var currentMenuPath = new StringBuilder("//*[@id='lmenu']/ul[@class = 'menu ']/li");

        try {
            var currentMenuList = driver.findElements(By.xpath(currentMenuPath.toString()));
            while (true){
                int randomMenuIndex = new Random().nextInt(currentMenuList.size()) + 1;
                currentMenuPath.append(String.format("[%d]", randomMenuIndex));
                driver.click(driver.findElement(By.xpath(currentMenuPath.toString())));
                currentMenuPath.append("/ul/li");
                currentMenuList = driver.findElements(By.xpath(currentMenuPath.toString()));
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }

    public AbstractPageWithLMenu clickLMenuItem(String... menuItems) {
        StringBuilder xpath = new StringBuilder("//*[@id='lmenu']");
        WebElement el;
        for (String menuItem: menuItems) {
            xpath.append(String.format("//li[./a[text() = '%s']]", menuItem ));
            el = driver.findElement(By.xpath( xpath.toString() ));
            driver.click(el);
        }
        return this;
    }
}
