package com.spring.training.config;

import com.spring.training.exception.SoapExceptionResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

@EnableWs
@Configuration
public class ApplicationConfig extends WsConfigurerAdapter {

    public static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver(){
        SoapFaultMappingExceptionResolver exceptionResolver = new SoapExceptionResolver();
        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        exceptionResolver.setDefaultFault(faultDefinition);
        Properties errorMappings = new Properties();
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        exceptionResolver.setExceptionMappings(errorMappings);
        exceptionResolver.setOrder(1);
        return exceptionResolver;
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "countries")
    public DefaultWsdl11Definition countriesWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CountriesPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(NAMESPACE_URI);
        definition.setSchema(countriesSchema);
        return definition;
    }

    @Bean(name = "persons")
    public DefaultWsdl11Definition personsWsdl11Definition(XsdSchema personsSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("PersonsPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(NAMESPACE_URI);
        definition.setSchema(personsSchema);
        return definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }

    @Bean
    public XsdSchema personsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("persons.xsd"));
    }

}