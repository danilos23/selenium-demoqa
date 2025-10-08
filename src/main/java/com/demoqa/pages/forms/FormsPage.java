package com.demoqa.pages.forms;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormsPage extends BasePage {

    public FormsPage(WebDriver driver) { super(driver); }

    private final By practiceFormMenuItem = By.xpath("//li[@id='item-0']/span[text()='Practice Form']");

    public PracticeFormPage clickPracticeForm() {
        scrollToElementJS(practiceFormMenuItem);
        click(practiceFormMenuItem);
        return new PracticeFormPage(driver);
    }
}