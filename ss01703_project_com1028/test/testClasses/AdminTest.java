package testClasses;

import org.junit.Test;

import implementationClasses.Admin;

public class AdminTest {
	
	@Test
	public void testConstruction() {
		
		Admin admin = Admin.getInstance(); //get the single instance of admin
		admin.logout(); // call the logout method
		
	}

}
