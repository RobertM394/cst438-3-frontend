package cst438assignment2.services;

import java.util.List;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438assignment2.CityRepository;
import cst438assignment2.CountryRepository;
import cst438assignment2.domain.City;
import cst438assignment2.domain.CityInfo;
import cst438assignment2.domain.Country;
import cst438assignment2.domain.TimeAndTemp;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private FanoutExchange fanout;
	
	//retrieve a CityInfo object
	public CityInfo getCityInfo(String cityName) {

		//build CityInfo object
		List<City> cities = cityRepository.findByName(cityName);
		City city = cities.get(0);

		Country country = countryRepository.findByCode(city.getCountry().getCode()); 
		TimeAndTemp timeAndTemp = weatherService.getTimeAndTemp(cityName);

		CityInfo cityInfo = new CityInfo(city, country, timeAndTemp);
		
		return cityInfo;
	}
	
	//enqueue a message to RabbitMQ exchange
	public void requestReservation(
			String cityName,
			String vacationLevel,
			String email) {
		
		String msg = "{\"cityName\": \"" + cityName + 
				"\" \"vacationLevel\": \"" + vacationLevel +
				"\" \"email\": \"" + email + "\"}";
		System.out.println("Sending message:" + msg);
		rabbitTemplate.convertSendAndReceive(
				fanout.getName(),
				"", //routing key = none
				msg);
	}
	
}
