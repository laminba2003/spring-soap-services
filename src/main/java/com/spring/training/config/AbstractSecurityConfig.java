package com.spring.training.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;

import java.util.List;

public abstract class AbstractSecurityConfig extends WsConfigurerAdapter {

    public abstract EndpointInterceptor securityInterceptor(ServerConfig serverConfig) ;

    @Bean
    public PayloadLoggingInterceptor payloadLoggingInterceptor() {
        return new PayloadLoggingInterceptor();
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(payloadLoggingInterceptor());
        interceptors.add(securityInterceptor(serverConfig()));
    }

    @Bean
    @ConfigurationProperties(prefix = "ws")
    public ServerConfig serverConfig() {
        return new ServerConfig();
    }

}
