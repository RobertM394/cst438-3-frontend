package cst438assignment2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cst438assignment2.domain.City;
import cst438assignment2.domain.CityInfo;
import cst438assignment2.domain.Country;
import cst438assignment2.domain.TimeAndTemp;
import cst438assignment2.services.CityService;
import cst438assignment2.services.WeatherService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class CityServiceTest {
	
	//Use MockBean to stub out classes required by CityController
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;
	
	@MockBean
	private WeatherService weatherService;
	
	//this is the class being tested
	@Autowired
	private CityService cityService;
	
	@BeforeEach
	public void setUpEach() {
		MockitoAnnotations.initMocks(this);
	}
	
	//Test Methods
	@Test
	public void cityServiceShouldReturnValidCityInfoObject() {
		
		//create data to be returned by MockBeans
		Country country = new Country("TST", "Test Country");
		City city = new City(1, "TestCity", "TestDistrict", 1000, country);
		List<City> cities = new ArrayList<City>();
		cities.add(city);
		
		//create stub for database access via CityRepository
		given(cityRepository.findByName("TestCity")).willReturn(cities);
		
		//create stub for database access via CityRepository
		given(countryRepository.findByCode("TST")).willReturn(country);
		
		//create stub for WeatherService
		given(weatherService.getTimeAndTemp("TestCity")).willReturn(new TimeAndTemp(273.15, 1000, -1000));
		
		//Expected TimeAndTemp object
		TimeAndTemp timeAndTemp = new TimeAndTemp(273.15, 1000, -1000);
		CityInfo expectedResult = new CityInfo(city, country, timeAndTemp);
		
		//Result from test
		CityInfo cityInfo = cityService.getCityInfo("TestCity");
		
		//assert expected result = test result
		//MUST implement .equals() method for CityInfo class
		assertThat(expectedResult).isEqualTo(cityInfo);
	}
	
}
