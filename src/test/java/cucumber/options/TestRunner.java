package cucumber.options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features/AddPlaceWithTestData.feature",
		glue = {"stepDefinitions","Hooks"},
				tags = "",
		dryRun = false,
		monochrome = true,
		plugin = {
				"pretty",
				"html:target/cucumber-reports.html",
				"json:target/jsonReports/cucumber-reports.json",
				"junit:target/cucumber-reports.xml",
				"rerun:target/rerun.txt"
				
		}
		
		
		
)

public class TestRunner {

}
