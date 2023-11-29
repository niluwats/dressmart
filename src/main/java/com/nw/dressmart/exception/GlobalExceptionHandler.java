package com.nw.dressmart.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.*;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Map<String,String>>>handleInvalidArguments(MethodArgumentNotValidException ex){
        List<FieldError> errors=ex.getBindingResult().getFieldErrors();
        Map<String,String> errorFields=new HashMap<>();

        for(FieldError error:errors){
            errorFields.put(error.getField(),error.getDefaultMessage());
        }

        Map<String,Map<String,String>> errorResponse=new HashMap<>();
        errorResponse.put("errors",errorFields);

        return  new ResponseEntity<>(errorResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception){
        ProblemDetail errorDetail=null;

        if(exception instanceof BadCredentialsException){
            errorDetail=ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(401),exception.getMessage()
            );
            errorDetail.setProperty("description","Incorrect email or password");
        }

        if(exception instanceof AccountStatusException){
            errorDetail=ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403), exception.getMessage()
            );
            errorDetail.setProperty("description","The account is locked");
        }

        if(exception instanceof AccessDeniedException){
            errorDetail=ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403),exception.getMessage()
            );
            errorDetail.setProperty("description","Unauthorized");
        }

        if(exception instanceof SignatureException){
            errorDetail=ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403), exception.getMessage()
            );
            errorDetail.setProperty("description","Invalid jwt");
        }

        if(exception instanceof ExpiredJwtException){
            errorDetail=ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(403), exception.getMessage()
            );
            errorDetail.setProperty("description","Expired jwt");
        }

        if(errorDetail == null){
            errorDetail=ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(500), exception.getMessage()
            );
            errorDetail.setProperty("description","Internal server error");
        }

        return errorDetail;
    }
}
