package serenity.test.step.utils;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.logging.LogEntry;
import serenity.test.step.pages.CustomElement;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
/**
 * Created by boomerang on 07.06.2018.
 */
public class Utils  {

    // Проверяем отображается ли элемент в данный момент
    public static boolean isElementDisplayed(MobileElement element) {
        if (element == null) {
            return false;
        }
        try {
            return element.isDisplayed();

        } catch (NoSuchElementException ex) {
            return false;
        } catch (StaleElementReferenceException ex2) {
            return false;
        }
    }


    // Проверяем отображается ли элемент в данный момент
    public static boolean isElementDisplayed(By by) {
        try {

            return getDriver().findElement(by).isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public static boolean isCustomElementDisplayed(CustomElement element) {
        if (element == null) {
            return false;
        }
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }


    // Ждем события
    public static void waitUntillElementHasText(String text, MobileElement element) {
        long elapsedTime = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < elapsedTime) {
            try {
                if (element.getText().contains(text)) {
                    return;
                }
            } catch (NoSuchElementException ex) {

            }
        }
    }

    // Ждем появление элемнта
    public static void waitUntillElementIsPresent(MobileElement element) {
        boolean result = false;
        long elapsedTime = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < elapsedTime) {
            try {
                result = isElementDisplayed(element);
                if (result) {
                    return;
                }
            } catch (NoSuchElementException ex) {

            } catch (StaleElementReferenceException ex1) {

            } catch (NullPointerException ex2) {

            }
        }
    }


    // Ждем появление элемнта определенное время
    public static void waitUntillElementIsPresent(MobileElement element, int time) {
        boolean result = false;
        long elapsedTime = System.currentTimeMillis() + time;
        while (System.currentTimeMillis() < elapsedTime) {
            try {
                result = isElementDisplayed(element);
                if (result) {
                    return;
                }
            } catch (NoSuchElementException ex) {

            } catch (StaleElementReferenceException ex1) {

            } catch (NullPointerException ex2) {

            }
        }
    }

