package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")

	public void beforeScenario() throws IOException{


		//execute this code only when place id is null 
		StepDefination m = new StepDefination();

		if(StepDefination.place_id==null){
			m.add_place_payload("Shetty", "French", "Satana");
			m.user_calls_with_http_requestt("AddplaceAPI", "POST");
			m.verify_placeid_created_maps_to_using("Shetty", "getPlaceAPI");
			
		}


	}

}
