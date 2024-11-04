package Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Option;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils extends ExtentReportListener{

	RequestSpecBuilder reqBuilder ;

	protected static JsonPath js;
	DocumentContext context;
	Configuration configuration;
	List<Map<String, Object>> jsonNode;
	protected static String actVal;
	Map<String,Object> hm;
	static Properties prop;
	static FileInputStream fis;

	public static RequestSpecification reqSpecification;

	public RequestSpecification reqSpecification() throws IOException
	{
		if(reqSpecification==null)
		{
			PrintStream stream = new PrintStream(new FileOutputStream("./Logs/Logs.txt"));
			reqBuilder = new RequestSpecBuilder();
			reqSpecification = reqBuilder.setBaseUri(readProperties("baseUrl")).setContentType(ContentType.JSON)
					.addFilter(RequestLoggingFilter.logRequestTo(stream))
					.addFilter(ResponseLoggingFilter.logResponseTo(stream))
					.addQueryParam("key", "qaclick123").build();

			return reqSpecification;
		}
		return reqSpecification;
	}

	public static String readProperties(String key) throws IOException
	{
		if(prop==null)
		{
			prop = new Properties();
			fis = new FileInputStream(new File("./src/test/resources/config/config.properties"));
			prop.load(fis);
		}

		return prop.getProperty(key);
	}

	public JsonPath initializeRestJsonPathRef(Response response)
	{
		return js = new JsonPath(response.asString());
	}

	public Object getResponseJsonValueUsingRestAssured(Response response,String path)
	{
		js = initializeRestJsonPathRef(response);


		return js.get(path);
	}


	public Object getResponseJsonValueUsingJaywayJsonPath(Response response,String path)
	{
		configuration = Configuration.defaultConfiguration(); 

		configuration = configuration.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);

		context = com.jayway.jsonpath.JsonPath.using(configuration).parse(response.asString());
		return context.read(path);
	}
	/**
	 * 
	 * @param expected - Expected Response value
	 * @param actual - Actual Response value
	 * @param verificationMsg - Verification Message
	 */
	public void verifyReponseExpvsActual(Object expected,Object actual,String verificationMsg)
	{
		if(expected.equals(actual))
		{
			testStatus("pass", verificationMsg+" . Expected value : "+expected+" == Actual value : "+actual);
		}
		else
		{
			testStatus("fail", verificationMsg+" . Expected value : "+expected+" not equals Actual value :"+actual);
		}
	}

	public List<Map<String, Object>> returnListfromJsonResponse(Response response, String path)
	{
		//js = initializeRestJsonPathRef(response);
		String strResponse = response.asString();
		js = new JsonPath(strResponse);
		System.out.println(js.getList(path));
		return js.getList(path);
	}

	public Map<String,Object> returnMapForMatchingValueFromJsonArray(Response response,String listPath,String key,String expJsonValue)
	{

		jsonNode = returnListfromJsonResponse(response,listPath);

		if (jsonNode == null || jsonNode.isEmpty()) {
			return null; // Return null if the JSON node is empty or missing
		}

		for (Map<String, Object> node : jsonNode) {
			actVal = (String) node.get(key);
			if (actVal != null && actVal.equalsIgnoreCase(expJsonValue)) {
				return node; // Return matching node directly
			}
		}

		return null;

	}

	public long returnResponseTime(Response response)
	{
		return response.getTime();
	}

	public String returnResponseHeader(Response response,String header)
	{
		return response.getHeader(header);
	}

	public String returnStatusLine(Response response)
	{
		return response.getStatusLine();
	}

	public String returnStatusCode(Response response)
	{
		return String.valueOf(response.statusCode());
	}
}
