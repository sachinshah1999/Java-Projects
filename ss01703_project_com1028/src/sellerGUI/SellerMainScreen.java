package sellerGUI;

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
import implementationClasses.Seller;
import userGUI.LoginScreen;
import userGUI.UserSettingsScreen;

/**
 * This application window class, contains the seller's main screen.
 * From here the seller can navigate to other seller function screens or log out.
 * @author Sachin
 */

public class SellerMainScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the seller.
	private JLabel lblBalance; //Label to show the seller's current account balance
	private JLabel lblWelcomeSeller; //Used to set the welcome text
	private Connection connection; //Used to provide connection to the database.
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellerMainScreen window = new SellerMainScreen();
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
	public SellerMainScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Seller Dashboard");
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
		
		JPanel panel_5 = new JPanel();
		panel_5.addMouseListener(new MouseAdapter() {
			
			/**
			 * When panel is clicked, the seller is redirected to login screen.
			 * Get the singleton instance of the seller class, and call the logout method.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				Seller.getInstance().logout();
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
		
		lblWelcomeSeller = new JLabel("Welcome, Sachin Shah", SwingConstants.CENTER);
		lblWelcomeSeller.setForeground(new Color(204, 204, 204));
		lblWelcomeSeller.setFont(new Font("Century Gothic", Font.PLAIN, 25));
		lblWelcomeSeller.setBounds(24, 101, 1015, 32);
		frame.getContentPane().add(lblWelcomeSeller);
		
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
			 * When this panel is clicked, the seller is redirected to the AddProductScreen window.
			 * Where they can add products to the market.
			 */

			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				AddProductScreen addProduct = new AddProductScreen();
				addProduct.show();
				
			}
		});
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setBounds(24, 192, 232, 279);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/addProduct.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(30, 44, 160, 160);
		panel_1.add(lblNewLabel);
		
		JLabel lblSearchForProducts = new JLabel("Add product");
		lblSearchForProducts.setForeground(Color.WHITE);
		lblSearchForProducts.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblSearchForProducts.setBounds(58, 224, 115, 23);
		panel_1.add(lblSearchForProducts);
		
		JPanel panel_3 = new JPanel();
		panel_3.addMouseListener(new MouseAdapter() {
			
			/**
			 * When this panel is clicked, the seller is redirected to the CurrentListingsScreen window.
			 * Where they can view their products currently listed on the market.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				CurrentListingsScreen listings = new CurrentListingsScreen();
				listings.show();
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
		Image img2 = new ImageIcon(this.getClass().getResource("/currentListings.png")).getImage();
		label_2.setIcon(new ImageIcon(img2));
		label_2.setBounds(36, 46, 160, 160);
		label_2.setForeground(Color.BLACK);
		panel_3.add(label_2);

		JLabel lblWishList = new JLabel("Current listings");
		lblWishList.setForeground(Color.WHITE);
		lblWishList.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblWishList.setBounds(54, 224, 124, 23);
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
			 * When this panel is clicked, the seller is redirected to the SoldProductsScreen window.
			 * Where they can view products that have been sold.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				SoldProductsScreen sold = new SoldProductsScreen();
				sold.show();
				
			}
		});
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(97, 212, 195));
		panel_4.setBounds(535, 192, 232, 279);
		frame.getContentPane().add(panel_4);

		JLabel label_3 = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/sold.png")).getImage();
		label_3.setIcon(new ImageIcon(img4));
		label_3.setBounds(36, 44, 160, 160);
		panel_4.add(label_3);

		JLabel lblPurchasedProducts = new JLabel("Sold products");
		lblPurchasedProducts.setForeground(Color.WHITE);
		lblPurchasedProducts.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblPurchasedProducts.setBounds(56, 224, 119, 23);
		panel_4.add(lblPurchasedProducts);
		
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
			 * When this panel is clicked, the seller is redirected to the UserSettingsScreen window.
			 * Where they can change their first name, last name and password.
			 */
			
			@Override
			public void mouseClicked(MouseEvent e) {
				UserSettingsScreen userSettingsScreen = new UserSettingsScreen();
				userSettingsScreen.show();
				frame.setVisible(false);
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
	}
	
	/**
	 * This method is used to setup lblWelcomeSeller text and seller's balance.
	 */
	
	private void setDetails() {

		try {

			String primaryKey = LoginScreen.getEmail(); //Get the current seller's primary key.

			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the current logged in seller's first name and last.
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
			 * While there are results, set the text for the welcome seller label.
			 * Set the first name and last name to the seller instances.
			 */

			while (rs.next()) {
				lblWelcomeSeller.setText("Welcome, " + rs.getString("First_Name") + " " + rs.getString("Last_Name"));
				Seller.getInstance().setFirstName(rs.getString("First_Name"));
				Seller.getInstance().setLastName(rs.getString("Last_Name"));
			}
			
			/**
			 * Close the result set, prepared statement and connection.
			 */
			
			rs.close();
			pst.close();
			connection.close();
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets the current logged in seller's balance.
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
			

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

	}
	
	/**
	 * This method is used to set the seller's main screen welcome text. 
	 * This will be called in the userSettingsScreen, when the first or last name is updated.
	 * @param s - The text that is going to be set as the welcome text.
	 */
	
	public void setWelcomeText(String s) {
		lblWelcomeSeller.setText(s);
	}
	
	/**
	 * This is method is specifically for JUnit testing.
	 * More specifically to test for successful GUI object creation.
	 * @return
	 */
	
	public String getWelcomeText() {
		return lblWelcomeSeller.getText();
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
	
	

}
