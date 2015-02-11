/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import beans.Order;
import beans.Person;
import dao.GetDBDetailsDao;
import daoImpl.GetDBDetailsDaoImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * 
 */
public class GetDBDetailsManager {
    

    private Connection conn;
   
    //Public Constructor
    public GetDBDetailsManager() throws Exception {
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
        //return conn;
    }
    
    /**
     * Method to get persons details with at least one order
     * @return 
     * @throws java.sql.SQLException 
     * @throws java.lang.Exception 
     */
    public List<Person> getPersonWithOneOrder() throws SQLException, Exception {
        GetDBDetailsDao getDBDetailsDao = new GetDBDetailsDaoImpl(conn);
        return getDBDetailsDao.getPersonWithOneOrder();
        
    }

    /**
     * Method to get the order details for all person with at least one order
     * @param personList
     * @return 
     * @throws java.sql.SQLException 
     */
    public Map<String, ArrayList<Order>> getOrderDetailsForPerson(List<Person> personList) throws SQLException, Exception {
        GetDBDetailsDao getDBDetailsDao = new GetDBDetailsDaoImpl(conn);
        return getDBDetailsDao.getOrderDetails(personList);
    }
}
