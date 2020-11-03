package com.miniprj.interviewcode.service.user.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniprj.interviewcode.model.role.Role;
import com.miniprj.interviewcode.model.role.RoleName;
import com.miniprj.interviewcode.repository.IRoleRepository;
import com.miniprj.interviewcode.service.user.IRoleService;


@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleRepository roleRepository;
	
	

	@Override
	public List<Role> getListByIdRole(Long idRole) {
		// TODO Auto-generated method stub
		return roleRepository.getListByIdRole(idRole);
	}

	@Override
	public Role save(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		roleRepository.deleteById(id);
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}

	@Override
	public Role findByName(RoleName roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByName(roleName);
	}

	
	

	
	
	

}
