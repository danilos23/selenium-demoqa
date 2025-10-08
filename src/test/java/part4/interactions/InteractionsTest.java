package part4.interactions;

import com.demoqa.pages.interactions.DroppablePage;
import com.demoqa.pages.elements.ButtonsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;

public class InteractionsTest extends BaseTest {

    @Test
    public void drag_and_drop_simple_should_show_Dropped() {
        var page = new DroppablePage(driver)
                .openByUrl()
                .goToSimpleTab()
                .dragToDrop_Robust();

        String text = page.getDropText();
        Assert.assertTrue(text.equalsIgnoreCase("Dropped!"),
                "Expected 'Dropped!' after drag&drop. Actual: " + text);
    }

    @Test
    public void double_and_right_click_should_show_messages() {
        var page = new ButtonsPage(driver).openByUrl()
                .doDoubleClick()
                .doRightClick()
                .doDynamicClick();

        Assert.assertTrue(page.getDoubleClickMessage().toLowerCase().contains("double"),
                "Double-click message missing. Actual: " + page.getDoubleClickMessage());
        Assert.assertTrue(page.getRightClickMessage().toLowerCase().contains("right"),
                "Right-click message missing. Actual: " + page.getRightClickMessage());
        Assert.assertTrue(page.getDynamicClickMessage().toLowerCase().contains("dynamic"),
                "Dynamic click message missing. Actual: " + page.getDynamicClickMessage());
    }
}
