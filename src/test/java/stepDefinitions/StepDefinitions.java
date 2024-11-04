package stepDefinitions;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import Resources.APIResources;
import Resources.ExcelUtility;
import Resources.TestDataBuild;
import Resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import pojo.AddPlacePOJO;
import pojo.Location;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;	

public class StepDefinitions extends Utils {

	RequestSpecification reqSpecification;
	ResponseSpecBuilder responseBuilder;

	ResponseSpecification responseSpec;

	Response response ;

	String resource;
	TestDataBuild testData ;
	ExcelUtility excelUtility;
	static String excelFilePath;
	static ObjectMapper mapper;
	static AddPlacePOJO addPlacePOJO;
	static String sheetName;
	static String rowData;

	public static String place_id ="";

	@Given("Add Place Payload with {string} {string}  and {string}")
	public void add_place_payload_with_and(String name, String language, String address) throws IOException {

		mapper = new ObjectMapper();

		String inpJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testData.addPlacePayload(name,language,address));



		reqSpecification = given().log().all().spec(reqSpecification()).body(inpJson);

		testStatus("info", "Add Place Payload created");
	}

	@When("user calls {string} with POST HTTP Request")
	public void user_calls_with_post_http_request(String string) {
		//responseBuilder = new ResponseSpecBuilder();

		//responseBuilder.expectStatusCode(200);

		response = reqSpecification.when().post("maps/api/place/add/json").then().log().all().extract().response();

	}

	@When("user calls {string} with {string} HTTP Request")
	public void user_calls_with_http_request(String resource, String httpMethod) {

		//APIResources.valueOf() will call the constructor and assigns the value of the API resource defined in Enum
		//to the resource private variable 
		APIResources apiResource = APIResources.valueOf(resource);

		resource = apiResource.getResource();

		switch(httpMethod.toUpperCase())
		{
		case "GET":
		{

			place_id = getResponseJsonValueUsingRestAssured(response, "place_id").toString();
			response = reqSpecification.
					queryParam("place_id",place_id )
					.when().get(resource).then().log().all().extract().response();

			break;
		}

		case "POST":
		{
			response = reqSpecification.when().post(resource).then().log().all().extract().response();
			break;
		}

		case "DELETE":
		{
			response = reqSpecification.when().delete(resource).then().log().all().extract().response();
			break;
		}

		case "PUT":
		{
			response = reqSpecification.when().put(resource).then().log().all().extract().response();
			break;
		}
		default:
		{
			System.err.println("Not a valid HTTP method ");
		}
		}
		testStatus("info", "User calls "+resource+ "with "+httpMethod+" request");

	}

	@Then("verify the status code is {int}")
	public void verify_the_status_code_is(Integer expStatusCode) {

		verifyReponseExpvsActual(returnStatusCode(response),String.valueOf(expStatusCode),"Status code verification");
		Assert.assertEquals(returnStatusCode(response), String.valueOf(expStatusCode));


	}


	@Then("{string} is {string}  in response body")
	public void is_in_response_body(String key, String expValue) {


		verifyReponseExpvsActual(expValue,getResponseJsonValueUsingRestAssured(response, key).toString(),"Status code verification");
		Assert.assertEquals(expValue, getResponseJsonValueUsingRestAssured(response, key).toString());
	}


	@Then("verify place_Id created maps to {string} using getPlaceAPI")
	public void verify_place_id_created_maps_to_using_get_place_api(String name) {

		verifyReponseExpvsActual(name,getResponseJsonValueUsingRestAssured(response, "name").toString(),"Name in Get Place API");
		Assert.assertEquals(name, getResponseJsonValueUsingRestAssured(response, "name").toString());

	}

	@Given("Delete Place Payload")
	public void delete_place_payload() throws IOException {

		reqSpecification = given().spec(reqSpecification()).body(testData.deletePlacePayload(place_id));

		testStatus("info", "Delete place payload  created");
	}

	@Given("Configure Excel Data {string}")
	public void configure_excel_data(String excelSheetName) {
		try
		{
			excelFilePath = "./src/test/resources/TestData/"+excelSheetName+".xlsx";
			testData = new TestDataBuild(excelFilePath);
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	@Given("Configure {string} Request payload with excel data")
	public void configure_request_payload_with_excel_data(String string, io.cucumber.datatable.DataTable dataTable) throws IOException {
		try
		{
			List<Map<String, String>> data = 	dataTable.asMaps(String.class, String.class);
			this.sheetName = data.get(0).get("SheetName");
			this.rowData = data.get(0).get("RowData");
			
			addPlacePOJO = testData.addPlacePayloadFromExcelData(this.sheetName, this.rowData);

			mapper = new ObjectMapper();

			String inpJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(addPlacePOJO);
			reqSpecification = given().log().all().spec(reqSpecification()).body(inpJson);

			testStatus("info", "Add Place Payload created");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	@Then("verify {string} in response body equals {string} passed in add request")
	public void verify_in_response_body_equals_passed_in_add_request(String responseJsonNode, String excelColumnData) throws IOException {
	    
		String expValue = testData.retrieveExcelSheetData(this.sheetName, this.rowData).get(excelColumnData);
		
		String actValue = getResponseJsonValueUsingRestAssured(response,responseJsonNode).toString();
		
		verifyReponseExpvsActual(expValue,actValue,"Response body "+responseJsonNode+" verification");
		Assert.assertEquals(expValue, actValue);

	}

}
