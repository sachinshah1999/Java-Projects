package testClasses;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Some GUI windows may popup when the tests are running. Ignore them until the test is finished.
 * Also if there are any message dialog boxes, press ok.
 * Below are all the test classes.
 * @author Sachin
 *
 */

	@RunWith(Suite.class)
	@SuiteClasses({ AdminMainScreenTest.class, ApproveSellerProductsScreenTest.class, BanSellersScreenTest.class,
		CustomerMainScreenTest.class, DisplayProductsScreenTest.class, PurchasedProductsScreenTest.class, WishListScreenTest.class, 
		dbConnectionTest.class,
		AdminTest.class, CustomerTest.class, SellerTest.class, UserTest.class, 
		AddProductsScreenTest.class, CurrentListingsScreenTest.class, SellerMainScreenTest.class, SoldProductsScreenTest.class, 
		LoginScreenTest.class, RegisterScreenTest.class, UserSettingsScreenTest.class} )
public class AllTests {
		
	} 
