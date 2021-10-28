package ru.netology.web.test;


import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    DataHelper.Card card1 = DataHelper.getFirstCardInfo();
    DataHelper.Card card2 = DataHelper.getSecondCardInfo();

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getCardBalance(card1.getVisiblePart());
        assertEquals(10500, firstCardBalance);
        var secondCardBalance = dashboardPage.getCardBalance(card2.getVisiblePart());
        assertEquals(9500, secondCardBalance);
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(100, card1.getNumber());

        assertEquals(10400, dashboardPage.getCardBalance(card1.getVisiblePart()));
        assertEquals(9600, dashboardPage.getCardBalance(card2.getVisiblePart()));
    }


}

