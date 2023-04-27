package ru.autotests.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ResetPasswordPage {

    public final SelenideElement resetPasswordHeader = $("[class*=reset-password__heading]");
    public final SelenideElement resetPasswordWithPhoneTab = $x("//*[text()='Номер телефона']/parent::*");
    public final SelenideElement resetPasswordWithEmailTab = $x("//*[text()='Электронная почта']/parent::*");
    public final SelenideElement mobilePhoneField = $("input[name=mobilePhone]");
    public final SelenideElement emailField = $("input[name=email]");
    public final SelenideElement getCodeBySmsButton = $("button[class*=reset-password]");
    public final SelenideElement sendInstructionByEmailButton = $("button[class*=reset-password]");
    public final SelenideElement resetPasswordWarning = $("[class*=reset-password__warning]");
    public final SelenideElement mobilePhoneInputErrorMessage = $("[class*=better-inputs__error]");
}
