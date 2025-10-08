package com.demoqa.pages.alerts;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import java.time.Duration;


public class BrowserWindowsPage extends BasePage {

    // Buttons that spawn new tab/window/message window
    private final By newTabBtn       = By.id("tabButton");
    private final By newWindowBtn    = By.id("windowButton");
    private final By newWindowMsgBtn = By.id("messageWindowButton");

    // In New Tab / New Window there is a heading with this id
    private final By sampleHeading   = By.id("sampleHeading"); // text: "This is a sample page"

    public BrowserWindowsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates directly to Browser Windows page and waits basic readiness.
     */
    public BrowserWindowsPage openByUrl() {
        driver.navigate().to("https://demoqa.com/browser-windows");
        // Minimal sync: ensure the main opener is visible
        waitVisible(newTabBtn, Duration.ofSeconds(10));
        return this;
    }

    /**
     * Clicks "New Tab", switches to the newly opened tab, reads the heading,
     * then closes the child and switches back to the original window.
     *
     * @return The text of #sampleHeading found in the new tab.
     */
    public String openNewTab_readHeading_andClose() {
        String original = driver.getWindowHandle();
        int before = driver.getWindowHandles().size();

        scrollToElementJS(newTabBtn);
        clickSmart(newTabBtn);

        // Wait for a new window handle to appear
        waitForWindowCount(before + 1, Duration.ofSeconds(10));

        // Switch to the child window
        String child = driver.getWindowHandles().stream()
                .filter(h -> !h.equals(original))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("New tab handle not found"));
        switchToWindow(child);

        // Read heading inside the child tab
        String text = waitVisible(sampleHeading, Duration.ofSeconds(5)).getText();

        // Close child and return to original window
        closeCurrentAndSwitchTo(original);
        return text;
    }

    /**
     * Clicks "New Window", switches to the new window, reads the heading,
     * then closes it and switches back to the original window.
     *
     * @return The text of #sampleHeading found in the new window.
     */
    public String openNewWindow_readHeading_andClose() {
        String original = driver.getWindowHandle();
        int before = driver.getWindowHandles().size();

        scrollToElementJS(newWindowBtn);
        clickSmart(newWindowBtn);
        waitForWindowCount(before + 1, Duration.ofSeconds(10));

        String child = driver.getWindowHandles().stream()
                .filter(h -> !h.equals(original))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("New window handle not found"));
        switchToWindow(child);

        String text = waitVisible(sampleHeading, Duration.ofSeconds(5)).getText();

        closeCurrentAndSwitchTo(original);
        return text;
    }

    /**
     * Clicks "New Window Message", switches to the new window, waits until <body>
     * has non-empty text (that page usually has plain body text, no #sampleHeading),
     * then closes it and returns to the original window.
     *
     * @return The non-empty text content of <body> in the message window.
     */
    public String openMessageWindow_readBody_andClose() {
        String original = driver.getWindowHandle();
        int before = driver.getWindowHandles().size();

        scrollToElementJS(newWindowMsgBtn);
        clickSmart(newWindowMsgBtn);
        waitForWindowCount(before + 1, Duration.ofSeconds(10));

        String child = driver.getWindowHandles().stream()
                .filter(h -> !h.equals(original))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Message window handle not found"));
        switchToWindow(child);

        String bodyText = waitForNonEmptyBodyText(Duration.ofSeconds(5));

        // Always try to close child and switch back to original
        try {
            closeCurrentAndSwitchTo(original);
        } catch (Exception ignored) {
            // If already closed for some reason, ensure we are on original
            switchToWindow(original);
        }
        return bodyText;
    }

    // --------------------------------------------------------------------------------------------
    // Internals
    // --------------------------------------------------------------------------------------------

    /**
     * Polls until document is ready and <body> has non-empty text.
     * Some lightweight message windows render body text without additional DOM.
     */
    private String waitForNonEmptyBodyText(Duration timeout) {
        long end = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < end) {
            try {
                // Ensure document is at least interactive/complete
                Object ready = js().executeScript("return document.readyState;");
                if (ready != null) {
                    String rs = ready.toString();
                    if ("interactive".equals(rs) || "complete".equals(rs)) {
                        String text = driver.findElement(By.tagName("body")).getText();
                        if (text != null && !text.isBlank()) return text;
                    }
                }
            } catch (NoSuchElementException | org.openqa.selenium.StaleElementReferenceException ignored) {
                // body not attached yet or re-rendered; keep polling
            }
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
        // Last-try: return body text even if blank (will let the caller assert)
        return driver.findElement(By.tagName("body")).getText();
    }
}
