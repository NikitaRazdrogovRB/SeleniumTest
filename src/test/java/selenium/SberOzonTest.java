package selenium;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pageobject.Basket;
import pageobject.MainPage;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Автотест для сайта Ozon.ru
 */
public class SberOzonTest extends BaseTest {

     // Инициализация драйвера
    @BeforeSuite
    public void init() {
        driver = Utils.initDriver();
//        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

//    1.       Перейдите на сервис http://www.ozon.ru/
//    2.       Выполните авторизацию на сервисе с ранее созданным логином и паролем
//    3.       Выполните поиск по «iPhone»
//    4.       Из результатов поиска добавьте в корзину все четные товары -- не все товары можно добавить, брались четные из тех что можно
//    5.       Перейдите в корзину, убедитесь, что все добавленные ранее товары находятся в корзине
//    6.       Удалите все товары из корзины
//    7.       Разлогиньтесь с сервиса
//    8.       Выполните авторизацию на сервисе
//    9.       Проверьте, что корзина не содержит никаких товаров

    @Features("Добавление товаров в корзину")
    @Stories("Я как пользователь хочу добавить покупки в корзину")
    @Title("Добавление товаров в корзину")
    @Test(description = "Добавление товаров в корзину", priority = 1)
    public void searchAndAddToBusket() throws InterruptedException {
        driver.get("http://www.ozon.ru/");
        MainPage mainPage = new MainPage();
        mainPage.openPage();
        mainPage.logIn("autotest@mail.ru", "PA1!ssword"); // по непонятным причинам иногда пишет неверный логин или пароль. Looks like defect
        Thread.sleep(5000);  //здесь и далее Thread.sleep в качестве костыля для стабильности теста, при локальном развертывании стенда будут не нужны
        mainPage.search("iPhone");
        Thread.sleep(5000);
        mainPage.pickGoods();
        mainPage.goToBasket();
        Basket basket = new Basket();
        Collection<String> list = basket.getGoodsInBasket();
        assertTrue(list.containsAll(mainPage.getPickedGoods()));
    }

    @Features("Удаление из корзины, логин + логаут")
    @Stories("Я как пользователь хочу удалить товары из корзины")
    @Title("Проверка удаления покупок")
    @Test(description = "Переход в раздел \"О нас\"", priority = 2, dependsOnMethods = "searchAndAddToBusket")
    public void CleanUpBasket() throws InterruptedException {
        Basket basket = new Basket();
        basket.removeAll();
        Thread.sleep(3000);
        MainPage mainPage = new MainPage();
        mainPage.logOut();
        Thread.sleep(5000);
        mainPage.logIn("autotest@mail.ru", "PA1!ssword");
        Thread.sleep(5000);
        mainPage.goToBasket();
        assertTrue("Корзина не очищена", basket.getBasketText().contains("Корзина пуста"));
    }

    /**
     * Закрытие драйвера
     */
    @AfterSuite
    public void close() {
        driver.close();
    }
}