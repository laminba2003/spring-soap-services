package com.spring.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.support.KeyStoreFactoryBean;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.KeyStoreCallbackHandler;

import java.util.List;

@Configuration
@Profile("certificate")
public class KeyStoreSecurityConfig extends WsConfigurerAdapter {

    @Bean
    public XwsSecurityInterceptor securityInterceptor() {
        XwsSecurityInterceptor interceptor = new XwsSecurityInterceptor();
        interceptor.setCallbackHandler(callbackHandler());
        interceptor.setPolicyConfiguration(new ClassPathResource("certificateSecurityPolicy.xml"));
        return interceptor;
    }

    @Bean
    public KeyStoreCallbackHandler callbackHandler() {
        KeyStoreCallbackHandler handler = new KeyStoreCallbackHandler();
        handler.setTrustStore(keyStoreFactoryBean().getObject());
        return handler;
    }

    @Bean
    public KeyStoreFactoryBean keyStoreFactoryBean() {
        KeyStoreFactoryBean keyStoreFactoryBean = new KeyStoreFactoryBean();
        keyStoreFactoryBean.setLocation(new ClassPathResource("trustStore.jks"));
        keyStoreFactoryBean.setPassword("changeit");
        return keyStoreFactoryBean;
    }

    @Bean
    public PayloadLoggingInterceptor payloadLoggingInterceptor() {
        return new PayloadLoggingInterceptor();
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(payloadLoggingInterceptor());
        interceptors.add(securityInterceptor());
    }

}
