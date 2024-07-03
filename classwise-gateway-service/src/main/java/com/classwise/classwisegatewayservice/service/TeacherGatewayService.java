package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.TeacherDTO;
import com.classwise.classwisegatewayservice.util.MessageBuilderUtil;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

import java.util.List;

@Service
public class TeacherGatewayService implements ServiceInterface<TeacherDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TeacherGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs, KafkaTemplate<String, String> kafkaTemplate) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public List<TeacherDTO> getAll() {
        String url = serviceURLs.getTeachersUrl();
        ResponseEntity<List> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), List.class);
        return response.getBody();
    }

    @Override
    public TeacherDTO getById(Long id) {
        String url = serviceURLs.getTeachersUrl() + "/" + id;
        ResponseEntity<TeacherDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), TeacherDTO.class);
        return response.getBody();
    }

    @Override
    public TeacherDTO add(TeacherDTO teacher) {
        Message message = MessageBuilderUtil.buildMessage("teacher-events", "create-teacher", teacher);
        kafkaTemplate.send(message);
        return teacher;
    }

    @Override
    public TeacherDTO update(Long id, TeacherDTO teacher) {
        teacher.setTeacherId(id);
        Message message = MessageBuilderUtil.buildMessage("teacher-events", "update-teacher", teacher);
        kafkaTemplate.send(message);
        return teacher;
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Message message = MessageBuilderUtil.buildMessage("teacher-events", "delete-teacher", id);
        kafkaTemplate.send(message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
