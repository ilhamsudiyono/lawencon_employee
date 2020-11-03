package com.miniprj.interviewcode.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniprj.interviewcode.model.user.User;



@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
	@Query(value = "SELECT * FROM m_user WHERE id = :idUser", nativeQuery = true)
	public List<User> getListByIdUser(@Param("idUser") Long userId);
	
	@Query(value = "SELECT * FROM m_user WHERE email = :email_", nativeQuery = true)
	public User getListByEmail(@Param("email_") String email);
	
	 
	Optional<User> findByEmail(String email);
	
	Optional<User> findByNoTelp(String noTlp);
	
	Optional<User> findByNoTelpOrEmail(String noTlp, String email);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByNoTelp(String noTelp);
	
	@Query(value = "select u.email from m_user u where u.id ?1", nativeQuery = true)
	List<String> findEmailByIdList(@Param("id") List<Long> idUser);

	@Query(value = "SELECT * FROM m_user WHERE u.id ?1", nativeQuery = true)
	public Optional<User> findById(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE m_user SET is_login = true WHERE email = :emaill", nativeQuery = true)
	public int updateUserLoginByEmail(@Param("emaill") String email);
	    
	Optional<User> findByNoTelpOrEmailAndPassword(String noTlp, String email, String pass);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE m_user SET is_login = true WHERE no_telp = :noTelp", nativeQuery = true)
	public int updateUserLoginByNoTelp(@Param("noTelp") String no_telp);
	
	
	@Query(value="select id, no_telp, email, password, is_active, is_login, version from m_user where no_telp = :notelp or email = :eml", nativeQuery = true)
	public List<User> getNoTelpOrEmail(String notelp,  String eml);
	
}
