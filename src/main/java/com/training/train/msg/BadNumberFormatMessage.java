package com.training.train.msg;

public class BadNumberFormatMessage {

	public final String url;
    public final String errorMessage = "Incorrect number format. Please enter a valid number.";

    public BadNumberFormatMessage(String url) {
        this.url = url;
    }
}
