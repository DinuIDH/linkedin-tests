package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage extends BasePage {

    // Limited to the main results area, so the header's own profile link is not matched
    private final By firstResultLink = By.cssSelector(
            "main a[href*='/in/'], main a[href*='/company/'], "
          + "[role='main'] a[href*='/in/'], [role='main'] a[href*='/company/']");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnResultsPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.urlContains("/search/results/"));
    }

    public boolean isFirstResultVisible() {
        return visible(firstResultLink, "first search result").isDisplayed();
    }

    public String getFirstResultHref() {
        return visible(firstResultLink, "first search result").getAttribute("href");
    }

    public void clickFirstResult() {
        WebElement link = visible(firstResultLink, "first search result");
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", link);
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
    }

    public boolean waitForUrlContains(String path) {
        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.urlContains(path));
    }

    public boolean isOnProfileOrCompanyPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("/in/"),
                        ExpectedConditions.urlContains("/company/")));
    }
}