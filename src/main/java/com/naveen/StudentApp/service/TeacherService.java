package com.naveen.StudentApp.service;

import com.naveen.StudentApp.dto.TeacherCSVRepresentation;
import com.naveen.StudentApp.entity.Teacher;
import com.naveen.StudentApp.repository.TeacherRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    public ResponseEntity<?> exportTeachersAsCsv(HttpServletResponse response) throws Exception {
        String filename = "TeacherData.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename+ "\"");

//        StatefulBeanToCsv<Teacher> beanToCsv = new StatefulBeanToCsvBuilder<Teacher>(response.getWriter())
//                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
//                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//                .withOrderedResults(false)
//                .build();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(byteArrayOutputStream);
        StatefulBeanToCsv<Teacher> beanToCsv = new StatefulBeanToCsvBuilder<Teacher>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
                .build();
        //write all employees data to csv file
        beanToCsv.write(teacherRepository.findAll());
        writer.flush();
        return new ResponseEntity<>("CSV file will be downloaded if you execute the url in any browser", HttpStatus.OK);
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
