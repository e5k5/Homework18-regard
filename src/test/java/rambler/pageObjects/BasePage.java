package rambler.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    private static final int TIMEOUT = 5; // для ожидания - таймаут в секундах
    private static final long SLEEP = 200; // для ожидания - интервал повторения в миллисекундах

    protected WebDriver driver;
    protected WebDriverWait wait;
    //локаторы и URL
    private String BASE_URL = "https://www.rambler.ru";
    private By startLoginButton = By.xpath("//button[*[text()='Вход']]");
    private By loginFrame = By.xpath("//iframe[contains(@src,'https://id.rambler.ru/login-20/')]");
    private By loginInputBox = By.xpath("//*[@id='login']");
    private By passwordInputBox = By.xpath("//*[@id='password']");
    private By loginButton = By.xpath("//button[*[text()='Войти']]");
    protected By loggedUserId = By.xpath("//button[contains(@data-cerber,'user::menu_open')]/*[2]");
    protected By userProfileLink = By.xpath("//*[contains(@data-cerber,'user::my_profile')]");


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT, SLEEP);
    }
    protected void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    protected void waitAndSwitchToFrame(By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }
    protected void waitAndSwitchToAnotherWindow() {
        driver.switchTo().window(driver
                .getWindowHandles().stream().filter( h -> !h.equals(driver.getWindowHandle()) ).findFirst().get());
    }
    protected WebElement waitToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    protected WebElement waitToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public BasePage loginAs(String id, String password){
        driver.get(BASE_URL);
        waitAndClick(startLoginButton);
        waitAndSwitchToFrame(loginFrame);
        waitToBeClickable(loginInputBox).sendKeys(id);
        waitToBeClickable(passwordInputBox).sendKeys(password);
        waitAndClick(loginButton);
        return this;
    }
    public ProfilePage openProfile() {
        waitToBeClickable(loggedUserId).click();
        waitToBeClickable(userProfileLink).click();
        waitAndSwitchToAnotherWindow();
        return new ProfilePage(driver);
    }
    public Boolean isLoggedUserId(String id) {
        return waitToBeClickable(loggedUserId).getText().equals(id);
    }

}
