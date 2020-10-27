/**
 * 
 */
/**
 * @author ilham.zannuary
 *
 */
package com.miniprj.interviewcode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.miniprj.interviewcode.auth.sercurity.CustomUserDetailsService;
import com.miniprj.interviewcode.auth.sercurity.JwtAuthenticationEntryPoint;
import com.miniprj.interviewcode.auth.sercurity.JwtAuthenticationFilter;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    private static final String[] AUTH_LIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  //Cross origin resource sharing
		 http
         .cors()
             .and()
         .csrf()
             .disable()
         .exceptionHandling()
             .authenticationEntryPoint(unauthorizedHandler)
             .and()
         .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
         .authorizeRequests()
         	.antMatchers(AUTH_LIST)
         	.permitAll()
             .antMatchers("/api/auth/**")
                 .permitAll()
             .antMatchers(HttpMethod.GET,"/api/users/**","/api/miniproject/**")
                 .permitAll()
               .antMatchers(HttpMethod.POST,"/api/users/**","/api/miniproject/**")
                 .authenticated().and().httpBasic();
 
		 	http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	

	@Bean
	public WebMvcConfigurer corsConfigure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");

			}
		};

	}

}