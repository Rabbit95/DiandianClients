package com.example.administrator.diandianclient.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/6.
 */

public class Order implements Serializable {
    private String name;
    private String number;
    private String OContent;
    private String ONumber;
    private String OTPrice;
    private String OStatus;
    private String OSNumber;

    public Order(){

    }
    public Order(String oNumber,String oContent,String oTPrice,String oStatus,String oSNumber){
        this.ONumber = oNumber;
        this.OContent = oContent;
        this.OTPrice = oTPrice;
        this.OStatus = oStatus;
        this.OSNumber = oSNumber;
    }

    public String getOSNumber() {
        return OSNumber;
    }

    public void setOSNumber(String OSNumber) {
        this.OSNumber = OSNumber;
    }

    public String getOContent() {
        return OContent;
    }

    public void setOContent(String OContent) {
        this.OContent = OContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOTPrice() {
        return OTPrice;
    }

    public void setOTPrice(String OTPrice) {
        this.OTPrice = OTPrice;
    }

    public String getOStatus() {
        return OStatus;
    }

    public void setOStatus(String OStatus) {
        this.OStatus = OStatus;
    }

    public String getONumber() {
        return ONumber;
    }

    public void setONumber(String ONumber) {
        this.ONumber = ONumber;
    }
}
