package com.training.train.model;

import lombok.Getter;
import lombok.Setter;

public class Train {
	@Getter @Setter
	private int trainNumber;
	@Getter @Setter
	private String trainCategory;
	
	public Train() {}
	public Train(int trainNumber, String trainCategory) {
		this.trainNumber = trainNumber;
		this.trainCategory = trainCategory;
	}
}
