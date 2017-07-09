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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.training.train.datasets.TrainDataCreator;
import com.training.train.datasets.TrainDataSet;
import com.training.train.model.Train;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class TrainCtrlTest {

	private MockMvc mockMvc;
	@InjectMocks
	private TrainCtrl ctrl;
	@Mock
	private RestTemplate mockTemplate;
	@Mock
	private RestTemplateBuilder builder;
	
	@Before
	public void Setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.ctrl).build();
//		TrainDataCreator.create(mockTemplate);
		for (Train t : TrainDataSet.TRAIN_DATA_SET) {
			TrainDataSet.trainDataMap.put(t.getTrainNumber(), new Train[] { t });
		}
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "1"),
				Train[].class)).thenReturn(TrainDataSet.trainDataMap.get(1));
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, "HKI"),
				int[].class)).thenReturn(TrainDataSet.TRAIN_NUMBERS_DATA_SET);
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_NUMBER_URL, "2"),
				Train[].class)).thenReturn(new Train[0]);
		Mockito.when(mockTemplate.getForObject(
				MessageFormat.format(UrlMap.TRAINS_BY_STATION_URL, "FOO"),
				int[].class)).thenReturn(new int[0]);
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

}
