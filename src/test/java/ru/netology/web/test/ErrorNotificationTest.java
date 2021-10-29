package ru.netology.web.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ErrorNotificationTest {
    DataHelper.Card card1 = DataHelper.getFirstCardInfo();
    DataHelper.Card card2 = DataHelper.getSecondCardInfo();


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotTransferWithInvalidCardNumber() {
        final Faker faker = new Faker(Locale.forLanguageTag("ru"));
        String cardNumber = faker.business().creditCardNumber();
        var dashboardPage = new DashboardPage();
        int amount = 1000;
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(amount, cardNumber);
        $("[data-test-id=error-notification]").shouldBe(appear, Duration.ofSeconds(7))
                .shouldHave(text("Ошибка! Произошла ошибка"));
    }

    @Test
    void shouldNotTransferMoreThanThereIsOnCard() {
        var dashboardPage = new DashboardPage();
        int amount = 10500;
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(amount, card1.getNumber());
        $("[data-test-id=error-notification]").shouldBe(appear, Duration.ofSeconds(7));
    }

}
