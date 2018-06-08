package serenity.test.step.steps.serenity;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import serenity.test.step.pages.LoginPage;

/**
 * Created by boomerang on 07.06.2018.
 */
public class LoginSteps extends ScenarioSteps {

    LoginPage loginPage;

    @Step("")
    public void fillLogin(String login){
        loginPage.getLoginBox().sendKeys(login);
    }


    @Step("")
    public void fillPass(String pass){
        loginPage.getPasswordBox().sendKeys(pass);
    }

}
