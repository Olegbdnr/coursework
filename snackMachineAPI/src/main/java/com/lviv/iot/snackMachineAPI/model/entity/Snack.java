package com.lviv.iot.snackMachineAPI.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Snack {
    private Long id;
    private Long snackMachineId;
    private String manufacturer;
    private SnackType snackType;
    private Integer amount;

    public Snack(Long id, Long snackMachineId, String manufacturer, String snackType, Integer amount) {
        this.id = id;
        this.snackMachineId = snackMachineId;
        this.manufacturer = manufacturer;
        this.snackType = SnackType.valueOf(snackType);
        this.amount = amount;
    }

    public String headers() {
        return "id,snackMachineId,manufacturer,snackType,amount";
    }

    public String toCSV() {
        return this.id + "," + this.snackMachineId + "," + this.manufacturer +
                "," + this.snackType + "," + this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Snack)) return false;

        Snack snack = (Snack) o;

        if (!Objects.equals(id, snack.id)) return false;
        if (!Objects.equals(snackMachineId, snack.snackMachineId))
            return false;
        if (!Objects.equals(manufacturer, snack.manufacturer)) return false;
        if (snackType != snack.snackType) return false;
        return Objects.equals(amount, snack.amount);
    }

}
