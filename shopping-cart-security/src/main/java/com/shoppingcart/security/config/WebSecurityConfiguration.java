/**
 * 
 */
package com.shoppingcart.security.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class contains all configuration for security.
 * @author Chetan
 *
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * dataSource declaration.
	 */
	private DataSource dataSource;
	
	/**
	 * passwordEncoder declaration.
	 */
	private PasswordEncoder passwordEncoder;
	
	/**
	 * userDetailService declaration.
	 */
	private UserDetailsService userDetailService;
	
	/**
	 * Parameterized constructor with data source.
	 * 
	 * @param dataSource
	 *            data source for database.
	 */
	public WebSecurityConfiguration(final DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Overriden configure method for AuthenticationManagerBuilder.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(getUserDetailsService()).passwordEncoder(getPasswordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		if (passwordEncoder == null) {
			passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}
		return passwordEncoder;
	}
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		if (userDetailService == null) {
			userDetailService = new JdbcDaoImpl();
			((JdbcDaoImpl)userDetailService).setDataSource(dataSource);
		}
		return userDetailService;
	}
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/h2-console/**", "/oauth/", "/oauth/token/**", "/oauth/**").permitAll()
                .and().authorizeRequests().antMatchers("/oauth/**").permitAll();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
}
