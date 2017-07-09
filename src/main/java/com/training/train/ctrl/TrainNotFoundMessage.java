package com.training.train.ctrl;

public class TrainNotFoundMessage {

	public final String url;
    public final String ex;

    public TrainNotFoundMessage(String url, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
    }
}
