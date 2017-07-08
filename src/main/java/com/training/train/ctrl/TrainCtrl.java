package com.training.train.ctrl;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.training.train.model.Train;

@RestController
public class TrainCtrl {
	
	private static final Logger log = LoggerFactory.getLogger(TrainCtrl.class);
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public UrlProvider urlProvider() {
		return new UrlProvider();
	}
	
	@RequestMapping(path="/v1/trains/{trainNumber}", method=RequestMethod.GET)
	public Train getTrain(@PathVariable int trainNumber,
			RestTemplate restTemplate, UrlProvider urlProvider) {
		String urlString = MessageFormat.format(urlProvider.getUrl("trainsUrl"), trainNumber);
		Train train = null;
		try {
			Train[] trains = restTemplate.getForObject(urlString, Train[].class);
			train = null;
			train = trains[0];
		} catch(Exception e) {
			return null;
		}
				
		log.info(train.toString());
		return train;
	}
}
