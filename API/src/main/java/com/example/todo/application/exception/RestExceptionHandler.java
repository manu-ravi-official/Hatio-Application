package com.example.todo.application.exception;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.todo.application.view.ResponseView;

@ControllerAdvice
@RestControllerAdvice
public class RestExceptionHandler {

  @Value("${global.language}")
  private String globalLanguage;

  @ExceptionHandler(value = { ResponseStatusException.class })
  public ResponseEntity<Object> responseStatus(ResponseStatusException ex) {
    String reason = ex.getReason();
    String[] arrOfStr;
    // Split the reason into parts using "-" as the delimiter
    if (reason != null) {
      arrOfStr = reason.split("-", 2);
    } else {
      arrOfStr = new String[0];
    }

    String errorCode = null;
    String errorMessage = null;
    if (arrOfStr.length > 1) {
      errorCode = arrOfStr[0];
      errorMessage = arrOfStr[1];
    } else {
      errorMessage = ex.getReason();
    }

    ResponseView responseView = new ResponseView(errorMessage, errorCode);
    return new ResponseEntity<>(responseView, null, ex.getStatusCode());
  }

  @ExceptionHandler(value = { MethodArgumentNotValidException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<ResponseView> formValidation(MethodArgumentNotValidException ex) {
    return ex.getBindingResult().getFieldErrors().stream().map(x -> {
      String err = x.getDefaultMessage();
      String[] arrOfError = err.split("-", 2);
      String errorCode = null;
      String errorMessage = null;
      if (arrOfError.length > 1) {
        errorCode = arrOfError[0];
        errorMessage = arrOfError[1];
      } else {
        errorMessage = err;
      }
      if (errorMessage == null) {
        errorMessage = err;
      }
      return new ResponseView(errorMessage, errorCode);
    }).toList();
  }

}
