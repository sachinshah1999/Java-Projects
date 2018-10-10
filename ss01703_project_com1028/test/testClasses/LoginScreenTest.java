package testClasses;

import org.junit.Test;

import userGUI.LoginScreen;

public class LoginScreenTest {
	
	@Test
	public void testConstruction() {
		
		LoginScreen login = new LoginScreen(); //Object created successfully
		login.show(); //Can call show() method on the object.
		
	}

}
