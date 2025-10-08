package Part1;

import com.google.common.annotations.VisibleForTesting;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class FirstSeleniumTest {

    public static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @AfterAll
    public static void tearDown(){
        // driver.quit();          // Close every window
        //driver.close();       // Just close the actual window
    }

    @Test
    public void testLoggingIntoApplication() throws InterruptedException {
        Thread.sleep(2000);
        WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("Admin");

        var password = driver.findElement(By.name("password"));
        password.sendKeys("admin123");

        driver.findElement(By.tagName("button")).click();
        Thread.sleep(2000);
        String actualResult = driver.findElement(By.tagName("h6")).getText();
        String expectedResult = "Dashboard";
        Assert.assertEquals(actualResult, expectedResult, "El título no coincide");
        // The third parameter just appears if it fails
    }

}
