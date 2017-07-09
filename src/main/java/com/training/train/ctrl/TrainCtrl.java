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
	
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE,
			reason="Service temporarily unavailable. Please, try again later.")
	@ExceptionHandler(RestClientException.class)
	public void noServiceAvailable() {}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public @ResponseBody TrainNotFoundMessage noTrainFound(HttpServletRequest req, Exception e) {
		log.error(e.toString());
		return new TrainNotFoundMessage(req.getRequestURL().toString(), e);
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
	
	@RequestMapping(path="/v1/trains/{trainNumber}", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<Train> getTrain(@PathVariable long trainNumber, RestTemplate restTemplate,
			UrlProvider urlProvider, HttpServletResponse response) throws Exception {
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		String numberStr = format.format(trainNumber);
		String url = MessageFormat.format(urlProvider.getUrl("trainByNumberUrl"), numberStr);
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
