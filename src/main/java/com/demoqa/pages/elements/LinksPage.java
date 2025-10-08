package com.demoqa.pages.elements;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

public class LinksPage extends BasePage {

    private final By badRequestLink = By.id("bad-request");
    private final By responseText = By.id("linkResponse");
    private final By headerLinks    = By.xpath("//div[@class='main-header' and normalize-space()='Links']");
    private final By linkWrapper  = By.id("linkWrapper");           // contenedor principal
    private final By createdLink  = By.id("created");               // uno de los links API, suele estar siempre


    public LinksPage(WebDriver driver){
        super(driver);
    }

    // Click al enlace 400 Bad Request (con scroll + click inteligente).
    // public LinksPage en lugar de public void ya que
    public LinksPage clickBadRequestLink(){
        scrollToElementJS(badRequestLink);
        clickSmart(badRequestLink);
        return this;                        // Fluido
    }

    // Devuelve el texto de respuesta del bloque "linkResponse" con espera explicita
    public String getResponse(){
        return waitVisible(responseText, Duration.ofSeconds(10)).getText();
    }

    public LinksPage waitForPage() {
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/links"),
                    ExpectedConditions.visibilityOfElementLocated(headerLinks),
                    ExpectedConditions.visibilityOfElementLocated(linkWrapper),
                    ExpectedConditions.visibilityOfElementLocated(createdLink)
            ));
        } catch (TimeoutException e) {
            // Fallback directo si algo tapó el click
            driver.navigate().to("https://demoqa.com/links");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/links"),
                    ExpectedConditions.visibilityOfElementLocated(headerLinks),
                    ExpectedConditions.visibilityOfElementLocated(linkWrapper),
                    ExpectedConditions.visibilityOfElementLocated(createdLink)
            ));
        }
        return this;
    }
}
