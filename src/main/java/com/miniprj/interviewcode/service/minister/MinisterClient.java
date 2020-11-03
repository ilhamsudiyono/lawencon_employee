package com.miniprj.interviewcode.service.minister;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniprj.interviewcode.model.Minister;
import com.miniprj.interviewcode.model.RequestMinisterDTO;
import com.miniprj.interviewcode.model.RequestMinisterUpdDTO;

@Service
public class MinisterClient {

	private static final Logger logger = LoggerFactory.getLogger(MinisterClient.class);

	RestTemplate restTemplate = new RestTemplate();
	
	@Async("asyncExecutor")
	public CompletableFuture<List<Minister>> getList() throws InterruptedException {
		String url = "http://localhost:9090/minister/list";
		Minister[] response = restTemplate.getForObject(url, Minister[].class);

		return CompletableFuture.completedFuture(Arrays.asList(response));
	}
	
	@Async("asyncExecutor")
	public CompletableFuture<List<Minister>> getListTest() throws InterruptedException {
		Minister result = new Minister("Minister of Education","Jakarta","S2","Nadiem Makarim",555,"WNI");

		return CompletableFuture.completedFuture(Arrays.asList(result));
	}
	
	@Async("asyncExecutor")
	public CompletableFuture<Minister[]> getAdd(RequestMinisterDTO req) throws InterruptedException {
		
		String url = "http://localhost:9090/minister/add";
		Minister minister = new Minister();
		minister.setDob(req.getDob());
		minister.setIdentityAddress(req.getIdentityAddress());
		minister.setLastEducation(req.getLastEducation());
		minister.setName(req.getName());
		minister.setNik(req.getNik());
		minister.setOccupation(req.getOccupation());
		Minister[] response = restTemplate.postForObject(url, minister, Minister[].class);

		return CompletableFuture.completedFuture(response);
	}
	
	@Async("asyncExecutor")
	public void getEdit(Long nik, RequestMinisterUpdDTO req) throws InterruptedException {
		
		String url = "http://localhost:9090/minister/edit/" + nik;
		Minister minister = new Minister();
		minister.setDob(req.getDob());
		minister.setIdentityAddress(req.getIdentityAddress());
		minister.setLastEducation(req.getLastEducation());
		minister.setName(req.getName());
		minister.setOccupation(req.getOccupation());
		restTemplate.put(url, minister);
		
	}
	
	
	@Async("asyncExecutor")
	public CompletableFuture<Void> getDelete(Long nik) throws InterruptedException {
		
		String url = "http://localhost:9090/minister/delete?nik=" + nik;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("nik", nik.toString());
		restTemplate.delete(url, params);
		
		return new CompletableFuture<Void>();
		
	}
	
	
}
