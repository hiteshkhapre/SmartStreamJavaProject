/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Order;
import beans.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *Interface to get the person and order details
 * 
 */
public interface GetDBDetailsDao {
    
    /**
     * Get the person details having at least one order
     * @return 
     */
    public List<Person> getPersonWithOneOrder();
    
    /**
     * Get the order details for all the persons with at least one order
     * @param personList
     * @return 
     */
    public Map<String, ArrayList<Order>> getOrderDetails(List<Person> personList);
}
