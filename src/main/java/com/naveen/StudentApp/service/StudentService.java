package com.naveen.StudentApp.service;

import com.naveen.StudentApp.dto.StudentRequest;
import com.naveen.StudentApp.dto.StudentResponse;
import com.naveen.StudentApp.entity.Student;
import com.naveen.StudentApp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    //    Get all Students with orderBy and sortBy
    public ResponseEntity<?> getAllStudents(String orderBy, String sortBy){

        List<Student> studentList = studentRepository.findAll();
        List<String> fields = Arrays.stream(Student.class.getDeclaredFields())
                .map(field -> field.getName())
                .collect(Collectors.toList());
        return sortAndGroupByStudents(studentList,fields,sortBy,orderBy);
    }

    //    Gets student by student ID
    public ResponseEntity<?> getStudentById(int id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()) {
            StudentResponse studentResponse = toDTO(student.get());
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No Student with provided id", HttpStatus.CONFLICT);
        }
    }

    //    Gets students of specific class
    public ResponseEntity<?> getStudentsByClass(String className){

        List<Student> studentList = studentRepository.findByClassName(className);
        List<StudentResponse> studentResponseList = studentList.stream()
                .map(student -> toDTO(student))
                .toList();
        return new ResponseEntity<>(studentResponseList, HttpStatus.OK);
    }

    public ResponseEntity<?> getStudentsByCity(String city) {
        List<Student> studentList = studentRepository.findByCity(city);
        List<StudentResponse> studentResponseList = studentList.stream()
                .map(student -> toDTO(student))
                .toList();
        return new ResponseEntity<>(studentResponseList, HttpStatus.OK);

    }


    //    Create a student
    public ResponseEntity<StudentResponse> createStudent(StudentRequest studentRequest){
        Student student = fromDTO(studentRequest);
        student = studentRepository.save(student);
        StudentResponse studentResponse = toDTO(student);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }



    //    Update a student
    public ResponseEntity<?> updateStudent(StudentRequest studentRequest, int id){
        Student student = fromDTO(studentRequest);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            student.setStudentId(id);
            studentRepository.save(student);
            StudentResponse  studentResponse = toDTO(student);
            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Invalid student id", HttpStatus.OK);
        }
    }


    //    Delete a student details
    public ResponseEntity<?> deleteStudent(int id){
        Optional <Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            studentRepository.delete(student.get());
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Invalid student id", HttpStatus.OK);
        }
    }

    //    Convert from DTO(request) to Student and return student obj
    private Student fromDTO(StudentRequest studentRequest){
        Student student = Student.builder()
                .name(studentRequest.getName())
                .department(studentRequest.getDepartment())
                .marks(studentRequest.getMarks())
                .className(studentRequest.getClassName())
                .address(studentRequest.getAddress())
                .build();

        return student;

    }

    //    Convert from Student to DTO(response) and return Student response
    private StudentResponse toDTO(Student student){
        StudentResponse studentResponse = StudentResponse.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .className(student.getClassName())
                .department(student.getDepartment())
                .address(student.getAddress())
                .build();

        return studentResponse;
    }


    //    Sort, order and group students and returns a map. Groups by department by  default
    private ResponseEntity<?> sortAndGroupByStudents(List<Student> studentList, List<String> fields, String sortBy, String orderBy){
        Comparator<Student> comparator = null;
        Map<String, List<Student>> studentsByDepartment = null;
        List<Student> sortedList = null;
        if(fields.contains(sortBy)){
            switch (sortBy){
                case "studentId":
                    comparator = Comparator.comparing(Student::getStudentId);
                    break;
                case "name":
                    comparator = Comparator.comparing(Student::getName);
                    break;
                case "department":
                    comparator = Comparator.comparing(Student::getDepartment);
                    break;
                case "marks":
                    comparator = Comparator.comparing(Student::getMarks);
                    break;
            }

        }
        else{
            return new ResponseEntity<>("Check sortBy field in header", HttpStatus.OK);
        }
        if(orderBy.equals("ascending")){
//            studentsByDepartment = studentList.stream()
//                    .sorted(comparator)
//                    .collect(Collectors.groupingBy(Student::getDepartment));

            sortedList = studentList.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());


        } else if (orderBy.equals("descending")){
//            studentsByDepartment = studentList.stream()
//                    .sorted(comparator.reversed() )
//                    .collect(Collectors.groupingBy(Student::getDepartment));

            sortedList = studentList.stream()
                    .sorted(comparator.reversed())
                    .collect(Collectors.toList());
        }
        else{
            return new ResponseEntity<>("Check orderBy field in header", HttpStatus.OK);
        }
        Map<String, List<StudentResponse>> studentsResponse = sortedList.stream()
                .map(student -> toDTO(student))
                .collect(Collectors.groupingBy(StudentResponse::getDepartment));
        return new ResponseEntity<>(studentsResponse,HttpStatus.OK);

    }


}

