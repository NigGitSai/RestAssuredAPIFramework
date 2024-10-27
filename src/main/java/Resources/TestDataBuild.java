package Resources;

import java.util.Arrays;

import pojo.AddPlacePOJO;
import pojo.Location;

public class TestDataBuild {

	
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
	
	public String deletePlacePayload(String placeID) {
		return "{\"place_id\":\""+placeID+"\"}";
	}
}
