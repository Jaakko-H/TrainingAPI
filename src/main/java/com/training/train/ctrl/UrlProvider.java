package com.training.train.ctrl;

import java.util.HashMap;
import java.util.Map;

public class UrlProvider {
	private Map<String, String> urls;

	public UrlProvider() {
		urls = new HashMap<String, String>();
		urls.put("trainsUrl", "https://rata.digitraffic.fi/api/v1/live-trains/{0, number, integer}/");
	}
	
	public String getUrl(String key) {
		return urls.get(key);
	}
}
