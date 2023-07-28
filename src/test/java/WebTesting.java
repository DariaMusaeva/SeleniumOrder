import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebTesting {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mel\\Desktop\\WebTesting\\driver\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
     void setupUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestForm() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
        Thread.sleep(10000);
    }

    @Test
    void shouldTestFormWithInvalidData() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("1234");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
        Thread.sleep(10000);
    }
}
