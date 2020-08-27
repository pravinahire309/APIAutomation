Feature: Validating Place API's'

@AddPlace
Scenario Outline: Verify place is successfully added using AddPlaceAPI
	Given Add place payload "<name>"  "<language>" "<address>"
	When user calls "AddplaceAPI" with "POST" http requestt
	Then the API call got success with Status code as 200
	And "status" in the response body is "OK"
	And "scope" in the response body is "APP"
	And verify place_id created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|name	| language	| address	|
	|AAhouse|English	|Pune		|
	|BBHouse|Hindi		|Mumbai		|
	|CCHouse|Marathi	|Nashik		|
	
@DeletePlace
Scenario: Verify if Delete Place Functionality is working correctly
	 Given DeletePlace Payload 
  	 When user calls "deletePlaceAPI" with "POST" http requestt
     Then the API call got success with Status code as 200
     And "status" in the response body is "OK"
     
     
