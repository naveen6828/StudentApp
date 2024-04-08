package com.naveen.StudentApp.producer;

import com.naveen.StudentApp.dto.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentProducer {

    private final JmsTemplate jmsTemplate;
    public void getAllStudentsQueue(List<StudentResponse> studentResponseList){
        jmsTemplate.convertAndSend("studentQueue",studentResponseList);
    }
}
