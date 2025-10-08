package part3_4.com.demoqa.tests.part3.elements;

import org.testng.Assert;
import part3_4.com.demoqa.tests.base.BaseTest;
import org.testng.annotations.Test;

public class LinksTest extends BaseTest {

    @Test
    public void testLinks(){
        String actualResponse = homePage
                .goToElements()
                .clickLinks()
                .waitForPage()                      // Asegura que estamos en "Links"
                .clickBadRequestLink()              // ← usamos el retorno (LinksPage)
                .getResponse();

        Assert.assertTrue(
                actualResponse.contains("400") &&
                        actualResponse.toLowerCase().contains("bad request"),
                "\nActual Response: " + actualResponse +
                        "\nExpected to contain '400' and 'Bad Request'");
    }
}
