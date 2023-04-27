package ru.autotests.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    public final SelenideElement loginForm = $("[class*=modal__content]");
    public final SelenideElement loginWithMobilePhoneTab = $x("//*[text()='Номер телефона']/parent::*");
    public final SelenideElement loginWithEmailTab = $x("//*[text()='E-mail']");
    public final SelenideElement mobilePhoneField = $("input[name=mobilePhone]");
    public final SelenideElement emailField = $("input[name=email]");
    public final SelenideElement passwordField = $("input[name=password]");
    public final SelenideElement submitButton = $("[class*=auth-form__submit]");
    public final SelenideElement authFormErrorNotification = $("[class*=auth-form__error]");
    public final SelenideElement resetPasswordLink = $("a[href='/ResetPassword']");

}