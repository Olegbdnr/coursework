package com.lviv.iot.snackMachineAPI.model.dao.exceptions;

public class EntityNotExistException extends Exception {
    public EntityNotExistException (String errorMassage) {
        super(errorMassage);
    }
}
