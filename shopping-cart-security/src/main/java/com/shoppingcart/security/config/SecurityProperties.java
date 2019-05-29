/**
 * 
 */
package com.shoppingcart.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author root
 *
 */
@ConfigurationProperties("security")
public class SecurityProperties {
	
	private JwtProperties jwt;

	/**
	 * Getter method for jwt
	 *
	 * @return the jwt
	 */
	public JwtProperties getJwt() {
		return jwt;
	}

	/**
	 * Setter method for jwt
	 *
	 * @param jwt 
	 *			the jwt to set
	 */
	public void setJwt(JwtProperties jwt) {
		this.jwt = jwt;
	}

	
}
