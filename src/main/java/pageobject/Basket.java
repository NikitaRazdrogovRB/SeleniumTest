package pageobject;

import org.openqa.selenium.WebElement;
import selenium.Utils;

import java.util.Collection;
import java.util.stream.Collectors;

import static selenium.Utils.MED_WAIT;
import static selenium.Utils.getElementByXpath;

public class Basket {

    private static final String GOODSNAME = "//span[@class='eCartItem_nameValue']";
    private static final String REMOVEALL = "//div[contains(@class, 'jsRemoveAll')]";
    private static final String SIGNOUT = "//div[contains(@class,'LogOff') and contains(text(), 'Выйти')]";
    private static final String MYOZON = "//span[@class='ePanelLinks_Label']";
    private static final String EMPTYBUCKET = "//span[@class='jsInnerContentpage_title']";


    public void logIn(String email, String pass) {
        Utils.hoverElement(MYOZON);
        getElementByXpath(SIGNOUT, MED_WAIT);
    }

    public void removeAll() {
        getElementByXpath(REMOVEALL, MED_WAIT);
    }

    public String getBasketText() {
        return getElementByXpath(EMPTYBUCKET, MED_WAIT).getText();
    }

    public Collection<String> getGoodsInBasket() {
        return Utils.getListElementsByXpath(GOODSNAME, MED_WAIT).stream()
                .map(WebElement::getText).collect(Collectors.toList());
    }
}
