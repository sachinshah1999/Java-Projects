package implementationClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import database.dbConnection;

/**
 * This is a singleton class. Meaning only one instance of this class can exists throughout the program.
 * @author Sachin
 */

public class Admin extends User {
	
	private static Admin single_instance = null; //Declare the single instance by making use of static modifier.
	private Connection connection; //Used to provide connection to the database.
	
	/**
	 * Admin constructor needs to be private so it can not be initialised, because this class is singleton
	 * It initialises values first name, last name, email and password from its superclass user.
	 */
	
	private Admin() {
		super(firstName, lastName, email, password);
	}
	
	/**
	 * static method to get the instance of the admin class.
	 * @return
	 */
	
	public static synchronized Admin getInstance(){
        if (single_instance == null)
            single_instance = new Admin();
        
        return single_instance;
	}
	
	/**
	 * This method is used to register the admin to the database.
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
				pst.setString(5, "Admin");
				pst.executeUpdate();
				pst.close();
				connection.close();
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query inserts the email into the admin table.
				 * Use a prepared statement to setup query.
				 * Set the email in the query.
				 * Execute the query.
				 */
				
				
				connection = dbConnection.dbConnector();
				String query2 = "INSERT INTO Admin VALUES(?)";
				PreparedStatement pst2 = connection.prepareStatement(query2);
				pst2.setString(1, email);
				pst2.executeUpdate();
				pst2.close();
				connection.close();

			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e);
			}
			return true;
		}
	}
	
	/**
	 * This method is used to approve the seller's product. So that is can be listed on the market.
	 * @param productID - The id of the product that is going to be approved.
	 * @return
	 */
	
	public boolean approveSellerProduct(int productID) {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query used to update the products, approved column and set it to yes.
			 * Use a prepared statement to setup query.
			 * Set the productID in the query.
			 * Execute the update query.
			 */

			connection = dbConnection.dbConnector(); 
			String query = "UPDATE Products SET Approved = 'Yes' WHERE ID = ?";
			PreparedStatement pst = connection.prepareStatement(query); 
			pst.setInt(1, productID);
			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Product has been approved and will now be listed on the market!"); //Inform admin that the product has been approved.
			
			pst.close(); //Close the prepared statement 
			connection.close(); //Close the connection

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return true;	
	}
	
	/**
	 * This method is used to ban sellers.
	 * @param sellerID - The id of the seller that is going to be banned.
	 * @return
	 */
	
	public boolean banSeller(String sellerID) {
		try {
			/**
			 * Make a connection to database.
			 * Construct the query. Query updates the seller's banned column and sets it to 'Yes' in the database.
			 * Use a prepared statement to setup query.
			 * Set the sellerID in the query.
			 * Execute the update query.
			 */

			connection = dbConnection.dbConnector();
			String query = "UPDATE Seller SET Banned = 'Yes' WHERE Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, sellerID);
			pst.executeUpdate();
			
			/**
			 * Close the result set, prepared statement and connection.
			 */
			
			pst.close();
			connection.close();

			JOptionPane.showMessageDialog(null, "Seller has been banned."); //Inform the admin, that the seller has been banned.
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	
	/**
	 * This method is used to unban sellers.
	 * @param sellerID - The id of the seller that is going to be banned.
	 * @return
	 */
	
	public boolean unBanSeller(String sellerID) {
		try {
			/**
			 * Make a connection to database.
			 * Construct the query. Query updates the seller's banned column and sets it to 'No' in the database.
			 * Use a prepared statement to setup query.
			 * Set the sellerID in the query.
			 * Execute the update query.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "UPDATE Seller SET Banned = 'No' WHERE Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, sellerID);
			pst.executeUpdate();
			
			/**
			 * Close the result set, prepared statement and connection.
			 */

			pst.close();
			connection.close();
			
			JOptionPane.showMessageDialog(null, "Seller has been unbanned."); //Inform the admin, that the seller has been banned.
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
		return true;
	}
	
	
	
}
