/**
 * 
 */
package com.shoppingcart.security.config;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author root
 *
 */

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private DataSource dataSource;

	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;

	private SecurityProperties securityProperties;

	private JwtAccessTokenConverter jwtAccessTokenConverter;

	private TokenStore tokenStore;

	public AuthorizationServerConfiguration(final DataSource dataSource, final PasswordEncoder passwordEncoder,
			final AuthenticationManager authenticationManager, final SecurityProperties securityProperties) {
		this.dataSource = dataSource;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.securityProperties = securityProperties;
	}

	@Bean
	public TokenStore tokenStore() {
		if (tokenStore == null) {
			tokenStore = new JwtTokenStore(jwtAccessTokenConverter);
		}
		return tokenStore;
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		if (jwtAccessTokenConverter == null) {
			JwtProperties jwtProperties = securityProperties.getJwt();
			KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));
			jwtAccessTokenConverter = new JwtAccessTokenConverter();
			jwtAccessTokenConverter.setKeyPair(keyPair);
		}
		return jwtAccessTokenConverter;
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(this.dataSource);
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(this.authenticationManager).accessTokenConverter(jwtAccessTokenConverter())
				.tokenStore(tokenStore());
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.passwordEncoder(this.passwordEncoder).tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}

	private KeyPair keyPair(JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
		return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(),
				jwtProperties.getKeyPairPassword().toCharArray());
	}

	private KeyStoreKeyFactory keyStoreKeyFactory(JwtProperties jwtProperties) {
		return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
	}
}
