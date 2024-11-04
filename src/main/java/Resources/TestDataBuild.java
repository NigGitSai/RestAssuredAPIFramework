package Resources;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import pojo.AddPlacePOJO;
import pojo.Location;

public class TestDataBuild extends ExcelUtility {

	
	public TestDataBuild(String filePath) {
		super(filePath);
		
	}

	
	static LinkedHashMap<String,String> excelData;
	
	public AddPlacePOJO addPlacePayload(String name, String language, String address)
	{
	 
		Location location = new Location();
		
		location.setLat(-32.523494);
		location.setLng(31.32532525);
		
		AddPlacePOJO addPlacePojo = new AddPlacePOJO();
		
		addPlacePojo.setLocation(location);
		
		addPlacePojo.setAccuracy(51);
		
		addPlacePojo.setName(name);
		
		addPlacePojo.setPhone_number("(+91) 215 893 3937");
		
		addPlacePojo.setTypes(Arrays.asList("shoe park","shop"));
		
		addPlacePojo.setWebsite("http://google.com");
		
		addPlacePojo.setAddress(address);
		
		addPlacePojo.setLanguage(language);
		return addPlacePojo;
		
	}
	
	public AddPlacePOJO addPlacePayloadFromExcelData(String sheetName,String rowData) throws IOException
	{
		excelData = retrieveExcelSheetData(sheetName,rowData);
		
		
		Location location = new Location();
		
		location.setLat(Double.parseDouble(excelData.get("lat")));
		location.setLng(Double.parseDouble(excelData.get("lng")));
		
		AddPlacePOJO addPlacePojo = new AddPlacePOJO();
		
		addPlacePojo.setLocation(location);
		
		addPlacePojo.setAccuracy(Integer.parseInt(excelData.get("accuracy")));
		
		addPlacePojo.setName(excelData.get("name"));
		
		addPlacePojo.setPhone_number(excelData.get("phone_number"));
		
		String[] typeList = excelData.get("types").split(",");
		
		addPlacePojo.setTypes(Arrays.asList(typeList));
		
		addPlacePojo.setWebsite(excelData.get("website"));
		
		addPlacePojo.setAddress(excelData.get("address"));
		
		addPlacePojo.setLanguage(excelData.get("language"));
		return addPlacePojo;
		
	}
	
	public String deletePlacePayload(String placeID) {
		return "{\"place_id\":\""+placeID+"\"}";
	}
	
	public LinkedHashMap<String,String> retrieveExcelSheetData(String sheetName,String rowData) throws IOException
	{
		
		return returnHashMapForExcelRow(sheetName, rowData);
	}
	
}
