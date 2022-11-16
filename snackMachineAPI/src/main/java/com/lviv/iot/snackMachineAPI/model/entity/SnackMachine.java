package com.lviv.iot.snackMachineAPI.model.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
public class SnackMachine {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private Long id;
    private String streetLocation;
    private List<Snack> currentSnackList;

    public SnackMachine(Long id, String streetLocation, List<Snack> currentSnackList, Float currentCash, Date cashLoadingDate, Date cashCollectingDate, Date snackLoadingDate, Integer lastCashLoaderId, Integer lastCashCollectorId, Integer lastSnackLoaderId) {
        this.id = id;
        this.streetLocation = streetLocation;
        this.currentSnackList = currentSnackList;
        this.currentCash = currentCash;
        this.cashLoadingDate = cashLoadingDate;
        this.cashCollectingDate = cashCollectingDate;
        this.snackLoadingDate = snackLoadingDate;
        this.lastCashLoaderId = lastCashLoaderId;
        this.lastCashCollectorId = lastCashCollectorId;
        this.lastSnackLoaderId = lastSnackLoaderId;
    }

    private Float currentCash;
    private Date cashLoadingDate;
    private Date cashCollectingDate;
    private Date snackLoadingDate;
    private Integer lastCashLoaderId;
    private Integer lastCashCollectorId;
    private Integer lastSnackLoaderId;

    public String headers() {
        return "id,streetLocation,currentCash,cashLoadingDate,cashCollectingDate,snackLoadingDate,lastCashLoaderId," +
                "lastCashCollectorId,lastSnackLoaderId";
    }

    public String toCSV () {
        return this.id + "," + this.streetLocation + "," + this.currentCash + "," + dateFormat.format(cashLoadingDate)
                + "," + dateFormat.format(cashCollectingDate) + "," + dateFormat.format(snackLoadingDate) + "," +
                this.lastCashLoaderId + "," + this.lastCashCollectorId + "," + this.lastSnackLoaderId;
    }

    public String getCashLoadingDate() {
        return dateFormat.format(cashLoadingDate);
    }

    public String getCashCollectingDate() {
        return dateFormat.format(cashCollectingDate);
    }

    public String getSnackLoadingDate() {
        return dateFormat.format(snackLoadingDate);
    }

    public void setCashLoadingDate(String cashLoadingDate) throws ParseException {
        this.cashLoadingDate = dateFormat.parse(cashLoadingDate);
    }

    public void setCashCollectingDate(String cashCollectingDate) throws ParseException {
        this.cashCollectingDate = dateFormat.parse(cashCollectingDate);
    }

    public void setSnackLoadingDate(String snackLoadingDate) throws ParseException {
        this.snackLoadingDate = dateFormat.parse(snackLoadingDate);
    }


}
