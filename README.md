# Spring SOAP Web Services

SOAP is a messaging protocol. Messages (requests and responses) are XML documents over HTTP. The XML contract is defined by the WSDL (Web Services Description Language). It provides a set of rules to define the messages, bindings, operations, and location of the service.
There are two possible approaches when creating a web service: Contract-Last and Contract-First. When we use a contract-last approach, we start with the Java code, and generate the web service contract (WSDL) from the classes. When using contract-first, we start with the WSDL contract, from which we generate the Java classes.
Spring-WS only supports the contract-first development style.

## Setup

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web-services</artifactId>
    </dependency>
    <dependency>
        <groupId>wsdl4j</groupId>
        <artifactId>wsdl4j</artifactId>
    </dependency>
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>jaxb2-maven-plugin</artifactId>
            <version>2.5.0</version>
            <executions>
                <execution>
                    <id>xjc</id>
                    <goals>
                        <goal>xjc</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <sources>
                    <source>${project.basedir}/src/main/resources/countries.xsd</source>
                    <source>${project.basedir}/src/main/resources/persons.xsd</source>
                </sources>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Security

Spring WS-Security allows you to sign SOAP messages, encrypt and decrypt them, or authenticate against them. The WS-Security implementation of Spring Web Services provides integration with Spring Security.

```xml
<dependency>
    <groupId>org.springframework.ws</groupId>
    <artifactId>spring-ws-security</artifactId>
</dependency>
<dependency>
    <groupId>com.sun.xml.wss</groupId>
    <artifactId>xws-security</artifactId>
    <version>3.0</version>
    <exclusions>
        <exclusion>
            <groupId>javax.activation</groupId>
            <artifactId>activation</artifactId>
        </exclusion>
        <exclusion>
            <groupId>javax.xml.crypto</groupId>
            <artifactId>xmldsig</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
**Authentication**: 

This is the process of determining whether a principal is who they claim to be. In this context, a "principal" generally means a user, device or some other system which can perform an action in your application.

**Digital signatures**:  

The digital signature of a message is a piece of information based on both the document and the signer's private key. It is created through the use of a hash function and a private signing function (encrypting with the signer's private key).

**Encryption and Decryption**:  

Encryption is the process of transforming data into a form that is impossible to read without the appropriate key. It is mainly used to keep information hidden from anyone for 

Two implementations of WS-Security, WSS4J and XWSS, are supported. 

- Plain password
- Signature
- Encryption

## URLs for the web services

By default, the web services will be deployed to the following URL:

```
http://localhost:8080/ws
```

With this, by running the project the following endpoints are mapped each to their own URL:

|URL|
| ----------- |
|[http://localhost:8080/ws/persons.wsdl](http://localhost:8080/ws/persons.wsdl)|
|[http://localhost:8080/ws/countries.wsdl](http://localhost:8080/ws/countries.wsdl)|

## Security activation

You need to run the project with the appropriate profile and you can read below the security configuration declared in the *application.yml* file.

```
ws:
  security:
    password:
      policy: classpath:passwordSecurityPolicy.xml
      users:
        admin: pwd123
    certificate:
      policy: classpath:certificateSecurityPolicy.xml
      keyStore: classpath:keyStore.jks
      password: changeit
      alias: thinktech
```
 
- Plain password

```
mvn spring-boot:run -Dspring-boot.run.profiles=password
```

- Signature

```
mvn spring-boot:run -Dspring-boot.run.profiles=certificate
```

- Encryption 

```
mvn spring-boot:run -Dspring-boot.run.profiles=encryption
```

## Generate your own certificate

You can generate your own certificate with the *openssl* tool following these steps:

### Create self-signed certificate

```
openssl genrsa -out server.key 2048
openssl req -new -out server.csr -key server.key
openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
```

### Convert the x.509 cert and key to a pkcs12 file

```
openssl pkcs12 -export -in server.crt -inkey server.key \
               -out server.p12 -name [some-alias] \
               -CAfile ca.crt -caname root
```

### Convert the pkcs12 file to a Java keystore

```
keytool -importkeystore \
        -deststorepass [changeit] -destkeypass [changeit] -destkeystore server.keystore \
        -srckeystore server.p12 -srcstoretype PKCS12 -srcstorepass some-password \
        -alias [some-alias]
```
