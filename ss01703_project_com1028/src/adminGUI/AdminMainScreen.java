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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import database.dbConnection;
import implementationClasses.Admin;
import userGUI.LoginScreen;
import userGUI.UserSettingsScreen;


/**
 * This application window class, contains the admin's main screen.
 * From here the admin can navigate to other admin function screens or logout. 
 * @author Sachin
 *
 */

public class AdminMainScreen {

	
	/** Fields **/
	
	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the admin.
	private JLabel lblWelcomeAdmin; //Used to set the welcome text
	private Connection connection; //Used to provide connection to the database.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainScreen window = new AdminMainScreen();
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
	public AdminMainScreen() {
		initialize();
		setDetails();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/**
		 * Setup the frame settings.
		 */

		frame = new JFrame("Admin Dashboard");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(852, 514);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		/**
		 * Setup panel settings.
		 * This panel will be used as a top navigator.
		 */

		JPanel panel = new JPanel();
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(0, 0, 836, 45);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel logoutPanel = new JPanel();
		logoutPanel.addMouseListener(new MouseAdapter() {
			/**
			 * When panel is clicked, the admin is redirected to login screen.
			 * Get the singleton instance of the admin class, and call the logout method.
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				Admin.getInstance().logout();
			}
		});
		logoutPanel.setBounds(0, 0, 69, 45);
		panel.add(logoutPanel);
		logoutPanel.setLayout(null);
		logoutPanel.setBackground(new Color(36, 47, 65));
		
		/**
		 * Label to name the logout panel.
		 */
		
		JLabel lblLogout = new JLabel("Logout");
		lblLogout.setForeground(Color.WHITE);
		lblLogout.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblLogout.setBounds(12, 13, 45, 19);
		logoutPanel.add(lblLogout);
		
		/**
		 * Setup the welcome text settings.
		 */

		lblWelcomeAdmin = new JLabel("Welcome, Sachin Shah", SwingConstants.CENTER); //SwingCostants.CENTER to centre the text
		lblWelcomeAdmin.setForeground(new Color(204, 204, 204));
		lblWelcomeAdmin.setFont(new Font("Century Gothic", Font.PLAIN, 25));
		lblWelcomeAdmin.setBounds(24, 79, 743, 32);
		frame.getContentPane().add(lblWelcomeAdmin);
		
		/**
		 * Setup the approveSeller panel.
		 */

		JPanel approveSellerPanel = new JPanel();
		approveSellerPanel.addMouseListener(new MouseAdapter() {
			
			/**
			 * mousePressed and mouseReleased used to simulate clicking an actual button.
			 * Since I have used panels instead of buttons.
			 */
			
			@Override
			public void mousePressed(MouseEvent e) {
				approveSellerPanel.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				approveSellerPanel.setBackground(new Color(97, 212, 195));
			}
			/**
			 * When this panel is clicked, the admin is redirected to the approveSellerProductsScreen.
			 * This is where they can approve seller products before they are listed on the market.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose(); //Get rid of the frame
				ApproveSellerProductsScreen approve = new ApproveSellerProductsScreen(); //Create a approveSellerProductsScreen object
				approve.show(); //Call the show method on the object, to set the frame visible.
			}
		});
		approveSellerPanel.setLayout(null);
		approveSellerPanel.setBackground(new Color(97, 212, 195));
		approveSellerPanel.setBounds(24, 148, 232, 279);
		frame.getContentPane().add(approveSellerPanel);
		
		/**
		 * ApproveSellerProducts panel image setup.
		 */

		JLabel approveImg = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/approve.png")).getImage();
		approveImg.setIcon(new ImageIcon(img));
		approveImg.setBounds(30, 44, 160, 160);
		approveSellerPanel.add(approveImg);
		
		/**
		 * Label the approveSellerProducts panel.
		 */

		JLabel lblApproveSellerProducts = new JLabel("Approve seller products");
		lblApproveSellerProducts.setForeground(Color.WHITE);
		lblApproveSellerProducts.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblApproveSellerProducts.setBounds(11, 224, 209, 23);
		approveSellerPanel.add(lblApproveSellerProducts);
		
		/**
		 * banUnbanSellerPanel setup.
		 */

		JPanel banUnbanSellerPanel = new JPanel();
		banUnbanSellerPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose(); //Dispose the frame.
				BanSellersScreen banSellersScreen = new BanSellersScreen(); //Create a banSellersScreen object.
				banSellersScreen.show(); //Set the frame of this screen visible.
			}
			
			/**
			 * mousePressed and mouseReleased used to simulate clicking an actual button.
			 * Since I have used panels instead of buttons.
			 */

			@Override
			public void mousePressed(MouseEvent arg0) {
				banUnbanSellerPanel.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				banUnbanSellerPanel.setBackground(new Color(97, 212, 195));
			}
		});
		banUnbanSellerPanel.setLayout(null);
		banUnbanSellerPanel.setBackground(new Color(97, 212, 195));
		banUnbanSellerPanel.setBounds(301, 148, 232, 279);
		frame.getContentPane().add(banUnbanSellerPanel);
		
		/**
		 * banUban seller panel image.
		 */

		JLabel banImg = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/ban.png")).getImage();
		banImg.setIcon(new ImageIcon(img2));
		banImg.setBounds(36, 46, 160, 160);
		banImg.setForeground(Color.BLACK);
		banUnbanSellerPanel.add(banImg);
		
		/**
		 * Label to name the banUnban seller ban.
		 */

		JLabel lblBanUnban = new JLabel("Ban/unban sellers");
		lblBanUnban.setForeground(Color.WHITE);
		lblBanUnban.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblBanUnban.setBounds(40, 224, 152, 23);
		banUnbanSellerPanel.add(lblBanUnban);

		JPanel settingsPanel = new JPanel();
		settingsPanel.addMouseListener(new MouseAdapter() {
			
			/**
			 * mousePressed and mouseReleased used to simulate clicking an actual button.
			 * Since I have used panels instead of buttons.
			 */
			
			@Override
			public void mousePressed(MouseEvent e) {
				settingsPanel.setBackground(new Color(25, 157, 141));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				settingsPanel.setBackground(new Color(97, 212, 195));
			}
			/**
			 * When this panel is clicked, the admin is redirected to the userSettingsScreen. 
			 * From here they can change their first name, last name and password.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose(); //Dispose the frame
				UserSettingsScreen userSettingsScreen = new UserSettingsScreen(); //Create a userSettingsScreen object.
				userSettingsScreen.show(); //Set the frame in the object visible.
			}
		});
		settingsPanel.setLayout(null);
		settingsPanel.setBackground(new Color(97, 212, 195));
		settingsPanel.setBounds(573, 148, 232, 279);
		frame.getContentPane().add(settingsPanel);
		
		/**
		 * userSettings panel img setup.
		 */

		JLabel settingsImg = new JLabel("");
		Image img4 = new ImageIcon(this.getClass().getResource("/settingsSmall.png")).getImage();
		settingsImg.setIcon(new ImageIcon(img4));
		settingsImg.setBounds(36, 44, 160, 160);
		settingsPanel.add(settingsImg);
		
		/**
		 * userSettings label setup for panel identification.
		 */

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setForeground(Color.WHITE);
		lblSettings.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblSettings.setBounds(82, 224, 68, 23);
		settingsPanel.add(lblSettings);
	}
	
	/**
	 * This method is used to set the admin's main screen welcome text. 
	 * This will be called in the userSettingsScreen, when the first or last name is updated.
	 * @param s - The text that is going to be set as the welcome text.
	 */

	public void setWelcomeText(String s) {
		lblWelcomeAdmin.setText(s);
	}
	
	/**
	 * This is method is specifically for JUnit testing.
	 * More specifically to test for successful GUI object creation.
	 * @return
	 */
	
	public String getWelcomeText() {
		return lblWelcomeAdmin.getText();
	}
	
	/**
	 * This method is used to open and view this window.
	 */

	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * This method is used to setup the lblWelcomeAdmin text.
	 */
	
	private void setDetails() {
		
		String primaryKey = LoginScreen.getEmail(); //Get the current admin's primary key.

		try {
			
			/**
			 * Make a connection to database.
			 * Construct the query. Query gets all the current logged in admin's first name and last name.
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
			 * While there are results, set the text for the WelcomeAdmin label.
			 */
			
			while (rs.next()) {
				lblWelcomeAdmin.setText("Welcome, " + rs.getString("First_Name") + " " + rs.getString("Last_Name"));
				Admin.getInstance().setFirstName(rs.getString("First_Name"));
				Admin.getInstance().setLastName(rs.getString("Last_Name"));
			}
			
			/**
			 * Close the result set, prepared statement and connection.
			 */
			
			rs.close();
			pst.close();
			connection.close();

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

	}
}
