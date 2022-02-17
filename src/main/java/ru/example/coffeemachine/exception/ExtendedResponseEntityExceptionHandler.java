package ru.example.coffeemachine.exception;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Set;

@RestControllerAdvice
public class ExtendedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CUSTOM_MSG_TEMPLATE_ALLOWED = " Allowed types: %s.";
    private static final String CUSTOM_MSG_TEMPLATE_NOT_SUPPORTED = "%s is not supported.%s Type given: [%s]";

    /**
     * 404 NOT_FOUND
     * - spring.mvc.throw-exception-if-no-handler-found=true
     * - вызываем несуществующий метод
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            @NonNull HttpHeaders headers,
            HttpStatus status,
            @NonNull WebRequest request
    ) {
        ErrorBody body = new ErrorBody()
                .setTimestamp(LocalDateTime.now())
                .setCustomMessage("(NoHandlerFoundException) The handler for the request method was not found")
                .setDebugMessage(ex.getMessage())
                .setStatus(status.value())
                .setStatusName(status.name());

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * 405 METHOD_NOT_ALLOWED
     * - вместо POST - GET
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request
    ) {
        pageNotFoundLogger.warn(ex.getMessage());

        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        String allowed = "";

        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
            allowed += String.format(
                    CUSTOM_MSG_TEMPLATE_ALLOWED,
                    supportedMethods.toString()
            );
        }

        ErrorBody body = new ErrorBody()
                .setTimestamp(LocalDateTime.now())
                .setCustomMessage(String.format(
                        CUSTOM_MSG_TEMPLATE_NOT_SUPPORTED,
                        "(HttpRequestMethodNotSupportedException) Type method",
                        allowed,
                        ex.getMethod()
                ))
                .setDebugMessage(ex.getMessage())
                .setStatus(status.value())
                .setStatusName(status.name());

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
