package com.naveen.StudentApp.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    private String line1;
    private String city;
    private String state;
    private String zipCode;
}
