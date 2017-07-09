package com.training.train.msg;

public class TrainNotFoundMessage {

	public final String url;
    public final String errorMessage = "No train was found by the given train number.";

    public TrainNotFoundMessage(String url) {
        this.url = url;
    }
}
