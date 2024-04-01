package com.naveen.StudentApp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String name;
    private String department;
    private String marks;
    private String className;
    @OneToOne(cascade = CascadeType.ALL)    // To save address data automatically
    private Address address;

    Student student = new Student();

}
