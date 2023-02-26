import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses()
	{
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int totalAmount = 0;
		JsonPath js1 = new JsonPath(payload.coursePrice());
		int count = js1.getInt("courses.size()");
		for(int i=0;i<count;i++)
		{
			int price=js1.get("courses["+i+"].price");
			int copies=js1.get("courses["+i+"].copies");
			int amount = price * copies;

			 totalAmount = totalAmount + amount;
		}
		System.out.println(totalAmount);
		int purchaseAmount = js1.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(totalAmount, purchaseAmount);
	}
}
