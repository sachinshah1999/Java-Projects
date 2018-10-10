package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

import database.dbConnection;
import sellerGUI.CurrentListingsScreen;
import userGUI.LoginScreen;

public class CurrentListingsScreenTest {
	
private Connection connection;
	
	/**
	 * Test object creation
	 * Get the product name of the first record in the products table that is sold by "ss@gmail.com" and is approved
	 */
	
	@Test
	public void testConstruction() {
		
		String productName = null;
		
		try {
			
			connection = dbConnection.dbConnector();
			//Get the first record in the products table that is sold by "ss@gmail.com" and is approved.
			String query = "SELECT Products.ID, Products.Name "
					+ "FROM Products "
					+ "INNER JOIN Sells ON Products.ID = Sells.ID "
					+ "WHERE Sells.Email = 'ss@gmail.com' AND Sold = 'No' AND Approved = 'Yes' LIMIT 1";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				productName = rs.getString("Name"); //Assign string variable productName to product name.
			}
			
			rs.close();
			pst.close();
			connection.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}		
		
		LoginScreen.setEmail("ss@gmail.com"); //Set the seller primary key
		CurrentListingsScreen listings = new CurrentListingsScreen();
		
		assertEquals(productName, listings.getProductName()); //Check the product name, can be obtained using getProductName() method.	
		
		
		
	}

}
