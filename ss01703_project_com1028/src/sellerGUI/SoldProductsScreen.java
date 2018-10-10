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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.dbConnection;
import userGUI.LoginScreen;

/**
 * This application window class, contains the seller's sold products.
 * From here the seller can navigate back to the main screen, or choose to re-list a sold product
 * @author Sachin
 *
 */

public class SoldProductsScreen {
	
	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the seller.
	private JLabel lblBalance; //Label to show the seller's current account balance
	private JList<String> soldProductsList; // JList which contains String references to the products the seller has sold out.
	private Connection connection; // Used to provide connection to the database.
	private String primaryKey; //The email of seller that is currently logged in.
	private int productID; // The id of the product, currently selected in the JList.
	private DefaultListModel<String> soldProductsModel; //DefaultListModel to implement the JList.
	private JLabel lblProductName; //Declared as variable for testing purposes. Used to represent the product's name.
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SoldProductsScreen window = new SoldProductsScreen();
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
	public SoldProductsScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("Sold Products");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(0, 0, 405, 528);
		frame.setSize(810, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo( null );

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setBounds(0, 0, 799, 45);
		frame.getContentPane().add(panel_1);
		
		lblBalance = new JLabel("\u00A3: 100.0", SwingConstants.CENTER);
		lblBalance.setForeground(Color.WHITE);
		lblBalance.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBalance.setBounds(663, 11, 126, 23);
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
		
		JLabel lblSoldProducts = new JLabel("Sold Products", SwingConstants.CENTER);
		lblSoldProducts.setForeground(new Color(204, 204, 204));
		lblSoldProducts.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblSoldProducts.setBounds(10, 56, 288, 26);
		frame.getContentPane().add(lblSoldProducts);
		
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
		
		JLabel lblAmountSold = new JLabel("Amount sold");
		lblAmountSold.setForeground(new Color(204, 204, 204));
		lblAmountSold.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblAmountSold.setBounds(328, 290, 269, 20);
		frame.getContentPane().add(lblAmountSold);
		
		JLabel lblPriceSoldAt = new JLabel("Price sold at");
		lblPriceSoldAt.setForeground(new Color(204, 204, 204));
		lblPriceSoldAt.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblPriceSoldAt.setBounds(328, 321, 269, 20);
		frame.getContentPane().add(lblPriceSoldAt);
		
		JLabel lblTotalProfit = new JLabel("Total profit");
		lblTotalProfit.setForeground(new Color(204, 204, 204));
		lblTotalProfit.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblTotalProfit.setBounds(328, 352, 269, 20);
		frame.getContentPane().add(lblTotalProfit);
		
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
		
		soldProductsList = new JList<String>();
		scrollPane.setViewportView(soldProductsList);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) soldProductsList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * soldProductsList has a selection listener, to determine which product
		 * the seller has currently selected. This information is used to determine the
		 * productID, and setup the product details to the right of the JList.
		 */
		
		soldProductsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				/**
				 * Check if you have selected a value and if you have, remove all the letters
				 * from the value and parse it to an integer to get the productID.
				 */
				
				if (soldProductsList.getSelectedValue() != null) {
					productID = Integer.parseInt(soldProductsList.getSelectedValue().replaceAll("\\D+", ""));
				}

				try {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query gets all the products that the seller has currently sold.
					 * Use a prepared statement to setup query.
					 * Set the productID and primaryKey in the query.
					 * Execute the query.
					 */

					connection = dbConnection.dbConnector();
					String query = "SELECT Products.Name, Products.Description, Purchases.Amount, Purchases.Price FROM Products "
							+ "INNER JOIN Sells ON Sells.ID = Products.ID "
							+ "INNER JOIN Purchases ON Purchases.ID = Sells.ID WHERE Sold = 'Yes' AND Products.ID = ? AND Sells.Email = ?";

					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, productID);
					pst.setString(2, primaryKey);
					ResultSet rs = pst.executeQuery();
					
					/**
					 * While there are results, set the product's name, description, amount sold, price sold at and total revenue to the appropriate labels.
					 */

