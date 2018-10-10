package testClasses;

import org.junit.Test;

import userGUI.UserSettingsScreen;

public class UserSettingsScreenTest {
	
	@Test
	public void testConstruction() {
		
		UserSettingsScreen settings = new UserSettingsScreen(); //Object created successfully
		settings.show(); //Can call show() method on the object.
		
	}
}
