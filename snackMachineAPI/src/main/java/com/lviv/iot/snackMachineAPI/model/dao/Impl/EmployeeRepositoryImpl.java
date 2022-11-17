package com.lviv.iot.snackMachineAPI.model.dao.Impl;

import com.lviv.iot.snackMachineAPI.model.dao.EmployeeRepository;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    private EntityCSV csvManager;
    private final Map<Long, Employee> storage = new HashMap<>();

    private void fillEmployeesFromFile() {
        List<Employee> employees = csvManager.getAllEmployees();
        for (Employee employee : employees) {
            storage.put(employee.getId(), employee);
        }
    }

    @Override
    public void save(Employee employee) throws EntityExistException {
        if (storage.isEmpty()) {
            fillEmployeesFromFile();
        }
        csvManager.writeEmployee(employee);
        storage.put(employee.getId(), employee);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillEmployeesFromFile();
        }
        csvManager.deleteEmployeeById(id);
        storage.remove(id);
    }

    @Override
    public Employee findById(Long id) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillEmployeesFromFile();
        }
        return storage.get(id);
    }

    @Override
    public List<Employee> findAll() {
        if (storage.isEmpty()) {
            fillEmployeesFromFile();
        }
        return storage.values().stream().toList();
    }

    @Override
    public void update(Long id, Employee employee) throws EntityNotExistException {
        if (storage.isEmpty()) {
            fillEmployeesFromFile();
        }
        csvManager.updateEmployee(id, employee);
        storage.replace(id, employee);
    }
}
