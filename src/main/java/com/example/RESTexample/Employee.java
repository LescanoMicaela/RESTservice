package com.example.RESTexample;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
//@Data is a Lombok annotation to create all the getters,
// setters, equals, hash, and toString methods, based on the fields
@Entity
public class Employee {

    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    Employee(String name, String role){
        this.name = name;
        this.role = role;
    }
}
