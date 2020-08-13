package lv.vsikhvart.cucumber.util;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.util.Optional;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.sleep;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class ElementWaiter {

    private ElementWaiter() {
        throw new IllegalStateException("Util class");
    }

    private static final Integer DEFAULT_WAIT_TIME_MILLIS = 7000;

    public static boolean isElementVisible(SelenideElement e, Integer timeoutMillis) {
        try {
            e.waitUntil(appear, timeoutMillis);
            return true;
        } catch (ElementNotFound ignore) {
            return false;
        }
    }

    public static boolean isElementVisible(SelenideElement e) {
        return isElementVisible(e, DEFAULT_WAIT_TIME_MILLIS);
    }

    public static Optional<ElementsCollection> waitFirstElement(final ElementsCollection c, Condition visibility, long fluentWaitInMillis, long afterSleepInMillis) {
        try {
            return ofNullable(c.first().waitUntil(visibility, fluentWaitInMillis))
                    .map(e -> {
                        sleep(afterSleepInMillis);
                        return c;
                    });
        } catch (ElementNotFound ignore) {
            return empty();
        }
    }

    public static Optional<SelenideElement> waitElement(final SelenideElement e, Condition visibility, long fluentWaitInMillis, long afterSleepInMillis) {
        try {
            return ofNullable(e.waitUntil(visibility, fluentWaitInMillis))
                    .map(se -> {
                        sleep(afterSleepInMillis);
                        return e;
                    });
        } catch (ElementNotFound ignore) {
            return empty();
        }
    }

    public static Optional<SelenideElement> waitElement(final SelenideElement e, Condition visibility, long fluentWaitInMillis) {
        return waitElement(e, visibility, fluentWaitInMillis, DEFAULT_WAIT_TIME_MILLIS);
    }
}
