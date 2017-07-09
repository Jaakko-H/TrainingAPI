package com.training.train.ctrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.training.train.model.Train;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainCtrl.class)
public class TrainCtrlTest {

	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private TrainCtrl ctrl;
	@Mock
	private RestTemplate mockTemplate;
	
	@Before
	public void Setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.ctrl).build();
		
//		Mockito.when(mockTemplate.getForObject(urlProvider.getUrl("trainsUrl"), Train.class))
//					.thenReturn()
	}
	
	@Test
	public void testGetTrain() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trains/6");
//		mockMvc.perform(requestBuilder)
//						.andExpect(MockMvcResultMatchers.jsonPath(expression, args))
	}

}
