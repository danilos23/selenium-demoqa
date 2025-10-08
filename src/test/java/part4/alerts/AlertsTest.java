package part4.alerts;

import com.demoqa.pages.alerts.AlertsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;

public class AlertsTest extends BaseTest {

    @Test
    public void immediateAlert_accept() {
        AlertsPage alerts = new AlertsPage(driver).openByUrl().waitForPage();

        String alertText = alerts.clickImmediateAlert_andAccept();
        // DemoQA shows something like "You clicked a button"
        Assert.assertTrue(alertText != null && !alertText.isBlank(),
                "Alert text should not be empty");
    }

    @Test
    public void timerAlert_accept_afterDelay() {
        AlertsPage alerts = new AlertsPage(driver).openByUrl().waitForPage();

        String alertText = alerts.clickTimerAlert_andAccept();
        Assert.assertTrue(alertText != null && !alertText.isBlank(),
                "Timer alert text should not be empty");
    }

    @Test
    public void confirmAlert_ok_and_cancel() {
        AlertsPage alerts = new AlertsPage(driver).openByUrl().waitForPage();

        String okResult = alerts.clickConfirm_andAccept_thenGetResult();
        Assert.assertTrue(okResult.toLowerCase().contains("ok"),
                "Expected confirm result to mention OK. Actual: " + okResult);

        String cancelResult = alerts.clickConfirm_andDismiss_thenGetResult();
        Assert.assertTrue(cancelResult.toLowerCase().contains("cancel"),
                "Expected confirm result to mention Cancel. Actual: " + cancelResult);
    }

    @Test
    public void promptAlert_type_and_accept() {
        AlertsPage alerts = new AlertsPage(driver).openByUrl().waitForPage();

        String typed = "Dani";
        String result = alerts.clickPrompt_sendKeys_andAccept_thenGetResult(typed);
        Assert.assertTrue(result.contains(typed),
                "Expected prompt result to contain typed text. Actual: " + result);
    }
}

