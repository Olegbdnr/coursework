package com.lviv.iot.snackMachineAPI.controller;

import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import com.lviv.iot.snackMachineAPI.model.service.SnackMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SnackMachineController {
    @Autowired
    private SnackMachineService service;

    @GetMapping(path = "machine")
    public List<SnackMachine> getAll() {
        return service.findAll();
    }

    @GetMapping(path = "machine/{id}")
    public SnackMachine getById(@PathVariable("id") Long id) {
        try {
            return service.findById(id);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(path = "machine")
    public void post(@RequestBody SnackMachine machine) {
        try {
            service.save(machine);
        } catch (EntityExistException | EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "machine/{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "machine")
    public void update(@RequestBody SnackMachine machine) {
        try {
            service.update(machine.getId(), machine);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
