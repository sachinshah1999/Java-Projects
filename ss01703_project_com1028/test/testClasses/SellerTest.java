package testClasses;

import org.junit.Test;

import implementationClasses.Seller;

public class SellerTest {
	
	@Test
	public void testConstruction() {
		
		Seller seller = Seller.getInstance(); //get the single instance of seller
		
		seller.logout(); // call the logout method
		
	}

}
