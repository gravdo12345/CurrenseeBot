package org.august.dtomodel;


import org.august.bankinfo.BankInterface;

public class NBUModel implements BankInterface {
    private Integer r030;
    private Float rate;
    private String cc;
    private String exchangedate;

    public Integer getR030() {
        return r030;
    }

    public void setR030(Integer r030) {
        this.r030 = r030;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExchangedate() {
        return exchangedate;
    }

    public void setExchangedate(String exchangedate) {
        this.exchangedate = exchangedate;
    }

    @Override
    public float getBuy() {
        return rate;
    }

    @Override
    public float getSell() {
        return rate;
    }
}
