package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adminGUI.AdminMainScreen;
import database.dbConnection;
import implementationClasses.Admin;
import userGUI.LoginScreen;



public class AdminMainScreenTest {
	
	private Connection connection;
	
	/**
	 * Setup a dummy admin account in the database, before object creation.
	 */
	
	@Before
	public void setup() {
		Admin.getInstance().register("Admin", "Test", "AdminMainScreenTest@gmail.com", "Test");
		LoginScreen.setEmail("AdminMainScreenTest@gmail.com");
	}
	
	/**
	 * Delete dummy admin account from database after object testing.
	 */
	
	@After
	public void deleteAdmin() {
		
		try {
			
			connection = dbConnection.dbConnector();
			String query = "DELETE FROM User WHERE Email = 'AdminMainScreenTest@gmail.com'";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query2 = "DELETE FROM Admin WHERE Email = 'AdminMainScreenTest@gmail.com'";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.executeUpdate();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	/**
	 * Test object creation.
	 * Get the welcome text after object creation
	 * If the correct welcome text, with the dummy admin first name and last name is shown,
	 * Then the object works.
	 */
	
	@Test
	public void testConstruction() {		
		AdminMainScreen admin = new AdminMainScreen();
		
		assertEquals("Welcome, Admin Test", admin.getWelcomeText());
	}
}
