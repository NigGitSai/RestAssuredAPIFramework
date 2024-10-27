package Resources;

public enum APIResources {

	AddPlaceRequest("maps/api/place/add/json"),
	
	GetPlaceRequest("maps/api/place/get/json"),
	
	DeletePlaceRequest("maps/api/place/delete/json"),
	
	PUTPlaceRequest("maps/api/place/update/json");
	
	private String resource;
	
	APIResources(String resource)
	{
		this.resource = resource;
	}
	
	public String getResource()
	{
		return this.resource;
	}
}
