package com.miniprj.interviewcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.miniprj.interviewcode.auth.sercurity.JwtTokenProvider;
import com.miniprj.interviewcode.auth.sercurity.UserPrincipal;
import com.miniprj.interviewcode.model.auth.LoginRequest;
import com.miniprj.interviewcode.model.auth.SignUpRequest;
import com.miniprj.interviewcode.model.payload.ApiResponse;
import com.miniprj.interviewcode.model.payload.JwtAuthenticationResponse;
import com.miniprj.interviewcode.model.role.Role;
import com.miniprj.interviewcode.model.role.RoleName;
import com.miniprj.interviewcode.model.user.User;
import com.miniprj.interviewcode.repository.IRoleRepository;
import com.miniprj.interviewcode.repository.IUserRepository;
import com.miniprj.interviewcode.service.user.impl.UserServiceImpl;
import com.miniprj.interviewcode.utils.AppException;

import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    private MessageSource messages;
   
    @Autowired
    ApplicationEventPublisher eventPublisher;
    
    @PostMapping("/signin")
    @ApiOperation(value = "Get generate token", notes = "${AuthController.authenticateUser.notes}")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getNoTelpOrEmail(),
                        loginRequest.getPassword()
                )
        );
//      System.out.println("authentication>>>>>>>>>>>>>>>>>>" + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String noTelpOrEmail = loginRequest.getNoTelpOrEmail();
        
        if (isValid(noTelpOrEmail)) {
//        	System.out.println("EMAIL>>>>>>" + noTelpOrEmail);
//        	System.out.println("PASS>>>>>>" + loginRequest.getPassword());
        	userServiceImpl.updateUserLoginByEmail(noTelpOrEmail);
		}else {
			userServiceImpl.updateUserLoginByNoTelp(noTelpOrEmail);
		}
        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
        return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getId(),userDetails.getUsername(), userDetails.getEmail(),roles, jwt));
    }
    

    @PostMapping("/signup-email")
    public ResponseEntity<?> registerUserEmail(@Valid @RequestBody SignUpRequest signUpRequest) {


        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        Instant instant = Instant.now();
       
        // Creating user's account
        User user = new User(signUpRequest.getNoTelp(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), true,  instant);
        
        Role role = new Role(RoleName.ROLE_USER);
        
        roleRepository.save(role);
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
        System.out.println("ROLES = " + userRole);
        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);
 
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    
   
    
    
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
     }
    
    

    
}
