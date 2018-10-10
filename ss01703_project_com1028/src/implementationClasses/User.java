package implementationClasses;

import userGUI.LoginScreen;

/**
 * Abstract class to represent the a user. This class will be extended by the Seller, Customer and Admin classes.
 * @author Sachin
 *
 */

public abstract class User {
	
	/**
	 * All the fields associated with the class.
	 * They are all protected meaning only the sub-classes can access them.
	 * They are all static as my Admin, Customer and Seller classes are all Singleton classes. 
	 */
	
	protected static String firstName; 
	protected static String lastName; 
	protected static String email; 
	protected static String password; 
	
	/**
	 * Constructor to initialise the instance variables.
	 * @param firstName - First name of user
	 * @param lastName - last name of user
	 * @param email - email of user
	 * @param password - password of user
	 */
	
	public User(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Abstract method register, to register user with the database. 
	 * Method is abstract because in each case (seller, admin and customer, this method will affect different tables.
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @return
	 */
	
	protected abstract boolean register(String firstName, String lastName, String email, String password);
	
	/**
	 * logout method to show the login screen.
	 * reset the values since user has logged out.
	 * @return
	 */
	
	public boolean logout() {
		
		User.setFirstName(null);
		User.setLastName(null);
		User.setEmail(null);
		User.setPassword(null);
		
		LoginScreen login = new LoginScreen();
		login.show();
		return true;
	}
	
	/** Getters & Setters **/

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		User.firstName = firstName;
	}

	public static String getLastName() {
		return lastName;
	}

	public static void setLastName(String lastName) {
		User.lastName = lastName;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		User.email = email;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		User.password = password;
	}
	
	
}
