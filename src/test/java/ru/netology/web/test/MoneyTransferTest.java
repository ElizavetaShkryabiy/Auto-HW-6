package ru.netology.web.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @Test
    void shouldNotTransferWithMinusAmount() {
        var dashboardPage = new DashboardPage();

        var neededCardBalance = dashboardPage.getCardBalance(card1.getVisiblePart());
        int amount = -1_000;
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(neededCardBalance, amount, card1.getNumber());
        TransferPage transferPage = new TransferPage();
        assertEquals((-amount),transferPage.getSum().getValue());

    }



}

