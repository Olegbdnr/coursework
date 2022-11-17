package com.lviv.iot.snackMachineAPI.model.dao.Impl;

import com.lviv.iot.snackMachineAPI.model.dao.EmployeeRepository;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Employee;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    private EntityCSV csvManager;
    private Map<Long, Employee> storage = new HashMap<>();

    @Override
    public void save(Employee employee) throws EntityExistException {
        csvManager.writeEmployee(employee);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        csvManager.deleteEmployeeById(id);
    }

    @Override
    public Employee findById(Long id) throws EntityNotExistException {
        return csvManager.getEmployeeById(id);
    }

    @Override
    public List<Employee> findAll() {
        return csvManager.getAllEmployees();
    }

    @Override
    public void update(Long id, Employee employee) throws EntityNotExistException {
        csvManager.updateEmployee(id, employee);
    }
}
