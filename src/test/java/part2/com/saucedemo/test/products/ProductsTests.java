package part2.com.saucedemo.test.products;

import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import part2.com.saucedemo.base.BaseTest;
import org.testng.annotations.Test;

public class ProductsTests extends BaseTest{

    @Test
    public void testProductsHeaderIsDisplayed(){
        ProductsPage productsPage = loginPage.logIntoApplication("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isProductsHeaderDisplayed(), "\n Products Header Is Not Displayed");
    }
}
