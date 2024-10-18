package com.studentapp.config;

import com.studentapp.exception.ErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorHandlingConfig {

    @Bean
    public ErrorHandler exceptionHandler() {
        return new ErrorHandler();
    }
}
