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

    public void save(SnackMachine machine) throws EntityExistException {
        csvManager.writeSnackMachine(machine);
    }

    public void delete(Long id) throws EntityNotExistException {
        csvManager.deleteSnackMachineById(id);
    }

    public SnackMachine findById(Long id) throws EntityNotExistException {
        SnackMachine fileEntity = null;
        fileEntity = csvManager.getSnackMachineById(id);
        return fileEntity;
    }

    public List<SnackMachine> findAll() {
        List<SnackMachine> allEntities = csvManager.getAll();
        //ToDo: compare allEntities to repository entities
        return allEntities;
    }

    public void update(Long id, SnackMachine machine) throws EntityNotExistException {
        csvManager.updateSnackMachine(id, machine);
        //ToDo: check if fileEntity correspond to entity exist in repository
        storage.replace(id, machine);
    }
}
