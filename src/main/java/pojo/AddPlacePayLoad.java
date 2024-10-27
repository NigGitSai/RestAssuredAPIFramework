package pojo;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddPlacePayLoad {

	public static void main(String[] args) throws JsonProcessingException {
		// TODO Auto-generated method stub

		Location location = new Location();
		
		location.setLat(-32.523494);
		location.setLng(31.32532525);
		
		AddPlacePOJO addPlacePojo = new AddPlacePOJO();
		
		addPlacePojo.setLocation(location);
		
		addPlacePojo.setAccuracy(51);
		
		addPlacePojo.setName("Frontline house");
		
		addPlacePojo.setPhone_number("(+91) 215 893 3937");
		
		addPlacePojo.setTypes(Arrays.asList("shoe park","shop"));
		
		addPlacePojo.setWebsite("http://google.com");
		
		addPlacePojo.setLanguage("French-IN");
		
		ObjectMapper mapper = new ObjectMapper();
		
		String inpJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(addPlacePojo);
		
		System.out.println(inpJson);
	}

}
