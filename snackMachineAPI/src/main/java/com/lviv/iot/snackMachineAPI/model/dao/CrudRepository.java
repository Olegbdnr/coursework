package com.lviv.iot.snackMachineAPI.model.dao;

import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;

import java.util.List;

public interface CrudRepository<T, ID> {
    void save(T entity) throws EntityExistException, EntityNotExistException;

    void delete(ID id) throws EntityNotExistException;

    T findById(ID id) throws EntityNotExistException;

    List<T> findAll();

    void update(ID id, T entity) throws EntityNotExistException;
}
