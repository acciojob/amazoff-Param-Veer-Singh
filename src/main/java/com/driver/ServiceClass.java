package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ServiceClass {

    @Autowired
    RepositoryClass repositoryClass;

    public void addOrder(Order order){

        repositoryClass.addOrder(order);
    }

    public void addPartner(String partnerId){

        repositoryClass.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId){

        repositoryClass.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId){

        Order order = repositoryClass.getOrderById(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId){

        DeliveryPartner deliveryPartner = repositoryClass.getPartnerById(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId){

        Integer countOfOrders = repositoryClass.getOrderCountByPartnerId(partnerId);
        return countOfOrders;
    }

    public List<String> getOrdersByPartnerId(String partnerId){

        List<String> orderList = repositoryClass.getOrdersByPartnerId(partnerId);
        return orderList;
    }

    public List<String> getAllOrders(){

        List<String> allOrders = repositoryClass.getAllOrders();
        return allOrders;
    }

    public Integer getCountOfUnassignedOrders(){

        Integer count =  repositoryClass.getCountOfUnassignedOrders();
        return count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){

        return repositoryClass.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time = repositoryClass.getLastDeliveryTimeByPartnerId(partnerId);
        return time;
    }

    public void deletePartnerById(String partnerId) {

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        repositoryClass.deletePartnerById(partnerId);

    }

    public void deleteOrderById(String orderId) {

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        repositoryClass.deleteOrderById(orderId);
    }
}
