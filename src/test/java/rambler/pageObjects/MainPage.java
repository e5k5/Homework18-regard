package rambler.pageObjects;

import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage{

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public MainPage loginAs(String id, String password) {
        super.loginAs(id, password);
    return this;
    }

}
