package ru.autotests.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public final SelenideElement loginButton = $("#auth-panel>button");
}
