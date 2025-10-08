package part3_4.com.demoqa.tests.part3.elements;

import org.testng.annotations.Test;
import part3_4.com.demoqa.tests.base.BaseTest;


public class WebTableTest extends BaseTest {

    @Test
    public void testWebTable_AddRecord() {
        var webTables = homePage
                .goToElements()       // ahora sí navega (o hace fallback a URL)
                .clickWebTables()     // espera header/URL de Web Tables
                .waitForPage()
                .openAddForm()
                .setFirstName("Anna")
                .setLastName("López")
                .setEmail("anna@example.com")
                .setAge("27")
                .setSalary("50000")
                .setDepartment("QA")
                .submit();
    }

}