					while (rs.next()) {
						lblProductName.setText(rs.getString("Name"));
						lblProductDesc.setText(rs.getString("Description"));
						lblAmountSold.setText("Amount sold: " + rs.getInt("Amount"));
						lblPriceSoldAt.setText("Price sold at: " + rs.getDouble("Price"));
						lblTotalProfit.setText("Total revenue: " + round((rs.getInt("Amount") * rs.getDouble("Price")), 2));
					}
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs.close();
					pst.close();
					connection.close();
					
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query used to get the all the product reviews of the selected product
					 * Use a prepared statement to setup query.
					 * Set the productID in the query.
					 * Execute the query.
					 */
					
					
					connection = dbConnection.dbConnector();
					String query2 = "SELECT Reviews.Rating, Reviews.Review, User.First_Name, User.Last_Name FROM Reviews "
							+ "INNER JOIN Products ON Products.ID = Reviews.ID "
							+ "INNER JOIN User ON Reviews.Email = User.Email " + "WHERE Products.ID = ?";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					pst2.setInt(1, productID);
					ResultSet rs2 = pst2.executeQuery();
					
					DefaultListModel<String> productReviews = new DefaultListModel<String>(); //Create the DefaultListModel to contain the product reviews.

					productReviews.removeAllElements(); //Remove any elements from the DLM if there are any.
					
					/**
					 * While there are results, 
					 * create a string reference to the product rating (out of 5), the actual review and the customer's name, and add it to the DLM.
					 * Set the model for the productReviewsList to the productReviews DefaultListModel.
					 * Set the selected index to the first item in the list.
					 */

