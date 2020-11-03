package com.miniprj.interviewcode.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.xml.ws.RequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.miniprj.interviewcode.model.Minister;
import com.miniprj.interviewcode.model.RequestMinisterDTO;
import com.miniprj.interviewcode.model.RequestMinisterUpdDTO;
import com.miniprj.interviewcode.service.minister.MinisterClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@Api(value = "MinisterResource")
@RestController
@RequestMapping("/api")
public class MinisterResource {

	private static final Logger logger = LoggerFactory.getLogger(MinisterClient.class);

	private static MinisterClient ministerClient;

	public MinisterResource(MinisterClient ministerClient) {
		this.ministerClient = ministerClient;
	}

	@ApiOperation(httpMethod = "GET", value = "Get list Minister", response = String.class, responseContainer = "List")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@GetMapping("/list")
	@PreAuthorize("hasRole('USER')")
	public CompletableFuture<List<Minister>> getListMinister() throws InterruptedException {


		return ministerClient.getList();
	}

	
	
	@ApiOperation(httpMethod = "POST", value = "Post Minister", response = String.class, responseContainer = "List")
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "minister", value = "List of strings", paramType = "body", dataType = "Minister") })
	@ResponseBody
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Object> postListMinister(@RequestBody final RequestMinisterDTO req)
			throws InterruptedException {
		ministerClient.getAdd(req);

		return ResponseEntity.status(HttpStatus.CREATED).body(req);
	}

	
	
	@ApiOperation(httpMethod = "PUT", value = "Edit Minister", response = String.class, responseContainer = "List")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "nik", value = "Nik value", required = true, dataType = "Long", paramType = "path"),
  		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })

	@RequestMapping(value = "/edit/{nik}", method = RequestMethod.PUT)
	@ResponseBody
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Object> editListMinister(@PathVariable Long nik, @RequestBody RequestMinisterUpdDTO req)
			throws InterruptedException {

		ministerClient.getEdit(nik, req);

		return ResponseEntity.status(HttpStatus.OK).body(req);
	}
	

	
	@ApiOperation(httpMethod = "DELETE", value = "Delete Minister", response = String.class, responseContainer = "List")
	@ApiImplicitParams({
			
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "nik", value = "Nik value", required = true, dataType = "Long", paramType = "path")})
	@RequestMapping(value = "/delete/{nik}", method = RequestMethod.DELETE)
	@ResponseBody
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Object>  deleteListMinister(@PathVariable Long nik)
			throws InterruptedException {

		ministerClient.getDelete(nik);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
