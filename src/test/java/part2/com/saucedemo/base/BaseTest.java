package part2.com.saucedemo.base;

import com.base.BasePage;
import com.saucedemo.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class BaseTest {

    protected WebDriver driver;
    protected BasePage basePage;
    protected LoginPage loginPage;
    private final String url = "https://www.saucedemo.com";

    @BeforeClass
    public void setUp(){
        System.out.println("SETUP ejecutado");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);

        // 👇 ahora pasamos el driver por constructor
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
    }

    @AfterClass
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
        System.out.println("TEAR DOWN");
    }
}
