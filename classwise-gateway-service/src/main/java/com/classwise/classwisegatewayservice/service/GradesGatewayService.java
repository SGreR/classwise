package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.GradesDTO;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradesGatewayService implements ServiceInterface<GradesDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;

    public GradesGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
    }

    @Override
    public List<GradesDTO> getAll() {
        String url = serviceURLs.getGradesUrl();
        ResponseEntity<List> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), List.class);
        return response.getBody();
    }

    @Override
    public GradesDTO getById(Long id) {
        String url = serviceURLs.getGradesUrl() + "/" + id;
        ResponseEntity<GradesDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), GradesDTO.class);
        return response.getBody();
    }

    @Override
    public GradesDTO add(GradesDTO grades) {
        String url = serviceURLs.getGradesUrl();
        ResponseEntity<GradesDTO> response = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(grades), GradesDTO.class);
        return response.getBody();
    }

    @Override
    public GradesDTO update(Long id, GradesDTO grades) {
        String url = serviceURLs.getGradesUrl() + "/" + id;
        ResponseEntity<GradesDTO> response = restClientUtil.exchange(url, HttpMethod.PUT, restClientUtil.createHttpEntity(grades), GradesDTO.class);
        return response.getBody();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        String url = serviceURLs.getGradesUrl() + "/" + id;
        return restClientUtil.exchange(url, HttpMethod.DELETE, restClientUtil.createHttpEntity(null), GradesDTO.class);
    }
}
