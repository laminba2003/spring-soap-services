package com.spring.training.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    String message;
}
