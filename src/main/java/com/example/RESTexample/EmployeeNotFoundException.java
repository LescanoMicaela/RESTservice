package com.example.RESTexample;

//used to render an HTTP 404
public class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(Long id){
        super("Could not find employee "+ id);
    }
}
