package serenity.test.step.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import net.serenitybdd.core.pages.PageObject;

/**
 * Created by boomerang on 07.06.2018.
 */
public class LoginPage extends PageObject {

    @AndroidFindBy(id = "user_real_name")
    private MobileElement userRealName;

    @HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
    @AndroidFindBy(xpath = "//*[contains(@resource-id,'error_message_text')]")
    @AndroidFindBy(id = "error_message_text")
    private MobileElement errorMessageText;

    @AndroidFindBy(id = "login")
    private MobileElement loginBox;

    @AndroidFindBy(id = "password")
    private MobileElement passwordBox;


    @AndroidFindBy(id = "button_login")
    private MobileElement loginButton;

    @AndroidFindBy(id = "login_old_way")
    private MobileElement loginOldWayButton;

    @AndroidFindBy(id = "forward")
    private MobileElement forwardButton;

    @AndroidFindBy(id = "go_back")
    private MobileElement goBackButton;

    @AndroidFindBy(id = "switch_user")
    private MobileElement switchUserButton;

    public MobileElement getUserRealName() {
        return userRealName;
    }

    public MobileElement getLoginOldWayButton() {
        return loginOldWayButton;
    }

    public MobileElement getForwardButton() {
        return forwardButton;
    }

    public MobileElement getGoBackButton() {
        return goBackButton;
    }

    public MobileElement getLoginBox() {
        return loginBox;
    }

    public MobileElement getPasswordBox() {
        return passwordBox;
    }

    public MobileElement getLoginButton() {
        return loginButton;
    }

    public MobileElement getSwitchUserButton() {
        return switchUserButton;
    }

    public MobileElement getErrorMessage() {
        return errorMessageText;
    }


}



