package com.training.train.ctrl;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.train.model.Train;

@RestController
public class TrainCtrl {
	@RequestMapping(path="/train/{number}", method=RequestMethod.GET)
	public Train getTrain(@PathVariable int number) {
		return null;
	}
}
