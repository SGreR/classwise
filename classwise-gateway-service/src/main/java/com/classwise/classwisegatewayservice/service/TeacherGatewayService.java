package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.TeacherDTO;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherGatewayService implements ServiceInterface<TeacherDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;

    public TeacherGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
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
        String url = serviceURLs.getTeachersUrl();
        ResponseEntity<TeacherDTO> response = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(teacher), TeacherDTO.class);
        return response.getBody();
    }

    @Override
    public TeacherDTO update(Long id, TeacherDTO teacher) {
        String url = serviceURLs.getTeachersUrl() + "/" + id;
        ResponseEntity<TeacherDTO> response = restClientUtil.exchange(url, HttpMethod.PUT, restClientUtil.createHttpEntity(teacher), TeacherDTO.class);
        return response.getBody();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        String url = serviceURLs.getTeachersUrl() + "/" + id;
        return restClientUtil.exchange(url, HttpMethod.DELETE, restClientUtil.createHttpEntity(null), TeacherDTO.class);
    }
}
