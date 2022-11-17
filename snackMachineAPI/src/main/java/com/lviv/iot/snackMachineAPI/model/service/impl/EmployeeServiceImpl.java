package com.lviv.iot.snackMachineAPI.model.service.impl;

import com.lviv.iot.snackMachineAPI.model.dao.EmployeeRepository;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Employee;
import com.lviv.iot.snackMachineAPI.model.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository storage;

    @Override
    public void save(Employee employee) throws EntityExistException, EntityNotExistException {
        storage.save(employee);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        storage.delete(id);
    }

    @Override
    public Employee findById(Long id) throws EntityNotExistException {
        return storage.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return storage.findAll();
    }

    @Override
    public void update(Long id, Employee employee) throws EntityNotExistException {
        storage.update(id, employee);
    }
}
