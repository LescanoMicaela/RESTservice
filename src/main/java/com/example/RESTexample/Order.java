package com.example.RESTexample;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Data
@Table(name = "CUSTOMER_ORDER")
//needs to change table name because order is not valid name
public class Order {

    private @Id @GeneratedValue Long id;

    private String description;
    private Status status;

    Order(String description,Status status){
        this.description = description;
        this.status = status;
    }
}
