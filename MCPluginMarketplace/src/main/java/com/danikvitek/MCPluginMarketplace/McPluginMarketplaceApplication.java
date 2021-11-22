package com.danikvitek.MCPluginMarketplace;

import com.danikvitek.MCPluginMarketplace.configuration.JWTAuthorizationFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@SpringBootApplication
public class McPluginMarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(McPluginMarketplaceApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.anyRequest().authenticated();
		}
	}

}
