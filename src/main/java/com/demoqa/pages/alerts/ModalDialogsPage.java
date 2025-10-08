package com.demoqa.pages.alerts;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class ModalDialogsPage extends BasePage {

    // Buttons that open each modal
    private final By openSmallBtn = By.id("showSmallModal");
    private final By openLargeBtn = By.id("showLargeModal");

    // Visible modal & its parts (Bootstrap-style modals in DemoQA)
    private final By modalRoot   = By.cssSelector(".modal.fade.show");
    private final By modalHeader = By.cssSelector(".modal.fade.show .modal-header");
    private final By modalBody   = By.cssSelector(".modal.fade.show .modal-body");
    private final By closeX      = By.cssSelector(".modal.fade.show .close, .modal.fade.show [data-dismiss='modal']");
    private final By closeBtn    = By.cssSelector(".modal.fade.show .btn.btn-secondary"); // “Close”
    private final By okBtn       = By.cssSelector(".modal.fade.show .btn.btn-primary");   // “OK” (if present)
    private final By backdrop    = By.cssSelector(".modal-backdrop.show");
    private final By bodyTag     = By.tagName("body");

    public ModalDialogsPage(WebDriver driver) {
        super(driver);
    }

    /** Go directly to Modal Dialogs and wait for the open buttons to be visible. */
    public ModalDialogsPage openByUrl() {
        driver.navigate().to("https://demoqa.com/modal-dialogs");
        waitVisible(openSmallBtn, Duration.ofSeconds(10));
        return this;
    }

    /** Open small modal and wait until it's fully shown. */
    public ModalDialogsPage openSmallModal() {
        scrollToElementJS(openSmallBtn);
        clickSmart(openSmallBtn);
        waitModalOpen();
        return this;
    }

    /** Open large modal and wait until it's fully shown. */
    public ModalDialogsPage openLargeModal() {
        scrollToElementJS(openLargeBtn);
        clickSmart(openLargeBtn);
        waitModalOpen();
        return this;
    }

    /** Read current modal body text. */
    public String getModalText() {
        return waitVisible(modalBody, Duration.ofSeconds(10)).getText();
    }

    /** Close via the “X” (or any dismiss control in header). */
    public ModalDialogsPage closeByX() {
        if (isVisible(closeX, Duration.ofSeconds(2))) {
            clickSmart(closeX);
            waitModalClosed();
        }
        return this;
    }

    /** Close via the “Close” button. */
    public ModalDialogsPage closeByCloseButton() {
        if (isVisible(closeBtn, Duration.ofSeconds(2))) {
            clickSmart(closeBtn);
            waitModalClosed();
        }
        return this;
    }

    /** Close via the “OK” button (if present). */
    public ModalDialogsPage closeByOkButton() {
        if (isVisible(okBtn, Duration.ofSeconds(2))) {
            clickSmart(okBtn);
            waitModalClosed();
        }
        return this;
    }

    /** Click backdrop to close (if library allows it). */
    public ModalDialogsPage closeByBackdrop() {
        if (isVisible(backdrop, Duration.ofSeconds(2))) {
            clickSmart(backdrop);
            waitModalClosed();
        }
        return this;
    }

    /** Send ESC to body to close (if enabled). */
    public ModalDialogsPage closeByEsc() {
        driver.findElement(bodyTag).sendKeys(Keys.ESCAPE);
        waitModalClosed();
        return this;
    }

    // --------------------------------------- INTERNALS -------------------------------------------------------------

    /** Wait until modal root & backdrop are visible and body has the 'modal-open' class. */
    private void waitModalOpen() {
        waitVisible(modalRoot, Duration.ofSeconds(10));
        waitVisible(backdrop, Duration.ofSeconds(10));
        waitBodyHasClass("modal-open", Duration.ofSeconds(10));
        // optional: ensure header/body are visible too
        waitVisible(modalHeader, Duration.ofSeconds(10));
        waitVisible(modalBody, Duration.ofSeconds(10));
    }

    /** Wait until modal & backdrop are gone and body no longer has 'modal-open'. */
    private void waitModalClosed() {
        waitHidden(modalRoot, Duration.ofSeconds(10));
        waitHidden(backdrop, Duration.ofSeconds(10));
        waitBodyLacksClass("modal-open", Duration.ofSeconds(10));
    }

    /** Waits until the element is NOT visible (using isVisible in a small polling loop). */
    private void waitHidden(By locator, Duration timeout) {
        long end = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < end) {
            if (!isVisible(locator, Duration.ofMillis(200))) return;
            sleep(100);
        }
        throw new RuntimeException("Timeout waiting for element to be hidden: " + locator);
    }

    /** Waits until body has a class. */
    private void waitBodyHasClass(String klass, Duration timeout) {
        long end = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < end) {
            String classes = driver.findElement(bodyTag).getAttribute("class");
            if (classes != null && classes.contains(klass)) return;
            sleep(100);
        }
        throw new RuntimeException("Timeout waiting for body to gain class: " + klass);
    }

    /** Waits until body does NOT have a class. */
    private void waitBodyLacksClass(String klass, Duration timeout) {
        long end = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < end) {
            String classes = driver.findElement(bodyTag).getAttribute("class");
            if (classes == null || !classes.contains(klass)) return;
            sleep(100);
        }
        throw new RuntimeException("Timeout waiting for body to lose class: " + klass);
    }

    /** Tiny sleep helper. */
    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
