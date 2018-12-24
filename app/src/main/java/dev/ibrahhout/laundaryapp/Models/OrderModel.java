package dev.ibrahhout.laundaryapp.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This file is created By: ( Ibrahim A. Elhout ) on 07/21/2018 at 5:49 PM
 * Project : LaundaryApp1
 * Contacts:
 * Email: Ibrahimhout.dev@gmail.com
 * Phone: 00972598825662
 **/
public class OrderModel implements Serializable {

    private String orderAddress;
    private String orderDate;
    private ArrayList<HashMap<String,Long>> ordDetials;
    private String orderDueDate;
    private String orderID;
    private String orderName;
    private String orderStatus;
    private String orderTotalPrice;

    public OrderModel() {
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    public ArrayList<HashMap<String, Long>> getOrdDetials() {
        return ordDetials;
    }

    public void setOrdDetials(ArrayList<HashMap<String, Long>> ordDetials) {
        this.ordDetials = ordDetials;
    }

    public String getOrderDueDate() {
        return orderDueDate;
    }

    public void setOrderDueDate(String orderDueDate) {
        this.orderDueDate = orderDueDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

}
