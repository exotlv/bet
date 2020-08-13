package lv.vsikhvart.cucumber.hooks;


import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import lv.vsikhvart.cucumber.core.DriverBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.MDC;

import java.io.File;
import java.util.UUID;

@Slf4j
public class Hooks {

    @Before
    public void setup(Scenario scenario) throws Exception {
        MDC.put("scenarioId", "scenarioId:" + UUID.randomUUID().toString());
        DriverBase.instantiateDriverObject();
        String sessionId = ((RemoteWebDriver) DriverBase.getDriver()).getSessionId().toString();
        log.info("Starting Scenario: \"" + scenario.getName() + "\" with Session ID: " + sessionId);
        DriverBase.getDriver().manage().deleteAllCookies();
        DriverBase.getDriver().manage().window().maximize();
//        DriverBase.getDriver().manage().window().setSize(new Dimension(1600,900));

    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverBase.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
                FileUtils.writeByteArrayToFile(new File(String.format("target\\%s.png", scenario.getName())), screenshot);
            } catch (WebDriverException | ClassCastException wde) {
                log.error(wde.getMessage());
            }
        }
        log.info(String.format("Ending Scenario: \"%s\"", scenario.getName()) + " result: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
        DriverBase.closeDriverObjects();
    }
}
