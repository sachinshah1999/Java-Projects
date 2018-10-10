package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

import customerGUI.PurchasedProductsScreen;
import database.dbConnection;
import userGUI.LoginScreen;

public class PurchasedProductsScreenTest {
	
	private Connection connection;
	
	/**
	 * Test object creation
	 * Get the product name of the first record in the purchases table
	 */
	
	@Test
	public void testConstruction() {
		
		String productName = null;
		
		try {
			
			connection = dbConnection.dbConnector();
			//Get the first record in the purchases table.
			String query = "SELECT Products.Name, Products.ID FROM Products INNER JOIN Purchases ON Products.ID = Purchases.ID WHERE Purchases.Email = 's@gmail.com' LIMIT 1"; 
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
		
		LoginScreen.setEmail("s@gmail.com"); //Set the customer primary key
		PurchasedProductsScreen product = new PurchasedProductsScreen();
		
		assertEquals(productName, product.getProductName()); //Check the product name, can be obtained using getProductName() method.
		
	}
}
