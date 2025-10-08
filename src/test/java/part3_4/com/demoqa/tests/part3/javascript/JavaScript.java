package part3_4.com.demoqa.tests.part3.javascript;

import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;

public class JavaScript extends BaseTest {

    @Test
    public void testScrollingToElement(){
        homePage.goToForms();
    }
}
