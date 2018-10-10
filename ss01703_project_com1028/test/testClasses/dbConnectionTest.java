package testClasses;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import database.dbConnection;
import userGUI.LoginScreen;

public class dbConnectionTest {
	
	@Test
	public void testConstruction() {
		
		dbConnection connection = new dbConnection(); //Object created successfully
		
		LoginScreen.setEmail("s@gmail.com"); //Set Customer ID
		assertEquals(true, connection.verifyFunds(50));
		
		
	
	}
	
	

}
