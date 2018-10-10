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

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.dbConnection;
import implementationClasses.Customer;
import userGUI.LoginScreen;

/**
 * This application window class, allows the customer to view products on the market.
 * Products can be bought, added to the their wish list or refined by a certain criteria to better match the customers need.
 * @author Sachin
 *
 */

public class DisplayProductsScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the customer.
	private JLabel lblBalance; //Label to show the customer's current account balance
	private JList<String> productsList; // JList which contains String references to the products currently on the market
	private int productID; // The id of the product, currently selected in the JList.
	private String primaryKey; //The email of customer that is currently logged in.
	private DefaultListModel<String> productsListModel; //DefaultListModel to implement the JList.
	private Connection connection; // Used to provide connection to the database.
	private CustomerMainScreen customer; //Object reference to the customerMainScreen.
	private JLabel lblProductName; //Declared as variable for testing purposes. Used to represent the product's name.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayProductsScreen window = new DisplayProductsScreen();
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
	public DisplayProductsScreen() {
		initialize();
		setDetails();
		this.customer = new CustomerMainScreen(); //initialise the customer object.
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("Products");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(0, 0, 405, 528);
		frame.setSize(810, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(0, 0, 799, 45);
		frame.getContentPane().add(panel);

		lblBalance = new JLabel("\u00A3: 100.0", SwingConstants.CENTER);
		lblBalance.setForeground(Color.WHITE);
		lblBalance.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBalance.setBounds(706, 11, 83, 23);
		panel.add(lblBalance);
		
		/**
		 * When label is clicked, the customer is taken back to the customer main screen.
		 */

		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				customer.show();
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		lblBack.setIcon(new ImageIcon(img));
		lblBack.setForeground(Color.WHITE);
		lblBack.setForeground(Color.WHITE);
		lblBack.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBack.setBounds(10, 2, 32, 43);
		panel.add(lblBack);
		
		lblProductName = new JLabel("Name of the product", SwingConstants.CENTER);
		lblProductName.setForeground(new Color(204, 204, 204));
		lblProductName.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblProductName.setBounds(328, 56, 456, 32);
		frame.getContentPane().add(lblProductName);
		
		JLabel lblProductDesc = new JLabel("Description of the product");
		lblProductDesc.setForeground(new Color(204, 204, 204));
		lblProductDesc.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblProductDesc.setBounds(328, 150, 456, 20);
		frame.getContentPane().add(lblProductDesc);
		
		JLabel lblPrice = new JLabel("Price of the product");
		lblPrice.setForeground(new Color(204, 204, 204));
		lblPrice.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPrice.setBounds(328, 290, 269, 20);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblQuantity = new JLabel("Quantity of the product");
		lblQuantity.setForeground(new Color(204, 204, 204));
		lblQuantity.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblQuantity.setBounds(328, 321, 269, 20);
		frame.getContentPane().add(lblQuantity);

		JLabel lblProductsOnMarket = new JLabel("Products on market", SwingConstants.CENTER);
		lblProductsOnMarket.setForeground(new Color(204, 204, 204));
		lblProductsOnMarket.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblProductsOnMarket.setBounds(10, 56, 288, 26);
		frame.getContentPane().add(lblProductsOnMarket);
		
		JLabel lblProductSeller = new JLabel("Seller of the product");
		lblProductSeller.setForeground(new Color(204, 204, 204));
		lblProductSeller.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblProductSeller.setBounds(328, 352, 269, 20);
		frame.getContentPane().add(lblProductSeller);
		
		JPanel panel_2 = new JPanel();
		
		JLabel lblAddToWish = new JLabel("Add to wish list", SwingConstants.CENTER);
		lblAddToWish.setForeground(Color.WHITE);
		lblAddToWish.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblAddToWish.setBounds(10, 10, 157, 19);
		panel_2.add(lblAddToWish);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(328, 428, 456, 70);
		frame.getContentPane().add(scrollPane_1);
		
		JList<String> productReviewsList = new JList<String>();
		scrollPane_1.setViewportView(productReviewsList);
		DefaultListCellRenderer renderer2 = (DefaultListCellRenderer) productReviewsList.getCellRenderer();
		renderer2.setHorizontalAlignment(SwingConstants.CENTER);
		productReviewsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productReviewsList.setForeground(new Color(204, 204, 204));
		productReviewsList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		productReviewsList.setBackground(new Color(36, 47, 65));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 99, 288, 369);
		frame.getContentPane().add(scrollPane);

		productsList = new JList<String>();
		scrollPane.setViewportView(productsList);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) productsList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * productsList has a selection listener, to determine which product
		 * the customer has currently selected. This information is used to determine the
		 * productID, and setup the product details to the right of the JList.
		 */

		productsList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				/**
				 * Check if you have selected a value and if you have, remove all the letters
				 * from the value and parse it to an integer to get the productID.
				 */

				if (productsList.getSelectedValue() != null) {
					productID = Integer.parseInt(productsList.getSelectedValue().replaceAll("\\D+", ""));
				}

				try {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query gets all the currently selected product's details. A check is made to ensure that the product has not been sold
					 * Use a prepared statement to setup query.
					 * Set the productID in the query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query = "SELECT Products.*, Sells.Sold, Sells.Email FROM Products INNER JOIN Sells ON Products.ID = Sells.ID WHERE SOLD = 'No' AND Products.ID = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, productID);
					ResultSet rs = pst.executeQuery();
					
					/**
					 * While there are results, set the product's name, description, price, quantity and seller to the appropriate labels.
					 */

					while (rs.next()) {

						lblProductName.setText(rs.getString("Name"));
						lblProductDesc.setText(rs.getString("Description"));
						lblPrice.setText("£: " + rs.getDouble("Price"));
						lblQuantity.setText("Quantity: " + rs.getInt("Quantity"));
						lblProductSeller.setText("Seller: " + rs.getString("Email"));
					}
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs.close();
					pst.close();
					connection.close();
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query checks if the currently selected product, is in the customer's wish list.
					 * Use a prepared statement to setup query.
					 * Set the primaryKey and productID in the query.
					 * Execute the query.
					 */

					connection = dbConnection.dbConnector();
					String query2 = "SELECT * FROM Wish_List WHERE ID = ? AND Email = ?";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					pst2.setInt(1, productID);
					pst2.setString(2, primaryKey);
					ResultSet rs2 = pst2.executeQuery();
					
					/**
					 * If there is a result, meaning if the product is in the wish list, give the customer the option to remove it from the wish list.
					 * Else, the product is not in the wish list, and may need to be added.
					 */

					if (rs2.next()) {
						lblAddToWish.setText("Remove from wish list");
					} else {
						lblAddToWish.setText("Add to wish list");
					}
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs2.close();
					pst2.close();
					connection.close();
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query used to get the all the product reviews of the selected product
					 * Use a prepared statement to setup query.
					 * Set the productID in the query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query3 = "SELECT Reviews.Rating, Reviews.Review, User.First_Name, User.Last_Name FROM Reviews "
							+ "INNER JOIN Products ON Products.ID = Reviews.ID "
							+ "INNER JOIN User ON Reviews.Email = User.Email " + "WHERE Products.ID = ?";
					PreparedStatement pst3 = connection.prepareStatement(query3);
					pst3.setInt(1, productID);
					ResultSet rs3 = pst3.executeQuery();
					
					DefaultListModel<String> productReviews = new DefaultListModel<String>(); //Create the DefaultListModel to contain the product reviews.

					productReviews.removeAllElements(); //Remove any elements from the DLM if there are any.
					
					/**
					 * While there are results, create a string reference to the product rating (out of 5), the actual review and the customer's name, and add it to the DLM.
					 * Set the model for the productReviewsList to the productReviews DefaultListModel.
					 * Set the selected index to the first item in the list.
					 */

					while (rs3.next()) {
						productReviews.addElement("[" + rs3.getInt("Rating") + "/5]" + " | \"" + rs3.getString("Review") + "\" | "
								+ rs3.getString("First_Name") + " " + rs3.getString("Last_Name"));

					}
					productReviewsList.setModel(productReviews);
					productReviewsList.setSelectedIndex(0);
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs3.close();
					pst3.close();
					connection.close();

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}

		}

		);

		productsList.setBackground(new Color(36, 47, 65));
		productsList.setForeground(new Color(204, 204, 204));
		productsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productsList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.WHITE);
		separator.setBounds(308, 56, 20, 442);
		frame.getContentPane().add(separator);

		JLabel lblDescription = new JLabel("Description", SwingConstants.CENTER);
		lblDescription.setForeground(new Color(204, 204, 204));
		lblDescription.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblDescription.setBounds(328, 119, 83, 20);
		frame.getContentPane().add(lblDescription);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(328, 99, 456, 9);
		frame.getContentPane().add(separator_1);

		JLabel lblDetails = new JLabel("Details", SwingConstants.CENTER);
		lblDetails.setForeground(new Color(204, 204, 204));
		lblDetails.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblDetails.setBounds(328, 259, 49, 20);
		frame.getContentPane().add(lblDetails);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(328, 239, 456, 9);
		frame.getContentPane().add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(328, 383, 456, 9);
		frame.getContentPane().add(separator_3);

		JPanel panel_1 = new JPanel();

		
		/**
		 * This spinner is used to choose the amount of the product the customer wants to purchase.
		 */
		
		SpinnerModel sm = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
		JSpinner amount = new JSpinner(sm);

		amount.setFont(new Font("SansSerif", Font.PLAIN, 11));
		amount.setBounds(129, 11, 38, 20);
		panel_1.add(amount);

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
			 * When this panel is clicked, the customer purchases the product.
			 * There are a-lot of background database tasks than occur. 
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {

				double price = Double.parseDouble(lblPrice.getText().replace("£: ", "")); // price of product
				double balance = Double.parseDouble(lblBalance.getText().replaceAll("£: ", "")); // current balance of the customer
				int quantity = Integer.parseInt(lblQuantity.getText().replaceAll("Quantity: ", "")); // quantity of the product
				int amountToPurchase = (int) amount.getValue(); // Value of spinner
				
				int newQuantity = quantity - amountToPurchase; // The new quantity of the product, after deducting the amount the customer wants to purchase.
				
				
				/**
				 * The double amountPurchased represents the total cost of purchasing the product.
				 * This amount will be halfed if the customer is a prime member.
				 * The string msg is a message which will be displayed in the options dialog.
				 * This message will contain the total cost of purchase, and will change according if the customer is a prime member.
				 */
				
				double amountPurchased = 0.0;
				String msg = null; 
				if(customer.isPrime() == "Prime member: No") {
					amountPurchased = price * amountToPurchase;
					msg = "Are you sure you want to purchase this item? \n Total cost : £"
							+ round(amountPurchased, 2);
				} else if (customer.isPrime() == "Prime member: Yes") {
					amountPurchased = (price * amountToPurchase) / 2;
					msg = "Are you sure you want to purchase this item? \n Total cost : £"
							+ round(amountPurchased, 2) + " (prime)";
				}

				double newBalance = balance - amountPurchased; // balance after purchasing item
				
				try {

					if (newBalance < 0) { //If the newBalance after purchasing is less than 0.
						JOptionPane.showMessageDialog(null, "Insufficient funds to purchase item"); //Inform the customer they don't make funds to make purchase.
					} else if (newBalance >= 0 && newQuantity >= 0) { //If the newBalance is 0 or greater and the newQuantity of the product is 0 or greater.

						
						/**
						 * Create and display an options dialog to confirm purchase.
						 */
						
						int n = JOptionPane.showOptionDialog(null,
								msg,
								"Confirm Purchase", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
								null, null ,null);

						if (n == 0) { //If they have selected yes

							lblBalance.setText("£: " + round(newBalance, 2)); //Update the balance label
							lblQuantity.setText("Quantity: " + newQuantity); //Update the product quantity label.
							
							/**
							 * Make a connection to database.
							 * Construct the query. Query used to update the customer's balance in the database.
							 * Use a prepared statement to setup query.
							 * Set the newBalance and primaryKey in the query.
							 * Execute the update query.
							 */

							connection = dbConnection.dbConnector();
							String query = "UPDATE Non_Admin SET Balance = ? WHERE Email = ?";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setDouble(1, newBalance);
							pst.setString(2, primaryKey);
							pst.executeUpdate();
							
							/**
							 * Close the prepared statement and connection.
							 */
							
							pst.close();
							connection.close();
							
							/**
							 * Make a connection to database.
							 * Construct the query. Query used to update the product's quantity in the database.
							 * Use a prepared statement to setup query.
							 * Set the newQuantity and productID in the query.
							 * Execute the update query.
							 */

							connection = dbConnection.dbConnector();
							String query2 = "UPDATE Products SET Quantity = ? WHERE ID = ?";
							PreparedStatement pst2 = connection.prepareStatement(query2);
							pst2.setInt(1, newQuantity);
							pst2.setInt(2, productID);
							pst2.executeUpdate();
							
							/**
							 * Close the prepared statement and connection.
							 */
							
							pst2.close();
							connection.close();
							
							/**
							 * Make a connection to database.
							 * Construct the query. Query used to add the product purchase to the purchases table in the database.
							 * Use a prepared statement to setup query.
							 * Set the productID and primaryKey in the query.
							 * Execute the update query.
							 */
							
							connection = dbConnection.dbConnector();
							String query3 = "SELECT * FROM Purchases WHERE ID = ? AND Email = ?";
							PreparedStatement pst3 = connection.prepareStatement(query3);
							pst3.setInt(1, productID);
							pst3.setString(2, primaryKey);
							ResultSet rs3 = pst3.executeQuery();
							
							/**
							 * count variable used to check if the customer has previously purchased the product.
							 * alreadyPurchased is the amount the customer has already purchased.
							 */

							int count = 0;
							int alreadyPurchased = 0;
							
							/**
							 * While there are results, increment counter, and get the amount already purchased.
							 */
							
							while (rs3.next()) {
								count += 1;
								alreadyPurchased = rs3.getInt("Amount");
							}
							
							/**
							 * Close the result set, prepared statement and connection.
							 */
							
							rs3.close();
							pst3.close();
							connection.close();

							if (count == 1) { //If a record exists, i.e. If the customer has previously purchased the product.
								
								/**
								 * Make a connection to database.
								 * Construct the query. Query used to update the amount purchased in the database
								 * Use a prepared statement to setup query.
								 * Set the productID and primaryKey in the query.
								 * Execute the update query.
								 */
								
								connection = dbConnection.dbConnector();
								String query4 = "UPDATE Purchases SET Amount = ? WHERE ID = ? AND Email = ?";
								PreparedStatement pst4 = connection.prepareStatement(query4);
								pst4.setInt(1, alreadyPurchased + amountToPurchase);
								pst4.setInt(2, productID);
								pst4.setString(3, primaryKey);
								pst4.executeUpdate();
								
								/**
								 * Close the prepared statement and connection.
								 */
								
								pst4.close();
								connection.close();

							} else { //If they have not previously purchased the product
								
								/**
								 * Make a connection to database.
								 * Construct the query. Query used to insert the amount and the price they have purchased the product for.
								 * Use a prepared statement to setup query.
								 * Set the productID, primaryKey, amountToPurchase, and price in the query.
								 * Execute the update query.
								 */

								connection = dbConnection.dbConnector();
								String query5 = "INSERT INTO Purchases VALUES(?, ?, ?, ?)";
								PreparedStatement pst5 = connection.prepareStatement(query5);
								pst5.setInt(1, productID);
								pst5.setString(2, primaryKey);
								pst5.setInt(3, amountToPurchase);
								pst5.setDouble(4, price);
								pst5.executeUpdate();
								pst5.close();
								connection.close();
							}
							
							/**
							 * Make a connection to database.
							 * Construct the query. Query used to update the balance of the seller. Increase balance by amount purchased by customer
							 * Use a prepared statement to setup query.
							 * Set the amountPurchased and sellerID in the query.
							 * Execute the update query.
							 */

							connection = dbConnection.dbConnector();
							String query6 = "UPDATE Non_Admin SET Balance = Balance + ? WHERE Email = ?";
							PreparedStatement pst6 = connection.prepareStatement(query6);
							pst6.setDouble(1, amountPurchased);
							pst6.setString(2, lblProductSeller.getText().replaceAll("Seller: ", "")); //Get the product seller email
							pst6.executeUpdate();
							pst6.close();
							connection.close();

							JOptionPane.showMessageDialog(null, "Congratulations, you have successfully purchased this item!"); //Inform customer of successful purchase. 

							if (newQuantity == 0) { //If the new quantity is 0. i.e. The product has been sold
								
								/**
								 * Make a connection to database.
								 * Construct the query. Query used to update the product's sold column to 'Yes' in the database.
								 * Use a prepared statement to setup query.
								 * Set the amountPurchased and sellerID in the query.
								 * Execute the update query.
								 */

								connection = dbConnection.dbConnector();
								String query7 = "UPDATE Sells SET Sold = 'Yes' WHERE Email = ? AND ID = ?";
								PreparedStatement pst7 = connection.prepareStatement(query7);
								pst7.setString(1, lblProductSeller.getText().replaceAll("Seller: ", ""));
								pst7.setInt(2, productID);
								pst7.executeUpdate();
								pst7.close();
								connection.close();

								setDetails(); // SetDetails called to "refresh" the JList. Ultimately removing the product from the JList.

							}

						} else if (n == 1) { //If they selected no
							JOptionPane.showMessageDialog(null, "Purchase cancelled!"); //Inform customer of purchase cancellation.
						} else { //If they close the options dialog
							JOptionPane.showMessageDialog(null, "Purchase cancelled!"); //Inform customer of purchase cancellation.
						}

					} else if (amountToPurchase > quantity) { //If they amount they want to purchase is greater than the product's available quantity
						JOptionPane.showMessageDialog(null, "The amount you want to purchase if unavaliable. Please select a lower amount."); //Inform customer
					}
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setBounds(607, 270, 177, 40);
		frame.getContentPane().add(panel_1);

		JLabel lblPurchase = new JLabel("Purchase", SwingConstants.CENTER);
		lblPurchase.setForeground(Color.WHITE);
		lblPurchase.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblPurchase.setBounds(10, 10, 109, 19);
		panel_1.add(lblPurchase);

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
			 * When panel is clicked, panel text is checked.
			 * According to this the product is either removed from wish list or added to wish list.
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					
					/**
					 * If the panel text is "Add to wish list" then call the customer class addProductToWishList method.
					 * Set panel text to remove from wish list.
					 */

					if (lblAddToWish.getText() == "Add to wish list") { 
						
						if(Customer.getInstance().addProductToWishList(productID, primaryKey)) {
						
						JOptionPane.showMessageDialog(null, "Product successfully added to your wish list!");	//Inform customer.
							
						lblAddToWish.setText("Remove from wish list");
						
						}
						
						/**
						 * Else if the panel text is is "Remove from wish list" then call the customer class removeProductFromWishList method.
						 * set panel text to add to wish list.
						 */

					} else if (lblAddToWish.getText() == "Remove from wish list") {

						if(Customer.getInstance().removeProductFromWishList(productID, primaryKey)) {
							
							JOptionPane.showMessageDialog(null, "Product removed from your wish list!"); //Inform customer.
							
							lblAddToWish.setText("Add to wish list");	
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(97, 212, 195));
		panel_2.setBounds(607, 331, 177, 40);
		frame.getContentPane().add(panel_2);
		
		/**
		 * Here the combo box values and settings are defined.
		 * Combo box used to select the type of refinement of products in the list.
		 */

		String[] refineTypes = { "ID", "Price: Low to High", "Price: High to Low", "Quantity: Least to Most",
				"Quantity: Most to Least", "Alphabetically"};
		JComboBox refineComboBox = new JComboBox(refineTypes);
		refineComboBox.setFocusable(false);
		refineComboBox.setSelectedIndex(0);
		refineComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		refineComboBox.setBorder(null);
		refineComboBox.setBounds(10, 479, 170, 20);
		frame.getContentPane().add(refineComboBox);

		JPanel panel_3 = new JPanel();
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panel_3.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				panel_3.setBackground(new Color(97, 212, 195));
			}
			
			/**
			 * When panel is clicked, products in the JList are refined according to comboBox value.
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					
					/**
					 * If combo box selected value is ID then sort products according to oldest to newest product entry.
					 */

					if (refineComboBox.getSelectedItem() == "ID") {

						setDetails(); //This is the same as setting the initial details of the productsList.

					} else if (refineComboBox.getSelectedItem() == "Price: Low to High") { //Sort list according to lowest price first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by price in ascending order.
						 * Use a prepared statement to setup query.
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.Name, Products.ID, Sells.Sold FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE SOLD = 'No' AND Products.Approved = 'Yes' ORDER BY Price";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();

						productsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							productsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						productsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */
						
						rs.close();
						pst.close();
						connection.close();
						

					} else if (refineComboBox.getSelectedItem() == "Price: High to Low") { //Sort list according to highest price first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by price in descending order.
						 * Use a prepared statement to setup query.
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.Name, Products.ID, Sells.Sold FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE SOLD = 'No'AND Products.Approved = 'Yes' ORDER BY Price DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();

						productsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							productsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						productsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */

						rs.close();
						pst.close();
						connection.close();

					} else if (refineComboBox.getSelectedItem() == "Quantity: Least to Most") { //Sort list according to least quantity first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by quantity in ascending order.
						 * Use a prepared statement to setup query.
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.Name, Products.ID, Sells.Sold FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE SOLD = 'No' AND Products.Approved = 'Yes' ORDER BY Quantity";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();

						productsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							productsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						productsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */

						rs.close();
						pst.close();
						connection.close();

					} else if (refineComboBox.getSelectedItem() == "Quantity: Most to Least") { //Sort list according to most quantity first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by quantity in descending order.
						 * Use a prepared statement to setup query.
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.Name, Products.ID, Sells.Sold FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE SOLD = 'No' AND Products.Approved = 'Yes' ORDER BY Quantity DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();

						productsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							productsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						productsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */

						rs.close();
						pst.close();
						connection.close();

					} else if (refineComboBox.getSelectedItem() == "Alphabetically") { //Sort list alphabetically. i.e. A-Z.
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by product name in ascending order.
						 * Use a prepared statement to setup query.
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.Name, Products.ID, Sells.Sold FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE SOLD = 'No' AND Products.Approved = 'Yes' ORDER BY Name";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();

						productsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							productsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						productsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */

						rs.close();
						pst.close();
						connection.close();
						
					}

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(97, 212, 195));
		panel_3.setBounds(190, 479, 108, 20);
		frame.getContentPane().add(panel_3);

		JLabel lblRefine = new JLabel("Refine");
		lblRefine.setBounds(33, 0, 42, 19);
		panel_3.add(lblRefine);
		lblRefine.setForeground(Color.WHITE);
		lblRefine.setFont(new Font("Century Gothic", Font.BOLD, 14));
		
		JLabel label = new JLabel("Reviews", SwingConstants.CENTER);
		label.setForeground(new Color(204, 204, 204));
		label.setFont(new Font("Century Gothic", Font.BOLD, 15));
		label.setBounds(328, 397, 62, 20);
		frame.getContentPane().add(label);

	}
	
	/**
	 * This method is used to setup the productsList.
	 * Setup is done by using a DefaultListModel. This is used to provide a simple implementation of the ListModel.
	 * Customer's balance is also setup here.
	 */

	private void setDetails() {
		try {
			
			primaryKey = LoginScreen.getEmail(); //Get the current customer's primary key.
			productsListModel = new DefaultListModel<String>(); //Initialise the DLM for the productsList.
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the products that have been approved and not been sold yet.
			 * Use a prepared statement to setup query.
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT Products.Name, Products.ID FROM Products INNER JOIN Sells ON Products.ID = Sells.ID WHERE SOLD = 'No' AND Approved = 'Yes'";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			productsListModel.removeAllElements(); //Remove any elements if there are any.
			
			/**
			 * While there are results, create a string reference to the products ID and name, and add it to the DLM
			 * Set the model for the productsList to the DLM.
			 * Set the selected index to the first item in the list.
			 */

			while (rs.next()) {
				productsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
			}
			productsList.setModel(productsListModel);
			productsList.setSelectedIndex(0);
			
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

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
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
	 * This method is created for testing purposes only.
	 * Used to get the product's name
	 * @return
	 */
	
	public String getProductName() {
		return lblProductName.getText();
	}
}
