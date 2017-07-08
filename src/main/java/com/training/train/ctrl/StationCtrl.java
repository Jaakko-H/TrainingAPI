package com.training.train.ctrl;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.train.model.Station;

@RestController
public class StationCtrl {
	@RequestMapping(path="/v1/stations/{stationShortCode}", method=RequestMethod.GET)
	public Station getStation(@PathVariable int stationShortCode) {
		return null;
	}
}
