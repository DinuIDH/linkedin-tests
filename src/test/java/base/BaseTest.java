package base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();   // Selenium Manager downloads ChromeDriver automatically
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        // Automatic screenshot when a test fails
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            byte[] shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path folder = Paths.get("screenshots");
            Files.createDirectories(folder);
            Files.write(folder.resolve(result.getName() + "_" + System.currentTimeMillis() + ".png"), shot);
        }
        if (driver != null) {
            driver.quit();
        }
    }
}