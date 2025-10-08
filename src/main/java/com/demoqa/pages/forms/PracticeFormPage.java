package com.demoqa.pages.forms;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.time.Duration;

public class PracticeFormPage extends BasePage {

    private final By femaleRadioButton = By.id("gender-radio-2");
    private final By sportsHobbyCheckbox = By.id("hobbies-checkbox-1");
    private final By readingHobbyCheckbox = By.id("hobbies-checkbox-2");
    private final By musicHobbyCheckbox = By.id("hobbies-checkbox-3");
    private final By submitButton = By.id("submit");
    // Visible, reliable elements on the Practice Form
    private final By header       = By.cssSelector("div.main-header");
    private final By firstNameInp = By.id("firstName");
    // If you want a visible control for gender, prefer the LABEL (inputs are hidden)
    private final By femaleLabel  = By.cssSelector("label[for='gender-radio-2']");


    public PracticeFormPage(WebDriver driver) { super(driver); }

    /** Waits until the Practice Form view is ready (header or first name visible). */
    public PracticeFormPage waitForPage() {
        // Either the header text "Practice Form" is present, or the first name input is visible
        waitVisible(firstNameInp, Duration.ofSeconds(10));
        return this;
    }

    public void clickFemaleRadioButton() {
        clickJS(femaleRadioButton);
    }

    public boolean isFemaleSelected(){
        return find(femaleRadioButton).isSelected();
    }

    public void clickSportsCheckbox(){
        if(!find(sportsHobbyCheckbox).isSelected()){
            scrollToElementJS(sportsHobbyCheckbox);
            clickJS(sportsHobbyCheckbox);
        }
    }

    public void clickReadingCheckbox(){
        if(!find(readingHobbyCheckbox).isSelected()){
            scrollToElementJS(readingHobbyCheckbox);
            clickJS(readingHobbyCheckbox);
        }
    }

    public void clickMusicCheckbox(){
        if(!find(musicHobbyCheckbox).isSelected()){
            scrollToElementJS(musicHobbyCheckbox);
            clickJS(musicHobbyCheckbox);
        }
    }

    public void unclickReadingCheckbox(){
        if(find(readingHobbyCheckbox).isSelected()){
            scrollToElementJS(readingHobbyCheckbox);
            clickJS(readingHobbyCheckbox);
        }
    }

    public boolean isReadingSelected(){
        return find(readingHobbyCheckbox).isSelected();
    }

    /** Element-only screenshot of the First Name input */
    public Path screenshotFirstName() {
        scrollToElementJS(firstNameInp);
        var el = waitVisible(firstNameInp, Duration.ofSeconds(10)); // WebElement
        return saveElementScreenshot(el, "practice_form_firstName");
    }

    /** Element-only screenshot of the Female label (input is hidden; label is visible) */
    public Path screenshotFemaleLabel() {
        scrollToElementJS(femaleLabel);
        var el = waitVisible(femaleLabel, Duration.ofSeconds(10));  // WebElement
        return saveElementScreenshot(el, "practice_form_female_label");
    }

}

