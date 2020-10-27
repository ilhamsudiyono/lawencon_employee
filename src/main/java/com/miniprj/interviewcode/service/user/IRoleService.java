package com.miniprj.interviewcode.service.user;

import java.util.List;
import java.util.Optional;

import com.miniprj.interviewcode.model.Role;
import com.miniprj.interviewcode.model.RoleName;


public interface IRoleService  {
	
	List<Role> getListByIdRole(Long idRole);
	
	List<Role> findAll();
	
	Role save(Role role);

    void deleteById(Long id);
    
    Role findByName(RoleName roleName);

}
