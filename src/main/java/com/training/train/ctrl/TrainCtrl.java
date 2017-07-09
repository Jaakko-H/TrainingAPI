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
	
	/* Handle exceptions with custom response messages. */
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
	
	/* This method returns an array of numbers, thus it shall be
	 * mapped respectively. */
	@RequestMapping(path="/v1/train-numbers", method=RequestMethod.GET)
	public long[] getTrainNumbersByStation(@RequestParam(required=true) String stationShortCode,
			RestTemplate restTemplate) {
		//Inject request parameter into the url to be used by RestTemplate
		String url = MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, stationShortCode);
		
		log.info("url=" + url);
		//Get json objects from external rest api and convert them into java-objects
		Train[] trains = restTemplate.getForObject(url, Train[].class);
		
		//Initialize response with new array of numbers
		long[] trainNumbers = new long[trains.length];
		
		//Assign the trains' train numbers into the response array
		for (int i = 0; i < trains.length; i++) {
			trainNumbers[i] = trains[i].getTrainNumber();
		}
		
		log.info(trainNumbers.toString());
		return trainNumbers;
	}
	
	/* This get-method for a single resource is mapped under the resource name.
	 * In there it is mapped by it's own identifier. */
	@RequestMapping(path="/v1/trains/{trainNumber}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<Train> getTrain(@PathVariable long trainNumber, RestTemplate restTemplate,
			HttpServletResponse response) throws Exception {
		//Get the current default number format
		NumberFormat format = NumberFormat.getIntegerInstance();
		
		//Set the grouping of the format to false, so that large numbers, such as 1000,
		//don't get formatted to 1,000 for example
		format.setGroupingUsed(false);
		
		//Process request parameter with the new format and make it a string
		String numberStr = format.format(trainNumber);
		
		//Inject request parameter into the url to be used by RestTemplate
		String url = MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, numberStr);
		
		log.info("url=" + url);
		//Get json objects from external rest api and convert them into java-objects
		Train[] trains = restTemplate.getForObject(url, Train[].class);
		
		//Get the first element of the json-array
				//The length of the array should never be more than 1 for singular resources
		Train train = trains[0];
		
		log.info(train.toString());
		return new ResponseEntity<Train>(train, HttpStatus.OK);
	}
}
