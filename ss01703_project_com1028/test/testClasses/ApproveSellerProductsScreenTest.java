package testClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adminGUI.ApproveSellerProductsScreen;
import database.dbConnection;
import implementationClasses.Admin;
import implementationClasses.Seller;
import userGUI.LoginScreen;


public class ApproveSellerProductsScreenTest {
	
	private Connection connection;
	
	/**
	 * Setup a dummy product product in the database, before object creation.
	 */
	
	@Before
	public void setup() {
		LoginScreen.setEmail("ss@gmail.com");
		Seller.getInstance().listProduct("Tests", "Test", 5.99, 5);
	}
	
	/**
	 * Delete dummy product from database after object testing.
	 */
	
	@After
	public void deleteProduct() {
		
		int productID = 0;
		
		try {
			
			connection = dbConnection.dbConnector();
			String query = "SELECT * FROM Products ORDER BY ID DESC LIMIT 1";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				productID = rs.getInt("ID");
			}
			
			rs.close();
			pst.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		
		Seller.getInstance().removeProduct(productID, "ss@gmail.com");
		
	}
	
	/**
	 * Test object creation.
	 * Get the product name after dummy product creation
	 * If the correct product name is shown then the object is created successfully and works as intended.
	 */
	
	@Test
	public void testConstruction() {
		
		ApproveSellerProductsScreen approve = new ApproveSellerProductsScreen();
		
		assertEquals("Tests", approve.getProductName());
		
		
	}

}
