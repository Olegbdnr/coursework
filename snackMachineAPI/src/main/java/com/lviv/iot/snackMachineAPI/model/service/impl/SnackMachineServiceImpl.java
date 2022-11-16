package com.lviv.iot.snackMachineAPI.model.service.impl;

import com.lviv.iot.snackMachineAPI.model.dao.Impl.SnackMachineRepositoryImpl;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import com.lviv.iot.snackMachineAPI.model.service.SnackMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnackMachineServiceImpl implements SnackMachineService {
    @Autowired
    SnackMachineRepositoryImpl storage;

    @Override
    public void save(SnackMachine machine) throws EntityExistException {
        storage.save(machine);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        storage.delete(id);
    }

    @Override
    public SnackMachine findById(Long id) throws EntityNotExistException {
        return storage.findById(id);
    }

    @Override
    public List<SnackMachine> findAll() {
        return storage.findAll();
    }

    @Override
    public void update(Long id, SnackMachine machine) throws EntityNotExistException {
        storage.update(id, machine);
    }
}
