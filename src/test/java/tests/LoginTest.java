package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test(description = "TC01 - Valid login")
    public void validLogin() {
        LoginPage login = new LoginPage(driver).open();
        login.login(ConfigReader.get("email"), ConfigReader.get("password"));
        Assert.assertTrue(login.isLoggedIn(), "Feed page was not reached");
    }

    @Test(description = "TC02 - Invalid password shows error")
    public void invalidLogin() {
        LoginPage login = new LoginPage(driver).open();
        login.login(ConfigReader.get("email"), ConfigReader.get("wrongPassword"));
        Assert.assertTrue(login.isErrorDisplayed(), "Error message not displayed");
        String text = login.getErrorText();
        System.out.println("TC02 error text: " + text);
        Assert.assertFalse(text.trim().isEmpty(), "Error message is empty");
    }
}