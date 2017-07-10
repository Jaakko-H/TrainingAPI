package com.training.train.datasets;

import java.text.MessageFormat;

import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.training.train.ctrl.UrlMap;
import com.training.train.model.Train;

public class TrainDataCreator {
	public static void create(RestTemplate mockTemplate) {
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "1"),
				Train[].class)).thenReturn(TrainDataSet.train1);
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, "HKI"),
				Train[].class)).thenReturn(TrainDataSet.TRAIN_DATA_SET);
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "2"),
				Train[].class)).thenReturn(new Train[0]);
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, "FOO"),
				Train[].class)).thenReturn(new Train[0]);
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "503"),
				Train[].class)).thenThrow(new RestClientException(null));
	}
}
