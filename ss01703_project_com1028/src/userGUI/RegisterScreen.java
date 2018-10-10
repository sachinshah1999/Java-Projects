package userGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import database.dbConnection;
import implementationClasses.Admin;
import implementationClasses.Customer;
import implementationClasses.Seller;

/**
 * This application window class, allows the user to register an account.
 * User can also navigate back to login screen.
 * @author Sachin
 *
 */

public class RegisterScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the user.
	private LoginScreen loginScreen; //Object reference to the LoginScreen.
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterScreen window = new RegisterScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//TODO Model is a database in a MVC

	/**
	 * Create the application.
	 */
	public RegisterScreen() {
		super();
		this.initialize();
		this.loginScreen = new LoginScreen(); //initialise the customer object.
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Register");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(815, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(0, 0, 405, 523);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Amazon Application");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(111, 64, 182, 23);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/shoppingcart.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setForeground(new Color(204, 204, 204));
		label.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		label.setBounds(74, 143, 256, 256);
		panel.add(label);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.WHITE);
		separator_6.setBounds(37, 410, 331, 9);
		panel.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.WHITE);
		separator_7.setBounds(79, 446, 246, 2);
		panel.add(separator_7);
		
		JLabel lblSignInOr = new JLabel("Register");
		lblSignInOr.setBounds(482, 11, 53, 19);
		frame.getContentPane().add(lblSignInOr);
		lblSignInOr.setForeground(new Color(204, 204, 204));
		lblSignInOr.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		
		JLabel lblFullName = new JLabel("LAST NAME");
		lblFullName.setForeground(new Color(204, 204, 204));
		lblFullName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblFullName.setBounds(482, 130, 73, 19);
		frame.getContentPane().add(lblFullName);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(255, 255, 255));
		separator.setBounds(482, 182, 246, 2);
		frame.getContentPane().add(separator);
		
		JTextField lastNameField = new JTextField();
		lastNameField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lastNameField.setForeground(new Color(255, 255, 255));
		lastNameField.setBackground(new Color(36, 47, 65));
		lastNameField.setBorder(null);
		lastNameField.setBounds(482, 160, 246, 20);
		lastNameField.setDisabledTextColor(new Color(204, 204, 204));;
		frame.getContentPane().add(lastNameField);
		lastNameField.setColumns(10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(482, 270, 246, 2);
		frame.getContentPane().add(separator_2);
		
		JTextField emailField = new JTextField();
		emailField.setForeground(Color.WHITE);
		emailField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		emailField.setDisabledTextColor(new Color(204, 204, 204));
		emailField.setColumns(10);
		emailField.setBorder(null);
		emailField.setBackground(new Color(36, 47, 65));
		emailField.setBounds(482, 248, 246, 20);
		frame.getContentPane().add(emailField);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setForeground(new Color(204, 204, 204));
		lblEmail.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblEmail.setBounds(482, 218, 40, 19);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblFirstName = new JLabel("FIRST NAME");
		lblFirstName.setForeground(new Color(204, 204, 204));
		lblFirstName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblFirstName.setBounds(482, 51, 76, 19);
		frame.getContentPane().add(lblFirstName);
		
		JTextField firstNameField = new JTextField();
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
		passwordField.setBounds(482, 324, 246, 20);
		frame.getContentPane().add(passwordField);
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		passwordField.setBorder(null);
		passwordField.setBackground(new Color(36, 47, 65));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(482, 346, 246, 2);
		frame.getContentPane().add(separator_1);
		separator_1.setForeground(Color.WHITE);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(482, 294, 75, 19);
		frame.getContentPane().add(lblPassword);
		lblPassword.setForeground(new Color(204, 204, 204));
		lblPassword.setFont(new Font("Century Gothic", Font.BOLD, 14));
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(Color.WHITE);
		separator_5.setBounds(482, 422, 246, 2);
		frame.getContentPane().add(separator_5);
		
		JLabel lblUserType = new JLabel("USER TYPE");
		lblUserType.setForeground(new Color(204, 204, 204));
		lblUserType.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblUserType.setBounds(482, 370, 75, 19);
		frame.getContentPane().add(lblUserType);
		
		String[] userTypes = { "Customer", "Seller", "Admin"}; //Maybe at top with fields
		JComboBox comboBox = new JComboBox(userTypes);
		comboBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		comboBox.setBorder(null);
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(482, 400, 246, 20);
		frame.getContentPane().add(comboBox);
		
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
			 * When this panel is clicked the user is registered, and redirected to the login screen.
			 * Provided they have provided correct and valid details.
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				/**
				 * Get the first name, last name, email, password and userType from the textFields and combo box.
				 */
				
				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String email = emailField.getText();
				String password = passwordField.getText();
				String userType = comboBox.getSelectedItem().toString();
				
				try {
					
					/**
					 * If the type is customer. Call the customer register method.
					 * If the method return true, then inform the customer of successful register.
					 * Then close register screen and open login screen. 
					 */
					
				if(userType == "Customer") {
					
					if(Customer.getInstance().register(firstName, lastName, email, password)) {
						JOptionPane.showMessageDialog(null, "Successful Register!");
						frame.dispose();
						loginScreen.show();
					}
					
					/**
					 * If the type is seller. Call the seller register method.
					 * If the method return true, then inform the customer of successful register.
					 * Then close register screen and open login screen. 
					 */
					
				} else if (userType == "Seller") {
					
					if(Seller.getInstance().register(firstName, lastName, email, password)) {
						JOptionPane.showMessageDialog(null, "Successful Register!");
						frame.dispose();
						loginScreen.show();
					}
					
					/**
					 * If the type is admin. Call the admin register method.
					 * If the method return true, then inform the customer of successful register.
					 * Then close register screen and open login screen. 
					 */
					
				} else if (userType == "Admin") {
					
					if(Admin.getInstance().register(firstName, lastName, email, password)) {
						JOptionPane.showMessageDialog(null, "Successful Register!");
						frame.dispose();
						loginScreen.show();
					}
				}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		panel_1.setBounds(482, 459, 177, 40);
		frame.getContentPane().add(panel_1);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setLayout(null);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setForeground(new Color(255, 255, 255));
		lblRegister.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblRegister.setBounds(62, 10, 53, 19);
		panel_1.add(lblRegister);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.addMouseListener(new MouseAdapter() {
			
			/**
			 * When clicked, the login screen is opened, and the register screen is closed
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				loginScreen.show();
				frame.setVisible(false);
			}
		});
		separator_3.setBounds(669, 487, 84, 12);
		frame.getContentPane().add(separator_3);
		separator_3.setForeground(Color.WHITE);
		
		JLabel lblAlreadyAUser = new JLabel("Already a user");
		lblAlreadyAUser.addMouseListener(new MouseAdapter() {
			
			/**
			 * When clicked, the login screen is opened, and the register screen is closed
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				loginScreen.show();
				frame.setVisible(false);
			}
		});
		lblAlreadyAUser.setBounds(669, 469, 84, 16);
		frame.getContentPane().add(lblAlreadyAUser);
		lblAlreadyAUser.setForeground(new Color(204, 204, 204));
		lblAlreadyAUser.setFont(new Font("Century Gothic", Font.PLAIN, 12));
	}
	
	/**
	 * This method is used to open and view this window.
	 */
	
	public void show() {
		frame.setVisible(true);
	}
}