    public static void waitUntillElementIsPresent(CustomElement element) {
        boolean result = false;
        long elapsedTime = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < elapsedTime) {
            try {
                result = isCustomElementDisplayed(element);
                if (result) {
                    return;
                }
            } catch (NoSuchElementException ex) {

            } catch (IndexOutOfBoundsException ex) {

            } catch (NullPointerException ex) {

            } catch (StaleElementReferenceException ex) {
            }

        }
    }

    public static void waitUntillElementIsPresent(CustomElement element, int time) {
        boolean result = false;
        long elapsedTime = System.currentTimeMillis() + time;
        while (System.currentTimeMillis() < elapsedTime) {
            try {
                result = isCustomElementDisplayed(element);
                if (result) {
                    return;
                }
            } catch (NoSuchElementException ex) {

            } catch (IndexOutOfBoundsException ex) {

            }
        }
    }

    // Ждем появления одного конкретного элемента из списка по индексу
    public static void waitUntillElementIsPresent(List<MobileElement> elements, int index) {
        long elapsedTime = System.currentTimeMillis() + 20000;
        while (System.currentTimeMillis() < elapsedTime) {
            if (isElementDisplayed(getElementWithIndex(index, elements))) {
                return;
            }
        }
    }

    public static void waitUntillCustomElementIsPresent(List<? extends CustomElement> elements, int index) {
        long elapsedTime = System.currentTimeMillis() + 20000;
        while (System.currentTimeMillis() < elapsedTime) {
            if (isCustomElementDisplayed(getCustomElementWithIndex(index, elements))) {
                return;
            }
        }
    }

    // Ждем появления одного конкретного элемента из списка по имени
    public static void waitUntillElementIsPresent(List<MobileElement> elements, String value) {
        long elapsedTime = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < elapsedTime) {
            if (isElementDisplayed(getElementWithText(value, elements))) {
                return;
            }
        }
    }

    // Ждем появления списка
    public static void waitUntillListIsPresent(List<?> list) {
        long elapsedTime = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < elapsedTime) {
            try {
                if (list.size() > 0) {
                    return;
                }
            } catch (NoSuchElementException ex) {

            } catch (StaleElementReferenceException ex1) {

            } catch (NullPointerException ex2) {

            }
        }
    }

    // Выбираем кастомный элемент из списка
    public static <T extends CustomElement> T getCustomElementWithText(String name, List<T> elementList) {
        smallWait();

        for (T element : elementList) {
            try {
                if (element.getText().contains(name)) {
                    return element;
                }
            } catch (NoSuchElementException ex) {

            }

        }

        return null;
    }

    // Выбираем элемент из списка
    public static <T extends CustomElement> T getCustomElementWithIndex(int index, List<T> elementList) {
        smallWait();
        try {
            if (elementList.get(index).isDisplayed()) {
                return elementList.get(index);

            }
        } catch (NoSuchElementException ex) {

        } catch (IndexOutOfBoundsException ex) {

        }
        return null;
    }


    public static <T extends MobileElement> T getElementWithText(String name, List<T> elementList) {
        waitUntillListIsPresent(elementList);
        smallWait();
        for (T element : elementList) {
            try {
                if (element.getText().contains(name)) {
                    return element;
                }
            } catch (NoSuchElementException ex) {

            } catch (NullPointerException ex) {

            }
        }

        return null;
    }

    // Выбираем элемент из списка
    public static MobileElement getElementWithIndex(int index, List<MobileElement> elementList) {
        smallWait();
        try {
            if (elementList.get(index).isDisplayed()) {
                return elementList.get(index);

            }
        } catch (NoSuchElementException ex) {

        } catch (IndexOutOfBoundsException ex) {

        }
        return null;
    }

    public static void smallWait() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitMills(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Дата в нужном формате, пример: dd.MM.yyyy
    public static String getCuurentDateInFormat(String dateAndTimeFormat) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(dateAndTimeFormat);
        return sdf.format(date);
    }


    public static String getDate(String year, String month, String day) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(new Locale("ru"))
                .format(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
    }

    //Возвращает дату в числовом выражении и месяц словами
    public static String getCurrentDateAndMonthInRussian() {
        Date jud = null;

        try {
            jud = new SimpleDateFormat("MM-dd").parse(getCuurentDateInFormat("MM-dd"));
        } catch (Exception ex) {

        }

        String dateWithMonth = new SimpleDateFormat("dd MMMM", Locale.forLanguageTag("ru")).format(jud);

        return dateWithMonth;
    }

    public static String currentDatePlusDaysAndYears(int amoutOfYears, int amountOfDays) {
        Date dt = new Date();
        LocalDateTime date = LocalDateTime.from(dt.toInstant()
                .atZone(ZoneId.of("UTC+3"))).plusYears(amoutOfYears).plusDays(amountOfDays);
        Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return new SimpleDateFormat("dd MMMM yyyy").format(out);
    }


    public static String getCurrentDatePlusPeriodAndShowInFormat(String dateFormat, int years, int moths, int days) {
        Date dt = new Date();
        LocalDateTime date = LocalDateTime.from(dt.toInstant()
                .atZone(ZoneId.of("UTC+3"))).plusYears(years).plusMonths(moths).plusDays(days);
        Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return new SimpleDateFormat(dateFormat).format(out);
    }

    public static String getCurrentDatePlusPeriodAndShowInFormatRus(String dateFormat, int years, int moths, int days) {
        Date dt = new Date();
        LocalDateTime date = LocalDateTime.from(dt.toInstant()
                .atZone(ZoneId.of("UTC+3"))).plusYears(years).plusMonths(moths).plusDays(days);
        Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return new SimpleDateFormat(dateFormat, Locale.forLanguageTag("ru")).format(out);
    }


    //Получить дату в нужном формате
    public static String getDateInFormat(String data, String dateFormat, String wantedFormat) {


        Date parsingDate = null;
        try {
            parsingDate = new SimpleDateFormat(dateFormat).parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(wantedFormat).format(parsingDate);

    }

    public static boolean isKeyBoardDisplayed() {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        try {
            ((MobileDriver) driver).hideKeyboard();
            return true;
        } catch (WebDriverException ex) {
            return false;
        }
    }

    // Убрать клавиатуру
    @Step()
    public static void hidekeyBoard() {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        waitMills(1500);
        try {
            ((MobileDriver) driver).hideKeyboard();
        } catch (WebDriverException ex) {
        }
    }

    //Ввести значение и спрятать клавиатуру
    public static void enterAndHideKeyBoard(String value, MobileElement element) {
        waitUntillElementIsPresent(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        hidekeyBoard();
    }

    public static void setValueAndHideKeyBoard(String value, MobileElement element) {
        element.click();
        element.setValue(value);
        hidekeyBoard();
    }

    //Ввести значение МЕДЛЕННО  и спрятать клавиатуру
    public static void enterSlowAndHideKeyBoard(String value, MobileElement element) {
        element.clear();
        enterTextSlow(value, element);
        hidekeyBoard();
    }

    public static void enterTextSlow(String text, MobileElement element) {
        for (char simbol : text.toCharArray()) {
            String simbolString = simbol + "";
            element.setValue(simbolString);
            waitMills(1000);
        }
    }

    //IV. Проверить введеное значние
    public static void checkEnteredValue(String value, MobileElement element) {
        int i = 0;
        try {
            while (isElementDisplayed(element) && (!value.equals(element.getText())) && (i <= 10)) {

                element.click();
                element.clear();
                element.sendKeys(value);
                smallWait();
            }
        } catch (NoSuchElementException ex) {
            return;
        } catch (StaleElementReferenceException ex) {
            return;
        }

    }


    //  Скролл к определенным координатам
    public static void scroll(int fromX, int fromY, int toX, int toY) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        new TouchAction((MobileDriver) driver).press(fromX, fromY)
                .waitAction(Duration.ofSeconds(1))
                .moveTo(toX, toY).release().perform();
    }

    //  Скролл к определенным координатам
    public static void dragAndDrop(int fromX, int fromY, int toX, int toY) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        new TouchAction((MobileDriver) driver).longPress(fromX, fromY).moveTo(toX, toY).release().perform();
    }

    //  Скролл к определенным координатам
    private static void scrollTo(MobileElement element) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        TouchAction touchAction = new TouchAction((MobileDriver) driver).moveTo(element);
    }

    //  Скролл к определенным координатам
    public static void scrollTo(CustomElement element) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        TouchAction touchAction = new TouchAction((MobileDriver) driver).moveTo(element.getWrappedElement());
    }

    // Скролл вниз на половину экрана
    public static void scrollDown() {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        //if pressX was zero it didn't work for me
        int pressX = driver.manage().window().getSize().width / 2;
        // 4/5 of the screen as the bottom finger-press point
        int bottomY = driver.manage().window().getSize().height * 4 / 6;
        // just non zero point, as it didn't scroll to zero normally
        int topY = driver.manage().window().getSize().height / 6;
        //scroll with TouchAction by itself
        scroll(pressX, bottomY, pressX, topY);

    }

    //Скролл элемента вправо
    public static void scrollElementToRight(MobileElement element) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        int pressX = element.getLocation().getX() / 2;
        int bottomY = element.getLocation().getY();
        int moveToX = driver.manage().window().getSize().width * 9 / 10;
        scroll(pressX, bottomY, moveToX, bottomY);
    }

    //Скролл элемента вправо
    public static void scrollElementToRight(CustomElement element) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        int bottomY = ((MobileElement) element.getWrappedElement()).getLocation().getY();
        int pressX = ((MobileElement) element.getWrappedElement()).getLocation().getX() / 2;
        int moveToX = driver.manage().window().getSize().width * 9 / 10;
        scroll(pressX, bottomY, moveToX, bottomY);
    }

    //Скролл элемента влево
    public static void scrollElementToTheLeft(MobileElement element) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        int pressX = driver.manage().window().getSize().width * 8 / 10;
        int bottomY = element.getLocation().getY();
