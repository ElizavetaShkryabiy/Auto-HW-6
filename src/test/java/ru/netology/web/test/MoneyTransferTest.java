package ru.netology.web.test;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
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

    @AfterEach
    void shouldCheckBalance() {
        var dashboardPage = new DashboardPage();
        dashboardPage.getCardBalance(card1.getVisiblePart());
        dashboardPage.getCardBalance(card2.getVisiblePart());
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var dashboardPage = new DashboardPage();
        var neededCardBalance = dashboardPage.getCardBalance(card1.getVisiblePart());
        int amount = 100;
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(neededCardBalance, amount, card1.getNumber());
    }




}

