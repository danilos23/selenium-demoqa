package com.saucedemo.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    private final By productsHeader = By.xpath("//span[text()='Products']");

    public ProductsPage(WebDriver driver) {
        super(driver); // 👈 inyecta el WebDriver en BasePage
    }

    public boolean isProductsHeaderDisplayed() {
        return find(productsHeader).isDisplayed(); // isDisplayed devuelve boolean
    }
}
