package part4.screenshot;

import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;

import com.demoqa.pages.forms.PracticeFormPage;
import org.testng.Assert;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Element-only screenshots on DemoQA Practice Form:
 * Flow: Home -> Forms -> Practice Form -> take element screenshots (First Name & Female label)
 */
public class ElementScreenshotsTest extends BaseTest {

    @Test
    public void elementScreenshots_PracticeForm() {
        // 1) Navigate to Practice Form
        PracticeFormPage form = homePage
                .goToForms()            // Home -> FormsPage
                .clickPracticeForm()    // FormsPage -> PracticeFormPage
                .waitForPage();         // ensure the page is ready (first name visible)

        // 2) Take element-only screenshots using page-level wrappers (these call BasePage helper inside)
        Path firstNameShot = form.screenshotFirstName();
        Path femaleLabelShot = form.screenshotFemaleLabel();

        // 3) Assert files exist and are not empty
        assertNonEmptyPng(firstNameShot, "First Name input screenshot");
        assertNonEmptyPng(femaleLabelShot, "Female label screenshot");

        System.out.println("📸 First Name: " + firstNameShot.toAbsolutePath());
        System.out.println("📸 Female label: " + femaleLabelShot.toAbsolutePath());
    }

    // --- Helpers for file assertions ---

    /** Asserts the screenshot file exists and has a size > 0 bytes. */
    private void assertNonEmptyPng(Path path, String what) {
        Assert.assertNotNull(path, what + " path is null");
        Assert.assertTrue(Files.exists(path), what + " file was not created: " + path);
        try {
            Assert.assertTrue(Files.size(path) > 0, what + " file is empty: " + path);
        } catch (Exception io) {
            Assert.fail("Could not read " + what + " size: " + io.getMessage());
        }
    }
}
