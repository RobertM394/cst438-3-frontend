package cst438assignment2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cst438assignment2.CityRepository;
import cst438assignment2.domain.City;
import cst438assignment2.services.WeatherService;
import cst438assignment2.domain.TimeAndTemp;


@RestController
public class CityRestController {
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired 
	CityRepository cityRepository;
	
	@GetMapping("/api/cities/{name}")
	public ResponseEntity<City> cityInfo(@PathVariable("name") String name) {
		
		//look up city info from the database. Multiple cities may have same name.
		List<City> cities = cityRepository.findByName(name);
		if ( cities.size() == 0) {
			
			//return 404 not found HTTP status code
			return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
		} else {
		//get first city in result array
		City city = cities.get(0);
		
	//Call service WeatherService to retrieve time and temp data for city name
	   TimeAndTemp timeAndTemp = weatherService.getTimeAndTemp(name); 
	   
		double tempFahrenheit = Math.round((timeAndTemp.temp - 273.15) * 9.0/5.0 + 32.0);
		timeAndTemp.temp = tempFahrenheit;
		
		//add TimeAndTemp object to City object
		city.timeAndTemp = timeAndTemp;
		
		//return status 200(OK) code and city information in JSON format
		return new ResponseEntity<City>(city, HttpStatus.OK);
	}
}

}
