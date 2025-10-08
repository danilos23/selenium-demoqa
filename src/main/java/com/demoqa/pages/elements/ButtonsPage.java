package com.demoqa.pages.elements;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class ButtonsPage extends BasePage {

    private final By doubleClickBtn    = By.id("doubleClickBtn");
    private final By rightClickBtn     = By.id("rightClickBtn");
    private final By dynamicClickBtn   = By.xpath("//button[text()='Click Me']");

    private final By doubleMsg         = By.id("doubleClickMessage");
    private final By rightMsg          = By.id("rightClickMessage");
    private final By dynamicMsg        = By.id("dynamicClickMessage");

    public ButtonsPage(WebDriver driver) {
        super(driver);
    }

    public ButtonsPage openByUrl() {
        driver.navigate().to("https://demoqa.com/buttons");
        waitVisible(doubleClickBtn, Duration.ofSeconds(10));
        return this;
    }

    public ButtonsPage doDoubleClick() {
        doubleClick(doubleClickBtn);
        waitVisible(doubleMsg, Duration.ofSeconds(5));
        return this;
    }

    public ButtonsPage doRightClick() {
        rightClick(rightClickBtn);
        waitVisible(rightMsg, Duration.ofSeconds(5));
        return this;
    }

    public ButtonsPage doDynamicClick() {
        clickSmart(dynamicClickBtn);
        waitVisible(dynamicMsg, Duration.ofSeconds(5));
        return this;
    }

    public String getDoubleClickMessage() { return find(doubleMsg).getText(); }
    public String getRightClickMessage()  { return find(rightMsg).getText(); }
    public String getDynamicClickMessage(){ return find(dynamicMsg).getText(); }
}
