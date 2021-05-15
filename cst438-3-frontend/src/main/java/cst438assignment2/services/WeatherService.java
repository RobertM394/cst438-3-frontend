//reference: 
package cst438assignment2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import cst438assignment2.domain.TimeAndTemp;

@Service
public class WeatherService {

	private static final Logger log =
			LoggerFactory.getLogger(WeatherService.class);
	private RestTemplate restTemplate;
	private String weatherUrl;
	private String apiKey;
	
	public WeatherService( //1
			@Value("${weather.url}") final String weatherUrl,
			@Value("${weather.apikey}") final String apiKey ) {
		this.restTemplate = new RestTemplate();
		this.weatherUrl = weatherUrl;
		this.apiKey = apiKey;
	}
	
	public TimeAndTemp getTimeAndTemp(String cityName) {
		ResponseEntity<JsonNode> response =
				restTemplate.getForEntity(
						weatherUrl + "?q=" + cityName + "&appid=" + apiKey,
						JsonNode.class);
		JsonNode json = response.getBody(); //2
		log.info("Status code from weather server:" + response.getStatusCodeValue());
		/*if (response.getStatusCodeValue() != "200") 
			return new TimeAndTemp(0, 0, 0); */
		
		double temp = json.get("main").get("temp").asDouble();
		long time = json.get("dt").asLong();
		int timezone = json.get("timezone").asInt();
		return new TimeAndTemp(temp, time, timezone);
	}
}
