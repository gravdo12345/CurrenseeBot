package org.august.dtomodel;


import org.august.bankinfo.BankInterface;

import java.util.Date;

public class PrivatBankModel implements BankInterface {
    private String ccy;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
    }

    public float getBuy() {
        return this.buy;
    }

    @Override
    public float getSell() {
        return this.sale;
    }

    public void setBuy(Float buy) {
        this.buy = buy;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String base_ccy;
    private Float buy;
    private Float sale;
    private Date date;
}
