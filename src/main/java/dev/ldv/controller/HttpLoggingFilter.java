package dev.ldv.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper(request);

        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequest(requestWrapper);
            logResponse(responseWrapper);

            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String body = getBody(request.getContentAsByteArray(), request.getCharacterEncoding());

        log.info(
                """
                
                HTTP REQUEST
                method={}
                uri={}
                headers={}
                params={}
                body={}
                """,
                request.getMethod(),
                request.getRequestURI(),
                getHeaders(request),
                getParams(request),
                body
        );
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String body = getBody(response.getContentAsByteArray(), response.getCharacterEncoding());

        log.info(
                """
                
                HTTP RESPONSE
                status={}
                headers={}
                body={}
                """,
                response.getStatus(),
                getHeaders(response),
                body
        );
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        request::getHeader
                ));
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        return response.getHeaderNames()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        response::getHeader
                ));
    }

    private Map<String, String> getParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(", ", entry.getValue())));
    }

    private String getBody(byte[] content, String encoding) {
        if (content.length == 0) {
            return "";
        }

        try {
            return new String(content, Charset.forName(encoding));
        } catch (Exception e) {
            return "<failed to read body>";
        }
    }
}
