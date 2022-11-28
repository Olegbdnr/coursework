package com.lviv.iot.snackMachineAPI.model.dao.impl;

import com.lviv.iot.snackMachineAPI.model.dao.Impl.EntityCSV;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityExistException;
import com.lviv.iot.snackMachineAPI.model.dao.exceptions.EntityNotExistException;
import com.lviv.iot.snackMachineAPI.model.entity.Snack;
import com.lviv.iot.snackMachineAPI.model.entity.SnackMachine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EntityCSVTests {
    EntityCSV entityCSV;
    File snackMachineTest;
    File snackTest;
    File employeeTest;
    SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() throws Exception {
        snackMachineTest = new File("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\snackMachineTest.csv");
        snackTest = new File("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\snackTest.csv");
        employeeTest = new File("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\employeeTest.csv");
        snackMachineTest.createNewFile();
        snackTest.createNewFile();
        employeeTest.createNewFile();
        entityCSV = new EntityCSV(snackMachineTest, snackTest, employeeTest);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    public void entityFilesExistsTest() throws IOException {
        Assertions.assertTrue(snackMachineTest.exists());
        Assertions.assertTrue(snackTest.exists());
        Assertions.assertTrue(employeeTest.exists());
    }

    @Test
    public void writeMachineToFileTest() throws IOException, ParseException, EntityExistException, EntityNotExistException {
        BufferedReader expectedReader = new BufferedReader(new FileReader("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\expectedSnackMachine.csv"));
        String line;
        expectedReader.readLine();
        String expected = null;
        while ((line = expectedReader.readLine()) != null) {
            expected = line;
        }
        expectedReader.close();
        Long snackMachineId = 1L;
        String streetLocation = "Zubrivska";
        Float currentCash = 1305.0F;
        List<Snack> currentSnackList = new LinkedList<>();
        Date cashLoadingDate = dateFormat.parse("13/04/2004");
        Date cashCollectingDate = dateFormat.parse("13/04/2004");
        Date snackLoadingDate = dateFormat.parse("13/04/2004");
        Long lastCashLoaderId = 1L;
        Long lastCashCollectorId = 1L;
        Long lastSnackLoaderId = 1L;
        snackMachineTest.delete();
        entityCSV.writeSnackMachine(new SnackMachine(snackMachineId, streetLocation,
                currentSnackList, currentCash,
                cashLoadingDate, cashCollectingDate, snackLoadingDate,
                lastCashLoaderId, lastCashCollectorId, lastSnackLoaderId));
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\snackMachineTest.csv"));
        String line1;
        reader.readLine();
        String exact = null;
        while ((line1 = reader.readLine()) != null) {
            exact = line1;
        }
        reader.close();
        Assertions.assertEquals(expected, exact);
    }

    @Test
    public void getMachineByIdTest() throws IOException, EntityNotExistException, EntityExistException, ParseException {
        BufferedReader expectedReader = new BufferedReader(new FileReader("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\expectedSnackMachine.csv"));
        String line;
        expectedReader.readLine();
        String expected = null;
        while ((line = expectedReader.readLine()) != null) {
            expected = line;
        }
        expectedReader.close();
        Long snackMachineId = 1L;
        String streetLocation = "Zubrivska";
        Float currentCash = 1305.0F;
        List<Snack> currentSnackList = new LinkedList<>();
        Date cashLoadingDate = dateFormat.parse("13/04/2004");
        Date cashCollectingDate = dateFormat.parse("13/04/2004");
        Date snackLoadingDate = dateFormat.parse("13/04/2004");
        Long lastCashLoaderId = 1L;
        Long lastCashCollectorId = 1L;
        Long lastSnackLoaderId = 1L;
        snackMachineTest.delete();
        entityCSV.writeSnackMachine(new SnackMachine(snackMachineId, streetLocation,
                currentSnackList, currentCash,
                cashLoadingDate, cashCollectingDate, snackLoadingDate,
                lastCashLoaderId, lastCashCollectorId, lastSnackLoaderId));
        String exact = entityCSV.getMachineById(1L).toCSV();
        Assertions.assertEquals(expected, exact);
    }

    @Test
    public void updateSnackMachineTest() throws EntityNotExistException, IOException, ParseException {
        BufferedReader expectedReader = new BufferedReader(new FileReader("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\expectedUpdatedSnackMachine.csv"));
        String line;
        expectedReader.readLine();
        String expected = null;
        while ((line = expectedReader.readLine()) != null) {
            expected = line;
        }
        expectedReader.close();
        Long snackMachineId = 1L;
        String streetLocation = "Ternopilska";
        Float currentCash = 1305.0F;
        List<Snack> currentSnackList = new LinkedList<>();
        Date cashLoadingDate = dateFormat.parse("13/04/2004");
        Date cashCollectingDate = dateFormat.parse("13/04/2004");
        Date snackLoadingDate = dateFormat.parse("13/04/2004");
        Long lastCashLoaderId = 1L;
        Long lastCashCollectorId = 1L;
        Long lastSnackLoaderId = 1L;
        entityCSV.updateSnackMachine(snackMachineId, new SnackMachine(snackMachineId, streetLocation,
                currentSnackList, currentCash,
                cashLoadingDate, cashCollectingDate, snackLoadingDate,
                lastCashLoaderId, lastCashCollectorId, lastSnackLoaderId));
        String exact = entityCSV.getMachineById(snackMachineId).toCSV();
        Assertions.assertEquals(expected, exact);
    }

    @Test
    public void deleteSnackMachineTest() throws IOException {
        try {
            entityCSV.deleteSnackById(1L);
        } catch (EntityNotExistException e) {
            e.printStackTrace();
        }
        BufferedReader expectedReader = new BufferedReader(new FileReader("C:\\Users\\38093\\OneDrive\\Рабочий стол\\Repos\\"
                + "coursework\\snackMachineAPI\\src\\test\\java\\com\\lviv\\iot\\snackMachineAPI\\data\\expectedEmptySnackMachine.csv"));
        String line;
        expectedReader.readLine();
        String expected = null;
        while ((line = expectedReader.readLine()) != null) {
            expected = line;
        }
        expectedReader.close();
        String exact;
        try {
            entityCSV.getMachineById(1L);
        } catch (EntityNotExistException e) {
            exact = null;
            Assertions.assertEquals(expected, exact);
        }
    }
}
