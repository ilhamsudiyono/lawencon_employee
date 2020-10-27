package com.miniprj.interviewcode.auth.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniprj.interviewcode.model.User;
import com.miniprj.interviewcode.repository.IUserRepository;
import com.miniprj.interviewcode.utils.ResourceNotFoundException;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String noTelpOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either no_telp or email
    		
    	System.out.println("noTelpOrEmail===>" + noTelpOrEmail);
        User user = userRepository.findByNoTelpOrEmail(noTelpOrEmail, noTelpOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + noTelpOrEmail)
        );

        
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}