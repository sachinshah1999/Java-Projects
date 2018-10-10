package customerGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import database.dbConnection;
import implementationClasses.Customer;
import userGUI.LoginScreen;
import userGUI.UserSettingsScreen;

/**
 * This application window class, contains the customer's main screen.
 * From here the customer can navigate to other customer function screens or log out or purchase a prime membership.
 * @author Sachin
 *
 */

public class CustomerMainScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the customer.
	private JLabel lblWelcomeCustomer; //Used to set the welcome text
	private JLabel lblBalance; //Label to show the customer's current account balance
	private JLabel lblPrimeMember; //Label to indicate whether the customer is a prime member of not.
	private Connection connection; //Used to provide connection to the database.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerMainScreen window = new CustomerMainScreen();
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
	public CustomerMainScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Customer Dashboard");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(1082, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(0, 0, 1073, 45);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		lblBalance = new JLabel("\u00A3: 100.0", SwingConstants.CENTER);
		lblBalance.setForeground(Color.WHITE);
		lblBalance.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBalance.setBounds(960, 11, 107, 23);
		panel.add(lblBalance);
		
		/**
		 * When panel is clicked, the customer is redirected to login screen.
		 * Get the singleton instance of the customer class, and call the logout method.
		 */
		
		JPanel panel_5 = new JPanel();
		panel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				Customer.getInstance().logout();
			}
		});
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(36, 47, 65));
		panel_5.setBounds(0, 0, 69, 45);
		panel.add(panel_5);
		
		JLabel label = new JLabel("Logout");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label.setBounds(12, 13, 45, 19);
		panel_5.add(label);
		
		lblPrimeMember = new JLabel("Prime member: No", SwingConstants.CENTER);
		lblPrimeMember.addMouseListener(new MouseAdapter() {
			
			/**
			 * When the prime status text is clicked the customer is prompted with an options dialog;
			 * Asking them is they want to purchase a prime subscription for £50. 
			 * Text status changes to "Prime member: Yes" if they press yes, else it stays as "Prime member: No"
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(lblPrimeMember.getText() == "Prime member: No") { //If they are not a prime member.
					
					/**
					 * Create an options dialog to confirm if the customer wants to purchase a prime subscription.
					 */
				
				int n = JOptionPane.showOptionDialog(null,
						"Are you sure you want to purchase a prime member subscription? \n Cost : £50",
						"Confirm Purchase", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, null ,null);
				
				if(n == 0) { //If they have selected yes
					
					double currentBalance = Double.parseDouble(lblBalance.getText().replaceAll("£: ", "")); //Get there current balance.
					
					/**
					 * Call the verify funds method from the dbConnection class. 
					 * If customer has the funds, deduct £50 from the customers balance.
					 * Set the prime status text to inform the customer, they are a prime member now.
					 */
					
					if(dbConnection.verifyFunds(50.0)) {
						lblBalance.setText("£: " + round((currentBalance - 50.0),2));
						lblPrimeMember.setText("Prime member: Yes");
						
						if(Customer.getInstance().purchasePrime()) { //Call the purchase prime method from the customer class.
							
							JOptionPane.showMessageDialog(null, "Congratulations, you have successfully purchased a prime membership. \n "
									+ "Your purchases will now all be half price!"); //Inform the customer of successful purchase

						}
						
					} else {
						JOptionPane.showMessageDialog(null, "Insufficient funds to purchase a membership!"); //Inform the customer if they Insufficient funds to purchase prime.
					}
					
				}
				} else if (lblPrimeMember.getText() == "Prime member: Yes") { //If the customer is a prime member, and they clicked on the prime status text.
					JOptionPane.showMessageDialog(null, "You are a prime member. All your purchases will be half price!"); //Inform customer that they are prime.
				}
			}
		});
		lblPrimeMember.setForeground(Color.WHITE);
		lblPrimeMember.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblPrimeMember.setBounds(452, 11, 168, 23);
		panel.add(lblPrimeMember);

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
			 * When this panel is clicked, the customer is redirected to the DisplayProductsScreen window.
			 * Where they can purchase products.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose(); //Get rid of the frame
				DisplayProductsScreen products = new DisplayProductsScreen();  //Create a DisplayProductsScreen object
				products.show(); //Call the show method on the object, to set the frame visible.
			}
		});
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setBounds(24, 192, 232, 279);
		frame.getContentPane().add(panel_1);

		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/search.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(30, 44, 160, 160);
		panel_1.add(lblNewLabel);

		JLabel lblSearchForProducts = new JLabel("Search for products");
		lblSearchForProducts.setForeground(Color.WHITE);
		lblSearchForProducts.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblSearchForProducts.setBounds(30, 224, 172, 23);
		panel_1.add(lblSearchForProducts);

		lblWelcomeCustomer = new JLabel("Welcome, Sachin Shah", SwingConstants.CENTER);
		lblWelcomeCustomer.setForeground(new Color(204, 204, 204));
		lblWelcomeCustomer.setFont(new Font("Century Gothic", Font.PLAIN, 25));
		lblWelcomeCustomer.setBounds(24, 101, 1015, 32);
		frame.getContentPane().add(lblWelcomeCustomer);

		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panel_2.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				panel_2.setBackground(new Color(97, 212, 195));
			}
			
			/**
			 * When this panel is clicked, the customer will be redirected to the settings page.
			 * Where they can change their first name, last name and password.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				UserSettingsScreen userSettingsScreen = new UserSettingsScreen(); //Create a UserSettingsScreen object
				userSettingsScreen.show(); //Call the show method on the object, to set the frame visible.
				frame.setVisible(false); //Set the CustomerMainScreen frame visibiity to false.
			}
		});
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(97, 212, 195));
		panel_2.setBounds(807, 192, 232, 279);
		frame.getContentPane().add(panel_2);

		JLabel label_4 = new JLabel("");
		Image img3 = new ImageIcon(this.getClass().getResource("/settingsSmall.png")).getImage();
		label_4.setIcon(new ImageIcon(img3));

		label_4.setBounds(36, 44, 160, 160);
		panel_2.add(label_4);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setForeground(Color.WHITE);
		lblSettings.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblSettings.setBounds(82, 224, 68, 23);
		panel_2.add(lblSettings);

		JPanel panel_3 = new JPanel();
		panel_3.addMouseListener(new MouseAdapter() {
			
			/**
			 * When this frame is clicked, the customer is redirected to the WishListScreen window.
			 * Here they can view all the products they have in their wish list.
			 * They can also choose to purchase products from here.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose(); //Dispose the frame
				WishListScreen wishlist = new WishListScreen(); //Create a WishListScreen object.
				wishlist.show(); //Set the CustomerMainScreen frame visibiity to false.
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				panel_3.setBackground(new Color(25, 157, 141));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				panel_3.setBackground(new Color(97, 212, 195));
			}
		});
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(97, 212, 195));
		panel_3.setBounds(280, 192, 232, 279);
		frame.getContentPane().add(panel_3);

		JLabel label_2 = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/ListWish.png")).getImage();
		label_2.setIcon(new ImageIcon(img2));
		label_2.setBounds(36, 46, 160, 160);
		label_2.setForeground(Color.BLACK);
		panel_3.add(label_2);

		JLabel lblWishList = new JLabel("Wish list");
		lblWishList.setForeground(Color.WHITE);
		lblWishList.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblWishList.setBounds(82, 224, 68, 23);
		panel_3.add(lblWishList);

		JPanel panel_4 = new JPanel();
		panel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panel_4.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				panel_4.setBackground(new Color(97, 212, 195));
			}
			
			/**
			 * When this panel is clicked, the customer is redirected to the PurchasedProductsScreen window.
			 * Here they can view all their purchased products, and add reviews to these products.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose(); 
				PurchasedProductsScreen purchased = new PurchasedProductsScreen();
				purchased.show();
			}
		});
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(97, 212, 195));
		panel_4.setBounds(535, 192, 232, 279);
		frame.getContentPane().add(panel_4);

		JLabel label_3 = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/purchased.png")).getImage();
		label_3.setIcon(new ImageIcon(img4));
		label_3.setBounds(36, 44, 160, 160);
		panel_4.add(label_3);

		JLabel lblPurchasedProducts = new JLabel("Purchased products");
		lblPurchasedProducts.setForeground(Color.WHITE);
		lblPurchasedProducts.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblPurchasedProducts.setBounds(28, 224, 176, 23);
		panel_4.add(lblPurchasedProducts);
	}
	
	/**
	 * This method is used to setup lblWelcomeCustomer text.
	 * Set the customer's balance.
	 * Set the customer's prime status.
	 */

	private void setDetails() {

		try {
			
			String primaryKey = LoginScreen.getEmail(); //Get the current customer's primary key.
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the current logged in customer's first name and last.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query.
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT * FROM User WHERE Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, primaryKey);

			ResultSet rs = pst.executeQuery();
			
			/**
			 * While there are results, set the text for the WelcomeCustomer label.
			 * Set the first name and last name to the customer instance.
			 */

			while (rs.next()) {
				lblWelcomeCustomer.setText("Welcome, " + rs.getString("First_Name") + " " + rs.getString("Last_Name"));
				Customer.getInstance().setFirstName(rs.getString("First_Name"));
				Customer.getInstance().setLastName(rs.getString("Last_Name"));
			}
			
			/**
			 * Close the result set, prepared statement and connection.
			 */
			
			rs.close();
			pst.close();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets the current logged in customer's balance.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query.
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query2 = "SELECT * FROM Non_Admin WHERE Email = ?";
			PreparedStatement pst2 = connection.prepareStatement(query2);
			pst2.setString(1, primaryKey);

			ResultSet rs2 = pst2.executeQuery();
			
			/**
			 * While there are results, set the balance in the lblBalance label.
			 */

			while (rs2.next()) {
				lblBalance.setText("£: " + round(rs2.getDouble("Balance"), 2));
			}
			
			/**
			 * Close the result set, prepared statement and connection.
			 */
			
			rs2.close();
			pst2.close();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets the current logged in customer's prime status.
			 * Use a prepared statement to setup query.
			 * Set the primaryKey in the query.
			 * Execute the query.
			 */
			
			connection = dbConnection.dbConnector();
			String query3 = "SELECT * From Customer WHERE Email = ? AND Prime = 'Yes'";
			PreparedStatement pst3 = connection.prepareStatement(query3);
			pst3.setString(1, primaryKey);
			
			ResultSet rs3 = pst3.executeQuery();
			
			/**
			 * if there are results, meaning if there exists a record where the current logged in customer is prime, set that in lblPrimeMember label.
			 */
			
			if(rs3.next()) {
				lblPrimeMember.setText("Prime member: Yes");
			} else {
				lblPrimeMember.setText("Prime member: No");
			}
			
			/**
			 * Close the result set, prepared statement and connection.
			 */
			
			rs3.close();
			pst3.close();
			connection.close();
			

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

	}
	
	/**
	 * This method is used to set the customer's main screen welcome text. 
	 * This will be called in the userSettingsScreen, when the first or last name is updated.
	 * @param s - The text that is going to be set as the welcome text.
	 */
	
	public void setWelcomeText(String s) {
		lblWelcomeCustomer.setText(s);
	}
	
	/**
	 * This is method is specifically for JUnit testing.
	 * More specifically to test for successful GUI object creation.
	 * @return
	 */
	
	public String getWelcomeText() {
		return lblWelcomeCustomer.getText();
	}
	
	/**
	 * This method is used to open and view this window.
	 */

	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Method used to round a double value to 2 decimal places. 
	 * Mainly used to round the custome's balance.
	 * @param value - The value you want to round
	 * @param places - How places you want to round to.
	 * @return - Returns the rounded value.
	 */

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	/**
	 * Used to determine, whether or not the customer currently logged in is prime.
	 * This will be used to determine if the customer purchasing the products is prime.
	 * @return
	 */
	
	public String isPrime() {
		return lblPrimeMember.getText();
	}
}
