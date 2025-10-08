package part4.windows;

import com.demoqa.pages.alerts.BrowserWindowsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;

/**
 * Tests for switching to new tabs/windows and back on DemoQA → Browser Windows.
 * Uses BrowserWindowsPage helpers that handle window handles safely.
 */
public class WindowsTest extends BaseTest {

    @Test
    public void newTab_readSampleHeading_and_back() {
        BrowserWindowsPage page = new BrowserWindowsPage(driver).openByUrl();

        String heading = page.openNewTab_readHeading_andClose();
        System.out.println("New Tab heading: " + heading);

        Assert.assertTrue(heading != null && !heading.isBlank(),
                "Heading in new Tab should not be empty");
        Assert.assertTrue(heading.toLowerCase().contains("sample"),
                "Expected 'sample' in heading. Actual: " + heading);
    }

    @Test
    public void newWindow_readSampleHeading_and_back() {
        BrowserWindowsPage page = new BrowserWindowsPage(driver).openByUrl();

        String heading = page.openNewWindow_readHeading_andClose();
        System.out.println("New Window heading: " + heading);

        Assert.assertTrue(heading != null && !heading.isBlank(),
                "Heading in new Window should not be empty");
        Assert.assertTrue(heading.toLowerCase().contains("sample"),
                "Expected 'sample' in heading. Actual: " + heading);
    }

}
