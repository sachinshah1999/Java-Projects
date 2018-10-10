package testClasses;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import implementationClasses.User;

public class UserTest {
	
	@Test
	public void testConstruction() {
		User.setEmail("test");
		assertEquals("test", User.getEmail());
	}
}
