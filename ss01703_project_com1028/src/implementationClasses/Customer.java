package implementationClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import database.dbConnection;
import userGUI.LoginScreen;

/**
 * This is a singleton class. Meaning only one instance of this class can exists throughout the program.
 * @author Sachin
 */

public class Customer extends User {
	
	private static Customer single_instance = null; //Declare the single instance by making use of static modifier.
	private Connection connection; //Used to provide connection to the database.
	
	/**
	 * Customer constructor needs to be private so it can not be initialised, because this class is singleton.
	 * It initialises values first name, last name, email and password from its superclass user.
	 */
	
	private Customer() {
		super(firstName, lastName, email, password);
	}
	
	/**
	 * static method to get the instance of the customer class.
	 * @return
	 */
	
	public static synchronized Customer getInstance(){
        if (single_instance == null)
            single_instance = new Customer();
        
        return single_instance;
	}
	
	/**
	 * This method is used to register the customer to the database.
	 */

	@Override
	public boolean register(String firstName, String lastName, String email, String password) {
		
		boolean changeScreen = false;
		
		/**
		 * Validation is done to ensure the fields are not empty and the correct data-type is entered into each field
		 */

		if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
			throw new NullPointerException("All fields must be completed!");
		} else if (!email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$")) {
			throw new IllegalArgumentException("Please enter a valid email address.");
		} else if (!firstName.matches("[A-Z][a-zA-Z]*") || !lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
			throw new IllegalArgumentException("Please enter a valid name.");
		} else {
			
			changeScreen = true;

			try {
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the parameter values into the User table
				 * Use a prepared statement to setup query.
				 * Set the firstName, lastName, email and password in the query.
				 * Execute the query.
				 */
				
				connection = dbConnection.dbConnector();
				String query = "INSERT INTO User VALUES(?, ?, ?, ?, ?)";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, firstName);
				pst.setString(2, lastName);
				pst.setString(3, email);
				pst.setString(4, password);
				pst.setString(5, "Customer");
				pst.executeUpdate();
				pst.close();
				connection.close();
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the email and the balance of the customer into the Non_Admin table.
				 * All customers start with a £100 balance.
				 * Use a prepared statement to setup query.
				 * Set the email and balance in the query.
				 * Execute the query.
				 */
				
				connection = dbConnection.dbConnector();
				String query2 = "INSERT INTO Non_Admin VALUES(?, ?)";
				PreparedStatement pst2 = connection.prepareStatement(query2);
				pst2.setString(1, email);
				pst2.setDouble(2, 100.00);
				pst2.executeUpdate();
				pst2.close();
				connection.close();
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the email and sets the prime column to 'No' in the customer table.
				 * This is because the customer is not yet a prime member, until they purchase it.
				 * Use a prepared statement to setup query.
				 * Set email and prime status in the query.
				 * Execute the query.
				 */
				
				connection = dbConnection.dbConnector();
				String query3 = "INSERT INTO Customer VALUES(?, ?)";
				PreparedStatement pst3 = connection.prepareStatement(query3);
				pst3.setString(1, email);
				pst3.setString(2, "No");
				pst3.executeUpdate();
				pst3.close();
				connection.close();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			return changeScreen;
		}
	}
	
	/**
	 * This method is used to purchase a prime membership
	 * @return - returns true if the purchase was successful.
	 */
	
	public boolean purchasePrime() {
		
		String primaryKey = LoginScreen.getEmail(); //Get the current customer's primary key.
		
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query takes away £50 from the customer's current balance.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "UPDATE Non_Admin SET Balance = Balance - 50 WHERE Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, primaryKey);
			pst.executeUpdate();
			pst.close();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query sets the prime column to 'Yes' for the customer.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query
			 * Execute the query.
			 */
			 
			connection = dbConnection.dbConnector();
			String query2 = "UPDATE Customer SET Prime = 'Yes' WHERE Email = ?";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.setString(1, primaryKey);
			pst2.executeUpdate();
			pst2.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	
	/**
	 * This method is used to add products to the customer's wish list.
	 * @param productID - The id of the product to be added to the wish list
	 * @param primaryKey - The email address of the customer.
	 * @return - Returns true if the method is executed successfully.
	 */
	
	public boolean addProductToWishList(int productID, String primaryKey) {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query inserts product into the wish list.
			 * Use a prepared statement to setup query.
			 * Set the productID and primaryKey in the query
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "INSERT INTO Wish_List VALUES(?, ?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, productID);
			pst.setString(2, primaryKey);
			pst.executeUpdate();
			pst.close();
			connection.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	
	/**
	 * This method is used to remove products from the customer's wish list.
	 * @param productID - The id of the product to be removed from the wish list
	 * @param primaryKey - The email address of the customer.
	 * @return - Returns true if the method is executed successfully.
	 */
	
	public boolean removeProductFromWishList(int productID, String primaryKey) {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query removes product into the wish list.
			 * Use a prepared statement to setup query.
			 * Set the productID and primaryKey in the query
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query2 = "DELETE FROM Wish_List WHERE ID = ? AND Email = ?";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.setInt(1, productID);
			pst2.setString(2, primaryKey);
			pst2.executeUpdate();
			pst2.close();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	
	/**
	 * Method to add a customer's product review
	 * @param productID - The id of the product, the customer is reviewing
	 * @param primaryKey - The email address of the customer. To identify which customer is writing the review.
	 * @param review - The actual text review
	 * @param rating - The rating out of 5
	 * @return - Returns true if the method is executed successfully.
	 */
	
	public boolean addProductReview(int productID, String primaryKey, String review, int rating) {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query inserts the review and rating into the Reviews table.
			 * Use a prepared statement to setup query.
			 * Set the productID, primaryKey, review and rating in the query
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "INSERT INTO Reviews VALUES(?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, productID);
			pst.setString(2, primaryKey);
			pst.setString(3, review);
			pst.setInt(4, rating);
			pst.executeUpdate();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query updates the customer's balance by an increase of £50.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query2 = "UPDATE Non_Admin SET Balance = Balance + 50 WHERE Email = ?";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.setString(1, primaryKey);
			pst2.executeUpdate();
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	
	/**
	 * Method to remove a customer's product review
	 * @param productID - The id of the product, the customer is removing the review for
	 * @param primaryKey - The email address of the customer. To identify which customer is removing the review.
	 * @return - Returns true if the method is executed successfully.
	 */
	
	public boolean removeProductReview(int productID, String primaryKey) {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query removes the customer's review from the Reviews table.
			 * Use a prepared statement to setup query.
			 * Set the productID and primaryKey in the query
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "DELETE FROM Reviews WHERE ID = ? AND Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, productID);
			pst.setString(2, primaryKey);
			pst.executeUpdate();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query updates the customer's balance by an decrease of £50.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query2 = "UPDATE Non_Admin SET Balance = Balance - 50 WHERE Email = ?";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.setString(1, primaryKey);
			pst2.executeUpdate();
			connection.close();

			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
		
	}
}
