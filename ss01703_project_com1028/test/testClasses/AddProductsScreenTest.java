package testClasses;

import org.junit.Test;

import sellerGUI.AddProductScreen;

public class AddProductsScreenTest {
	
	@Test
	public void testConstruction() {
		
		AddProductScreen addProducts = new AddProductScreen(); //Object created successfully
		addProducts.toString(); //Can call toString() method on the object.
		
	}

}