					while (rs2.next()) {
						productReviews.addElement("[" + rs2.getInt("Rating") + "/5]" + " | \"" + rs2.getString("Review") + "\" | "
								+ rs2.getString("First_Name") + " " + rs2.getString("Last_Name"));

					}
					productReviewsList.setModel(productReviews);
					productReviewsList.setSelectedIndex(0);
					
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

		}

		);

		soldProductsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		soldProductsList.setSelectedIndex(0);
		soldProductsList.setForeground(new Color(204, 204, 204));
		soldProductsList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		soldProductsList.setBackground(new Color(36, 47, 65));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.WHITE);
		separator.setBounds(308, 56, 20, 442);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(328, 99, 456, 9);
		frame.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(328, 239, 456, 9);
		frame.getContentPane().add(separator_2);
		
		JLabel label_1 = new JLabel("Description", SwingConstants.CENTER);
		label_1.setForeground(new Color(204, 204, 204));
		label_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
		label_1.setBounds(328, 119, 83, 20);
		frame.getContentPane().add(label_1);
		
		JLabel label_4 = new JLabel("Details", SwingConstants.CENTER);
		label_4.setForeground(new Color(204, 204, 204));
		label_4.setFont(new Font("Century Gothic", Font.BOLD, 15));
		label_4.setBounds(328, 259, 49, 20);
		frame.getContentPane().add(label_4);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(328, 383, 456, 9);
		frame.getContentPane().add(separator_3);
		
		/**
		 * Here the combo box values and settings are defined.
		 * Combo box used to select the type of refinement of products in the list.
		 */
		
		String[] refineTypes = {"ID", "Price: Low to High", "Price: High to Low", "Sold: Least to Most", "Sold: Most to Least", "Alphabetically"};
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
					
					if(refineComboBox.getSelectedItem() == "ID") {
						
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
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sells.Sold = 'Yes' ORDER BY Price";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						soldProductsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							soldProductsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}
						
						soldProductsList.setSelectedIndex(0); //Set the selected index to 0.

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
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sells.Sold = 'Yes' ORDER BY Price DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						soldProductsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							soldProductsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}
						
						soldProductsList.setSelectedIndex(0); //Set the selected index to 0.

						/**
						 * Close the result set, prepared statement and connection.
						 */
						
						rs.close();
						pst.close();
						connection.close();
						
					} else if (refineComboBox.getSelectedItem() == "Sold: Least to Most") { //Sort list according to least sold first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by amount in ascending order.
						 * Use a prepared statement to setup query.
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.ID, Products.Name, Products.Description, Purchases.Amount, Purchases.Price FROM Products "
								+ "INNER JOIN Sells ON Sells.ID = Products.ID "
								+ "INNER JOIN Purchases ON Purchases.ID = Sells.ID WHERE Sold = 'Yes' AND Sells.Email = ? ORDER BY Amount";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						soldProductsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							soldProductsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}
						
						soldProductsList.setSelectedIndex(0); //Set the selected index to 0.

						/**
						 * Close the result set, prepared statement and connection.
						 */
						
						rs.close();
						pst.close();
						connection.close();
						
					} else if (refineComboBox.getSelectedItem() == "Sold: Most to Least") { //Sort list according to most sold first
						
						/**
						 * Make a connection to database.
						 * Construct the query. Query used to sort products in the list by amount in descending order.
						 * Use a prepared statement to setup query.
						 * Set the primary key in the query
						 * Execute the update query.
						 */

						connection = dbConnection.dbConnector();
						String query = "SELECT Products.ID, Products.Name, Products.Description, Purchases.Amount, Purchases.Price FROM Products "
								+ "INNER JOIN Sells ON Sells.ID = Products.ID "
								+ "INNER JOIN Purchases ON Purchases.ID = Sells.ID WHERE Sold = 'Yes' AND Sells.Email = ? ORDER BY Amount DESC";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						soldProductsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							soldProductsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}
						
						soldProductsList.setSelectedIndex(0); //Set the selected index to 0.

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
								+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ? AND Sells.Sold = 'Yes' ORDER BY Name";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, primaryKey);
						ResultSet rs = pst.executeQuery();

						soldProductsModel.removeAllElements(); //Remove any elements, as the list will be recreated.
						
						/**
						 * While there are results, add the product id and name to the list DLM.
						 */

						while (rs.next()) {
							soldProductsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
						}
						
						soldProductsList.setSelectedIndex(0); //Set the selected index to 0.

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
			
			/**
			 * When this panel is clicked. The product is re-listed onto the market.
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query updates the quantity of the product to the amount sold.
					 * Set the quantity and productID in the query.
					 * Use a prepared statement to setup query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query = "UPDATE Products SET Quantity = ? WHERE ID = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, Integer.parseInt(lblAmountSold.getText().replaceAll("Amount sold: ", "")));
					pst.setInt(2, productID);
					pst.executeUpdate();
					pst.close();
					connection.close();
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query updates sells table to set the product as not sold, since it is being re-listed.
					 * Set the productID and primaryKey in the query.
					 * Use a prepared statement to setup query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query2 = "UPDATE Sells SET Sold = 'No' WHERE ID = ? AND Email = ?";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					pst2.setInt(1, productID);
					pst2.setString(2, primaryKey);
					pst2.executeUpdate();
					pst2.close();
					connection.close();
					
					JOptionPane.showMessageDialog(null, "Product relisted on the market, with previous details. Change details at current listings!"); //Inform seller.
					
					setDetails(); //Call method to refresh the JList.

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		panel.setLayout(null);
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(607, 270, 177, 40);
		frame.getContentPane().add(panel);

		JLabel lblRemoveFromMarket = new JLabel("Relist product");
		lblRemoveFromMarket.setForeground(Color.WHITE);
		lblRemoveFromMarket.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblRemoveFromMarket.setBounds(43, 10, 91, 19);
		panel.add(lblRemoveFromMarket);
		
		JLabel label = new JLabel("Reviews", SwingConstants.CENTER);
		label.setForeground(new Color(204, 204, 204));
		label.setFont(new Font("Century Gothic", Font.BOLD, 15));
		label.setBounds(328, 397, 62, 20);
		frame.getContentPane().add(label);
	}
	
	/**
	 * This method is used to setup the soldProductsList list.
	 * Setup is done by using a DefaultListModel. This is used to provide a simple implementation of the ListModel.
	 * Seller's balance is also setup here.
	 */
	
	private void setDetails() {
		try {
			
			
			
			primaryKey = LoginScreen.getEmail(); //Get the current seller's primary key.
			soldProductsModel = new DefaultListModel<String>(); //Initialise the DLM for the soldProductsList
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the products the seller has currently sold.
			 * Set the primaryKey in the query.
			 * Use a prepared statement to setup query.
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT Products.ID, Products.Name FROM Products INNER JOIN Sells ON "
					+ "Products.ID = Sells.ID WHERE Sells.Email = ? AND Sells.Sold = 'Yes'";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, primaryKey);
			ResultSet rs = pst.executeQuery();
			
			soldProductsModel.removeAllElements(); //Remove any elements if there are any.
			
			/**
			 * While there are results, create a string reference to the products ID and name, and add it to the DLM
			 * Set the model for the soldProductsList to the DLM.
			 * Set the selected index to the first item in the list.
			 */

			while (rs.next()) {
				soldProductsModel.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
			}
			soldProductsList.setModel(soldProductsModel);
			soldProductsList.setSelectedIndex(0);
			
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
	
	public static double round(double value, int places) {
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