//      int moveToX = element.getLocation().getX();
        int moveToX = driver.manage().window().getSize().width / 10;
        scroll(pressX, bottomY, moveToX, bottomY);
    }

    public static void scrollElementToTheLeft(CustomElement element) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        int pressX = driver.manage().window().getSize().width - 5;
        int bottomY = ((MobileElement) element.getWrappedElement()).getLocation().getY();
        int moveToX = ((MobileElement) element.getWrappedElement()).getLocation().getX();
        scroll(pressX, bottomY, moveToX, bottomY);
    }

    public static boolean swipeToDirection_iOS_XCTest(MobileElement el, String direction) {
        try {
            WebDriver facade = getDriver();
            WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> swipeObject = new HashMap<String, String>();
            if (direction.equals("d")) {
                swipeObject.put("direction", "down");
            } else if (direction.equals("u")) {
                swipeObject.put("direction", "up");
            } else if (direction.equals("l")) {
                swipeObject.put("direction", "left");
            } else if (direction.equals("r")) {
                swipeObject.put("direction", "right");
            }
            swipeObject.put("element", el.getId());
            js.executeScript("mobile:swipe", swipeObject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static <T extends CustomElement> boolean swipeToDirection_iOS_XCTest(T el, String direction) {
        try {
            WebDriver facade = getDriver();
            WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> swipeObject = new HashMap<String, String>();
            if (direction.equals("d")) {
                swipeObject.put("direction", "down");
            } else if (direction.equals("u")) {
                swipeObject.put("direction", "up");
            } else if (direction.equals("l")) {
                swipeObject.put("direction", "left");
            } else if (direction.equals("r")) {
                swipeObject.put("direction", "right");
            }
            swipeObject.put("element", ((MobileElement) el.getWrappedElement()).getId());
            js.executeScript("mobile:swipe", swipeObject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static MobileElement scrollElementLeftToTheText(MobileElement element, String text) {

        for (int i = 0; i < 10; i++) {
            try {

                if (element.getText().contains(text)) {
                    return element;

                }
            } catch (NoSuchElementException ex) {

            }
            scrollElementToRight(element);
        }

        return null;
    }

    // Скролл вверх на половину экрана
    public static void scrollUp() {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        //if pressX was zero it didn't work for me
        int pressX = driver.manage().window().getSize().width / 2;
        // 4/5 of the screen as the bottom finger-press point
        int bottomY = driver.manage().window().getSize().height / 6;
        // just non zero point, as it didn't scroll to zero normally
        int topY = driver.manage().window().getSize().height * 4 / 5;
        //scroll with TouchAction by itself
//        if (Utills.getProrerty("appium.platformVersion").equals("6.0")) {
        scroll(pressX, bottomY + 78, pressX, topY);
//        } else {
//            scroll(pressX, bottomY, pressX, topY);
//        }
    }

    // Скролим вниз к элементу
    public static MobileElement scrollDownToElement(MobileElement element) {
        smallWait();

        for (int i = 0; i < 10; i++) {
            try {
                if (isElementDisplayed(element)) {
                    waitUntillElementIsPresent(element);
                    return element;

                }
            } catch (NoSuchElementException ex) {

            } catch (IndexOutOfBoundsException ex) {

            }

            scrollDown();
            smallWait();

        }
        return null;
    }


    // Скролим вверх к элементу
    public static MobileElement scrollUpToElement(MobileElement element) {
        try {
            for (int i = 0; i < 10; i++) {
                if (isElementDisplayed(element)) {
                    return element;

                }
                scrollUp();
            }
        } catch (NoSuchElementException ex) {

        }
        return null;
    }

    // Скролим вниз к кастом элементу
    public static <T extends CustomElement> T scrollDownToCustomElement(T element) {
        smallWait();
        for (int i = 0; i < 10; i++) {
            try {
                if (element.isDisplayed()) {
                    return element;
                }
            } catch (NoSuchElementException ex) {

            } catch (NullPointerException es) {

            }

            scrollDown();
            smallWait();

        }

        return null;
    }

    // Скролим вверх к кастом элементу
    public static <T extends CustomElement> T scrollUpToCustomElement(T element) {

        for (int i = 0; i < 10; i++) {
            try {
                if (element.isDisplayed()) {
                    return element;
                }
            } catch (NoSuchElementException ex) {

            } catch (NullPointerException ex) {

            }
            scrollUp();
            smallWait();
        }

        return null;
    }

    // Скролим вниз к одному элементу из сипска с именем
    public static MobileElement scrollDownToElement(String value, List<MobileElement> elements) {

        waitMills(3000);

        try {
            for (int i = 0; i < 10; i++) {
                for (MobileElement element : elements) {
                    if (element.getText().contains(value)) {
                        return element;
                    }
                }
                scrollDown();
                smallWait();

            }
        } catch (NoSuchElementException ex) {

        }
        return null;
    }


    // Скролим вверх к одному элементу из сипска с именем
    public static MobileElement scrollUpToElement(String value, List<MobileElement> elements) {
        smallWait();
        try {
            for (int i = 0; i < 10; i++) {
                for (MobileElement element : elements) {
                    if (element.getText().contains(value)) {
                        return element;
                    }
                }
                scrollUp();
            }
        } catch (NoSuchElementException ex) {

        }
        return null;
    }

    // Скролим вниз к одному кастом элементу из сипска с именем
    public static <T extends CustomElement> T scrollDownToCustomElement(String value, List<T> elements) {

        smallWait();
        try {
            for (int i = 0; i < 10; i++) {
                for (T element : elements) {
                    try {
                        if (element.getText().contains(value)) {
                            return element;
                        }
                    } catch (ConcurrentModificationException ec) {

                    } catch (NoSuchElementException ex) {

                    } catch (IndexOutOfBoundsException ind) {

                    } catch (NullPointerException ex) {

                    } catch (StaleElementReferenceException ex) {

                    }
                }
                scrollDown();
                smallWait();

            }
        } catch (NoSuchElementException ex) {

        }

        return null;
    }


    // Скролим вниз к одному кастом элементу из сипска с индексом
    public static <T extends CustomElement> T scrollDownToCustomElement(int index, List<T> elements) {

        smallWait();
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    if (isCustomElementDisplayed(elements.get(index))) {
                        return elements.get(index);
                    }
                } catch (ConcurrentModificationException ec) {

                } catch (NoSuchElementException ex) {

                } catch (IndexOutOfBoundsException ind) {

                }
                scrollDown();
                smallWait();

            }
        } catch (NoSuchElementException ex) {

        }

        return null;
    }

    // Скролим вниз к одному элементу из сипска с индексом
    public static MobileElement scrollDownToElement(int index, List<MobileElement> elements) {

        smallWait();
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    if (isElementDisplayed(elements.get(index))) {
                        return elements.get(index);
                    }
                } catch (ConcurrentModificationException ec) {

                } catch (NoSuchElementException ex) {

                } catch (IndexOutOfBoundsException ind) {

                }
                scrollDown();
                smallWait();

            }
        } catch (NoSuchElementException ex) {

        }

        return null;
    }


    public static <T extends CustomElement> T scrollUpToCustomElement(String value, List<T> elements) {
        smallWait();
        try {
            for (int i = 0; i < 10; i++) {
                for (T element : elements) {
                    try {
                        if (element.getText().contains(value)) {
                            return element;
                        }
                    } catch (ConcurrentModificationException ec) {

                    } catch (NoSuchElementException ex) {

                    } catch (IndexOutOfBoundsException ind) {

                    }
                }
                scrollUp();
                smallWait();
            }
        } catch (NoSuchElementException ex) {

        }

        return null;
    }


    public static void selectCheckBox(MobileElement element) {
        if (element.getAttribute("checked").contains("false")) {
            element.click();
        }
    }

    public static void deselectCheckBox(MobileElement element) {
        if (element.getAttribute("checked").contains("true")) {
            element.click();
        }
    }

    public static void clickToPointFromElement(int tapX, int tapY, MobileElement element) {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        int elementX = element.getCenter().getX();
        int elementY = element.getCenter().getY();
        TouchAction touchAction = new TouchAction((MobileDriver) driver);
        touchAction.tap(elementX + tapX, elementY + tapY).perform();
    }

    public static void clickToScreenCenter() {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        int elementX = driver.manage().window().getSize().getWidth() / 2;
        int elementY = driver.manage().window().getSize().getHeight() / 2;
        TouchAction touchAction = new TouchAction((MobileDriver) driver);
        touchAction.tap(elementX, elementY).perform();
    }


    public static void emulateEnterButtonOnAndroid() {
        WebDriver facade = getDriver();
        WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
    }

    public static String getProrerty(String properyName) {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        return variables.getProperty(properyName);
    }

    public static void captureLog(String testName) {

        try {

            WebDriver facade = getDriver();
            WebDriver driver = ((WebDriverFacade) facade).getProxiedDriver();


            DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);
//            String logPath = System.getProperty("user.dir") + "\\src\\target\\logs\\";
            String logPath = "target\\logs\\";
            new File(logPath).mkdir();

            List<LogEntry> logEntries = ((AndroidDriver) driver).manage().logs().get("logcat").filter(Level.ALL);

            File logFile = new File(logPath + testName + ".txt");
            FileOutputStream oFile = new FileOutputStream(logFile, false);
            PrintWriter log_file_writer = new PrintWriter(oFile);
            log_file_writer.println(logEntries);
            log_file_writer.flush();
            oFile.close();
//        log.info(driver.getSessionId() + ": Saving device log - Done.");

        } catch (Exception ex) {

        }

    }

    public static String[] getAdbLogCat(String fileName) {

        String[] commands = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "adb logcat -d >  target/logs/" + fileName + ".txt"};

        try {
            Process p = Runtime.getRuntime().exec(commands);
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            final StringBuffer output = new StringBuffer();
            String line;
            ArrayList<String> arrList = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            return (String[]) arrList.toArray(new String[0]);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
            return new String[]{};
        }
    }

    //парсинг строки
    public static String parseString(String string, String pattern) {
        Pattern pt = Pattern.compile(pattern);

        Matcher matcher = pt.matcher(string);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }


    static List<Process> allProcesses = new ArrayList<>();

    public static void saveLog(String fileName) {
        String logPath = "target\\logs\\";
        new File(logPath).mkdir();
        String[] commands = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "adb logcat -c > target/logs/" + fileName + ".txt"};
        Process proc = null;

        try {
            proc = Runtime.getRuntime().exec(commands);
            allProcesses.add(proc);
        } catch (IOException e) {

        } finally {
            if (getDriver() == null)
                proc.destroy();
        }
    }


    public static void copyAndDeleteLogFile(String fileName) {
        String logPath = "target\\logs\\";
        new File(logPath).mkdir();

        String[] copy = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "cp target/temp/" + fileName + ".txt target/logs"};
        String[] del = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "del target/temp/" + fileName + ".txt"};
        String[] cleanLog = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "adb logcat -c"};

        try {
            Runtime.getRuntime().exec(copy).destroy();
            Runtime.getRuntime().exec(del);
            Runtime.getRuntime().exec(cleanLog);
        } catch (IOException e) {

        } finally {
        }
    }


    public static void saveLog2(String fileName) throws Exception {
//            DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");
//            Date today = Calendar.getInstance().getTime();
//            String reportDate = df.format(today);
//
//            log.info(driver.getSessionId() + ": Saving device log...");
//            List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
//            File logFile = new File(logPath + reportDate + "_" + testName + ".txt");
//            PrintWriter log_file_writer = new PrintWriter(logFile);
//            log_file_writer.println(logEntries );
//            log_file_writer.flush();
//            log.info(driver.getSessionId() + ": Saving device log - Done.");
//        }
    }

    public static void cleanLog() {
        waitMills(3000);

        String[] kill = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "tskill bash"};
//        String[] commands = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "adb logcat -c"};

        try {
            Runtime.getRuntime().exec(kill).destroy();
//            Runtime.getRuntime().exec(commands).destroy();
        } catch (IOException e) {

        }
    }

    public static void killAllprocess(){
        for (Process proc : allProcesses){
            proc.exitValue();
            proc.destroy();
        }
    }

    /*    public static void cleanLog() {

            String[] commands = {"C:\\Program Files\\Git\\bin\\bash.exe", "-c", "adb logcat -c"};

            try {
                Runtime.getRuntime().exec(commands);
            } catch (IOException e) {

            }
        }*/


}