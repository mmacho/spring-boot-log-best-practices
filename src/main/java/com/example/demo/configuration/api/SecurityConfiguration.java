package com.example.demo.configuration.api;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
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

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration cc = new CorsConfiguration();
		cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type",
				"Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
		cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		cc.setAllowedOrigins(Arrays.asList("/*"));
		cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH"));
		cc.addAllowedOrigin("*");
		cc.setMaxAge(Duration.ZERO);
		cc.setAllowCredentials(Boolean.TRUE);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cc);
		return source;
	}

}
