package userGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import adminGUI.AdminMainScreen;
import customerGUI.CustomerMainScreen;
import database.dbConnection;
import sellerGUI.SellerMainScreen;

/**
 * This application window class, allows the user to change their first name, last name and password details.
 * 
 * @author Sachin
 *
 */

public class UserSettingsScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the user.
	private JTextField firstNameField; //Text field to represent the user's first name
	private JTextField lastNameField; //Text field to represent the user's last name
	private String primaryKey; //The email of user that is currently logged in.
	private String userType; //The type of user that is using this screen. (customer, seller or admin).
	private Connection connection; // Used to provide connection to the database.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserSettingsScreen window = new UserSettingsScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserSettingsScreen() {
		initialize();
		setDetails();
		findUserType();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Profile Settings");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(815, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(0, 0, 405, 523);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Profile Settings");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(140, 64, 125, 23);
		panel.add(lblNewLabel);

		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/settingsLarge.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setForeground(new Color(204, 204, 204));
		label.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		label.setBounds(74, 133, 256, 256);
		panel.add(label);

		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.WHITE);
		separator_6.setBounds(37, 410, 331, 9);
		panel.add(separator_6);

		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.WHITE);
		separator_7.setBounds(79, 446, 246, 2);
		panel.add(separator_7);

		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {
			
			/**
			 * When label is clicked, the userType is determined and the appropriate main screen is displayed.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {

				if (userType == "Customer") {
					CustomerMainScreen customerMainScreen = new CustomerMainScreen();
					customerMainScreen.show();
					frame.setVisible(false);
				} else if (userType == "Seller") {
					SellerMainScreen sellerMainScreen = new SellerMainScreen();
					sellerMainScreen.show();
					frame.setVisible(false);
				} else if (userType == "Admin") {
					AdminMainScreen adminMainScreen = new AdminMainScreen();
					adminMainScreen.show();
					frame.setVisible(false);
				}
			}
		});
		Image img2 = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		lblBack.setIcon(new ImageIcon(img2));
		lblBack.setForeground(Color.WHITE);
		lblBack.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBack.setBounds(10, 11, 32, 32);
		panel.add(lblBack);

		JLabel lblUpdateDetails = new JLabel("Update Account Details");
		lblUpdateDetails.setBounds(482, 11, 169, 19);
		frame.getContentPane().add(lblUpdateDetails);
		lblUpdateDetails.setForeground(new Color(204, 204, 204));
		lblUpdateDetails.setFont(new Font("Century Gothic", Font.PLAIN, 14));

		JLabel lblFullName = new JLabel("LAST NAME");
		lblFullName.setForeground(new Color(204, 204, 204));
		lblFullName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblFullName.setBounds(482, 130, 73, 19);
		frame.getContentPane().add(lblFullName);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(255, 255, 255));
		separator.setBounds(482, 182, 246, 2);
		frame.getContentPane().add(separator);

		lastNameField = new JTextField();
		lastNameField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lastNameField.setForeground(new Color(255, 255, 255));
		lastNameField.setBackground(new Color(36, 47, 65));
		lastNameField.setBorder(null);
		lastNameField.setBounds(482, 160, 246, 20);
		lastNameField.setDisabledTextColor(new Color(204, 204, 204));
		frame.getContentPane().add(lastNameField);
		lastNameField.setColumns(10);

		JLabel lblFirstName = new JLabel("FIRST NAME");
		lblFirstName.setForeground(new Color(204, 204, 204));
		lblFirstName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblFirstName.setBounds(482, 51, 76, 19);
		frame.getContentPane().add(lblFirstName);

		firstNameField = new JTextField();
		firstNameField.setForeground(Color.WHITE);
		firstNameField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		firstNameField.setDisabledTextColor(new Color(204, 204, 204));
		firstNameField.setColumns(10);
		firstNameField.setBorder(null);
		firstNameField.setBackground(new Color(36, 47, 65));
		firstNameField.setBounds(482, 81, 246, 20);
		frame.getContentPane().add(firstNameField);

		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.WHITE);
		separator_4.setBounds(482, 103, 246, 2);
		frame.getContentPane().add(separator_4);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(482, 248, 246, 20);
		frame.getContentPane().add(passwordField);
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		passwordField.setBorder(null);
		passwordField.setBackground(new Color(36, 47, 65));

		JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(482, 332, 246, 20);
		frame.getContentPane().add(confirmPasswordField);
		confirmPasswordField.setForeground(new Color(255, 255, 255));
		confirmPasswordField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		confirmPasswordField.setBorder(null);
		confirmPasswordField.setBackground(new Color(36, 47, 65));

		JLabel lblConfirmPassword = new JLabel("CONFIRM PASSWORD");
		lblConfirmPassword.setForeground(new Color(204, 204, 204));
		lblConfirmPassword.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblConfirmPassword.setBounds(482, 302, 144, 19);
		frame.getContentPane().add(lblConfirmPassword);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(482, 270, 246, 2);
		frame.getContentPane().add(separator_1);
		separator_1.setForeground(Color.WHITE);

		JLabel lblPassword = new JLabel("NEW PASSWORD");
		lblPassword.setBounds(482, 218, 109, 19);
		frame.getContentPane().add(lblPassword);
		lblPassword.setForeground(new Color(204, 204, 204));
		lblPassword.setFont(new Font("Century Gothic", Font.BOLD, 14));

		JPanel panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panel_1.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				panel_1.setBackground(new Color(97, 212, 195));
			}
			
			/**
			 * Validation is done of the fields to ensure that the correct data is entered.
			 * Fields cannot be empty, and names cannot have numbers in them.
			 */

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if (firstNameField.getText().equals("") || lastNameField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Fields: First Name and Last Name MUST be completed!");
					} else if (!firstNameField.getText().matches("[A-Z][a-zA-Z]*")
							|| !lastNameField.getText().matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
						JOptionPane.showMessageDialog(null, "Please enter a valid name.");
					}
					
					/**
					 * This is check if the confirmed password is equal to the new password.
					 */

					else if (!String.valueOf(confirmPasswordField.getPassword()).equals("")
							|| !passwordField.getText().equals("")) {
						if (String.valueOf(confirmPasswordField.getPassword()).equals(passwordField.getText())) {
							
							/**
							 * Make a connection to database.
							 * Construct the query. Query updates the user's details in the database.
							 * Use a prepared statement to setup query.
							 * Set the firstName, lastName, and password in the query.
							 * Execute the query update.
							 */

							connection = dbConnection.dbConnector();
							String query = "UPDATE User SET First_Name = ?, Last_Name = ?, Password = ? WHERE Email = ?";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, firstNameField.getText());
							pst.setString(2, lastNameField.getText());
							pst.setString(3, passwordField.getText());
							pst.setString(4, primaryKey);
							pst.executeUpdate();
							pst.close();
							connection.close();

							JOptionPane.showMessageDialog(null, "Details Updated!"); //Inform user that their details have been updated.

							goBackToMain(); //Go back to user main screen.

						} else {
							JOptionPane.showMessageDialog(null, "Passwords do not match. Try again!"); //If password's don't match, inform the user.
						}
					} else {
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query updates the user's first name and last name.
						 * Use a prepared statement to setup query.
						 * Set the firstName and lastName in the query.
						 * Execute the query update.
						 */
						
						connection = dbConnection.dbConnector();
						String query = "UPDATE User SET First_Name = ?, Last_Name = ? WHERE Email = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, firstNameField.getText());
						pst.setString(2, lastNameField.getText());
						pst.setString(3, primaryKey);
						pst.executeUpdate();
						pst.close();
						connection.close();

						JOptionPane.showMessageDialog(null, "Details Updated!"); //Inform user that their details have been updated.

						goBackToMain(); //Go back to user main screen.
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
		panel_1.setBounds(482, 391, 246, 40);
		frame.getContentPane().add(panel_1);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setLayout(null);

		JLabel lblUpdate = new JLabel("Update");
		lblUpdate.setForeground(new Color(255, 255, 255));
		lblUpdate.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblUpdate.setBounds(96, 10, 53, 19);
		panel_1.add(lblUpdate);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(482, 354, 246, 2);
		frame.getContentPane().add(separator_3);
	}
	
	/**
	 * This method is used to setup the first name and last name field.
	 * Ensuring the fields already contains the user's first name and last name.
	 */

	private void setDetails() {

		try {

			primaryKey = LoginScreen.getEmail(); //Get the current user's primary key.
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets the user's details.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query = "SELECT * FROM User WHERE Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, primaryKey);

			ResultSet rs = pst.executeQuery();
			
			/**
			 * While there are results, set the first name and last name fields, to the values found in the table.
			 */

			while (rs.next()) {
				firstNameField.setText(rs.getString("First_Name"));
				lastNameField.setText(rs.getString("Last_Name"));
			}
			
			/**
			 * Close the result set, prepared statement and connection.
			 */

			rs.close();
			pst.close();
			connection.close();

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

	}
	
	/**
	 * This method is used to find the userType that is using this screen.
	 */

	private void findUserType() {
		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets the user's details.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT * FROM User WHERE Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, primaryKey);

			ResultSet rs = pst.executeQuery();
			
			/**
			 * While there are results, get the user type and set it to string type.
			 */

			while (rs.next()) {
				String type = rs.getString("User_Type");
				
				/**
				 * Check what user type is obtained from the table, and set the string UserType to that value.
				 */

				if (type.equals("Customer")) {
					userType = "Customer";
				}
				if (type.equals("Seller")) {
					userType = "Seller";
				}
				if (type.equals("Admin")) {
					userType = "Admin";
				}
			}
			
			/**
			 * Close the result set, prepared statement and connection.
			 */
			
			rs.close();
			pst.close();
			connection.close();

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

	}
	
	/**
	 * This method is used to determine which main screen to go back to. (customer, seller, or admin).
	 * This depends on the user type.
	 */

	private void goBackToMain() {
		
		/**
		 * Create all customer, seller and admin, main screen objects.
		 */

		CustomerMainScreen customerMainScreen = new CustomerMainScreen();
		SellerMainScreen sellerMainScreen = new SellerMainScreen();
		AdminMainScreen adminMainScreen = new AdminMainScreen();
		
		/**
		 * Check what userType, and call the show method on the corresponding main screen object.
		 * Also set the welcome text to reflect the updates ones in the first name and last name field.
		 */

		if (userType == "Customer") {
			customerMainScreen.show();
			frame.setVisible(false);
			customerMainScreen.setWelcomeText("Welcome, " + firstNameField.getText() + " " + lastNameField.getText());
		} else if (userType == "Seller") {
			sellerMainScreen.show();
			frame.setVisible(false);
			sellerMainScreen.setWelcomeText("Welcome, " + firstNameField.getText() + " " + lastNameField.getText());
		} else {
			adminMainScreen.show();
			frame.setVisible(false);
			adminMainScreen.setWelcomeText("Welcome, " + firstNameField.getText() + " " + lastNameField.getText());
		}
	}
	
	/**
	 * This method is used to open and view this window.
	 */

	public void show() {
		frame.setVisible(true);
	}
}
