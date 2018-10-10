package customerGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.dbConnection;
import implementationClasses.Customer;
import userGUI.LoginScreen;

/**
 * This application window class, allows the customer to view all their purchased products.
 * Customers can also add a review to a product they have purchased.
 * Customers can refine their products according to a certain criteria.
 * @author Sachin
 *
 */

public class PurchasedProductsScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the customer.
	private JList<String> purchasedProductsList; // JList which contains String references to the products purchased by the customer.
	private String primaryKey; //The email of customer that is currently logged in.
	private JLabel lblBalance; //Label to show the customer's current account balance
	private Connection connection; // Used to provide connection to the database.
	private DefaultListModel<String> purchasedProductsListModel; //DefaultListModel to implement the JList.
	private int productID; // The id of the product, currently selected in the JList.
	private JLabel lblProductName; //Declared as variable for testing purposes. Used to represent the product's name.
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PurchasedProductsScreen window = new PurchasedProductsScreen();
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
	public PurchasedProductsScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Purchased Products");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(0, 0, 405, 528);
		frame.setSize(810, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setBounds(0, 0, 799, 45);
		frame.getContentPane().add(panel_1);

		lblBalance = new JLabel("\u00A3: 100.0", SwingConstants.CENTER);
		lblBalance.setForeground(Color.WHITE);
		lblBalance.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBalance.setBounds(706, 11, 83, 23);
		panel_1.add(lblBalance);

		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {
			
			/**
			 * When label is clicked, the customer is taken back to the customer main screen.
			 */

			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				CustomerMainScreen customer = new CustomerMainScreen();
				customer.show();
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		lblBack.setIcon(new ImageIcon(img));
		lblBack.setForeground(Color.WHITE);
		lblBack.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBack.setBounds(10, 2, 32, 43);
		panel_1.add(lblBack);

		JLabel lblPurchasedProducts = new JLabel("Purchased Products", SwingConstants.CENTER);
		lblPurchasedProducts.setForeground(new Color(204, 204, 204));
		lblPurchasedProducts.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblPurchasedProducts.setBounds(10, 56, 288, 26);
		frame.getContentPane().add(lblPurchasedProducts);

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

		JLabel lblPriceBoughtAt = new JLabel("Price bought at");
		lblPriceBoughtAt.setForeground(new Color(204, 204, 204));
		lblPriceBoughtAt.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPriceBoughtAt.setBounds(328, 290, 269, 20);
		frame.getContentPane().add(lblPriceBoughtAt);

		JLabel lblAmountBought = new JLabel("Amount bought");
		lblAmountBought.setForeground(new Color(204, 204, 204));
		lblAmountBought.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblAmountBought.setBounds(328, 321, 269, 20);
		frame.getContentPane().add(lblAmountBought);

		JLabel lblTotalcost = new JLabel("Total cost");
		lblTotalcost.setForeground(new Color(204, 204, 204));
		lblTotalcost.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblTotalcost.setBounds(328, 352, 269, 20);
		frame.getContentPane().add(lblTotalcost);

		JPanel panel = new JPanel();

		JLabel lblReview = new JLabel("Add a review", SwingConstants.CENTER);
		lblReview.setForeground(Color.WHITE);
		lblReview.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblReview.setBounds(10, 10, 157, 19);
		panel.add(lblReview);

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
		scrollPane.setBackground(new Color(97, 212, 195));
		frame.getContentPane().add(scrollPane);

		purchasedProductsList = new JList<String>();
		scrollPane.setViewportView(purchasedProductsList);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) purchasedProductsList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * purchasedProductsList has a selection listener, to determine which product
		 * the customer has currently selected. This information is used to determine the
		 * productID, and setup the product details to the right of the JList.
		 */
		
		purchasedProductsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				/**
				 * Check if you have selected a value and if you have, remove all the letters
				 * from the value and parse it to an integer to get the productID.
				 */

				if (purchasedProductsList.getSelectedValue() != null) {
					productID = Integer.parseInt(purchasedProductsList.getSelectedValue().replaceAll("\\D+", ""));
				}

				try {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query gets all the products that the customer has purchased.
					 * Use a prepared statement to setup query.
					 * Set the productID and primaryKey in the query.
					 * Execute the query.
					 */

					connection = dbConnection.dbConnector();
					String query = "SELECT Products.Name, Purchases.Price, Products.Description, Purchases.Amount "
							+ "FROM Products " + "INNER JOIN Purchases ON Products.ID = Purchases.ID "
							+ "WHERE Products.ID = ? AND Purchases.Email = ?";

					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, productID);
					pst.setString(2, primaryKey);
					ResultSet rs = pst.executeQuery();
					
					/**
					 * While there are results, set the product's name, description, price purchased for, amount purchased and total cost to the appropriate labels.
					 */

					while (rs.next()) {
						lblProductName.setText(rs.getString("Name"));
						lblProductDesc.setText(rs.getString("Description"));
						lblPriceBoughtAt.setText("Purchased for £: " + rs.getDouble("Price"));
						lblAmountBought.setText("Amount purchased: " + rs.getInt("Amount"));
						lblTotalcost.setText("Total cost: " + round((rs.getDouble("Price") * rs.getInt("Amount")), 2));
					}
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs.close();
					pst.close();
					connection.close();
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query checks to see if you have written a product review for a specific product.
					 * Use a prepared statement to setup query.
					 * Set the productID and primaryKey in the query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query2 = "SELECT * FROM Reviews WHERE ID = ? AND Email = ?";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					pst2.setInt(1, productID);
					pst2.setString(2, primaryKey);
					ResultSet rs2 = pst2.executeQuery();
					
					/**
					 * If there is a result, meaning the customer has written a review, give the customer the option to remove the review.
					 * Else, the customer has not yet written a review, and so give the option to add a review.
					 */

					if (rs2.next()) {
						lblReview.setText("Remove your review");
					} else {
						lblReview.setText("Add a review");
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
						productReviews.addElement("[" + rs3.getInt("Rating") + "/5]" + " | \"" + rs3.getString("Review")
								+ "\" | " + rs3.getString("First_Name") + " " + rs3.getString("Last_Name"));

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
					JOptionPane.showMessageDialog(null, e);
				}
			}

		}

		);

		purchasedProductsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		purchasedProductsList.setSelectedIndex(0);
		purchasedProductsList.setForeground(new Color(204, 204, 204));
		purchasedProductsList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		purchasedProductsList.setBackground(new Color(36, 47, 65));

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.WHITE);
		separator.setBounds(308, 56, 20, 442);
		frame.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(328, 99, 456, 9);
		frame.getContentPane().add(separator_1);

		JLabel lblDescription = new JLabel("Description", SwingConstants.CENTER);
		lblDescription.setForeground(new Color(204, 204, 204));
		lblDescription.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblDescription.setBounds(328, 119, 83, 20);
		frame.getContentPane().add(lblDescription);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(328, 239, 456, 9);
		frame.getContentPane().add(separator_2);

		JLabel lblDetails = new JLabel("Details", SwingConstants.CENTER);
		lblDetails.setForeground(new Color(204, 204, 204));
		lblDetails.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblDetails.setBounds(328, 259, 49, 20);
		frame.getContentPane().add(lblDetails);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(328, 383, 456, 9);
		frame.getContentPane().add(separator_3);
		
		/**
		 * Here the combo box values and settings are defined.
		 * Combo box used to select the type of refinement of products in the list.
		 */

		String[] refineTypes = { "ID", "Price: Low to High", "Price: High to Low", "Quantity: Least to Most",
				"Quantity: Most to Least", "Alphabetically" };
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

			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					
					/**
					 * If combo box selected value is ID then sort products according to oldest to newest product entry.
					 */

					if (refineComboBox.getSelectedItem() == "ID") {

						setDetails(); //This is the same as setting the initial details of the purchasedProductsList.

					} else if (refineComboBox.getSelectedItem() == "Price: Low to High") { //Sort list according to lowest price first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by price in ascending order.
						 * Use a prepared statement to setup query.
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Purchases.ID, Products.Name FROM Products INNER JOIN "
								+ "Purchases ON Products.ID = Purchases.ID WHERE Purchases.Email = ? ORDER BY Purchases.Price";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						purchasedProductsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							purchasedProductsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						purchasedProductsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */

						rs.close();
						pst.close();
						connection.close();

					} else if (refineComboBox.getSelectedItem() == "Price: High to Low") { //Sort list according to highest price first.
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by price in descending order.
						 * Use a prepared statement to setup query.
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Purchases.ID, Products.Name FROM Products INNER JOIN "
								+ "Purchases ON Products.ID = Purchases.ID WHERE Purchases.Email = ? ORDER BY Purchases.Price DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						purchasedProductsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							purchasedProductsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						purchasedProductsList.setSelectedIndex(0); //Set the selected index to 0.
						
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
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Purchases.ID, Products.Name FROM Products INNER JOIN "
								+ "Purchases ON Products.ID = Purchases.ID WHERE Purchases.Email = ? ORDER BY Purchases.Amount";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						purchasedProductsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							purchasedProductsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						purchasedProductsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */

						rs.close();
						pst.close();
						connection.close();

					} else if (refineComboBox.getSelectedItem() == "Quantity: Most to Least") { //Sort list according to most quantity first.
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by quantity in descending order.
						 * Use a prepared statement to setup query.
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Purchases.ID, Products.Name FROM Products INNER JOIN "
								+ "Purchases ON Products.ID = Purchases.ID WHERE Purchases.Email = ? ORDER BY Purchases.Amount DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						purchasedProductsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							purchasedProductsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						purchasedProductsList.setSelectedIndex(0); //Set the selected index to 0.
						
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
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Purchases.ID, Products.Name FROM Products INNER JOIN "
								+ "Purchases ON Products.ID = Purchases.ID WHERE Purchases.Email = ? ORDER BY Products.Name";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						purchasedProductsListModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							purchasedProductsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						purchasedProductsList.setSelectedIndex(0); //Set the selected index to 0.
						
						/**
						 * Close the result set, prepared statement and connection.
						 */

						rs.close();
						pst.close();
						connection.close();
					}

				} catch (Exception e) {
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

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setBackground(new Color(97, 212, 195));
			}
			
			/**
			 * When this panel is clicked, the customer is prompted with an options dialog.
			 * From here the customer can add their product review and rating
			 */

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {

					double currentBalance = Double.parseDouble(lblBalance.getText().replaceAll("£: ", "")); //Get the customer's balance.
					

					/*
					 * If the label text is "Add a review", create string of items, a combo box, text fields and panels.
					 * All of this will make up the confirm dialog box. From the dialog box the customer can give a rating and a text review.
					 */
					
					if (lblReview.getText() == "Add a review") {

						String[] items = { "1", "2", "3", "4", "5" };
						JComboBox rating = new JComboBox(items);
						rating.setFocusable(false);
						JTextField review = new JTextField();
						JPanel panel = new JPanel(new GridLayout(0, 1));
						panel.add(new JLabel("Rating (out of 5):"));
						panel.add(rating);
						panel.add(new JLabel("Review:"));
						panel.add(review);
						
						/**
						 * Show the confirm dialog box to the customer.
						 */
						
						int result = JOptionPane.showConfirmDialog(null, panel, "Product Review",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						
						/**
						 * Once they have pressed "Ok". Call the addProductReview method from the customer class.
						 * If the method returns true, this means the method has been executed successfully.
						 * In that case, update the lblBalance, adding £50 more to the customer's current balance.
						 * Set the panel text to "Remove your review" if they choose to remove their review later on.
						 * setDetails() essentially refreshs the list.
						 */

						if (result == JOptionPane.OK_OPTION) {

							if(Customer.getInstance().addProductReview(productID, primaryKey, review.getText(), Integer.parseInt(rating.getSelectedItem().toString()))) {
							
							JOptionPane.showMessageDialog(null, "Product review succesfull added! \n £50.0 added to your account balance!"); //Inform customer
								
							lblBalance.setText("£: " + round((currentBalance + 50.00), 2));
							
							JOptionPane.showMessageDialog(null, "Your product review has been removed! \n £50.0 deducted from your account balance!"); //Inform customer

							lblReview.setText("Remove your review");

							setDetails();
							}
						}
						
						/**
						 * If the panel text is "Remove your review". Call the customer removeProductReview method.
						 * Set the balance to £50 less.
						 * Change panel text to "Add a review". Because now the customer can add a product review.
						 * setDetails() to refresh the list.
						 */

					} else if (lblReview.getText() == "Remove your review") {

						if(Customer.getInstance().removeProductReview(productID, primaryKey)) {
						
						lblBalance.setText("£: " + round((currentBalance - 50.00), 2));

						lblReview.setText("Add a review");

						setDetails();
						}

					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		panel.setLayout(null);
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(607, 270, 177, 40);
		frame.getContentPane().add(panel);

		JLabel label = new JLabel("Reviews", SwingConstants.CENTER);
		label.setForeground(new Color(204, 204, 204));
		label.setFont(new Font("Century Gothic", Font.BOLD, 15));
		label.setBounds(328, 397, 62, 20);
		frame.getContentPane().add(label);
	}
	
	/**
	 * This method is used to setup the purchasedProductsList.
	 * Setup is done by using a DefaultListModel. This is used to provide a simple implementation of the ListModel.
	 * Customer's balance is also setup here.
	 */

	private void setDetails() {
		try {
			
			primaryKey = LoginScreen.getEmail(); //Get the current customer's primary key.
			purchasedProductsListModel = new DefaultListModel<String>(); //Initialise the DLM for the productsList.
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the products that have been purchased by the customer.
			 * Set the primaryKey in the query
			 * Use a prepared statement to setup query.
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT Products.Name, Products.ID FROM Products INNER JOIN Purchases ON "
					+ "Products.ID = Purchases.ID WHERE Purchases.Email = ?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, primaryKey);
			ResultSet rs = pst.executeQuery();

			purchasedProductsListModel.removeAllElements(); //Remove any elements if there are any.
			
			/**
			 * While there are results, create a string reference to the products ID and name, and add it to the DLM
			 * Set the model for the purchasedProductsList to the DLM.
			 * Set the selected index to the first item in the list.
			 */

			while (rs.next()) {
				purchasedProductsListModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
			}
			purchasedProductsList.setModel(purchasedProductsListModel);
			purchasedProductsList.setSelectedIndex(0);
			
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
			
			/**
			 * While there are results, set the balance in the lblBalance label.
			 */

			ResultSet rs2 = pst2.executeQuery();

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
