package part3_4.com.demoqa.tests.base;

import com.demoqa.pages.HomePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import static Part1.FirstSeleniumTest.driver;
import static com.base.BasePage.delay;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    private final String DEMOQA_URL = "https://demoqa.com/";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void loadApplication() {
        driver.get(DEMOQA_URL);
        // DRIVER
        homePage = new HomePage(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        delay(3000);
        if (driver != null) {
            driver.quit();
        }
    }

}
