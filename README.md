# Selenium DemoQA Automation Project

A Java-based Selenium automation framework showcasing UI test automation best practices.
It uses the DemoQA site as a playground for testing modern web UI elements.

---

## Tech Stack

- Language: Java 21
- Build tool: Maven
- Test framework: TestNG
- Automation: Selenium 4.23
- IDE: IntelliJ IDEA

---

## Project Structure
```
SeleniumFreeCodeCamp/
|-- src/
| |-- main/java/
| | -- pages/ -> Page Object classes (encapsulate locators and actions)
| -- test/java/
| |-- tests/ -> Test classes organized by feature
| -- base/ -> BasePage and helpers
|-- pom.xml -> Maven dependencies and configuration
|-- .gitignore -> Ignored files (target/, logs/, screenshots/)
-- README.md
```
---

## Key Features

- Page Object Model (POM): clean separation between test logic and UI locators/actions. 
- Reusable BasePage Helpers: waitVisible, isVisible, clickSmart, scrollToElementJS, takeScreenshot, and helpers for alerts, frames, and windows.
- Cross-browser Ready: Configurable WebDriver setup for Chrome, Edge, and Firefox.
- Dynamic Waits and Smart Actions: Custom wrapper methods for stability against timing issues.
- Rich Coverage: Tests for modals, alerts, frames, windows/tabs, interactions, and dynamic content.

---

## Installation and Setup

1. Clone the repository
```bash
git clone https://github.com/tu-usuario-de-github/selenium-demoqa.git
cd selenium-demoqa
```

2. Build the project
```bash
mvn clean compile
```

3. Run all tests
```bash
mvn -q -DskipTests=false test
```

4. Run a specific test class
```bash
mvn -Dtest=AlertsTest test
```

Tip: Make sure you have Java 21 and Maven configured in your system path.

---

## Screenshots


---

## Example Tests

| Category | Example Test Class | Description |
|-----------|-------------------|--------------|
| Alerts | AlertsTest.java | Validates browser and JS alerts |
| Frames | FramesTest.java | Handles iframe content switching |
| Windows/Tabs | WindowsTest.java | Tests multiple window/tab handling |
| Interactions | InteractionsTest.java | Drag/drop, hover, and clicks |

---

## Future Enhancements

- Integrate with GitHub Actions for continuous integration.
- Add Allure Reports for detailed test analytics.
- Parameterize browser selection via Maven profiles.

---

## Author

Daniel Illan
QA Automation Engineer
GitHub: [https://github.com/danilos23](https://github.com/danilos23)
LinkedIn: [https://www.linkedin.com/](https://www.linkedin.com/in/daniel-illan/)

---

## License

This project is licensed under the MIT License.
