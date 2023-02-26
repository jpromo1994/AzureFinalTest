import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	//1. Print No of courses returned by API

	//2.Print Purchase Amount

	//3. Print Title of the first course

	//4. Print All course titles and their respective Prices

	//5. Print no of copies sold by RPA Course

	//6. Verify if Sum of all Course prices matches with Purchase Amount
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.coursePrice());
		//1. Print No of courses returned by API
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//2.Print Purchase Amount
		int totaleAmount  = js.getInt("dashboard.purchaseAmount");
		System.out.println(totaleAmount);
		
		//3. Print Title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//4. Print All course titles and their respective Prices
		for(int i=0; i<count; i++)
		{
			System.out.println(js.get("courses["+i+"].title").toString());
			System.out.println(js.get("courses["+i+"].price").toString());
				
		}
		
		//5. Print no of copies sold by RPA Course
		System.out.println("Print no of copies sold by RPA Course");
		
		for(int i=0; i<count; i++)
		{
		String courseTitle=js.get("courses["+i+"].title").toString();
		if(courseTitle.equalsIgnoreCase("RPA"))
		{
			System.out.println(js.get("courses["+i+"].copies").toString());
			break;
		}
			
			
		//6. Verify if Sum of all Course prices matches with Purchase Amount\
		//Nasa class SumValidation
		/*
		 * int newCoursePrice=0, coursePrice=0; for(int x=0; x<count; x++) {
		 * coursePrice=js.getInt("courses["+i+"].price"); newCoursePrice =
		 * newCoursePrice + coursePrice; } System.out.println(newCoursePrice);
		 * if(newCoursePrice==totaleAmount) {
		 * System.out.println("Sum of all courses prices matched: All Course sum:"
		 * +newCoursePrice+" and Total Sum:"+coursePrice+""); }
		 */
	}
	}
}
