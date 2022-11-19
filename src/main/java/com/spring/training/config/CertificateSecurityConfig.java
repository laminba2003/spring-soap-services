package com.spring.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.support.KeyStoreFactoryBean;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.KeyStoreCallbackHandler;

import java.util.Map;

@Configuration
@Profile("certificate")
public class CertificateSecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Override
    public EndpointInterceptor securityInterceptor(ServerConfig serverConfig) {
        XwsSecurityInterceptor interceptor = new XwsSecurityInterceptor();
        Map<String, String> securityConfig = (Map<String, String>) serverConfig.getSecurity().get("certificate");
        DefaultResourceLoader loader = new DefaultResourceLoader();
        interceptor.setPolicyConfiguration(loader.getResource(securityConfig.get("policy")));
        KeyStoreCallbackHandler handler = new KeyStoreCallbackHandler();
        handler.setDefaultAlias(securityConfig.get("alias"));
        KeyStoreFactoryBean keyStoreFactoryBean = keyStoreFactoryBean(serverConfig);
        handler.setTrustStore(keyStoreFactoryBean.getObject());
        handler.setKeyStore(keyStoreFactoryBean.getObject());
        handler.setPrivateKeyPassword(securityConfig.get("password"));
        interceptor.setCallbackHandler(handler);
        return interceptor;
    }

    @Bean
    public KeyStoreFactoryBean keyStoreFactoryBean(ServerConfig serverConfig) {
        Map<String, String> securityConfig = (Map<String, String>) serverConfig.getSecurity().get("certificate");
        DefaultResourceLoader loader = new DefaultResourceLoader();
        KeyStoreFactoryBean keyStoreFactoryBean = new KeyStoreFactoryBean();
        keyStoreFactoryBean.setType("JKS");
        keyStoreFactoryBean.setLocation(loader.getResource(securityConfig.get("keyStore")));
        keyStoreFactoryBean.setPassword(securityConfig.get("password"));
        return keyStoreFactoryBean;
    }

}
