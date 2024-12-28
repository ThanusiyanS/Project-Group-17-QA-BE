package tests.runners;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"tests.steps"},
        plugin = {"pretty", "html:target/cucumber-reports"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
