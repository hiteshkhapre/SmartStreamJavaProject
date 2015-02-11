/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Order;
import beans.Person;
import java.util.List;

/**
 * Interface to store the details of person and orders
 *
 */
public interface StoreTableDataDao {
    
    /**
     * Store person details in DB
     * @param personList 
     * @param orderList 
     */
    public void storePersonAndOrderData(List<Person> personList, List<Order> orderList);   
}
