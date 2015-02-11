/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor;

import beans.Order;
import beans.Person;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hitzyy
 */
public class FileProcessorTest {
    
    public FileProcessorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    /**
     * Test of readPersonData method, of class FileProcessor.
     */
    @Test
    public void testReadPersonData() throws Exception {
        System.out.println("readAndPopulatePersonData");
        String filePath = "test/inputFiles/Person.data";
        FileProcessor instance = new FileProcessor();
        
        List<Person> expResult = new ArrayList<Person>();
        Person person = null;
        person = new Person();
        person.setPersonId("1");
        person.setFirstName("Ola");
        person.setLastName("Hansen");
        person.setStreet("Timoteivn");
        person.setCity("Sandnes");
        expResult.add(0, person);
        
        List<Person> result = instance.readPersonData(filePath);
        assertEquals(4, result.size());
        assertEquals(expResult.get(0).getPersonId(), result.get(0).getPersonId());
        assertEquals(expResult.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(expResult.get(0).getLastName(), result.get(0).getLastName());
        assertEquals(expResult.get(0).getStreet(), result.get(0).getStreet());
        assertEquals(expResult.get(0).getCity(), result.get(0).getCity());
    }

    /**
     * Test of readOrderData method, of class FileProcessor.
     */
    @Test
    public void testReadOrderData() throws Exception {
        System.out.println("readAndPopulateOrderData");
        String filePath = "test/inputFiles/Order.data";
        FileProcessor instance = new FileProcessor();
       
        List<Order> expResult = new ArrayList<Order>();
        Order order = null;
        order = new Order();
        order.setOrderId(10);
        order.setOrderNumber(2000);
        order.setPersonId("1");
        expResult.add(0, order);
        
        List<Order> result = instance.readOrderData(filePath);
        assertEquals(4, result.size());
        assertEquals(expResult.get(0).getOrderId(), result.get(0).getOrderId());
        assertEquals(expResult.get(0).getOrderNumber(), result.get(0).getOrderNumber());
        assertEquals(expResult.get(0).getPersonId(), result.get(0).getPersonId());
    }
}
