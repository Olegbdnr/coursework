package com.lviv.iot.snackMachineAPI.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private String surname;

    public String headers() {
        return "id,name,surname";
    }

    public String toCSV() {
        return this.id + "," + this.name + "," + this.surname;
    }
}
