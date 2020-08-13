package lv.vsikhvart.cucumber.stepdefs;


import lv.vsikhvart.cucumber.pageobjects.HomePage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;

import javax.inject.Inject;

import static com.codeborne.selenide.Condition.*;

@ScenarioScoped
public class HomeSteps {
    @Inject public HomePage homePage;
    @Inject private lv.vsikhvart.cucumber.stepdefs.HomeSteps homeSteps;

    @Then("^check that user is logged in$")
    public void checkThatUserIsLoggedIn() {
        homePage.checkThatUserLogged();
    }

    @When("^logout user$")
    public void logoutUser() {
        homePage.logOutButton().shouldBe(visible).click();
    }
}