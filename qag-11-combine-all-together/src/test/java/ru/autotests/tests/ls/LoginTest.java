package ru.autotests.tests.ls;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.autotests.pages.LoginPage;
import ru.autotests.pages.MainPage;
import ru.autotests.tests.TestBase;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Story("Login scenarios")
public class LoginTest extends TestBase {

    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();

    @BeforeAll
    static void configureBaseUrl() {
        Configuration.baseUrl = TestData.getWebUrl();
    }

    @Test
    @Tag("ui")
    @Owner("dtitar")
    @DisplayName("Verifying for the presence of authorization form elements")
    void testLoginFormFields() {
        step("Open login page", () -> {
            open("");
            mainPage.loginButton.click();
            loginPage.loginForm.shouldBe(visible);
        });

        step("Verify authorization with mobile phone form fields are present", () -> {
            loginPage.loginWithMobilePhoneTab.shouldBe(enabled);
            loginPage.mobilePhoneField.shouldBe(visible);
            loginPage.passwordField.shouldBe(visible);
            loginPage.submitButton.shouldBe(disabled);
        });

        step("Click E-mail tab", () -> {
            loginPage.loginWithEmailTab.click();
            loginPage.emailField.shouldBe(visible);
        });

        step("Verify authorization with email form fields are present", () -> {
            loginPage.loginWithEmailTab.shouldBe(enabled);
            loginPage.emailField.shouldBe(visible);
            loginPage.passwordField.shouldBe(visible);
            loginPage.submitButton.shouldBe(disabled);
        });
    }

    @Test
    @Tag("ui")
    @Owner("dtitar")
    @DisplayName("Failed login with mobile phone with invalid credentials")
    void testUserLoginWithMobilePhoneFail() {
        Faker fake = new Faker();
        String expectedLoginErrorMessageText = "Неверно указан номер телефона или пароль";

        step("Open login page", () -> {
            open("");
            mainPage.loginButton.click();
            loginPage.loginForm.shouldBe(visible);
        });

        step("Fill login with mobile phone form with invalid credentials and click Login button", () -> {
            loginPage.mobilePhoneField.setValue(fake.phoneNumber().subscriberNumber(9));
            loginPage.passwordField.setValue(fake.internet().password());
            loginPage.submitButton.shouldBe(enabled).click();
        });

        step("Verify login fail message is displayed", () -> {
            loginPage.loginForm.shouldBe(visible);
            loginPage.authFormErrorNotification.shouldHave(text(expectedLoginErrorMessageText));
        });
    }

    @Test
    @Tag("ui")
    @Owner("dtitar")
    @DisplayName("Failed login with email with invalid credentials")
    void testUserLoginWithEmailFail() {
        Faker fake = new Faker();
        String expectedLoginErrorMessageText = "Неверно указан E-mail или пароль";

        step("Open login page", () -> {
            open("");
            mainPage.loginButton.click();
            loginPage.loginForm.shouldBe(visible);
        });

        step("Fill login with email form with invalid credentials and click Login button", () -> {
            loginPage.loginWithEmailTab.click();
            loginPage.emailField.setValue(fake.internet().emailAddress());
            loginPage.passwordField.setValue(fake.internet().password());
            loginPage.submitButton.shouldBe(enabled).click();
        });

        step("Verify login fail message is displayed", () -> {
            loginPage.loginForm.shouldBe(visible);
            loginPage.authFormErrorNotification.shouldHave(text(expectedLoginErrorMessageText));
        });
    }
}
