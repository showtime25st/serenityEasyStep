package serenity.test.step.pages;

import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

/**
 * Created by boomerang on 07.06.2018.
 */
public abstract class CustomElement extends Widget {

    protected CustomElement(WebElement element) {
        super(element);
    }

    public abstract  void click();

    public abstract  boolean isDisplayed();

    public abstract String getText();


}
