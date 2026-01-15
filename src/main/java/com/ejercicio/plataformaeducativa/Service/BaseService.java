package com.ejercicio.plataformaeducativa.Service;

import java.util.List;

public interface BaseService<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T save(T entity);
    void deleteById(ID id);
    T update(T entity, ID id);
}
