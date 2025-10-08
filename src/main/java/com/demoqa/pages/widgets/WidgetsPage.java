package com.demoqa.pages.widgets;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WidgetsPage extends BasePage {

    private final By selectMenuItem = By.xpath("//li[@id='item-8']/span[text()='Select Menu']");
    private final By datePickerMenuItem = By.xpath("//li[@id='item-2']/span[text()='Date Picker']");

    public WidgetsPage(WebDriver driver){
        super(driver);
    }

    /**
     * Navigates from Widgets landing to the "Select Menu" page.
     * Returns the destination Page Object and waits for it to be ready.
     */
    public SelectMenuPage clickSelectMenu(){                                            // Transition Method
        // Close ad banner if present to avoid click interception
        closeFixedBannerIfPresent();

        // Ensure the menu item is in view, then click robustly (normal → retry → JS)
        scrollToElementJS(selectMenuItem);
        clickSmart(selectMenuItem);

        // Synchronize: wait for either the URL fragment or the "Select Menu" header
        var headerSelectMenu = By.cssSelector("div.main-header");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("/select-menu"),
                        ExpectedConditions.textToBePresentInElementLocated(headerSelectMenu, "Select Menu")
                )
        );

        // Hand back a ready-to-use page object (you can also call .waitForPage() here)
        return new SelectMenuPage(driver).waitForPage();
    }

    public DatePickerMenuPage clickDatePicker(){                                     // Transition Method
        // Close ad banner if present to avoid click interception
        closeFixedBannerIfPresent();

        // Ensure the menu item is in view, then click robustly (normal → retry → JS)
        scrollToElementJS(datePickerMenuItem);
        clickSmart(datePickerMenuItem);

        // Synchronize: wait for either the URL fragment or the "Select Menu" header
        var headerSelectMenu = By.cssSelector("div.main-header");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("/date-picker"),
                        ExpectedConditions.textToBePresentInElementLocated(headerSelectMenu, "Date Picker")
                )
        );

        // Hand back a ready-to-use page object (you can also call .waitForPage() here)
        return new DatePickerMenuPage(driver).waitForPage();
    }
}
