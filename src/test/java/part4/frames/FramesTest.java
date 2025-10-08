package part4.frames;

import com.demoqa.pages.alerts.FramesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;

public class FramesTest extends BaseTest {

    @Test
    public void frames_readHeadings() {
        FramesPage frames = new FramesPage(driver).openByUrl();

        String h1 = frames.getFrame1Heading();
        String h2 = frames.getFrame2Heading();

        Assert.assertTrue(h1.toLowerCase().contains("sample"),
                "Frame1 heading should contain 'sample'. Actual: " + h1);
        Assert.assertTrue(h2.toLowerCase().contains("sample"),
                "Frame2 heading should contain 'sample'. Actual: " + h2);
    }

}
