package com.qualitychemicals.qciss.exceptions;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class AppExceptionHandler  extends ResponseEntityExceptionHandler  {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(

            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details=ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        ErrorDetails errorDetails =new ErrorDetails(new Date(),"Validation Error","",details);
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetails errorDetails =new ErrorDetails(
                new Date(),"Http Request Method Not Supported:",ex.getLocalizedMessage(),null);
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails =new ErrorDetails(
                new Date(),"Media Type not supported",ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails =new ErrorDetails(
                new Date(),"Missing Path Variable: "+ex.getVariableName(),ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails =new ErrorDetails(
                new Date(),"Type Mismatch: "+ex.getPropertyName(),ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails =new ErrorDetails(
                new Date(),"No handler Found:",ex.getLocalizedMessage(), null);
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>(Collections.singletonList(ex.getMessage()));
        ErrorDetails errorDetails =new ErrorDetails(new Date(),"Media Type Not Acceptable",
                ex.getCause().getMessage(),details);
        return new ResponseEntity<>(errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable
            (HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>(Collections.singletonList(ex.getMessage()));
        ErrorDetails errorDetails =new ErrorDetails(new Date(),"Message Not Readable",
                ex.getCause().getMessage(),details);
        return new ResponseEntity<>(errorDetails, status);
    }


    //handle global exceptions
   /*@ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException
    (Exception exception, WebRequest request){
       ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(true), null);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    //handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException
    (ResourceNotFoundException exception, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),null);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }





}
