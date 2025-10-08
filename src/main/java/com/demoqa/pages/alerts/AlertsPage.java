package com.demoqa.pages.alerts;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class AlertsPage extends BasePage {

    // Buttons on DemoQA Alerts page
    private final By alertBtn       = By.id("alertButton");        // immediate alert
    private final By timerAlertBtn  = By.id("timerAlertButton");    // appears after 5s
    private final By confirmBtn     = By.id("confirmButton");       // confirm()
    private final By promptBtn      = By.id("promtButton");         // prompt()  (sí, va sin 'p' en DemoQA)

    // Result texts (rendered in DOM after confirm/prompt)
    private final By confirmResult  = By.id("confirmResult");
    private final By promptResult   = By.id("promptResult");

    public AlertsPage(WebDriver driver) {
        super(driver);
    }

    /** Direct navigation (sin menú intermedio) y sync mínima. */
    public AlertsPage openByUrl() {
        driver.navigate().to("https://demoqa.com/alerts");
        waitVisible(alertBtn, Duration.ofSeconds(10));
        return this;
    }

    public AlertsPage waitForPage() {
        waitVisible(alertBtn, Duration.ofSeconds(10));
        return this;
    }

    // ---------- Immediate alert ----------
    public String clickImmediateAlert_andAccept() {
        scrollToElementJS(alertBtn);
        clickSmart(alertBtn);
        return acceptAlert(Duration.ofSeconds(3)); // returns alert text
    }

    // ---------- Timer alert (appears after ~5s) ----------
    public String clickTimerAlert_andAccept() {
        scrollToElementJS(timerAlertBtn);
        clickSmart(timerAlertBtn);
        return acceptAlert(Duration.ofSeconds(8)); // give it a bit more than 5s
    }

    // ---------- Confirm alert (OK/Cancel) ----------
    public String clickConfirm_andAccept_thenGetResult() {
        scrollToElementJS(confirmBtn);
        clickSmart(confirmBtn);
        acceptAlert(Duration.ofSeconds(3));
        return find(confirmResult).getText(); // e.g., "You selected Ok"
    }

    public String clickConfirm_andDismiss_thenGetResult() {
        scrollToElementJS(confirmBtn);
        clickSmart(confirmBtn);
        dismissAlert(Duration.ofSeconds(3));
        return find(confirmResult).getText(); // e.g., "You selected Cancel"
    }

    // ---------- Prompt alert (text input) ----------
    public String clickPrompt_sendKeys_andAccept_thenGetResult(String textToType) {
        scrollToElementJS(promptBtn);
        clickSmart(promptBtn);
        sendKeysToAlertAndAccept(textToType, Duration.ofSeconds(5));
        return find(promptResult).getText(); // e.g., "You entered <text>"
    }
}