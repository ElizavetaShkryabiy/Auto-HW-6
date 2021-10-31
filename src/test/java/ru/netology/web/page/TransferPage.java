package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TransferPage {
    private SelenideElement heading = $$(".heading").find(text("Пополнение карты"));
    private SelenideElement sum = $("[data-test-id=amount] input");

    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement transfer = $("[data-test-id=action-transfer]");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage successfulTransfer(int amount, String cardId) {

        sum.setValue(String.valueOf(amount));
        from.setValue(cardId);
        transfer.click();
        return new DashboardPage();

    }
    public void errorNotification(){
        $("[data-test-id=error-notification]").shouldBe(appear, Duration.ofSeconds(7))
                .shouldHave(text("Ошибка! Произошла ошибка"));
    }

}
