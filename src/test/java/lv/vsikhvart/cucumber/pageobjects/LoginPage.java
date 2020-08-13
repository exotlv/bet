package lv.vsikhvart.cucumber.pageobjects;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static lv.vsikhvart.cucumber.util.ElementWaiter.waitElement;
import static lv.vsikhvart.cucumber.util.ParallelCheck.parallelCheck;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginPage {

    private final SelenideElement USER_NAME = $("[name='email']");
    private final SelenideElement PASSWORD = $("[name='password']");
    private final SelenideElement LOGIN_POP_UP_BUTTON = $("[data-id='login-button']");
    private final SelenideElement LOST_PASSWORD = $("[data-role='loginForgotPasswordButton']");
    private final SelenideElement LOGIN_BUTTON = $("[data-role='loginHeaderButton']");
    private final SelenideElement LOGIN_FORM = $("[data-role='loginDialog']");

    public void checkLoginForm() {
        boolean isLoginVisible = waitElement(LOGIN_FORM, visible, 15000, 500).isPresent();
        assertThat("Login form not visible", isLoginVisible, is(true));
        parallelCheck(
                () -> assertThat("User name not displayed", USER_NAME.isDisplayed(), is(true)),
                () -> assertThat("Password not displayed", PASSWORD.isDisplayed(), is(true)),
                () -> assertThat("Login button name not displayed", LOGIN_POP_UP_BUTTON.isDisplayed(), is(true)),
                () -> assertThat("Lost password name not displayed", LOST_PASSWORD.isDisplayed(), is(true))
        );
    }

    public void logIn(String userName, String password) {

        USER_NAME.shouldBe(visible).setValue(userName);
        PASSWORD.shouldBe(visible).setValue(password);
        LOGIN_POP_UP_BUTTON.shouldBe(visible).click();

        sleep(3000); //time to aggregate request
    }

    public void open() {
        Selenide.open("https://www.optibet.lt/en/login");
        waitForPageLoaded();
    }

    public void optibetOpen() {
        Selenide.open("https://www.optibet.lt/");
    }

    public void loginButtonClick() {
        LOGIN_BUTTON.shouldBe(visible).click();
    }

    public void sendLogin(String userName) {
        waitElement(USER_NAME, visible, 15000, 300);
        USER_NAME.setValue(userName);
    }

    public void sendPassword(String password) {
        waitElement(PASSWORD, visible, 15000, 300);
        PASSWORD.setValue(password);
    }

    public void submit() {
        waitElement(LOGIN_POP_UP_BUTTON, visible, 15000, 300)
                .ifPresent(SelenideElement::click);
    }

    private void waitForPageLoaded() {
        boolean loginFrame = waitElement(LOGIN_FORM, visible, 15000, 1000).isPresent();
        Assert.assertThat("Login form is not loaded", loginFrame, is(true));
    }

}