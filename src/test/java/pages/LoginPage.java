package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait elementWait;  // for finding elements
    private final WebDriverWait manualWait;   // long, for CAPTCHA solved by hand

    private final By usernameField = By.cssSelector("input[type='email']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By errorMessage  = By.cssSelector(
            "#error-for-password, #error-for-username, [role='alert'], [aria-live='assertive']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.manualWait  = new WebDriverWait(driver, Duration.ofSeconds(120));
    }

    // Returns the first matching element that is actually displayed
    private WebElement visible(By locator, String name) {
        return elementWait
                .withMessage("Could not find a visible element: " + name)
                .until(d -> {
                    List<WebElement> list = d.findElements(locator);
                    for (WebElement e : list) {
                        try {
                            if (e.isDisplayed()) return e;
                        } catch (Exception ignored) { }
                    }
                    return null;
                });
    }

    public LoginPage open() {
        driver.get("https://www.linkedin.com/login");
        visible(usernameField, "email box");
        return this;
    }

    public void login(String email, String password) {
        WebElement user = visible(usernameField, "email box");
        user.clear();
        user.sendKeys(email);

        WebElement pass = visible(passwordField, "password box");
        pass.sendKeys(password);
        pass.sendKeys(Keys.ENTER);   // submits the form, no button locator needed
    }

    public boolean isLoggedIn() {
        // 120 seconds in case LinkedIn shows a CAPTCHA that you solve manually
        return manualWait.until(ExpectedConditions.urlContains("/feed"));
    }

    public boolean isErrorDisplayed() {
        return visible(errorMessage, "error message").isDisplayed();
    }

    public String getErrorText() {
        return visible(errorMessage, "error message").getText();
    }
}