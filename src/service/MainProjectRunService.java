package service;

import beans.Order;
import beans.Person;
import processor.FileProcessor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Service Class to run the service functionality
 */
public class MainProjectRunService {
    
    public static void main(String[] args)
    {
        Properties prop = new Properties();
	InputStream input = null;
 
	try {
                //get the properties file
		input = new FileInputStream("src\\properties\\fileProperties.properties");
 
		// load the properties file
		prop.load(input);
 
		FileProcessor fileProcessor = new FileProcessor();
                
                //Method to read the file "Person.data" and populate the table 'PERSON' with the data in file "Person.data"
                List<Person> personList = fileProcessor.readPersonData(prop.getProperty("personFilePath"));
                System.out.println("Data from Person.data file has been successfully read and populated in DB table.");
                
                //Method to read the file "Order.data" and populate the table 'ORDER' with the data in file "Order.data"
                List<Order> orderList = fileProcessor.readOrderData(prop.getProperty("orderFilePath"));
                System.out.println("Data from Order.data file has been successfully read and populated in DB table.\n");
                
                //Store Details in DB tables
                fileProcessor.populatePersonAndOrderData(personList,orderList);
                
                //Method to get the details of person with at least one order and all order details for the corressponding person 
                fileProcessor.getPersonAndOrderDetails();
       
        } catch (IOException ex) {
            Logger.getLogger(MainProjectRunService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);;
	}catch (Exception e)
        {
           Logger.getLogger(MainProjectRunService.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				 Logger.getLogger(MainProjectRunService.class.getName());
			}
		}
	}
 
  }
}
