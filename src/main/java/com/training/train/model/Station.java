package com.training.train.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {
//	@Getter @Setter
	private int stationShortCode;
//	@Getter @Setter
	private int[] trainNumbers;
	
	public Station() {}
	public Station(int stationShortCode, int[] trainNumbers) {
		this.stationShortCode = stationShortCode;
		this.trainNumbers = trainNumbers;
	}
	public int getStationShortCode() {
		return stationShortCode;
	}
	public void setStationShortCode(int stationShortCode) {
		this.stationShortCode = stationShortCode;
	}
	public int[] getTrainNumbers() {
		return trainNumbers;
	}
	public void setTrainNumbers(int[] trainNumbers) {
		this.trainNumbers = trainNumbers;
	}
	@Override
	public String toString() {
		return "Station [stationShortCode=" + stationShortCode + ", trainNumbers=" + Arrays.toString(trainNumbers)
				+ "]";
	}
}
