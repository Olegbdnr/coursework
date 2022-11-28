package com.lviv.iot.snackMachineAPI.model.dao.Impl;

import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Employee;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import com.lviv.iot.snackMachineAPI.model.entity.SnackType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EntityCSV {
    private final SimpleDateFormat dateFormat;
    private final String snackMachinePath = "C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
            + "coursework\\snackMachineAPI\\src\\main\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\snackMachine.csv";
    private final String snackPath = "C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
            + "coursework\\snackMachineAPI\\src\\main\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\snack.csv";
    private final String employeePath = "C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
            + "coursework\\snackMachineAPI\\src\\main\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\employee.csv";
    private final File snackMachineFile;
    private final File snackFile;
    private final File employeeFile;
    private final String snackMachineHeaders = new SnackMachine().headers();
    private final String snackHeaders = new Snack().headers();
    private final String employeeHeaders = new Employee().headers();


    public EntityCSV() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.snackMachineFile = new File(this.snackMachinePath);
        this.snackFile = new File(this.snackPath);
        this.employeeFile = new File(this.employeePath);
    }

    public EntityCSV(File snackMachineFile, File snackFile, File employeeFile) {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.snackMachineFile = snackMachineFile;
        this.snackFile = snackFile;
        this.employeeFile = employeeFile;
    }

    private List<SnackMachine> getAllSnackMachinesFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(snackMachineFile));
            reader.readLine();
            String line = null;
            List<SnackMachine> snackMachineList = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Long snackMachineId = Long.valueOf(values[0]);
                String streetLocation = values[1];
                Float currentCash = Float.valueOf(values[2]);
                List<Snack> currentSnackList = getSnacksWithMachineId(Long.valueOf(values[0]));
                Date cashLoadingDate = dateFormat.parse(values[3]);
                Date cashCollectingDate = dateFormat.parse(values[4]);
                Date snackLoadingDate = dateFormat.parse(values[5]);
                Long lastCashLoaderId = Long.valueOf(values[6]);
                Long lastCashCollectorId = Long.valueOf(values[7]);
                Long lastSnackLoaderId = Long.valueOf(values[8]);
                snackMachineList.add(new SnackMachine(snackMachineId, streetLocation,
                        currentSnackList, currentCash,
                        cashLoadingDate, cashCollectingDate, snackLoadingDate,
                        lastCashLoaderId, lastCashCollectorId, lastSnackLoaderId));
            }
            reader.close();
            for (SnackMachine machine : snackMachineList) {
                System.out.println(machine.toCSV());
            }
            return snackMachineList;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private List<Snack> getAllSnacksFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(snackFile));
            reader.readLine();
            String line;
            List<Snack> snackList = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Long snackId = Long.valueOf(values[0]);
                Long snackMachineId = Long.valueOf(values[1]);
                String manufacturer = values[2];
                SnackType snackType = SnackType.valueOf(values[3]);
                Integer amount = Integer.valueOf(values[4]);
                snackList.add(new Snack(snackId, snackMachineId, manufacturer,
                        snackType, amount));
            }
            reader.close();
            return snackList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private List<Employee> getAllEmployeesFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(employeeFile));
            reader.readLine();
            String line;
            List<Employee> employeeList = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Long employeeId = Long.valueOf(values[0]);
                String name = values[1];
                String surname = values[2];
                employeeList.add(new Employee(employeeId, name, surname));
            }
            reader.close();
            return employeeList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private void rewriteSnackMachines(List<SnackMachine> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(snackMachineFile, false));
        writer.write(snackMachineHeaders);
        list.forEach(machine -> {
            try {
                writer.write("\n" + machine.toCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private void rewriteSnacks(List<Snack> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(snackFile, false));
        writer.write(snackHeaders);
        list.forEach(snack -> {
            try {
                writer.write("\n" + snack.toCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private void rewriteEmployees(List<Employee> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(employeeFile, false));
        writer.write(employeeHeaders);
        list.forEach(employee -> {
            try {
                writer.write("\n" + employee.toCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private SnackMachine getSnackMachineByIdFromFile(Long id) throws EntityNotExistException {
        List<SnackMachine> allMachines = getAllSnackMachinesFromFile();
        Optional<SnackMachine> result = allMachines.stream().filter(machine ->
                machine.getId().compareTo(id) == 0).findAny();
        if (result.isEmpty()) {
            throw new EntityNotExistException("SnackMachine with id:" + id + " not exist");
        }
        return result.get();
    }

    private List<Snack> getSnacksWithMachineId(Long id) {
        List<Snack> allSnacks = getAllSnacks();
        List<Snack> snacksWithMachineId = new LinkedList<>();
        allSnacks.forEach(snack -> {
            if (snack.getSnackMachineId().compareTo(id) == 0) {
                snacksWithMachineId.add(snack);
            }
        });
        return snacksWithMachineId;
    }

    private boolean isMachineExist(SnackMachine machine) {
        List<SnackMachine> currentMachines = getAllSnackMachinesFromFile();
        System.out.println(machine.getId());
        List<Long> idList = new LinkedList<>();
        currentMachines.forEach(snackMachine -> idList.add(snackMachine.getId()));
        System.out.println(idList.contains(machine.getId()));
        return idList.contains(machine.getId());
    }

    private boolean isSnackExist(Snack snack) {
        List<Snack> currentSnacks = getAllSnacksFromFile();
        List<Long> idList = new LinkedList<>();
        currentSnacks.forEach(currentSnack -> idList.add(currentSnack.getId()));
        return idList.contains(snack.getId());
    }

    private boolean isEmployeeExist(Employee employee) {
        List<Employee> currentEmployees = getAllEmployeesFromFile();
        List<Long> idList = new LinkedList<>();
        currentEmployees.forEach(currentEmployee -> idList.add(currentEmployee.getId()));
        return idList.contains(employee.getId());
    }

    private boolean isEmployeeExist(Long id) {
        List<Employee> currentEmployees = getAllEmployeesFromFile();
        List<Long> idList = new LinkedList<>();
        currentEmployees.forEach(currentEmployee -> idList.add(currentEmployee.getId()));
        return idList.contains(id);
    }

    // SnackMachine methods //
    public void writeSnackMachine(SnackMachine machine) throws EntityExistException, EntityNotExistException {
        if (isMachineExist(machine)) {
            throw new EntityExistException("Snack Machine with id:" + machine.getId() + " already exist");
        }
        if (!isEmployeeExist(machine.getLastCashCollectorId())) {
            throw new EntityNotExistException("Employee with id:" + machine.getLastCashCollectorId() + " not exist");
        }
        if (!isEmployeeExist(machine.getLastCashLoaderId())) {
            throw new EntityNotExistException("Employee with id:" + machine.getLastCashLoaderId() + " not exist");
        }
        if (!isEmployeeExist(machine.getLastSnackLoaderId())) {
            throw new EntityNotExistException("Employee with id:" + machine.getLastSnackLoaderId() + " not exist");
        }
        try {
            if (snackMachineFile.exists() && snackMachineFile.length() > 0) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(snackMachineFile, true));
                writer.write("\n" + machine.toCSV());
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(snackMachineFile, true));
                writer.write(machine.headers());
                writer.write("\n" + machine.toCSV());
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SnackMachine> getAllMachines() {
        return getAllSnackMachinesFromFile();
    }

    public SnackMachine getMachineById(Long id) throws EntityNotExistException {
        return getSnackMachineByIdFromFile(id);
    }

    public void deleteSnackMachineById(Long id) throws EntityNotExistException {
        List<SnackMachine> currentMachinesInFile = this.getAllSnackMachinesFromFile();
        Optional<SnackMachine> machineWithId = currentMachinesInFile.stream().filter(machine ->
                machine.getId().compareTo(id) == 0).findAny();
        if (machineWithId.isEmpty()) {
            throw new EntityNotExistException("SnackMachine with id:" + id + " not exist");
        }
        List<SnackMachine> updatedMachines = new LinkedList<>();
        currentMachinesInFile.forEach(machine -> {
            if (machine.getId().compareTo(id) != 0) {
                updatedMachines.add(machine);
            }
        });
        try {
            rewriteSnackMachines(updatedMachines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSnackMachine(Long id, SnackMachine machine) throws EntityNotExistException {
        List<SnackMachine> currentMachinesInFile = getAllSnackMachinesFromFile();
        Optional<SnackMachine> machineWithId = currentMachinesInFile.stream().filter(snackMachine ->
                snackMachine.getId().compareTo(id) == 0).findAny();
        if (machineWithId.isEmpty()) {
            throw new EntityNotExistException("SnackMachine with id:" + id + " not exist");
        }
        List<SnackMachine> updated = new LinkedList<>();
        currentMachinesInFile.forEach(entity -> {
            if (entity.getId().compareTo(id) == 0) {
                updated.add(machine);
                return;
            }
            updated.add(entity);
        });
        try {
            rewriteSnackMachines(updated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Snack methods //
    public void writeSnack(Snack snack) throws EntityExistException, EntityNotExistException {
        if (isSnackExist(snack)) {
            throw new EntityExistException("Snack with id:" + snack.getId() + " already exist");
        }
        if (!isMachineExist(getSnackMachineByIdFromFile(snack.getSnackMachineId()))) {
            throw new EntityNotExistException("Snack Machine with id:" + snack.getSnackMachineId() + " not exist");
        }
        try {
            if (snackFile.exists() && snackFile.length() > 0) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(snackFile, true));
                writer.write("\n" + snack.toCSV());
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(snackFile, true));
                writer.write(snack.headers());
                writer.write("\n" + snack.toCSV());
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Snack> getAllSnacks() {
        return getAllSnacksFromFile();
    }

    public void deleteSnackById(Long id) throws EntityNotExistException {
        List<Snack> currentSnacksInFile = this.getAllSnacksFromFile();
        Optional<Snack> snackWithId = currentSnacksInFile.stream().filter(snack ->
                snack.getId().compareTo(id) == 0).findAny();
        if (snackWithId.isEmpty()) {
            throw new EntityNotExistException("Snack with id:" + id + " not exist");
        }
        List<Snack> updatedSnacks = new LinkedList<>();
        currentSnacksInFile.forEach(snack -> {
            if (snack.getId().compareTo(id) != 0) {
                updatedSnacks.add(snack);
            }
        });
        try {
            rewriteSnacks(updatedSnacks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSnack(Long id, Snack snack) throws EntityNotExistException {
        List<Snack> currentSnacksInFile = getAllSnacksFromFile();
        Optional<Snack> snackWithId = currentSnacksInFile.stream().filter(currentSnack ->
                currentSnack.getId().compareTo(id) == 0).findAny();
        if (snackWithId.isEmpty()) {
            throw new EntityNotExistException("Snack with id:" + id + " not exist");
        }
        List<Snack> updated = new LinkedList<>();
        currentSnacksInFile.forEach(entity -> {
            if (entity.getId().compareTo(id) == 0) {
                updated.add(snack);
                return;
            }
            updated.add(entity);
        });
        try {
            rewriteSnacks(updated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Employee methods //
    public void writeEmployee(Employee employee) throws EntityExistException {
        if (isEmployeeExist(employee)) {
            throw new EntityExistException("Employee with id:" + employee.getId() + " already exist");
        }
        try {
            if (employeeFile.exists() && employeeFile.length() > 0) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(employeeFile, true));
                writer.write("\n" + employee.toCSV());
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(employeeFile, true));
                writer.write(employee.headers());
                writer.write("\n" + employee.toCSV());
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployees() {
        return getAllEmployeesFromFile();
    }

    public void deleteEmployeeById(Long id) throws EntityNotExistException {
        List<Employee> currentEmployeesInFile = getAllEmployeesFromFile();
        Optional<Employee> employeeWithId = currentEmployeesInFile.stream().filter(employee ->
                employee.getId().compareTo(id) == 0).findAny();
        if (employeeWithId.isEmpty()) {
            throw new EntityNotExistException("Employee with id:" + id + " not exist");
        }
        List<Employee> updatedEmployees = new LinkedList<>();
        currentEmployeesInFile.forEach(employee -> {
            if (employee.getId().compareTo(id) != 0) {
                updatedEmployees.add(employee);
            }
        });
        try {
            rewriteEmployees(updatedEmployees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Long id, Employee employee) throws EntityNotExistException {
        List<Employee> currentEmployeesInFile = getAllEmployeesFromFile();
        Optional<Employee> employeeWithId = currentEmployeesInFile.stream().filter(currentEmployee ->
                currentEmployee.getId().compareTo(id) == 0).findAny();
        if (employeeWithId.isEmpty()) {
            throw new EntityNotExistException("Snack with id:" + id + " not exist");
        }
        List<Employee> updated = new LinkedList<>();
        currentEmployeesInFile.forEach(entity -> {
            if (entity.getId().compareTo(id) == 0) {
                updated.add(employee);
                return;
            }
            updated.add(entity);
        });
        try {
            rewriteEmployees(updated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
