package com.naveen.StudentApp.dto;


import com.naveen.StudentApp.entity.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {
    private int studentId;
    private String name;
    private String department;
    private String marks;
    private String className;
    private Address address;

}

