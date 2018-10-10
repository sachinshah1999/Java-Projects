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

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.dbConnection;
import implementationClasses.Seller;
import userGUI.LoginScreen;

import javax.swing.JScrollPane;

/**
 * This application window class, allows the seller to view all the products they have currently listed on the market.
 * The seller can also change the details of their products or choose to remove the product entirely of the market.
 * @author Sachin
 *
 */

public class CurrentListingsScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the seller.
	private JList<String> currentListings; // JList which contains String references to the products the seller currently has listed on the market
	private String primaryKey; //The email of seller that is currently logged in.
	private JLabel lblBalance; //Label to show the seller's current account balance
	private int productID; // The id of the product, currently selected in the JList.
	private DefaultListModel<String> currentListingsModel; //DefaultListModel to implement the JList.
	private Connection connection; // Used to provide connection to the database.
	private JTextField lblProductName; //Declared as variable for testing purposes. Used to represent the product's name.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CurrentListingsScreen window = new CurrentListingsScreen();
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
	public CurrentListingsScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("Current Listings");
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
		lblBalance.setBounds(673, 11, 116, 23);
		panel_1.add(lblBalance);
		
		/**
		 * When label is clicked, the seller is taken back to the seller main screen.
		 */

		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				SellerMainScreen seller = new SellerMainScreen();
				seller.show();
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		lblBack.setIcon(new ImageIcon(img));
		lblBack.setForeground(Color.WHITE);
		lblBack.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBack.setBounds(10, 2, 32, 43);
		panel_1.add(lblBack);

		JLabel lblCurrentListings = new JLabel("Current Listings", SwingConstants.CENTER);
		lblCurrentListings.setForeground(new Color(204, 204, 204));
		lblCurrentListings.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblCurrentListings.setBounds(10, 56, 288, 26);
		frame.getContentPane().add(lblCurrentListings);
		
		lblProductName = new JTextField("Name of the product");
		lblProductName.setCaretColor(Color.WHITE);
		lblProductName.setHorizontalAlignment(JTextField.CENTER);
		lblProductName.setBackground(new Color(36, 47, 65));
		lblProductName.setForeground(new Color(204, 204, 204));
		lblProductName.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblProductName.setBounds(328, 56, 456, 32);
		frame.getContentPane().add(lblProductName);
		
		JTextField lblProductDesc = new JTextField("Description of the product");
		lblProductDesc.setCaretColor(Color.WHITE);
		// lblProductDesc.setHorizontalAlignment(JTextField.LEFT);
		lblProductDesc.setBackground(new Color(36, 47, 65));
		lblProductDesc.setForeground(new Color(204, 204, 204));
		lblProductDesc.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblProductDesc.setBounds(328, 150, 456, 26);
		frame.getContentPane().add(lblProductDesc);
		
		JTextField lblPrice = new JTextField("Price of the product");
		lblPrice.setCaretColor(Color.WHITE);
		lblPrice.setForeground(new Color(204, 204, 204));
		lblPrice.setBackground(new Color(36, 47, 65));
		lblPrice.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPrice.setBounds(350, 290, 153, 20);
		frame.getContentPane().add(lblPrice);
		
		JTextField lblQuantity = new JTextField("Quantity");
		lblQuantity.setCaretColor(Color.WHITE);
		lblQuantity.setBackground(new Color(36, 47, 65));
		lblQuantity.setForeground(new Color(204, 204, 204));
		lblQuantity.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblQuantity.setBounds(404, 321, 99, 20);
		frame.getContentPane().add(lblQuantity);
		

		JLabel lblAmountSold = new JLabel("Amount sold");
		lblAmountSold.setForeground(new Color(204, 204, 204));
		lblAmountSold.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblAmountSold.setBounds(328, 352, 172, 20);
		frame.getContentPane().add(lblAmountSold);
		
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

		currentListings = new JList<String>();
		scrollPane.setViewportView(currentListings);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) currentListings.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * currentListings has a selection listener, to determine which product
		 * the seller has currently selected. This information is used to determine the
		 * productID, and setup the product details to the right of the JList.
		 */
		
		currentListings.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				/**
				 * Check if you have selected a value and if you have, remove all the letters
				 * from the value and parse it to an integer to get the productID.
				 */

				if (currentListings.getSelectedValue() != null) {
					productID = Integer.parseInt(currentListings.getSelectedValue().replaceAll("\\D+", ""));
				}

				try {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query gets all the products that the seller has currently listed on the market.
					 * Use a prepared statement to setup query.
					 * Set the productID and primaryKey in the query.
					 * Execute the query.
					 */

					connection = dbConnection.dbConnector();
					String query = "SELECT Products.ID, Products.Name, Products.Price, Products.Quantity, Products.Description "
							+ "FROM Products " + "INNER JOIN Sells ON Products.ID = Sells.ID "
							+ "WHERE Sells.ID = ? AND Sells.Email = ?";

					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, productID);
					pst.setString(2, primaryKey);
					ResultSet rs = pst.executeQuery();
					
					/**
					 * While there are results, set the product's name, description, price and quantity to the appropriate labels.
					 */

					while (rs.next()) {

						lblProductName.setText(rs.getString("Name"));
						lblProductDesc.setText(rs.getString("Description"));
						lblPrice.setText(rs.getDouble("Price") + "");
						lblQuantity.setText(rs.getInt("Quantity") + "");
					}
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs.close();
					pst.close();
					connection.close();
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query used to get the amount of product that has been sold
					 * Use a prepared statement to setup query.
					 * Set the productID in the query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query2 = "SELECT Amount FROM Purchases WHERE ID = ?";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					pst2.setInt(1, productID);

					ResultSet rs2 = pst2.executeQuery();

					int total = 0; //This will hold the total amount of a product sold.
					
					/**
					 * While there are results, increment the total by the amount.
					 */

					while (rs2.next()) {
						total += rs2.getInt("Amount");
					}

					lblAmountSold.setText("Amount sold: " + total); //Set the label to reflect this information to the seller.
					
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
					 * While there are results, 
					 * create a string reference to the product rating (out of 5), the actual review and the customer's name, and add it to the DLM.
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
					JOptionPane.showMessageDialog(null, e);
				}
			}

		}

		);

		currentListings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currentListings.setSelectedIndex(0);
		currentListings.setForeground(new Color(204, 204, 204));
		currentListings.setFont(new Font("Century Gothic", Font.BOLD, 14));
		currentListings.setBackground(new Color(36, 47, 65));

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
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					
					/**
					 * Validation to ensure the fields are not empty, and the correct data-type is in each field.
					 */

					if (lblProductName.getText().equals("") || lblPrice.getText().equals("") || lblQuantity.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Fields: Name, Price and Quantity MUST be completed!");
					} else if (!lblPrice.getText().matches("[0-9]+([.][0-9]{1,2})?")
							|| Double.parseDouble(lblPrice.getText()) <= 0) {
						JOptionPane.showMessageDialog(null, "Please enter a valid price! e.g. 2.99");
					} else if (!lblQuantity.getText().matches("[0-9]*")
							|| Integer.parseInt(lblQuantity.getText()) <= 0) {
						JOptionPane.showMessageDialog(null, "Please enter a valid quantity!");
					} else {
						
						/**
						 * Call the seller updateProduct method to list the product.
						 * If the method returns true, meaning it executed correctly; call the setDetails method and refresh the list
						 */

						if(Seller.getInstance().updateProduct(lblProductName.getText(), lblProductDesc.getText(), Double.parseDouble(lblPrice.getText()), 
								Integer.parseInt(lblQuantity.getText()), productID)){
							setDetails();
						}

					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(97, 212, 195));
		panel_2.setBounds(607, 331, 177, 40);
		frame.getContentPane().add(panel_2);

		JLabel lblAddToWish = new JLabel("Update details");
		lblAddToWish.setForeground(Color.WHITE);
		lblAddToWish.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblAddToWish.setBounds(39, 10, 98, 19);
		panel_2.add(lblAddToWish);

		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setBackground(new Color(97, 212, 195));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					Seller.getInstance().removeProduct(productID, primaryKey);

					setDetails(); //Called to refresh the JList.

					JOptionPane.showMessageDialog(null, "Product removed from market");

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		panel.setLayout(null);
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(607, 270, 177, 40);
		frame.getContentPane().add(panel);

		JLabel lblRemoveFromMarket = new JLabel("Remove from market");
		lblRemoveFromMarket.setForeground(Color.WHITE);
		lblRemoveFromMarket.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblRemoveFromMarket.setBounds(17, 10, 143, 19);
		panel.add(lblRemoveFromMarket);

		JLabel label = new JLabel("\u00A3:");
		label.setForeground(new Color(204, 204, 204));
		label.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label.setBounds(328, 290, 12, 20);
		frame.getContentPane().add(label);

		JLabel lblQuantity_1 = new JLabel("Quantity:");
		lblQuantity_1.setForeground(new Color(204, 204, 204));
		lblQuantity_1.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblQuantity_1.setBounds(328, 321, 66, 20);
		frame.getContentPane().add(lblQuantity_1);
		
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

						setDetails(); //This is the initial state of the list, so we can use the setDetails method to do this automatically.

					} else if (refineComboBox.getSelectedItem() == "Price: Low to High") { //Sort list according to lowest price first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by price in ascending order.
						 * Use a prepared statement to setup query.
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.ID, Products.Name FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sold = 'No' ORDER BY Price";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						currentListingsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							currentListingsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						currentListings.setSelectedIndex(0); //Set the selected index to 0.

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
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.ID, Products.Name FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sold = 'No' ORDER BY Price DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						currentListingsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							currentListingsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						currentListings.setSelectedIndex(0); //Set the selected index to 0.

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
						String query = "SELECT Products.ID, Products.Name FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sold = 'No' ORDER BY Quantity";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						currentListingsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							currentListingsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						currentListings.setSelectedIndex(0); //Set the selected index to 0.

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
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.ID, Products.Name FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sold = 'No' ORDER BY Quantity DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						currentListingsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							currentListingsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						currentListings.setSelectedIndex(0); //Set the selected index to 0.

						/**
						 * Close the result set, prepared statement and connection.
						 */
						
						rs.close();
						pst.close();
						connection.close();

					} else if (refineComboBox.getSelectedItem() == "Alphabetically") { //Sort list alphabetically. i.e. A-Z
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by product name in ascending order.
						 * Use a prepared statement to setup query.
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.ID, Products.Name FROM Products "
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sold = 'No' ORDER BY Name";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						currentListingsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							currentListingsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}

						currentListings.setSelectedIndex(0); //Set the selected index to 0.

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
		
		JLabel label_1 = new JLabel("Reviews", SwingConstants.CENTER);
		label_1.setForeground(new Color(204, 204, 204));
		label_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
		label_1.setBounds(328, 397, 62, 20);
		frame.getContentPane().add(label_1);
	}
	
	/**
	 * This method is used to setup the currentListings list.
	 * Setup is done by using a DefaultListModel. This is used to provide a simple implementation of the ListModel.
	 * Seller's balance is also setup here.
	 */

	private void setDetails() {
		try {
			
			primaryKey = LoginScreen.getEmail(); //Get the current seller's primary key.
			currentListingsModel = new DefaultListModel<String>(); //Initialise the DLM for the currentListings.
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the products the seller has currently listed on the market
			 * Set the primaryKey in the query.
			 * Use a prepared statement to setup query.
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT Products.ID, Products.Name FROM Products INNER JOIN Sells ON "
					+ "Products.ID = Sells.ID WHERE Sells.Email = ? AND Sold = 'No' AND Approved = 'Yes'";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, primaryKey);
			ResultSet rs = pst.executeQuery();

			currentListingsModel.removeAllElements(); //Remove any elements if there are any.
			
			/**
			 * While there are results, create a string reference to the products ID and name, and add it to the DLM
			 * Set the model for the currentListings to the DLM.
			 * Set the selected index to the first item in the list.
			 */

			while (rs.next()) {
				currentListingsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
			}
			currentListings.setModel(currentListingsModel);
			currentListings.setSelectedIndex(0);
			
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
