package resources;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req ;
	public RequestSpecification requestSpecification() throws IOException{

		// This logic is to ensure that when there are multiple tests in execution, each test caselg should be logged.

		if(req==null){
			PrintStream log = new PrintStream (new FileOutputStream("logging.txt"));

			//RestAssured.baseURI="https://rahulshettyacademy.com";
			req =new RequestSpecBuilder().setBaseUri(getGlobalValue("baseurl")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();

			return req;
		}

		return req;
	}

	// This logic is to ensure to read values from global.properties 

	public static String getGlobalValue(String key) throws IOException{

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream ("C:\\Users\\Pravin\\workspace\\APIAutomation\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}

	// THis is to parse response of Json

	public String getJsonPath(Response response, String Key){
		String resp=response.asString();
		JsonPath js= new JsonPath(resp);
		return js.get(Key).toString();


	}


}
