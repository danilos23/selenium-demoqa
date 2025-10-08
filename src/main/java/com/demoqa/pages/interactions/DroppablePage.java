package com.demoqa.pages.interactions;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class DroppablePage extends BasePage {

    // Tabs (we’ll use the “Simple” one)
    private final By simpleTab = By.id("droppableExample-tab-simple");

    // Simple scenario: a draggable box and the drop target
    private final By dragBox   = By.id("draggable");
    private final By dropBox   = By.id("droppable"); // text changes to "Dropped!"

    public DroppablePage(WebDriver driver) {
        super(driver);
    }

    /** Direct navigation. */
    public DroppablePage openByUrl() {
        driver.navigate().to("https://demoqa.com/droppable");
        waitVisible(simpleTab, Duration.ofSeconds(10));
        return this;
    }

    /** Makes sure the Simple tab is active (safe if already active). */
    public DroppablePage goToSimpleTab() {
        clickSmart(simpleTab);
        waitVisible(dragBox, Duration.ofSeconds(5));
        waitVisible(dropBox, Duration.ofSeconds(5));
        return this;
    }

    /** Robust drag&drop: try native → fallback to hold/move/release → finally JS polyfill. */
    public DroppablePage dragToDrop_Robust() {
        // 1) Try native
        dragAndDrop(dragBox, dropBox);
        if (didDrop()) return this;

        // 2) Fallback: hold/move/release
        dragAndDropHoldMoveRelease(dragBox, dropBox);
        if (didDrop()) return this;

        // 3) Last resort: JS HTML5 DnD simulation
        dragAndDropHtml5_JS(dragBox, dropBox);
        if (!didDrop()) {
            throw new RuntimeException("Drag&Drop did not result in 'Dropped!' state");
        }
        return this;
    }

    /** Checks if target shows the 'Dropped!' text (success criteria). */
    private boolean didDrop() {
        try {
            var el = waitVisible(dropBox, Duration.ofSeconds(2));
            return el.getText().trim().equalsIgnoreCase("Dropped!");
        } catch (Exception ignored) {
            return false;
        }
    }

    /** Minimal HTML5 drag&drop JS (DataTransfer). */
    private void dragAndDropHtml5_JS(By source, By target) {
        String js =
                "function dt(){return new DataTransfer();}" +
                        "function fire(el,type,dt){const e=new DragEvent(type,{bubbles:true,cancelable:true,dataTransfer:dt});el.dispatchEvent(e);}" +
                        "const src=arguments[0], dst=arguments[1];" +
                        "const data=dt();" +
                        "fire(src,'dragstart',data);" +
                        "fire(dst,'dragenter',data);" +
                        "fire(dst,'dragover',data);" +
                        "fire(dst,'drop',data);" +
                        "fire(src,'dragend',data);";
        js().executeScript(js, find(source), find(target));
    }

    /** Returns the current text inside the drop box. */
    public String getDropText() {
        return find(dropBox).getText();
    }
}
