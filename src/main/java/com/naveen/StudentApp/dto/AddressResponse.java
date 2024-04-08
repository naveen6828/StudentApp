package com.naveen.StudentApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse implements Serializable {
    private long addressId;
    private String line1;
    private String city;
    private String state;
    private String zipCode;
}
