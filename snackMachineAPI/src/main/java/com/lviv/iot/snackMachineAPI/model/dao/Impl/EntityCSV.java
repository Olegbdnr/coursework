package com.lviv.iot.snackMachineAPI.model.dao.Impl;

import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EntityCSV {
    private final SimpleDateFormat dateFormat;
    //ToDo: path name should contain corresponding name: entityName-DateOfCreation
    private final String path = "C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\" +
            "coursework\\snackMachineAPI\\src\\main\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\snackMachine.csv";
    private final File snackMachineFile;
    private final String snackMachineHeaders = new SnackMachine().headers();

    public EntityCSV() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.snackMachineFile = new File(this.path);
    }

    private List<SnackMachine> getAllSnackMachines() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(snackMachineFile));
            reader.readLine();
            String line = null;
            List<SnackMachine> snackMachineList = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                snackMachineList.add(new SnackMachine(Long.valueOf(values[0]), values[1], new LinkedList<>(), Float.valueOf(values[2]),
                        dateFormat.parse(values[3]), dateFormat.parse(values[4]), dateFormat.parse(values[5]),
                        Integer.valueOf(values[6]), Integer.valueOf(values[7]), Integer.valueOf(values[8])));
            }
            reader.close();
            return snackMachineList;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    private void rewriteSnackMachine(List<SnackMachine> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(snackMachineFile, false));
        writer.write(snackMachineHeaders);
        list.forEach(machine -> {
            try {
                System.out.println(machine.toCSV());
                writer.write("\n" + machine.toCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private boolean isMachineExist(SnackMachine machine) {
        List<SnackMachine> currentMachines = getAllSnackMachines();
        List<Long> idList = new LinkedList<>();
        Boolean result;
        currentMachines.forEach(snackMachine -> {
            idList.add(machine.getId());
        });
        return idList.contains(machine.getId());
    }

    public void writeSnackMachine(SnackMachine machine) throws EntityExistException {
        if (isMachineExist(machine)) {
            throw new EntityExistException("Snack Machine with id:" + machine.getId() + " already exist");
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

    public SnackMachine getSnackMachineById(Long id) throws EntityNotExistException {
        List<SnackMachine> allMachines = getAllSnackMachines();
        Optional<SnackMachine> result = allMachines.stream().filter(machine ->
                machine.getId().compareTo(id) == 0).findAny();
        if (result.isEmpty()) {
            throw new EntityNotExistException("SnackMachine with id:" + id + " not exist");
        }
        return result.get();
    }

    public List<SnackMachine> getAll() {
        return getAllSnackMachines();
    }

    public void deleteSnackMachineById(Long id) throws EntityNotExistException {
        List<SnackMachine> currentMachinesInFile = this.getAllSnackMachines();
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
            rewriteSnackMachine(updatedMachines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSnackMachine(Long id, SnackMachine machine) throws EntityNotExistException {
        List<SnackMachine> currentMachinesInFile = getAllSnackMachines();
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
            rewriteSnackMachine(updated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
