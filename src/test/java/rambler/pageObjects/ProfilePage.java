package rambler.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {
    private String userProfilePageURL = "https://id.rambler.ru/account/profile";
    private By userProfileGeneralSection = By.xpath("//section[@data-profile-section='general']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public Boolean isProfileOpened() {
        wait.until(ExpectedConditions.urlToBe(userProfilePageURL));
        return waitToBeVisible(userProfileGeneralSection).isDisplayed();
    }
}
