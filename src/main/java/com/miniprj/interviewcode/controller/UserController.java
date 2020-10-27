package com.miniprj.interviewcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.miniprj.interviewcode.auth.sercurity.CurrentUser;
import com.miniprj.interviewcode.auth.sercurity.UserPrincipal;
import com.miniprj.interviewcode.model.UserSummary;
import com.miniprj.interviewcode.repository.IUserRepository;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private IUserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/users/me")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Get User by CurrentUser", notes = "Get User By Current User")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getEmail());
		return userSummary;
	}

}
