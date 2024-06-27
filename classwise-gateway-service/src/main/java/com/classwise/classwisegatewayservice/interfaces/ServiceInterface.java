package com.classwise.classwisegatewayservice.interfaces;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> getAll();
    T getById(Long id);
    T add(T t);
    T update(Long id, T t);
    ResponseEntity<?> deleteById(Long id);
}
