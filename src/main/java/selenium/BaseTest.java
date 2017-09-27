package selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.IOException;

/**
 * Класс предназначен для наследования тестовым классом.
 * При наследовании этого класса автоматически будет сниматся скриншот, если тест провален.
 */
public class BaseTest implements IHookable {

    /**
     * Веб драйвер браузера для автотеста
     */
    public static WebDriver driver;

    /**
     * Метод выполняющийся при запуске каждого теста
     * @param callBack колбэк для запуска теста
     * @param testResult результат выполнения теста
     */
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            try {
                takeScreenShot(testResult.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для снятия скриншота с именем теста (метода) из которого он вызван
     * @param methodName имя метода
     * @return скриншот для перехвата генератором отчета allure
     * @throws IOException в случае ошибки ввода вывода
     */
    @Attachment(value = "Ошибка в методе {0}", type = "image/png")
    private byte[] takeScreenShot(String methodName) throws IOException {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
