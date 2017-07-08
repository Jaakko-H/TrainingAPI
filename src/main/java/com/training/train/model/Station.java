package com.training.train.model;

import lombok.Getter;
import lombok.Setter;

public class Station {
	@Getter @Setter
	private int stationShortCode;
	@Getter @Setter
	private int[] trainNumbers;
	
	public Station() {}
	public Station(int stationShortCode, int[] trainNumbers) {
		this.stationShortCode = stationShortCode;
		this.trainNumbers = trainNumbers;
	}
}
