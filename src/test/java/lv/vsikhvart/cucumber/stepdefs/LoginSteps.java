package lv.vsikhvart.cucumber.stepdefs;


import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;
import lv.vsikhvart.cucumber.pageobjects.HomePage;
import lv.vsikhvart.cucumber.pageobjects.LoginPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;

import static com.codeborne.selenide.Condition.*;

@ScenarioScoped
public class LoginSteps {

    @Inject
    private LoginPage loginPage;
    @Inject
    private HomeSteps homeSteps;
    @Inject
    public HomePage homePage;

    @Given("open Optibet page")
    public void openOptibetPage() {
        loginPage.optibetOpen();
    }

    @Then("^check login form$")
    public void checkLoginForm() {
        loginPage.checkLoginForm();
    }

    @When("^user logged in with the following account: \"([^\"]*)\", \"([^\"]*)\"$")
    public void logIn(String login, String password) {
        loginPage.logIn(login, password);
    }

    @Given("open login form")
    public void openLoginPage() {
        loginPage.open();
    }

    @And("^flash error message is shown with \"([^\"]*)\" text$")
    public void flashErrorMessageWithText(String message) {
        SelenideElement error = homePage.flashMessage();

        homePage.flashMessage().waitUntil(visible, 10000, 200);

        if (error.has(not(text(message)))) {
            throw new RuntimeException("Another error was received");
        }
    }
}
