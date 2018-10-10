package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import adminGUI.AdminMainScreen;
import customerGUI.CustomerMainScreen;
import implementationClasses.Admin;
import implementationClasses.Customer;
import implementationClasses.Seller;
import sellerGUI.SellerMainScreen;
import userGUI.LoginScreen;

/**
 * This class is used to provide database connection methods.
 * @author Sachin
 *
 */

public class dbConnection {

	private static Connection connection; //Connection object used to interact with the database.

	public static Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC"); 
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db"); //specify the database path. Currently stored in root project folder.
			return connection; //return the connection.
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
	/**
	 * This method is used to login. I placed the method here because I was not sure as to which class (seller, admin or customer) would call the login method.
	 * Since I can't call methods on abstract objects, the user class is out of the question. 
	 * This method checks the email and password against a database and returns true if the a login is successful.
	 * @param email - Email used to login with
	 * @param password - Password used to login with
	 * @return - return true or false, depending on whether the method was executed correctly.
	 */

	public static boolean login(String email, String password) {
		
		/**
		 * Validation to prevent empty fields.
		 */

		if (email.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter an email address and password.");
			return false;

		} else {

			try {
				
				/**
				 * Make a connection to database.
				 * Construct the query. Query tries to find record associated with the email and password from the user table.
				 * Use a prepared statement to setup query.
				 * Set the email and password in the query.
				 * Execute the query.
				 */

				connection = dbConnection.dbConnector();
				String query = "SELECT * FROM User WHERE Email = ? AND Password = ?";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, email);
				pst.setString(2, password);

				ResultSet rs = pst.executeQuery();
				
				/**
				 * If record is found, find the user type.
				 */

				if (rs.next()) {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query gets the details associated with the email from the user table.
					 * Use a prepared statement to setup query.
					 * Set the email in the query.
					 * Execute the query.
					 */

					connection = dbConnection.dbConnector();
					PreparedStatement pst2 = connection.prepareStatement("SELECT * FROM User WHERE Email = ?");
					pst2.setString(1, email);
					ResultSet rs2 = pst2.executeQuery();
					
					/**
					 * While there are results, find the user type.
					 */

					while (rs2.next()) {

						String type = rs.getString("User_Type"); //The user type obtained from the user_type column in the user table.
						
						/**
						 * If the type is customer, inform them of a successful login and redirected them to the customer main screen.
						 * Set the email and password to the instance of seller.
						 */

						if (type.equals("Customer")) {
							JOptionPane.showMessageDialog(null, "Successful Login!");
							
							Customer.getInstance().setEmail("email");
							Customer.getInstance().setPassword("password");
							
							CustomerMainScreen customer = new CustomerMainScreen();
							customer.show();
							
							/**
							 * If the type is seller, determine whether or not they are banned, by querying the seller table
							 */
							
						} else if (type.equals("Seller")) {
							
							/**
							 * Make a connection to database.
							 * Construct the query. Query the seller table to find a record where the seller is not banned.
							 * Use a prepared statement to setup query.
							 * Set the email in the query.
							 * Execute the query.
							 */
							
							connection = dbConnection.dbConnector();
							PreparedStatement pst3 = connection.prepareStatement("SELECT * FROM Seller WHERE Email = ? AND Banned = 'No'");
							pst3.setString(1, email);
							ResultSet rs3 = pst3.executeQuery();
							
							/**
							 * If there is a result, meaning the seller is not banned.
							 * Set the email and password, to the instance of seller.
							 * Inform the seller of a successful login and redirected them to the seller main screen.
							 */
							
							if (rs3.next()) {
								JOptionPane.showMessageDialog(null, "Successful Login!");
								
								Seller.getInstance().setEmail(email);
								Seller.getInstance().setPassword(password);
								
								SellerMainScreen sellerMainScreen = new SellerMainScreen();
								sellerMainScreen.show();
								
								/**
								 * Else there is no record, which means they are banned.
								 * Inform the seller of failed login.
								 */
								
							} else {
								JOptionPane.showMessageDialog(null, "Your account has been banned due to too much negative feedback on your products.");
								return false;
							}
							
							/**
							 * Close the result set, prepared statement and connection.
							 */
							
							rs3.close();
							pst3.close();
							connection.close();
							
							/**
							 * If the type is admin, inform them of a successful login and redirected them to the admin main screen.
							 */
							
						} else if (type.equals("Admin")) {
							JOptionPane.showMessageDialog(null, "Successful Login!");
							
							Admin.getInstance().setEmail(email);
							Admin.getInstance().setPassword(password);
							
							AdminMainScreen admin = new AdminMainScreen();
							admin.show();
						}

					}
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs2.close();
					rs2.close();
					connection.close();

				} else {
					JOptionPane.showMessageDialog(null, "Incorrect email address or password, Please try again!"); //Else the email and password do not match. Inform the user.
					return false;
				}
				
				/**
				 * Close the result set, prepared statement and connection.
				 */

				rs.close();
				pst.close();
				connection.close();

			} catch (Exception e) {

			}
			return true;
		}
	}
	
	/**
	 * Verify funds method is used to check whether the customer or sellers balance is greater than the cost.
	 * @param cost - The cost of the what they are trying to purchase.
	 * @return - True is they can purchase item else return false.
	 */
	
	public static boolean verifyFunds(double cost) {
		
		/**
		 * declare boolean called verify and double called balance. 
		 */
		
		boolean verify = false; 
		double balance = 0.0;  		
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query used to get the seller or customer's balance.
			 * Use a prepared statement to setup query.
			 * Set the email in the query.
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "SELECT Balance FROM Non_Admin WHERE Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, LoginScreen.getEmail());
			
			ResultSet rs = pst.executeQuery();
			
			/**
			 * While there are results, get the "Balance" column value from the non_admin table and set it to the balance variable.
			 */
			
			while(rs.next()) {
				balance = rs.getDouble("Balance");
			}
			
			/**
			 * If balance greater than or equal to the cost; if they can afford to purchase, return truel
			 * Else if balance less than cost, return false;
			 */
			
			if(balance >= cost) {
				return true;
			} else if (balance < cost) {
				return false;
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		return verify; //return boolean value.
	}
	
	
}
