package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import customerGUI.CustomerMainScreen;
import database.dbConnection;
import implementationClasses.Customer;
import userGUI.LoginScreen;

public class CustomerMainScreenTest {
	
	private Connection connection;
	
	/**
	 * Setup a dummy customer account in the database, before object creation.
	 */
	
	@Before
	public void setup() {
		Customer.getInstance().register("Customer", "Test", "CustomerMainScreenTest@gmail.com", "Test"); //Register dummy admin
		LoginScreen.setEmail("CustomerMainScreenTest@gmail.com"); //Set the primary key
	}
	
	/**
	 * Delete dummy customer account from database after object testing.
	 */
	
	@After
	public void deleteAdmin() {
		
		try {
			
			connection = dbConnection.dbConnector();
			String query = "DELETE FROM User WHERE Email = 'CustomerMainScreenTest@gmail.com'";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query2 = "DELETE FROM Customer WHERE Email = 'CustomerMainScreenTest@gmail.com'";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query3 = "DELETE FROM Non_Admin WHERE Email = 'CustomerMainScreenTest@gmail.com'";
			PreparedStatement pst3 = connection.prepareStatement(query3);
			pst3.executeUpdate();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Test object creation.
	 * Get the welcome text after object creation
	 * If the correct welcome text, with the dummy customer first name and last name is shown,
	 * Then the object works.
	 */
	
	@Test
	public void testConstruction() {		
		CustomerMainScreen customer = new CustomerMainScreen();
		
		assertEquals("Welcome, Customer Test", customer.getWelcomeText());
	}

}
