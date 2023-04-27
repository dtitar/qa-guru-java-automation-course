package scenarios;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class GithubSelenideRepoTest {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    final void shouldDisplayExampleCodeForJunit5OnTheSoftAssertionsPage() {
        open("https://github.com/selenide/selenide");
        $("[data-content=Wiki]").click();
        $("#wiki-body").shouldHave(text("Soft assertions"));
        $(byText("Soft assertions")).click();
        $("#wiki-body").shouldHave(text("Using JUnit5 extend test class:"));
        $(withText("ExtendWith")).shouldBe(visible);

    }
}
