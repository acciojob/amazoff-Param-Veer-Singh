package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RepositoryClass {

    HashMap<String,Order> orderHashMap = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
    HashMap<String,String> orderDeliveryPair = new HashMap<>();
    HashMap<String, List<String>> deliveryOrderPair = new HashMap<>();

    public void addOrder(Order order){
        if(orderHashMap.containsKey(order.getId()))return;

        String id = order.getId();
        orderHashMap.put(id,order);
    }

    public void addPartner(String partnerId){
        if (deliveryPartnerHashMap.containsKey(partnerId))return;

        deliveryPartnerHashMap.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId){

        if(orderHashMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId)){
            orderDeliveryPair.put(orderId,partnerId);
        }else return;

        List<String> orders = deliveryOrderPair.getOrDefault(partnerId,new ArrayList<>());
        orders.add(orderId);
        deliveryOrderPair.put(partnerId,orders);

        int noOfOrders = deliveryPartnerHashMap.get(partnerId).getNumberOfOrders();
        deliveryPartnerHashMap.get(orderId).setNumberOfOrders(noOfOrders+1);

    }

    public Order getOrderById(String orderId){

        Order order = orderHashMap.get(orderId);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId){

        DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(partnerId);
        return deliveryPartner;
    }

    public Integer getOrderCountByPartnerId(String partnerId){

        Integer countOfOrders = deliveryPartnerHashMap.get(partnerId).getNumberOfOrders();
        return countOfOrders;
    }

    public List<String> getOrdersByPartnerId(String partnerId){

        List<String> orderList = deliveryOrderPair.get(partnerId);
        return orderList;
    }

    public List<String> getAllOrders(){

        List<String> allOrders = new ArrayList<>();
        for (String orders : orderHashMap.keySet()){
            allOrders.add(orders);
        }

        return allOrders;
    }

    public Integer getCountOfUnassignedOrders(){

        return orderHashMap.size() - deliveryOrderPair.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){

        Integer count = 0;
        int givenTime = Integer.parseInt(time);
        for(String orderId : deliveryOrderPair.get(partnerId)){
            if(orderHashMap.get(orderId).getDeliveryTime() > givenTime){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){

        int maxTime = 0;
        for (String orderId : deliveryOrderPair.get(partnerId)){
            int time = orderHashMap.get(orderId).getDeliveryTime();
            if(time > maxTime){
                maxTime = time;
            }
        }

        String HH = String.valueOf(maxTime/60);
        String MM = String.valueOf(maxTime%60);

        if(HH.length() < 2){
            HH = '0' + HH;
        }
        if (MM.length() < 2){
            MM = '0' + MM;
        }

        return  HH + ":" + MM;
    }

    public void deletePartnerById(String partnerId) {

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        for (String orderId : deliveryOrderPair.get(partnerId)){
            orderDeliveryPair.remove(orderId);
        }
        deliveryOrderPair.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        String deliveryId = orderDeliveryPair.get(orderId);
        orderHashMap.remove(orderId);

        orderDeliveryPair.remove(orderId);
        DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(deliveryId);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders() - 1);

        deliveryOrderPair.get(deliveryId).remove(orderId);

    }
}
