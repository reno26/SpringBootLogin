package com.examen.user.rene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.examen.user.rene.service.impl.CustomUserDetailsService;

@Configuration
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		 http.authorizeRequests()
		 .antMatchers("/hello").authenticated()
		 .and()
		 .formLogin().loginPage("/login")
		 .usernameParameter("username").passwordParameter("password")
		 .and()
		 .logout().logoutSuccessUrl("/login?logout")
		 .and()
		 .csrf();
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordencoder() {
		
		return new BCryptPasswordEncoder();
	}
}