
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;

import io.restassured.path.json.JsonPath;
import pojo.GetCourse;
import pojo.WebAutomation;



public class demo {

	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub

		String[] titles ={"Selenium WebDriver Java","Cypress"};
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWtgzh6Uyi48ZcXoccRWQrXyxy36pcBQ2tKcvfdEw6XMx2zkKc4nMfO8iv1Oxk6sKFlJfQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";

		String partialcode = url.split("code=")[1];

		String code = partialcode.split("&scope")[0];

		System.out.println("This is the code"+code);

		String response = given().urlEncodingEnabled(false).queryParams("code", code).queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").queryParams("grant_type", "authorization_code").when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();				//.queryParams("state", "verifyfjdss")
				//.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")// .queryParam("scope",// "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")


		System.out.println("This is the response:"+response);

		JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");
        System.out.println("This is the access token"+accessToken);

		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

		//System.out.println("This is the final"+gc);
		
		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		
		gc.getCourses().getApi().get(1).getCourseTitle();
		
		List<WebAutomation> apiWeb =gc.getCourses().getWebAutomation();
		
		for(int i=0;i<apiWeb.size();i++)
		{
			if(apiWeb.get(i).getCourseTitle().equalsIgnoreCase("Selenium Webdriver Java"))
			{
				System.out.println(apiWeb.get(i).getPrice());
			}
		}
		
		//Get the course names of WebAutomation
		ArrayList<String> a = new ArrayList<String>();
		
		for(int j=0;j<apiWeb.size();j++)
		{
				a.add(apiWeb.get(j).getCourseTitle());
		}
		
		List<String> expectedVal=Arrays.asList(titles);
		
		Assert.assertTrue(a.equals(expectedVal));

	}

}


