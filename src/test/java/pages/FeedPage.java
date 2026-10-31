package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FeedPage extends BasePage {

    private final By searchInput = By.cssSelector(
            "input[placeholder='Search'], input[aria-label='Search'], "
          + "input[role='combobox'], input[type='search']");
    private final By searchIcon = By.cssSelector(
            "button[aria-label*='Search'], a[aria-label*='Search']");

    public FeedPage(WebDriver driver) {
        super(driver);
    }

    // Finds the search box, opening it with the magnifier icon if needed
    private WebElement findSearchBox() {
        try {
            return visible(searchInput, "search box", 10);
        } catch (Exception firstTry) {
            visible(searchIcon, "search icon", 10).click();
            return visible(searchInput, "search box", 15);
        }
    }

    public boolean isSearchBoxVisible() {
        return findSearchBox().isDisplayed();
    }

    public SearchResultsPage search(String term) {
        // Let the feed finish its first re-render before touching the box
        try { Thread.sleep(2000); } catch (InterruptedException ignored) { }

        // Retry the whole sequence if the element goes stale
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                findSearchBox().click();
                WebElement box = findSearchBox();
                box.clear();
                box.sendKeys(term);
                findSearchBox().sendKeys(Keys.ENTER);
                return new SearchResultsPage(driver);
            } catch (StaleElementReferenceException e) {
                if (attempt == 3) throw e;
            }
        }
        return new SearchResultsPage(driver);
    }
}