package part2.com.saucedemo.test.login;

import org.testng.Assert;
import part2.com.saucedemo.base.BaseTest;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    public void testLoginErrorMessage() throws InterruptedException {
        loginPage.setUsername("standard_user");
        loginPage.setPassword("xyz3400");
        loginPage.clickLoginButton();
        Thread.sleep(2000);
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains("Epic sadface"));
    }

}
