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
    private final Map<Long, SnackMachine> storage = new HashMap<>();

    private void fillSnackMachinesFromFile() {
        List<SnackMachine> snackMachines = csvManager.getAllMachines();
        for (SnackMachine snackMachine : snackMachines) {
            storage.put(snackMachine.getId(), snackMachine);
        }
    }

    @Override
    public void save(SnackMachine machine) throws EntityExistException, EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnackMachinesFromFile();
        }
        csvManager.writeSnackMachine(machine);
        storage.put(machine.getId(), machine);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnackMachinesFromFile();
        }
        csvManager.deleteSnackMachineById(id);
        storage.remove(id);
    }

    @Override
    public SnackMachine findById(Long id) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnackMachinesFromFile();
        }
        return storage.get(id);
    }

    @Override
    public List<SnackMachine> findAll() {
        if (storage.isEmpty()) {
            fillSnackMachinesFromFile();
        }
        return storage.values().stream().toList();
    }

    @Override
    public void update(Long id, SnackMachine machine) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnackMachinesFromFile();
        }
        csvManager.updateSnackMachine(id, machine);
        storage.replace(id, machine);
    }
}
