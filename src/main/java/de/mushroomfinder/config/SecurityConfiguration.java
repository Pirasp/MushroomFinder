package de.mushroomfinder.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import de.mushroomfinder.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


	     
	    @Bean
	    public UserDetailsService userDetailsService() {
	        return new MyUserDetailService();
	    }
	     
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	     
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	 

	 
	    @Bean
	    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.authorizeRequests().antMatchers("/login", "/map", "/register/**", "/registrationConfirm/**", "/api/**", "/markers", "/spots", "/marker.png").permitAll()
	        	.antMatchers("/user/**").hasAuthority("ADMIN")
	        	.anyRequest().authenticated()
	            .and()
	            .formLogin()
	                .usernameParameter("email")
	                .defaultSuccessUrl("/setSession", true)
	                .permitAll()
	            .and()
	            .logout().logoutSuccessUrl("/").permitAll();
	        return http.build();
	    }

}
