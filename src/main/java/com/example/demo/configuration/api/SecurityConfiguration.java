package com.example.demo.configuration.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// disable
		// http.authorizeRequests(requests ->
		// requests.antMatchers("/**").permitAll().anyRequest().authenticated()).csrf(csrf
		// -> csrf.disable());

//		http.authorizeRequests(request -> request.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
//				.antMatchers("/ws/**").permitAll().antMatchers("/soap/**").permitAll().antMatchers("/messages/**")
//				.permitAll().antMatchers("/h2-console/**").permitAll().antMatchers("/api/**").permitAll().anyRequest()
//				.authenticated()).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

		http.csrf(csrf -> csrf.disable());
		http.headers(headers -> headers.frameOptions().disable());
		http.authorizeRequests(
				requests -> requests.antMatchers("/a/**", "/b/**").authenticated().antMatchers("/").permitAll())
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// For SOAP
		web.ignoring().antMatchers("/ws/**").antMatchers("/soap/**");
	}

}
