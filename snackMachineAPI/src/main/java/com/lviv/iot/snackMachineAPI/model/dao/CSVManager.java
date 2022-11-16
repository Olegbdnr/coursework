package com.lviv.iot.snackMachineAPI.model.dao;

import java.util.List;

public interface CSVManager <T>{
    void writeEntity(T entity);

    T getEntityById (Long id);

    List<T> getAllEntities();

    void deleteEntityById (Long id);

    void updateEntity (Long id, T entity);
}
