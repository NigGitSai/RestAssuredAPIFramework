package Hooks;

import java.io.IOException;



import Resources.ExtentReportListener;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import stepDefinitions.StepDefinitions;

public class Hooks  extends ExtentReportListener{

	StepDefinitions stepDef;
	
	@BeforeAll
	public static void initializeReport()
	{
		startReport();
	}
	
	@Before // Runs before each scenario
    public void setUp(Scenario scenario) {
        // Initialize ExtentReports only once
        
        
        createTest(scenario.getName());
        
      
    }

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		stepDef =  new StepDefinitions();



		if(StepDefinitions.place_id.equals(""))
		{
			stepDef.add_place_payload_with_and("Ck House","Arabic","32, side layout, neww 09");

			stepDef.user_calls_with_http_request("AddPlaceRequest","POST");

			stepDef.user_calls_with_http_request("GetPlaceRequest", "GET");
		}
	}

	@After // Runs after each scenario
    public void tearDown(Scenario scenario) {
		
        if (scenario.isFailed()) {
           testStatus("fail", "Scenario failed : "+scenario.getName());
        } else {
        	 testStatus("pass", "Scenario passed : "+scenario.getName());
        }
        extent.flush();
    }
}
