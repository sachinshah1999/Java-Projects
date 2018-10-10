package testClasses;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.dbConnection;
import implementationClasses.Customer;

public class CustomerTest {
	
	@Test
	public void testConstruction() {
		
		Customer customer = Customer.getInstance(); //get the single instance of customer
		
		customer.toString(); // call the toString method
	}
	
	@Test(expected = NullPointerException.class)
	public void testEmptyFieldRegister() {
		Customer customer = Customer.getInstance(); //Get the instance of customer
		
		/**
		 * A first name field is empty hence an null pointer exception is expected
		 */
		
		assertEquals(false, customer.register("", "Shah", "CustomerTest@gmail.com", "password")); 
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidEmailFieldRegister() {
		Customer customer = Customer.getInstance(); //Get the instance of customer
		
		/**
		 * Email field doesn't have an @ sign.
		 */
		
		assertEquals(false, customer.register("Sachin", "Shah", "CustomerTestgmail.com", "password")); 
	}
	
	@Before
	@Test
	public void testSuccessfulRegister() {
		
		Customer customer = Customer.getInstance(); //Get the instance of customer
		
		/**
		 * All fields are correct
		 */	
		
		assertEquals(true, customer.register("Sachin", "Shah", "CustomerTest@gmail.com", "password")); 
	}
	
	@After
	public void removeCustomer() {
		
		Connection connection;
		
		try {
			
			connection = dbConnection.dbConnector();
			String query = "DELETE FROM User WHERE Email = 'CustomerTest@gmail.com'";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query2 = "DELETE FROM Customer WHERE Email = 'CustomerTest@gmail.com'";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query3 = "DELETE FROM Non_Admin WHERE Email = 'CustomerTest@gmail.com'";
			PreparedStatement pst3 = connection.prepareStatement(query3);
			pst3.executeUpdate();
			connection.close();
			
			connection = dbConnection.dbConnector();
			String query4 = "DELETE FROM Wish_List WHERE Email = 'CustomerTest@gmail.com'";
			PreparedStatement pst4 = connection.prepareStatement(query4);
			pst4.executeUpdate();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	@Test
	public void testPurchasePrime() {
		
		Customer customer = Customer.getInstance();
		
		assertEquals(true, customer.purchasePrime());
		
	}
	
	/**
	 * Test if customer can add product to their wish list
	 */
	
	@Test
	public void addProductToWishListTest() {
		
		Customer customer = Customer.getInstance();
		
		assertEquals(true, customer.addProductToWishList(5, "CustomerTest@gmail.com"));
		
	}
	
	/**
	 * Test if customer can remove product from their wish list.
	 */
	
	@Test
	public void removeProductFromWishListTest() {
		
		Customer customer = Customer.getInstance();
		
		assertEquals(true, customer.removeProductFromWishList(5, "CustomerTest@gmail.com"));
		
	}
	
	/**
	 * Test if customer can add a product review.
	 */
	
	@Test
	public void addProductReviewTest() {
		
		Customer customer = Customer.getInstance();
		
		assertEquals(true, customer.addProductReview(5, "CustomerTest@gmail.com", "test", 5));
		
	}
	
	
	/**
	 * Test if customer can remove a product review.
	 */
	
	@Test
	public void removeProductReview() {
		
		Customer customer = Customer.getInstance();
		
		assertEquals(true, customer.removeProductReview(5, "CustomerTest@gmail.com"));
		
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

}
