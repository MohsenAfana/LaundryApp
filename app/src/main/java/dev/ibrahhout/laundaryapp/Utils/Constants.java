package dev.ibrahhout.laundaryapp.Utils;

/**
 * This file is created By: ( Ibrahim A. Elhout ) on 07/06/2018 at 5:51 PM
 * Project : LaundaryApp
 * Contacts:
 * Email: Ibrahimhout.dev@gmail.com
 * Phone: 00972598825662
 **/
public interface Constants {
    String FEEDBACK_NODE = "FeedbackNode";
    String FIREBASE_USERS_NODE = "users";
    String FIREBASE_USERS_USERNAME= "name";
    int SERVICE_TYPE_NORMAL_CLOTHES = 1;
    int SERVICE_TYPE_DUVETS = 2;
    int SERVICE_TYPE_CARPETS= 3;
    int SERVICE_TYPE_CURTAINS= 4;

    int SERVICE_PROCEDURE_NONE= 3;
    int SERVICE_PROCEDURE_TYPE_CLEANING= 0;
    int SERVICE_PROCEDURE_TYPE_CLEANING_AND_IRONING= 1;


    int NORMAL_CLOTHES_PRICE = 5;
    int DUVETS_PRICE = 10;
    int CARPETS_PRICE = 50;
    int CURTAINS_PRICE = 15;
    String FIREBASE_USERS_ADDRESS = "Address";


    String ORDER_NODE_ORDER_ID = "orderID";
    String ORDER_NODE_ORDER_STATUS = "orderStatus";
    String ORDER_NODE_ORDER_DATE= "orderDate";
    String ORDER_NODE_ORDER_DUE_DATE= "orderDueDate";
    String ORDER_NODE_ORDER_NAME= "orderName";
    String ORDER_NODE_ORDER_DETAILS= "orderDetials";
    String ORDER_NODE_ORDER_TOTAL_PRICE= "orderTotalPrice";
    String ORDER_NODE_ORDER_ADDRESS= "orderAddress";

    String ORDERS_NODE = "Orders";
    String STATUS_ON_PROGRESS = "On progress";
    String STATUS_CANCELED = "Cancelled";
    String STATUS_COMPLETED = "Completed";

    String SERVICE_TYPE =  "serviceType";
    String PROCEDURE_TYPE=  "procedureType";
    String COUNT=  "count";

    String ORDER_OWNER_ID = "ownerID";
}
