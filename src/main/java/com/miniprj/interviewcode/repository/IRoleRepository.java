package com.miniprj.interviewcode.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniprj.interviewcode.model.role.Role;
import com.miniprj.interviewcode.model.role.RoleName;




@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
	
	@Query(value = "SELECT * FROM m_role WHERE id_role = :idRole", nativeQuery = true)
	public List<Role> getListByIdRole(@Param("idRole") Long roleId);

	public Role save(Role role);
	
	public Role findByName(RoleName roleName);
	
//	 Optional<Role> findBy(RoleName roleName);
//	public List<Role> findAll();
	
    
	
}
