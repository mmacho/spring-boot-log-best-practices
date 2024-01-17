package configuration;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(request -> request.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
				.antMatchers("/websocket/**").permitAll().antMatchers("/ws/**").permitAll().antMatchers("/messages/**")
				.permitAll().antMatchers("/integration/**").permitAll().antMatchers("/sok/**").permitAll()
				// request.requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
				.anyRequest().authenticated()).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}

}
