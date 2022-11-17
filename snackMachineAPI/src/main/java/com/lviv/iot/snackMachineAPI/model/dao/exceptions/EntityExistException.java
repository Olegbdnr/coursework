package com.lviv.iot.snackMachineAPI.model.dao.exceptions;

public class EntityExistException extends Exception {
    public EntityExistException(String errorMassage) {
        super(errorMassage);
    }
}
