package ru.autotests.tests.ls;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.autotests.pages.LoginPage;
import ru.autotests.pages.MainPage;
import ru.autotests.pages.ResetPasswordPage;
import ru.autotests.tests.TestBase;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Story("Reset password scenarios")
class ResetPasswordTest extends TestBase {

    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();
    ResetPasswordPage resetPasswordPage = new ResetPasswordPage();

    @BeforeAll
    static void configureBaseUrl() {
        Configuration.baseUrl = TestData.getWebUrl();
    }

    @Test
    @Tag("ui")
    @Owner("dtitar")
    @DisplayName("Verifying presence of the reset password form elements")
    void testResetPasswordFormFields() {

        step("Open login page", () -> {
            open("");
            mainPage.loginButton.click();
            loginPage.loginForm.shouldBe(visible);
        });

        step("Click reset password link", () -> {
            loginPage.resetPasswordLink.click();
            resetPasswordPage.resetPasswordHeader.shouldBe(visible);

        });

        step("Verify reset password with mobile phone form is displayed", () -> {
            resetPasswordPage.resetPasswordWithPhoneTab.shouldBe(enabled);
            resetPasswordPage.mobilePhoneField.shouldBe(visible);
            resetPasswordPage.getCodeBySmsButton
                    .shouldBe(disabled)
                    .shouldHave(text("Получить код по SMS"));
        });

        step("Click Email tab", () -> {
            resetPasswordPage.resetPasswordWithEmailTab.click();
            resetPasswordPage.emailField.shouldBe(visible);
        });

        step("Verify reset password with email form is displayed", () -> {
            resetPasswordPage.resetPasswordWithEmailTab.shouldBe(enabled);
            resetPasswordPage.emailField.shouldBe(visible);
            resetPasswordPage.sendInstructionByEmailButton
                    .shouldBe(disabled)
                    .shouldHave(text("Отправить инструкцию на e-mail"));
        });
    }

    @Test
    @Tag("ui")
    @Owner("dtitar")
    @DisplayName("Failed reset password with invalid mobile phone")
    void testResetPasswordFormWithMobilePhoneInvalidData() {
        Faker fake = new Faker();
        String expectedResetPasswordWarningMessageText = "Номер не найден. Проверьте правильность введённого номера телефона или укажите email";

        step("Open reset password with mobile phone form", () -> {
            open("/ResetPassword");
            resetPasswordPage.resetPasswordHeader.shouldBe(visible);
        });

        step("Fill mobile phone and click get code by sms button", () -> {
            resetPasswordPage.mobilePhoneField.setValue(fake.phoneNumber().subscriberNumber(9));
            resetPasswordPage.getCodeBySmsButton.shouldBe(enabled).click();
        });

        step("Verify reset password fail message is displayed", () -> {
            resetPasswordPage.resetPasswordWarning.shouldBe(visible)
                    .shouldHave(text(expectedResetPasswordWarningMessageText));
        });
    }

    @Test
    @Tag("ui")
    @Owner("dtitar")
    @DisplayName("Failed reset password with invalid email")
    void testResetPasswordFormWithEmailInvalidData() {
        Faker fake = new Faker();
        String expectedResetPasswordWarningMessageText = "Указанный электронный адрес не найден среди зарегистрированных в системе";

        step("Open reset password with email form", () -> {
            open("/ResetPassword");
            resetPasswordPage.resetPasswordHeader.shouldBe(visible);
            resetPasswordPage.resetPasswordWithEmailTab.click();
            resetPasswordPage.emailField.shouldBe(visible);
        });

        step("Fill email and click send instruction by email button", () -> {
            resetPasswordPage.emailField.setValue(fake.internet().emailAddress());
            resetPasswordPage.sendInstructionByEmailButton.shouldBe(enabled).click();
        });

        step("Verify reset password fail message is displayed", () -> {
            resetPasswordPage.resetPasswordWarning.shouldBe(visible)
                    .shouldHave(text(expectedResetPasswordWarningMessageText));
        });
    }

    @Test
    @Tag("ui")
    @Owner("dtitar")
    @DisplayName("Reset password fields errors notifications")
    @Disabled("needs test case update")
    void testResetPasswordFormFieldsErrorNotifications() {
        String expectedMobilePhoneInputErrorMessageText = "Это поле обязательно к заполнению";

        step("Open reset password with mobile phone form", () -> {
            open("/ResetPassword");
            resetPasswordPage.resetPasswordHeader.shouldBe(visible);
        });

        step("Click on mobile phone input and send sms button", () -> {
            resetPasswordPage.mobilePhoneField.click();
            resetPasswordPage.getCodeBySmsButton.click();
        });

        step("Verify mobile phone field input message is displayed", () -> {
            resetPasswordPage.mobilePhoneInputErrorMessage.shouldBe(visible)
                    .shouldHave(text(expectedMobilePhoneInputErrorMessageText));
        });
    }
}
