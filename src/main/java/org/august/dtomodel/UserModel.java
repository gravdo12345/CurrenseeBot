package org.august.dtomodel;

import org.august.bankinfo.BankInterface;

import java.util.List;

public class UserModel {
    List<String> bank;
    List<String> currency;
    int notificationTime;
    int sumbols;

    public List<String> getBank() {
        return bank;
    }

    public void setBank(List<String> bank) {
        this.bank = bank;
    }

    public List<String> getCurrency() {
        return currency;
    }

    public void setCurrency(List<String> currency) {
        this.currency = currency;
    }

    public int getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(int notificationTime) {
        this.notificationTime = notificationTime;
    }

    public int getSumbols() {
        return sumbols;
    }

    public void setSumbols(int sumbols) {
        this.sumbols = sumbols;
    }
}
