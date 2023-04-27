package scenarios;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import steps.Websteps;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@DisplayName("Проверка отображения github issue различными способами написания сценария")
public class GithubIssueNameTest {

    private static final String BASE_URL = "https://github.com";
    private static final String REPOSITORY = "selenide/selenide";
    private static final int ISSUE_NUMBER = 217;

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    @DisplayName("Проверка отображения issue с использованием чистого selenide")
    final void shouldDisplayGithubIssuePureSelenide() {
        //Ожидалось, что данный листенер будет работать только для этого сценария, но он добавляет в аллюр код ко всем трем сценариям
        SelenideLogger.addListener("allure", new AllureSelenide());
        Allure.label("owner", "dtitar");
        Allure.feature("Поиск Github Issue");
        Allure.story("Поиск github issue с использованием чистого selenide");
        Allure.description("Описание сценария проверки отображения issue с использованием чистого selenide");

        open(BASE_URL);
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(REPOSITORY);
        $(".header-search-input").submit();

        $(By.linkText(REPOSITORY)).click();
        $(withText("Issues")).click();

        $(withText("#" + ISSUE_NUMBER)).should(exist);
    }

    @Test
    @Owner("dtitar")
    @Feature("Поиск Github Issue")
    @Story("Поиск github issue с использованием лямбда-шагов")
    @Description("Проверка отображения issue с использованием лямбда-шагов")
    final void shouldDisplayGithubIssueLambda() {
        step("Открываем главную страницу", (step) -> {
            step.parameter("url", BASE_URL);
            open(BASE_URL);
        });

        step("Ищем репозиторий", (step) -> {
            step.parameter("repository", REPOSITORY);
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });

        step("Переходим в репозиторий", (step) -> {
            step.parameter("repository", REPOSITORY);
            $(By.linkText(REPOSITORY)).click();
        });

        step("Переходим в таб Issues", () -> {
            $(withText("Issues")).click();
        });

        step("Проверяем что Issue с номером {step} существует", (step) -> {
            step.parameter("issue", ISSUE_NUMBER);
            $(withText("#" + ISSUE_NUMBER)).should(exist);

        });
    }

    @Test
    @Owner("dtitar")
    @Feature("Поиск Github Issue")
    @Story("Поиск github issue с использованием annotated steps")
    @Description("Проверка отображения issue с использованием annotated steps")
    final void shouldDisplayGithubIssueAnnotatedSteps() {
        Websteps steps = new Websteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.goToRepository(REPOSITORY);
        steps.clickOnIssueTab();
        steps.shouldSeeIssueWithNumber(ISSUE_NUMBER);
    }
}
