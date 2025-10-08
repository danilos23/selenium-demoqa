package com.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasePage {

    protected final WebDriver driver;
    protected final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    // TimeStamp:
    private static final DateTimeFormatter TS =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");


    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    /* ----------------------------------- Explicit Waits --------------------------------------------------------- */

    protected WebElement waitPresent(By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement waitVisible(By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitGone(By locator, Duration timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitUrlContains(String fragment, Duration timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.urlContains(fragment));
    }
    /* ------------------------------------ Utilities ------------------------------------------------------------- */

    public static void delay(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch(InterruptedException exc){
            Thread.currentThread().interrupt();
        }
    }

    protected boolean isVisible(By locator, Duration timeout) {
        try {
            return waitVisible(locator, timeout).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void closeFixedBannerIfPresent() {
        By closeBan = By.id("close-fixedban");
        try {
            var els = driver.findElements(closeBan);
            if (!els.isEmpty() && els.getFirst().isDisplayed()) {
                els.getFirst().click();
            }
        } catch (Exception ignored) {}
    }

    /** Element-only screenshot (PNG) */
    protected Path saveElementScreenshot(WebElement element, String baseName) {
        String stamp = LocalDateTime.now().format(TS);
        Path dir = Paths.get("target", "screenshots", "elements");
        try {
            Files.createDirectories(dir);
            File src = element.getScreenshotAs(OutputType.FILE);
            Path dest = dir.resolve(baseName + "_" + stamp + ".png");
            Files.move(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            return dest;
        } catch (IOException io) {
            throw new RuntimeException("I/O saving element screenshot", io);
        }
    }

    /* -------------------------------- Basic Interactions --------------------------------------------------------- */

    protected WebElement find(By locator){return driver.findElement(locator);}

    protected WebElement findVisible(By locator){
        return waitVisible(locator, DEFAULT_TIMEOUT);
    }

    protected void set(By locator, String text){
        WebElement el = findVisible(locator);
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.BACK_SPACE);
        el.sendKeys(text);
    }

    protected void click(By locator){
        waitClickable(locator, DEFAULT_TIMEOUT).click();
    }

    protected void clickSmart(By locator){
        try {
            click(locator);
        } catch (ElementClickInterceptedException | MoveTargetOutOfBoundsException e) {
            scrollToElementJS(locator);
            clickJS(locator);
        } catch (StaleElementReferenceException e) {
            click(locator);
        }
    }

    protected Actions actions() {
        return new Actions(driver);
    }

    /** Moves the mouse over the element (hover). */
    protected void hover(By locator) {
        scrollToElementJS(locator);
        actions().moveToElement(find(locator)).perform();
    }

    /** Double-click on the element. */
    protected void doubleClick(By locator) {
        scrollToElementJS(locator);
        actions().doubleClick(find(locator)).perform();
    }

    /** Right-click (context click) on the element. */
    protected void rightClick(By locator) {
        scrollToElementJS(locator);
        actions().contextClick(find(locator)).perform();
    }

    /** Native drag and drop (may fail on some HTML5 libs). */
    protected void dragAndDrop(By source, By target) {
        scrollToElementJS(source);
        scrollToElementJS(target);
        actions().dragAndDrop(find(source), find(target)).perform();
    }

    /** Fallback drag: click&hold → moveToElement → release (often works where dragAndDrop doesn’t). */
    protected void dragAndDropHoldMoveRelease(By source, By target) {
        scrollToElementJS(source);
        scrollToElementJS(target);
        actions()
                .clickAndHold(find(source))
                .moveToElement(find(target))
                .release()
                .perform();
    }

    /* ------------------------------------ Helpers JavaScript ----------------------------------------------------- */

    protected JavascriptExecutor js() {
        return (JavascriptExecutor) driver;
    }

    protected void scrollToElementJS(By locator) {
        WebElement el = waitPresent(locator, DEFAULT_TIMEOUT);
        js().executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    protected void clickJS(By locator) {
        WebElement el = waitPresent(locator, DEFAULT_TIMEOUT);
        js().executeScript("arguments[0].click();", el);
    }


    /* ------------------------------------ Dropdowns ------------------------------------------------------------- */
    protected Select dropdown(By locator) {
        // Wait to be visible
        var el = waitVisible(locator, DEFAULT_TIMEOUT);
        // Optional: validate that is a <select>
        if (!"select".equalsIgnoreCase(el.getTagName())) {
            throw new IllegalArgumentException("Locator does not point to a <select>: " + locator);
        }
        return new Select(el);
    }

    protected void selectByVisibleText(By locator, String text) {
        dropdown(locator).selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        dropdown(locator).selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        dropdown(locator).selectByIndex(index);
    }

    /* ---------------------------------------- Alerts ------------------------------------------------------------- */

    /** Waits until a JS alert is present and returns it. */
    protected Alert waitAlert(Duration timeout) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.alertIsPresent());
    }

    /** Accepts an alert (if present within timeout) and returns its text. */
    protected String acceptAlert(Duration timeout) {
        Alert a = waitAlert(timeout);
        String text = a.getText();
        a.accept();
        return text;
    }

    /** Dismisses an alert (if present within timeout) and returns its text. */
    protected String dismissAlert(Duration timeout) {
        Alert a = waitAlert(timeout);
        String text = a.getText();
        a.dismiss();
        return text;
    }

    /** Sends text to a prompt alert and accepts it. Returns the alert text (before accepting). */
    protected String sendKeysToAlertAndAccept(String keys, Duration timeout) {
        Alert a = waitAlert(timeout);
        String text = a.getText(); // usually prompt header, not the typed text
        a.sendKeys(keys);
        a.accept();
        return text;
    }

    /* ---------------------------------------- iFrame ------------------------------------------------------------- */

    /** Switches into an iframe found by locator (waits for it to be visible). */
    protected void switchToFrame(By frameLocator, Duration timeout) {
        scrollToElementJS(frameLocator);                       // ensure it’s in viewport
        WebElement frame = waitVisible(frameLocator, timeout); // iframe element itself
        driver.switchTo().frame(frame);                        // enter the frame DOM
    }

    /** Leaves the current frame and returns to the top-level document. */
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    /** Goes one level up (useful in nested frames: child → parent). */
    protected void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    /* ---------------------------------------- Windows ------------------------------------------------------------- */
    // --- Window helpers (no WebDriverWait; polling simple) ---

    /** Waits until the total number of open windows equals 'expected'. */
    protected void waitForWindowCount(int expected, java.time.Duration timeout) {
        long end = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < end) {
            if (driver.getWindowHandles().size() == expected) return;
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
        throw new RuntimeException("Timeout waiting for window count = " + expected);
    }

    /** Clicks an opener that spawns a new window/tab, switches to it, and returns the original handle. */
    protected String clickOpenerAndSwitchToNew(By opener, java.time.Duration timeout) {
        String original = driver.getWindowHandle();
        int before = driver.getWindowHandles().size();

        scrollToElementJS(opener);
        clickSmart(opener);

        waitForWindowCount(before + 1, timeout);

        for (String h : driver.getWindowHandles()) {
            if (!h.equals(original)) {
                driver.switchTo().window(h);
                return original; // caller can switch back using this
            }
        }
        throw new RuntimeException("New window handle not found after clicking: " + opener);
    }

    /** Switch back to a specific window handle. */
    protected void switchToWindow(String handle) {
        driver.switchTo().window(handle);
    }

    /** Closes the current window and switches to the given handle (usually the original). */
    protected void closeCurrentAndSwitchTo(String handle) {
        driver.close();
        driver.switchTo().window(handle);
    }


}

