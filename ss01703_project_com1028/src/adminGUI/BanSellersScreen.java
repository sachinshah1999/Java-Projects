package adminGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import implementationClasses.Admin;

/**
 * This application window class, allows the admin to ban sellers.
 * @author Sachin
 *
 */

public class BanSellersScreen {
	
	/** Fields **/

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the admin.
	private Connection connection; //Used to provide connection to the database.
	private JList<String> sellersList; //JList which contains String references to the all seller email's.
	private String sellerID; //The id of the seller, currently selected in the JList.
	private int productID; //The id of the product, currently selected in the JList.
	private JLabel lblSellerEmail; //Declared as variable for testing purposes. Used to represent the seller's email.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BanSellersScreen window = new BanSellersScreen();
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
	public BanSellersScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Ban Sellers");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(0, 0, 405, 528);
		frame.setSize(810, 570);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setBounds(0, 0, 799, 45);
		frame.getContentPane().add(panel_1);

		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {
			
			/**
			 * Method to go back to the admin main screen.
			 * Dispose the current frame, create a adminMainScreen and call the show method.
			 * This method sets the frame in the adminMainScreen class visible.
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				AdminMainScreen admin = new AdminMainScreen();
				admin.show();
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		lblBack.setIcon(new ImageIcon(img));
		lblBack.setForeground(Color.WHITE);
		lblBack.setForeground(Color.WHITE);
		lblBack.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBack.setBounds(10, 2, 32, 43);
		panel_1.add(lblBack);

		JLabel lblProductsAwaitingApproval = new JLabel("Sellers", SwingConstants.CENTER);
		lblProductsAwaitingApproval.setForeground(new Color(204, 204, 204));
		lblProductsAwaitingApproval.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblProductsAwaitingApproval.setBounds(10, 56, 288, 26);
		frame.getContentPane().add(lblProductsAwaitingApproval);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.WHITE);
		separator.setBounds(308, 56, 20, 464);
		frame.getContentPane().add(separator);

		lblSellerEmail = new JLabel("Email of the seller ", SwingConstants.CENTER);
		lblSellerEmail.setForeground(new Color(204, 204, 204));
		lblSellerEmail.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblSellerEmail.setBounds(328, 56, 456, 32);
		frame.getContentPane().add(lblSellerEmail);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(328, 99, 456, 9);
		frame.getContentPane().add(separator_1);

		JLabel lblDetails = new JLabel("Details", SwingConstants.CENTER);
		lblDetails.setForeground(new Color(204, 204, 204));
		lblDetails.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblDetails.setBounds(328, 119, 49, 20);
		frame.getContentPane().add(lblDetails);

		JLabel lblSellerFirstName = new JLabel("Seller first name");
		lblSellerFirstName.setForeground(new Color(204, 204, 204));
		lblSellerFirstName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblSellerFirstName.setBounds(328, 150, 456, 20);
		frame.getContentPane().add(lblSellerFirstName);

		JLabel lblSellerLastName = new JLabel("Seller last name");
		lblSellerLastName.setForeground(new Color(204, 204, 204));
		lblSellerLastName.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblSellerLastName.setBounds(328, 181, 456, 20);
		frame.getContentPane().add(lblSellerLastName);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(328, 407, 454, 113);
		frame.getContentPane().add(scrollPane_2);

		JList<String> sellerProductsReviewsList = new JList<String>();
		scrollPane_2.setViewportView(sellerProductsReviewsList);
		DefaultListCellRenderer renderer3 = (DefaultListCellRenderer) sellerProductsReviewsList.getCellRenderer();
		renderer3.setHorizontalAlignment(SwingConstants.CENTER);
		sellerProductsReviewsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sellerProductsReviewsList.setForeground(new Color(204, 204, 204));
		sellerProductsReviewsList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		sellerProductsReviewsList.setBackground(new Color(36, 47, 65));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(328, 263, 456, 93);
		frame.getContentPane().add(scrollPane_1);

		JList<String> sellerProductsList = new JList<String>();
		scrollPane_1.setViewportView(sellerProductsList);
		DefaultListCellRenderer renderer2 = (DefaultListCellRenderer) sellerProductsList.getCellRenderer();
		renderer2.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * sellerProductsList has a selection listener, to determine which product the admin has currently selected.
		 * This information is used to determine the productID, and setup the product details to the right of the JList.
		 */

		sellerProductsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				/**
				 * Check if you have selected a value and if you have, remove all the letters from the value and parse it to an integer.
				 */

				if (sellerProductsList.getSelectedValue() != null) {
					productID = Integer.parseInt(sellerProductsList.getSelectedValue().replaceAll("\\D+", ""));
				}

				try {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query used to get the all the product reviews of the selected product
					 * Use a prepared statement to setup query.
					 * Set the productID in the query.
					 * Execute the query.
					 */

					connection = dbConnection.dbConnector();
					String query = "SELECT Reviews.Rating, Reviews.Review, User.First_Name, User.Last_Name FROM Reviews "
							+ "INNER JOIN Products ON Products.ID = Reviews.ID "
							+ "INNER JOIN User ON Reviews.Email = User.Email " + "WHERE Products.ID = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, productID);
					ResultSet rs = pst.executeQuery();

					DefaultListModel<String> productReview = new DefaultListModel<String>(); //Create the DefaultListModel to contain the product reviews.

					productReview.removeAllElements(); //Remove any elements from the DLM if there are any.
					
					/**
					 * While there are results, create a string reference to the product rating (out of 5), the actual review and the customer's name, and add it to the DLM.
					 * Set the model for the sellerProductsReviewsList to the productReview DefaultListModel.
					 * Set the selected index to the first item in the list.
					 */

					while (rs.next()) {
						productReview.addElement("[" + rs.getInt("Rating") + "/5]" + " | \"" + rs.getString("Review")
								+ "\" | " + rs.getString("First_Name") + " " + rs.getString("Last_Name"));

					}
					sellerProductsReviewsList.setModel(productReview);
					sellerProductsReviewsList.setSelectedIndex(0);
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs.close();
					pst.close();
					connection.close();
					

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}

		}

		);

		sellerProductsList.setBackground(new Color(36, 47, 65));
		sellerProductsList.setForeground(new Color(204, 204, 204));
		sellerProductsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sellerProductsList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(10, 479, 288, 41);
		frame.getContentPane().add(panel);

		JLabel lblBan = new JLabel("Ban", SwingConstants.CENTER);
		lblBan.setForeground(Color.WHITE);
		lblBan.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblBan.setBounds(10, 11, 268, 19);
		panel.add(lblBan);
		
		
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
			 * If the panel text is "Ban" and the panel is clicked, the current seller selected from the list is banned. 
			 * The panel text is set to "Unban". Which changes the functionality of clicking the panel to unbanning the selected seller from the list.
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (lblBan.getText() == "Ban") { //If the panel text is "Ban"
					
					/**
					 * Create and display an options dialog to confirm if the admin wants to ban the seller.
					 */

					int n = JOptionPane.showOptionDialog(null, "Are you sure you want to ban seller: " + sellerID,
							"Ban seller", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

					if (n == 0) { //If they have selected yes
						
						Admin.getInstance().banSeller(sellerID); //Call the ban seller method from the admin class.

						lblBan.setText("Unban"); //Set the text to "Unban", so the admin can unban the seller if they wish to.
					}
				} else if (lblBan.getText() == "Unban") { //If the panel text is "Unban"
					
					/**
					 * Create an options dialog to confirm if the admin wants to unban the seller.
					 */
					
					int n = JOptionPane.showOptionDialog(null, "Are you sure you want to unban seller: " + sellerID,
							"Unban seller", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					
					if (n == 0) { //If they have selected yes
						
						Admin.getInstance().unBanSeller(sellerID); //Call the unban seller method from the admin class.
					
					lblBan.setText("Ban"); //Set the text to "Ban", so the admin can unban the seller if they wish to.
					}
				}
			}
		});
		
		/**
		 * Surround the sellersList with a scroll pane, to ensure sellers can still be seen if the list gets too long.
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 99, 288, 369);
		frame.getContentPane().add(scrollPane);

		sellersList = new JList<String>();
		scrollPane.setViewportView(sellersList);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) sellersList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * sellersList has a selection listener, to determine which seller the admin has currently selected.
		 * This information is used to determine the sellerID, and setup the product and their review details to the right of the JList.
		 */

		sellersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				/**
				 * Check if you have selected a value and get that value. 
				 * The selected value is the sellerID.
				 */

				if (sellersList.getSelectedValue() != null) {
					sellerID = sellersList.getSelectedValue();
				}

				try {
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query used to get the seller's details.
					 * Use a prepared statement to setup query.
					 * Set the sellerID in the query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query = "SELECT User.First_Name, User.Last_Name, User.Email FROM User INNER JOIN Seller ON Seller.Email = User.Email WHERE User.Email = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, sellerID);
					ResultSet rs = pst.executeQuery();
					
					/**
					 * Result set used to capture the query results.
					 * While there are results, set the appropriate labels, to convey the seller's email, first name and last name.
					 */

					while (rs.next()) {

						lblSellerEmail.setText(rs.getString("Email"));
						lblSellerFirstName.setText("First name: " + rs.getString("First_Name"));
						lblSellerLastName.setText("Last name: " + rs.getString("Last_Name"));
					}
					
					/**
					 * Close the result set, prepared statement and connection.
					 */
					
					rs.close();
					pst.close();
					connection.close();
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query used to get the seller's product details
					 * Use a prepared statement to setup query.
					 * Set the sellerID in the query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query2 = "SELECT Products.ID, Products.Name FROM Products "
							+ "INNER JOIN Sells ON Products.ID = Sells.ID WHERE Sells.Email = ?";
					PreparedStatement pst2 = connection.prepareStatement(query2);
					pst2.setString(1, sellerID);
					ResultSet rs2 = pst2.executeQuery();

					DefaultListModel<String> sellerProducts = new DefaultListModel<String>(); //Create the DefaultListModel to contain the product ID and name

					sellerProducts.removeAllElements(); //Remove any elements from the DLM if there are any.
					
					/**
					 * While there are results, create a string reference to the product id and name, and add it to the DLM.
					 * Set the model for the sellerProductsList to the sellerProducts DefaultListModel.
					 * Set the selected index to the first item in the list.
					 */
					
					while (rs2.next()) {
						sellerProducts.addElement("(id:" + rs2.getInt("ID") + ") " + rs2.getString("Name"));

					}
					sellerProductsList.setModel(sellerProducts);
					sellerProductsList.setSelectedIndex(0);
					
					/**
					 * Close the result set, prepared statement and connection.
					 */

					rs2.close();
					pst2.close();
					connection.close();
					
					/**
					 * Make a connection to database.
					 * Construct the query. Query used to get the sellers that are not banned.
					 * Use a prepared statement to setup query.
					 * Set the sellerID in the query.
					 * Execute the query.
					 */
					
					connection = dbConnection.dbConnector();
					String query3 = "SELECT * FROM Seller WHERE Email = ? AND Banned = 'No'";
					PreparedStatement pst3 = connection.prepareStatement(query3);
					pst3.setString(1, sellerID);
					ResultSet rs3 = pst3.executeQuery();
					
					/**
					 * While there are results, meaning the current selected seller from the JList is banned, set the panel label to ban.
					 * Otherwise set it to "unban", meaning the seller is not currently banned.
					 */
					
					if(rs3.next()) {
						lblBan.setText("Ban");
					} else {
						lblBan.setText("Unban");
					}
					
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

		sellersList.setBackground(new Color(36, 47, 65));
		sellersList.setForeground(new Color(204, 204, 204));
		sellersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sellersList.setFont(new Font("Century Gothic", Font.BOLD, 14));

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(328, 212, 456, 9);
		frame.getContentPane().add(separator_2);

		JLabel lblProducts = new JLabel("Products", SwingConstants.CENTER);
		lblProducts.setForeground(new Color(204, 204, 204));
		lblProducts.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblProducts.setBounds(328, 232, 62, 20);
		frame.getContentPane().add(lblProducts);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(328, 367, 456, 9);
		frame.getContentPane().add(separator_3);

		JLabel lblReviews = new JLabel("Reviews", SwingConstants.CENTER);
		lblReviews.setForeground(new Color(204, 204, 204));
		lblReviews.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblReviews.setBounds(328, 376, 62, 20);
		frame.getContentPane().add(lblReviews);

	}
	
	/**
	 * This method is used to setup the sellersList.
	 * Setup is done by using a DefaultListModel. This is used to provide a simple implementation of the ListModel.
	 */

	private void setDetails() {
		try {

			DefaultListModel<String> DLM = new DefaultListModel<String>(); //Create the DefaultListModel to contain the seller email.
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the sellers email's from the seller table.
			 * Use a prepared statement to setup query.
			 * Set the productID in the query.
			 * Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT Email FROM Seller";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			DLM.removeAllElements(); //Remove any elements from the DLM if there are any.
			
			/**
			 * While there are results, create a string reference to the seller's email, and add it to the DLM.
			 * Set the model for the sellersList to the DLM.
			 * Set the selected index to the first item in the list.
			 */

			while (rs.next()) {
				DLM.addElement(rs.getString("Email"));
			}
			sellersList.setModel(DLM);
			sellersList.setSelectedIndex(0);
			
			/**
			 * Close the result set, prepared statement and connection.
			 */

			rs.close();
			pst.close();
			connection.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Method used to set this frame visible.
	 */

	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * This method is created for testing purposes only.
	 * Used to get the seller's email
	 * @return
	 */
	
	public String getSellerEmail() {
		return lblSellerEmail.getText();
	}
}
