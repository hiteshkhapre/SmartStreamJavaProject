/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import beans.Order;
import beans.Person;
import dao.StoreTableDataDao;
import daoImpl.StoreTableDataDaoImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 *  Manager class to manage all the interactions with DB
 * 
 */
public class InputDBManager {

    private Connection conn;
   
    //Public Constructor
    public InputDBManager() throws Exception {
       try{
           Properties prop = new Properties();
           InputStream input = new FileInputStream("src\\properties\\dbProperties.properties");
            prop.load(input);
       //get the driver for JDBC
       Class.forName(prop.getProperty("driverName"));
       //connect to the database
       conn = DriverManager.getConnection(prop.getProperty("URL"),prop.getProperty("username"),prop.getProperty("password"));
       conn.setAutoCommit(false);
       }
       catch(SQLException | ClassNotFoundException | IOException e){
        throw new Exception(e.getMessage(),e.getCause());
       } 
    }
    
    /**
     * Method to store the Person details in DB
     * @param personList 
     * @param orderList 
     */
    public void storePersonAndOrderData(List<Person> personList,List<Order> orderList)
    {
        StoreTableDataDao storePersonTableDataDao = new StoreTableDataDaoImpl(conn);
        storePersonTableDataDao.storePersonAndOrderData(personList, orderList);
        
    }
}
