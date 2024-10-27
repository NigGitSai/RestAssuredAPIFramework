package Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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

	JsonPath js;
	DocumentContext context;
	Configuration configuration;

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
		Properties prop = new Properties();


		FileInputStream fis = new FileInputStream(new File("./src/test/resources/config/config.properties"));
		prop.load(fis);

		return prop.getProperty(key);
	}


	public Object getResponseJsonValueUsingRestAssured(Response response,String path)
	{
		js = new JsonPath(response.asString());

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
}