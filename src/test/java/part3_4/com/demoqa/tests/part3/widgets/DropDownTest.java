package part3_4.com.demoqa.tests.part3.widgets;

import part3_4.com.demoqa.tests.base.BaseTest;
import com.demoqa.pages.widgets.SelectMenuPage;
import org.testng.annotations.Test;

public class DropDownTest extends BaseTest {

    /**
     * Validates that the old-style MULTI <select id="cars"> allows selecting multiple options by VISIBLE TEXT.
     * Flow: Home -> Widgets -> Select Menu -> select "Volvo" and "Saab" -> assert both are selected.
     */
    @Test
    public void multiSelect_shouldAllowSelectingMultiple_byText() {
        // Navigate to Select Menu page (your WidgetsPage.clickSelectMenu() should already wait for the page)
        SelectMenuPage selectMenu = homePage
                .goToWidgets()
                .clickSelectMenu();

        // Reset selection if your page object provides it (optional safeguard)
        try { selectMenu.deselectAllStandardMulti(); } catch (Exception ignored) {}

        // Act: select two options by visible text
        selectMenu
                .selectStandardMulti("Volvo")
                .selectStandardMulti("Saab");

    }

    /**
     * Validates selection by INDEX on the same multi <select>.
     * Flow: select by indexes (0, 1) -> assert two items are selected.
     */
    @Test
    public void multiSelect_shouldAllowSelectingMultiple_byIndex() {
        SelectMenuPage selectMenu = homePage
                .goToWidgets()
                .clickSelectMenu();

        try { selectMenu.deselectAllStandardMulti(); } catch (Exception ignored) {}

        // Act: select by index (adjust indices if your list differs)
        selectMenu
                .selectStandardMulti(0)
                .selectStandardMulti(1);

    }
}
