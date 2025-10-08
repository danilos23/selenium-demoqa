package com.demoqa.pages.elements;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebTablesPage extends BasePage {

    private final By header = By.xpath("//div[@class='main-header' and normalize-space()='Web Tables']");
    private final By addButton           = By.id("addNewRecordButton");
    private final By registrationModal   = By.id("registration-form-modal");

    private final By firstNameInput      = By.id("firstName");
    private final By lastNameInput       = By.id("lastName");
    private final By emailInput          = By.id("userEmail");
    private final By ageInput            = By.id("age");
    private final By salaryInput         = By.id("salary");
    private final By departmentInput     = By.id("department");
    private final By submitBtn           = By.id("submit");

    public WebTablesPage(WebDriver driver) { super(driver); }

    /** Espera a estar en la página de Web Tables */
    public WebTablesPage waitForPage() {
        var header = By.xpath("//div[@class='main-header' and normalize-space()='Web Tables']");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("/webtables"),
                        ExpectedConditions.visibilityOfElementLocated(header)
                )
        );
        return this;
    }

    public WebTablesPage openAddForm() {
        clickSmart(addButton);
        // Espera a que el modal y algún campo estén visibles
        waitVisible(registrationModal, Duration.ofSeconds(10));
        waitVisible(ageInput, Duration.ofSeconds(10));
        return this;
    }

    private void ensureAddFormOpen() {
        if (!isVisible(ageInput, Duration.ofSeconds(2))) {
            openAddForm();
        }
    }

    public WebTablesPage setFirstName(String first) { ensureAddFormOpen(); set(firstNameInput, first); return this; }
    public WebTablesPage setLastName(String last)   { ensureAddFormOpen(); set(lastNameInput, last);  return this; }
    public WebTablesPage setEmail(String email)     { ensureAddFormOpen(); set(emailInput, email);    return this; }
    public WebTablesPage setAge(String age)         { ensureAddFormOpen(); set(ageInput, age);        return this; }
    public WebTablesPage setSalary(String salary)   { ensureAddFormOpen(); set(salaryInput, salary);  return this; }
    public WebTablesPage setDepartment(String dept) { ensureAddFormOpen(); set(departmentInput, dept); return this; }

    public WebTablesPage submit() {
        clickSmart(submitBtn);
        waitGone(registrationModal, Duration.ofSeconds(10)); // opcional
        return this;
    }
}


