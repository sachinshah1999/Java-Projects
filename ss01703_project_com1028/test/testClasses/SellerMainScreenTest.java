package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.dbConnection;
import implementationClasses.Seller;
import sellerGUI.SellerMainScreen;
import userGUI.LoginScreen;

public class SellerMainScreenTest {
	
	private Connection connection;
	
	/**
	 * Setup a dummy seller account in the database, before object creation.
	 */
	
	@Before
	public void setup() {
		Seller.getInstance().register("Customer", "Test", "SellerMainScreenTest@gmail.com", "Test"); //Register dummy admin
		LoginScreen.setEmail("SellerMainScreenTest@gmail.com"); //Set the primary key
	}
	
	/**
	 * Delete dummy seller account from database after object testing.
	 */
	
	@After
	public void deleteAdmin() {
		
		try {
			
			connection = dbConnection.dbConnector();
			String query = "DELETE FROM User WHERE Email = 'SellerMainScreenTest@gmail.com'";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query2 = "DELETE FROM Seller WHERE Email = 'SellerMainScreenTest@gmail.com'";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query3 = "DELETE FROM Non_Admin WHERE Email = 'SellerMainScreenTest@gmail.com'";
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
	 * If the correct welcome text, with the dummy seller first name and last name is shown,
	 * Then the object works.
	 */
	
	@Test
	public void testConstruction() {		
		SellerMainScreen seller = new SellerMainScreen();
		
		assertEquals("Welcome, Customer Test", seller.getWelcomeText());
	}

}
