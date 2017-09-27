package selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by nikita
 */
public class Utils {


    public static final int MED_WAIT = 14;
    public static final int MIN_WAIT = 5;
    /**
     * Настройка драйвера
     * @return настроенный драйвер
     */
    static WebDriver initDriver() {
        final WebDriver wrappedDriver;
        String chrome = Paths.get(System.getProperty("user.dir"), "webdrivers", "chromedriver-2.32-x64.exe").toString();
        if (System.getProperty("os.name").toLowerCase().contains("linux"))
            chrome = chrome.replace("chromedriver-2.32-x64.exe", "chromedriver-2.32-x86-lin");
        try {
            System.setProperty("webdriver.chrome.driver", chrome);
            wrappedDriver = new ChromeDriver();
        } catch (Exception e) {
            Assert.fail();
            return null;
        }
        return new EventFiringWebDriver(wrappedDriver);
    }

    /**
     * Снятие скриншота переданного WebElement
     * @param driver драйвер для снятия скриншота
     * @param element элемент который нужно "соскринить"
     * @return файл с изображением элемента
     * @throws IOException
     */
    public static File captureElementBitmap(final WebDriver driver, final WebElement element) throws IOException {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        Rectangle rect = new Rectangle(width, height);
        Point p = element.getLocation();
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
        ImageIO.write(dest, "png", screen);
        return screen;
    }

    public static WebElement getElementByXpath(final String xpath, final long waitTime) {
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, waitTime);
        wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
//                .withTimeout(waitTime, TimeUnit.SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return BaseTest.driver.findElement(By.xpath(xpath));
    }

    public static List<WebElement> getListElementsByXpath(final String xpath, final long waitTime) {
        WebDriverWait wait = new WebDriverWait(BaseTest.driver, waitTime);
        wait.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
//                .withTimeout(waitTime, TimeUnit.SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return BaseTest.driver.findElements(By.xpath(xpath));
    }

    public static void hoverElement(String xpath) {
        Actions actions = new Actions(BaseTest.driver);
        actions.moveToElement(getElementByXpath(xpath, 3)).build().perform();
    }
 }