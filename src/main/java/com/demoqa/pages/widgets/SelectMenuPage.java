package com.demoqa.pages.widgets;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class SelectMenuPage extends BasePage {

    private final By standardMultiSelect = By.id("cars");

    public SelectMenuPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Ensures the page is ready to be used.
     * We wait for the <select id="cars"> to be visible (most reliable),
     * optionally you could also assert header text equals "Select Menu".
     */
    public SelectMenuPage waitForPage() {
        waitVisible(standardMultiSelect, Duration.ofSeconds(10));
        return this;
    }

    /** Selects an option in the multi-select by visible text. */
    public SelectMenuPage selectStandardMulti(String visibleText) {
        scrollToElementJS(standardMultiSelect);                          // Brings the control into view for stability
        selectByVisibleText(standardMultiSelect, visibleText);
        return this;                                                      // Fluent API
    }

    /** Selects an option in the multi-select by index. */
    public SelectMenuPage selectStandardMulti(int index) {
        scrollToElementJS(standardMultiSelect);
        selectByIndex(standardMultiSelect, index);
        return this;
    }

    /** Deselects all options in the multi-select (useful for resetting state). */
    public SelectMenuPage deselectAllStandardMulti() {
        dropdown(standardMultiSelect).deselectAll();
        return this;
    }

}
