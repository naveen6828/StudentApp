package com.naveen.StudentApp.dto;


import com.naveen.StudentApp.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse implements Serializable {
    private int studentId;
    private String name;
    private String department;
    private String marks;
    private String className;
    private AddressResponse address;
}

