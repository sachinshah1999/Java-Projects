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
 * This application window class, allows the admin to approve seller products
 * before they are listed onto the market.
 * 
 * @author Sachin
 *
 */

public class ApproveSellerProductsScreen {

	/** Fields **/

	private JFrame frame; // Frame contains all the UI design elements, which will be viewed by the admin.
	private JList<String> notApprovedProductsList; // JList which contains String references to the products that have not been approved yet.
	private int productID; // The id of the product, currently selected in the JList.
	private Connection connection; // Used to provide connection to the database.
	private JLabel lblProductName; //Declared as variable for testing purposes. Used to represent the product's name.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApproveSellerProductsScreen window = new ApproveSellerProductsScreen();
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
	public ApproveSellerProductsScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Approve Products");
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

		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {

			/**
			 * Method to go back to the admin main screen. Dispose the current frame, create
			 * a adminMainScreen and call the show method. This method sets the frame in the
			 * adminMainScreen class visible.
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

		JLabel lblProductsAwaitingApproval = new JLabel("Products awaiting approval", SwingConstants.CENTER);
		lblProductsAwaitingApproval.setForeground(new Color(204, 204, 204));
		lblProductsAwaitingApproval.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblProductsAwaitingApproval.setBounds(10, 56, 288, 26);
		frame.getContentPane().add(lblProductsAwaitingApproval);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.WHITE);
		separator.setBounds(308, 56, 20, 442);
		frame.getContentPane().add(separator);

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
		lblPrice.setBounds(328, 290, 264, 20);
		frame.getContentPane().add(lblPrice);

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

		JLabel lblQuantity = new JLabel("Quantity of the product");
		lblQuantity.setForeground(new Color(204, 204, 204));
		lblQuantity.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblQuantity.setBounds(328, 321, 264, 20);
		frame.getContentPane().add(lblQuantity);

		JLabel lblProductSeller = new JLabel("Seller of the product");
		lblProductSeller.setForeground(new Color(204, 204, 204));
		lblProductSeller.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblProductSeller.setBounds(328, 352, 264, 20);
		frame.getContentPane().add(lblProductSeller);

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
			 * Method to approve the seller's product.
			 */

			@Override
			public void mouseClicked(MouseEvent arg0) {

				Admin.getInstance().approveSellerProduct(productID);

				setDetails(); // SetDetails called to "refresh" the JList. Ultimately removing the product from the JList.
			}
		});
		panel.setLayout(null);
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(607, 270, 177, 40);
		frame.getContentPane().add(panel);

		JLabel lblApproveProduct = new JLabel("Approve product");
		lblApproveProduct.setForeground(Color.WHITE);
		lblApproveProduct.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblApproveProduct.setBounds(32, 10, 113, 19);
		panel.add(lblApproveProduct);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(328, 383, 456, 9);
		frame.getContentPane().add(separator_3);

		/**
		 * Surround the notApprovedProductsList with a scroll pane, to ensure products
		 * can still be seen if the list gets too long.
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 99, 288, 399);
		frame.getContentPane().add(scrollPane);

		notApprovedProductsList = new JList<String>();
		scrollPane.setViewportView(notApprovedProductsList);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) notApprovedProductsList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);

		/**
		 * notApprovedProductsList has a selection listener, to determine which product
		 * the admin has currently selected. This information is used to determine the
		 * productID, and setup the product details to the right of the JList.
		 */

		notApprovedProductsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {

				/**
				 * Check if you have selected a value and if you have, remove all the letters
				 * from the value and parse it to an integer, to get the product ID.
				 */

				if (notApprovedProductsList.getSelectedValue() != null) {
					productID = Integer.parseInt(notApprovedProductsList.getSelectedValue().replaceAll("\\D+", ""));
				}

				try {

					/**
					 * Make a connection to database. Construct the query. INNER JOIN used to get
					 * the necessary product details from the Sells and Products table. Use a
					 * prepared statement to setup query. Set the productID in the query. Execute
					 * the query.
					 */

					connection = dbConnection.dbConnector();
					String query = "SELECT Products.*, Sells.Email FROM Products INNER JOIN Sells ON Products.ID = Sells.ID WHERE Approved = 'No' AND Products.ID = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, productID);
					ResultSet rs = pst.executeQuery();

					/**
					 * Result set used to capture the query results. While there are results.
					 * According to my query there should only be one. Set the text for the various
					 * labels. These labels convey the product details.
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

				} catch (Exception e) { // Catch any exceptions.
					e.printStackTrace(); // Print exception stack trace.
					JOptionPane.showMessageDialog(null, e); // Output the exception in a JOptionPane.
				}
			}

		}

		);

		notApprovedProductsList.setBackground(new Color(36, 47, 65));
		notApprovedProductsList.setForeground(new Color(204, 204, 204));
		notApprovedProductsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		notApprovedProductsList.setFont(new Font("Century Gothic", Font.BOLD, 14));

	}

	/**
	 * This method is used to setup the notApprovedProductsList. Setup is done by
	 * using a DefaultListModel. This is used to provide a simple implementation of
	 * the ListModel
	 */

	private void setDetails() {
		try {

			DefaultListModel<String> DLM = new DefaultListModel<String>(); // Create the DefaultListModel to contain the product ID and name

			/**
			 * Make a connection to database. Construct the query. Query gets the product
			 * name and id from the Products table, where the products have not been
			 * approved yet. Use a prepared statement to setup query. Set the productID in
			 * the query. Execute the query.
			 */

			connection = dbConnection.dbConnector();
			String query = "SELECT Products.Name, Products.ID FROM Products WHERE Approved = 'No'";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			DLM.removeAllElements(); // Remove any elements from the DLM if there are any.

			/**
			 * While there are results, create a string reference to the product id and
			 * name, and add it to the DLM. Set the model for the notApprovedProductsList to
			 * the DLM. Set the selected index to the first item in the list.
			 */

			while (rs.next()) {
				DLM.addElement("(id:" + rs.getInt("ID") + ") " + rs.getString("Name"));
			}
			notApprovedProductsList.setModel(DLM);
			notApprovedProductsList.setSelectedIndex(0);

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
	 * Used to get the product's name
	 * @return
	 */
	
	public String getProductName() {
		return lblProductName.getText();
	}
}
