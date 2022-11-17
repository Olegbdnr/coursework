package com.lviv.iot.snackMachineAPI.controller;

import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import com.lviv.iot.snackMachineAPI.model.service.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SnackController {
    @Autowired
    SnackService service;

    @GetMapping(path = "snack")
    public List<Snack> getAll() {
        return service.findAll();
    }

    @GetMapping(path = "snack/{id}")
    public Snack getById(@PathVariable("id") Long id) {
        try {
            return service.findById(id);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(path = "snack")
    public void post(@RequestBody Snack snack) {
        try {
            service.save(snack);
        } catch (EntityExistException | EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "snack/{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "snack")
    public void update(@RequestBody Snack snack) {
        try {
            service.update(snack.getId(), snack);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
