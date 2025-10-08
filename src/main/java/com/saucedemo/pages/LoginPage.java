package com.saucedemo.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("#login_button_container h3");

    public LoginPage(WebDriver driver) { super(driver); }

    public void setUsername(String username){
        set(usernameField, username);
    }

    public void setPassword(String password){
        set(passwordField, password);
    }

    // Transition Method: tras login navega a ProductsPage
    public ProductsPage clickLoginButton(){
        click(loginButton);
        return new ProductsPage(driver); // ⬅️ pasa el driver
    }

    public ProductsPage logIntoApplication(String username, String password){
        setUsername(username);
        setPassword(password);
        return clickLoginButton();
    }

    public String getErrorMessage(){
        return find(errorMessage).getText();
    }
}
