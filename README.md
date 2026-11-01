# LinkedIn UI Test Suite (IT3153 Assignment 04)

Automated UI tests for LinkedIn written in Java 17 with Selenium WebDriver and TestNG, built with Maven using the Page Object Model.

## Tests
| ID | Test | Class |
|---|---|---|
| TC01 | Valid login | `LoginTest.validLogin` |
| TC02 | Invalid login shows an error | `LoginTest.invalidLogin` |
| TC03 | Search shows a results page | `SearchTest.searchShowsResults` |
| TC04 | Open the first search result | `SearchResultTest.clickFirstResult` |

## Prerequisites
- JDK 17 or later (`java -version`)
- Apache Maven (`mvn -version`)
- Google Chrome (ChromeDriver is downloaded automatically by Selenium Manager)
- Git

## Setup
1. Clone the repository:
   ```
   git clone https://github.com/DinuIDH/linkedin-tests.git
   cd linkedin-tests
   ```
2. Copy `src/test/resources/config.example.properties` to `src/test/resources/config.properties`.
3. Edit `config.properties` and enter YOUR OWN dedicated test account:
   ```
   email=your_test_account_email
   password=your_test_account_password
   wrongPassword=WrongPass@123
   searchTerm=software testing
   ```
   `config.properties` is listed in `.gitignore`, so it is never committed. Do not use anyone else's credentials.

## Run the tests
Run one test at a time, a few minutes apart:
```
mvn test -Dtest=LoginTest#validLogin
mvn test -Dtest=LoginTest#invalidLogin
mvn test -Dtest=SearchTest
mvn test -Dtest=SearchResultTest
```
Run the whole suite (uses `testng.xml`):
```
mvn clean test
```

## Screenshots
When a test fails, `BaseTest` saves a PNG to the `screenshots/` folder (ignored by Git).

## CAPTCHA and security checks
LinkedIn may show a CAPTCHA or verification step. The tests never try to bypass it. Solve it manually in the open Chrome window (the login wait is 120 seconds), then let the test continue. Repeated automated logins make this more likely, so avoid running the suite many times in a row.

## Notes
- LinkedIn changes its page structure often, so locators may need updating.
- Use for learning only. LinkedIn's User Agreement restricts automated access.