/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor;

import beans.Order;
import beans.Person;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import manager.GetDBDetailsManager;
import manager.InputDBManager;

/**
 *
 * Processor class to process the input files 
 */
public class FileProcessor {
    InputDBManager inputDBManager = null;
    GetDBDetailsManager getDBDetailsManager = null;
    
   /**
    * Method to read the "Person.data" file
    * @param filePath
    * @return
    * @throws Exception 
    */
    public List<Person> readPersonData(String filePath) throws Exception
    {
        Scanner scanPerson;
        List<Person> personList = new ArrayList<Person>();
        Person person = null;
        
        try
        {
        //Read the Person.data file 
        scanPerson = new Scanner(new File(filePath));
        int i = 0;
        int j = 0;

        //Looping the contents of the file and splitting them by 'Comma' so that it can be stored in the 
        // java objects and then in turn to the arraylist.
        
        while (scanPerson.hasNextLine()) {
            if(i==0)
            {
                   //Assuming that the first line would always contain the names of columns
                   scanPerson.nextLine();
                   i++;
            }else
            {
                   //Assuming the data starts from the second line
                   String[] column = scanPerson.nextLine().split(",");
                   if(!column[0].isEmpty()) {
                        person = new Person();
                        person.setPersonId(column[0]);
                        person.setFirstName(column[1]);
                        person.setLastName(column[2]);
                        person.setStreet(column[3]);
                        person.setCity(column[4]);
                        personList.add(j,person);
                        j++;
                        i++;
                    }
                   else {   
                       //Considering the empty line in data file.
                       person = new Person();
                       person.setCity("");
                       person.setFirstName("");
                       person.setStreet("");
                       person.setLastName("");
                       person.setPersonId("");
                       personList.add(j,person);
                       j++;
                       i++;
                    }
            }
        }
           
        }catch(IOException e)
        {
             throw new Exception(e.getMessage(),e.getCause());
        }catch(Exception e)
        {
             throw new Exception(e.getMessage(),e.getCause());
        }
            return personList;
    }    

    
    /**
     *  Method to read the "Order.data" file 
     * @param filePath
     * @return
     * @throws Exception 
     */
    public List<Order> readOrderData(String filePath) throws Exception {
        

        Scanner scanOrder;
        List<Order> orderList = new ArrayList<Order>();
        Order order = null;
        
        try
        {
            scanOrder = new Scanner(new File(filePath));
            int i = 0;
            int j = 0;
            
             //Looping the contents of the file and splitting them using indexOf method and "|" so that it can be stored in the 
             // java objects and then in turn to the arraylist.
            while (scanOrder.hasNextLine()) {
            if(i==0)
            {
                 //Assuming that the first line would always contain the names of columns
                scanOrder.nextLine();
                i++;
            }
            else {
                  //Assuming the data starts from the second line
                String line = scanOrder.nextLine();
                order = new Order();
                int firstIndex = line.indexOf("|"); 

                order.setOrderId(Integer.valueOf(line.substring(0, firstIndex)));
                order.setOrderNumber(Integer.valueOf(line.substring(firstIndex+1, line.lastIndexOf("|"))));
                order.setPersonId(line.substring(line.lastIndexOf("|")+1,line.length()));

                orderList.add(j,order);
                j++;
                i++;
            }
        }
             
        }catch(IOException e)
        {
            throw new Exception(e.getMessage(),e.getCause());
        }catch(Exception e)
        {
            throw new Exception(e.getMessage(),e.getCause());
        }
            return orderList;
    }

    /**
     * Populate the Person and Order data in DB tables
     * @param personList
     * @param orderList
     * @throws Exception 
     */
    public void populatePersonAndOrderData(List<Person> personList, List<Order> orderList) throws Exception {
        
        try
        {
             //Calling the method in manager class to store the details read from Person.data and order.data files.
             inputDBManager = new InputDBManager();
             inputDBManager.storePersonAndOrderData(personList,orderList);
             
        }catch(Exception e)
        {
            throw new Exception(e.getMessage(),e.getCause());
        }
    }
    
   /**
    * Method to get the person and order details from DB 
    * @throws Exception 
    */
    public void getPersonAndOrderDetails() throws Exception {
       
        getDBDetailsManager = new GetDBDetailsManager();
       // List<Person> personList = new ArrayList<>();
        //Method to get the list of persons with at least one order 
        List<Person> personList = getDBDetailsManager.getPersonWithOneOrder();
        
        //Printing the details of persons with at least one order
        System.out.println("Persons with at least one order are - ");
        System.out.println("Person_Id \t First_Name \t Last_Name \t Street \t City\n");
        
        for(Person person : personList)
        {
            System.out.println(person.getPersonId()+"\t\t"+person.getFirstName()+"\t\t"+ person.getLastName()+"\t\t"+person.getStreet()+"\t"+person.getCity()+"\n");
        }
        
        System.out.println("All Orders with First Name of the corresponding persons are - ");
        System.out.println("First_Name \tOrder_Id \tOrder_No\n");
        
        getDBDetailsManager = new GetDBDetailsManager();
        //Get the order details of the corresponding persons 
        Map<String, ArrayList<Order>> personOrderMap = getDBDetailsManager.getOrderDetailsForPerson(personList);
        
        for(Person person : personList)
        {
            for(Order order: personOrderMap.get(person.getFirstName()))
            {
                 //Printing the order details.
            System.out.println(person.getFirstName()+"\t\t"+order.getOrderId()+"\t\t"+ order.getOrderNumber()+"\n");
            }
           
        }           
    }

    
}
