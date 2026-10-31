package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Waits up to 'seconds' for the first element matching the locator that is displayed
    protected WebElement visible(By locator, String name, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
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

    protected WebElement visible(By locator, String name) {
        return visible(locator, name, 30);
    }
}