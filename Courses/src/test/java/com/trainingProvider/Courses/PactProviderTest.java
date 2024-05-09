package com.trainingProvider.Courses;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.trainingProvider.controller.AllCourseData;
import com.trainingProvider.repository.CoursesRepository;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.loader.PactUrl;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("CoursesCatalogue")
//@PactUrl(urls = { "file:src/main/java/pacts/BooksCatalogue-CoursesCatalogue.json" })
//@PactFolder("pacts")//url
@PactBroker(url="https://blutack.pactflow.io/",
authentication= @PactBrokerAuth(token="oVjEZij8nkC6L7y05yjppw"))


public class PactProviderTest {
	
	@LocalServerPort
	public int port;
	
	@Autowired
	  CoursesRepository repository;

	
	
	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	public void pactVerificationTest(PactVerificationContext context)
	{
		context.verifyInteraction();
		
	}
	
	@BeforeEach
	public void setup(PactVerificationContext context)
	{
		
		context.setTarget(new HttpTestTarget("localhost",port));
	}
	
	@State(value= "course exist",action= StateChangeAction.SETUP)
	public void coursesExist()
	
	{
		
	}
	
	@State(value= "course exist",action= StateChangeAction.TEARDOWN)
	public void coursesExistTearDown()
	
	{
		
	}
	
	
	@State(value= "Course Appium does not exist",action= StateChangeAction.SETUP)
	public void appiumCourseDoNotExist()
	
	{
		
	}
	
	@State(value= "Course Appium does not exist",action= StateChangeAction.TEARDOWN)
	public void appiumCourseDoNotExisttearDown()
	
	{
		
	}
	
	
}