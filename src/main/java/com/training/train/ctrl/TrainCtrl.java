package com.training.train.ctrl;

import java.text.MessageFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.training.train.model.Train;
import com.training.train.msg.BadNumberFormatMessage;
import com.training.train.msg.ServiceUnavailableMessage;
import com.training.train.msg.TrainNotFoundMessage;

@RestController
public class TrainCtrl {
	
	private static final Logger log = LoggerFactory.getLogger(TrainCtrl.class);
	
	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder();
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public @ResponseBody BadNumberFormatMessage badNumberFormat(HttpServletRequest req, Exception e) {
		log.error(e.toString());
		return new BadNumberFormatMessage(req.getRequestURL().toString());
	}
	
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler(RestClientException.class)
	public @ResponseBody ServiceUnavailableMessage serviceUnavailable(HttpServletRequest req, Exception e) {
		log.error(e.toString());
		return new ServiceUnavailableMessage(req.getRequestURL().toString());
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public @ResponseBody TrainNotFoundMessage noTrainFound(HttpServletRequest req, Exception e) {
		log.error(e.toString());
		return new TrainNotFoundMessage(req.getRequestURL().toString());
	}
	
	@RequestMapping(path="/v1/train-numbers", method=RequestMethod.GET)
	public long[] getTrainNumbersByStation(@RequestParam(required=true) String stationShortCode,
			RestTemplate restTemplate) {
		String url = MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, stationShortCode);
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
	
	@RequestMapping(path="/v1/trains/{trainNumber}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<Train> getTrain(@PathVariable long trainNumber, RestTemplate restTemplate,
			HttpServletResponse response) throws Exception {
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		String numberStr = format.format(trainNumber);
		String url = MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, numberStr);
		log.info("url=" + url);
		Train[] trains = new Train[0];
		trains = restTemplate.getForObject(url, Train[].class);
//		} catch(Exception e) {
//			log.error(e.toString());
////			return e.toString();
//			return new ResponseEntity<Train>(HttpStatus.SERVICE_UNAVAILABLE);
//		}
//		if (trains.length == 0) {
//			String result = "No train was found with the given id.";
//			log.info(result);
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
////			return response.toString();
//			return new ResponseEntity<Train>(HttpStatus.NOT_FOUND);
//		}
		Train train = trains[0];
		log.info(train.toString());
//		return train.toString();
		return new ResponseEntity<Train>(train, HttpStatus.OK);
	}
}
