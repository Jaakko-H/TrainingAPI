package com.training.train.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Train {
	private long trainNumber;
	private String trainCategory;
	
	public Train() {}
	public Train(int trainNumber, String trainCategory) {
		this.trainNumber = trainNumber;
		this.trainCategory = trainCategory;
	}
	
	public long getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(int trainNumber) {
		this.trainNumber = trainNumber;
	}
	public String getTrainCategory() {
		return trainCategory;
	}
	public void setTrainCategory(String trainCategory) {
		this.trainCategory = trainCategory;
	}
	@Override
	public String toString() {
		return "Train [trainNumber=" + trainNumber + ", trainCategory=" + trainCategory + "]";
	}
}
