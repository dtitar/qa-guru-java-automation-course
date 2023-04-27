package scenarios;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    final void successfulFillTest() {

        final String firstName = "Eric";
        final String lastName = "Cartman";
        final String email = "www.freehat@sp.cc";
        final String mobileNumber = "9681129110";
        final String gender = "Male";
        final LocalDate birthDate = LocalDate.of(2000, 9, 13);
        final List<String> subjects = List.of("Maths", "Computer Science", "Commerce");
        final List<String> hobbies = List.of("Sports", "Reading");
        final String fileName = "screwYouGuys.png";
        final String currentAddress = "28201 E. Bonanza St. South Park";
        final String state = "NCR";
        final String city = "Delhi";

        open("https://demoqa.com/automation-practice-form");
        $(byClassName("main-header")).shouldHave(text("Practice Form"));
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byValue(gender)).doubleClick();
        $("#userNumber").setValue(mobileNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOptionByValue(String.valueOf(birthDate.getMonth().getValue() - 1));
        $(".react-datepicker__year-select").selectOptionByValue(String.valueOf(birthDate.getYear()));
        $(String.format(".react-datepicker__day--0%s", String.format("%02d", birthDate.getDayOfMonth()))).click();
        $("#subjectsInput").setValue(subjects.get(0)).pressEnter()
                .setValue(subjects.get(1)).pressEnter()
                .setValue(subjects.get(2)).pressEnter();
        $(byText(hobbies.get(0))).click();
        $(byText(hobbies.get(1))).click();
        $("#uploadPicture").uploadFromClasspath("images/" + fileName);
        $("#currentAddress").setValue(currentAddress);
        $("#react-select-3-input").setValue(state).pressEnter();
        $("#react-select-4-input").setValue(city).pressEnter();
        $("#submit").click();

        $(byClassName("modal-content")).shouldBe(visible);
        $$(".table tr").shouldHave(size(11));
        $(".table tr", 0).shouldHave(text("Label")).shouldHave(text("Values"));
        $(".table tr", 1).shouldHave(text(firstName)).shouldHave(text(lastName));
        $(".table tr", 2).shouldHave(text(email));
        $(".table tr", 3).shouldHave(text(gender));
        $(".table tr", 4).shouldHave(text(mobileNumber));
        $(".table tr", 5).shouldHave(text(birthDate.format(DateTimeFormatter.ofPattern("dd MMMM,yyyy"))));
        $(".table tr", 6).shouldHave(text(String.join(", ", subjects)));
        $(".table tr", 7).shouldHave(text(String.join(", ", hobbies)));
        $(".table tr", 8).shouldHave(text(fileName));
        $(".table tr", 9).shouldHave(text(currentAddress));
        $(".table tr", 10).shouldHave(text(state)).shouldHave(text(city));
    }
}
