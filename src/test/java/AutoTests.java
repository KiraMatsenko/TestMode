import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import usergenerator.UserGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class AutoTests {

    UserGenerator generator = new UserGenerator();

        @Test
        void shouldLoginActive () {
            generator.authTest("nadya", "create2", "active");
            open("http://localhost:7777/");
            Configuration.holdBrowserOpen=true;
            $("[name=login]").setValue("nadya");
            $("[name=password]").setValue("create2");
            $("button[data-test-id='action-login']").click();
            $x("//h2[contains(text(),'Личный кабинет')]").shouldHave().should(appear);
        }

        @Test
        void shouldNotLoginBlocked () {
            generator.authTest("vasya", "create", "blocked");
            open("http://localhost:7777/");
            Configuration.holdBrowserOpen=true;
            $("[name=login]").setValue("vasya");
            $("[name=password]").setValue("create");
            $("button[data-test-id='action-login']").click();
            $(".notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
            $(".notification__content").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        }

        @Test
        void shouldNotLoginUnregistered () {
            open("http://localhost:7777/");
            Configuration.holdBrowserOpen=true;
            $("[name=login]").setValue("petya");
            $("[name=password]").setValue("create");
            $("button[data-test-id='action-login']").click();
            $(".notification__title").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
            $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        }

        @Test
        void shouldErrorActiveWithWrongPass () {
            generator.authTest("vasya", "create", "active");
            open("http://localhost:7777/");
            Configuration.holdBrowserOpen=true;
            $("[name=login]").setValue("vasya");
            $("[name=password]").setValue("creative");
            $("button[data-test-id='action-login']").click();
            $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15)).shouldBe(Condition.visible);

        }

        @Test
        void shouldErrorBlockedWithWrongPass () {
            generator.authTest("vasya", "create", "blocked");
            open("http://localhost:7777/");
            Configuration.holdBrowserOpen=true;
            $("[name=login]").setValue("vasya");
            $("[name=password]").setValue("creative");
            $("button[data-test-id='action-login']").click();
            $(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        }
 }

