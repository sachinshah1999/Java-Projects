package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adminGUI.BanSellersScreen;
import database.dbConnection;
import implementationClasses.Seller;

public class BanSellersScreenTest {
	
	private Connection connection;
	
	/**
	 * Setup a dummy seller account in the database.
	 */
	
	@Before
	public void setup() {
		Seller.getInstance().register("Test", "Test", "BanSellersScreenTest@gmail.com", "Test");
	}
	
	@After
	public void deleteSeller() {
		try {
			
			connection = dbConnection.dbConnector();
			String query = "DELETE FROM User WHERE Email = 'BanSellersScreenTest@gmail.com'";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query2 = "DELETE FROM Seller WHERE Email = 'BanSellersScreenTest@gmail.com'";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query3 = "DELETE FROM Non_Admin WHERE Email = 'BanSellersScreenTest@gmail.com'";
			PreparedStatement pst3 = connection.prepareStatement(query3);
			pst3.executeUpdate();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Test object creation
	 * Get the seller name after dummy seller creation
	 * If the correct seller name is shown then the object is created successfully and works as intended.
	 */
	
	@Test
	public void testConstruction() {		
		BanSellersScreen ban = new BanSellersScreen();
		
		assertEquals("BanSellersScreenTest@gmail.com", ban.getSellerEmail());
	}

}
