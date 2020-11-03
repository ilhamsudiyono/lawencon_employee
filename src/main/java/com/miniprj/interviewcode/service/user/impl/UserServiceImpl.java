package com.miniprj.interviewcode.service.user.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miniprj.interviewcode.model.user.User;
import com.miniprj.interviewcode.repository.IRoleRepository;
import com.miniprj.interviewcode.repository.IUserRepository;
import com.miniprj.interviewcode.service.user.IUserService;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getListByIdUser(Long idUser) {
		// TODO Auto-generated method stub
		return userRepository.getListByIdUser(idUser);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public User findByNoTelp(String noTlp) {
		// TODO Auto-generated method stub
		return userRepository.findByNoTelp(noTlp).orElse(null);
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		user.setEmail(user.getEmail());
		user.setNoTelp(user.getNoTelp());
		user.setPassword(user.getPassword());
		user.setIsLogin(user.getIsLogin());
		return userRepository.save(user);
	}

	@Override
	public List<String> findUsers(List<Long> idUser) {
		// TODO Auto-generated method stub
		return userRepository.findEmailByIdList(idUser);
	}

	private boolean emailExists(final String email) {
		return userRepository.findByEmail(email) != null;
	}

	@Override
	public int updateUserLoginByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.updateUserLoginByEmail(email);
	}

	@Override
	public int updateUserLoginByNoTelp(String notelp) {
		// TODO Auto-generated method stub
		return userRepository.updateUserLoginByNoTelp(notelp);
	}

	@Override
	public List<User> getNoTelpOrEmail(String notelp, String eml) {
		// TODO Auto-generated method stub
		return userRepository.getNoTelpOrEmail(notelp, eml);
	}

	@Override
	public User getListByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.getListByEmail(email);
	}

	
	

}
