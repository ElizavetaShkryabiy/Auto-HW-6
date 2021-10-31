package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

public class ErrorNotificationTest {
    DataHelper.Card card1 = DataHelper.getCardInfo(1);
    DataHelper.Card card2 = DataHelper.getCardInfo(2);


    @BeforeEach
    void setUp() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldNotTransferWithInvalidCardNumber() {
        var dashboardPage = new DashboardPage();
        var cardNumber = DataHelper.getInvalidCardInfo().getNumber();
        int amount = 1000;
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(amount, cardNumber);
        var transferPage = new TransferPage();
        transferPage.errorNotification();
    }

    @Test
    void shouldNotTransferMoreThanThereIsOnCard() {
        var dashboardPage = new DashboardPage();
        int amount = 10500;
        dashboardPage.transferMoney(card2.getDepositButton()).successfulTransfer(amount, card1.getNumber());
        var transferPage = new TransferPage();
        transferPage.errorNotification();
    }

}
