package pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPlacePOJO {

	private Location location;
	
	private int accuracy;
	
	private String name;
	
	private String phone_number;
	
	private String address;
	
	private List<String> types;
	
	private String website;
	
	private String language;

	
}
