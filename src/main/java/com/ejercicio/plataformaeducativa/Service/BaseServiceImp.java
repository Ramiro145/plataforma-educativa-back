package com.ejercicio.plataformaeducativa.Service;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public abstract class BaseServiceImp<T, ID> implements BaseService<T,ID> {

    protected final JpaRepository<T,ID> repository;

    protected BaseServiceImp(JpaRepository<T,ID> repository){
        this.repository = repository;
    }


    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public T update(T entity, ID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found");
        }
        return repository.save(entity);
    }


}
