package com.training.train.ctrl;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.training.train.model.Station;
import com.training.train.model.Train;

@RestController
public class StationCtrl {
	
	private static final Logger log = LoggerFactory.getLogger(TrainCtrl.class);
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public UrlMap urlProvider() {
		return new UrlMap();
	}
	
	@RequestMapping(path="/v1/stations/{stationShortCode}", method=RequestMethod.GET)
	public Station getStation(@PathVariable int stationShortCode,
			RestTemplate restTemplate, UrlMap urlProvider) {
		return null;
	}
}
