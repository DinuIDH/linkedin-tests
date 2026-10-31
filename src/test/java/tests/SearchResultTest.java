package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FeedPage;
import pages.LoginPage;
import pages.SearchResultsPage;
import utils.ConfigReader;

import java.net.URI;

public class SearchResultTest extends BaseTest {

    @Test(description = "TC04 - Click first search result")
    public void clickFirstResult() throws Exception {
        LoginPage login = new LoginPage(driver).open();
        login.login(ConfigReader.get("email"), ConfigReader.get("password"));
        Assert.assertTrue(login.isLoggedIn(), "Login failed");

        SearchResultsPage results = new FeedPage(driver).search(ConfigReader.get("searchTerm"));
        Assert.assertTrue(results.isOnResultsPage(), "Not on results page");

        // Remember where the first result points (path only, no tracking parameters)
        String expectedPath = new URI(results.getFirstResultHref()).getPath().replaceAll("/$", "");
        System.out.println("TC04 clicking result with path: " + expectedPath);

        results.clickFirstResult();

        Assert.assertTrue(results.isOnProfileOrCompanyPage(), "Not on a profile/company page");
        Assert.assertTrue(results.waitForUrlContains(expectedPath),
                "Opened page does not match the clicked result. Expected path: " + expectedPath);
    }
}