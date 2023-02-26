package files;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {
	
	public static JsonPath rawToJson(String reponse) {
		JsonPath js1=new JsonPath(reponse);
		return js1;
	}
	
}
