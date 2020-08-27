package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import static org.junit.Assert.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils {

	ResponseSpecification resspec;
	RequestSpecification res;

	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();


	@Given("Add place payload {string}  {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException{

		res=given().spec(requestSpecification()).body(data.addPlaceApi(name,language,address ));

	}

	@When("user calls {string} with {string} http requestt")
	public void user_calls_with_http_requestt(String resource, String method) {
		/*constructor will be called with value of resource which you pass */
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("POST"))
			response= res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response= res.when().get(resourceAPI.getResource());
	}

	@Then("the API call got success with Status code as {int}")
	public void the_api_call_got_success_with_status_code_as(Integer int1) {

		assertEquals(response.getStatusCode(),200);
	}


	@And("{string} in the response body is {string}")
	public void in_the_response_body_is(String key, String value)   {

		assertEquals(getJsonPath(response,key), value);
	}

	@And("verify place_id created maps to {string} using {string}")
	public void verify_placeid_created_maps_to_using(String expectedname, String resource) throws IOException
	{

		place_id= getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_requestt(resource,"GET");
		String actualname=getJsonPath(response,"name");
		assertEquals(actualname , expectedname);
	}


	@Given("DeletePlace Payload")
	public void deleteplace_payload() throws IOException  {


		given().spec(requestSpecification()).body(data.deletePlacepayload(place_id));


	}



}
