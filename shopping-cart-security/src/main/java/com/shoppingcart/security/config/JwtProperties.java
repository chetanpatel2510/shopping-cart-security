/**
 * 
 */
package com.shoppingcart.security.config;

import org.springframework.core.io.Resource;

/**
 * @author root
 *
 */
public class JwtProperties {

	private Resource keyStore;
	
	private String keyStorePassword;
	
	private String keyPairAlias;
	
	private String keyPairPassword;

	/**
	 * Getter method for keyStore
	 *
	 * @return the keyStore
	 */
	public Resource getKeyStore() {
		return keyStore;
	}

	/**
	 * Setter method for keyStore
	 *
	 * @param keyStore 
	 *			the keyStore to set
	 */
	public void setKeyStore(Resource keyStore) {
		this.keyStore = keyStore;
	}

	/**
	 * Getter method for keyStorePassword
	 *
	 * @return the keyStorePassword
	 */
	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	/**
	 * Setter method for keyStorePassword
	 *
	 * @param keyStorePassword 
	 *			the keyStorePassword to set
	 */
	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	/**
	 * Getter method for keyPairAlias
	 *
	 * @return the keyPairAlias
	 */
	public String getKeyPairAlias() {
		return keyPairAlias;
	}

	/**
	 * Setter method for keyPairAlias
	 *
	 * @param keyPairAlias 
	 *			the keyPairAlias to set
	 */
	public void setKeyPairAlias(String keyPairAlias) {
		this.keyPairAlias = keyPairAlias;
	}

	/**
	 * Getter method for keyPairPassword
	 *
	 * @return the keyPairPassword
	 */
	public String getKeyPairPassword() {
		return keyPairPassword;
	}

	/**
	 * Setter method for keyPairPassword
	 *
	 * @param keyPairPassword 
	 *			the keyPairPassword to set
	 */
	public void setKeyPairPassword(String keyPairPassword) {
		this.keyPairPassword = keyPairPassword;
	}
	
}
