package com.demoqa.pages.alerts;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class FramesPage extends BasePage {

    // Two iframes in this view
    private final By frame1 = By.id("frame1");
    private final By frame2 = By.id("frame2");

    // Inside each frame there is a heading with this id
    private final By frameHeading = By.id("sampleHeading");

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    /** Direct navigation – handy for isolated tests. */
    public FramesPage openByUrl() {
        driver.navigate().to("https://demoqa.com/frames");
        // Minimal sync: first frame visible
        waitVisible(frame1, Duration.ofSeconds(10));
        return this;
    }

    /** Reads the H1 text from frame1. */
    public String getFrame1Heading() {
        switchToFrame(frame1, Duration.ofSeconds(10));                 // enter frame1
        String text = waitVisible(frameHeading, Duration.ofSeconds(5)) // now searching INSIDE frame1
                .getText();
        switchToDefaultContent();                                      // important: go back out
        return text;
    }

    /** Reads the H1 text from frame2. */
    public String getFrame2Heading() {
        switchToFrame(frame2, Duration.ofSeconds(10));
        String text = waitVisible(frameHeading, Duration.ofSeconds(5)).getText();
        switchToDefaultContent();
        return text;
    }
}
