package com.lviv.iot.snackMachineAPI.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Snack {
    private Long id;
    private Long snackMachineId;
    private String manufacturer;
    private SnackType snackType;
    private Integer amount;
}
