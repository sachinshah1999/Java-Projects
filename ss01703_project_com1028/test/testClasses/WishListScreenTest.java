package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

import customerGUI.PurchasedProductsScreen;
import customerGUI.WishListScreen;
import database.dbConnection;
import userGUI.LoginScreen;

public class WishListScreenTest {
	
	private Connection connection;
	
	/**
	 * Test object creation
	 * Get the product name of the first record in the wish_list table
	 */
	
	@Test
	public void testConstruction() {
		
		String productName = null;
		
		try {
			
			connection = dbConnection.dbConnector();
			//Get the first record in the wish_list table.
			String query = "SELECT Products.ID, Products.Name FROM Products INNER JOIN Wish_List ON Products.ID = Wish_List.ID WHERE Email = 's@gmail.com' LIMIT 1"; 
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
		WishListScreen wish = new WishListScreen();
		
		assertEquals(productName, wish.getProductName()); //Check the product name, can be obtained using getProductName() method.
		
	}

}
