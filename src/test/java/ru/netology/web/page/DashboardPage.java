package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement reload = $("[data-test-id=action-reload]");

    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement deposit =
            $("[data-test-id=action-deposit]");

    private String card1Id = ("92df3f1c-a033-48e6-8390-206f6b1f56c0");
    private String card2Id = ("0f3f5c2a-249e-4c3d-8287-09f7a039391d");



    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String id) {
        val text = cards.find(Condition.attributeMatching("data-test-id", id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage transferMoney(String id) {
        cards.find(Condition.attributeMatching("data-test-id", id)).find(String.valueOf(deposit));
        this.deposit.click();
        return new TransferPage();
    }

}
