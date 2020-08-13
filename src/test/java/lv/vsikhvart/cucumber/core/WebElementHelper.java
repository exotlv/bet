package lv.vsikhvart.cucumber.core;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static lv.vsikhvart.cucumber.config.Constants.WAIT_NORMAL_SECONDS;
import static lv.vsikhvart.cucumber.config.Constants.WAIT_SHORT_SECONDS;
import static lv.vsikhvart.cucumber.core.DriverBase.getDriver;
import static org.junit.Assert.fail;

@Slf4j
public class WebElementHelper {
    public static boolean isElementDisplayed(WebElement webElement) {
        return isElementDisplayed(webElement, WAIT_SHORT_SECONDS);
    }

    public static boolean isElementDisplayed(WebElement webElement, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            wait.until(ExpectedConditions.visibilityOf(webElement));
            return webElement.isDisplayed();
        } catch (NoSuchElementException | TimeoutException ne) {
            return false;
        } catch (StaleElementReferenceException ex) {
            return isElementDisplayed(webElement, timeOut);
        }
    }

    public static void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), WAIT_SHORT_SECONDS);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException te) {
            log.error(te.getMessage());
            fail("Element '" + element + "' not found after waiting for it's visibility");
        } catch (NoSuchElementException ne) {
            log.error(ne.getMessage());
            fail("Element '" + element + "' not found, unable to locate it in the DOM");
        } catch (Exception e) {
            log.error(e.getMessage());
            fail("Element '" + element + "' not found");
        }
    }

    public static void waitForVisibility(By element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        } catch (TimeoutException te) {
            log.error(te.getMessage());
            fail("Element '" + element + "' not found after waiting for it's visibility");
        } catch (NoSuchElementException ne) {
            log.error(ne.getMessage());
            fail("Element '" + element + "' not found, unable to locate it in the DOM");
        } catch (Exception e) {
            log.error(e.getMessage());
            fail("Element '" + element + "' not found");
        }
    }

    public static void sendKeys(WebElement webElement, CharSequence... keysToSend) {
        WebDriverWait wait = new WebDriverWait(getDriver(), WAIT_SHORT_SECONDS);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        webElement.sendKeys(keysToSend);
    }

    public static void click(WebElement webElement) {
        isElementDisplayed(webElement);
        waitForElementToBeClickable(webElement);
        webElement.click();
    }

    public static void click(By webElement) {
        waitForElementToBeClickable(webElement).click();
    }

    public static void selectByVisiableText(WebElement webElement, String value) {
        Select select = new Select(webElement);
        select.selectByVisibleText(value);
    }

    public static void selectByValue(WebElement webElement, String value) {
        Select select = new Select(webElement);
        select.selectByValue(value);
    }

    public static void selectByIndex(WebElement webElement, int index) {
        Select select = new Select(webElement);
        select.selectByIndex(index);
    }

    public static void waitForElementToBeClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(getDriver(), WAIT_SHORT_SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));

    }

    public static WebElement waitForElementToBeClickable(By webElement) {
        WebDriverWait wait = new WebDriverWait(getDriver(), WAIT_SHORT_SECONDS);
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static String getText(WebElement webElement) {
        waitForVisibility(webElement);
        return webElement.getText();
    }

    public static String getValue(WebElement webElement) {
        waitForVisibility(webElement);
        return webElement.getAttribute("value");
    }

    public static void selectByIndex(WebElement dropdownElement, List<WebElement> webElementListFromDrpdwn, int index) {
        click(dropdownElement);
        click(webElementListFromDrpdwn.get(index));
    }

    public static String getAttribute(WebElement webElement, String attribute) {
        waitForVisibility(webElement);
        return webElement.getAttribute(attribute);
    }

    public static void navigateToPage(String url) {
        log.info("Navigating to: " + url);
        getDriver().get(url);
    }

    public static void scrollToCenterOfScreen(WebElement webElement) {
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) getDriver()).executeScript(scrollElementIntoMiddle, webElement);
    }

    public static void setColorForDebug(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background-color:coral')", element);
    }

    public static void clickWithOffset(WebElement element) {
        Actions builder = new Actions(getDriver());
        Action action = builder.moveToElement(element, 2, 2).click().build();
        action.perform();
    }

    public static void waitForCookie(String cookieName) {
        new WebDriverWait(getDriver(), WAIT_SHORT_SECONDS).until(d -> d.manage().getCookieNamed(cookieName) != null);
    }

    public static boolean isElementPresentByLocator(By locator) {
        return getDriver().findElements(locator).size() != 0;
    }

    public static void waitUntilElementToDisappear(By by, int timeout) {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), timeout);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static String getElementColor(WebElement element) {
        waitForVisibility(element);
        return element.getCssValue("color");
    }

    public static void waitForLoadingIndicatorDissapear() {
        try {
            new WebDriverWait(getDriver(), WAIT_SHORT_SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.className("loading-indicator")));
        } catch (TimeoutException e) {
            log.debug("Progress Indicator is not displayed: " + e);
        } catch (NoSuchElementException ne) {
            log.debug("Progress Indicator is not present in DOM: " + ne);
        }
        new WebDriverWait(getDriver(), WAIT_NORMAL_SECONDS).until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading-indicator")));
    }
}
