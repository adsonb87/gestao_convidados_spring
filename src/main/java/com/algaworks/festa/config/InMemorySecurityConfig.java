package com.algaworks.festa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/","/convidados").permitAll()
		.anyRequest().authenticated().and().formLogin().permitAll()
		.and()	
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login").permitAll()
		.and()
		.httpBasic();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("adson").password(passwordEncoder()
			.encode("12345")).roles("ADMIN")
			.and()
			.withUser("jose").password(passwordEncoder()
			.encode("12345")).roles("USER");
	
	}
	
	
	
	
	
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder)throws Exception {
		builder
			.inMemoryAuthentication()
			.withUser("joao").password("123").roles("USER")
			.and()
			.withUser("alexandre").password("123").roles("USER")
			.and()
			.withUser("thiago").password("123").roles("USER");
	}
	*/
	
}
