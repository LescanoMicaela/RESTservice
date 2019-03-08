package com.example.RESTexample;

//used to render an HTTP 404
public class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long id) {super("Could not find order "+ id);}
}
