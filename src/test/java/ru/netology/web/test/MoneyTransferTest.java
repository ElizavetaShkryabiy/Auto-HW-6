package ru.netology.web.test;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

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


    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var dashboardPage = new DashboardPage();
        int amount = 100;
        val getCardFromBalanceBefore = dashboardPage.getCardBalance(card1.getVisiblePart());
        val getCardToBalanceBefore = dashboardPage.getCardBalance(card2.getVisiblePart());
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(amount, card1.getNumber());
        assertEquals(getCardFromBalanceBefore - amount, dashboardPage.getCardBalance(card1.getVisiblePart()));
        assertEquals(getCardToBalanceBefore + amount, dashboardPage.getCardBalance(card2.getVisiblePart()));

    }

    @Test
    void shouldNotTransferWithMinusAmount() {
        var dashboardPage = new DashboardPage();
        int amount = -1_000;
        val getCardFromBalanceBefore = dashboardPage.getCardBalance(card1.getVisiblePart());
        val getCardToBalanceBefore = dashboardPage.getCardBalance(card2.getVisiblePart());
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(amount, card1.getNumber());
        assertEquals(getCardFromBalanceBefore - (-amount), dashboardPage.getCardBalance(card1.getVisiblePart()));
        assertEquals(getCardToBalanceBefore + (-amount), dashboardPage.getCardBalance(card2.getVisiblePart()));
    }

}

