package userGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import database.dbConnection;
import implementationClasses.Customer;
import implementationClasses.User;
import sellerGUI.SellerMainScreen;

/**
 * This application window class, allows a user to login.
 * It also allows users to navigate to the register screen.
 * @author Sachin
 *
 */

public class LoginScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the user.
	private static String email; //Static reference to the email address used to login, which will be used as a primary key in other classes.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen window = new LoginScreen();
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
	public LoginScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Login");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(0, 0, 405, 528);
		frame.setSize(810, 508);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(0, 0, 405, 469);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblTitle = new JLabel("Amazon Application");
		lblTitle.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblTitle.setForeground(new Color(255, 255, 255));
		lblTitle.setBounds(111, 64, 182, 23);
		panel.add(lblTitle);

		JLabel logo = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/shoppingcart.png")).getImage();
		logo.setIcon(new ImageIcon(img));
		logo.setForeground(new Color(204, 204, 204));
		logo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		logo.setBounds(74, 143, 256, 256);
		panel.add(logo);

		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.WHITE);
		separator_6.setBounds(37, 410, 331, 9);
		panel.add(separator_6);

		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.WHITE);
		separator_7.setBounds(79, 446, 246, 2);
		panel.add(separator_7);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(486, 67, 36, 19);
		frame.getContentPane().add(lblLogin);
		lblLogin.setForeground(new Color(204, 204, 204));
		lblLogin.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		;

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(486, 178, 246, 2);
		frame.getContentPane().add(separator_2);

		JTextField emailField = new JTextField();
		// emailField.setCaretColor(new Color(204, 204, 204));
		emailField.setForeground(Color.WHITE);
		emailField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		emailField.setColumns(10);
		emailField.setBorder(null);
		emailField.setBackground(new Color(36, 47, 65));
		emailField.setBounds(486, 156, 246, 20);
		frame.getContentPane().add(emailField);

		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setForeground(new Color(204, 204, 204));
		lblEmail.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblEmail.setBounds(486, 126, 40, 19);
		frame.getContentPane().add(lblEmail);

		JPasswordField passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				passwordField.selectAll();
			}
		});
		passwordField.setToolTipText("Password");
		passwordField.setBounds(486, 232, 246, 20);
		frame.getContentPane().add(passwordField);
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		passwordField.setBorder(null);
		passwordField.setBackground(new Color(36, 47, 65));

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(486, 254, 246, 2);
		frame.getContentPane().add(separator_1);
		separator_1.setForeground(Color.WHITE);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(486, 202, 75, 19);
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
			@Override
			public void mouseClicked(MouseEvent e) {

				email = emailField.getText(); //Set the email to the email field text.
				
				/**
				 * Call the login method from the dbConnection class.
				 * Pass parameters email and password.
				 * If successful, dispose the frame.
				 * What frame to open is decided by the login method.
				 */
				
				if(dbConnection.login(emailField.getText(), passwordField.getText())) {
					frame.dispose();
				}
				
				/*try {

					if (emailField.getText().equals("") || passwordField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please enter an email address and password.");
					} else {
						String query = "SELECT * FROM User WHERE Email = ? AND Password = ?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, emailField.getText());
						pst.setString(2, passwordField.getText());

						ResultSet rs = pst.executeQuery();
						int count = 0;
						while (rs.next()) {
							count += 1;
						}
						if (count == 1) {
							
							PreparedStatement pst2 = connection.prepareStatement("SELECT * FROM User WHERE Email = ?");
							pst2.setString(1, emailField.getText());
							ResultSet rs2 = pst2.executeQuery();

							while (rs2.next()) {

								String type = rs2.getString("User_Type");

								if (type.equals("Customer")) {
									JOptionPane.showMessageDialog(null, "Successful Login!");
									CustomerMainScreen customerMainScreen = new CustomerMainScreen();
									customerMainScreen.show();
									frame.setVisible(false);
								}
								if (type.equals("Seller")) {

									PreparedStatement pst3 = connection
											.prepareStatement("SELECT * FROM Seller WHERE Email = ? AND Banned = 'No'");
									pst3.setString(1, emailField.getText());
									ResultSet rs3 = pst3.executeQuery();

									if (rs3.next()) {
										JOptionPane.showMessageDialog(null, "Successful Login!");
										SellerMainScreen sellerMainScreen = new SellerMainScreen();
										sellerMainScreen.show();
										frame.setVisible(false);
									} else {
										
										JOptionPane.showMessageDialog(null, "Your account has been banned due to too much negative feedback on your products.");

									}
								}
								if (type.equals("Admin")) {
									JOptionPane.showMessageDialog(null, "Successful Login!");
									AdminMainScreen adminMainScreen = new AdminMainScreen();
									adminMainScreen.show();
									frame.setVisible(false);
								}

							}
							pst2.close();
							rs2.close();

						} else {
							JOptionPane.showMessageDialog(null,
									"Incorrect email address or password, Please try again!");
						}

						rs.close();
						pst.close();

					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}*/
			}

		});

		panel_1.setBounds(486, 328, 177, 40);
		frame.getContentPane().add(panel_1);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setLayout(null);

		JLabel lblLoginButton = new JLabel("Login");
		lblLoginButton.setForeground(new Color(255, 255, 255));
		lblLoginButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblLoginButton.setBounds(70, 10, 36, 19);
		panel_1.add(lblLoginButton);

		JSeparator separator_3 = new JSeparator();
		separator_3.addMouseListener(new MouseAdapter() {
			
			/**
			 * When clicked, the register screen is opened and login screen is closed.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterScreen registerScreen = new RegisterScreen();
				registerScreen.show();
				frame.setVisible(false);
			}
		});
		separator_3.setBounds(673, 356, 86, 12);
		frame.getContentPane().add(separator_3);
		separator_3.setForeground(Color.WHITE);

		JLabel lblNotRegistered = new JLabel("Not registered");
		
		/**
		 * When clicked, the register screen is opened and login screen is closed.
		 */
		
		lblNotRegistered.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterScreen registerScreen = new RegisterScreen();
				registerScreen.show();
				frame.setVisible(false);

			}
		});
		lblNotRegistered.setBounds(673, 338, 86, 16);
		frame.getContentPane().add(lblNotRegistered);
		lblNotRegistered.setForeground(new Color(204, 204, 204));
		lblNotRegistered.setFont(new Font("Century Gothic", Font.PLAIN, 12));

	}
	
	/**
	 * Static getter to get the email. This will be used by other classes to get the email. 
	 * The email is the primary key for the user. Which will be used to query the database.
	 * @return - Returns the email.
	 */

	public static String getEmail() {
		return email;
	}
	
	public static void setEmail(String s) {
		email = s;
	}
	
	/**
	 * This method is used to open and view this window.
	 */
	
	public void show() {
		frame.setVisible(true);
	}
}
