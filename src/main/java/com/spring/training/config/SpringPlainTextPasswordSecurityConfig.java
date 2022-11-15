package com.spring.training.config;

import com.spring.training.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SpringPlainTextPasswordValidationCallbackHandler;

import java.util.List;

@Configuration
@Profile("springPlainTextPassword")
@AllArgsConstructor
public class SpringPlainTextPasswordSecurityConfig extends WsConfigurerAdapter {

    final UserAuthenticationService authenticationService;

    @Bean
    public XwsSecurityInterceptor securityInterceptor() {
        XwsSecurityInterceptor interceptor = new XwsSecurityInterceptor();
        interceptor.setCallbackHandler(callbackHandler());
        interceptor.setPolicyConfiguration(new ClassPathResource("passwordSecurityPolicy.xml"));
        return interceptor;
    }

    @Bean
    public SpringPlainTextPasswordValidationCallbackHandler callbackHandler() {
        SpringPlainTextPasswordValidationCallbackHandler handler = new SpringPlainTextPasswordValidationCallbackHandler();
        handler.setAuthenticationManager(authenticationManager());
        return handler;
    }

    @Bean
    public ProviderManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authenticationService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(provider);
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