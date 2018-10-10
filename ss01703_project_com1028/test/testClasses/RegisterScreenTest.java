package testClasses;

import org.junit.Test;

import userGUI.RegisterScreen;

public class RegisterScreenTest {
	
	@Test
	public void testConstruction() {
		
		RegisterScreen register = new RegisterScreen(); //Object created successfully
		register.show(); //Can call show() method on the object.
		
	}

}
