package com.training.train.ctrl;

import java.text.MessageFormat;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.training.train.datasets.TrainDataCreator;
import com.training.train.model.Train;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class TrainCtrlTest {

	@Inject
	private MockMvc mockMvc;
	@InjectMocks
	private TrainCtrl ctrl;
	@Mock
	private RestTemplate mockTemplate;
	
	@Before
	public void Setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.ctrl).build();
		System.out.println("foo");
		TrainDataCreator.create(mockTemplate);
	}
	
	@Test
	public void testGetTrain() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trains/1");
		mockMvc.perform(requestBuilder)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$.trainNumber", Matchers.is(1)))
						.andExpect(MockMvcResultMatchers.jsonPath("$.trainCategory", Matchers.is("foo")));
		Mockito.verify(mockTemplate, Mockito.times(1)).getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "1"), Train[].class);
	}
	
	@Test
	public void testDontGetInexistingTrain() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trains/2");
		mockMvc.perform(requestBuilder)
						.andExpect(MockMvcResultMatchers.status().isNotFound())
						.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is(
								"No train was found by the given train number.")));
		Mockito.verify(mockTemplate, Mockito.times(1)).getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "2"), Train[].class);
	}
	
	@Test
	public void testDontGetTrainWithInvalidNumberFormat() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trains/foobar");
		mockMvc.perform(requestBuilder)
						.andExpect(MockMvcResultMatchers.status().isBadRequest());
		Mockito.verify(mockTemplate, Mockito.times(0)).getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "foobar"), Train[].class);
	}
	
	@Test
	public void testDontGetTrainFromOfflineService() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trains/503");
		mockMvc.perform(requestBuilder)
						.andExpect(MockMvcResultMatchers.status().isServiceUnavailable());
		Mockito.verify(mockTemplate, Mockito.times(1)).getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "503"), Train[].class);
	}

	@Test
	public void testGetTrainNumbersByStationShortCode() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/train-numbers/?stationShortCode=HKI");
		mockMvc.perform(requestBuilder)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.contains(1, 1337)));;
		Mockito.verify(mockTemplate, Mockito.times(1)).getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, "HKI"), Train[].class);
	}
	
	@Test
	public void testGetTrainNumbersByInexistingStation() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/train-numbers/?stationShortCode=FOO");
		mockMvc.perform(requestBuilder)
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.empty()));;
		Mockito.verify(mockTemplate, Mockito.times(1)).getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, "FOO"), Train[].class);
	}
}
