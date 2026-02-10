package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = "http://localhost:" + serverPort + "/product";
    }

    @Test
    void createProduct_isCorrect(ChromeDriver driver) {
        // 1. Navigate to create page directly or via list
        driver.get(baseUrl + "/create");

        // 2. Fill in the form
        // Inputs have th:field="*{productName}" which usually generates name="productName"
        // OR we can use the IDs from your HTML: id="nameInput" and id="quantityInput"
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Selenium Test Product");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("100");

        // 3. Submit
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        submitButton.click();

        // 4. Verify redirection to list
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));

        // 5. Verify the product is in the table
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Selenium Test Product"));
        assertTrue(pageSource.contains("100"));
    }
}