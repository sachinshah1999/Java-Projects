package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

import customerGUI.DisplayProductsScreen;
import database.dbConnection;

public class DisplayProductsScreenTest {
	
	private Connection connection;
	
	/**
	 * Test object creation
	 * Get the product name of the first record in the products table
	 */
	
	@Test
	public void testConstruction() {
		
		String productName = null;
		
		try {
			
			connection = dbConnection.dbConnector();
			String query = "SELECT * FROM Products ORDER BY ID ASC LIMIT 1"; //Get the first record in the products table
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
		
		DisplayProductsScreen product = new DisplayProductsScreen();
		
		assertEquals(productName, product.getProductName()); //Check the product name, can be obtained using getProductName() method.
		
		
	}

}
