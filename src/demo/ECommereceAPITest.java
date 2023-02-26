package demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;


public class ECommereceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("jpromo1994@gmail.com");
		loginRequest.setUserPassword("Abc123$%");
		
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().spec(req).body(loginRequest);
		LoginResponse loginResponse = reqLogin.when().post("api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		System.out.println(loginResponse.getToken());
		String token = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		
		//Add Product
		RequestSpecification addProductBaseReq  = new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification reqAddProduct = given().spec(req).body(addProductBaseReq).param("productName", "gwapo")
		.param("productAddedBy", "63f3d150568c3e9fb11bc194")
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice","11500")
		.param("productDescription","Addias Originals")
		.param("productFor", "woman")
		.multiPart("productImage", new File("C://Users//LENOVO//Pictures//Capture.jpg"));
		
		String addProductResponse = reqAddProduct.when().post("api/ecom/auth/login").
				then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");
		
		//Create Order
		RequestSpecification createOrderBaseReq  = new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON)
				.build();
		
		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setCountry("Philippines");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		orderDetailList.add(orderDetail);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq = given().spec(createOrderBaseReq).body(orders);
		String responseAddOrder = createOrderReq.when().post("api/ecom/order/create-order").then().log().all().extract().response().toString();
		System.out.println(responseAddOrder);
		
		//Delete Product
		RequestSpecification deleteProdBaseReq  = new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
		
		String deleteProductResponse = deleteProdReq.when().delete("https://rahulshettyacademy.com/api/ecom/product/delete-product/{productId}").then().log().all()
		.extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
		
		
		
		
		
		
		
		
		
	}



}
