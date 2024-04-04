package com.naveen.StudentApp.controller;

import com.naveen.StudentApp.repository.TeacherRepository;
import com.naveen.StudentApp.service.TeacherService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    //   The @RequestPart annotation in Spring Boot is used to associate a part of a multipart/form-data request with a method argument
    // When you annotate a method argument with @RequestPart, it associates that part of the multipart request with the specified argument.
    // These requests can include multipart files (e.g., audio, image files) along with other data.
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createTeachersFromCSV(@RequestPart("file") MultipartFile file) throws IOException {
       return teacherService.createTeachersFromCSV(file);

    }

    @GetMapping
    public ResponseEntity<?> getAllTeachers(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportTeachersAsCsv(HttpServletResponse response) throws Exception {
        return teacherService.exportTeachersAsCsv(response);
    }

}
