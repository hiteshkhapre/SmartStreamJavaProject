/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import beans.Order;
import beans.Person;
import dao.GetDBDetailsDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Implementation class for getting the person and order details from DB
 * 
 */
public class GetDBDetailsDaoImpl implements GetDBDetailsDao {

    Connection conn = null;
    //Constructor
    public GetDBDetailsDaoImpl(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Get the person details with at least one order
     * @return 
     */
    @Override
    public List<Person> getPersonWithOneOrder() {
      PreparedStatement stat = null;
      Person person = null;
      ResultSet rs = null;
      List<Person> personList = new ArrayList<Person>();
         try
         {
            //Prepared statement to get the data from DB tables
            stat = conn.prepareStatement("SELECT p.person_id, p.first_name, p.last_name, p.street, p.city \n" +
                                            "from person p, `order` o\n" +
                                            "where o.person_id = p.person_id\n" +
                                            "group by p.first_name");
            
            //Storing the result in resultset
            rs = stat.executeQuery();
           
            while(rs.next())
            {
            person = new Person();
            person.setPersonId(rs.getString("PERSON_ID"));
            person.setFirstName(rs.getString("FIRST_NAME"));
            person.setLastName(rs.getString("LAST_NAME"));
            person.setStreet(rs.getString("STREET"));
            person.setCity(rs.getString("CITY"));
            personList.add(person);
            }   
            
         }catch(SQLException e)
         {
             try {
                 throw new Exception(e.getMessage(),e.getCause());
             } catch (Exception ex) {
                 Logger.getLogger(GetDBDetailsDaoImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
             }
         }catch(Exception ex)
         {
             try {
                 throw new Exception(ex.getMessage(),ex.getCause());
             } catch (Exception ex1) {
                 Logger.getLogger(GetDBDetailsDaoImpl.class.getName()).log(Level.SEVERE, ex1.getMessage(), ex1);
             }
         }
         finally
         {
             try {
                 if(conn!=null)
                 conn.close();
                 
                 if(rs!=null)
                 rs.close();
             } catch (SQLException ex) {
                 Logger.getLogger(GetDBDetailsDaoImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
             }
         }
        return personList;    
    }

    /**
     * Get the order details for particular person
     * @return 
     */
    @Override
    public Map<String, ArrayList<Order>> getOrderDetails(List<Person> personList) {
             
        PreparedStatement stat = null;
        Order order = null;
        Map<String, ArrayList<Order>> personOrderMap = new HashMap<>();
        ArrayList<Order> orderList = null;
        
        ResultSet rs = null;
                
         try
         {
            for(Person person : personList)
            {
                //Prepared statement to get the data from DB
                stat = conn.prepareStatement("SELECT o.order_id, o.order_no from `order` o where (o.person_id in (select "
                    + "PERSON_ID from person where first_name = ?))\n" +
                    "group by o.order_id;");
                stat.setString(1,person.getFirstName());
                //Storing the result of execution in resultset  
                rs = stat.executeQuery();
                
                orderList = new ArrayList<Order>();
                while(rs.next())
                {
                order = new Order();
                order.setOrderId(rs.getInt("ORDER_ID"));
                order.setOrderNumber(rs.getInt("ORDER_NO"));
                orderList.add(order);
                }    
                //Storing the Order details for persons in map
                personOrderMap.put(person.getFirstName(), orderList);
            }
             
         }catch(SQLException e)
         {
             try {
                 throw new Exception(e.getMessage(),e.getCause());
             } catch (Exception ex) {
                 Logger.getLogger(GetDBDetailsDaoImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
             }
         }catch(Exception ex)
         {
             try {
                 throw new Exception(ex.getMessage(),ex.getCause());
             } catch (Exception ex1) {
                 Logger.getLogger(GetDBDetailsDaoImpl.class.getName()).log(Level.SEVERE, ex1.getMessage(), ex1);
             }
         }
         finally
         {
             try {
                 if(conn!=null)
                 conn.close();
                 
                 if(rs!=null)
                 rs.close();
             } catch (SQLException ex) {
                 Logger.getLogger(GetDBDetailsDaoImpl.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
             }
         }
            
        return personOrderMap;     
    }
}
