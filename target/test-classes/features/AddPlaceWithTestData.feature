Feature: Verify Place API's

Background:
Given Configure Excel Data "PlaceTestData" 

@AddPlace
Scenario: To verify Add Place API

Given Configure "Add Place" Request payload with excel data 
|SheetName|RowData|
|AddPlace|AddPlaceTestCase1|
When user calls "AddPlaceRequest" with "POST" HTTP Request
Then verify the status code is 200
And "status" is "OK"  in response body
When user calls "GetPlaceRequest" with "GET" HTTP Request
Then verify "name" in response body equals "name" passed in add request
