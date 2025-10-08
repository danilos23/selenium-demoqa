package com.demoqa.pages.elements;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementsPage extends BasePage {

    private final By headerElements   = By.xpath("//div[@class='main-header' and normalize-space()='Elements']");
    // item “Web Tables” solo dentro del panel abierto de Elements:
    private final By webTablesNavItem = By.cssSelector("div.element-list.collapse.show li#item-3");
    private final By headerLinks = By.xpath("//li[@id='item-5']/span[text()='Links']");
    private final By linksMenuItem = By.id("item-5");

    public ElementsPage(WebDriver driver) { super(driver); }

    public ElementsPage waitForPage() {
        waitVisible(headerElements, Duration.ofSeconds(10));
        return this;
    }

    public WebTablesPage clickWebTables() {
        // Asegura que estás en Elements (si llegas desde fallback, ya estará)
        if (!isVisible(headerElements, Duration.ofSeconds(2))) {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.or(
                            ExpectedConditions.urlContains("/elements"),
                            ExpectedConditions.visibilityOfElementLocated(headerElements)
                    )
            );
        }

        closeFixedBannerIfPresent();
        scrollToElementJS(webTablesNavItem);
        // Si el CSS de arriba no te encaja, alternativa robusta:
        // By webTablesNavItem = By.xpath("//span[normalize-space()='Web Tables']");
        clickSmart(webTablesNavItem);

        // Espera destino
        var headerWebTables = By.xpath("//div[@class='main-header' and normalize-space()='Web Tables']");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("/webtables"),
                        ExpectedConditions.visibilityOfElementLocated(headerWebTables)
                )
        );

        return new WebTablesPage(driver);
    }

    public LinksPage clickLinks() {
        closeFixedBannerIfPresent();
        scrollToElementJS(linksMenuItem);
        clickSmart(linksMenuItem);

        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/links"),
                    ExpectedConditions.visibilityOfElementLocated(headerLinks)
            ));
        } catch (TimeoutException e) {
            // Si el menú no navegó (pasa a veces), navega directo y espera:
            driver.navigate().to("https://demoqa.com/links");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/links"),
                    ExpectedConditions.visibilityOfElementLocated(headerLinks)
            ));
        }
        return new LinksPage(driver);
    }
}


