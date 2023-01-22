package de.mushroomfinder.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import de.mushroomfinder.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	   @Autowired
	    private DataSource dataSource;
	     
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
	        http.authorizeRequests()
	            .antMatchers("/filter/").authenticated()
	            .antMatchers("/comments/**").authenticated()
	            .anyRequest().permitAll()
	            .and()
	            .formLogin()
	                .usernameParameter("email")
	                .defaultSuccessUrl("/setSession")
	                .permitAll()
	            .and()
	            .logout().logoutSuccessUrl("/").permitAll();
	        return http.build();
	    }

}
