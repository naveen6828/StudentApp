package com.naveen.StudentApp.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCSVRepresentation {
    @CsvBindByName(column = "name") // refers to the column name in csv file to which below variable is to be mapped
    private String name;
    @CsvBindByName(column = "age")
    private int age;
    @CsvBindByName(column = "subject")
    private String subject;
}
