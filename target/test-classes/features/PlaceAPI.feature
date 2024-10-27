@PlaceAPI
Feature: Verify Place API's

@AddPlace
Scenario Outline: To verify Add Place API

Given Add Place Payload with "<Name>" "<Language>"  and "<Address>"
When user calls "AddPlaceRequest" with "POST" HTTP Request
Then verify the status code is 200
And "status" is "OK"  in response body
When user calls "GetPlaceRequest" with "GET" HTTP Request
Then verify place_Id created maps to "<Name>" using getPlaceAPI

Examples:
	|Name           | Language | Address|
#	|Frontline house| English |30, side layout, cohen 09|
	|Backyard house| German |31, side layout, neww 09|
	
@DeletePlace
Scenario: To verify Delete Place API

Given Delete Place Payload
When user calls "DeletePlaceRequest" with "POST" HTTP Request
Then verify the status code is 200
And "status" is "OK"  in response body
