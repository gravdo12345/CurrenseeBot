package org.august.dtomodel;

import org.august.bankinfo.BankInterface;

public class MonobankModel implements BankInterface {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private int date;
    private Float rateSell;
    private Float rateBuy;

    public Integer getCurrencyCodeA() {
        return currencyCodeA;
    }

    public void setCurrencyCodeA(Integer currencyCodeA) {
        this.currencyCodeA = currencyCodeA;
    }

    public Integer getCurrencyCodeB() {
        return currencyCodeB;
    }

    public void setCurrencyCodeB(Integer currencyCodeB) {
        this.currencyCodeB = currencyCodeB;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Float getRateSell() {
        return rateSell;
    }

    public void setRateSell(Float rateSell) {
        this.rateSell = rateSell;
    }

    public Float getRateBuy() {
        return rateBuy;
    }

    public void setRateBuy(Float rateBuy) {
        this.rateBuy = rateBuy;
    }

    @Override
    public float getBuy() {
        return this.rateBuy;
    }

    @Override
    public float getSell() {
        return this.rateSell;
    }
}

