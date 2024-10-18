package com.studentapp.config;

import com.studentapp.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.logging.Logger;


@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Value("${api.key}")
    private String APIKey;

    private static final Logger logger = Logger.getLogger(SecurityInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqAPIKey = request.getHeader("x-api-key");
        logger.info("api-key received in request :: {}");
        if (!Objects.equals(APIKey, reqAPIKey)) {
            logger.warning("Request received without api-key");
            throw new AccessDeniedException("Unauthorised request, Missing api-key in header");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
