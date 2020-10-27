package com.miniprj.interviewcode.service.user;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.miniprj.interviewcode.model.User;

public interface IUserService {

	List<User> getListByIdUser(Long idUser);

	User findByEmail(String email);
	
	User findByNoTelp(String noTlp);

	List<User> findAllUsers();

	User saveUser(User user);

	List<String> findUsers(List<Long> idUser);
	
	int updateUserLoginByEmail(String email);
	
	int updateUserLoginByNoTelp(String notelp);
	
	List<User> getNoTelpOrEmail(String notelp,  String eml);
	
	User getListByEmail(String email);
	
	

}
