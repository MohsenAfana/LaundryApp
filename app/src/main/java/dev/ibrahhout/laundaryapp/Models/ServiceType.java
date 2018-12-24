package dev.ibrahhout.laundaryapp.Models;

import java.io.Serializable;

/**
 * This file is created By: ( Ibrahim A. Elhout ) on 07/17/2018 at 9:53 PM
 * Project : LaundaryApp1
 * Contacts:
 * Email: Ibrahimhout.dev@gmail.com
 * Phone: 00972598825662
 **/
public class ServiceType implements Serializable {
    private int serviceType;
    private int procedureType;
    private int count;

    public ServiceType() {
    }

    public ServiceType(int serviceType, int procedureType, int count) {
        this.serviceType = serviceType;
        this.procedureType = procedureType;
        this.count = count;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(int procedureType) {
        this.procedureType = procedureType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "serviceType=" + serviceType +
                ", procedureType=" + procedureType +
                ", count=" + count +
                '}';
    }
}
