package lv.vsikhvart.cucumber.pageobjects;


import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class SearchPage {
    private final SelenideElement CASINO = $("[data-role='Casino']");
    private final SelenideElement GAME_THUMB = $("[data-role='gameThumb']");
    private final SelenideElement GAME_TITLE = $x("//h1[contains(text(),'Dragon Stone')]");
    private final SelenideElement SEARCH_BUTTON = $("[data-role='searchBtn']");
    private final SelenideElement SEARCH_FIELD = $("[data-role='searchInput']");

    public void casinoSection() {
        CASINO.shouldBe(visible).click();
    }

    public void enterSearchData(String data) {
        SEARCH_BUTTON.scrollIntoView(true).shouldBe(visible).click();
        SEARCH_FIELD.setValue(data);
    }

    public void clickOnGame() {
        GAME_THUMB.shouldBe(visible).click();
    }

    public void checkGamePage() {
        GAME_TITLE.shouldBe(visible);
    }
}
