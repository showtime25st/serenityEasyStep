package serenity.test.step.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by boomerang on 08.06.2018.
 */
@AndroidFindBy(id = "bottom_navigation")
public class FooterMenu extends CustomElement {

    protected FooterMenu(WebElement element) {
        super(element);
    }

    @AndroidFindBy(id = "bottom_navigation_container")
    private List<MobileElement> pymentsButtons;


    public List<MobileElement> getPymentsButtons() {
        return pymentsButtons;
    }


    public void openMainPage() {
        pymentsButtons.get(0).click();

    }

    public void openProductsPage() {
        pymentsButtons.get(1).click();

    }

    public void openPaymentsPage() {
        pymentsButtons.get(2).click();

    }

    public void openShowCasePage() {
        pymentsButtons.get(3).click();

    }

    public void openOthersPage() {
        pymentsButtons.get(4).click();

    }

    public boolean isDisplayed(){
        return  pymentsButtons.size()>0;
    }

    @Override
    public void click() {

    }

    @Override
    public String getText() {
        return null;
    }
}
