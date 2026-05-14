package com.challenge.coupon.exception;

import io.micrometer.common.lang.NonNull;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(status);
        problem.setType(URI.create("/errors/invalid-request"));
        problem.setTitle("Erro de Validação");
        problem.setDetail("Um ou mais campos estão inválidos.");

        Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        f -> f.getDefaultMessage() != null ?
                                f.getDefaultMessage() : "Valor inválido",
                        (existing, replacement) -> existing
                ));

        problem.setProperty("violations", fields);
        problem.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(status).body(problem);
    }


    @ExceptionHandler(CouponNotFoundException.class)
    ProblemDetail handleNotFound(CouponNotFoundException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setType(URI.create("/errors/resource-not-found"));

        problem.setTitle("Cupom não encontrado");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }


    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessExceptions(BusinessException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        problem.setType(URI.create("/errors/business-rules"));

        problem.setTitle("Violação de Regra de Negócio");

        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleUncaught(Exception e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado.");

        problem.setType(URI.create("/errors/internal-server-error"));
        problem.setTitle("Erro do Servidor");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}