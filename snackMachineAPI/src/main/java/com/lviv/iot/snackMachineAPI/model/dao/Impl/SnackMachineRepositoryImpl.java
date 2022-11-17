package com.lviv.iot.snackMachineAPI.model.dao.Impl;

import com.lviv.iot.snackMachineAPI.model.dao.SnackMachineRepository;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@Getter
@Setter
public class SnackMachineRepositoryImpl implements SnackMachineRepository {
    @Autowired
    private EntityCSV csvManager;
    private Map<Long, SnackMachine> storage = new HashMap<>();

    @Override
    public void save(SnackMachine machine) throws EntityExistException, EntityNotExistException {
        csvManager.writeSnackMachine(machine);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        csvManager.deleteSnackMachineById(id);
    }

    @Override
    public SnackMachine findById(Long id) throws EntityNotExistException {
        return csvManager.getSnackMachineById(id);
    }

    @Override
    public List<SnackMachine> findAll() {
        return csvManager.getAllMachines();
    }

    @Override
    public void update(Long id, SnackMachine machine) throws EntityNotExistException {
        csvManager.updateSnackMachine(id, machine);
        //ToDo: check if fileEntity correspond to entity exist in repository
        storage.replace(id, machine);
    }
}
