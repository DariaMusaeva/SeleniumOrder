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
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
     void setupUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestForm() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldTestFormWithoutAgreement() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79000000000");
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text);
    }

    @Test
    void shouldTestFormWithInvalidNameV1() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("1234");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldTestFormWithInvalidNameV2() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("^&*");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldTestFormWithEmptyName() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldTestFormWithInvalidPhoneV1() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7900000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldTestFormWithInvalidPhoneV2() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldTestFormWithInvalidPhoneV3() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldTestFormWithInvalidPhoneV4() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790000000000");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldTestFormWithEmptyPhone() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }
}
