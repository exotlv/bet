package lv.vsikhvart.cucumber.pageobjects;


import com.codeborne.selenide.SelenideElement;
import static lv.vsikhvart.cucumber.util.ParallelCheck.parallelCheck;
import static lv.vsikhvart.cucumber.util.ElementWaiter.isElementVisible;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HomePage {

    private final SelenideElement LOGOUT_BUTTON = $("[data-role='logoutHeaderButton']");
    private final SelenideElement PROFILE = $("[data-role='accountProfileLink']");

    public void checkThatUserLogged() {
        parallelCheck(
                () -> assertThat("Profile is not visible",
                        isElementVisible(PROFILE), is(true))
        );
    }

    public SelenideElement logOutButton() {
        return LOGOUT_BUTTON;
    }

    public SelenideElement flashMessage() {
        return $("[data-role='validationError']");
    }
}


