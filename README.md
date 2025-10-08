# Selenium DemoQA Automation Project

A Java-based Selenium automation framework showcasing UI test automation best practices.  

---

## ?? Tech Stack

- **Language:** Java 21
- **Build tool:** Maven
- **Test framework:** TestNG
- **Automation:** Selenium 4.23
- **IDE:** IntelliJ IDEA

---

## ?? Project Structure

\`\`\`
SeleniumFreeCodeCamp/
ÃÄÄ src/
³   ÃÄÄ main/java/
³   ÃÄÄ test/java/
³   ³   ÃÄÄ tests/           Test classes organized by feature
³   ³   ÀÄÄ base/            BasePage and helpers
ÃÄÄ pom.xml                  Maven dependencies and configuration
ÀÄÄ README.md
\`\`\`

---

## ?? Key Features

- **Reusable BasePage Helpers:** `waitVisible`, `isVisible`, `clickSmart`, `scrollToElementJS`, `takeScreenshot`, and helpers for alerts, frames, and windows.
- **Cross-browser Ready:** Configurable WebDriver setup for Chrome, Edge, and Firefox.
- **Dynamic Waits 
- **Rich Coverage:** Covers modals, alerts, frames, windows/tabs, interactions, and dynamic content.

---

## ?? Installation & Setup

### 1) Clone the repository
```bash
git clone https://github.com/tu-usuario-de-github/selenium-demoqa.git
cd selenium-demoqa
```

### 2) Build the project
```bash
mvn clean compile
```

### 3) Run all tests
```bash
mvn -q -DskipTests=false test
```

### 4) Run a specific test class
```bash
mvn -Dtest=AlertsTest test
```


---

## ?? Screenshots


---

## ?? Example Tests

| Category | Example Test Class | Description |
|-----------|-------------------|--------------|
| Alerts | AlertsTest.java | Validates browser and JS alerts |
| Frames | FramesTest.java | Handles iframe content switching |
| Windows/Tabs | WindowsTest.java | Tests multiple window/tab handling |
| Interactions | InteractionsTest.java | Drag/drop, hover, and clicks |

---

## ?? Future Enhancements

- Integrate with **GitHub Actions** for CI.
- Add **Allure Reports** for test analytics.
- Parameterize browser selection via Maven profiles.

---

## ?? Author

**Daniel Ill n**
QA Automation Engineer

---

## ?? License

