package com.lviv.iot.snackMachineAPI.model.dao.Impl;

import com.lviv.iot.snackMachineAPI.model.dao.SnackMachineRepository;
import com.lviv.iot.snackMachineAPI.model.dao.SnackRepository;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SnackRepositoryImpl implements SnackRepository {
    @Autowired
    private EntityCSV csvManager;
    private Map<Long, Snack> storage = new HashMap<>();

    @Override
    public void save(Snack snack) throws EntityExistException, EntityNotExistException {
        csvManager.writeSnack(snack);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        csvManager.deleteSnackById(id);
    }

    @Override
    public Snack findById(Long id) throws EntityNotExistException {
        return csvManager.getSnackById(id);
    }

    @Override
    public List<Snack> findAll() {
        return csvManager.getAllSnacks();
    }

    @Override
    public void update(Long id, Snack snack) throws EntityNotExistException {
        csvManager.updateSnack(id, snack);
    }
}
