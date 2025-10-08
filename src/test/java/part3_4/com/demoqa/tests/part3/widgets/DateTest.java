package part3_4.com.demoqa.tests.part3.widgets;

import org.testng.Reporter;
import part3_4.com.demoqa.tests.base.BaseTest;
import com.demoqa.pages.widgets.DatePickerMenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Month;

public class DateTest extends BaseTest {

    @Test
    public void testSelectingDate() {
        // Inputs you want to select
        String month = "December";
        String day   = "31";
        String year  = "2034";

        // Navigate: Home -> Widgets -> Date Picker
        // Then: open the calendar, select YEAR, select MONTH, click DAY
        DatePickerMenuPage datePicker = homePage
                .goToWidgets()
                .clickDatePicker()   // WidgetsPage -> DatePickerMenuPage (should return the page object)
                .waitForPage()       // make sure input is visible
                .openCalendar()      // open the popup first
                .selectYear(year)
                .selectMonth(month)
                .clickDay(day);      // click the exact day in the current month grid

        // Actual value shown in the input (MM/dd/yyyy)
        String actualDate = datePicker.getDate();

        // Build expected value in MM/dd/yyyy (e.g., 12/31/2034)
        String expectedDate = expectedDate(month, day, year);

        Assert.assertEquals(
                actualDate,
                expectedDate,
                "\nActual & Expected dates do not match" +
                        "\nActual:   " + actualDate +
                        "\nExpected: " + expectedDate
        );
        // If we got here, the assertion passed:
        Reporter.log("✅ Date selected correctly: " + actualDate, true); // 'true' also prints to console
    }

    /** Helper: builds MM/dd/yyyy from inputs like "December", "31", "2034". */
    private String expectedDate(String monthName, String day, String year) {
        int mm = Month.valueOf(monthName.toUpperCase()).getValue(); // December -> 12
        int dd = Integer.parseInt(day.trim());                      // "01" -> 1
        return String.format("%02d/%02d/%s", mm, dd, year.trim());  // 12/31/2034
    }
}
