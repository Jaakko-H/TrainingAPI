package com.training.train.ctrl;

import java.text.MessageFormat;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(path="/v1/train-numbers", method=RequestMethod.GET)
	public long[] getTrainNumbersByStation(@RequestParam(required=true) String stationShortCode,
			RestTemplate restTemplate, UrlProvider urlProvider) {
		String url = MessageFormat.format(urlProvider.getUrl("trainsByStationUrl"), stationShortCode);
		log.info("url=" + url);
		try {
			Train[] trains = restTemplate.getForObject(url, Train[].class);
			long[] trainNumbers = new long[trains.length];
			for (int i = 0; i < trains.length; i++) {
				trainNumbers[i] = trains[i].getTrainNumber();
			}
			log.info(trainNumbers.toString());
			return trainNumbers;
		} catch (Exception e) {
			log.error(e.toString());
			return null;
		}
	}
	
	@RequestMapping(path="/v1/trains/{trainNumber}", method=RequestMethod.GET)
	public Train getTrain(@PathVariable long trainNumber,
			RestTemplate restTemplate, UrlProvider urlProvider) {
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		String numberStr = format.format(trainNumber);
		String url = MessageFormat.format(urlProvider.getUrl("trainByNumberUrl"), numberStr);
		log.info("url=" + url);
		try {
			Train[] trains = restTemplate.getForObject(url, Train[].class);
			Train train = trains[0];
			log.info(train.toString());
			return train;
		} catch(Exception e) {
			log.error(e.toString());
			return null;
		}
	}
}
