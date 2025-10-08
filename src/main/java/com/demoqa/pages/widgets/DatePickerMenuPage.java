package com.demoqa.pages.widgets;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class DatePickerMenuPage extends BasePage {

    // Input that opens the calendar (Month/Year picker)
    private final By selectDateField = By.id("datePickerMonthYearInput");

    // Native <select> elements inside the React Datepicker header
    private final By monthDropDown = By.className("react-datepicker__month-select");
    private final By yearDropDown  = By.className("react-datepicker__year-select"); // CSS also works

    public DatePickerMenuPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Ensures the page/datepicker is ready to interact.
     * We wait for the input to be visible.
     */
    public DatePickerMenuPage waitForPage() {
        waitVisible(selectDateField, Duration.ofSeconds(10));
        return this;
    }

    /**
     * Builds a locator for a day cell inside the calendar.
     * - Avoids selecting days from the previous/next month by excluding the 'outside-month' class.
     * - Uses normalize-space on the cell text so " 15 " and "15" match the same.
     */
    private By dayCell(String day) {
        String trimmed = day.strip(); // normalize input like " 01" -> "01" (we'll normalize below)
        // Many calendars render day text without leading zeros, so use the int value to be safe.
        try {
            int d = Integer.parseInt(trimmed);
            trimmed = String.valueOf(d); // "01" -> "1"
        } catch (NumberFormatException ignore) { /* keep original if not numeric */ }

        return By.xpath(
                "//div[contains(@class,'react-datepicker__day')" +
                        " and not(contains(@class,'outside-month'))" +
                        " and normalize-space(text())='" + trimmed + "']"
        );
    }

    /** Opens the calendar by clicking the input (id="datePickerMonthYearInput"). */
    public DatePickerMenuPage openCalendar() {
        scrollToElementJS(selectDateField);
        clickSmart(selectDateField);
        // Optional: wait a beat for the calendar pop-up to render
        waitVisible(monthDropDown, Duration.ofSeconds(5));
        return this;
    }

    /** Clicks a specific visible day in the current month view. */
    public DatePickerMenuPage clickDay(String day) {
        clickSmart(dayCell(day));
        return this;
    }

    /** Returns true if the given day exists in the current month grid (and is not an outside-month cell). */
    public boolean isDayInCurrentMonth(String day) {
        return !driver.findElements(dayCell(day)).isEmpty();
    }

    /** Returns the current value of the input (e.g., "09/17/2025"). */
    public String getDate() {
        return find(selectDateField).getAttribute("value");
    }

    /** Selects a month by its visible text in the native <select> (e.g., "September"). */
    public DatePickerMenuPage selectMonth(String month) {
        selectByVisibleText(monthDropDown, month);
        return this;
    }

    /** Selects a year by its visible text in the native <select> (e.g., "2025"). */
    public DatePickerMenuPage selectYear(String year) {
        selectByVisibleText(yearDropDown, year);
        return this;
    }

    /**
     * High-level helper: opens the calendar and picks the given (year, month, day).
     * Example: pickDate("2025", "September", "17");
     */
    public DatePickerMenuPage pickDate(String year, String month, String day) {
        openCalendar();
        selectMonth(month);
        selectYear(year);
        clickDay(day);
        return this;
    }

}
