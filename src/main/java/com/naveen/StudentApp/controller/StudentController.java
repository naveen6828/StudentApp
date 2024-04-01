package com.naveen.StudentApp.controller;



import com.naveen.StudentApp.dto.StudentRequest;
import com.naveen.StudentApp.dto.StudentResponse;
import com.naveen.StudentApp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService studentService;

    // Get list of students segrated by class like all 10th clas in 1 list 9th class in other list
    // if we ask seperate class we need to get seperate class

    @GetMapping()
    public ResponseEntity<?> getAllStudents(@RequestHeader(value = "orderBy", defaultValue =  "ascending", required = false) String orderBy,
                                            @RequestHeader(value =  "sortBy", defaultValue = "studentId", required = false) String sortBy){

        return studentService.getAllStudents(orderBy, sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id){
        return studentService.getStudentById(id);
    }

    @GetMapping("/class/{className}")
    public ResponseEntity<?> getStudentsByClass(@PathVariable String className){
        return studentService.getStudentsByClass(className);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<?> getStudentsByCity(@PathVariable String city){
        return studentService.getStudentsByCity(city);
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest){
        return studentService.createStudent(studentRequest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody StudentRequest studentRequest, @PathVariable int id){
        return studentService.updateStudent(studentRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id){
        return studentService.deleteStudent(id);

    }


}

