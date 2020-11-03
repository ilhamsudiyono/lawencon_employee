package com.miniprj.interviewcode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.assertj.core.util.Arrays;
import org.hamcrest.Matcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniprj.interviewcode.config.WebMvcConfig;
import com.miniprj.interviewcode.config.WebSecurityConfig;
import com.miniprj.interviewcode.controller.MinisterResource;
import com.miniprj.interviewcode.model.minister.Minister;
import com.miniprj.interviewcode.service.minister.MinisterClient;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static java.util.stream.Collectors.toList;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { WebMvcConfig.class, WebSecurityConfig.class })
class InterviewcodeApplicationTests {

	@Autowired
	private MinisterResource ministerResource;

	@MockBean
	private MinisterClient ministerClient;

	private List<Minister> listMinister;

	@Autowired
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void helloworld() throws InterruptedException, ExecutionException {
		CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello")
				.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

		assertEquals("Hello World", completableFuture.get());
	}

	@Test
	public void getMinisterList() throws Exception {
//		Minister minister = Minister.builder().dob("Minister of Education").identityAddress("Jakarta")
//				.lastEducation("S2").name("Nadiem Makarin").nik(555).occupation("WNI").build();
//
//		listMinister = new ArrayList<Minister>();
//		listMinister.add(minister);
//
//		MinisterClient ministerClientMock = mock(MinisterClient.class);
//		when(ministerClientMock.getList()).thenReturn(CompletableFuture.completedFuture(listMinister));
//		MinisterClient businessImpl = new MinisterClient();
//		CompletableFuture<List<Minister>> result = businessImpl.getList();
//
//		assertEquals(listMinister.get(0).getIdentityAddress(), result.get().get(0).getIdentityAddress());
		
		Minister mn = restTemplate.getForObject("http://localhost:9090/minister/list", Minister.class);
		assertNotNull(mn);
		
	}

	@Test
	public void testAddMinisterMissingHeader() throws URISyntaxException {

		final String baseUrl = "http://localhost:9090/minister/add";

		URI uri = new URI(baseUrl);

		Minister ministerObj = new Minister("Java", "Bandung", "S1", "Argana", 10, "WNI");

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<Minister> request = new HttpEntity<>(ministerObj, headers);

		System.out.println("=============" + ministerObj);
		try {
			restTemplate.postForEntity(uri, request, String.class);
			Assert.fail();
		} catch (HttpClientErrorException ex) {
			// Verify bad request and missing header
			Assert.assertEquals(400, ex.getRawStatusCode());
			Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
		}
	}

	@Test
	public void testEditMinisterMissingHeader() throws URISyntaxException {

		int nik = 5;
		final String baseUrl = "http://localhost:9090/minister/add" + nik;

		URI uri = new URI(baseUrl);

		Minister ministerObj = new Minister();
		ministerObj.setDob("Dodol garut");

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<Minister> request = new HttpEntity<>(ministerObj, headers);

		try {
			restTemplate.postForEntity(uri, request, String.class);
			Assert.fail();
		} catch (HttpClientErrorException ex) {
			// Verify bad request and missing header
			Assert.assertEquals(400, ex.getRawStatusCode());
			Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
		}
	}

	@Test
	public void testDeleteMinisterMissingHeader() throws URISyntaxException {

		int nik = 3;
		final String baseUrl = "http://localhost:9090/minister/delete?nik=" + nik;

		URI uri = new URI(baseUrl);

		Minister ministerObj = new Minister();
		ministerObj.setDob("Dodol garut");

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<Minister> request = new HttpEntity<>(ministerObj, headers);

		try {
			restTemplate.delete(uri);
			Assert.fail();
		} catch (HttpClientErrorException ex) {
			// Verify bad request and missing header
			Assert.assertEquals(400, ex.getRawStatusCode());
			Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
		}
	}

}
