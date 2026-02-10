package com.example.eshop;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_shouldAppearInProductList(ChromeDriver driver) {
        // Step 1: Open create product page
        driver.get(baseUrl + "/product/create");

        // Step 2: Fill the form (simulate user input)
        driver.findElement(By.id("nameInput"))
                .sendKeys("Functional Test Product");

        driver.findElement(By.id("quantityInput"))
                .sendKeys("30");

        // Step 3: Submit the form
        driver.findElement(By.tagName("button")).click();

        // Step 4: Verify product appears in product list page
        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains("Functional Test Product"));
        assertTrue(pageSource.contains("30"));
    }
}
