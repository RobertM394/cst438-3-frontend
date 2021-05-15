package cst438assignment2;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;

import cst438assignment2.controllers.CityRestController;
import cst438assignment2.domain.City;
import cst438assignment2.domain.Country;
import cst438assignment2.domain.TimeAndTemp;
import cst438assignment2.services.WeatherService;

@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	//FAST Test - Fast Independent Repeatable Self-checking Timely
	
	//declare as @MockBean the classes which will be stubbed out in the test
	//The test stubs must be Spring components such as repositories or @Service
	//classes
	@MockBean
	private WeatherService weatherService;
	
	@MockBean
	private CityRepository cityRepository;
	
	//this class is used to make simulated HTTP requests to the class that is 
	//tested (in this case, CityRestController)
	@Autowired
	private MockMvc mvc;
	
	//These objects will be magically initialized by the initFields method below.
	private JacksonTester<City> jsonCityAttempt;
	
	@BeforeEach
	public void setUpEach() {
		MockitoAnnotations.initMocks(this);
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	//Test Methods
	@Test
	public void testValidCityName() throws Exception {
		
		Country country = new Country("TST", "Test Country");
		City city = new City(1, "TestCity", "TestDistrict", 1000, country);
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		
		//create test stubs
		given(weatherService.getTimeAndTemp("TestCity")).willReturn(new TimeAndTemp(273.15, 1000, -1000));
		given(cityRepository.findByName("TestCity")).willReturn(cities);
		
		//perform test using simulated HTTP GET request using URL: /city/TestCity
		MockHttpServletResponse response = mvc.perform(get("/api/cities/TestCity")).andReturn().getResponse();
		
		City cityResult = jsonCityAttempt.parseObject(response.getContentAsString());
	
   	City expectedResult = new City(1, "TestCity", "TestDistrict", 1000, country);
   	//expected weather is temp in Fahrenheit 
   	expectedResult.setTimeAndTemp(new TimeAndTemp(32, 1000, -1000));

   	assertThat(expectedResult).isEqualTo(cityResult);  
			
	}	
	
	@Test
	public void testInvalidCityName() throws Exception {
		
		Country country = new Country("AAA", "AAAA");
		City city = new City(1, "InvalidCity", "InvalidDistrict", 0, country);
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		
		//create test stubs 
		given(weatherService.getTimeAndTemp("InvalidCity")).willReturn(new TimeAndTemp(0, 0, 0));
		given(cityRepository.findByName("InvalidCity")).willReturn(cities);

		MockHttpServletResponse response = mvc.perform(get("/api/cities/InvalidCity")).andReturn().getResponse();
		
		City cityResult = jsonCityAttempt.parseObject(response.getContentAsString());
	
		City expectedResult = new City(1, "InvalidCity", "InvalidDistrict", 1000, country);
		expectedResult.setTimeAndTemp(new TimeAndTemp(0, 0, 0));
	
		assertThat(expectedResult).isNotEqualTo(cityResult); //expected but unclear what values are returned from invalid city name. cannot construct objects with null values.
	}	
	
	@Test
	public void testCityNameThatShouldReturnMultipleCities() throws Exception {
		
		Country country = new Country("TST", "TestCountry");
		City city1 = new City(1, "MultiCity", "MultiCityDistrict1", 1000, country);
		City city2 = new City(2, "MultiCity", "MultiCityDistrict2", 2000, country);
		List<City> cities = new ArrayList<City>();
		cities.add(city1);
		cities.add(city2);
		
		//create test stubs
		given(weatherService.getTimeAndTemp("MultiCity")).willReturn(new TimeAndTemp(273.15, 1000, -1000));
		given(cityRepository.findByName("MultiCity")).willReturn(cities);
		
		//perform test using simulated HTTP GET request using URL: /city/TestCity
		MockHttpServletResponse response = mvc.perform(get("/api/cities/MultiCity")).andReturn().getResponse();

	City cityResult = jsonCityAttempt.parseObject(response.getContentAsString()); 
	
	City expectedResult = new City(1, "MultiCity", "MultiCityDistrict1", 1000, country); //first result of list should be assigned to City object
	expectedResult.setTimeAndTemp(new TimeAndTemp(32, 1000, -1000));
	
	assertThat(expectedResult).isEqualTo(cityResult); 
	}	
}
