package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FeedPage;
import pages.LoginPage;
import pages.SearchResultsPage;
import utils.ConfigReader;

public class SearchTest extends BaseTest {

    @Test(description = "TC03 - Search shows results page")
    public void searchShowsResults() {
        LoginPage login = new LoginPage(driver).open();
        login.login(ConfigReader.get("email"), ConfigReader.get("password"));
        Assert.assertTrue(login.isLoggedIn(), "Login failed");

        SearchResultsPage results = new FeedPage(driver).search(ConfigReader.get("searchTerm"));

        Assert.assertTrue(results.isOnResultsPage(), "URL is not a search results URL");
        Assert.assertTrue(driver.getCurrentUrl().contains("keywords="),
                "Search term is missing from the URL");
        Assert.assertTrue(results.isFirstResultVisible(), "No result element visible");
    }
}