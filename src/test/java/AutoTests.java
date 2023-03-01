import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;
import static data.DataGenerator.Registration.getRegisteredUser;

public class AutoTests {

    @Test
    void shouldLoginActive() {
        open("http://localhost:7777/");
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        $("[name=login]").setValue(registeredUser.getLogin());
        $("[name=password]").setValue(registeredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $x("//h2").shouldHave().should(appear);
    }

    @Test
    void shouldNotLoginBlocked() {
        open("http://localhost:7777/");
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("blocked");
        $("[name=login]").setValue(registeredUser.getLogin());
        $("[name=password]").setValue(registeredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $(".notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $(".notification__content").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldNotLoginUnregistered() {
        open("http://localhost:7777/");
        Configuration.holdBrowserOpen = true;
        $("[name=login]").setValue(DataGenerator.getRandomLogin());
        $("[name=password]").setValue(DataGenerator.getRandomPassword());
        $("button[data-test-id='action-login']").click();
        $(".notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorActiveWithWrongPass() {
        open("http://localhost:7777/");
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        $("[name=login]").setValue(registeredUser.getLogin());
        $("[name=password]").setValue(DataGenerator.getRandomPassword());
        $("button[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15)).shouldBe(Condition.visible);

    }

    @Test
    void shouldErrorBlockedWithWrongPass() {
        open("http://localhost:7777/");
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("blocked");
        $("[name=login]").setValue(registeredUser.getLogin());
        $("[name=password]").setValue(DataGenerator.getRandomPassword());
        $("button[data-test-id='action-login']").click();
        $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}

