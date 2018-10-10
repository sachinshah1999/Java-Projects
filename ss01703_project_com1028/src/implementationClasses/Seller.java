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

public class Seller extends User {
	
	private static Seller single_instance = null; //Declare the single instance by making use of static modifier.
	private Connection connection; //Used to provide connection to the database.
	
	/**
	 * Seller constructor needs to be private so it can not be initialised, because this class is singleton.
	 * It initialises values first name, last name, email and password from its superclass user.
	 */
	
	private Seller() {
		super(firstName, lastName, email, password);
	}
	
	/**
	 * static method to get the instance of the customer class.
	 * @return
	 */
	
	public static synchronized Seller getInstance(){
        if (single_instance == null)
            single_instance = new Seller();
        
        return single_instance;
	}
	
	/**
	 * This method is used to register the customer to the database.
	 */

	@Override
	public boolean register(String firstName, String lastName, String email, String password) {
		
		/**
		 * Validation is done to ensure the fields are not empty and the correct data-type is entered into each field
		 */

		if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "All fields must be completed!");
			return false;
		} else if (!email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$")) {
			JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
			return false;
		} else if (!firstName.matches("[A-Z][a-zA-Z]*") || !lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
			JOptionPane.showMessageDialog(null, "Please enter a valid name.");
			return false;
		} else {

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
				pst.setString(5, "Seller");
				pst.executeUpdate();
				pst.close();
				connection.close();
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the email and the balance of the customer into the Non_Admin table.
				 * Use a prepared statement to setup query.
				 * Set the email and balance in the query.
				 * Execute the query.
				 */
				
				connection = dbConnection.dbConnector();
				String query2 = "INSERT INTO Non_Admin VALUES(?, ?)";
				PreparedStatement pst2 = connection.prepareStatement(query2);
				pst2.setString(1, email);
				pst2.setDouble(2, 0.0);
				pst2.executeUpdate();
				pst2.close();
				connection.close();
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the email and sets the banned to 'No' in the Seller table.
				 * Use a prepared statement to setup query.
				 * Set email and ban status in the query.
				 * Execute the query.
				 */
				
				connection = dbConnection.dbConnector();
				String query3 = "INSERT INTO Seller VALUES(?, ?)";
				PreparedStatement pst3 = connection.prepareStatement(query3);
				pst3.setString(1, email);
				pst3.setString(2, "No");
				pst3.executeUpdate();
				pst3.close();
				connection.close();

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showConfirmDialog(null, e);
			}
			return true;
		}
	}
	
	/**
	 * This method is used to list a product, for the admin to approve, before it is put onto the market.
	 * @param name - Name of the product
	 * @param desc - Description of the product
	 * @param price - Price of the product
	 * @param quantity - Amount of the product on sale.
	 * @return - Returns true if the method runs successfully.
	 */
	
	public boolean listProduct(String name, String desc, double price, int quantity) {
			try {
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the product details into the products table in the database
				 * Use a prepared statement to setup query.
				 * Set the product name, description, quantity and price in the query
				 * Execute the query update.
				 */
				
				connection = dbConnection.dbConnector();
				String query = "INSERT INTO Products (Name, Description, Price, Quantity) VALUES(?, ?, ?, ?)";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, name);
				pst.setString(2, desc);
				pst.setDouble(3, price);
				pst.setInt(4, quantity);
				pst.executeUpdate();
				pst.close();
				connection.close();
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the email of the seller into the sells table, which keeps track of the seller of each product.
				 * Use a prepared statement to setup query.
				 * Set the primaryKey in the query.
				 * Execute the query update.
				 */

				connection = dbConnection.dbConnector();
				String query2 = "INSERT INTO Sells (Email) VALUES (?)";
				PreparedStatement pst2 = connection.prepareStatement(query2);
				pst2.setString(1, LoginScreen.getEmail());
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
	 * This method is used to a product, that has been listed on the market.
	 * @param name - Name of the product
	 * @param desc - Description of the product
	 * @param price - Price of the product
	 * @param quantity - Amount of the product on sale.
	 * @return - Returns true if the method runs successfully.
	 */
	
	public boolean updateProduct(String name, String desc, double price, int quantity, int productID) {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query updates the product details in Products table.
			 * Use a prepared statement to setup query.
			 * Set the product name, description, price, quantity, and id in the query
			 * Execute the query update.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "UPDATE Products SET Name = ?, Description = ?, Price = ?, Quantity = ? WHERE ID = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, desc);
			pst.setDouble(3, price);
			pst.setInt(4, quantity);
			pst.setInt(5, productID);
			pst.executeUpdate();
			pst.close();
			
			pst.close();
			connection.close();

			JOptionPane.showMessageDialog(null, "Product details updated!"); //Inform the seller
			
		} catch (Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	
	/**
	 * This method is used to remove a product from the database
	 * @param productID - The id of the product you are removing from the database
	 * @param primaryKey - The email address of the seller, to verify who is removing the product.
	 * @return - Returns true if the method executes successfully.
	 */
	
	public boolean removeProduct(int productID, String primaryKey) {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query used to delete the product from the products table
			 * Use a prepared statement to setup query.
			 * Set the productID in the query
			 * Execute the update query.
			 */

			connection = dbConnection.dbConnector();
			String query = "DELETE FROM Products WHERE ID = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, productID);
			pst.executeUpdate();
			pst.close();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query used to delete the product's seller reference from the sells table.
			 * Use a prepared statement to setup query.
			 * Set the productID and primaryKey in the query
			 * Execute the update query.
			 */

			connection = dbConnection.dbConnector();
			String query2 = "DELETE FROM Sells WHERE ID = ? AND Email = ?";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.setInt(1, productID);
			pst2.setString(2, primaryKey);
			pst2.executeUpdate();
			pst2.close();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query used to update the "Products" auto increment field ID. As the product has been deleted, we need to update the reference.
			 * Use a prepared statement to setup query.
			 * Execute the update query.
			 */

			connection = dbConnection.dbConnector();
			String query3 = "UPDATE sqlite_sequence SET seq = seq - 1 WHERE name = 'Products'";
			PreparedStatement pst3 = connection.prepareStatement(query3);
			pst3.executeUpdate();
			pst3.close();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query used to update the "Sells" auto increment field ID. As the product has been deleted, we need to update the reference.
			 * Use a prepared statement to setup query.
			 * Execute the update query.
			 */

			connection = dbConnection.dbConnector();
			String query4 = "UPDATE sqlite_sequence SET seq = seq - 1 WHERE name = 'Sells'";
			PreparedStatement pst4 = connection.prepareStatement(query4);
			pst4.executeUpdate();
			pst4.close();
			connection.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
}
