package com.example.RESTexample;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
//@ControllerAdvice annotation allows us to consolidate our multiple, scattered
// @ExceptionHandlers from before into a single, global error handling component
public class EmployeeNotFoundAdvice {

    @ResponseBody
    //@responseBody : This advice is rendered straight into the response body
    @ExceptionHandler(EmployeeNotFoundException.class)
    //Only response if an EmployeedNotFoundException is thrown
    @ResponseStatus(HttpStatus.NOT_FOUND)
    //issues an Http not found
    String employeeNotFoundHandleer(EmployeeNotFoundException ex){
        return ex.getMessage();
    }
}
