package com.lviv.iot.snackMachineAPI.model.dao.Impl;

import com.lviv.iot.snackMachineAPI.model.dao.SnackRepository;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SnackRepositoryImpl implements SnackRepository {
    @Autowired
    private EntityCSV csvManager;
    private final Map<Long, Snack> storage = new HashMap<>();

    private void fillSnacksFromFile() {
        List<Snack> snacks = csvManager.getAllSnacks();
        for (Snack snack : snacks) {
            storage.put(snack.getId(), snack);
        }
    }

    @Override
    public void save(Snack snack) throws EntityExistException, EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnacksFromFile();
        }
        csvManager.writeSnack(snack);
        storage.put(snack.getId(), snack);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnacksFromFile();
        }
        csvManager.deleteSnackById(id);
        storage.remove(id);
    }

    @Override
    public Snack findById(Long id) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnacksFromFile();
        }
        return storage.get(id);
    }

    @Override
    public List<Snack> findAll() {
        if (storage.isEmpty()) {
            fillSnacksFromFile();
        }
        return storage.values().stream().toList();
    }

    @Override
    public void update(Long id, Snack snack) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillSnacksFromFile();
        }
        csvManager.updateSnack(id, snack);
        storage.replace(id, snack);
    }
}
