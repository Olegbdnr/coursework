package com.lviv.iot.snackMachineAPI.model.service.impl;

import com.lviv.iot.snackMachineAPI.model.dao.SnackRepository;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import com.lviv.iot.snackMachineAPI.model.service.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnackServiceImpl implements SnackService {
    @Autowired
    SnackRepository storage;

    @Override
    public void save(Snack snack) throws EntityExistException, EntityNotExistException {
        storage.save(snack);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        storage.delete(id);
    }

    @Override
    public Snack findById(Long id) throws EntityNotExistException {
        return storage.findById(id);
    }

    @Override
    public List<Snack> findAll() {
        return storage.findAll();
    }

    @Override
    public void update(Long id, Snack snack) throws EntityNotExistException {
        storage.update(id, snack);
    }
}
