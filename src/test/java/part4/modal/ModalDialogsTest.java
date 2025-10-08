package part4.modals;

import com.demoqa.pages.alerts.ModalDialogsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;

public class ModalDialogsTest extends BaseTest {

    @Test
    public void smallModal_open_read_and_close() {
        var modals = new ModalDialogsPage(driver).openByUrl()
                .openSmallModal();

        String text = modals.getModalText();
        Assert.assertTrue(text != null && !text.isBlank(), "Modal text should not be empty");

        // Close with different strategies to exercise flows
        modals.closeByX()
                .openSmallModal()
                .closeByCloseButton();
    }

    @Test
    public void largeModal_open_and_closeByEsc() {
        new ModalDialogsPage(driver).openByUrl()
                .openLargeModal()
                .closeByEsc();
    }
}
