/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import beans.Order;
import beans.Person;
import dao.StoreTableDataDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Implementation class for storing the person and order details into DB 
 */
public class StoreTableDataDaoImpl implements StoreTableDataDao {

    Connection conn = null;
    //Constructor
    public StoreTableDataDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Stores Person details in DB table
     * @param personList 
     * @param orderList 
     */
    @Override
    public void storePersonAndOrderData(List<Person> personList,List<Order> orderList) {
        
         PreparedStatement stat = null;
         
         try
         {
             //Store Person data 
             for (Person personList1 : personList) {
                 //Prepared statment to insert data into DB
                 stat = conn.prepareStatement("insert into `smartjava`.person (PERSON_ID, LAST_NAME, FIRST_NAME, STREET, CITY)"
                         + "values"
                         + "(?,?,?,?,?)");
                 stat.setString(1, personList1.getPersonId());
                 stat.setString(2, personList1.getLastName());
                 stat.setString(3, personList1.getFirstName());
                 stat.setString(4, personList1.getStreet());
                 stat.setString(5, personList1.getCity());
                 //Execute the pstatement
                 stat.execute();
             }
             
             //Store Order data
              for (Order orderList1 : orderList) {
               //Prepared pstatement to insert the order data into db
               stat = conn.prepareStatement("insert into `smartjava`.order (ORDER_ID, ORDER_NO, PERSON_ID)"
                       + "values"
                       + "(?,?,?)");
               stat.setInt(1, orderList1.getOrderId());
               stat.setInt(2, orderList1.getOrderNumber());
               stat.setString(3, orderList1.getPersonId());
               //Execute the pstatement
               stat.execute();
           }
             
            //Commit the operation in db
           conn.commit();
             
         }catch(SQLException e)
         {
             try {
                 throw new Exception(e.getMessage(),e.getCause());
             } catch (Exception ex) {
                 Logger.getLogger(StoreTableDataDaoImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
             }
         }catch(Exception ex)
         {
             try {
                 throw new Exception(ex.getMessage(),ex.getCause());
             } catch (Exception ex1) {
                 Logger.getLogger(StoreTableDataDaoImpl.class.getName()).log(Level.SEVERE, ex1.getMessage(), ex1);
             }
         }
         finally
         {
             try {
                 //Connection close 
                 if(conn!=null)
                 conn.close();
             } catch (SQLException ex) {
                 Logger.getLogger(StoreTableDataDaoImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
             }
         }
    }
}
