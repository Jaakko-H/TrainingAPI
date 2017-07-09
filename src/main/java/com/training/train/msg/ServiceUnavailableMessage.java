package com.training.train.msg;

public class ServiceUnavailableMessage {
	
	public final String url;
    public final String errorMessage = "The service is temporarily unavailable. Please, try again later.";

    public ServiceUnavailableMessage(String url) {
        this.url = url;
    }
}
