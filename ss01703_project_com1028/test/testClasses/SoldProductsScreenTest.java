package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

import database.dbConnection;
import sellerGUI.SoldProductsScreen;
import userGUI.LoginScreen;

public class SoldProductsScreenTest {
	
	private Connection connection;
	
	/**
	 * Test object creation
	 * Get the product name of the first record from the sells table, where the product is sold.
	 */
	
	@Test
	public void testConstruction() {
		
		String productName = null;
		
		try {
			
			connection = dbConnection.dbConnector();
			//Get the first record in the sells table INNER JOIN with products table, to get product name.
			String query = "SELECT Products.ID, Products.Name FROM Products INNER JOIN Sells ON Products.ID = Sells.ID "
					+ "WHERE Sells.Email = 'ss@gmail.com' AND Sells.Sold = 'Yes' LIMIT 1";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				productName = rs.getString("Name"); //Assign string variable productName to product name.
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		LoginScreen.setEmail("ss@gmail.com"); //Set the seller's primary key
		SoldProductsScreen sold = new SoldProductsScreen();
		
		assertEquals("Name of the product", sold.getProductName()); //Check the product name, can be obtained using getProductName() method.
		
		
	}

}
