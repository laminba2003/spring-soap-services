package com.spring.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;

import java.util.Map;

@Configuration
@Profile("password")
public class SimplePasswordSecurityConfig extends AbstractSecurityConfig {

    @Bean
    public XwsSecurityInterceptor securityInterceptor(ServerConfig serverConfig) {
        XwsSecurityInterceptor interceptor = new XwsSecurityInterceptor();
        Map<String, Object> securityConfig = (Map<String, Object>) serverConfig.getSecurity().get("password");
        DefaultResourceLoader loader = new DefaultResourceLoader();
        interceptor.setPolicyConfiguration(loader.getResource(securityConfig.get("policy").toString()));
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        Map<String, String> users = (Map<String, String>) securityConfig.get("users");
        handler.setUsersMap(users);
        interceptor.setCallbackHandler(handler);
        return interceptor;
    }

}
