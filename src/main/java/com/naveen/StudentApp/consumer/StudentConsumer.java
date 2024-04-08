package com.naveen.StudentApp.consumer;

import com.naveen.StudentApp.dto.StudentResponse;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentConsumer {

    @JmsListener(destination = "studentQueue")
    public void getMessage(List<StudentResponse> studentResponses){
        studentResponses.forEach(studentResponse -> System.out.println(studentResponse.toString()));
    }


}
