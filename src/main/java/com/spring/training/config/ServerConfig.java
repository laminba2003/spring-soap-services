package com.spring.training.config;

import lombok.Data;

import java.util.Map;

@Data
public class ServerConfig {
    Map<String, Object> security;
}
