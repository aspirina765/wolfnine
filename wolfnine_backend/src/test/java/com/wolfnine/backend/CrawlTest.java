package com.wolfnine.backend;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WolfnineBackendApplication.class)
public class CrawlTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void crawling() {
        driver.get("https://fptshop.com.vn/dien-thoai");
        List<WebElement> elements = driver.findElements(By.cssSelector("#root > main > div > div.row.fspdbox > div.col-9.p-0 > div.card.fplistbox > div > div.cdt-product-wrapper.m-b-20 h3"));
        for (WebElement element: elements) {
            System.out.println(element.getText());
        }
    }
}
