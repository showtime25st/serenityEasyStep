package serenity.test.step.features;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import serenity.test.step.steps.serenity.LoginSteps;

/**
 * Created by boomerang on 08.06.2018.
 */

//@Concurrent
@RunWith(SerenityRunner.class)
public class TestStory {

    @Managed(uniqueSession = false)
    public static WebDriver webdriver;

    @Steps
    LoginSteps loginSteps;

    @BeforeClass
    public static void getData() {

    }

    @AfterClass
    public static void stopAppium() {
        webdriver.quit();
    }

    @WithTag("TestSuite:smoke")
    @Title("Карта банкоматов")
    //@Issues({"VTBRTBR-61449 МБ. Прочее - > Банкоматы. Параметры фильтрации применяются только после перезахода на страницу \"Банкоматы\", должны применяться сразу."})
    @Test
    public void cashpointSearchTest() {

        SoftAssert soft = new SoftAssert();

        loginSteps.fillLogin("11811362");
        loginSteps.fillPass("123");

        soft.assertAll();

    }

}
