package com.training;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.controller.LibraryController;
import com.training.controller.ProductsPrices;
import com.training.controller.SpecificProduct;
import com.training.controller.ProductsPrices;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName= "CoursesCatalogue")
public class PactConsumerTest {
	
	@Autowired
	private LibraryController libraryController;
	
	@Pact(consumer="BooksCatalogue")
	public RequestResponsePact PactallCourseDetailsConfig(PactDslWithProvider builder) {
		return builder.given("course exist").uponReceiving("getting all couse details")
				.path("/allCourseDetails")
				.willRespondWith()
				.status(200)
				.body(PactDslJsonArray.arrayMinLike(2)
				.stringType("course_name")
				.stringType("id")
				.integerType("price",10)
				.stringType("category").closeObject()).toPact();
	}
	
	@Test
	@PactTestFor(pactMethod="PactallCourseDetailsConfig",port = "9999")
	public void testAllProductSum(MockServer mockServer) throws JsonMappingException, JsonProcessingException  {
		String expectedJson = "{\"booksPrice\":250,\"coursesPrice\":20}";
		libraryController.setBaseUrl(mockServer.getUrl());
		ProductsPrices productPrices =libraryController.getProductPrices();
		ObjectMapper obj = new ObjectMapper();
		String actualJson= obj.writeValueAsString(productPrices);
		Assertions.assertEquals(expectedJson, actualJson);	
	}
	
	@Pact(consumer="BooksCatalogue")
	public  RequestResponsePact getCourseByNameNotExist(PactDslWithProvider builder) {
		
		return builder.given("Course Appium does not exist","name","Appium")
				.uponReceiving("Appium does not exist")
				.path("/getCourseByName/Appium")
				.willRespondWith()
				.status(404)
				.toPact();
						
	}
	
	@Test
	@PactTestFor(pactMethod = "getCourseByNameNotExist",port ="9999")
	public void testByProductNameNotExist(MockServer mockServer) throws JsonMappingException, JsonProcessingException {
		libraryController.setBaseUrl(mockServer.getUrl());
		String expectedJson ="{\"product\":{\"book_name\":\"Appium\",\"id\":\"ttefs36\",\"isbn\":\"ttefs\",\"aisle\":36,\"author\":\"Shetty\"},\"msg\":\"AppiumCategory and price details are not available at this time\"}";
		SpecificProduct specificProduct=libraryController.getProductFullDetails("Appium");
		ObjectMapper obj=new ObjectMapper();
		String actualJson=obj.writeValueAsString(specificProduct);
		Assertions.assertEquals(expectedJson,actualJson);
		
		
	}
	
	
	
}