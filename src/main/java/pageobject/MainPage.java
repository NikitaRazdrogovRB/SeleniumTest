package pageobject;

import org.openqa.selenium.WebElement;
import selenium.BaseTest;
import selenium.Utils;

import java.util.*;
import java.util.stream.Collectors;

import static selenium.Utils.MED_WAIT;
import static selenium.Utils.MIN_WAIT;
import static selenium.Utils.getElementByXpath;

public class MainPage {
    private static final String URL = "http://www.ozon.ru/";
    private static final String TOBASKET = "//div[contains(@class, 'mAddToCart') and not(contains(@class, 'mMicro')) and text()=' В корзину ']";
    private static final String BASKET = "//div[@class='mCart']/parent::*";
    private static final String SIGNIN = "//div[contains(@class,'LoginWindowButton')]";
    private static final String SIGNOUT = "//div[contains(@class,'LogOff ') and contains(text(), 'Выйти')]";
    private static final String LOGIN = "//input[@name='login']";
    private static final String PASSWORD = "//input[@name='Password']";
    private static final String MYOZON = "//span[@class='ePanelLinks_Label']";
    private static final String ENTRY = "//div[contains(@class, 'jsLoginPanel')  and contains(text(), 'Вход')]";
    private static final String GOODSNAME = "//div[contains(@class, 'mAddToCart')]/ancestor::div[@itemprop='itemListElement']//div[@itemprop='name']";
    private static final String SEARCH = "//input[@name='SearchText']";
    private static final String STARTSEARCH = "//input[@name='StartSearch']/parent::*";



    private List<String> pickedGoods = new ArrayList<>();

    public void openPage() {
        BaseTest.driver.get(URL);
    }

    public void logIn(String email, String pass) throws InterruptedException {
        Utils.hoverElement(MYOZON);
        getElementByXpath(ENTRY, MED_WAIT).click();
        getElementByXpath(LOGIN, MED_WAIT).sendKeys(email);
        getElementByXpath(PASSWORD, MED_WAIT).sendKeys(pass);
        Thread.sleep(1000);
        getElementByXpath(SIGNIN, MED_WAIT).click();
    }

    public void search(String text) {
        getElementByXpath(SEARCH, MED_WAIT).sendKeys(text);
        getElementByXpath(STARTSEARCH, MED_WAIT).click();
    }

    public void logOut() {
        Utils.hoverElement(MYOZON);
        getElementByXpath(SIGNOUT, MED_WAIT).click();
    }

    public void goToBasket() {
        Utils.hoverElement(MYOZON);
        getElementByXpath(BASKET, MIN_WAIT).click();
    }

    public void pickGoods() {
        List<WebElement> elements =  Utils.getListElementsByXpath(GOODSNAME, MED_WAIT);
        pickedGoods = getEvenElements(elements).stream()
                .map(WebElement::getText).collect(Collectors.toList());
        elements = Utils.getListElementsByXpath(TOBASKET, MED_WAIT);
        getEvenElements(elements).forEach(WebElement::click);
    }

    private <T> List<T> getEvenElements(List<T> list) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                result.add(list.get(i));
            }
        }
        return result;
    }



    public static void main(String[] args) {
        ArrayList<String> arrayList =
                new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        System.out.println(new MainPage().getEvenElements(arrayList));
    }

    public List<String> getPickedGoods() {
        return pickedGoods;
    }
}
