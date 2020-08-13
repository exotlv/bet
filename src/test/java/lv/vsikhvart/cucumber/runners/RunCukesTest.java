package lv.vsikhvart.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * This file should not be used. cucumber-jvm-parallel-plugin is used by default in pom.xml
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber/all", "json:target/cucumber/cucumber-all.json"},
        features = {"src/test/resources/features"},
        tags = {"@all"},
        glue = {"lv.vsikhvart.cucumber.stepdefs", "lv.vsikhvart.cucumber.hooks"}
)
public class RunCukesTest {

}
