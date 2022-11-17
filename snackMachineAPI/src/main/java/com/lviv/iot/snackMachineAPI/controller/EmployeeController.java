package com.lviv.iot.snackMachineAPI.controller;

import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Employee;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import com.lviv.iot.snackMachineAPI.model.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService service;

    @GetMapping(path = "employee")
    public List<Employee> getAll() {
        return service.findAll();
    }

    @GetMapping(path = "employee/{id}")
    public Employee getById(@PathVariable("id") Long id) {
        try {
            return service.findById(id);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(path = "employee")
    public void post(@RequestBody Employee employee) {
        try {
            service.save(employee);
        } catch (EntityExistException | EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(path = "employee/{id}")
    public void delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(path = "employee")
    public void update(@RequestBody Employee employee) {
        try {
            service.update(employee.getId(), employee);
        } catch (EntityNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
