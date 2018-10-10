package sellerGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import implementationClasses.Seller;
import userGUI.LoginScreen;

/**
 * This application window class, allows a seller to list a product on the market.
 * @author Sachin
 *
 */

//TODO 

public class AddProductScreen {

	private JFrame frame; //Frame contains all the UI design elements, which will be viewed by the seller.
	private SellerMainScreen sellerMainScreen; //Object reference to the sellerMainScreen

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddProductScreen window = new AddProductScreen();
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
	public AddProductScreen() {
		initialize();
		sellerMainScreen = new SellerMainScreen();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Add product");
		frame.getContentPane().setBackground(new Color(36, 47, 65));
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(815, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(97, 212, 195));
		panel.setBounds(0, 0, 405, 723);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblAddProduct = new JLabel("Add a product");
		lblAddProduct.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		lblAddProduct.setForeground(new Color(255, 255, 255));
		lblAddProduct.setBounds(136, 64, 132, 23);
		panel.add(lblAddProduct);

		JLabel addProductImg = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/addProductLarge.png")).getImage();
		addProductImg.setIcon(new ImageIcon(img));
		addProductImg.setForeground(new Color(204, 204, 204));
		addProductImg.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		addProductImg.setBounds(74, 143, 256, 256);
		panel.add(addProductImg);

		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.WHITE);
		separator_6.setBounds(37, 422, 331, 9);
		panel.add(separator_6);

		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.WHITE);
		separator_7.setBounds(79, 458, 246, 2);
		panel.add(separator_7);
		
		/**
		 * When label is clicked, the seller is redirected back to the seller main screen.
		 */

		JLabel lblBack = new JLabel("");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				sellerMainScreen.show();
			}
		});
		lblBack.setBounds(10, 11, 32, 43);
		panel.add(lblBack);
		lblBack.setForeground(Color.WHITE);
		lblBack.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		Image img2 = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		lblBack.setIcon(new ImageIcon(img2));
		lblBack.setForeground(Color.WHITE);
		lblBack.setFont(new Font("Century Gothic", Font.PLAIN, 18));

		JLabel lblProductDetails = new JLabel("Product details");
		lblProductDetails.setBounds(482, 11, 103, 19);
		frame.getContentPane().add(lblProductDetails);
		lblProductDetails.setForeground(new Color(204, 204, 204));
		lblProductDetails.setFont(new Font("Century Gothic", Font.PLAIN, 14));

		JLabel lblProductName = new JLabel("NAME");
		lblProductName.setForeground(new Color(204, 204, 204));
		lblProductName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblProductName.setBounds(482, 51, 40, 19);
		frame.getContentPane().add(lblProductName);

		JTextField productNameField = new JTextField();
		productNameField.setCaretColor(Color.WHITE);
		productNameField.setForeground(Color.WHITE);
		productNameField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		productNameField.setBorder(null);
		productNameField.setBackground(new Color(36, 47, 65));
		productNameField.setBounds(482, 81, 246, 20);
		frame.getContentPane().add(productNameField);

		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.WHITE);
		separator_4.setBounds(482, 103, 246, 2);
		frame.getContentPane().add(separator_4);

		JLabel lblDescription = new JLabel("DESCRIPTION");
		lblDescription.setForeground(new Color(204, 204, 204));
		lblDescription.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblDescription.setBounds(482, 130, 87, 19);
		frame.getContentPane().add(lblDescription);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(255, 255, 255));
		separator.setBounds(482, 261, 246, 2);
		frame.getContentPane().add(separator);
		;

		JTextArea description = new JTextArea();
		description.setCaretColor(Color.WHITE);
		description.setForeground(Color.WHITE);
		description.setBackground(new Color(36, 47, 65));
		description.setBounds(482, 165, 246, 85);
		description.setBorder(null);
		description.setFont(new Font("Century Gothic", Font.PLAIN, 14));

		frame.getContentPane().add(description);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(482, 343, 246, 2);
		frame.getContentPane().add(separator_1);

		JTextField priceTextField = new JTextField();
		priceTextField.setCaretColor(Color.WHITE);
		priceTextField.setForeground(Color.WHITE);
		priceTextField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		priceTextField.setDisabledTextColor(new Color(204, 204, 204));
		priceTextField.setColumns(10);
		priceTextField.setBorder(null);
		priceTextField.setBackground(new Color(36, 47, 65));
		priceTextField.setBounds(482, 321, 246, 20);
		frame.getContentPane().add(priceTextField);

		JLabel lblPrice = new JLabel("PRICE");
		lblPrice.setForeground(new Color(204, 204, 204));
		lblPrice.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblPrice.setBounds(482, 291, 40, 19);
		frame.getContentPane().add(lblPrice);

		JLabel lblQuantity = new JLabel("QUANTITY");
		lblQuantity.setForeground(new Color(204, 204, 204));
		lblQuantity.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblQuantity.setBounds(482, 370, 65, 19);
		frame.getContentPane().add(lblQuantity);

		JTextField quantityTextField = new JTextField();
		quantityTextField.setForeground(Color.WHITE);
		quantityTextField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		quantityTextField.setDisabledTextColor(new Color(204, 204, 204));
		quantityTextField.setColumns(10);
		quantityTextField.setCaretColor(Color.WHITE);
		quantityTextField.setBorder(null);
		quantityTextField.setBackground(new Color(36, 47, 65));
		quantityTextField.setBounds(482, 400, 246, 20);
		frame.getContentPane().add(quantityTextField);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(482, 422, 246, 2);
		frame.getContentPane().add(separator_2);

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
			 * When the panel is clicked, the product is sent for approval to the admin, before it is placed onto the market.
			 */
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				/**
				 * Validation to ensure the fields are not empty, and the correct data-type is in each field.
				 */

				try {
					if (productNameField.getText().equals("") || priceTextField.getText().equals("") || quantityTextField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Fields: Name, Price and Quantity MUST be completed!");
					} else if (!priceTextField.getText().matches("[0-9]+([.][0-9]{1,2})?") || Double.parseDouble(priceTextField.getText()) <= 0) {
						JOptionPane.showMessageDialog(null, "Please enter a valid price! e.g. 2.99");
					} else if (!quantityTextField.getText().matches("[0-9]*") || Integer.parseInt(quantityTextField.getText()) <= 0) {
						JOptionPane.showMessageDialog(null, "Please enter a valid quantity!");
					} else {
						
						/**
						 * Call the seller listProduct method to list the product.
						 * If the method returns true, meaning it executed correctly; Show the seller main screen and dispose the addProductScreen frame. 
						 */
						
						if(Seller.getInstance().listProduct(productNameField.getText(), description.getText(), Double.parseDouble(quantityTextField.getText()), 
								Integer.parseInt(quantityTextField.getText()))) {
							JOptionPane.showMessageDialog(null, "Product will be listed, for approval by an admin!"); //Inform the seller.
							frame.dispose();
							sellerMainScreen.show();
						}
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}

			}
		});
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(97, 212, 195));
		panel_1.setBounds(482, 459, 246, 40);
		frame.getContentPane().add(panel_1);

		JLabel lblListProduct = new JLabel("List Product");
		lblListProduct.setForeground(Color.WHITE);
		lblListProduct.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblListProduct.setBounds(85, 10, 75, 19);
		panel_1.add(lblListProduct);
	}
	
	/**
	 * This method is used to open and view this window.
	 */

	public void show() {
		frame.setVisible(true);
	}
}
