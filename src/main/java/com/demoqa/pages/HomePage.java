package com.demoqa.pages;

import com.base.BasePage;
import com.demoqa.pages.elements.ElementsPage;
import com.demoqa.pages.forms.FormsPage;
import com.demoqa.pages.widgets.WidgetsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    // Card completo (no el <h5>)
    private final By elementsCard = By.xpath("//h5[normalize-space()='Elements']" +
                                                "/ancestor::div[contains(@class,'top-card')]");
    private final By formsCard = By.xpath("//h5[normalize-space()='Forms']" +
                                            "/ancestor::div[contains(@class,'top-card')]");
    private final By widgetsCard = By.xpath("//h5[normalize-space()='Widgets']" +
                                                "/ancestor::div[contains(@class,'top-card')]");

    public HomePage(WebDriver driver) { super(driver); }

    public ElementsPage goToElements() {
        closeFixedBannerIfPresent();                 // evita overlay
        scrollToElementJS(elementsCard);
        clickSmart(elementsCard);

        // Espera header o URL; si no llega, navega por URL como fallback
        var header = By.xpath("//div[@class='main-header' and normalize-space()='Elements']");
        if (!isVisible(header, Duration.ofSeconds(3))) {
            driver.navigate().to("https://demoqa.com/elements");   // fallback
        }

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                        ExpectedConditions.urlContains("/elements"));

        return new ElementsPage(driver);
    }

    public FormsPage goToForms() {
        closeFixedBannerIfPresent();
        scrollToElementJS(formsCard);
        clickSmart(formsCard);

        var header = By.xpath("//div[@class='main-header' and normalize-space()='Forms']");
        if (!isVisible(header, Duration.ofSeconds(3))) {
            driver.navigate().to("https://demoqa.com/forms");
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                        ExpectedConditions.urlContains("/forms"));

        return new FormsPage(driver);
    }


    public WidgetsPage goToWidgets() {
        // 1) Close DemoQA's fixed ad banner if present to avoid click interception.
        closeFixedBannerIfPresent();

        // 2) Bring the "Widgets" card into view to avoid off-viewport click issues.
        scrollToElementJS(widgetsCard);

        // 3) Click robustly: normal click → scroll+retry → JS click as fallback.
        clickSmart(widgetsCard);

        // 4) Define multiple signals that indicate the destination view is ready.
        //    In /widgets the main header usually shows "Accordian" (first sub-module),
        //    so don't hardcode "Widgets" here.
        By mainHeader     = By.cssSelector("div.main-header"); // we'll check its text
        By leftPanelOpen  = By.xpath("//div[contains(@class,'element-list') and contains(@class,'show')]");
        By accordianItem  = By.xpath("//span[normalize-space()='Accordian']");

        // 5) Hard-sync: wait until EITHER the URL contains "/widgets"
        //    OR the left panel/Accordian elements are visible
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/widgets"),
                    ExpectedConditions.textToBePresentInElementLocated(mainHeader, "Accordian"),
                    ExpectedConditions.visibilityOfElementLocated(leftPanelOpen),
                    ExpectedConditions.visibilityOfElementLocated(accordianItem)
            ));
        } catch (TimeoutException e) {
            // 6) Fallback: if the click didn't navigate, go directly by URL and wait again.
            driver.navigate().to("https://demoqa.com/widgets");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/widgets"),
                    ExpectedConditions.textToBePresentInElementLocated(mainHeader, "Accordian"),
                    ExpectedConditions.visibilityOfElementLocated(leftPanelOpen),
                    ExpectedConditions.visibilityOfElementLocated(accordianItem)
            ));
        }

        // 7) Return the destination Page Object wired with the same driver.
        return new WidgetsPage(driver);
    }


}


