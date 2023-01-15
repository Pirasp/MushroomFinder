package de.mushroomfinder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import de.mushroomfinder.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
    MyUserDetailService userDetailService;
		    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();
    	
    	http.authorizeRequests().antMatchers("/login").permitAll() //all users can access this page
   
        		.antMatchers("/admin/**", "/settings/**").hasAuthority("ADMIN") // only admins can access this page
        		//more permissions here....
                .anyRequest().authenticated()
                .and().formLogin()
          //      .loginPage("/login")
                .and()
                .logout()                                            
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                
                .permitAll();
    	
        return http.build();
    }
 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
    		AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
	public PasswordEncoder getPasswordEncoder() {
		//return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}


}
