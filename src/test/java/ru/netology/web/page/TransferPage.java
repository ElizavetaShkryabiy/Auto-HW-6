package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TransferPage {
    private SelenideElement heading = $$(".heading").find(text("Пополнение карты"));
    private SelenideElement sum = $("[data-test-id=amount] input");

    public SelenideElement getSum() {
        return sum;
    }


    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement transfer = $("[data-test-id=action-transfer]");

    public TransferPage() {
        heading.shouldBe(visible);
    }
    public DashboardPage successfulTransfer(int neededCardBalance, int amount, String cardId) {

        sum.setValue(String.valueOf(amount));
        from.setValue(cardId);
        if (amount<neededCardBalance){
        transfer.click();
            return new DashboardPage();
        }else{return null;}

    }

}
