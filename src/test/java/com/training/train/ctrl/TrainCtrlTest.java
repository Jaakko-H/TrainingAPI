package com.training.train.ctrl;

import java.text.MessageFormat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
import com.training.train.model.Train;

@RunWith(SpringRunner.class)
@SpringBootTest
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

}
