package com.naveen.StudentApp.service;

import com.naveen.StudentApp.dto.TeacherCSVRepresentation;
import com.naveen.StudentApp.entity.Teacher;
import com.naveen.StudentApp.repository.TeacherRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;

    public ResponseEntity<?> createTeachersFromCSV(MultipartFile file) throws IOException {
        Set<Teacher> teacherSet = parseCSV(file);
        return new ResponseEntity<>(teacherRepository.saveAll(teacherSet), HttpStatus.OK);

    }

    public ResponseEntity<?> getAllTeachers() {
        return new ResponseEntity<>(teacherRepository.findAll(),HttpStatus.OK);
    }

    private Set<Teacher> parseCSV(MultipartFile file) throws IOException {
        // Reader is created inside try so after try block is executed, it gets deleted
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){

//          A HeaderColumnNameMappingStrategy is created to map the column names from the CSV file to the fields of the TeacherCSVRepresentation class.
            HeaderColumnNameMappingStrategy<TeacherCSVRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
//          strategy tells the CSV parser how to map each column in the CSV file to the corresponding field in the TeacherCSVRepresentation class.
            strategy.setType(TeacherCSVRepresentation.class);
//            A CsvToBean object is created using a CsvToBeanBuilder
//            This builder configures the CSV parser with the mapping strategy and other settings like ignoring empty lines and leading white spaces.
            CsvToBean<TeacherCSVRepresentation> csvToBean = new CsvToBeanBuilder<TeacherCSVRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse().stream()
                    .map(csvLine -> Teacher.builder()
                            .name(csvLine.getName())
                            .age(csvLine.getAge())
                            .subject(csvLine.getSubject())
                            .build())
                    .collect(Collectors.toSet());
        }
    }
}
