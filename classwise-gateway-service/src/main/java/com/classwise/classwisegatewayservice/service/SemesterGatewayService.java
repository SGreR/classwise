package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.SemesterDTO;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterGatewayService implements ServiceInterface<SemesterDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;

    public SemesterGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
    }

    @Override
    public List<SemesterDTO> getAll() {
        String url = serviceURLs.getSemesterUrl();
        ResponseEntity<List> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), List.class);
        return response.getBody();
    }

    @Override
    public SemesterDTO getById(Long id) {
        String url = serviceURLs.getSemesterUrl() + "/" + id;
        ResponseEntity<SemesterDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class);
        return response.getBody();
    }

    @Override
    public SemesterDTO add(SemesterDTO semester) {
        String url = serviceURLs.getSemesterUrl();
        ResponseEntity<SemesterDTO> response = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(semester), SemesterDTO.class);
        return response.getBody();
    }

    @Override
    public SemesterDTO update(Long id, SemesterDTO semester) {
        String url = serviceURLs.getSemesterUrl() + "/" + id;
        ResponseEntity<SemesterDTO> response = restClientUtil.exchange(url, HttpMethod.PUT, restClientUtil.createHttpEntity(semester), SemesterDTO.class);
        return response.getBody();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        String url = serviceURLs.getSemesterUrl() + "/" + id;
        return restClientUtil.exchange(url, HttpMethod.DELETE, restClientUtil.createHttpEntity(null), SemesterDTO.class);
    }
}
