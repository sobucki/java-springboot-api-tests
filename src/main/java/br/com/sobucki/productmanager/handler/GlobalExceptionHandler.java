package br.com.sobucki.productmanager.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    Map<String, String> errors = new HashMap<>();
    // Extrair a mensagem da causa raiz, se disponível
    String errorMessage = ex.getCause() != null && ex.getCause().getMessage() != null
        ? ex.getCause().getMessage()
        : "Invalid input format.";
    errors.put("error", errorMessage);
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
