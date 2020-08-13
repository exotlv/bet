package lv.vsikhvart.cucumber.stepdefs;

import com.google.inject.Inject;
import lv.vsikhvart.cucumber.pageobjects.LoginPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.runtime.java.guice.ScenarioScoped;
import lv.vsikhvart.cucumber.pageobjects.SearchPage;


@ScenarioScoped

public class SearchSteps {

    @Inject
    private SearchPage searchPage;

    @Then("^click on Casino button$")
    public void clickOnCasinoButton() {
        searchPage.casinoSection();
    }

    @And("^enter in search field \"(.+)\" game$")
    public void enterInSearchField(String data) {
        searchPage.enterSearchData(data);
    }

    @Then("^click on game which was found$")
    public void clickOnGameWhichWasFound() {
        searchPage.clickOnGame();
    }

    @Then("^check that game page is loaded with correct game title$")
    public void checkGamePage() {
        searchPage.checkGamePage();
    }

}
